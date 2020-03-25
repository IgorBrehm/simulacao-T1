// Objeto que simula um evento a ser administrado pelo escalonador
public class Event{
    public final double scheduledTime; // tempo de simulacao agendado para o evento acontecer
    public final String targetQueue; // fila alvo do evento
    public final String operation; // tipo de operacao do evento, pode ser uma chegada ou saida

    public Event(double scheduledTime, String targetQueue, String operation){
        this.scheduledTime = scheduledTime;
        this.targetQueue = targetQueue;
        this.operation = operation;
    }
}