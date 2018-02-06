package ByteCode;

import CPU.CPU;

/** Clase de la instruccion DIV */
public class Div extends Arithmetics {

	/** Ejecuta la division de la subcima y la cima de la pila
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @param op1 int
	 @param op2 int
	 @return true si la operacion se ha realizado con exito, false en caso contrario
	 */
	@Override
	public boolean executeAux(CPU cpu, int op1, int op2) {
			
				if(op1 != 0) {
										
					return cpu.push(op2 / op1);
				}
				else
					return false;			
			}

	/** Comprueba si lo introducido concuerda con este ByteCode, y si es asi crea uno
	 @param s String con lo introducido por el usuario ya separado
	 @return nuevo ByteCode si coincide, null en caso contrario
	 */
	@Override
	public ByteCode parse(String[] s) {
		if (s.length == 1 && s[0].equalsIgnoreCase("DIV"))
			return new Div();
		else
		return null;
	}
	
	/** Pasa un div a String
	@return String con div */
	@Override
	public String toString() {
		return "DIV ";
	}
}
