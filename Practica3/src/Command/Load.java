package Command;

import java.io.FileNotFoundException;

import Excepciones.ArrayException;
import Paquete.Engine;

public class Load extends Command {
	
private String nombre;
	
	public Load() {
		this.nombre = "";
	}
	
	public Load(String nombre) {
		this.nombre = nombre;
	}
	
	/** Ejecuta el load
	 *  @param engine Engine
	 *  @return true si se ha podido ejecutar, false en caso contrario
	 * @throws FileNotFoundException 
	 * @throws ArrayException */
	public boolean execute(Engine engine) throws FileNotFoundException, ArrayException {
			
			return engine.executeLoad(this.nombre);
	}
		
	/** Pasa un string a Comando
	@param s el String a parsear
	@return null si el string no es un Load, Load en caso contrario*/
	public Command parse(String[] s) {
			
		if (s.length == 2 && s[0].equalsIgnoreCase("LOAD"))
			return new Load(s[1]);// Hay que comprobar que lo otro es un sring?
		else
			return null;
	}
	/** Muestra la ayuda asociada al comando Load*/
		
	public String textHelp() {
		
		return " LOAD: Carga un nuevo programa " + System.getProperty("line.separator");
	}

}
