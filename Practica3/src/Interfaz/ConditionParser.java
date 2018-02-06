package Interfaz;

public class ConditionParser {
	
	/** Distintos comandos disponibles */
	private final static Condition[] conds = {
			new Equal(), new NEqual(), new Less(), new LessE()};

	/** Pasa un string a Comando
	@param line el String a parsear
	@return null si el string no es un Comando, un comando en caso contrario*/
	public static Condition parse(String t1, String op, String t2, LexicalParser lex) {
		boolean found = false;
		int i=0;
		Condition c = null;
		
		while (i < conds.length && !found) {
			c = conds[i].parse(t1, op, t2, lex);
			
			if (c!=null)
				found = true;
			else 
				i++;
		}
		return c;
	}
}
