package Command;

import Paquete.Engine;

/** Clase de la instruccion Command */
public class Replace extends Command {

	private int pos;
	
	public Replace() {
		this.pos = 0;
	}
	
	public Replace(int pos) {
		this.pos = pos;
	}

	/** Ejecuta el comando replace
	 *  @param engine Engine
	 *  @return true si se ha podido reemplazar el bytecode,
	 *   false en caso contrario*/
public boolean execute(Engine engine) {
		
		return engine.executeReplace(this.pos);
	}
	
/** Pasa un string a Comando
@param s el String a parsear
@return null si el string no es un replace, replace en caso contrario*/
	public Command parse(String[] s) {
		
		boolean isInt = true;
		int pos = 0; // Si no lo inicializamos, se queja el return new Replace(pos)
		
		if (s.length == 2 && s[0].equalsIgnoreCase("REPLACE")) {
			try {
				 pos = Integer.parseInt(s[1]);
				 						
			 } catch (NumberFormatException nfe) {
				 isInt = false;
			 }
			
			if(isInt)
				return new Replace(pos);
			else
				return null;
		}
			
		
		else return null;
	}
	/** Muestra la ayuda asociada al comando replace*/
	public String textHelp() {
	
		return " REPLACE: Cambia la instruccion " + System.getProperty("line.separator");
	}
}
