package Command;

import Paquete.Engine;

/** Clase de la instruccion Help */
public class Help extends Command {

	/** Ejecuta la ayuda
	 *  @param engine Engine
	 *  @return true si se ha podido ejecutar, false en caso contrario*/
	public boolean execute(Engine engine) {
			
		return Engine.executeHelp();
	}
		
	/** Pasa un string a Comando
	@param s el String a parsear
	@return null si el string no es un Help, Help en caso contrario*/
	public Command parse(String[] s) {
			
		if (s.length == 1 && s[0].equalsIgnoreCase("HELP"))
			return new Help();
		else
			return null;
	}
	/** Muestra la ayuda asociada al comando help*/
		
	public String textHelp() {
	
		return " HELP: Muestra la ayuda " + System.getProperty("line.separator");
	}
}
