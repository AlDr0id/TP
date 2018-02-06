package Paquete;

/** Clase con los distintos comandos que puede utilizar un usuario */
public class Command {
	
	/** Enumerado que indica el tipo de Comando */
	private ENUM_COMMAND comando; 
	/** ByteCode*/
	private ByteCode instruction; 
	/** Numero que indica que ByteCode hay que remplazar */
	private int replace; 
	
	/** Crea un Comando distinto a newinst y replace
	 * @param comando Comando a crear */
	public Command(ENUM_COMMAND comando) { // Constructora para las instrucciones que no son newinst ni replace
		
		this.replace = -1;
		this.comando = comando;
		this.instruction = null;
	}
	
	/** Crea un Comando replace
	 * @param comando Comando a crear
	 * @param replace Posicion de memoria a reemplazar */
	public Command(ENUM_COMMAND comando, int replace) { // Constructora para replace
		
		this.comando = comando;
		this.replace = replace;
	}
	
	/** Crea un Comando newinst
	 * @param comando Comando a crear
	 * @param bc ByteCode a agregar */
	public Command(ENUM_COMMAND comando,ByteCode bc) { // Constructora para newinst
		
		this.comando = comando;
		this.instruction = bc;
		this.replace = -1;
	}
	
	/** Ejecuta un comando 
	 * @param engine Programa principal
	 * @return True si se ha podido ejecutar el comando, false en caso contrario */
	public boolean execute (Engine engine) {
		
		boolean ok = false;
		
		if (this.comando == ENUM_COMMAND.QUIT) {
			
			ok = engine.executeEnd(); // devuelve true
		}		
		else if(this.comando == ENUM_COMMAND.HELP) {
			
			ok = Engine.executeHelp(); // devuelve true
		}		
		else if (this.comando == ENUM_COMMAND.RUN) {
			
			ok = engine.executeRun();
		}		
		else if(this.comando == ENUM_COMMAND.NEWINST) {
			
			ok = engine.executeNewInst(this.instruction);
		}		
		else if(this.comando == ENUM_COMMAND.REPLACE) {
			
			ok = engine.executeReplace(this.replace);
		}		
		else if(this.comando == ENUM_COMMAND.RESET) {
			
			ok = engine.executeReset();
		}
		
		return ok;
	}
	
	/** Pasa un comando a String
	@return String con el comando */
	public String toString() {
		
		String s = "";
		
		switch (this.comando) {
		
		case HELP: s = "HELP";
			break;
		case NEWINST: s = "NEWINST " + this.instruction;
			break;
		case QUIT: s = "QUIT";
			break;
		case REPLACE: s = "REPLACE " + this.replace;
			break;
		case RESET: s = "RESET";
			break;
		case RUN: s = "RUN";
			break;
		default:
			break;
		}
		
		return s;
	}
}
