package ByteCode;

import CPU.CPU;
import Excepciones.DivisionByZero;
import Excepciones.StackException;

/** Clase con las distintas instrucciones ByteCode de tipo aritmetico que se pueden manejar */
abstract public class Arithmetics extends ByteCode {

	/** Ejecuta la operacion correspondiente
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @return true si la operacion se ha realizado con exito, false en caso contrario
	 * @throws StackException 
	 * @throws DivisionByZero 
	 */
	public boolean execute(CPU cpu) throws DivisionByZero, StackException {
		boolean ok = false;
		if(cpu.getSizeStack() < 2)
			throw new StackException("No hay dos operandos en pila ");
		else {
			
			int op1 = cpu.pop();
			int op2 = cpu.pop();
			cpu.increaseProgramCounter();
			ok = executeAux(cpu, op1, op2);
		}
		return ok;
	}

	/** Ejecuta la operacion con la subcima y la cima de la pila
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @param op1 int
	 @param op2 int
	 @return true si la operacion se ha realizado con exito, false en caso contrario
	 * @throws StackException 
	 * @throws DivisionByZero 
	 */
	abstract public boolean executeAux(CPU cpu, int op1, int op2) throws DivisionByZero, StackException;
}