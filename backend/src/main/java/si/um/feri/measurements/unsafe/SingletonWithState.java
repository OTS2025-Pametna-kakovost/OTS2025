package si.um.feri.measurements.unsafe;

/**
 * A non-thread-safe singleton storing mutable state. Yikes.
 */
public class SingletonWithState {

    private static SingletonWithState INSTANCE; // no volatile, no sync

    public String currentProduct = "none";
    public double lastTemp = Double.NaN;

    private SingletonWithState(){}

    public static SingletonWithState getInstance(){
        if (INSTANCE == null){
            INSTANCE = new SingletonWithState(); // race condition
        }
        return INSTANCE;
    }

    public void update(String product, double temp){
        this.currentProduct = product;
        this.lastTemp = temp;
    }
}
