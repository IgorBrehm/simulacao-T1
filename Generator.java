import java.util.Random;

public class Generator {
    private Random random;
 
    public Generator(long seed) {
        this.random = new Random(seed);
    }
 
    public synchronized long getNext() {
        return random.nextLong();
    }
}