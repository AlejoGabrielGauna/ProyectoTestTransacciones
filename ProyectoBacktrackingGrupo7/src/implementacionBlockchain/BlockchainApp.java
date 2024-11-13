import uade.edu.progra3.AlgoritmoDeBlockchain;
import uade.edu.progra3.TransaccionUtils;
import uade.edu.progra3.model.Bloque;
import uade.edu.progra3.model.Transaccion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockchainApp {

    public static void main(String[] args) {
        // Crear la lista de transacciones utilizando TransaccionUtils
        TransaccionUtils transaccionUtils = new TransaccionUtils();
        List<Transaccion> transacciones = TransaccionUtils.crearTransaccionesMixtas(10, 3, 2, 5, 4); // Ejemplo de creación mixta
        transaccionUtils.firmarTransacciones(transacciones); // Firmar todas las transacciones

        // Ejecutar el algoritmo principal de construcción de la blockchain
        List<Bloque> cadenaDeBloques = blockchain(transacciones);
        
        // Imprimir la cadena de bloques resultante
        cadenaDeBloques.forEach(bloque -> {
            System.out.println("Bloque:");
            bloque.getTransacciones().forEach(transaccion -> {
                System.out.println("  Transacción: " + transaccion);
            });
        });
    }

    // Método principal Blockchain
    public static List<Bloque> blockchain(List<Transaccion> transacciones) {
        List<Bloque> cadenaDeBloques = new ArrayList<>();
        Set<Transaccion> transaccionesYaAgregadas = new HashSet<>();
        int i = 0;

        while (!transacciones.isEmpty()) {
            Bloque bloque = construirBloque(transacciones, transaccionesYaAgregadas);
            cadenaDeBloques.add(bloque);
            actualizarTransaccionesRestantes(transacciones, bloque);
            i++;
        }

        return cadenaDeBloques;
    }

    // Método ConstruirBloque
    public static Bloque construirBloque(List<Transaccion> transacciones, Set<Transaccion> transaccionesYaAgregadas) {
        Bloque bloque = new Bloque();
        List<Transaccion> transaccionesBloque = new ArrayList<>();
        int tamañoActual = 0;
        int nroT = 0;

        for (Transaccion transaccion : transacciones) {
            if (transaccionesBloque.size() < 3) { // Máximo de 3 transacciones por bloque
                transaccionesBloque.add(transaccion);

                if (validarSatoshis(transaccionesBloque) && validarPoW(transaccionesBloque) && validarTamañoBloque(transaccionesBloque)) {
                    bloque.setTransacciones(transaccionesBloque);
                    return bloque;
                }
            }

            if (validarTamañoBloque(transaccionesBloque) && validarSatoshis(transaccionesBloque) && validarPoW(transaccionesBloque) && validarDependencias(transaccionesYaAgregadas, transaccion)) {
                transaccionesYaAgregadas.add(transaccion);
                tamañoActual += transaccion.getTamanio();
                nroT++;
                return construirBloque(transacciones, transaccionesYaAgregadas);
            }

            transaccionesBloque.remove(transaccion);
        }

        bloque.setTransacciones(transaccionesBloque);
        return bloque;
    }

    // Validación de Satoshis
    private static boolean validarSatoshis(List<Transaccion> bloque) {
        int sumaTotal = bloque.stream().mapToInt(Transaccion::getValor).sum();
        return sumaTotal <= 100;
    }

    // Validación de Prueba de Trabajo (PoW)
    private static boolean validarPoW(List<Transaccion> bloque) {
        int sumaTotal = bloque.stream().mapToInt(Transaccion::getValor).sum();
        return sumaTotal % 10 == 0;
    }

    // Validación de Tamaño del Bloque
    private static boolean validarTamañoBloque(List<Transaccion> bloque) {
        int tamañoTotal = bloque.stream().mapToInt(Transaccion::getTamanio).sum();
        return tamañoTotal <= 1000;
    }

    // Validación de Dependencias
    private static boolean validarDependencias(Set<Transaccion> transaccionesYaAgregadas, Transaccion transaccion) {
        Transaccion dependencia = transaccion.getDependencia();
        if (dependencia != null && !transaccionesYaAgregadas.contains(dependencia)) {
            return false;
        }
        return true;
    }

    // Actualización de Transacciones Restantes
    private static void actualizarTransaccionesRestantes(List<Transaccion> transacciones, Bloque bloque) {
        transacciones.removeAll(bloque.getTransacciones());
    }
}
