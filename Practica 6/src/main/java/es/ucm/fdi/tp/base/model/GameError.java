package es.ucm.fdi.tp.base.model;

/**
 * 
 * Clase que hereda de RuntimeException y se encarga de los errores que se produzcan
 *
 */
public class GameError extends RuntimeException {
	// Identificador
	private static final long serialVersionUID = 4703354133717328836L;
	/**
	 * Constructora para crear un nuevo error
	 * @param msg mensaje a mostrar
	 */
	public GameError(String msg) {
		super(msg);
	}

}
