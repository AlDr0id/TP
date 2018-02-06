package Paquete;

import Excepciones.ArrayException;

public class SourceProgram {

	private String[] sProgram;
	private int cont;
	public static final int MAX = 200;
	
	public SourceProgram() {
		this.sProgram = new String[MAX];
		this.cont = 0;
	}
	
	public void rellenar(String linea) throws ArrayException {
		
		if(this.cont < MAX) {
			this.sProgram[this.cont] = linea;
			this.cont++;
		}
		else
			throw new ArrayException("Demasiadas lineas de codigo. El maximo es " + MAX);
	}
	
	public int getNumeroInstrucciones() {
		return this.cont;
	}
	
	public String getInstruction(int programCounter) {
		
		return this.sProgram[programCounter];
	}
	
	public void reset() {
		this.cont = 0;
	}
	
	public String toString() {
		
		String s = System.getProperty("line.separator") + "Codigo fuente almacenado:" + System.getProperty("line.separator");
		
		if(this.cont == 0) {
			s += System.getProperty("line.separator") + " <vacio>" + System.getProperty("line.separator");
		}
		else {
			for (int i = 0; i < this.cont; i++) {
				
				s += i + ") " + this.sProgram[i] + System.getProperty("line.separator");
			}
		}
		
		return s;
	}
}
