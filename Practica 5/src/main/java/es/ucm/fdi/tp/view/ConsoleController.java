package es.ucm.fdi.tp.view;

import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameTable;

public class ConsoleController<S extends GameState<S,A>, A extends GameAction<S,A>> implements Runnable {

	private List<GamePlayer> players;
	private GameTable<S,A> game;
	
	/**
	 * Constructora del controlador para modo consola
	 * @param players jugadores
	 * @param game juego
	 */
	public ConsoleController(List<GamePlayer> players, GameTable<S,A> game) {

		this.game = game;
		this.players = players;
	}
	
	/**
	 * Bucle de juego
	 */
	public void run() {

		int playerCount = 0;
		for (GamePlayer p : this.players) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
		this.game.start();
		@SuppressWarnings("unchecked")
		S currentState = this.game.getState();
		//System.out.println(currentState);

		while (!currentState.isFinished()) {
			// request move
			A action = this.players.get(currentState.getTurn()).requestAction(currentState);
			// apply move
			this.game.execute(action);
			currentState =  this.game.getState();
			
			//System.out.println("After action:\n" + currentState);// MOSTRAR TABLERO

			/*if (currentState.isFinished()) { // Ya se muestra con los eventos asi que lo comentamos sin llegar a quitarlo
				// game over
				String endText = "The game ended: ";
				int winner = currentState.getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + this.players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
			}*/
		}
	}
}
