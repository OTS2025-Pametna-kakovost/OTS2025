package si.um.feri.measurements.dto.post;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostMeasurementResponseTest {

    @Test
    void recordStoresValues() {
        PostMeasurementResponse resp = new PostMeasurementResponse("OK");
        assertEquals("OK", resp.result());
    }
}
