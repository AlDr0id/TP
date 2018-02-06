package ByteCode;

import CPU.CPU;
import Excepciones.StackException;

/** Clase de la instruccion LOAD */
public class Load extends ByteCodeOneParameter {

	/** Constructora principal */ 
	public Load(){
		super();
	}
	
	/** Constructora con parametro
	 @param p int */
	public Load(int p){
		super(p);
	}
	
	/** Ejecuta un load a la cima de la pila desde una posicion de memoria
	 @param cpu contiene toda la unidad de procesamiento de la maquina
	 @return true si la operacion se ha realizado con exito, false en caso contrario
	 * @throws StackException 
	 */
	@Override
	public boolean execute(CPU cpu) throws StackException {
		if( cpu.read(this.param)) {
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
		
		if (string1.equalsIgnoreCase("LOAD")) {
			try {
				 pos = Integer.parseInt(string2);
				 						
			 } catch (NumberFormatException nfe) {
				 isInt = false;
			 }
			
			if(isInt)
				return new Load(pos);
			else
				return null;
		}
		else
			return null;
	}
	
	/** Pasa un load a String
	@return String con load */
	@Override
	protected String toStringAux() {

		return "LOAD";
	}
}
