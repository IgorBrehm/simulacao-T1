public class Simulation{

    protected static long time;
    private static Scheduler scheduler = new Scheduler();
    private static Queue F1 = new Queue("F1", 1, 3, 2, 3, 4, 5);

    public Simulation(){

        time = 0;
        Task firstArrival = new Task(2, "F1", "arrival");
        scheduler.schedulerQueue.add(firstArrival);

        for(int iterations = 0; iterations < 10; iterations++){
            Task nextTask = scheduler.schedulerQueue.poll();
            System.out.println("Next task: "+nextTask.getOperation()+" -> "+nextTask.getTargetQueue());

            if(nextTask.getOperation().equals("arrival")){
                // chegada
                arrival(nextTask);
            }
            else {
                // saida
                passage(nextTask);
            }
        }
    }

    private void arrival(Task task){
        time += task.getScheduledTime();
        
        if(F1.getCurrentPeopleOnQueue() < F1.getCapacity()){
            F1.addToQueue();
            if(F1.getCurrentPeopleOnQueue()<= 1){
                scheduler.schedulerQueue.add(new Task(scheduledTime, "F1", "passage"));
            }
        }
    }

    private void passage(Task task){

    }
}