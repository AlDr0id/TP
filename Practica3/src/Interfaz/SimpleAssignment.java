package Interfaz;

import ByteCode.Store;
import Excepciones.ArrayException;

public class SimpleAssignment implements Instruction {
	private String varName;
	private Term rhs;
	
	public SimpleAssignment(Term varName, Term rhs) {
		this.varName = varName.toString();
		this.rhs = rhs;
	}
	
	public SimpleAssignment() {
		this.varName = null;
		this.rhs = null;
	}
	
	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		
		if (words.length != 3) 
			return null;
		else {
			if(!words[1].equalsIgnoreCase("="))
				return null;
			else {
				Term var = new Variable();
				Term term = TermParser.parse(words[2]);
				
				var = var.parse(words[0]);
				
				lexParser.increaseProgramCounter();
				
				return new SimpleAssignment (var, term);
			}
		
		}
	}
	
	@Override
	public void compile(Compiler compiler) throws ArrayException{
		compiler.addByteCode(this.rhs.compile(compiler));
		compiler.addByteCode(new Store(compiler.getIndex(this.varName)));
	}
}
