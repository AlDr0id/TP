package Interfaz;

import ByteCode.Halt;
import Excepciones.ArrayException;

public class Return implements Instruction {

	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		if (words.length != 1)
			return null;
		else
			if(words[0].equalsIgnoreCase("RETURN")) {
				lexParser.increaseProgramCounter();
				return new Return();
			}
			else
				return null;
	}

	@Override
	public void compile(Compiler compiler) throws ArrayException {
		compiler.addByteCode(new Halt());
		
	}

}
