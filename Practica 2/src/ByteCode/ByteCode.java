package ByteCode;

import CPU.CPU;

/** Clase con las distintas instrucciones ByteCode que se pueden manejar */
abstract public class ByteCode {
	
	/** Ejecuta la operacion correspondiente
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @return true si la operacion se ha realizado con exito, false en caso contrario
	 */
	abstract public boolean execute(CPU cpu);
	
	/** Comprueba si lo introducido concuerda con el ByteCode, y si es asi crea uno de ese tipo
	 @param s String con lo introducido por el usuario parseado
	 @return nuevo ByteCode si coincide, null en caso contrario
	 */
	abstract public ByteCode parse(String[] s);
	
	/** Pasa un ByteCode a String
	@return String con el nombre del ByteCode */
	abstract public String toString();
}