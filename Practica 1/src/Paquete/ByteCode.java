package Paquete;

/** Clase con las distintas instrucciones ByteCode que se pueden manejar */
public class ByteCode {
	
	/** Enumerado que indica el tipo de ByteCode */
	private ENUM_BYTECODE name; 
	
	/** Parametro necesario para push, load y store */
	private int param; 
	
	/** Crea un ByteCode sin parametro
	 * @param a ByteCode a crear*/
	public ByteCode (ENUM_BYTECODE a) { // Constructora para ByteCodes sin parametro
	
		this.name = a;
	}
	
	/** Crea un ByteCode con parametro
	 * @param a ByteCode a crear
	 * @param b Parametro del ByteCode*/
	public ByteCode (ENUM_BYTECODE a, int b) { // Constructora para ByteCodes con parametro
		
		this.name = a;
		this.param = b;
	}
	
	/** Devuelve el enumerado de un ByteCode
	 * @return ENUM_BYTECODE */
	public ENUM_BYTECODE getEnum() {
		
		return this.name;
	}
	
	/** Devuelve el parametro de un ByteCode
	 * @return param */
	public int getParam(){
		
		
		return this.param;
	}
	
	/** Pasa un ByteCode a String
	@return String con el ByteCode */
	public String toString() {
		
		
		String s = "";
		
		switch(this.name){
		
		case ADD: s = "ADD";
			break;
		case DIV: s = "DIV";
			break;
		case HALT: s = "HALT";
			break;
		case LOAD: s = "LOAD " + this.param;
			break;
		case MUL: s = "MUL";
			break;
		case OUT: s = "OUT";
			break;
		case PUSH: s = "PUSH " + this.param;
			break;
		case STORE: s = "STORE " + this.param;
			break;
		case SUB: s = "SUB";
			break;
		default:
			break;
		
		}
		
		return s;
	}
	

}