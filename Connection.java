public class Connection{
    public final String target;
    public final double treshold;

    public Connection(String tgt, double trshld){
        this.target = tgt; // fila nas quais essa fila se conecta
        this.treshold = trshld; // chance de sair em vez de ocorrer passagem entre filas
    }
}