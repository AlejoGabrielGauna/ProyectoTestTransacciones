package testImplementacion;

import java.util.*;
import uade.edu.progra3.AlgoritmoDeBlockchain;
import uade.edu.progra3.model.Bloque;
import uade.edu.progra3.model.Transaccion;
import uade.edu.progra3.TransaccionUtils;

public class Blockchain {

    // Método principal blockchain
    public List<Bloque> blockchain() {
        List<Bloque> cadenaDeBloques = new ArrayList<>();
        Set<Transaccion> transaccionesYaAgregadas = new HashSet<>();
        List<Transaccion> transacciones = obtenerTransaccionesIniciales();

        while (!transacciones.isEmpty()) {
            Bloque nuevoBloque = construirBloque(transacciones, transaccionesYaAgregadas);
            cadenaDeBloques.add(nuevoBloque);
            actualizarTransaccionesRestantes(transacciones, nuevoBloque);
        }

        return cadenaDeBloques;
    }

    private List<Transaccion> obtenerTransaccionesIniciales() {
        return TransaccionUtils.crearTransaccionesConDependencias(5, 10, 2, 3);
    }

    private void actualizarTransaccionesRestantes(List<Transaccion> transacciones, Bloque nuevoBloque) {
        transacciones.removeAll(nuevoBloque.getTransacciones());
    }

    // Método para construir un bloque
    private Bloque construirBloque(List<Transaccion> transacciones, Set<Transaccion> transaccionesYaAgregadas) {
        Bloque bloque = new Bloque();
        int nroT = 0;

        for (int i = 0; i < transacciones.size(); i++) {
            Transaccion transaccionActual = transacciones.get(i);
            bloque.getTransacciones().add(transaccionActual);

            if (nroT <= 3) {
                if (satoshisValidation(bloque) && powValidation(bloque) && blockSize(bloque)) {
                    return new Bloque();
                }
                return bloque;
            }

            if (blockSize(bloque) && satoshisValidation(bloque) && powValidation(bloque) && validDependencia(transaccionesYaAgregadas, transaccionActual)) {
                transaccionesYaAgregadas.add(transaccionActual);
            } else {
                bloque.getTransacciones().remove(transaccionActual);
            }
            nroT++;
        }

        return bloque;
    }

    // Métodos auxiliares para validaciones y condiciones de bloques
    private boolean validDependencia(Set<Transaccion> transaccionesYaAgregadas, Transaccion transaccionActual) {
        Transaccion dependencia = transaccionActual.getDependencia();
        return dependencia == null || transaccionesYaAgregadas.contains(dependencia);
    }

    private boolean blockSize(Bloque bloque) {
        int sumaTamano = bloque.getTransacciones().stream().mapToInt(Transaccion::getTamanio).sum();
        return sumaTamano <= 1000;
    }

    private boolean satoshisValidation(Bloque bloque) {
        int sumaTotal = bloque.getTransacciones().stream().mapToInt(Transaccion::getValor).sum();
        return sumaTotal <= 100;
    }

    private boolean powValidation(Bloque bloque) {
        int sumaTotal = bloque.getTransacciones().stream().mapToInt(Transaccion::getValor).sum();
        return sumaTotal % 10 == 0;
    }
}

