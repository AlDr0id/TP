package Interfaz;

import ByteCode.GoTo;
import Excepciones.ArrayException;
import Excepciones.LexicalAnalysisException;
import Paquete.ParsedProgram;

public class While implements Instruction {

	private Condition condition;
	private ParsedProgram whileBody;
	
	public While() {
		this.condition = null;
		this.whileBody = null;
	}
	
	public While(Condition c, ParsedProgram pP) {
		this.condition = c;
		this.whileBody = pP;
	}
	
	public Instruction lexParse(String[] words, LexicalParser lexParser) throws LexicalAnalysisException, ArrayException {
		
		if(words.length != 4)
			return null;
		else {
			if (words[0].equalsIgnoreCase("WHILE") ) {
				
				Condition cd = ConditionParser.parse(words[1], words[2], words[3], lexParser);
				ParsedProgram wBody = new ParsedProgram();
				
				lexParser.increaseProgramCounter();
				lexParser.lexicalParser(wBody, "ENDWHILE");
				lexParser.increaseProgramCounter();
				
				return new While(cd, wBody);
			}
			else return null;
		}
	}
	
	public void compile(Compiler compiler) throws ArrayException {
		
		int a = compiler.getProgramCounter(); 
		this.condition.compile(compiler);
		compiler.compile(this.whileBody);
		this.condition.setJump(compiler.getProgramCounter()+1); // Si no pones +1 vuelves al goto
		compiler.addByteCode(new GoTo(a));
	}
}
