import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Simulation {

    protected long numberOfRandoms = 100000; // qtdade nums aleatorios a serem gerados na simulacao
    protected long seed = 5; // semente a ser usada pelo gerador
    private Scheduler scheduler = new Scheduler(); // escalonador de eventos
    protected ArrayList<Queue> queues = new ArrayList<Queue>(); // lista de filas
    protected double totalTime = 0; // tempo total
    private Generator generator = new Generator(seed); // gerador de numeros aleatorios
    protected long lost = 0; // numero de perdas da fila

    public Simulation() throws FileNotFoundException {

        readFromFile();

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
        for(int j = 0; j < queues.size(); j++){
            System.out.println("\n Queue: "+queues.get(j).name);
            for(int i = 0; i < queues.get(j).time.length; i++){
                double percentage = (queues.get(j).time[i] / totalTime) * 100;
                System.out.println(" "+i+": "+queues.get(j).time[i]+ " = "+String.format("%.2f", percentage)+"%");
            }
        }
        System.out.println("\n Total Time: "+totalTime);
        System.out.println(" Total Losses: "+lost);
    }

    // simula a chegada
    private void arrival(Event event){
        updateTimeVectors(event.scheduledTime);
        int index = findIndex(event.targetQueue);
        
        if(queues.get(index).peopleOnQueue < queues.get(index).capacity){
            queues.get(index).peopleOnQueue += 1;
            if(queues.get(index).peopleOnQueue <= queues.get(index).servers){
                scheduler.schedulerQueue.add(new Event(generateTime(queues.get(index).minService,queues.get(index).maxService), queues.get(index).name, "exit"));
            }
        }
        else{
            lost += 1;
        }
        scheduler.schedulerQueue.add(new Event(generateTime(queues.get(index).minArrival,queues.get(index).maxArrival), queues.get(index).name, "arrival"));
    }

    // simula a saida
    private void exit(Event event){
        updateTimeVectors(event.scheduledTime);
        int index = findIndex(event.targetQueue);
        queues.get(index).peopleOnQueue -= 1;

        if(queues.get(index).peopleOnQueue >= queues.get(index).servers){
            scheduler.schedulerQueue.add(new Event(generateTime(queues.get(index).minService,queues.get(index).maxService), queues.get(index).name, "exit"));
        }
    }

    // gera um numero aleatorio e coloca dentro do tempo maximo e minimo especificado
    private double generateTime(long minValue, long maxValue){
        double answer = ((minValue - maxValue) * generator.getNext() + maxValue) + totalTime;
        numberOfRandoms -= 1;
        return answer;
    }

    // encontra o indice na lista da fila que possui determinado nome
    private int findIndex(String queueName){
        for(int i = 0; i < queues.size(); i++){
            if(queues.get(i).name.equals(queueName)){
                return i;
            }
        }
        return -1;
    }

    // atualiza os vetores de tempo das filas
    private void updateTimeVectors(double scheduledTime){
        for(int i = 0; i < queues.size(); i++){
            queues.get(i).time[queues.get(i).peopleOnQueue] += (scheduledTime - totalTime);
        }
        totalTime += (scheduledTime - totalTime);
    }

    // le o arquivo de entrada e popula os objetos
    private void readFromFile() throws FileNotFoundException {

        File file = new File("input.txt");
        Scanner in = new Scanner(file);
    
        // lendo a primeira chegada
        String[] firstA = in.nextLine().split(":");
        Event firstArrival = new Event(Double.parseDouble(firstA[2]), firstA[1], "arrival");
        scheduler.schedulerQueue.add(firstArrival);
 
        // lendo e criando as filas
        while(in.hasNextLine()){
            
            String n = in.nextLine().split(":")[1];
            int s = Integer.parseInt(in.nextLine().split(":")[1]);
            int c = Integer.parseInt(in.nextLine().split(":")[1]);
            int minA = Integer.parseInt(in.nextLine().split(":")[1]);
            int maxA = Integer.parseInt(in.nextLine().split(":")[1]);
            int minS = Integer.parseInt(in.nextLine().split(":")[1]);
            int maxS = Integer.parseInt(in.nextLine().split(":")[1]);
            queues.add(new Queue(n, s, c, minA, maxA, minS, maxS));
        }

        in.close();
    }
}