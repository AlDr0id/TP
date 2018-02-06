package CPU;

import Excepciones.StackException;

/** Clase que representa la pila de operandos de tipo entero que se van apilando */
public class OperandStack {
	/** Dimension maxima de la pila */
	public static final int MAXPILA = 200;	
	/** Array con el contenido de la pila */
	private int[] stack;
	/** Indice que indica la dimension actual de la pila */
	private int indice;		
	
	/** Crea una nueva Pila de dimension MAXPILA e indice 0  */
	public OperandStack() { 
		
		this.stack = new int [MAXPILA];
		this.indice = 0;
	}
	
	/** Elimina la cima 
	 * @throws StackException */
	public void pop() throws StackException { 
		
		if(this.noVacia()) { // Por si en algun momento se llama sin comprobarlo previamente
			this.indice--;
		}
		else 
			throw new StackException("Pila vacia ");
	}
	
	/** Devuelve true si la pila no esta vacia
		@return True si indice de la pila es mayor que 0 */
	public boolean noVacia() { 
		
		return (this.indice > 0);
	}
	
	/** Devuelve la cima de la pila
		@return Valor de la cima de la pila */
	public int getCima() { 
		
		return this.stack[this.indice-1];
	}
	
	/** Recibe un int y lo coloca en la cima
		@param a Entero a colocar
		@return true si se ha realizado con exito
	 * @throws StackException */
	public boolean setCima(int a) throws StackException {
		
		boolean ok = false;
		
		if(this.indice < MAXPILA) {
			this.incrementaPila();
			this.stack[this.indice-1] = a;
			ok = true;
		}
		else
			throw new StackException("Pila llena");
		return ok;
	}
	
	/** Devuelve la longitud del array Stack
		@return Dimension maxima actual del Stack */
	public int getLength() { 
		
		return this.stack.length;
	}
	
	/** Devuelve el indice (numero de posiciones ocupadas) del Stack
		@return Indice del Stack */
	public int getIndice() { 
		
		return this.indice;
	}
	
	/** Restaura la pila poniendo el indice a 0 **/
	public void reset() { 
		
		this.indice = 0;		
	}
	
	/** Incrementra en 1 el indice de la pila*/
	public void incrementaPila() {
		this.indice++;
	}
	
	/** Pasa un OperandStack a String
		@return String s con el contenido de la pila */
	public String toString() { 
		
		String s = "Pila:";
		
		if(this.indice > 0)
			for(int i=0; i < this.indice; i++) {
				s += " " + this.stack[i];
			}
		else
			s += " <vacia>";
		return s;
	}
		
}
