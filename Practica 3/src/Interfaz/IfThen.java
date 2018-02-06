package Interfaz;

import Excepciones.ArrayException;
import Excepciones.LexicalAnalysisException;
import Paquete.ParsedProgram;

public class IfThen implements Instruction {

	private Condition condition;
	private ParsedProgram ifBody;
	
	public IfThen() {
		
	}
	
	public IfThen(Condition condition, ParsedProgram ifBody){
		this.condition = condition;
		this.ifBody = ifBody;
	}
	
	public Instruction lexParse(String[] words, LexicalParser lexParser) throws LexicalAnalysisException, ArrayException {
		
		if (words.length != 4)
			return null;
		else {
			if (words[0].equalsIgnoreCase("IF")) {
				
				Condition cd = ConditionParser.parse(words[1], words[2], words[3], lexParser);
				ParsedProgram wBody = new ParsedProgram();
				
				lexParser.increaseProgramCounter();
				lexParser.lexicalParser(wBody, "ENDIF");
				lexParser.increaseProgramCounter();
				
				return new IfThen(cd, wBody);
			}
			else return null;
		}
	}
	
	public void compile(Compiler compiler) throws ArrayException{
		
		this.condition.compile(compiler);
		//int a = compiler.getProgramCounter() - 1;
		compiler.compile(this.ifBody);
		this.condition.setJump(compiler.getProgramCounter());
				
	}

}
