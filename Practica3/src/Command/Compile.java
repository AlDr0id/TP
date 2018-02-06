package Command;

import Excepciones.ArrayException;
import Excepciones.LexicalAnalysisException;
import Paquete.Engine;

public class Compile extends Command {
	
	/** Ejecuta Compile, lo cual para el engine
	 *  @param engine Engine
	 *  @return true si se ha podido ejecutar, false en caso contrario
	 * @throws ArrayException 
	 * @throws LexicalAnalysisException */
	public boolean execute(Engine engine) throws LexicalAnalysisException, ArrayException {
			
		return engine.compile();
	}
		
	/** Pasa un string a Comando
	@param s el String a parsear
	@return null si el string no es un Compile, Compile en caso contrario*/
	public Command parse(String[] s) {
			
		if (s.length == 1 && s[0].equalsIgnoreCase("COMPILE"))
				return new Compile();
		else 
			return null;
	}
		/** Muestra la ayuda asociada al comando quit*/
	public String textHelp() {
		
		return " COMPILE: Compila el programa " + System.getProperty("line.separator");
	}
}
