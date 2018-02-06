package es.ucm.fdi.tp.base.player;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * A simple MinMax implementation.
 * Adapted from code by Simon Pickin
 */
public class MinMax implements AiAlgorithm {

    private static final Logger log = Logger.getLogger(MinMax.class.getName());

    /**
     * Interface for evaluation counters
     */
    public interface EvaluationCounter {
        /**
         * Will be called at each leaf evaluation.
         * Should be implemented in a thread-safe manner if a multithreaded
         * min-max is going to be used.
         */
        void increment();
    }
	// Profundidad del algoritmo
    private int depth;
	
    private EvaluationCounter evaluationCounter;

	/**
     * Constructora para crear un nuevo algoritmo MinMax
     */
    public MinMax() {
        this(5);
    }

    public MinMax(int depth) {
        this(depth, null);
    }

	/**
     * Constructora para crear un nuevo algoritmo MinMax con parametro
     * @param depth profundidad del algoritmo
	 * @param evaluationCounter contador
     */
    public MinMax(int depth, EvaluationCounter evaluationCounter) {
        // Minima profundidad
		if (depth < 1) {
            throw new IllegalArgumentException(
                    "Invalid depth ('" + depth + "') for the MinMax algorithm, expected > 0");
        }
        this.depth = depth;
        this.evaluationCounter = evaluationCounter;
    }

	/**
     * Representa un nodo de los movimientos
     * @param <S>
     * @param <A>
     */
    public static class Node<S extends GameState<S,A>, A extends GameAction<S,A>>
            implements Comparable<Node<S, A>> {
        private A move;
        private double value;
		/**
         * Da valor al nodo
         * @param move movimiento a realizar
         * @param value valor estimado
         */
        public Node(A move, double value) {
            this.move = move;
            this.value = value;
        }
        public A getMove() {
            return move;
        }
        public double getValue() {
            return value;
        }

        @Override
        public int compareTo(Node<S, A> o) {
            return Double.compare(value, o.getValue());
        }

        public String toString() {
            return "" + move + " = " + value;
        }
    };

	/**
     * Elige la accion a realizar
     * @param playerNumber numero del jugador
     * @param state estado del juego
     * @return nuevo estado
     */
    @Override
    public <S extends GameState<S,A>, A extends GameAction<S,A>> A chooseAction(int playerNumber, S state) {
        Node<S, A> best = chooseNode(playerNumber, state);

        if (best != null) {
            return best.move;
        } else {
            List<A> valid = state.validActions(playerNumber);
            return valid.get(new Random().nextInt(valid.size()));
        }
    }

    public <S extends GameState<S,A>, A extends GameAction<S,A>> Node<S,A> chooseNode(int playerNumber, S state) {
        try {
            Node<S,A> best = minmax(depth,
                    Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                    playerNumber, state);
            return best;
        } catch (InterruptedException ie) {
            log.fine("Interrupted while thinking!");
            return null;
        }
    }

	/**
     * Evalua el final del juego
     * @param state estado actual
     * @param d
     * @param playerNumber numero del jugador
     * @return valor determinado
     */
    private double evaluateFinished(GameState<?,?> state, int d, int playerNumber) {
        double v = state.evaluate(playerNumber);
        if (state.isFinished()) {
            // if winning, try to do it sooner; if losing, try to drag it later
            v *= 1.5 * (d+1);
        }
        if (evaluationCounter != null) {
            evaluationCounter.increment();
        }
        return v;
    }

	/**
     * Algoritmo minmax
     * @param d
     * @param alpha maximo
     * @param beta minimo
     * @param playerNumber numero del jugador
     * @param state estado actual
     * @return Nodo definido
     * @throws InterruptedException
     */
    private <S extends GameState<S,A>, A extends GameAction<S,A>> Node<S,A> minmax(int d, double alpha, double beta, int playerNumber, S state)
            throws InterruptedException {
    	
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        if (state.isFinished() || d < 1) {
            // finished or depth reached: evaluate state
            return new Node<S,A>(null, evaluateFinished(state, d, playerNumber));
        }

        // generate all moves
		List<A> actions = state.validActions(state.getTurn());
        assert (actions.size() > 0);

        // max is playerNumber, min are all other players
        if (playerNumber == state.getTurn()) {
            // return better than current best (alpha)
            return max(d, alpha, beta, playerNumber, state, actions);
        } else {
            // return only worse than current worst (beta)
            return min(d, alpha, beta, playerNumber, state, actions);
        }
    }
    
	/**
     * Maximo
     * @param d
     * @param alpha
     * @param beta
     * @param player jugador
     * @param state estado actual
     * @param actions acciones posibles
     * @return Nodo maximo
     * @throws InterruptedException
     */
    private <S extends GameState<S,A>, A extends GameAction<S,A>> Node<S,A> max(int d, double alpha, double beta, int player, S state,
                     List<A> actions) throws InterruptedException {

        A chosen = null;
        for (A a : actions) {
            try {
                S next = a.applyTo(state);
                Node<S,A> result = minmax(d - 1, alpha, beta, player, next);
                if (result.value > alpha) {
                    alpha = result.value;
                    chosen = a;
                }
                if (alpha >= beta) {
                    return new Node<>(chosen, alpha);
                }
            } catch (IllegalStateException ise) {
                throw new IllegalStateException(
                        "applying " + a + " to \n" + state
                        + " actions are " + actions, ise);
            }
        }

        return new Node<>(chosen, alpha);
    }

	/**
     * Minimo
     * @param d
     * @param alpha
     * @param beta
     * @param player jugador
     * @param state estado actual
     * @param actions acciones posibles
     * @return Nodo minimo
     * @throws InterruptedException
     */
    private <S extends GameState<S,A>, A extends GameAction<S,A>> Node<S,A> min(int d, double alpha, double beta, int player, S state,
                     List<A> actions) throws InterruptedException {

        A chosen = null;
        for (A a : actions) {
            try {
                S next = a.applyTo(state);
                Node<S,A> result = minmax(d - 1, alpha, beta, player, next);
                if (result.value < beta) {
                    beta = result.value;
                    chosen = a;
                }
                if (beta <= alpha) {
                    return new Node<>(chosen, beta);
                }
            } catch (IllegalStateException ise) {
                throw new IllegalStateException(
                        "applying " + a + " to \n" + state
                        + " actions are " + actions, ise);
            }
        }

        return new Node<>(chosen, beta);
    }
}
