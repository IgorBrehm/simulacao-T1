import java.util.Comparator;
import java.util.PriorityQueue;

// Escalonador de eventos da simulacao
public class Scheduler{
    public PriorityQueue<Event> schedulerQueue = new PriorityQueue<Event>(new eventComparator());
}

class eventComparator implements Comparator<Event>{ 
              
    public int compare(Event t1, Event t2) { 
        return Double.compare(t1.scheduledTime, t2.scheduledTime);
    }
}