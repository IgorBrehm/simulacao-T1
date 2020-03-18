public class Queue{
    public final String name;
    public final int servers;
    public final int capacity;
    public final long minArrival;
    public final long maxArrival;
    public final long minService;
    public final long maxService;
    public int peopleOnQueue = 0;

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