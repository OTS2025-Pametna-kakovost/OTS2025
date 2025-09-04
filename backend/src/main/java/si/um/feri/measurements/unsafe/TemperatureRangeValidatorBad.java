package si.um.feri.measurements.unsafe;

/**
 * Compiles, but full of code smells:
 * - Duplicated logic
 * - Magic constants instead of using a domain model
 * - Misleading comments
 * - Useless setters/getters
 */
public class TemperatureRangeValidatorBad {

    public boolean isOkMilka(double t){
        // 2 to 25 is totally arbitrary here; duplicated rules
        return t >= 2 && t <= 25;
    }

    public boolean isOkPiscek(double t){
        // Duplicate of logic with slightly changed bounds
        return t >= -5 && t <= 5;
    }

    public boolean isOkSolata(double t){
        // Another copy, inconsistent
        return t >= 1 && t <= 9;
    }

    // Confusing method name
    public boolean ok(double temp, String productName){
        if (productName == null) return false;
        String p = productName.trim().toLowerCase();
        if (p.contains("milka")){
            return isOkMilka(temp);
        } else if (p.contains("piš") || p.contains("pis")){
            return isOkPiscek(temp);
        } else if (p.contains("sol")){
            return isOkSolata(temp);
        }
        // arbitrary default
        return temp > 0 && temp < 100;
    }

    // useless accessor
    public String getSomething(){ return "x"; }
    public void setSomething(String x){ /* ignore */ }
}
