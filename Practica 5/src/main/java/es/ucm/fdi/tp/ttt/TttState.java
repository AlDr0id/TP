package es.ucm.fdi.tp.ttt;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

/**
 * A TickTackToe state.
 * Describes a board of TickTackToe that is either being
 * played or is already finished.
 */
public class TttState extends GameState<TttState, TttAction> {
	// Identificador
	private static final long serialVersionUID = -2641387354190472377L;
	// Turno actual
	private final int turn;
	// Indicador de partida terminada
    private final boolean finished;
    // Tablero
    private final int[][] board;
    // Ganador de la partida
    private final int winner;
    // Dimension del tablero
    private final int dim;
    // Valor que representa una casilla vacia
    final static int EMPTY = -1;
    /**
     * Constructora para crear un tablero vacio
     * @param dim dimension del tablero
     */
    public TttState(int dim) {    	
        super(2);
        // Si la dimension no es 3 o 4 lanza excepcion
        if (dim < 3 || dim > 4) {
            throw new IllegalArgumentException("Expected dim to be 3 or 4");
        }

        this.dim = dim;
        board = new int[dim][];
        // Inicializa el tablero a vacio
        for (int i=0; i<dim; i++) {
            board[i] = new int[dim];
            for (int j=0; j<dim; j++) board[i][j] = EMPTY;
        }
        this.turn = 0;
        this.winner = -1;
        this.finished = false;
    }
    /**
     * Constructora para crear un tablero con una partida ya empezada
     * @param prev estado de la partida anterior
     * @param board tablero
     * @param finished si ha terminado o no la partida
     * @param winner ganador
     */
    public TttState(TttState prev, int[][] board, boolean finished, int winner) {
    	super(2);
    	this.dim = prev.dim;
        this.board = board;
        this.turn = (prev.turn + 1) % 2;
        this.finished = finished;
        this.winner = winner;
    }    
    /**
     * Comprueba si una accion es valida
     * @param action accion a realizar
     * @return valido o no
     */
    public boolean isValid(TttAction action) {
        if (isFinished()) {
            return false;
        }
        return at(action.getRow(), action.getCol()) == EMPTY;
    }
	/**
	 * Lista que se rellena con las posibles jugadas que se pueden realizar
	 * @param playerNumber numero del jugador
	 */
    public List<TttAction> validActions(int playerNumber) {
        ArrayList<TttAction> valid = new ArrayList<>();
        if (finished) {
            return valid;
        }

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (at(i, j) == -1) {
                    valid.add(new TttAction(playerNumber, i, j));
                }
            }
        }
    
        return valid;
    }
    /**
     * Comprueba si el tablero esta completo
     * @param board tablero
     * @return si esta lleno o no
     */
    public static boolean isDraw(int[][] board) {
        boolean empty = false;
        for (int i=0; ! empty && i<board.length; i++) {
            for (int j=0; ! empty && j<board.length; j++) {
                if (board[i][j] == EMPTY) {
                    empty = true;
                }
            }
        }
        return ! empty;
    }
    /**
     * Comprueba si se ha ganado la partida
     * @param board tablero
     * @param playerNumber numero del jugador
     * @param x0
     * @param y0
     * @param dx
     * @param dy
     * @return si hay ganador
     */
    private static boolean isWinner(int[][] board, int playerNumber, 
    		int x0, int y0, int dx, int dy) {
        boolean won = true;
        for (int i=0, x=x0, y=y0; won && i<board.length; i++, x+=dx, y+=dy) {
            if (board[y][x] != playerNumber) won = false;
        }
        return won;
    }

    /**
     * Comprueba si se ha ganado la partida
     * @param board tablero
     * @param playerNumber numero del jugador
     * @return si se ha ganado o no
     */
    public static boolean isWinner(int[][] board, int playerNumber) {
        boolean won = false;
        for (int i=0; !won && i<board.length; i++) {
            if (isWinner(board, playerNumber, 0, i, 1, 0)) won = true;
            if (isWinner(board, playerNumber, i, 0, 0, 1)) won = true;
        }
        return won ||
                isWinner(board, playerNumber, 0, 0, 1, 1) ||
                isWinner(board, playerNumber, 2, 0, -1, 1);
    } 
    /**
     * Devuelve el contenido de una casilla
     * @param row fila
     * @param col columna
     * @return contenido
     */
    public int at(int row, int col) {
        return board[row][col];
    }
    /**
     * Devuelve el turno actual
     * @return turno
     */
    public int getTurn() {
        return turn;
    }
    /**
     * Devuelve el indicador de si se ha terminado o no la partida
     * @return terminada o no
     */
    public boolean isFinished() {
        return finished;
    }
    /**
     * Devuelve el ganador de la partida
     * @return ganador
     */
    public int getWinner() {
        return winner;
    }

    /**
     * @return a copy of the board
     */
    public int[][] getBoard() {
        int[][] copy = new int[board.length][];
        for (int i=0; i<board.length; i++) copy[i] = board[i].clone();
        return copy;
    }
    /**
     * Devuelve un string con el estado del tablero
     * @return estado del tablero
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<board.length; i++) {
            sb.append("|");
            for (int j=0; j<board.length; j++) {
                sb.append(board[i][j] == EMPTY ? "   |" : board[i][j] == 0 ? " O |" : " X |");
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
		return this.isValid((TttAction)action);
	}
	
}
