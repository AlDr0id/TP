package ByteCode;

/** Clase de la instruccion IFNEQ */
public class IfNeq extends ConditionalJumps {

	/** Constructora principal */ 
	public IfNeq(){
		super();
	}
	
	/** Constructora con parametro 
	 @param p int */ 
	public IfNeq(int p){
		super(p);
	}
	
	/** Ejecuta la comparacion de si la subcima de la pila es distinta de la cima
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @return true si se cumple, false en caso contrario
	 */
	@Override
	protected boolean compare(int n1, int n2) {
		// IFNEQ chequea que sc es distinto de c
		// subcima (sc) es n2, y cima es n1
		return n2 != n1;
	}

	/** Comprueba si lo introducido concuerda con este ByteCode, y si es asi crea uno
	 @param String string1 y String string2 con lo introducido por el usuario ya separado
	 @return nuevo ByteCode si coincide, null en caso contrario
	 */
	@Override
	protected ByteCode parseAux(String string1, String string2) {
		boolean isInt = true;
		int pos = 0;
		
		if (string1.equalsIgnoreCase("IFNEQ")) {
			try {
				 pos = Integer.parseInt(string2);
				 						
			 } catch (NumberFormatException nfe) {
				 isInt = false;
			 }
			
			if(isInt)
				return new IfNeq(pos);
			else
				return null;
		}
		else
			return null;
	}

	/** Pasa un ifneq a String
	@return String con ifneq */
	@Override
	protected String toStringAux() {
		
		return "IFNEQ";
	}
}