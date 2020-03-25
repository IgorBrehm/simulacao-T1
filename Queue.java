// Objeto que representa uma fila
public class Queue{
    public final String name; // nome da fila
    public final int servers; // qtdade servidores
    public final int capacity; // capacidade maxima da fila
    public final long minArrival; // tempo minimo de chegada
    public final long maxArrival; // tempo maximo de chegada
    public final long minService; // tempo minimo de atendimento
    public final long maxService; // tempo maximo de atendimento
    public int peopleOnQueue = 0; // qtdade de pessoas atualmente na fila

    public Queue(String n, int s, int c,int minA, int maxA, int minS, int maxS){
        this.name = n;
        this.servers = s;
        this.capacity = c;
        this.minArrival = minA;
        this.maxArrival = maxA;
        this.minService = minS;
        this.maxService = maxS;
    }

}