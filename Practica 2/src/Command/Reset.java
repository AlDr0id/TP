package Command;

import Paquete.Engine;

/** Clase de la instruccion Reset */
public class Reset extends Command {

	/** Ejecuta el reset
	 *  @param engine Engine
	 *  @return true si se ha podido ejecutar, false en caso contrario*/
	public boolean execute(Engine engine) {
		
		return engine.executeReset();
	}
	
	/** Pasa un string a Comando
	@param s el string a parsear
	@return null si el string no es un Reset, Reset en caso contrario*/
	public Command parse(String[] s) {
		
		if (s.length == 1 && s[0].equalsIgnoreCase("RESET"))
			return new Reset();
		else return null;
	}
	
	/** Muestra la ayuda asociada al comando reset*/
	public String textHelp() {
	
		return " RESET: Vacia el programa actual " + System.getProperty("line.separator");
	}
}
