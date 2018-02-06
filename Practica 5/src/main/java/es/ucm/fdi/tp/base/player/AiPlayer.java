package es.ucm.fdi.tp.base.player;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;

/**
 * A player that can play any game.
 */
public class AiPlayer implements GamePlayer {
	// Nombre del jugador
    protected String name;
    // Numero del jugador
    protected int playerNumber;
    // Algoritmo usado
    protected AiAlgorithm algorithm;

    /**
     * Constructora para crear un nuevo jugador inteligente
     * @param name nombre del jugador
     * @param algorithm algoritmo a usar
     */
    public AiPlayer(String name, AiAlgorithm algorithm) {
        this.name = name;
        this.algorithm = algorithm;
    }
    /**
     * Une el jugador con un numero de jugador
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
     * Elige accion a realizar
     * @param state estado actual
     * @return accion elegida
     */
	public <S extends GameState<S,A>, A extends GameAction<S,A>> A requestAction(S state) {
        return algorithm.chooseAction(playerNumber, state);
    }
}
