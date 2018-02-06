package Paquete;

/** Clase encargada de parsear un String y convertirlo en Command */
public class CommandParser {

	/** Recibe un String y lo pasa a Comando, si el String no se corresponde con ningun comando, devuelve null 
	@param line String a convertir
	@return Command */
	public static  Command parse(String line) {
		
		ENUM_COMMAND a = null;
		line = line.trim();
		String[] w = line.split(" +");
		boolean isInt = true;
		
		 if (w.length == 0 )
			 return null;
		 
		 else if(w.length == 1) { 
			 
			 switch(w[0].toUpperCase()) {
			 
			 case "HELP": a = ENUM_COMMAND.HELP;
			 	break;
			 case "QUIT": a = ENUM_COMMAND.QUIT;
				 break;
			 case "RUN": a = ENUM_COMMAND.RUN;
			 	break;
			 case "RESET": a = ENUM_COMMAND.RESET;
			 	break;
			 default:
				 break;
			 }
			 
		 return new Command(a);
		 }
		 else if(w.length == 2) {
			 
			 if(w[0].equalsIgnoreCase("NEWINST")) {
				 
				 a = ENUM_COMMAND.NEWINST;
				 return new Command(a, ByteCodeParser.parser(w[1]));
			}
			 else if (w[0].equalsIgnoreCase("REPLACE")) {
				 
				 a = ENUM_COMMAND.REPLACE;
				 return new Command(a, Integer.parseInt(w[1]));
				 
			 }
			 else return null;
		 
		 }
		 else if (w.length == 3) {
			 
			 try {
				 Integer.parseInt(w[2]);
				 						/* Aqui comprobamos si el tercer parametro introducido es un numero.
											Si no lo es no se realiza la ejecucion del comando.
											No es algo que hemos dado en clase, pero era necesario para solventar
											la excepcion que habia al introducir una letra en vez de un numero */
			 } catch (NumberFormatException nfe) {
				 isInt = false;
			 }					
			 
			 if(w[0].equalsIgnoreCase("NEWINST") && isInt) {
				 
				 a = ENUM_COMMAND.NEWINST;
				 return new Command( a, ByteCodeParser.parser(w[1]+ " " + w[2]));
			 }
			 else return null;
			 
		 }
		 else return null;
	}
}
