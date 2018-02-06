package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;

	
public class ConsoleView<S extends GameState<S,A>, A extends GameAction<S,A>> implements GameObserver<S,A> {
	
	/**
	 * Constructora de la vista para modo consola
	 * @param gameTable
	 */
	public ConsoleView(GameObservable<S,A> gameTable) {
		gameTable.addObserver(this);
	}

	@Override
	/**
	 * Notifica un eveto ocurrido
	 * @param e evento
	 */
	public void notifyEvent(GameEvent<S, A> e) {
		
		String mensaje = e.toString();

		if (e.getType() != EventType.Error)		
			System.out.println( mensaje + e.getState());// en casos normales mensaje y tablero.
		else
			System.out.println( e.getState() + mensaje);// para el mensaje final, no saldra after action:
		
	
	}
}