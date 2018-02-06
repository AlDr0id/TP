package ByteCode;

import CPU.CPU;

/** Clase de la instruccion PUSH */
public class Push extends ByteCodeOneParameter {

	/** Constructora principal */
	public Push(){
		super();
	}
	
	/** Constructora con parametro
	 @param p int */
	public Push(int p){
		super(p);
	}
	
	/** Ejecuta un push a la cima de la pila
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @return true si la operacion se ha realizado con exito, false en caso contrario
	 */
	@Override
	public boolean execute(CPU cpu) {
		if( cpu.push(this.param)){
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
		
		if (string1.equalsIgnoreCase("PUSH")) {
			try {
				 pos = Integer.parseInt(string2);
				 						
			 } catch (NumberFormatException nfe) {
				 isInt = false;
			 }
			
			if(isInt)
				return new Push(pos);
			else
				return null;
		}
		else
		return null;
	}

	/** Pasa un push a String
	@return String con push */
	@Override
	protected String toStringAux() {
		
		return "PUSH";
	}

}
