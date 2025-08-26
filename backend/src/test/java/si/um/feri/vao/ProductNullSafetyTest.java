package si.um.feri.measurements.vao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test
    void constructorWithNullDto_throwsException() {
        assertThrows(NullPointerException.class, () -> new Product((si.um.feri.measurements.dto.Product) null));
    }

    @Test
    void updateFromNullDto_throwsException() {
        Product product = new Product();
        assertThrows(NullPointerException.class, () -> product.updateFrom(null));
    }
}