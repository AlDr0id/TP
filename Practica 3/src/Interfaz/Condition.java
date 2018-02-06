package Interfaz;

import ByteCode.ConditionalJumps;
import Excepciones.ArrayException;

public abstract class Condition {
private Term t1;
private Term t2;
private ConditionalJumps condition; //para la compilación

	public Condition () {
		
	}
	
	public Condition (Term t1, Term t2, ConditionalJumps c) {
	
		this.t1 = t1;
		this.t2 = t2;
		this.condition = c;
	}
	
	abstract public Condition parse(String t1, String op, String t2, LexicalParser parser);
	
	public void compile(Compiler compiler) throws ArrayException {
		//darle valor al atributo condition y meterlo en el bcProgram de compiler
		
		compiler.addByteCode(this.t1.compile(compiler));
		compiler.addByteCode(this.t2.compile(compiler));
		compiler.addByteCode(this.condition);
		
	}
	
	public void setJump(int programCounter) {
		
		this.condition.setJump(programCounter);
	}
	
}
