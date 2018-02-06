package ByteCode;


/** Clase encargada de parsear un String y convertirlo en ByteCode */
public class ByteCodeParser {
	
	/** ByteCodes disponibles */
	private final static ByteCode[] byteCodes = {
			new Add(), new Div(), new GoTo(), new Halt(), new IfEq(), new IfLe(), new IfLeq(), new IfNeq(),
			new Load(), new Mul(), new Out(), new Push(), new Store(), new Sub() };
	
	/** Parsea un String
	 @param line String con lo introducido por el usuario
	 @return String con el Bytecode si ha tenido exito, null en caso contrario
	 */
	public static ByteCode parse(String line) {
		
		line = line.trim();
		String[] words = line.split(" +");
		boolean found = false;
		int i=0;
		ByteCode c = null;
		
		while (i < byteCodes.length && !found) {
			c = byteCodes[i].parse(words);
			if (c!=null)
				found=true;
			else 
				i++;
		}
		return c;
	}
}