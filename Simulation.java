import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Simulation {

    protected long numberOfRandoms = 100000; // qtdade nums aleatorios a serem gerados na simulacao
    protected long seed = 5; // semente a ser usada pelo gerador
    protected double treshold = 0.8; // chance de sair em vez de ocorrer passagem entre filas
    private Scheduler scheduler = new Scheduler(); // escalonador de eventos
    protected ArrayList<Queue> queues = new ArrayList<Queue>(); // lista de filas
    protected double totalTime = 0; // tempo total
    private Generator generator = new Generator(seed); // gerador de numeros aleatorios
    protected long lost[]; // numero de perdas da fila

    public Simulation() throws FileNotFoundException {

        readFromFile();

        // iteracoes da simulacao
        while(numberOfRandoms > 0){
            Event nextEvent = scheduler.schedulerQueue.poll();

            if(nextEvent.operation.equals("arrival")){
                // chegada
                arrival(nextEvent);
            }
            else if(nextEvent.operation.equals("exit")){
                // saida
                exit(nextEvent);
            }
            else{
                // passagem de fila
                passage(nextEvent);
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
        System.out.println(" Total Losses: ");
        for(int k = 0; k < lost.length; k++){
            System.out.println(" "+queues.get(k).name+": "+lost[k]);
        }
    }

    // simula a chegada
    private void arrival(Event event){
        updateTimeVectors(event.scheduledTime); // atualiza tempo
        int index = findIndex(event.targetQueue); // encontra fila alvo
        
        if(queues.get(index).peopleOnQueue < queues.get(index).capacity){ // menos gente na fila que a capacidade
            queues.get(index).peopleOnQueue += 1; // adicionada pessoa a fila
            if(queues.get(index).peopleOnQueue <= queues.get(index).servers){ // menos ou igual quantia de gente na fila que a quantia de servidores
                double chance = generator.getNext();
                if(chance < treshold || queues.size() < 2){
                    scheduler.schedulerQueue.add(new Event(generateTime(queues.get(index).minService,queues.get(index).maxService), queues.get(index).name, "exit"));
                } // agendei saida
                else{
                    scheduler.schedulerQueue.add(new Event(generateTime(queues.get(index).minService,queues.get(index).maxService), queues.get(index).name+":"+queues.get(generateIndex(index)).name, "passage"));
                } // agendei passagem pra uma fila aleatoria!
            }
        }
        else{
            lost[index] += 1;
        }
        scheduler.schedulerQueue.add(new Event(generateTime(queues.get(index).minArrival,queues.get(index).maxArrival), queues.get(index).name, "arrival"));
    }

    // simula a saida
    private void exit(Event event){
        updateTimeVectors(event.scheduledTime);
        int index = findIndex(event.targetQueue);
        queues.get(index).peopleOnQueue -= 1;

        if(queues.get(index).peopleOnQueue >= queues.get(index).servers){
            double chance = generator.getNext();
            if(chance < treshold || queues.size() < 2){
                scheduler.schedulerQueue.add(new Event(generateTime(queues.get(index).minService,queues.get(index).maxService), queues.get(index).name, "exit"));
            }
            else{
                scheduler.schedulerQueue.add(new Event(generateTime(queues.get(index).minService,queues.get(index).maxService), queues.get(index).name+":"+queues.get(generateIndex(index)).name, "passage"));
            }                
        }
    }

    // simula a passagem entre uma fila e outra
    private void passage(Event event){
        updateTimeVectors(event.scheduledTime);
        int originIndex = findIndex(event.targetQueue.split(":")[0]);
        int targetIndex = findIndex(event.targetQueue.split(":")[1]);
        queues.get(originIndex).peopleOnQueue -= 1;
        if(queues.get(originIndex).peopleOnQueue >= queues.get(originIndex).servers){
            double chance = generator.getNext();
            if(chance < treshold){
                scheduler.schedulerQueue.add(new Event(generateTime(queues.get(originIndex).minService,queues.get(originIndex).maxService), queues.get(originIndex).name, "exit"));
            }
            else{
                scheduler.schedulerQueue.add(new Event(generateTime(queues.get(originIndex).minService,queues.get(originIndex).maxService), queues.get(originIndex).name+":"+queues.get(generateIndex(originIndex)).name, "passage"));
            }                
        }
        if(queues.get(targetIndex).peopleOnQueue < queues.get(targetIndex).capacity){
            queues.get(targetIndex).peopleOnQueue += 1;
            if(queues.get(targetIndex).peopleOnQueue <= queues.get(targetIndex).servers){
                double chance = generator.getNext();
                if(chance < treshold){
                    scheduler.schedulerQueue.add(new Event(generateTime(queues.get(targetIndex).minService,queues.get(targetIndex).maxService), queues.get(targetIndex).name, "exit"));
                }
                else{
                    scheduler.schedulerQueue.add(new Event(generateTime(queues.get(targetIndex).minService,queues.get(targetIndex).maxService), queues.get(targetIndex).name+":"+queues.get(generateIndex(targetIndex)).name, "passage"));
                }                
            }
        }
        else{
            lost[targetIndex] += 1;
        }
    }

    // gera um numero aleatorio e coloca dentro do tempo maximo e minimo especificado
    private double generateTime(long minValue, long maxValue){
        double answer = ((minValue - maxValue) * generator.getNext() + maxValue) + totalTime;
        numberOfRandoms -= 1;
        return answer;
    }

    // gera um indice aleatorio pra escolha da fila alvo
    // recebe o indice atual para evitar passagens entre a mesma fila
    private int generateIndex(int index){
        int answer = index;
        while(answer == index){
            answer = (int)Math.round((0 - (queues.size()-1)) * generator.getNext() + (queues.size()-1));
        }
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
        lost = new long[queues.size()];
        in.close();
    }
}