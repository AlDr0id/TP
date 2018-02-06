package Command;

import Paquete.Engine;

/** Clase de la instruccion Quit */
public class Quit extends Command{

	/** Ejecuta quit, lo cual para el engine
	 *  @param engine Engine
	 *  @return true si se ha podido ejecutar, false en caso contrario*/
	public boolean execute(Engine engine) {
			
			return engine.executeEnd();
		}
		
	/** Pasa un string a Comando
	@param s el String a parsear
	@return null si el string no es un Quit, Quit en caso contrario*/
		public Command parse(String[] s) {
			
			if (s.length == 1 && s[0].equalsIgnoreCase("QUIT"))
				return new Quit();
			else return null;
		}
		/** Muestra la ayuda asociada al comando quit*/
		public String textHelp() {
		
			return " QUIT: Fin del programa " + System.getProperty("line.separator");
		}
	
}
