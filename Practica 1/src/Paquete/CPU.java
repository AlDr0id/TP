package Paquete;

/** Clase que representa la unidad de procesamiento de la maquina */
public class CPU {
	
	private OperandStack pila;
	private Memory memoria;
	private boolean terminado;
	
	/** Crea una CPU, la cual contiene una pila, una memoria y un booleano para saber si ha terminado */
	public CPU(){
		
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
		
		this.memoria = new Memory(); // Estamos creando una memoria nueva, aunque para redimensionar habria que hacerlo.
		this.pila.reset();
	}
	
	/** Recibe un ByteCode, si es correcto lo ejecuta y devuelve True, en caso contrario devuelve False
		@param instr Instruccion a ejecutar
		@return True si la instruccion se ha ejecutado con exito */ 
	public boolean execute(ByteCode instr) {	
		
		boolean ok = false;
		
		switch (instr.getEnum()) {
		
		case ADD: ok = this.suma();
			break;
		case DIV: ok = this.div();
			break;
		case HALT: this.terminado = true;
					ok = true;
			break;
		case LOAD: if(this.memoria.ocupado(instr.getParam())) {
						ok = this.push(this.memoria.read(instr.getParam()));
					}
			break;
		case MUL: ok = this.mul();
			break;
		case OUT: if (this.pila.noVacia()) {
			System.out.println("Cima de la pila: " + this.pila.getCima() + System.getProperty("line.separator")+ System.getProperty("line.separator"));
			ok = true;
		}
			break;
		case PUSH: ok = this.push(instr.getParam());
			break;
		case STORE: if (this.pila.noVacia() && this.memoria.write(instr.getParam(),this.pila.getCima())) {
						// Si la pila no esta vacia, escribe en memoria[instr.getParam] la cima, y la borra de pila
						this.pila.pop();
						ok = true;
					}
			break;
		case SUB: ok = this.sub();
			break;
		default:
			break;
		
		}
		
		return ok;
	}
	
	/** Si hay al menos dos elementos en la pila realiza la suma de los dos primeros y devuelve True.
	En caso contrario devuelve False
	@return True si se ha realizado la suma con exito */
	private boolean suma () { 
		
		if(this.pila.getIndice() < 2)
			return false;
		else {
			
			int op1 = this.pila.getCima();
			this.pila.pop();
			int op2 = this.pila.getCima();
			this.pila.pop();
			this.pila.setCima(op1+op2);
			return true;
		}
	}
	
	/** Si hay al menos dos elementos en la pila y se puede realizar la division del segundo
	entre el primero, se hace y se devuelve True. En caso contrario devuelve False
	@return True si se ha realizado la division con exito */
	private boolean div() {
		
		if(this.pila.getIndice() < 2)
			return false;
		else {
			
			int op1 = this.pila.getCima();
			if(op1 != 0) {
				this.pila.pop();
				int op2 = this.pila.getCima();
				this.pila.pop();
				this.pila.setCima(op2/op1);
				return true;
			}
			else
				return false;			
		}
	}
	
	/** Si hay al menos dos elementos en la pila realiza la multiplicacion de los dos primeros
	y devuelve True. En caso contrario devuelve False
	@return True si se ha realizado la multiplicacion con exito */
	private boolean mul() {
		
		if(this.pila.getIndice() < 2)
			return false;
		else {
			
			int op1 = this.pila.getCima();
			this.pila.pop();
			int op2 = this.pila.getCima();
			this.pila.pop();
			this.pila.setCima(op1*op2);
			return true;
		}
	}
	
	/** Si hay al menos dos elementos en la pila realiza la resta del segundo menos el primer elemento
		y devuelve True. En caso contrario devuelve False
		@return True si se ha realizado la resta con exito */
	private boolean sub() {
		
		if(this.pila.getIndice() < 2)
			return false;
		else {
			
			int op1 = this.pila.getCima();
			this.pila.pop();
			int op2 = this.pila.getCima();
			this.pila.pop();
			this.pila.setCima(op2-op1);
			return true;
		}
	}
	
	/** Recibe un int n y, si hay espacio en la pila, lo coloca en la cima
		@param n Valor a introducir en la pila
		@return True si se ha podido introducir el valor */
	private boolean push(int n) {	
		
		boolean ok = false;
		if (this.pila.getLength() > this.pila.getIndice()) {
			this.pila.setCima(n);
			ok = true;
		}
		
		return ok;
	}
	
	/**Se pasa un objeto CPU a String
		@return String con el estado de la CPU */
	public String toString() {
		return("Estado de la CPU:" + System.getProperty("line.separator") + "\t" + this.memoria + System.getProperty("line.separator") + "\t" + this.pila); 
	 }
}