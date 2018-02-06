package Paquete;

/** Clase que contiene las instrucciones ByteCode almacenadas hasta el momento */
public class ByteCodeProgram {

	/** Tamaño inicial del ByteCodeProgram */
	private final int MAX = 100;
	/** Array que contiene los ByteCodes del programa */
	private ByteCode[] program;
	/** Indice que indica el tamaño actual del array */
	private int indice;
	
	/** Crea un nuevo ByteCodeProgram de tamaño MAX	e indice 0 */
	public ByteCodeProgram() {
		
		this.program = new ByteCode[MAX];
		this.indice = 0;
	}
	
	/** Añade un ByteCode al ByteCodeProgram
	 * @param bc ByteCode a agregar
	 * @return True si se ha podido agregar, False en caso contrario */
	public boolean addByteCode(ByteCode bc) {
		
		
		boolean ok = false;
		if(this.indice < MAX && bc.getEnum() != null) {
			
			this.program[this.indice] = bc;
			this.indice ++;
			ok = true;
		}
		
		return ok;
	}
	
	/** Devuelve el ByteCode de una posicion
	 * @param pos posicion a leer
	 * @return ByteCode que se encuentra en la posicion pos */
	public ByteCode leer(int pos) {
		
		
		return this.program[pos];
	}
	
	/** Sustituye el ByteCode de una posicion dada por otro ByteCode dado
	 * @param bc Nuevo ByteCode
	 * @param pos Posicion a reemplazar*/
	public void replaceByteCode(ByteCode bc, int pos) {
		
		
		this.program[pos] = bc;
	}
	
	/** Devuelve el numero de elementos del ByteCodeProgram
	 * @return Indice del ByteCodeProgram */
	public int getIndice() {
		
		
		return this.indice;	
	}
	
	/** Resetea el ByteCodeProgram poniendo el indice a 0 */
	public void reset() {
		
		
		this.indice = 0;	
	}
	
	/** Pasa un ByteCodeProgram a string
	@return String con el contenido del ByteCodeProgram */
	public String toString() {
		
		
		String s = System.getProperty("line.separator") + "Programa almacenado:" + System.getProperty("line.separator");
		
		if(this.indice == 0) {
			s += System.getProperty("line.separator") + " <vacio>" + System.getProperty("line.separator");
		}
		else {
			for (int i = 0; i < this.indice; i++) {
				
				s += i + ") " + this.program[i] + System.getProperty("line.separator");
			}
		}
		
		return s;
	}
}
