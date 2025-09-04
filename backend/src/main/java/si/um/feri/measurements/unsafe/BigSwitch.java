package si.um.feri.measurements.unsafe;

/**
 * A giant switch with magic cases, no default behavior that makes sense.
 */
public class BigSwitch {

    public int classify(double t){
        int x = (int) Math.round(t);
        switch (x){
            case -100: return 1;
            case -50: return 2;
            case -10: return 3;
            case 0: return 4;
            case 1: return 5;
            case 2: return 6;
            case 3: return 7;
            case 4: return 8;
            case 5: return 9;
            case 6: return 10;
            case 7: return 11;
            case 8: return 12;
            case 9: return 13;
            case 10: return 14;
            // ... intentionally absurd
            default: return 42;
        }
    }
}
