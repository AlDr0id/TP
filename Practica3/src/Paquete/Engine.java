package Paquete;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import ByteCode.ByteCode;
import ByteCode.ByteCodeParser;
import CPU.CPU;
import Command.Command;
import Command.CommandParser;
import Excepciones.ArrayException;
import Excepciones.BadFormatByteCode;
import Excepciones.DivisionByZero;
//import Excepciones.ExecutionError;
import Excepciones.LexicalAnalysisException;
import Excepciones.StackException;
import Interfaz.Compiler;
import Interfaz.LexicalParser;
/** Clase que contiene el bucle de control de la aplicacion */
public class Engine {

	private boolean end;
	private ByteCodeProgram bcProgram;
	private static Scanner in = new Scanner (System.in);
	private CPU cpu;
	private SourceProgram sProgram;
	private ParsedProgram pProgram;
	private Compiler cp;
	private LexicalParser lp;
	
	/** Crea un Engine que contiene una CPU, un ByteCodeProgram y un booleano para ver si hay que terminar */
	public Engine() {
		
		this.end = false;
		this.cpu = new CPU();
		this.bcProgram = cpu.getByteCodeProgram();
		this.pProgram = new ParsedProgram();
		this.sProgram = new SourceProgram();
		this.cp = new Compiler(this.bcProgram);
		this.lp = new LexicalParser(this.sProgram);
	}

	/** Mientras que no se termine, se van recibiendo comandos e informando del Programa actual guardado 
	 * @throws ArrayException 
	 * @throws LexicalAnalysisException */
	public void start() {
		
		this.cpu.reset();
		this.end = false; /* Aunque en este programa solo se llama a start() en el main despues de haber llamado a la
							constructora de Engine, se añade por si en un futuro se llamara a start() sin llamar previamente
							a la constructora */
		String line = "";
		
		while (!end) {
			Command command = null;
			System.out.print('>');
			line = in.nextLine();
			command = CommandParser.parse(line);
			line = line.trim();
			
			System.out.println(System.getProperty("line.separator") + "Comienza la ejecucion de "
			+ line.toUpperCase() + System.getProperty("line.separator"));
			
			try {
				if (command == null || !command.execute(this))
					System.out.println("ERROR DE EJECUCION");
			} catch (LexicalAnalysisException e) {
				System.err.println(e.getMessage());
				//e.printStackTrace();
			} catch (ArrayException e) {
				System.err.println(e.getMessage());
				//e.printStackTrace();
			} catch (StackException e) {
				System.err.println(e.getMessage());
				//e.printStackTrace();
			} catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
				//e.printStackTrace();
			} catch (DivisionByZero e) {				
				System.err.println(e.getMessage());
				//e.printStackTrace();
			} catch (BadFormatByteCode e) {
				System.err.println(e.getMessage());
				//e.printStackTrace();
			}// catch (ExecutionError e){
			//	e.getMessage();
			//}
			System.out.println(this.sProgram.toString());
			System.out.println(this.bcProgram.toString());
		}
		
		System.out.println("Fin de la ejecucion");
		in.close();
	}
	
	/** Ejecuta las instrucciones guardadas en el ByteCodeProgram, siempre y cuando sean correctas.
		Si todas son correctas devuelve True. En caso contrario False
		@return True si todas las instrucciones que se ejecutan son correctas 
	 * @throws StackException 
	 * @throws DivisionByZero */
	public boolean executeRun() throws StackException, DivisionByZero { 
		
		boolean ok = true;
					
				if (!this.cpu.run())
					ok = false;

		cpu.reset();
		return ok;
	}
	
	/** Se muestra la ayuda y devuelve True
		@return True */
	public static boolean executeHelp() {
		
		CommandParser.showHelp();
		
		return true;
	}
	
	/** Se termina la ejecucion y devuelve true
		@return True */
	public boolean executeEnd() {
		
		return this.end = true;
	}
	
	
	/** Resetea la CPU y el ByteCodeProgram y devuelve True
		@return True */
	public boolean executeReset() {
		
		this.bcProgram.reset();
		this.cpu.reset();
		this.pProgram.reset();
		this.cp.reset();
		this.lp.reset();
		return true;
	}
	
	/** Recibe un int indicando la posicion de la instruccion a cambiar.
	Se pide un nuevo ByteCode. Si este es correcto y se puede sustituir en la
	posicion dada, se devuelve True. Se devuelve False en caso contrario
	@param pos Posicion a modificar
	@return True si se ha sustituido con exito el contenido de esa posicion 
	 * @throws BadFormatByteCode */
	public boolean executeReplace(int pos) throws BadFormatByteCode { 
		
		boolean ok = false;
		
		if(pos < bcProgram.getIndice() && pos >= 0) {
			System.out.println("Introduce la nueva instruccion:");
			String newbc = in.nextLine();
			ByteCode bc = ByteCodeParser.parse(newbc);
				
			if(bc != null) {
				bcProgram.replaceByteCode(bc, pos);
				ok = true;
			}
			else
				throw new BadFormatByteCode("El ByteCode no es correcto ");
		
		}		
		
		return ok;		
	}

	/**Almacena ByteCodes en el el ByteCodeProgram mientras estos sean correctos
	   o se escriba end
	   @return true si todos los ByteCode son correctos, false en otro caso 
	 * @throws ArrayException */
	public boolean readByteCodeProgram() throws ArrayException {
		
		boolean ok = true;
		bcProgram.reset();
		// Aunque en el pdf aparece que al introducir bytecode se resetea el programa,
		// no lo implementamos porque si no el comando reset no serviria
		// En caso necesario bastaria con descomentarlo y estaria operativo
		String instruccion = in.nextLine();
		
		while(ok && !instruccion.equalsIgnoreCase("END")) {
			ByteCode bc = ByteCodeParser.parse(instruccion);
			
			if(bc != null) {
				bcProgram.addByteCode(bc);
				instruccion = in.nextLine();
			}
			else
				ok = false;
		}
		return ok;
	}
	
	public boolean compile() throws LexicalAnalysisException, ArrayException {
	
			this.lexicalAnalysis();
			this.generateByteCode();
			return true;
	}
	
	private void lexicalAnalysis() throws LexicalAnalysisException, ArrayException {
		lp.lexicalParser(this.pProgram, "END");
	}
	
	private void generateByteCode() throws ArrayException {
		this.cp.compile(this.pProgram);
	
	}

	public boolean executeLoad(String nombre) throws FileNotFoundException, ArrayException {
	
		Scanner sc;
		boolean ok = false;
		String line;
		
		try {
				
			sc = new Scanner(new File(nombre));
			this.executeReset();
			
			while(sc.hasNextLine()) {	
				line = sc.nextLine();
				line = line.trim();
				this.sProgram.rellenar(line);
//				if (!line.equalsIgnoreCase(""))
//					this.sProgram.rellenar(line);
					 
			}
			
			/*if(sc.hasNextLine()) {
				line = sc.nextLine();
				line = line.trim();
				
			//	if (!line.equalsIgnoreCase(""))
			//		this.sProgram.rellenar(line);
				
				while(sc.hasNextLine()) {	
					line = sc.nextLine();
					line = line.trim();
					if (!line.equalsIgnoreCase(""))
						this.sProgram.rellenar(line);
						 
				}*/
			sc.close();
			ok = true;
				           
				    
		} catch (FileNotFoundException e) {
				    	
			throw new FileNotFoundException("No se ha encontrado el archivo");
				
		}
				
		return ok;
	}

}
