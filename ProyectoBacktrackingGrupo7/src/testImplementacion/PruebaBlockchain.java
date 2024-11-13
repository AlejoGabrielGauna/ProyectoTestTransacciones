package testImplementacion;


//importaciones de la libreria
import java.util.ArrayList;
import java.util.List;

import uade.edu.progra3.model.Bloque;
import uade.edu.progra3.model.Transaccion;

import uade.edu.progra3.TransaccionUtils;


public class PruebaBlockchain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//lista de transacciones 
		List<Transaccion> transacciones = TransaccionUtils.crearTransaccionesSimples(5, 10, 2, 100);
		
		
		//nuevo bloque, insertar transacciones
		Bloque bloque = new Bloque();
		for(Transaccion transaccion : transacciones) {
			bloque.getTransacciones().add(transaccion);
		}
		
		
		//impresiones 
		System.out.println("Bloque creado: ");
		System.out.println("Cantidad de transacciones en el bloque: " + bloque.getTransacciones().size());
		System.out.println("Tamaño total del bloque: " + bloque.getTamanioTotal());
		System.out.println("Valor total de transacciones en el bloque: "  + bloque.getValorTotal());
		
		//una validacion 
		int valorTotal = bloque.getTransacciones().stream().mapToInt(Transaccion::getValor).sum();
		System.out.println("Validacion del valor total de transacciones: " + (valorTotal == bloque.getValorTotal()));
		System.out.println("--Prueba o test completado, el resultado es desado....");
	}

}
