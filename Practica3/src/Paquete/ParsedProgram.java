package Paquete;

import Excepciones.ArrayException;
import Interfaz.Instruction;

public class ParsedProgram {

	private Instruction[] pProgram;
	private int cont;
	public static final int MAX = 200;
	
	public ParsedProgram() {
		this.pProgram = new Instruction[MAX];
		this.cont = 0;
	}
	
	public void rellenar(Instruction linea) throws ArrayException{
		
		if(this.cont < MAX){
			this.pProgram[this.cont] = linea;
			this.cont++;
		}
		else
			throw new ArrayException("Demasiadas Instrucciones");
	}
	
	public int getNumeroInstrucciones() {
		return this.cont;
	}
		
	public Instruction getInstruction(int i) {
		return this.pProgram[i];
	}
	
	public void reset(){
		this.cont = 0;
	}
}
