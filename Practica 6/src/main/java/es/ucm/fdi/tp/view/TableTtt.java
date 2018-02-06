package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;


public class TableTtt extends Table {
	
	int numOfRows = 3;
	int numOfCols = 3;
	private GameView grande;
	// Tablero
	private GameState tablero;
	private int jugador;
    
	GUIController controlador;
    
	/**
	 * Constructora del tablero del juego Tres en raya
	 * @param controlador
	 * @param player jugador
	 * @param tablero
	 */
    public TableTtt(GUIController controlador, int player, GameState tablero) {
    	
    	this.jugador = player;
    	
    	this.tablero = tablero;
        
        this.controlador = controlador;
        
    }

	@Override
	/**
	 * Realiza una accion en la celda dada por fila y columna
	 * @param row fila dada
	 * @param col columna dada
	 * @param clickCount numero de clicks hechos
	 * @param mouseButton boton con el que se ha hecho el click
	 */
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		
		
		if(!tablero.isFinished()){
		
			controlador.makeMove(new TttAction(this.jugador, row, col));
		}
	}

	@Override
	/**
	 * Muestra la tecla pulsada
	 * @param keyCode tecla pulsada
	 */
	protected void keyTyped(int keyCode) {
		//System.out.println("Key " + keyCode + " pressed .."); // Lo quitamos porque en la parte grafica no se mira la consola
		// Si se quiere usar descomentar y ya
	}

	@Override
	/**
	 * Devuelve la forma correspondiente a ese jugador
	 * @param player jugador
	 * @return forma
	 */
	protected Shape getShape(int player) {
			return Shape.CIRCLE;
	}

	@Override
	/**
	 * Devuelve lo que se encuentra en la posicion dada
	 * @param row fila dada
	 * @param col columna dada
	 * @return valor
	 */
	protected Integer getPosition(int row, int col) {
		return this.getPositionAux(row, col);
	}

	@Override
	/**
	 * Devuelve el numero de filas
	 * @return numero de filas
	 */
	protected int getNumRows() {
		return numOfRows;
	}

	@Override
	/**
	 * Devuelve el numero de columnas
	 * @return numero de columnas
	 */
	protected int getNumCols() {
		return numOfCols;
	}

	@Override
	/**
	 * Devuelve el color asociado a ese jugador
	 * @return color
	 */
	protected Color getColor(int player) {
		return this.getColorAux(player);
	}

	@Override
	/**
	 * Devuelve el color del fondo
	 * @return color
	 */
	protected Color getBackground(int row, int col) {
		return Color.LIGHT_GRAY;

		// use this for 2 chess like board
		//return (row+col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
	}

	@Override
	protected int getSepPixels() {
		return 1; // put to 0 if you don't want a separator between
					// cells
	}
	
	/**
	 * Devuelve lo que se encuentra en la posicion dada
	 * @param row fila dada
	 * @param col columna dada
	 * @return valor o null
	 */
	protected Integer getPositionAux(int row, int col) {
		if(((TttState) this.tablero).getBoard()[row][col] >= 0)
			return ((TttState) this.tablero).getBoard()[row][col];
		else
			return null;
	}
	
	/**
	 * Devuelve el color asociado al jugador
	 * @param player
	 * @return color
	 */
	protected Color getColorAux(int player) {
		//return player == 0 ? Color.BLUE : Color.RED;
		
		if(player == 0)
			return grande.getPanelDialogo().getInfo().colorFicha(player);
		else if (player == 1)
			return grande.getPanelDialogo().getInfo().colorFicha(player);
		else return null;
	}

	@Override
	/**
	 * Actualiza el estado del juego
	 * @param state estado nuevo
	 */
	public void update(GameState state) {
		this.tablero = state;
		repaint();
		
	}
	
	/**
	 * Da valor a la vista para poder usarla desde aqui
	 * @param grande vista de la parte grafica
	 */
	public void setGrande(GameView grande){
		this.grande = grande;
	}
	
	/**
	 * Devuelve el estado actual
	 * @return estado
	 */
	public GameState getState(){
		return this.tablero;
	}

	@Override
	protected Image ponerImagen(int row, int col) {
		return null;
	}
	
}