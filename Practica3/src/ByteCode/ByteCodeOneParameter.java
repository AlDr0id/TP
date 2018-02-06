package ByteCode;

import CPU.CPU;
import Excepciones.StackException;

/** Clase con las distintas instrucciones ByteCode con un parametro que se pueden manejar */
public abstract class ByteCodeOneParameter extends ByteCode {
	
	protected int param;
	
	/** Constructora principal */
	public ByteCodeOneParameter(){};
	
	/** Constructora con parametro
	 @param p int */
	public ByteCodeOneParameter(int p) {
		this.param = p;
	}
	
	/** Ejecuta la instruccion
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @return true si la operacion se ha realizado con exito, false en caso contrario
	 * @throws StackException 
	 */
	abstract public boolean execute(CPU cpu) throws StackException;
	
	/** Parsea el String que se le pasa por parametro
	 @param words lo introducido por el usuario separado por palabras
	 @return ByteCode si coincide, null en caso contrario */
	@Override
	public ByteCode parse(String[] words) {
		if (words.length!=2)
			return null;
		else
			return this.parseAux(words[0],words[1]);
	}
	
	/** Comprueba si lo introducido concuerda con este ByteCode, y si es asi crea uno
	 @param String string1 y String string2 con lo introducido por el usuario ya separado
	 @return nuevo ByteCode si coincide, null en caso contrario
	 */
	abstract protected ByteCode parseAux(String string1, String string2);
	
	/** Pasa un bytecode a String
	@return String s con el nombre del bytecode y el parametro*/
	public String toString(){
		return this.toStringAux() + " " + this.param;
	}
	
	/** Pasa un ByteCode a String
	@return String con ByteCode */
	abstract protected String toStringAux();
}