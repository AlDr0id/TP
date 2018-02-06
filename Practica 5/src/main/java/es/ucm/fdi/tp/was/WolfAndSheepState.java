package es.ucm.fdi.tp.was;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

/**
 * Un estado del juego WolfAndShepp, que puede estar jugandose o haber terminado ya
 * 
 */
public class WolfAndSheepState extends GameState<WolfAndSheepState, WolfAndSheepAction> {

	// Turno actual
	private final int turn;
	// Indicador de si ha terminado el juego o no
    private final boolean finished;
    // Tablero
    private final int[][] board;
    // Ganador de la partida
    private final int winner;
    // Dimension del tablero
    private final int dim;
    // Valor de casilla vacia
    final static int EMPTY = -1;
    // Valor de casilla no accesible
    final static int ILEGAL = -2;
    // Valor de ficha lobo
    final static int LOBO = 0;
    // Valor de ficha oveja
    final static int OVEJA = 1;

    /**
     * Constructora para crear un tablero inicial
     * @param dim dimension del tablero
     */
	public WolfAndSheepState(int dim) {
		super(2);
        if (dim != 8) {// Puede que cambie
            throw new IllegalArgumentException("Expected dim to be 8");
        }

        this.dim = dim;
        this.board = new int[dim][]; 
        // Inicializamos el tablero con casillas vacias y no accesibles
        for (int i=0; i<dim; i++) {
        	this.board[i] = new int[dim];
            for (int j=0; j<dim; j++) {
            	if((i+j)%2 == 1)
            		this.board[i][j] = EMPTY;
            	else
            		this.board[i][j] = ILEGAL;
            	} 
        }
        // Colocamos las fichas oveja
        for (int i = 1;i < this.dim; i +=2)
        	this.board[0][i] = OVEJA;
        // Colocamos el lobo
        this.board[dim-1][0] = LOBO;// solo valido para dim pares
        this.turn = 0;
        this.winner = -1;
        this.finished = false;
	}

	/**
	 * Constructora para crear un tablero de una partida ya empezada
	 * @param prev estado anterior
	 * @param board tablero
	 * @param finished indicador de si la partida ha terminado o no
	 * @param winner ganador de la partida
	 */
	public WolfAndSheepState(WolfAndSheepState prev, int[][] board, boolean finished,
			int winner) {
		super(2);
    	this.dim = prev.dim;
        this.board = board;
        this.turn = (prev.turn + 1) % 2;
        this.finished = finished;
        this.winner = winner;
	}

	/**
	 * Devuelve de quien es el turno
	 * @return turno actual
	 */
	@Override
	public int getTurn() {
		 return this.turn;
	}

	/**
	 * Lista de posibles movimientos que puede hacer el jugador que tiene el turno
	 * @param playerNumber numero del jugador actual
	 * @return movimientos validos
	 */
	@Override
	public List<WolfAndSheepAction> validActions(int playerNumber) { // SE NECESITA SABER LA FILA Y COLUMNA DE LA FICHA QUE SE MUEVE
		ArrayList<WolfAndSheepAction> valid = new ArrayList<>();
		
		if (finished) {
            return valid;
        }
        // Si el turno es del lobo
        if (playerNumber == LOBO) {
    		
        	int i = 0;
        	boolean found = false;
    		while (i < dim && !found) {
    			int j = 0;
                while( j < dim && !found) {
                    if (this.board[i][j] == LOBO) {// Si es lobo se puede mover en las cuatro dir
                    	// Siempre y cuando la casilla este vacia y no se salga del tablero
                    	found = true;
                    	if(i+1 < dim && j - 1 >= 0 && board[i+1][j-1] == EMPTY)
                    		valid.add(new WolfAndSheepAction(playerNumber, i, j, i+1, j-1));
                    	if(i+1 < dim && j + 1 < dim && board[i+1][j+1] == EMPTY)
                    		valid.add(new WolfAndSheepAction(playerNumber, i, j, i+1, j+1));
                    	if(i-1 >= 0 && j - 1 >= 0 && board[i-1][j-1] == EMPTY)
                    		valid.add(new WolfAndSheepAction(playerNumber, i, j, i-1, j-1));
                    	if(i-1 >= 0 && j + 1 < dim && board[i-1][j+1] == EMPTY)
                    		valid.add(new WolfAndSheepAction(playerNumber, i, j, i-1, j+1));
                     }
                    j++;
                }
                i++;
            }
    	}
        // Si no es lobo, es decir es oveja
    	else {
    		int i = 0;
    		int cont = 0;
    		while(i < dim && cont < 4 ) {	// POSIBLE CONTADOR HASTA 4 Y QUE SE SALGA
                int j = 0;
    			while (j < dim && cont < 4) {
                    if (this.board[i][j] == OVEJA && i != dim -1) {
                    	cont++;
                    	if (j != dim - 1 ) {
                    		if( board[i+1][j+1] == EMPTY)
                    			valid.add(new WolfAndSheepAction(playerNumber, i, j, i+1, j+1));
                    		}
                    	if (j != 0 ) {
                    		if( board[i+1][j-1] == EMPTY)
                    			valid.add(new WolfAndSheepAction(playerNumber, i, j, i+1, j-1));
                    		}                   			
                    }
                    j++;
                 }
                i++;
              }
           }
    
        return valid;
	}

	/**
	 * Determina si una jugada es valida o no
	 * Es especifica para el juego WolfAndSheep
	 * @param action accion a evaluar
	 * @return true si es valida, false si no
	 */
	public boolean isValid(WolfAndSheepAction action) { // esta funcion solo
		// se usa en el gui, luego solo te van a dar posiciones del tablero, no hace falta comprobarlo
		
		if(action.getPlayerNumber() == LOBO) {
			if(board[action.getOldRow()][action.getOldCol()]== LOBO){
				return(Math.abs((action.getOldCol() - action.getCol())) == 1 && (Math.abs(action.getOldRow() - action.getRow()) == 1)
						&& (board[action.getRow()][action.getCol()] == EMPTY));
			}
			else
				return false;	
		}
		else{
			if(board[action.getOldRow()][action.getOldCol()]== OVEJA)
				return(Math.abs((action.getOldCol() - action.getCol())) == 1 && (action.getOldRow() - action.getRow() == -1)
				&& (board[action.getRow()][action.getCol()] == EMPTY));
			else
				return false;
		}
	}
	
	
	/**
	 * Comprueba si ha terminado la partida
	 * @return indicador de si ha terminado la partida o no
	 */
	@Override
	public boolean isFinished() {
		return this.finished;
	}

	/**
	 * Devuelve el ganador de la partida
	 * @return ganador
	 */
	@Override
	public int getWinner() {
		return this.winner;
	}

	/**
	 * Clona el tablero
	 * @return copia del tablero actual
	 */
	public int[][] getBoard() {
		 int[][] copy = new int[board.length][]; // Creas matriz de forma especial
	        for (int i=0; i<board.length; i++) copy[i] = board[i].clone();
	        return copy;
	}

	/**
	 * Comprueba si hay un ganador de la partida
	 * @param board tablero actual
	 * @param turn turno actual
	 * @return si hay ganador o no
	 */
	public static boolean isWinner(int[][] board, int turn) {
		 boolean encontrado = false;
		 boolean win = false;
		 int i=0;
	        while (!encontrado && i<board.length) {
	        	int j=0;
	        	while ( !encontrado && j<board.length)
	        	{
	        		if (board[i][j] == LOBO) // Buscas al lobo por el tablero
	        		{
	        			encontrado = true;
	        			if(turn == LOBO) // Si le toca a el
	        			{
	        				if(i == 0) // y esta en la primera fila, ha ganado
	        					{
	        					win = true;
	        					}
	        				else{
	        					WolfAndSheepState state = new WolfAndSheepState(board.length);
	        					// Creamos un estado auxiliar para poder invocar validActions
		        				state = new WolfAndSheepState(state, board, false, -1);
		        				// Si no hay posibles acciones, gana el lobo
		        				if(state.validActions(OVEJA).size() == 0)
		        					win = true;
	        				}
	        			}
	        			else
	        			{
	        				WolfAndSheepState state = new WolfAndSheepState(board.length);
	        				 // Creamos un estado auxiliar para poder invocar validActions
	        				state = new WolfAndSheepState(state, board, false, -1);
	        				// Si no hay posibles acciones, ganan las ovejas
	        				if(state.validActions(LOBO).size() == 0)
	        					win = true;
	        			}
	        		}
	        		j++;
	        	}
	        	i++;
	        }
	        return win;
	}
	
	/**
	 * Devuelve la disposicion del tablero actual
	 * @return string con tablero actual
	 */
	public String toString() {// ATENCION TE PUEDES MOVER A BLANCOS Y NO A · , se puede cambiar
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<board.length; i++) {
            sb.append("|");
            for (int j=0; j<board.length; j++) {
                sb.append(board[i][j] == EMPTY ? "   |":board[i][j] == ILEGAL ? " · |" : board[i][j] == LOBO ? " O |" : " X |");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

	@Override
	/**
	 * Determina si una jugada es valida o no
	 * @param action accion a evaluar
	 * @return true si es valida, false si no
	 */
	public boolean isValid(GameAction action) {
		
		return this.isValid((WolfAndSheepAction)action);
	}
	

}
