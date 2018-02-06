package ByteCode;

import CPU.CPU;

/** Clase de la instruccion OUT */
public class Out extends ByteCode {

	/** Muestra la cima de la pila
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @return true si la operacion se ha realizado con exito, false en caso contrario
	 */
	@Override
	public boolean execute(CPU cpu) {

		if( cpu.out()){
			cpu.increaseProgramCounter();
			return true;
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
		if (s.length == 1 && s[0].equalsIgnoreCase("OUT"))
			return new Out();
		else
		return null;
	}
	
	/** Pasa un out a String
	@return String con out */
	@Override
	public String toString() {
		return "OUT ";
	}
}