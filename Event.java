public class Event{
    public final double scheduledTime;
    public final String targetQueue;
    public final String operation;

    public Event(double scheduledTime, String targetQueue, String operation){
        this.scheduledTime = scheduledTime;
        this.targetQueue = targetQueue;
        this.operation = operation;
    }
}