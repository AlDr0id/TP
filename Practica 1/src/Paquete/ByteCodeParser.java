package Paquete;

/** Clase encargada de parsear un String y convertirlo en ByteCode */
public class ByteCodeParser {
	
	/** Recibe un String y lo pasa a ByteCode, si el String no se corresponde con ningun ByteCode, devuelve null 
	 @param s String a convertir
	 @return ByteCode */
	public static ByteCode parser (String s) {
		
		ENUM_BYTECODE a = null;
		s = s.trim();
		String[] w = s.split(" +");
		
		if (w.length == 1 ) { // Si tiene una celda, solo contiene ByteCode
				
					switch (w[0].toUpperCase()) {
					
					case "ADD": a = ENUM_BYTECODE.ADD;
						break;
					case "SUB": a = ENUM_BYTECODE.SUB;
						break;
					case "MUL": a = ENUM_BYTECODE.MUL;
						break;
					case "DIV": a = ENUM_BYTECODE.DIV;
						break;
					case "OUT": a = ENUM_BYTECODE.OUT;
						break;
					case "HALT": a = ENUM_BYTECODE.HALT;
						break;
					default :
						break;
					}
					
					return new ByteCode(a);
				}		
			else if (w.length == 2 ) { // Contiene bytecode e indice
				
					int c;
					switch(w[0].toUpperCase()) {
					
					case "PUSH": a = ENUM_BYTECODE.PUSH;
						break;
					case "LOAD": a = ENUM_BYTECODE.LOAD;
						break;
					case "STORE": a = ENUM_BYTECODE.STORE;
						break;
					default :
						break;
					}
					
					c = Integer.valueOf(w[1]);
					return new ByteCode(a, c);
				}		
			else {
				
				System.out.println("error");
				return null;
				}
	}
}
