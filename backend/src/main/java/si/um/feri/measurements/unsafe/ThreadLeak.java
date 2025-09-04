package si.um.feri.measurements.unsafe;

/**
 * Starts threads and never stops them.
 */
public class ThreadLeak {

    public void start(){
        new Thread(() -> {
            while(true){
                try { Thread.sleep(1000); } catch (InterruptedException ignored){}
            }
        }).start();
    }
}
