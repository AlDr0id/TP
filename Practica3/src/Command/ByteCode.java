package Command;

import Excepciones.ArrayException;
import Paquete.Engine;

/** Clase de la instruccion ByteCode */
public class ByteCode extends Command {


	@Override
	/** Lee un conjunto de bytecodes
	 * @param Engine engine
	 * @return true si se ha podido realizar la lectura y es correcta,
	 * false en caso contrario*/
	public boolean execute(Engine engine) throws ArrayException {
		return engine.readByteCodeProgram();
	}
	
	@Override /** Pasa un string a Comando
	@param string s: el string a parsear
	@return null si el string no es un ByteCode, ByteCode en caso contrario*/
	public Command parse(String[] s) {
		if (s.length!=1 || !s[0].equalsIgnoreCase("BYTECODE"))
			return null;
		else
			return new ByteCode();
	}
	
	/** Muestra la ayuda asociada al comando bytecode*/
	@Override
	public String textHelp() {
		return " BYTECODE: Permite introducir un programa " +
		System.getProperty("line.separator");
	}
	
	/** Pasa un ByteCode a String
	@return String con bytecode */
	public String toString(){
		return "BYTECODE";
	}
	
}