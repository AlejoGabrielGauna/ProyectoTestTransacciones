1. Valor máximo por bloque: La suma de los valores de las transacciones en un
bloque no debe superar 100 satoshis.
2. Máximo de transacciones por bloque: Un bloque puede contener como
máximo 3 transacciones.
3. Prueba de trabajo: La suma de los valores de las transacciones en un bloque
debe ser divisible por 10.
4. Tamaño máximo de bloque: El tamaño de cada bloque sigue siendo de 1 MB.



ALGORITMO Blockchain
    Entrada: CadenaDeBloques: Vector<Objeto>, 
             Block: Objeto, 
             Transacciones: Vector<Objeto> 
                        //entiendo que Tansacciones va a tener dentro:
                                //tamaño    
                                //valor
                                //dependencia
                                //firma 
    Salida: CadenaDeBloques

    CadenaDeBloques <- [] //agrege el inicio de la cadena de bloques
    TransaccionesYaAgregadas <- {} //agrege el inicio de las transacciones ya visitadas.


    Entero i <- 0
    MIENTRAS longitud(Transacciones) > 0 HACER //mientras que tenga transacciones
        CadenaDeBloques[i] <- ConstruirBloque(Transacciones, TransaccionesYaAgregadas)
        ActualizarTransaccionesRestantes(Transacciones, CadenaDeBloques[i])
        i <- i + 1
    FIN MIENTRAS

    DEVOLVER CadenaDeBloques
FIN Blockchain



ALGORITMO ConstruirBloque
        Entrada: 
            Transacciones : Vector<Objeto>
            ActBloque: Vector <Objeto>,
            ActTamaño: entero,
            nroT: entero,
            TransaccionesYaAgregadas: Conjunto
            
        Entero i <- 0
        cantT <- longitud(Transacciones) - 1
        MIENTRAS i ≤ cantT HACER
            ActBloque[i] += Transacciones[nroT] 
            SI nroT ≤ 3 ENTONCES // Máximo 3 Transacciones por bloque
                SI ValidacionSatoshis(ActBloque) Y ValidacionPow(ActBloque) Y TamañoBloque(ActBloque) ENTONCES//agrege TamañoBloque para que sume el tamaño de cada bloque y no supere a 1000
                    DEVOLVER copia de ActBloque
                FIN SI
                DEVOLVER ActBloque
            FIN SI
            
            SI TamañoBloque(ActBloque + Transacciones[i].tamaño) Y ValidacionSatoshis(ActBloque + Transacciones[i].valor) Y ValidacionPow(ActBloque + Transacciones[i].tamño) Y ValidDependencia(TransaccionesYaAgregadas, Transacciones[i]) ENTONCES
                TransaccionesYaAgregadas <- TransaccionesYaAgregadas + Transacciones[i]
                ConstruirBloque(Transacciones, ActBloque, ActTamaño += Transacciones[i].tamaño , nroT + 1, TransaccionesYaAgregadas)
            FIN SI
            
            ActBloque[i] <- null 
            i <- i + 1 
        FIN MIENTRAS

    DEVOLVER ActBloque
FIN ConstruirBloque




//AGREGE PARA VALIDAR QUE EL VALOR NO SUPERE A 100
ALGORITMO ValidacionSatoshis(bloque)
    sumaTotal <- 0 //agrege el sumador
    PARA CADA transaccion EN bloque
        sumaTotal <- sumaTotal + transaccion.valor //suma el total + el valor de la transaccion
    DEVOLVER sumaTotal ≤ 100//deberia retornar V o F
FIN ValidacionSatoshis

//AGREGE PARA VALIDAR QUE EN LA PRUEBA DE TRABAJO EL VALOR SEA DIVISIBLE POR 10
ALGORITMO ValidacionPow(bloque)
    sumaTotal <- 0//agrege el sumador
    PARA CADA transaccion EN bloque
        sumaTotal <- sumaTotal + transaccion.valor//suma el total + el valor de la transaccion
    DEVOLVER sumaTotal % 10 = 0//deberia retornar V o F 
FIN ValidacionPow


//AGREGE LA FUNCION PARA VALIDAR EL TAMAÑO DEL BLOQUE
ALGORITMO TamañoBloque(ActTamaño)
    sumaTotal <- 0
    PARA CADA transaccion EN bloque
        sumaTotal <- sumaTotal + transaccion.tamaño
    DEVOLVER sumaTotal ≤ 1000
FIN TamañoBloque



//SE AGREGO EL ALGORIMTO PARA LAS DEPENDENCIAS
ALGORITMO ValidDependencia(TransaccionesYaAgregadas, transaccion)
    PARA CADA dependencia EN transaccion.dependencias
        SI dependencia NO ESTA EN TransaccionesYaAgregadas
            DEVOLVER falso
        FIN SI
    FIN PARA
    DEVOLVER verdadero
FIN ValidDependencia


//SE AGREGO EL ALGORITMO QUE ACTUALIZA LAS TRANSACCIONES
ALGORITMO ActualizarTransaccionesRestantes(Transacciones, Block)
    PARA CADA transaccionAc EN Block
        transaccion.sacar(transaccionAc)
    FIN PARA
FIN ActualizarTransaccionesRestantes



--------------------------------------------------------------------------------------------------------



TEMPLATE - Trabajo_PracticoObligatorio_Grupo7.docx

Introducción

Una blockchain es una estructura de datos distribuida que garantiza la integridad de las transacciones mediante 
reglas criptográficas y consensos entre los nodos. 
El objetivo es diseñar una cadena de bloques compleja, donde no solo deben respetarse las reglas básicas de tamaño 
y validación de hash, sino también deben gestionarse transacciones que dependen entre sí, firmas múltiples, 
restricciones de prueba de trabajo, y prioridades en las transacciones. 
El objetivo es implementar un algoritmo que construya una blockchain válida, considerando todas las 
combinaciones posibles de bloques. Cada bloque debe cumplir con un conjunto más amplio de reglas, 
que incluyen validaciones adicionales como la dependencia entre transacciones, transacciones con 
firmas múltiples, priorización y restricciones de prueba de trabajo (hash).


Descripción del Problema

	Estrategia de Resolución: El algoritmo se resolverá utilizando técnicas de backtracking. 
	
	Por nivel: Una transacción
	
	Opciones a evaluar por nivel: Se evalúa si la transacción puede ser agregada al bloque.
	
	Poda: 
		1. Valor máximo por bloque: La suma de los valores de las transacciones en un bloque no debe superar 100 satoshis.
		2. Máximo de transacciones por bloque: Un bloque puede contener como máximo 3 transacciones.
		3. Prueba de trabajo: La suma de los valores de las transacciones en un bloque debe ser divisible por 10.
		4. Tamaño máximo de bloque: El tamaño de cada bloque sigue siendo de 1 MB.
		
		
		
COMPLEJIDADES: O(n2)

Blockhain: O(n3) => O(n . n2 ) = O(n3)
ConstruirBloque: O(n2)
ActualizarTransaccionesRestante: O(n2) => Se recorre cada transacción máximo n veces y en cada iteración elimina 
									      la transacción correspondiente. La operación de eliminar tiene complejidad 
									      de n en el peor caso por lo que la complejidad en total sería n2
ValidDependencia: O(n) => Es una iteración, en el peor caso debe recorrer todo el conjunto
ValidacionPow: O(n) => Es una iteración, en el peor caso debe recorrer todo el conjunto
ValidacionSatoshis: O(n) => Es una iteración, en el peor caso debe recorrer todo el conjunto
TamañoBloque: O(n) => Es una iteración, en el peor caso debe recorrer todo el conjunto			   
						


Conclusiones

Este proyecto fue desarrollado utilizando la técnica de Backtracking y presenta una estructura modular 
para construir una cadena de bloques, organizando el proceso en funciones específicas para validar 
transacciones, controlar dependencias y gestionar bloques. 
La recursividad en ConstruirBloque y las validaciones aseguran la integridad de la cadena, mientras 
que ActualizarTransaccionesRestantes mantiene las transacciones pendientes de manera ordenada para que 
estas no sean repetidas.





borrar despues---


ALGORITMO Blockchain

    Entrada: CadenaDeBloques: Vector<Objeto>, 
             Block: Objeto, 
             Transacciones: Vector<Objeto> 
                                //tamaño    
                                //valor
                                //dependencia
                                //firma 
    Salida: CadenaDeBloques <- []
    TransaccionesYaAgregadas <- {} 

    Entero i ← 0
    mientras longitud(Transacciones) > 0 hacer
    
    CadenaDeBloques[i] ← ConstruirBloque(Transacciones, TransaccionesYaAgregadas)
        ActualizarTransaccionesRestantes(Transacciones, CadenaDeBloques[i])
        i <- i + 1
    fin mientras

    retornar CadenaDeBloques
fin Blockchain
    


ALGORITMO ConstruirBloque
    Entrada: 
        Transacciones: Vector<Objeto>
        ActBloque: Vector <Objeto>,
        ActTamaño: entero,
        nroT: entero,
        TransaccionesYaAgregadas: Conjunto
	Salida: Bloque

    Entero i <- 0
    cantT <- longitud(Transacciones) – 1

    mientras i ≤ cantT hacer
        ActBloque[i] += Transacciones[nroT]                                                    
        si nroT ≤ 3 entonces // Máximo 3 transacciones por bloque
            si ValidacionSatoshis(ActBloque) y ValidacionPow(ActBloque) Y TamañoBloques(ActTamaño) entonces 
                retornar copia de ActBloque
            fin si
            retornar ActBloque
        fin si
            
		//SI TODO ESTO DA VERDADERO, ENTONCES MANDO A CONSTRUIR OTRO BLOQUE Y GUARDO EL QUE YA TENGO
        si TamañoBloque(ActBloque + Tansacciones[i].tamaño) Y  ValidacionSatoshis(ActBloque + Transacciones[i].valor) Y ValidacionPow(ActBloque + Transacciones[i].tamaño) y ValidDependencia(TransaccionesYaAgregadas, Transacciones[i]) entonces
            TransaccionesYaAgregadas <- TransaccionesYaAgregadas + Transacciones[i]
            ConstruirBloque(Transacciones, ActBloque, ActTamaño += Transacciones[i].tamaño , nroT + 1, TransaccionesYaAgregadas)
        fin si
            
		//EN CASO DE QUE DE TODO FALSO O ALGUNO DE FALSO, ME VOY  A LA SIGUIENTE TRANSACCION
        ActBloque[i] <- null 
        i <- i + 1 
        nroT <- nroT + 1
    fin mientras

    retornar ActBloque
    
fin ConstruirBloque
        

	
ALGORITMO TamañoBloque
Entrada: ActTamaño: Entero,
Salida: Booleano
    sumaTotal <- 0
    para cada transaccion en bloque
        sumaTotal <- sumaTotal + transaccion.tamaño
    retornar sumaTotal ≤ 1000
fin TamañoBloque


ALGORITMO ValidacionSatoshis
Entrada: Bloque: Objeto
Salida: Booleano

    sumaTotal <- 0 
    para cada transaccion en bloque
        sumaTotal<- sumaTotal + transaccion.valor 
    retornar sumaTotal ≤ 100//deberia retornar V o F
fin ValidacionSatoshis


ALGORITMO ValidacionPow
Entrada: Bloque: Objeto
Salida: Booleano

    sumaTotal <- 0 
    para cada transaccion en bloque
        sumaTotal ← sumaTotal + transaccion.valor 
    retornar sumaTotal % 10 = 0//deberia retornar V o F 
fin ValidacionPow



ALGORITMO ValidDependencia
Entrada: TransaccionesYaAgregadas: Vector <Objeto>, transaccion: Objeto
Salida: Booleano
	dependencia<-transaccion.dependencia
      PARA CADA transac EN TransaccionesYaAgregadas
                si transac == dependencia
					retornar true
       			FIN SI
   	  FIN PARA
    Retornar false

fin ValidDependencia



ALGORITMO ActualizarTransaccionesRestantes
Entrada: Transacciones: Vector <Objeto>, Block: Objeto
Salida: Transacciones: Vector <Objeto>,
	PARA CADA transaccionAc EN Block
      Transacciones.sacar(transaccionAc)
    FIN PARA
	Devolver Transacciones

FIN ActualizarTransaccionesRestantes



