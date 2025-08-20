package si.um.feri.measurements.rest;

import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import si.um.feri.measurements.dao.MeasurementRepository;
import si.um.feri.measurements.dao.ProductRepository;
import si.um.feri.measurements.dto.post.PostMeasurement;
import si.um.feri.measurements.dto.post.PostMeasurementResponse;
import si.um.feri.measurements.vao.Measurement;
import si.um.feri.measurements.vao.Product;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testing library and framework:
 * - JUnit 5 (Jupiter) for test runner and assertions
 * - Mockito for mocking repositories and verifying interactions
 * - Mutiny Uni for async return types (resolved in tests with await().indefinitely())
 *
 * These are unit tests that instantiate MeasurementController directly and inject Mockito mocks,
 * avoiding the need for a full Quarkus runtime.
 */
public class MeasurementControllerTest {

    MeasurementController controller;

    MeasurementRepository measurementRepository;
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        controller = new MeasurementController();
        measurementRepository = Mockito.mock(MeasurementRepository.class);
        productRepository = Mockito.mock(ProductRepository.class);

        // Fields are package-private; we can assign directly within same package
        controller.measurementRepository = measurementRepository;
        controller.productRepository = productRepository;
    }

    private Product stubProduct(long id, double min, double max) {
        Product p = mock(Product.class);
        when(p.getId()).thenReturn(id);
        // Note: Method names reflect the controller's usage: getminmeasure() and getMaxMeasure()
        when(p.getminmeasure()).thenReturn(min);
        when(p.getMaxMeasure()).thenReturn(max);
        return p;
    }

    private void stubPersistOk() {
        // persistAndFlush returns Uni<Void> or Uni<Measurement>; the controller ignores the item and maps to RestResponse
        // We'll return Uni<Void> to simulate completion.
        when(measurementRepository.persistAndFlush(any(Measurement.class)))
            .thenReturn(Uni.createFrom().nullItem());
    }

    @Nested
    @DisplayName("addMeasurement happy paths and boundary conditions")
    class HappyAndBoundary {

        @Test
        @DisplayName("returns ok when avgTemperature is within [min, max]")
        void returnsOkWithinBounds() {
            // Given
            long productId = 42L;
            Product product = stubProduct(productId, 5.0, 10.0);
            when(productRepository.findById(productId)).thenReturn(Uni.createFrom().item(product));
            stubPersistOk();

            PostMeasurement dto = new PostMeasurement(String.valueOf(productId), 7.5);

            // When
            RestResponse<PostMeasurementResponse> response = controller.addMeasurement(dto)
                .await().indefinitely();

            // Then
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertNotNull(response.getEntity());
            assertEquals("ok", response.getEntity().message());

            // Verify Measurement persisted with ok=true and product is set
            ArgumentCaptor<Measurement> captor = ArgumentCaptor.forClass(Measurement.class);
            verify(measurementRepository, times(1)).persistAndFlush(captor.capture());
            Measurement saved = captor.getValue();
            assertNotNull(saved);
            assertTrue(saved.isOk(), "Measurement.ok should be true when avgTemperature within thresholds");
        }

        @Test
        @DisplayName("returns ok when avgTemperature equals min threshold")
        void returnsOkAtMinBoundary() {
            long productId = 99L;
            Product product = stubProduct(productId, 3.1, 8.2);
            when(productRepository.findById(productId)).thenReturn(Uni.createFrom().item(product));
            stubPersistOk();

            PostMeasurement dto = new PostMeasurement(String.valueOf(productId), 3.1);

            RestResponse<PostMeasurementResponse> response = controller.addMeasurement(dto)
                .await().indefinitely();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals("ok", response.getEntity().message());

            ArgumentCaptor<Measurement> captor = ArgumentCaptor.forClass(Measurement.class);
            verify(measurementRepository).persistAndFlush(captor.capture());
            assertTrue(captor.getValue().isOk());
        }

        @Test
        @DisplayName("returns ok when avgTemperature equals max threshold")
        void returnsOkAtMaxBoundary() {
            long productId = 100L;
            Product product = stubProduct(productId, 0.0, 1.0);
            when(productRepository.findById(productId)).thenReturn(Uni.createFrom().item(product));
            stubPersistOk();

            PostMeasurement dto = new PostMeasurement(String.valueOf(productId), 1.0);

            RestResponse<PostMeasurementResponse> response = controller.addMeasurement(dto)
                .await().indefinitely();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals("ok", response.getEntity().message());

            ArgumentCaptor<Measurement> captor = ArgumentCaptor.forClass(Measurement.class);
            verify(measurementRepository).persistAndFlush(captor.capture());
            assertTrue(captor.getValue().isOk());
        }
    }

    @Nested
    @DisplayName("addMeasurement out-of-bounds conditions")
    class OutOfBounds {

        @Test
        @DisplayName("returns not ok when avgTemperature below min")
        void returnsNotOkBelowMin() {
            long productId = 7L;
            Product product = stubProduct(productId, 2.0, 5.0);
            when(productRepository.findById(productId)).thenReturn(Uni.createFrom().item(product));
            stubPersistOk();

            PostMeasurement dto = new PostMeasurement(String.valueOf(productId), 1.99);

            RestResponse<PostMeasurementResponse> response = controller.addMeasurement(dto)
                .await().indefinitely();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals("not ok", response.getEntity().message());

            ArgumentCaptor<Measurement> captor = ArgumentCaptor.forClass(Measurement.class);
            verify(measurementRepository).persistAndFlush(captor.capture());
            assertFalse(captor.getValue().isOk(), "Measurement.ok should be false when below min");
        }

        @Test
        @DisplayName("returns not ok when avgTemperature above max")
        void returnsNotOkAboveMax() {
            long productId = 8L;
            Product product = stubProduct(productId, 10.0, 12.0);
            when(productRepository.findById(productId)).thenReturn(Uni.createFrom().item(product));
            stubPersistOk();

            PostMeasurement dto = new PostMeasurement(String.valueOf(productId), 12.0001);

            RestResponse<PostMeasurementResponse> response = controller.addMeasurement(dto)
                .await().indefinitely();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals("not ok", response.getEntity().message());

            ArgumentCaptor<Measurement> captor = ArgumentCaptor.forClass(Measurement.class);
            verify(measurementRepository).persistAndFlush(captor.capture());
            assertFalse(captor.getValue().isOk(), "Measurement.ok should be false when above max");
        }
    }

    @Nested
    @DisplayName("addMeasurement error recovery")
    class ErrorRecovery {

        @Test
        @DisplayName("returns 406 product-not-found when ProductRepository.findById fails")
        void returnsProductNotFoundOnFailure() {
            long productId = 1234L;
            when(productRepository.findById(productId))
                .thenReturn(Uni.createFrom().failure(new RuntimeException("not found")));

            PostMeasurement dto = new PostMeasurement(String.valueOf(productId), 20.0);

            RestResponse<PostMeasurementResponse> response = controller.addMeasurement(dto)
                .await().indefinitely();

            assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
            assertNotNull(response.getEntity());
            assertEquals("product-not-found", response.getEntity().message());

            // Ensure that when findById fails, persist is never attempted
            verify(measurementRepository, never()).persistAndFlush(any());
        }

        @Test
        @DisplayName("propagates ok/not ok even if persist completes with null item")
        void persistCompletesWithNullItem() {
            long productId = 55L;
            Product product = stubProduct(productId, -5.0, 5.0);
            when(productRepository.findById(productId)).thenReturn(Uni.createFrom().item(product));

            // Explicitly return null item to ensure mapping ignores the item and still produces RestResponse
            when(measurementRepository.persistAndFlush(any(Measurement.class)))
                .thenReturn(Uni.createFrom().nullItem());

            PostMeasurement dto = new PostMeasurement(String.valueOf(productId), 0.0);

            RestResponse<PostMeasurementResponse> response = controller.addMeasurement(dto)
                .await().indefinitely();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals("ok", response.getEntity().message());
        }
    }

    @Nested
    @DisplayName("input handling robustness")
    class InputHandling {

        @Test
        @DisplayName("gracefully handles numeric id parsing from PostMeasurement.id()")
        void parsesNumericIdFromString() {
            long productId = 202L;
            Product product = stubProduct(productId, 1.0, 2.0);
            when(productRepository.findById(productId)).thenReturn(Uni.createFrom().item(product));
            stubPersistOk();

            // id is numeric but provided as string (as expected by controller)
            PostMeasurement dto = new PostMeasurement("202", 1.5);

            RestResponse<PostMeasurementResponse> response = controller.addMeasurement(dto)
                .await().indefinitely();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals("ok", response.getEntity().message());
        }

        @Test
        @DisplayName("invalid numeric id in PostMeasurement.id() causes failure mapped to 406 product-not-found")
        void invalidNumericIdMapsToNotAcceptable() {
            // Controller uses Long.valueOf(m.id()), so a non-numeric id will throw NumberFormatException,
            // which should be recovered by onFailure().recoverWithItem(...) producing NOT_ACCEPTABLE.
            PostMeasurement dto = new PostMeasurement("abc", 1.5);

            RestResponse<PostMeasurementResponse> response = controller.addMeasurement(dto)
                .await().indefinitely();

            assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatus());
            assertEquals("product-not-found", response.getEntity().message());

            verifyNoInteractions(productRepository);
            verifyNoInteractions(measurementRepository);
        }
    }
}