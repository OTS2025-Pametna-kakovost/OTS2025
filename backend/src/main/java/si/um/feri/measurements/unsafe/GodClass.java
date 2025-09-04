package si.um.feri.measurements.unsafe;

import java.util.*;
/**
 * A classic "God Class" packed with unrelated responsibilities,
 * public mutable state, magic numbers, dead code, and duplicate logic.
 * Intentionally terrible but compilable.
 */
public class GodClass {

    // Public mutable fields (bad encapsulation)
    public static List<String> LOGS = new ArrayList<>();
    public Map<String, Integer> cache = new HashMap<>();
    public String state = "INIT";
    public int counter = 0;

    // Magic numbers galore
    public static final int TEN = 10; // not really magic, but...
    public static final int ELEVEN = 11; // why?
    public static final int THIRTY_SEVEN = 37; // random

    // Unused field
    private String unused = "I am never used";

    // Overly broad exception handling and swallow
    public void doEverything(String product, double temp) {
        try {
            if (product == null) { // naive null check
                product = "UNKNOWN";
            }
            LOGS.add("Start:" + System.currentTimeMillis());
            // duplicate logic (copy-paste)
            boolean ok1 = temp > -50 && temp < 200;
            boolean ok2 = temp > -50 && temp < 200; // duplicate
            if (ok1 && ok2) {
                state = "RUNNING";
            }
            // overcomplicated loop
            for (int i = 0; i < 100; i++) {
                if (i % 3 == 0) {
                    counter++;
                } else if (i % 5 == 0) {
                    counter += 2;
                } else if (i % 7 == 0) {
                    counter += 3;
                } else {
                    counter += 0; // pointless
                }
            }
            // poor cohesion: formatting time and random calc
            new Date().toString();
            double r = new Random().nextDouble();
            if (r > 0.9999) {
                System.out.println("Very rare");
            }
        } catch (Exception e) {
            // swallow
        }
    }

    // Long parameter list / bad naming
    public int c(int a,int b,int c,int d,int e,int f,int g,int h,int i){
        int s = 0;
        int[] arr = new int[]{a,b,c,d,e,f,g,h,i};
        for (int x : arr) { s += x; } // trivial
        return s;
    }

    // equals but no hashCode (and vice versa)
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GodClass gc = (GodClass) o;
        return Objects.equals(state, gc.state) && counter == gc.counter;
    }

    // Dead code
    private void neverCalled(){
        System.out.println("No one will ever see this.");
    }
}
