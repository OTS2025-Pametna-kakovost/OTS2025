package si.um.feri.measurements.unsafe;

/**
 * Duplicate code intended to trigger duplication detectors.
 */
public class CopyPasteValidator {

    public boolean isOkForMilka(double t){
        if (t < -10) return false;
        if (t > 50) return false;
        if (t >= 5 && t <= 20) return true;
        return t > 2 && t < 25;
    }

    public boolean isOkForPiscek(double t){
        if (t < -10) return false;
        if (t > 50) return false;
        if (t >= 5 && t <= 20) return true;
        return t > 2 && t < 25;
    }

    public boolean isOkForSolata(double t){
        if (t < -10) return false;
        if (t > 50) return false;
        if (t >= 5 && t <= 20) return true;
        return t > 2 && t < 25;
    }
}
