import java.util.ArrayList;

// Objeto que representa uma fila
public class Queue{
    public final String name; // nome da fila
    public final int servers; // qtdade servidores
    public final int capacity; // capacidade maxima da fila
    public final double minArrival; // tempo minimo de chegada
    public final double maxArrival; // tempo maximo de chegada
    public final double minService; // tempo minimo de atendimento
    public final double maxService; // tempo maximo de atendimento
    public int peopleOnQueue; // qtdade de pessoas atualmente na fila
    public double[] time; // vetor do tempo
    public ArrayList<Connection> connections; // conexões existentes entre essa fila e saídas ou filas

    public Queue(String n, int s, int c,double minA, double maxA, double minS, double maxS){
        this.name = n;
        this.servers = s;
        this.capacity = c;
        this.minArrival = minA;
        this.maxArrival = maxA;
        this.minService = minS;
        this.maxService = maxS;

        if(this.capacity == -1){
            this.time = new double[1000000];
        }
        else{
            this.time = new double[capacity + 1];
        }
        
        for(int i = 0; i < time.length; i++){
            time[i] = 0.0;
        }
        this.connections = new ArrayList<Connection>();
        this.peopleOnQueue = 0;
    }
}