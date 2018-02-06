package Interfaz;


public class TermParser {
	/** Distintos comandos disponibles */
	private final static Term[] terms = {
			new Number(), new Variable()};

	/** Pasa un string a Comando
	@param line el String a parsear
	@return null si el string no es un Comando, un comando en caso contrario*/
	public static Term parse(String line) {
		
		line = line.trim();
		//String[] words = line.split(" +");
		boolean found = false;
		int i=0;
		Term c = null;
		
		while (i < terms.length && !found) {
			c = terms[i].parse(line);
			if (c!=null) 
				found = true;
			else
				i++;
		}
		return c;
	}
}
