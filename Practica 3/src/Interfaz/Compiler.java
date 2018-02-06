package Interfaz;

import ByteCode.ByteCode;
import Excepciones.ArrayException;
import Paquete.ByteCodeProgram;
import Paquete.ParsedProgram;

public class Compiler {
	
	private ByteCodeProgram bytecode;
	private String [] varTable;
	private int numVars;
	public final int MAXVARS = 27;
	
	
	public Compiler() {
		
	}
	
	public Compiler(ByteCodeProgram bc){
		this.bytecode = bc;
		this.numVars = 0;
		this.varTable = new String [MAXVARS];
	}
	
	public void compile(ParsedProgram pProgram) throws ArrayException {
		int i = 0;
		
		try {
			while (i < pProgram.getNumeroInstrucciones()) {
				Instruction instr = pProgram.getInstruction(i);
				instr.compile(this);
				i++;
			}
			
		}
		catch (ArrayException e){
			
		}
	}

	public int getIndex(String varName) {
		int num = 0;
		boolean encontrado = false;
		
		while (num < this.numVars && !encontrado) {
			if (varName.equalsIgnoreCase( this.varTable[num]))
				encontrado = true;
			else
				num++;
		}
		
		if(!encontrado) {
			this.varTable[num] = varName;
			this.numVars++;
		}
		return num;
	}

	public int getProgramCounter() {
		
		return this.bytecode.getIndice();
	}

	public void addByteCode(ByteCode bc) throws ArrayException {
		
		this.bytecode.addByteCode(bc);
	}
	
	public void reset(){
		this.numVars = 0;
		this.bytecode.reset();
	}
}
