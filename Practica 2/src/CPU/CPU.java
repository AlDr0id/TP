package CPU;

import Paquete.ByteCodeProgram;
import ByteCode.ByteCode;

/** Clase que representa la unidad de procesamiento de la maquina */
public class CPU {
	
	private OperandStack pila;
	private Memory memoria;
	private boolean terminado;
	private int programCounter = 0;
	private ByteCodeProgram bcProgram = new ByteCodeProgram();
	
	/** Crea una CPU, la cual contiene una pila, una memoria y un booleano para saber si ha terminado */
	public CPU() {
		
		this.pila = new OperandStack();
		this.memoria= new Memory();
		this.terminado = false;
	}
	
	 /** Devuelve true si se ha terminado el programa
		@return True si el programa termina */
	public boolean haTerminado() {
		return this.terminado;
	}
	
	/** Resetea la CPU */
	public void reset() { 
		
		this.memoria = new Memory();
		this.pila.reset();
		this.programCounter = 0;
	}
	/** Obtiene la cima y decrementra el indice
	    @return la cima de la pila*/
	public int pop() {
		
		int cima = this.pila.getCima();
		this.pila.pop();
		return cima;
	}
	/** Establece como cima el contenido de la posicion param
	    @param param posicion de la que se quiere leer
	    @return true si se ha podido hacer, en otro caso false */
	public boolean read(int param) {
	
		return this.pila.setCima(this.memoria.read(param));
		
	}
	/** Escribe en la posición de memoria param el valor value
	    @param param posicion en la que escribir
	    @param value valor a escribir
	    @return true si se ha podido escribir, false en otro caso*/
	public boolean write(int param, int value) {
		if (this.memoria.write(param, value)){
			return true;
		}
		else{
			this.pila.incrementaPila();
			return false;
		}
		}
	
	/** Establece el contador de programa a jump
	    @param jump valor que tomara el contador de programa */
	public void setProgramCounter(int jump) {
		this.programCounter = jump;				
	}
	/** Incrementra en 1 el contador de programa */
	public void increaseProgramCounter() {
		this.programCounter++;
	}
	/** Mientras que haya ByteCodes en el ByteCode program y estos sean 
	    correctos, los ejecuta
	    @return true si se han podido ejecutar todos, false en otro caso*/
	public boolean run() { // Si no hay error devuelve false
		this.programCounter = 0;
		boolean error = false;
		this.terminado = false;
		while (this.programCounter < bcProgram.getIndice() && !error && !this.terminado)
		{
		ByteCode bc = bcProgram.leer(this.programCounter);
		System.out.println(System.getProperty("line.separator") + "El estado de la maquina tras ejecutar el bytecode "
				+ bc + " es:" + System.getProperty("line.separator") + System.getProperty("line.separator"));
		if (!bc.execute(this))
			error = true;
		else {
			System.out.println(this.toString());
		}
		}
		
		return !error;
	}
	
	/** Recibe un int n y, si hay espacio en la pila, lo coloca en la cima
		@param n Valor a introducir en la pila
		@return True si se ha podido introducir el valor */
	public boolean push(int n) {	
		
		boolean ok = false;
		if (this.pila.getLength() > this.pila.getIndice()) {
			this.pila.setCima(n);
			ok = true;
		}
		
		return ok;
	}
	/** Termina la ejecuccion de la cpu */
	public void halt() {
		
		this.terminado = true;

	}
	/** Muestra por pantalla la cima de la pila
	   @return true si la pila no esta vacia, false en caso contrario */
	public boolean out() {
		
		boolean ok = false;
		
		if (this.pila.noVacia()) {
			System.out.println("Cima de la pila: " + this.pila.getCima() + System.getProperty("line.separator")+ System.getProperty("line.separator"));
			ok = true;
		}
		
		return ok;
	}
	/** Devuelve el indice de la pila
	    @return el indice de la pila */
	public int getSizeStack() {
		
		return pila.getIndice();
	}
	/** Obtiene la cima de la pila
	    @return la cima de la pila */
	public int getCimaStack() {
		
		return pila.getCima();
	}
	/** Obtiene el indice del ByteCodeProgram
	    @return el indice del ByteCodeProgram*/
	public int getIndiceBcProgram(){
		return this.bcProgram.getIndice();
	}
	/** Obtiene el programCounter
	    @return programCounter*/
	public int getProgramCounter(){
		return this.programCounter;
	}
	/** Obtiene el ByteCodeProgram
	    @return el ByteCodeProgram*/
	public ByteCodeProgram getByteCodeProgram(){
		return this.bcProgram;
	}
	/**Se pasa un objeto CPU a String
		@return String con el estado de la CPU */
	public String toString() {
		return("Estado de la CPU:" + System.getProperty("line.separator") + "\t" + this.memoria + System.getProperty("line.separator") + "\t" + this.pila); 
	 }

}