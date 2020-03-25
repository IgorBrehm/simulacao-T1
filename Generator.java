import java.util.Random;

// Gerador de numeros aleatorios, recebe uma semente como parametro inicial
public class Generator {
    private final Random random;
 
    public Generator(long seed) {
        this.random = new Random(seed);
    }
 
    public synchronized double getNext() {
        return random.nextDouble();
    }
}