package CPU;

/** Clase que representa la memoria de la maquina */
public class Memory {

	/** Dimension inicial de la memoria */
	private final int TAM_INICIAL = 10;	
	/** Array con el contenido de la memoria */
	private Integer[] memory;	
	
	/** Crea una nueva memoria de dimension TAM_INICIAL */
	public Memory() { 
		
		this.memory = new Integer [TAM_INICIAL];
		
		for(int i = 0; i < this.memory.length; i++) {
			this.memory[i] = null;
		}
	}
	
	/** Recibe un int n y crea una nueva memoria con esa dimension
		@param n Nueva dimension de la memoria */
	public Memory(int n) {
		
		this.memory = new Integer [n];
		
		for(int i = 0; i < this.memory.length; i++) {
			this.memory[i] = null;
		}
	}
	
	/** Recibe dos int, el segundo indica que se va a guardar en memoria, 
	y el primero la posicion en la que se va a guardar. 
	Devuelve True si se ha podido guardar el valor en memoria
	@param value Valor a guardar en memoria
	@param pos Posicion de memoria en la que se guarda
	@return boolean ok que es true si se ha podido guardar el valor en memoria */
	public boolean write(int pos, int value) {
		
		boolean ok = false;
		
		while (pos >= this.memory.length)
			this.redimensionar();
		
		if (pos >= 0) {
			this.memory[pos] = value;
			ok = true;
		}
		return ok;
	}
	
	/** Recibe un int que indica una posicion de memoria.
	Devuelve true si esa posicion es no es null
	@param pos Posicion de memoria a comprobar
	@return True si la posicion indicada tiene un valor distinto de null */
	public boolean ocupado(int pos) { 
		
		return !(this.memory[pos] == null);
	}
	
	/** Recibe un int que indica la posicion de memoria de la que se quiere
		leer. Devuelve el valor de esa posicion si no era nulo. Si es nulo,
		devuelve 0
		@param pos Posicion de la que leer el valor
		@return Valor de lo contenido en la posicion indicada */
	public int read(int pos) { 

		if(this.memory[pos] == null)
			return 0;
		else
			return this.memory[pos];
	}
	
	/** Crea un array auxiliar de mayor dimension que el actual para luego sustituirlo por
		el principal y asi redimensionarlo */
	private void redimensionar () { 
		
		Memory memoryB = new Memory(this.memory.length + 5); // Crea un array auxiliar de mas dimension
		
		for (int i = 0; i < this.memory.length; i ++) {
			memoryB.memory[i] = this.memory[i]; // Copia todos los elementos
		}
		this.memory = memoryB.memory; // El array auxiliar pasa a ser principal
	}
	
	/** Pasa un objeto Memory a String
		@return String con el contenido de la memoria */
	public String toString() {
		
		String s= "Memoria:";
		boolean hay = false;
		
			for(int i=0; i < this.memory.length; i++) {
				if(this.ocupado(i)) {
					hay = true;
					s += " [" + i + "]:" +  this.memory[i];
				}
			}
			
		if (!hay)
			s += " <vacia>";
		return s;
	}
}