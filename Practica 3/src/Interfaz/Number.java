package Interfaz;

import ByteCode.ByteCode;
import ByteCode.Push;

public class Number implements Term {
	
	private String num;
		
	public Number() {
		this.num = null;
	}
	
	public Number (String num) {
		this.num = num;
	}
	
	@Override
	public Term parse(String term) {
		try{
			Integer.parseInt(term);
			return new Number(term);
		}
		catch(NumberFormatException nfe) {
			return null;
		}
			
	}
	
	@Override
	public ByteCode compile(Compiler compiler) {
		return new Push(Integer.parseInt(this.num));
	}
}
