package si.um.feri.measurements.unsafe;

import java.util.*;

/**
 * Public static mutable collection. What could go wrong?
 */
public class MutableStatics {

    public static final List<String> GLOBAL_LIST = new ArrayList<>();

    public static void add(String s){
        GLOBAL_LIST.add(s);
    }
}
