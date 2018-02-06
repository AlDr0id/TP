package es.ucm.fdi.tp.base.console;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;

/**
 * A console player
 * Can play any game, and offers console users a list of valid actions to choose from
 */
public class ConsolePlayer implements GamePlayer {
	
	// Numero del jugador
    private int playerNumber;
    // Nombre del jugador
    private String name;
    // Escaner para introducir datos al programa
    private Scanner in;	

    /**
     * Constructora con argumentos para crear un jugador de consola
     * @param name nombre del jugador
     * @param in entrada para el programa
     */
    public ConsolePlayer(String name, Scanner in) {
        this.name = name;
        this.in = in;
        this.playerNumber = -1;
    }
    /**
     * Da un numero de jugador a un jugador ya creado
     * @param playerNumber numero a asignar
     */
    @Override
    public void join(int playerNumber) {
        this.playerNumber = playerNumber;
    }
    /**
     * Devuelve el numero del jugador
     * @return playerNumber numero del jugador
     */
    @Override
    public int getPlayerNumber() {
    	return playerNumber;
    }
    
    /**
     * Devuelve el nombre del jugador
     * @return name nombre del jugador
     */
    @Override
    public String getName() {
        return name;
    }
    /**
     * Presenta las acciones disponibles
     * @param state estado actual
     * @return accion elegida
     */
	public <S extends GameState<S,A>, A extends GameAction<S,A>> A requestAction(S state) {

	    List<A> actions = new ArrayList<A>();
        actions.addAll(state.validActions(playerNumber));

    	// displays a menu with all available actions + exit
    	System.out.println(
                "Available actions: \n" +
                "\t0 - exit game");		
        int i = 0;
		for (GameAction<?,?> a : actions) {
			System.out.println("\t" + (++i) + " - " + a);
		}

		// read the user input and carry out action
        A action = null;
        while (action == null) {
            System.out.print("Please type your move index: ");
            try {
            	int choice = in.nextInt();

	            if (choice == 0) {
	                // user wants to exit
	                System.out.println("Game exiting by request of " + name);
	                System.exit(0);
	            } else if (choice > 0 && choice <= actions.size()) {
	                action = actions.get(choice - 1);
	            } else {
	                System.out.println(
	                		"Out of range (0 to " + actions.size() 
	                		+ "); please try again.");
	            }
            } catch (InputMismatchException ime) {
            	System.out.println("Expected an integer; please try again");
            }
        }
        return action;
	}
}
