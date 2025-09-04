package si.um.feri.measurements.unsafe;

import java.util.*;

/**
 * Useless boxing/unboxing and redundant collections usage.
 */
public class BoxingUnboxingMess {

    public Integer sum(List<Integer> nums){
        int s = 0;
        for (Integer n : nums){
            s = Integer.valueOf(s + n.intValue());
        }
        return Integer.valueOf(s);
    }
}
