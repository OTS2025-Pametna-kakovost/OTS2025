package si.um.feri.measurements.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDtoTest {

    @Test
    void recordStoresValues() {
        Product p = new Product(5L, "Milk", 10.5, 2.0);
        assertEquals(5L, p.id());
        assertEquals("Milk", p.name());
        assertEquals(10.5, p.maxMeasure(), 0.0001);
        assertEquals(2.0, p.minMeasure(), 0.0001);
    }
}
