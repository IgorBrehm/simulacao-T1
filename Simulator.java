import java.io.FileNotFoundException;

public class Simulator {
    public static void main(String args[]) throws FileNotFoundException {
        new Simulation(Long.parseLong(args[0]),Long.parseLong(args[1]),args[2]);
    }
}