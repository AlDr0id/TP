package Interfaz;

import ByteCode.ByteCode;
import ByteCode.Load;

public class Variable implements Term {
	
	private String varName;
	
	public Variable() {
		this.varName = null;
	}
	
	public Variable (String varName) {
		this.varName = varName;
	}
	
	@Override
	public Term parse(String term) {
		if (term.length() != 1) 
			return null;
		else {
			char name = term.charAt(0);
			
			if ('a' <= name && name <= 'z') 
				return new Variable(term);
			else
				return null;
		}
	}
	
	@Override
	public ByteCode compile(Compiler compiler) {
		int index = compiler.getIndex(this.varName);
		return new Load(index);
	}
	
	public String toString() {
		return this.varName;
	}

}
