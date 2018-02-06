package es.ucm.fdi.tp.launcher;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WolfAndSheepState;

/**
 * 
 * Clase principal del juego
 *
 */
public class Main {

	/**
	 * Metodo principal
	 * @param args
	 */
	public static void main(/*String[] args*/ String... args) {

		try{
		GameState<?, ?> initialState = Main.createInitialState(args[0]);// Intenta crear un juego
		if(initialState.getPlayerCount()== args.length-1){ // Si coinciden los argumentos
			List<GamePlayer> lista = new ArrayList<GamePlayer>();
			GamePlayer jugador;
			for (int i = 0; i < initialState.getPlayerCount(); i++){ 
			jugador = Main.createPlayer(args[0], args[i+1], "Jugador" + i+1); //Creas los jugadores
			lista.add(jugador);// y los metes en la lista
			}
			Main.playGame(initialState, lista);
		}
		else throw new GameError("Error: No coincide el numero de jugdores"); // Si no error
		}catch(GameError a){System.err.println(a.getMessage());}
	}
	
	// gameName puede ser cualquiera de las abreviaciones de los juegos
	/**
	 * Crea un nuevo estado
	 * @param gameName nombre del juego
	 * @return nuevo estado
	 * @throws GameError
	 */
	public static GameState<?, ?> createInitialState(String gameName) throws GameError {
		if (gameName.equalsIgnoreCase("TTT")){
			return new TttState(3);
		}else if (gameName.equalsIgnoreCase("WAS")){
			return new WolfAndSheepState(8);
		}else
			throw new GameError ("Error: Juego no valido");
	}
	// gameName como antes
	// playerType puede ser cualquiera de los tipos de jugador
	/**
	 * Crea un jugador
	 * @param gameName nombre del juego
	 * @param playerType tipo de jugador
	 * @param playerName nombre del jugador
	 * @return nuevo jugador
	 * @throws GameError
	 */
	public static GamePlayer createPlayer(String gameName, String playerType, String playerName)throws GameError {
		if(playerType.equalsIgnoreCase("CONSOLE")){
			
			return new ConsolePlayer(playerName, new Scanner(System.in));
			
		}else if(playerType.equalsIgnoreCase("RAND")){
			
			return new RandomPlayer(playerName);
			
		}else if(playerType.equalsIgnoreCase("SMART")){
			
			return new SmartPlayer(playerName, 5);
			
		}else
			throw new GameError ("Error: jugador "+ playerType + " no definido");
	}
	
	/**
	 * Juega un juego
	 * @param initialState estado inicial
	 * @param players jugadores
	 * @return ganador
	 */
	public static <S extends GameState<S, A>, A extends GameAction<S, A>> int playGame(GameState<S, A> initialState,
			List<GamePlayer> players) {
		int playerCount = 0;
		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
		@SuppressWarnings("unchecked")
		S currentState = (S) initialState;

		while (!currentState.isFinished()) {
			// request move
			A action = players.get(currentState.getTurn()).requestAction(currentState);
			// apply move
			currentState = action.applyTo(currentState);
			System.out.println("After action:\n" + currentState);

			if (currentState.isFinished()) {
				// game over
				String endText = "The game ended: ";
				int winner = currentState.getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
			}
		}
		return currentState.getWinner();
	}
	
}
