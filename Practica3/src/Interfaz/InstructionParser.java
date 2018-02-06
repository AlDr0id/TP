package Interfaz;

import Excepciones.ArrayException;
import Excepciones.LexicalAnalysisException;

public class InstructionParser {
	
	/** Distintos comandos disponibles */
	private final static Instruction[] instrs = {
			new SimpleAssignment(), new CompoundAssignment(), new While(),
			new IfThen(), new Write(), new Return()};

	/** Pasa un string a Comando
	@param line el String a parsear
	@return null si el string no es un Comando, un comando en caso contrario
	 * @throws LexicalAnalysisException 
	 * @throws ArrayException */
	public static Instruction parse(String line, LexicalParser lex) throws LexicalAnalysisException, ArrayException {
		
		line = line.trim();
		String[] words = line.split(" +");
		boolean found = false;
		int i=0;
		Instruction c = null;
		
		while (i < instrs.length && !found) {
			
			c = instrs[i].lexParse(words, lex);
			if (c!=null) 
				found = true;
			else 
				i++;
		}
		return c;
	}
}
