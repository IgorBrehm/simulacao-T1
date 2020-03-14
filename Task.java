public class Task{
    private final long scheduledTime;
    private final String targetQueue;
    private final String operation;

    public Task(long scheduledTime, String targetQueue, String operation){
        this.scheduledTime = scheduledTime;
        this.targetQueue = targetQueue;
        this.operation = operation;
    }

    public long getScheduledTime(){
        return this.scheduledTime;
    }

    public String getTargetQueue(){
        return this.targetQueue;
    }

    public String getOperation(){
        return this.operation;
    }
}