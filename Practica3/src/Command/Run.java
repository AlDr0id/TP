package Command;

import Excepciones.DivisionByZero;
import Excepciones.StackException;
import Paquete.Engine;

/** Clase de la instruccion Run */
public class Run extends Command {

	/** Comienza la ejecucción de byteCodes almacenados en el ByteCodeProgram
	 *  @param engine Engine
	 *  @return true si se ha podido ejecutar todo sin problemas,
	 *   false en caso contrario
	 * @throws StackException 
	 * @throws DivisionByZero */
	public boolean execute(Engine engine) throws StackException, DivisionByZero {
		
		return engine.executeRun();
	}
	
	/** Pasa un string a Comando
	@param s el string a parsear
	@return null si el string no es un Run, Run en caso contrario*/
	public Command parse(String[] s) {
		
		if (s.length == 1 && s[0].equalsIgnoreCase("RUN"))
			return new Run();
		else 
			return null;
	}
	
	/** Muestra la ayuda asociada al comando run*/
	public String textHelp() {
	
		return " RUN: Ejecuta el programa actual " + System.getProperty("line.separator");
	}
}
