package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.GameTable;

public class GUIController <S extends GameState<S, A>, A extends GameAction<S, A>> {

	private GameTable<S, A> modelo;
	private int jugador;
	
	/**
	 * Constructora del controlador de la version grafica
	 * @param modelo
	 * @param jugador
	 */
	public GUIController(GameTable<S, A> modelo, int jugador) {
		
		this.modelo = modelo;
		this.jugador = jugador;
		
	}
	
	/**
	 * Realiza un movimiento
	 * @param action movimiento a realizar
	 */
	public void makeMove(A action) {
		
		// Hay que comprobar que es el turno del jugador
		if(this.modelo.getState().getTurn() == this.jugador) {
			
			this.modelo.execute(action);
			
		}
		
	}
	
	/**
	 * Para el juego
	 */
	public void stopGame() {
		
		this.modelo.stop();
		
	}
	
	/**
	 * Inicia una partida nueva
	 */
	public void startGame() {
		
		this.modelo.start();
		
	}
	
}
