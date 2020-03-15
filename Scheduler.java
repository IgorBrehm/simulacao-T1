import java.util.Comparator;
import java.util.PriorityQueue;

public class Scheduler{
    public PriorityQueue<Task> schedulerQueue = new PriorityQueue<Task>(new taskComparator());
}

class taskComparator implements Comparator<Task>{ 
              
    public int compare(Task t1, Task t2) { 
        if (t1.getScheduledTime() < t2.getScheduledTime()){
            return 1; 
        }
        else if (t1.getScheduledTime() >= t2.getScheduledTime()){ 
            return -1; 
        }
        else{
            return 0;
        }
    }
}