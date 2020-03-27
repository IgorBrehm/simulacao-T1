import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Simulation {

    protected static long numberOfRandoms = 100000; // qtdade nums aleatorios a serem gerados na simulacao
    protected static long seed = 0; // semente a ser usada pelo gerador
    private static Scheduler scheduler = new Scheduler(); // escalonador de eventos
    protected static ArrayList<Queue> queues = new ArrayList<Queue>(); // lista de filas
    private static Queue F1 = new Queue("F1", 2, 5, 2, 4, 3, 5); // fila 1 e seus parâmetros
    protected static double[] time = new double[F1.capacity + 1]; // vetor do tempo
    protected static double totalTime = 0; // tempo total
    private static Generator generator = new Generator(seed); // gerador de numeros aleatorios
    protected static long lost = 0; // numero de perdas da fila

    public Simulation() throws FileNotFoundException {

        for(int i = 0; i < time.length; i++){
            time[i] = 0.0;
        }

        File file = new File("input.txt");
        Scanner in = new Scanner(file);

        String[] firstA = in.nextLine().split(":");
        //Event firstArrival = new Event(Double.parseDouble(firstA[2]), firstA[1], "arrival");
        Event firstArrival = new Event(3.0, "F1", "arrival");
        scheduler.schedulerQueue.add(firstArrival);

        // iteracoes da simulacao
        while(numberOfRandoms > 0){
            Event nextEvent = scheduler.schedulerQueue.poll();

            if(nextEvent.operation.equals("arrival")){
                // chegada
                arrival(nextEvent);
            }
            else {
                // saida
                exit(nextEvent);
            }
        }

        // calculando porcentagens e mostrando resultados finais
        System.out.println("\n Final results: ");
        for(int i = 0; i < time.length; i++){
            double percentage = (time[i] / totalTime) * 100;
            System.out.println(" "+i+": "+time[i]+ " = "+String.format("%.2f", percentage)+"%");
        }
        System.out.println("\n Total Time: "+totalTime);
        System.out.println(" Total Losses: "+lost);
    }

    // simula a chegada
    private void arrival(Event event){
        time[F1.peopleOnQueue] += (event.scheduledTime - totalTime);
        totalTime += (event.scheduledTime - totalTime);
        
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
        time[F1.peopleOnQueue] += (event.scheduledTime - totalTime);
        totalTime += (event.scheduledTime - totalTime);
        F1.peopleOnQueue -= 1;

        if(F1.peopleOnQueue >= F1.servers){
            scheduler.schedulerQueue.add(new Event(generateTime(F1.minService,F1.maxService), "F1", "exit"));
        }
    }

    // gera um numero aleatorio e coloca dentro do tempo maximo e minimo especificado
    private double generateTime(long minValue, long maxValue){
        double answer = ((minValue - maxValue) * generator.getNext() + maxValue) + totalTime;
        numberOfRandoms -= 1;
        return answer;
    }
}