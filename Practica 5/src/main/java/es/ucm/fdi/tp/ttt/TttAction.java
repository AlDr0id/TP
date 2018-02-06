package es.ucm.fdi.tp.ttt;

import es.ucm.fdi.tp.base.model.GameAction;

/**
 * An action for TickTackToe.
 */
public class TttAction implements GameAction<TttState, TttAction> {
	// Identificador
	private static final long serialVersionUID = -8491198872908329925L;
	// Numero del jugador
	private int player;
	// Fila elegida
    private int row;
    // Columna elegida
    private int col;
    /**
     * Constructora para crear una nueva jugada o accion
     * @param player numero del jugador
     * @param row fila elegida
     * @param col columna elegida
     */
    public TttAction(int player, int row, int col) {
        this.player = player;
        this.row = row;
        this.col = col;
    }
    /**
     * Devuelve el numero del jugador
     * @return player numero del jugador
     */
    public int getPlayerNumber() {
        return player;
    }
    /**
     * Aplica un movimiento
     * @param state estado actual
     * @return next siguiente estado
     */
    public TttState applyTo(TttState state) {
        if (player != state.getTurn()) {
            throw new IllegalArgumentException("Not the turn of this player");
        }

        // make move
        int[][] board = state.getBoard();
        board[row][col] = player;

        // update state
        TttState next;
        if (TttState.isWinner(board, state.getTurn())) {
            next = new TttState(state, board, true, state.getTurn());
        } else if (TttState.isDraw(board)) {
            next = new TttState(state, board, true, -1);
        } else {
            next = new TttState(state, board, false, -1);
        }
        return next;
    }
    /**
     * Devuelve una fila
     * @return row fila
     */
    public int getRow() {
        return row;
    }
    /**
     * Devuelve una columna
     * @return col columna
     */
    public int getCol() {
        return col;
    }
    /**
     * Devuelve un mensaje con informacion de la jugada
     * @return informacion
     */
    public String toString() {
        return "place " + player + " at (" + row + ", " + col + ")";
    }

}
