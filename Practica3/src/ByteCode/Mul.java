package ByteCode;

import CPU.CPU;
import Excepciones.StackException;

/** Clase de la instruccion MUL */
public class Mul extends Arithmetics {

	/** Ejecuta la multiplicacion de la subcima y la cima de la pila
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @param op1 int
	 @param op2 int
	 @return true si la operacion se ha realizado con exito, false en caso contrario
	 * @throws StackException 
	 */
	@Override
	public boolean executeAux(CPU cpu, int op1, int op2) throws StackException {
		
		return cpu.push(op1 * op2);
	}

	/** Comprueba si lo introducido concuerda con este ByteCode, y si es asi crea uno
	 @param s String con lo introducido por el usuario ya separado
	 @return nuevo ByteCode si coincide, null en caso contrario
	 */
	@Override
	public ByteCode parse(String[] s) {
		if (s.length == 1 && s[0].equalsIgnoreCase("MUL"))
			return new Mul();
		else
			return null;
	}
	
	/** Pasa un mul a String
	@return String con mul */
	@Override
	public String toString() {
		return "MUL ";
	}
}
