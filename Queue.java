public class Queue{
    private final String name;
    private final int servers;
    private final int capacity;
    private final long minArrival;
    private final long maxArrival;
    private final long minService;
    private final long maxService;
    private static int peopleOnQueue = 0;

    public Queue
    (String name, int servers, int capacity,int minArrival, 
    int maxArrival, int minService, int maxService){
        this.name = name;
        this.servers = servers;
        this.capacity = capacity;
        this.minArrival = minArrival;
        this.maxArrival = maxArrival;
        this.minService = minService;
        this.maxService = maxService;
    }

    public String getName(){
        return this.name;
    }

    public int getServers(){
        return this.servers;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public long getMinArrival(){
        return this.minArrival;
    }

    public long getMaxArrival(){
        return this.maxArrival;
    }

    public long getMinService(){
        return this.minService;
    }

    public long getMaxService(){
        return this.maxService;
    }

    public static int getCurrentPeopleOnQueue(){
        return peopleOnQueue;
    }

    public static void removeFromQueue(){
        peopleOnQueue -= 1;
    }

    public static void addToQueue(){
        peopleOnQueue += 1;
    }
}