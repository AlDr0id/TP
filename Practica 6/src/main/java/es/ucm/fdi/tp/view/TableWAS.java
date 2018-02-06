package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;


public class TableWAS extends Table {
	
	int numOfRows = 8;
	int numOfCols = 8;
	// Tablero
	private GameState tablero;
	private GameView grande;
   
	private int jugador;
	
	private int click1Row;
	private int click1Col;
	//private int click2Row;
	//private int click2Col;
	
	private GUIController controlador;
    
	/**
	 * Constructora del tablero del juego WolfAndSheep
	 * @param controlador
	 * @param player jugador
	 * @param tablero
	 */
    public TableWAS(GUIController controlador, int player, GameState tablero) {
    	
    	this.jugador = player;
    	
    	this.tablero = tablero;
 
        this.controlador = controlador;
        
        this.click1Col = -1;
		this.click1Row = -1;
		//this.click2Col = -1;
		//this.click2Row = -1;
        
        
    }

	@Override
	/**
	 * Realiza una accion en la celda dada por fila y columna
	 * En este caso se llama a esta funcion dos veces para mover ficha
	 * @param row fila dada
	 * @param col columna dada
	 * @param clickCount numero de clicks hechos
	 * @param mouseButton boton con el que se ha hecho el click
	 */
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		
		if(!tablero.isFinished() && tablero.getTurn() == jugador){// Quitar isFinished si se quiere 
		// que salte excepcion	
		
			if(mouseButton == 1) {
			
				if(this.click1Col == -1) {
					if(getPosition(row, col) == Integer.valueOf(jugador)) {
						this.click1Col = col;
						this.click1Row = row;
						grande.mostrar("Has seleccionado la ficha " + row + " " + col + "\n");
						// Aqui poner el setBorder(BorderFactory.createLineBorder(Color.black));
					}
				}
				else {
					
					WolfAndSheepAction accion = new WolfAndSheepAction(this.jugador, this.click1Row, this.click1Col, row, col);
					this.click1Col = -1;
					this.click1Row = -1;
					
					controlador.makeMove(accion);
				}
			}
			else {
				this.click1Col = -1;
				this.click1Row = -1;
				
				grande.mostrar("Ninguna ficha seleccionada\n");
			}
			
		}		
		
	}

	@Override
	/**
	 * Muestra la tecla pulsada
	 * @param keyCode tecla pulsada
	 */
	protected void keyTyped(int keyCode) {
		//System.out.println("Key " + keyCode + " pressed ..");
		// Lo quitamos porque en la parte grafica no se mira la consola
		// Si se quiere usar descomentar y ya
		
		this.click1Col = -1;
		this.click1Row = -1;
		
		grande.mostrar("Ninguna ficha seleccionada\n");
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
		//return Color.LIGHT_GRAY;

		// use this for 2 chess like board
		return (row+col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
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
		if(((WolfAndSheepState) this.tablero).getBoard()[row][col] >= 0)
			return ((WolfAndSheepState) this.tablero).getBoard()[row][col];
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
