package es.ucm.fdi.tp.base.player;

/**
 * A smart player.
 */
public class SmartPlayer extends AiPlayer {
	/**
	 * Constructora para crear un nuevo jugador listo
	 * @param name nombre del jugador
	 * @param depth profundidad del algoritmo MinMax a usar
	 */
    public SmartPlayer(String name, int depth) {
        super(name, new MinMax(depth));
    }
}
