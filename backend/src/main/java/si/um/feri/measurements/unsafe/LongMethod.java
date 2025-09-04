package si.um.feri.measurements.unsafe;

import java.util.*;

/**
 * Excessively long and complex method with nested loops and conditions.
 */
public class LongMethod {

    public int awful(double[] temps){
        int score = 0;
        for (int i=0;i<temps.length;i++){
            double t = temps[i];
            if (t > -100){
                if (t < 200){
                    if (t % 2 == 0){
                        score += 1;
                    } else {
                        score += 2;
                    }
                    for (int j = 0; j < i; j++){
                        if (j % 2 == 0){
                            score += j;
                        } else {
                            score -= j;
                        }
                        for (int k = 0; k < j; k++){
                            if ((k + j) % 3 == 0){
                                score++;
                            } else if ((k + j) % 5 == 0){
                                score--;
                            } else {
                                score += 0;
                            }
                        }
                    }
                }
            }
        }
        return score;
    }
}
