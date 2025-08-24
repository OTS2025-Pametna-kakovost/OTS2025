package si.um.feri.measurements.dto.post;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostMeasurementTest {

    @Test
    void recordStoresValues() {
        PostMeasurement pm = new PostMeasurement(7L, 22.5);
        assertEquals(7L, pm.id());
        assertEquals(22.5, pm.avgTemperature(), 0.0001);
    }
}
