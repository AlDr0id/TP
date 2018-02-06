package Interfaz;

import ByteCode.IfLe;

public class Less extends Condition {

	public Less (){
		super();
	}
	public Less(Term t1, Term t2){
		super(t1, t2, new IfLe());
//		this.t1  = t1;
//		this.t2 = t2;
//		this.condition = new IfLe();
		
	}
	
	public Condition parse(String t1, String op, String t2, LexicalParser parser) {
		
		if(op.equalsIgnoreCase("<")) {
			
			Term term1 = TermParser.parse(t1);
			Term term2 = TermParser.parse(t2);
			
			return new Less(term1, term2);
		}
		else return null;
		
	}
}
