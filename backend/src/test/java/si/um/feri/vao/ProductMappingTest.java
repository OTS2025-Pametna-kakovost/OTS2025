package si.um.feri.measurements.vao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductMappingTest {

    @Test
    void constructorFromDto_setsFields() {
        si.um.feri.measurements.dto.Product dto =
                new si.um.feri.measurements.dto.Product(null, "Thermometer", 100.0, -10.0);

        Product entity = new Product(dto);

        assertEquals("Thermometer", entity.getName());
        assertEquals(100.0, entity.getMaxMeasure(), 1e-9);
        assertEquals(-10.0, entity.getMinMeasure(), 1e-9);
    }

    @Test
    void updateFrom_overwritesEntityFields() {
        Product entity = new Product();
        entity.setName("Old");
        entity.setMaxMeasure(1.0);
        entity.setMinMeasure(0.0);

        si.um.feri.measurements.dto.Product dto =
                new si.um.feri.measurements.dto.Product(null, "NewName", 50.5, 5.5);

        entity.updateFrom(dto);

        assertAll(
                () -> assertEquals("NewName", entity.getName()),
                () -> assertEquals(50.5, entity.getMaxMeasure(), 1e-9),
                () -> assertEquals(5.5, entity.getMinMeasure(), 1e-9)
        );
    }
}