package Interfaz;

import Excepciones.ArrayException;
import Excepciones.LexicalAnalysisException;

public interface Instruction {
	
	Instruction lexParse(String[] words, LexicalParser lexParser) throws LexicalAnalysisException, ArrayException;
	void compile(Compiler compiler) throws ArrayException;
}
