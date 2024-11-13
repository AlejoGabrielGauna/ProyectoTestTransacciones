package testImplementacion;

import java.util.List;
import uade.edu.progra3.model.Bloque;








public class Main {
    public static void main(String[] args) {
        // Crear una instancia de Blockchain
        Blockchain blockchain = new Blockchain();
        
        // Llamar al método blockchain() para generar la cadena de bloques
        List<Bloque> cadenaDeBloques = blockchain.blockchain();
        
        // Mostrar los bloques generados
        System.out.println("Cadena de Bloques Generada:");
        for (Bloque bloque : cadenaDeBloques) {
            System.out.println(bloque);
        }
        
        
        
        
        
        
    }
    
    
    
    
    
    
}
