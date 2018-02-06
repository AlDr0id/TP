package Paquete;

import java.util.*;

/** Clase que contiene el bucle de control de la aplicacion */
public class Engine {

	private boolean end;
	private ByteCodeProgram bcProgram;
	private static Scanner in = new Scanner (System.in);
	private CPU cpu;
	
	/** Crea un Engine que contiene una CPU, un ByteCodeProgram y un booleano para ver si hay que terminar */
	public Engine() {
		
		this.bcProgram = new ByteCodeProgram();
		this.end = false;
		this.cpu = new CPU();
	}

	/** Mientras que no se termine, se van recibiendo comandos e informando del Programa actual guardado */
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
			
			System.out.println(System.getProperty("line.separator") + "Comienza la ejecucion de "
			+ line.toUpperCase() + System.getProperty("line.separator"));
			
			if (command == null || !command.execute(this))
				System.out.println("ERROR DE EJECUCION");
			
			System.out.println(this.bcProgram.toString());
		}
		
		System.out.println("Fin de la ejecucion");
		in.close();
}
	
	/** Ejecuta las instrucciones guardadas en el ByteCodeProgram, siempre y cuando sean correctas.
		Si todas son correctas devuelve True. En caso contrario False
		@return True si todas las instrucciones que se ejecutan son correctas */
	public boolean executeRun() { 
		
		int i = 0;
		boolean ok = true;
					
			while (i < this.bcProgram.getIndice() && ok && !end) { // Mientras que haya instruciones y se puedan ejecutar
				
				ByteCode instr = this.bcProgram.leer(i); // Se leen las instrucciones
				System.out.println(System.getProperty("line.separator") + "El estado de la maquina tras ejecutar el bytecode "
						+ instr + " es:" + System.getProperty("line.separator") + System.getProperty("line.separator"));
				
				if (this.cpu.execute(instr)) {
					System.out.println(this.cpu.toString()); // Se muestra la CPU si se ha ejecutado la instruccion
					i++;
					this.end = cpu.haTerminado();
				}
				else
					ok = false;
			}
				
		cpu.reset();
		return ok;
	}
	
	/** Se muestra la ayuda y devuelve True
		@return True */
	public static boolean executeHelp() {
		
		System.out.println("HELP: Muestra esta ayuda." + System.getProperty("line.separator") + "NEWINST BYTECODE: Introduce una nueva instruccion al programa" + System.getProperty("line.separator")
				+ "QUIT: Cierra el programa" + System.getProperty("line.separator") + "REPLACE N: Reemplaza la instruccion N por la solicitada al usuario" + System.getProperty("line.separator")
				+ "RUN: Ejecuta el programa" + System.getProperty("line.separator") + "RESET: Vacia el programa actual" + System.getProperty("line.separator"));
		return true;
	}
	
	/** Se termina la ejecucion y devuelve true
		@return True */
	public boolean executeEnd() {
		
		return this.end = true;
	}
	
	/** Añade un ByteCode al ByteCodeProgram y devuelve True.
	Si no lo consigue añadir devuelve False
	@param bc ByteCode que agregar a la lista de instrucciones
	@return True si se ha podido añadir el ByteCode */
	public boolean executeNewInst(ByteCode bc) {
		
		return this.bcProgram.addByteCode(bc);
	}
	
	/** Resetea la CPU y el ByteCodeProgram y devuelve True
		@return True */
	public boolean executeReset() {
		
		this.bcProgram.reset();
		this.cpu.reset();
		return true;
	}
	
	/** Recibe un int indicando la posicion de la instruccion a cambiar.
	Se pide un nuevo ByteCode. Si este es correcto y se puede sustituir en la
	posicion dada, se devuelve True. Se devuelve False en caso contrario
	@param pos Posicion a modificar
	@return True si se ha sustituido con exito el contenido de esa posicion */
	public boolean executeReplace(int pos) { 
		
		boolean ok = false;
		
		if(pos < bcProgram.getIndice()) {
			System.out.println("Introduce la nueva instruccion:");
			String newbc = in.nextLine();
			ByteCode bc = ByteCodeParser.parser(newbc);
			
			if(bc.getEnum() != null) {
				bcProgram.replaceByteCode(bc, pos);
				ok = true;
			}
		}		
		
		return ok;		
	}
}
