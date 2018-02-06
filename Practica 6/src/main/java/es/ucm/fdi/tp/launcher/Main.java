package es.ucm.fdi.tp.launcher;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.ConcurrentAiPlayer;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.chess.ChessBoard;
import es.ucm.fdi.tp.chess.ChessState;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.ConsoleController;
import es.ucm.fdi.tp.view.ConsoleView;
import es.ucm.fdi.tp.view.GUIController;
import es.ucm.fdi.tp.view.GameView;
import es.ucm.fdi.tp.view.Table;
import es.ucm.fdi.tp.view.TableChess;
import es.ucm.fdi.tp.view.TableTtt;
import es.ucm.fdi.tp.view.TableWAS;
import es.ucm.fdi.tp.was.WolfAndSheepState;

/**
 * 
 * Clase principal del juego
 *
 */
public class Main {
	/**
	 * Crea un nuevo juego del tipo especificado
	 * @param gType juego elegido
	 * @return nuevo juego
	 */
	private static GameTable<?, ?> createGame(String gType) {
		// create a game with a GameState depending on the value of gType
		if (gType.equalsIgnoreCase("TTT")){
			return new GameTable(new TttState(3));
		}else if (gType.equalsIgnoreCase("WAS")){
			return new GameTable(new WolfAndSheepState(8));
		}else if(gType.equalsIgnoreCase("CHESS")){
			return new GameTable(new ChessState());
		}else
			throw new GameError ("Error: Juego no valido");
	}
	
	/**
	 * Inicia el juego en modo consola
	 * @param gType juego elegido
	 * @param game juego inicializado
	 * @param playerModes modo de los jugadores
	 */
	private static <S extends GameState<S, A>, A extends GameAction<S,
	A>>
	void startConsoleMode(String gType, GameTable<S, A> game, String playerModes[]) {
		// create the lis of players as in assignemnt 4
		// ...
		
			List<GamePlayer> players = new ArrayList<GamePlayer>();
			GamePlayer jugador;
			for (int i = 0; i < playerModes.length; i++){ 
			jugador = Main.createPlayer(gType, playerModes[i], "Jugador" + (i+1)); //Creas los jugadores
			players.add(jugador);// y los metes en la lista
			}
		
		new ConsoleView<S,A>(game);
		new ConsoleController<S,A>(players,game).run();
	}
	
	/**
	 * Crea un jugador para la partida
	 * @param gameName nombre del juego
	 * @param playerType tipo de jugador
	 * @param playerName nombre del jugador
	 * @return jugador creado
	 * @throws GameError
	 */
	public static GamePlayer createPlayer(String gameName, String playerType, String playerName)throws GameError {
		if(playerType.equalsIgnoreCase("MANUAL")){
			
			return new ConsolePlayer(playerName, new Scanner(System.in));
			
		}else if(playerType.equalsIgnoreCase("RANDOM")){
			
			return new RandomPlayer(playerName);
			
		}else if(playerType.equalsIgnoreCase("SMART")){
			
			return new ConcurrentAiPlayer(playerName);
			
		}else
			throw new GameError ("Error: jugador "+ playerType + " no definido");
	}
	
	/**
	 * Inicia el juego en modo grafico
	 * @param gType tipo de juego
	 * @param game juego inicializado
	 * @param playerModes modo de los jugadores
	 */
	private static <S extends GameState<S, A>, A extends GameAction<S,
	A>> void startGUIMode(String gType, GameTable<S, A> game, String playerModes[]) {
		// add code here
		/*for ( int i = 0; i < game.getState().getPlayerCount(); i++) {// Se supone que es mejor, pero peta
			GamePlayer p1;
			GamePlayer p2;
			p1 = createPlayer(gType, "RANDOM", "Jugador " + i);
			p2 = createPlayer(gType, "SMART", "Jugador "+i);
			
			// Se unen en la constructora de GameView
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						GUIController<S, A> gameCtrl = new GUIController<S, A>(game, i);
						Table tablero;
						if (gType.equalsIgnoreCase("TTT")){
							tablero = new TableTtt(controlador, i,game.getState());
							
						}else if (gType.equalsIgnoreCase("WAS")){
							tablero = new TableWAS(controlador, i, game.getState());
							
						}else
							throw new GameError ("Error: Juego no valido");
						GameView<S,A> guiView = new GameView(0, p1, p2,
								tablero, gameCtrl);
						game.addObserver(guiView);
						
						}
				});
			} catch (InvocationTargetException | InterruptedException e) {
			System.err.println("Some error occurred while creating the view ...");
			System.exit(1);
			}
			}
			SwingUtilities.invokeLater(new Runnable() { public void run() { game.start(); } });*/
		
		for ( int i = 0; i < game.getState().getPlayerCount(); i++) {
			GUIController<S, A> controlador1 = new GUIController<S, A>(game, i);
			//GUIController<S, A> controlador2 = new GUIController<S, A>(game, 1);
			// Posible solucion
			Table tablero1/*, tablero2*/;
			if (gType.equalsIgnoreCase("TTT")){
				tablero1 = new TableTtt(controlador1, i,game.getState());
				//tablero2 = new TableTtt(controlador2, 1,game.getState());
			}else if (gType.equalsIgnoreCase("WAS")){
				tablero1 = new TableWAS(controlador1, i, game.getState());
				//tablero2 = new TableWAS(controlador2, 1, game.getState());
			} else if (gType.equalsIgnoreCase("CHESS")) {
				tablero1 = new TableChess(controlador1, i, game.getState());
			}else
				throw new GameError ("Error: Juego no valido");
			//
			GameView vista1 = new GameView(i, createPlayer(gType, "RANDOM", "Jugador "+i), (ConcurrentAiPlayer)createPlayer(gType, "SMART", "Jugador "+i),
					tablero1, controlador1);
			
			//GameView vista2 = new GameView(1, createPlayer(gType, "RANDOM", "Jugador 1"), createPlayer(gType, "SMART", "Jugador 1"),
			//		tablero2, controlador2);
			
			
			//game.addObserver(vista2);
			game.addObserver(vista1);
		}
		
		game.start(); // Si no, el estado esta a null
		
	}
	
	/**
	 * Muestra la ayuda de como invocar los juegos
	 */
	private static void usage() {
		
		String ayuda = "Uso: << Main [juego] [modo de juego] [jugador 1] [jugador 2] >>";
		
		System.out.println(ayuda);
	}
	
	/**
	 * Funcion principal
	 * @param args argumentos elegidos
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			usage();
			System.exit(1);
		}
		GameTable<?, ?> game = createGame(args[0]);
		if (game == null) {
			System.err.println("Invalid game");
			usage();
			System.exit(1);
		}
		String[] otherArgs = Arrays.copyOfRange(args, 2, args.length);
		switch (args[1]) {
		case "console":
			startConsoleMode(args[0],game,otherArgs);
			break;
		case "gui":
			startGUIMode(args[0],game,otherArgs);
			break;
		default:
			System.err.println("Invalid view mode: "+args[1]);
			usage();
			System.exit(1);
		}
	}
	
}
