package si.um.feri.measurements.vao;

import org.junit.jupiter.api.Test;
import si.um.feri.measurements.dto.post.PostMeasurement;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementMappingTest {

    @Test
    void constructorFromPostAndProduct_setsValueAndProduct() {
        Product product = new Product();
        product.setId(9L);
        PostMeasurement pm = new PostMeasurement(9L, 23.5);

        // Measurement here is the ENTITY from the same package (vao)
        Measurement entity = new Measurement(pm, product);

        assertEquals(23.5, entity.getValue(), 1e-9);
        assertSame(product, entity.getProduct());
    }

    @Test
    void toDto_usesFormatterAndProductIdAndOkFlag() {
        Product product = new Product();
        product.setId(77L);

        PostMeasurement pm = new PostMeasurement(77L, 12.34);
        Measurement entity = new Measurement(pm, product);

        entity.setCreated(LocalDateTime.of(2025, 1, 2, 3, 4, 5));
        entity.setOk(true);
        entity.setId(123L);

        si.um.feri.measurements.dto.Measurement dto = entity.toDto();

        assertEquals(123L, dto.id());
        assertEquals("2025-01-02T03:04:05", dto.date());
        assertEquals(77L, dto.productId());
        assertEquals(12.34, dto.avgTemperature(), 1e-9);
        assertTrue(dto.isOk());
    }
}