package ByteCode;

import CPU.CPU;

/** Clase de la instruccion GOTO */
public class GoTo extends ByteCodeOneParameter {
	
	/** Constructora principal */
	public GoTo(){
		super();
	}
	
	/** Constructora con parametro
	 @param p int */
	public GoTo(int p){
		super(p);
	}
	
	/** Coloca el contador de programa en una posicion determinada
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @return true si la operacion se ha realizado con exito, false en caso contrario
	 */
	@Override
	public boolean execute(CPU cpu) {
		if (this.param < cpu.getIndiceBcProgram())
		{
			cpu.setProgramCounter(this.param);
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
		
		if (string1.equalsIgnoreCase("GOTO")) {
			try {
				 pos = Integer.parseInt(string2);
				 						
			 } catch (NumberFormatException nfe) {
				 isInt = false;
			 }
			if(isInt)
				return new GoTo(pos);
			else
				return null;
		}
		else
			return null;
	}

	/** Pasa un goto a String
	@return String con goto */
	@Override
	protected String toStringAux() {
		return "GOTO";
	}
}
