package Excepciones;

public class ExecutionError extends Exception {
	
	public ExecutionError (String string) {
		super(string);
	}

	public ExecutionError() {
		super();
	}
}
