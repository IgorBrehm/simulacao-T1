import java.io.FileNotFoundException;

public class Simulator {
    public static void main(String args[]) throws FileNotFoundException {

        try{
            if(args[0].length() != 0 && args[1].length() != 0 && args[2].length() != 0){
   
                new Simulation(Long.parseLong(args[0]),Long.parseLong(args[1]),args[2]);
            }
            else{
   
               throw new Exception();
            }
        }
        catch(Exception exception){
   
            System.out.println("Insira Argumentos: java Simulator <qtdadeAleatorios> <semente> <arquivoEntrada>");
        }
    }
}