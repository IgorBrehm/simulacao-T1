public class Simulation{
    
    protected static long numberOfRandoms = 100000; // qtdade nums aleatorios a serem gerados na simulacao
    protected static long seed = 5; // semente a ser usada pelo gerador
    private static Scheduler scheduler = new Scheduler(); // escalonador de eventos
    private static Queue F1 = new Queue("F1", 1, 5, 2, 4, 3, 5); // fila 1 e seus parâmetros
    protected static double[] time = new double[F1.capacity+1]; // vetor do tempo
    private static Generator generator = new Generator(seed); // gerador de numeros aleatorios
    protected static long lost = 0; // numero de perdas da fila

    public Simulation(){

        for(int i = 0; i < time.length; i++){
            time[i] = 0.0;
        }

        Event firstArrival = new Event(3, "F1", "arrival");
        scheduler.schedulerQueue.add(firstArrival);

        // iteracoes da simulacao
        while(numberOfRandoms > 0){
            Event nextEvent = scheduler.schedulerQueue.poll();
            System.out.println("Next task: "+nextEvent.operation+" -> "+nextEvent.targetQueue);

            if(nextEvent.operation.equals("arrival")){
                // chegada
                arrival(nextEvent);
            }
            else {
                // saida
                exit(nextEvent);
            }
        }

        double total = 0;
        // calculando tempo total
        for(int i = 0; i < time.length; i++){
            total += time[i];
        }

        // calculando porcentagens e mostrando resultados finais
        System.out.println("\n Final results: ");
        for(int i = 0; i < time.length; i++){
            double percentage = (time[i] / total) * 100;
            System.out.println(" "+i+": "+time[i]+ " = "+String.format("%.2f", percentage)+"%");
        }
        System.out.println("\n Total Time: "+total);
        System.out.println(" Total Losses: "+lost);
    }

    // simula a chegada
    private void arrival(Event event){
        time[F1.peopleOnQueue] += event.scheduledTime;
        
        if(F1.peopleOnQueue < F1.capacity){
            F1.peopleOnQueue += 1;
            if(F1.peopleOnQueue <= F1.servers){
                scheduler.schedulerQueue.add(new Event(generateTime(F1.minService,F1.maxService), "F1", "exit"));
            }
        }
        else{
            lost += 1;
        }
        scheduler.schedulerQueue.add(new Event(generateTime(F1.minArrival,F1.maxArrival), "F1", "arrival"));
    }

    // simula a saida
    private void exit(Event event){
        time[F1.peopleOnQueue] += event.scheduledTime;
        F1.peopleOnQueue -= 1;

        if(F1.peopleOnQueue >= F1.servers){
            scheduler.schedulerQueue.add(new Event(generateTime(F1.minService,F1.maxService), "F1", "exit"));
        }
    }

    // gera um numero aleatorio e coloca dentro do tempo maximo e minimo especificado
    private double generateTime(long minValue, long maxValue){
        double answer = (minValue - maxValue) * generator.getNext() + maxValue;
        numberOfRandoms -= 1;
        return answer;
    }
}