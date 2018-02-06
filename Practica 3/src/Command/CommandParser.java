package Command;

/** Clase encargada de parsear un String y convertirlo en Command */
public class CommandParser {
	
	/** Distintos comandos disponibles */
	private final static Command[] commands = {
			new Help(), new Quit(), new Reset(), new Replace(), new Run(),
			/*new ByteCode(),*/ new Load(), new Compile()};

	/** Pasa un string a Comando
	@param line el String a parsear
	@return null si el string no es un Comando, un comando en caso contrario*/
	public static Command parse(String line) {
		
		line = line.trim();
		String[] words = line.split(" +");
		boolean found = false;
		int i=0;
		Command c = null;
		
		while (i < commands.length && !found){
			c = commands[i].parse(words);
			
			if (c!=null) 
				found=true;
			else 
				i++;
		}
		return c;
	}
	
	/** Muestra la ayuda de todos los comandos*/
	public static void showHelp() {
		for (int i=0; i < CommandParser.commands.length; i++)
			System.out.println(CommandParser.commands[i].textHelp());
	}
	
}
