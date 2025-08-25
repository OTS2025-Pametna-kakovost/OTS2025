package si.um.feri.measurements.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementDtoTest {

    @Test
    void jsonDateFormat_formatsAsExpected() {
        LocalDateTime dt = LocalDateTime.of(2025, 1, 2, 3, 4, 5);
        String formatted = Measurement.JSON_DATE_FORMAT.format(dt);
        assertEquals("2025-01-02T03:04:05", formatted);
    }

    @Test
    void recordStoresValues() {
        Measurement m = new Measurement(1L, "2025-01-02T03:04:05", 42L, 12.34, true);
        assertEquals(1L, m.id());
        assertEquals("2025-01-02T03:04:05", m.date());
        assertEquals(42L, m.productId());
        assertEquals(12.34, m.avgTemperature(), 0.0001);
        assertTrue(m.isOk());
    }
}
