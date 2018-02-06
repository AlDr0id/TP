package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;

/**
 * Una accion para el juego WolfAndSheep
 *
 */
public class WolfAndSheepAction implements GameAction<WolfAndSheepState, WolfAndSheepAction> {

	// Numero del jugador
	private int player;
    // Fila a la que va
	private int row;
	// Columna a la que va
    private int col;
    private int oldRow; // Atributos necesarios para poder saber cual es la ficha que se mueve
    private int oldCol;	// y poder "borrar" la ficha al mover
    
    /**
     * Constructora para crear una nueva accion en la partida
     * @param player jugador actual
     * @param oldRow fila donde estaba la ficha
     * @param oldCol columna donde estaba la ficha
     * @param row fila a la que va
     * @param col columna a la que va
     */
    public WolfAndSheepAction(int player,int oldRow, int oldCol, int row, int col) {
        this.player = player;
        this.row = row;
        this.col = col;
        this.oldRow = oldRow;
        this.oldCol = oldCol;
    }
    /**
     * Devuelve el numero del jugador
     * @return numero del jugador
     */
	@Override
	public int getPlayerNumber() {
		return this.player;
	}

	/**
	 * Aplica la jugada al tablero actual
	 * @param state estado de juego previo
	 * @return nuevo estado del juego
	 */
	@Override
	public WolfAndSheepState applyTo(WolfAndSheepState state) {
		if (player != state.getTurn()) {
            throw new IllegalArgumentException("Not the turn of this player");
        }

        // make move
        int[][] board = state.getBoard();
        board[row][col] = player; // Añades ficha
        board[this.oldRow][this.oldCol]= -1; // Y borras la antigua (el -1 es el empty de estado)

        // update state
        WolfAndSheepState next;
        if (WolfAndSheepState.isWinner(board, state.getTurn())) { // si hay ganador se acaba
            next = new WolfAndSheepState(state, board, true, state.getTurn());
        } /*else if (WolfAndSheepState.isDraw(board)) {
            next = new WolfAndSheepState(state, board, true, -1); // WaS es un juego con ganador, no hay empate
        }*/ else {
            next = new WolfAndSheepState(state, board, false, -1); // si no se sigue jugando
        }
        return next;
	}
	/**
	 * Devuelve la informacion de una posible jugada
	 * @return string con una posible jugada
	 */
	 public String toString() {
	        return "place " + player+ "from ("+ oldRow + ", " + oldCol + ") at (" + row + ", " + col + ")";
	    }


}
