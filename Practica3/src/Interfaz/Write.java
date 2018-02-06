package Interfaz;

import ByteCode.Load;
import ByteCode.Out;
import Excepciones.ArrayException;


public class Write implements Instruction {
	private String varName;
	
	public Write () {
		this.varName = null;
	}
	
	public Write (String varName) {
		this.varName = varName;
	}
	
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		
		if (words.length != 2 || !words[0].equalsIgnoreCase("WRITE"))
			return null;
		else {
			char name = words[1].charAt(0);
			
			if( !('a' <= name && name <= 'z'))
				return null;
			else {
				lexParser.increaseProgramCounter();
				return new Write(words[1]);
			}
		}
	}
	
	public void compile(Compiler compiler) throws ArrayException {
		int index = compiler.getIndex(this.varName);
		compiler.addByteCode(new Load(index));
		compiler.addByteCode(new Out());
	}

}
