package Interfaz;


import ByteCode.IfNeq;

public class NEqual extends Condition {
	
	public NEqual() {
		super();
	}
	public NEqual(Term t1, Term t2) {
		super(t1, t2, new IfNeq());
		
	}
	
	public Condition parse(String t1, String op, String t2, LexicalParser parser) {
		
		if(op.equalsIgnoreCase("!=")) {
			
			Term term1 = TermParser.parse(t1);
			Term term2 = TermParser.parse(t2);
			
			return new NEqual(term1, term2);
		}
		else return null;
		
	}
}
