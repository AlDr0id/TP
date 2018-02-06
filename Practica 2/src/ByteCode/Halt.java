package ByteCode;

import CPU.CPU;

/** Clase de la instruccion HALT */
public class Halt extends ByteCode {

	/** Para la ejecucion del programa actual
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @return true
	 */
	@Override
	public boolean execute(CPU cpu) {
		
		cpu.halt();
		return true;
	}

	/** Comprueba si lo introducido concuerda con este ByteCode, y si es asi crea uno
	 @param s String con lo introducido por el usuario ya separado
	 @return nuevo ByteCode si coincide, null en caso contrario
	 */
	@Override
	public ByteCode parse(String[] s) {
		if (s.length == 1 && s[0].equalsIgnoreCase("HALT"))
			return new Halt();
		else
		return null;
	}
	/** Pasa un halt a String
	@return String con halt */
	public String toString() {
		return "HALT "; 
	}
}
