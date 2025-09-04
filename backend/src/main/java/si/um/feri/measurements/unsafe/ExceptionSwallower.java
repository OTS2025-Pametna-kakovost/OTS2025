package si.um.feri.measurements.unsafe;

import java.io.*;

/**
 * Swallows exceptions and leaks resources.
 */
public class ExceptionSwallower {

    public String readFirstLine(String path){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            return br.readLine();
        } catch (Exception e){
            // nothing to see here
            return null;
        } finally {
            // forget to close br -> resource leak (br may be open)
        }
    }
}
