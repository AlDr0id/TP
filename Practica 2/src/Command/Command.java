package Command;

import Paquete.Engine;

/** Clase con los distintos comandos que puede utilizar un usuario */
abstract public class Command {
	
/** Ejecuta un comando
 *  @param engine Engine
 *  @return true si se ha podido ejecutar, false en caso contrario*/
	abstract public boolean execute (Engine engine);
	
	/** Muestra la ayuda asociada a un comando
	 * @return String con la ayuda respectiva al comando */
	abstract public String textHelp();
	
	/** Pasa un string a Comando
	@param s el String a parsear
	@return null si el string no es un Comando, un comando en caso contrario*/
	abstract public Command parse(String[ ] s);
	
}
