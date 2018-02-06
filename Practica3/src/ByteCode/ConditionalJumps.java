package ByteCode;

import CPU.CPU;
import Excepciones.StackException;

/** Clase con las instrucciones de saltos condicionales */
abstract public class ConditionalJumps extends ByteCodeOneParameter {

	/** Constructora principal */
	public ConditionalJumps(){}
	
	/** Constructora con parametro
	 @param j int */
	public ConditionalJumps(int j){ super(j); }
	
	/** Si no se cumple la comparacion, salta a la posicion del programa indicada 
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @return true si la operacion se ha realizado con exito, false en caso contrario
	 * @throws StackException 
	 */
	@Override
	public boolean execute(CPU cpu) throws StackException {
		boolean ok = false;
		if (cpu.getSizeStack()>=2 && this.param < cpu.getIndiceBcProgram()) {
			
			int n1 = cpu.pop();
			int n2 = cpu.pop();
			
			if (compare(n1,n2))		
				cpu.increaseProgramCounter();	
			else			
				cpu.setProgramCounter(this.param);
			ok = true;
	}
	else {
		throw new StackException("No hay dos operandos en pila ");
	}
		return ok;
	}
	
	/** Ejecuta la comparacion de la subcima de la pila y la cima en base a una condicion
	 @param CPU cpu, que contiene toda la unidad de procesamiento de la maquina
	 @return true si se cumple, false en caso contrario
	 */
	abstract protected boolean compare(int n1, int n2);
	
	/** Comprueba si lo introducido concuerda con este ByteCode, y si es asi crea uno
	 @param String string1 y String string2 con lo introducido por el usuario ya separado
	 @return nuevo ByteCode si coincide, null en caso contrario
	 */
	abstract protected ByteCode parseAux(String string1, String string2);
	
	/** Pasa un ByteCode a String
	@return String con ByteCode */
	abstract protected String toStringAux();

	public void setJump(int programCounter) {
		this.param = programCounter;
	}
}