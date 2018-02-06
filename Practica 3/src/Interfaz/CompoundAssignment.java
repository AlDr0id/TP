package Interfaz;

import ByteCode.Add;
import ByteCode.Div;
import ByteCode.Mul;
import ByteCode.Store;
import ByteCode.Sub;
import Excepciones.ArrayException;

public class CompoundAssignment implements Instruction {
	
	private String varName;
	private String operator;
	private Term t1;
	private Term t2;
	
	public CompoundAssignment (String varName, String operator, Term t1, Term t2) {
		this.varName = varName;
		this.operator = operator;
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public CompoundAssignment(){
		this.varName = null;
		this.operator = null;
		this.t1 = null;
		this.t2 = null;
	}
	
	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser){
		if (words.length != 5) 
			return null;
		else {
			if(!words[1].equalsIgnoreCase("="))
				return null;
			else
			{
				if (words[3].equalsIgnoreCase("*") || words[3].equalsIgnoreCase("/")
						|| words[3].equalsIgnoreCase("+") || words[3].equalsIgnoreCase("-")) {
					
					Term var = new Variable();
					Term term = TermParser.parse(words[2]);
					Term term2 = TermParser.parse(words[4]);
					
					var = var.parse(words[0]);
					
					lexParser.increaseProgramCounter();
					
					return new CompoundAssignment (var.toString(), words [3], term, term2 );
				}
				else
					return null;
				
			}
		}
	}
	
	@Override
	public void compile(Compiler compiler) throws ArrayException {
		
		compiler.addByteCode(this.t1.compile(compiler));
		compiler.addByteCode(this.t2.compile(compiler));
		
		switch (this.operator) {
			case "+": compiler.addByteCode(new Add()); break;
			case "-": compiler.addByteCode(new Sub()); break;
			case "*": compiler.addByteCode(new Mul()); break;
			case "/": compiler.addByteCode(new Div()); break;
		}
		compiler.addByteCode(new Store(compiler.getIndex(this.varName)));
	}
	
}
