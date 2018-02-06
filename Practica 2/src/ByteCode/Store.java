package ByteCode;

import CPU.CPU;

/** Clase de la instruccion STORE */
public class Store extends ByteCodeOneParameter {

	/** Constructora principal */
	public Store(){
		super();
	}
	
	/** Constructora con parametro
	 @param p int */
	public Store(int p){
		super(p);
	}
	
	/** Ejecuta un store de la cima de la pila
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @return true si la operacion se ha realizado con exito, false en caso contrario
	 */
	@Override
	public boolean execute(CPU cpu) {
		
		if( cpu.write(this.param, cpu.pop()))
		{
				cpu.increaseProgramCounter();
		return true;
	}
	else
		return false;
	}

	/** Comprueba si lo introducido concuerda con este ByteCode, y si es asi crea uno
	 @param String string1 y String string2 con lo introducido por el usuario ya separado
	 @return nuevo ByteCode si coincide, null en caso contrario
	 */
	@Override
	protected ByteCode parseAux(String string1, String string2) {
		boolean isInt = true;
		int pos = 0;
		
		if (string1.equalsIgnoreCase("STORE")) {
			try {
				 pos = Integer.parseInt(string2);
				 						
			 } catch (NumberFormatException nfe) {
				 isInt = false;
			 }
			
			if(isInt)
				return new Store(pos);
			else
				return null;
		}
		else
			return null;
	}
	
	/** Pasa un store a String
	@return String con store */
	@Override
	protected String toStringAux() {
		return "STORE";
	}
}
