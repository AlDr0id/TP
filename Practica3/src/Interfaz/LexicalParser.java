package Interfaz;

import Excepciones.ArrayException;
import Excepciones.LexicalAnalysisException;
import Paquete.ParsedProgram;
import Paquete.SourceProgram;

public class LexicalParser {
	
	private SourceProgram sProgram;
	private int programCounter;
	
	public LexicalParser() {
		this.sProgram = new SourceProgram();
		this.programCounter = 0;
	}
	
	public LexicalParser(SourceProgram sp) {
		this.sProgram = sp;
		this.programCounter = 0;
	}
	
	public void lexicalParser(ParsedProgram pProgram, String stopKey) throws LexicalAnalysisException, ArrayException {
		
		boolean stop = false;
		
		while (this.programCounter < sProgram.getNumeroInstrucciones()&& !stop) {
			String instr = sProgram.getInstruction(this.programCounter);
			instr = instr.trim();
			
			if (instr.equalsIgnoreCase(stopKey)) {
				stop = true;
			}
			else {
				Instruction instruction = InstructionParser.parse(instr,this);
				
				if(instruction != null)
					pProgram.rellenar(instruction);
				else
					throw new LexicalAnalysisException( "Error sintactico en linea " + Integer.toString(this.programCounter));
				}
			}
	}
	
	
	public void increaseProgramCounter() {
		this.programCounter++;
	}

	public void reset() {
		this.programCounter = 0;
		this.sProgram.reset();
		
	}

}
