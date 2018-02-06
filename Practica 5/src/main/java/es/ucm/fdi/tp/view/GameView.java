package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;

public class GameView <S extends GameState<S, A>, A extends GameAction<S, A>>
extends JFrame implements GameObserver<S, A> {

	private int playerId;
	private GamePlayer randPlayer;
	private GamePlayer smartPlayer;
	
	private int rowElegida;
	private int colElegida;
	
	private GUIController<S, A> gameCtrl;
	
	// Componentes
	private Dialog panelDialogo;
	private Table<S, A> gameView; // Tablero
	private Buttons panelBotones;
	
	/**
	 * Constructora de la vista para el modo grafico
	 * @param playerId
	 * @param randPlayer
	 * @param smartPlayer
	 * @param tablero
	 * @param gameCtrl controlador
	 */
	public GameView (int playerId, GamePlayer randPlayer, GamePlayer smartPlayer,
					Table<S, A> tablero, GUIController<S, A> gameCtrl) {
		
	
			super(tablero.getState().getGameDescription() + " Jugador "+ playerId);
			
			this.gameCtrl = gameCtrl;
			this.playerId = playerId;
			this.randPlayer = randPlayer;
			this.smartPlayer = smartPlayer;
			this.gameView = tablero;
			this.randPlayer = randPlayer;
			this.smartPlayer = smartPlayer;
			
			this.randPlayer.join(this.playerId);
			this.smartPlayer.join(this.playerId);
			
			this.setSize(/*this.getPreferredSize()*/750, 580);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			this.getContentPane().setLayout(new BorderLayout());
			
			
			// Region norte
			this.panelBotones = new Buttons(this.gameCtrl, this.randPlayer, this.smartPlayer);
			this.getContentPane().add(panelBotones,BorderLayout.NORTH);			
	        
	        //Region este
	        this.panelDialogo = new Dialog();
			this.add(panelDialogo ,BorderLayout.EAST);
			
			// Region central
	        this.add(tablero, BorderLayout.CENTER);
			
			// Hacer visible la ventana del juego
			this.setVisible(true);
			
			
			this.panelDialogo.setGrande(this);
			this.panelBotones.setGrande(this);
			this.gameView.setGrande(this);
			this.getPanelDialogo().getPlayerInfo().setGrande(this);
		}
	
	@Override
	/**
	 * Notifica los eventos que ocurran
	 * @param e evento
	 */
	public void notifyEvent(final GameEvent<S, A> e) {
		
		// Sacado de las transparencias de Jesus Correas
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				gameView.update(e.getState());
				
				if(e.getType() == EventType.Start)// Si se ha empezado se limpia texto
					reiniciarTexto("");
				if(e.getType() != EventType.Change)// Muestra mensajes distintos a movimientos (el juego ha empezado...)
					panelDialogo.addContent(e.toString());
				if(e.getType() != EventType.Stop)// Si no se ha parado se indica el turno del jugador
				{
					if(!e.getState().isFinished()){
						if( e.getState().getTurn() == playerId){
							panelDialogo.addContent("Es tu turno.\n");
							
							if(mode() == 1){// RAND
								A action = (A) randPlayer.requestAction(getState1());// Se supone que A es un GameAction
							// Pero si ponemos del tipo GameAction no nos deja invocar a makeMove
								mostrar("Has hecho una jugada aleatoria\n");
							
								gameCtrl.makeMove(action);
							}
							else if(mode() == 2){// SMART
								A  action = (A) smartPlayer.requestAction(getState1());
								
								mostrar("Has hecho una jugada inteligente\n");
								
								gameCtrl.makeMove(action);
							
							}
						}
					else 
						panelDialogo.addContent("Es el turno del jugador " + e.getState().getTurn()+ "\n");
					}
				}
			}
			});
		
	}
	
	/**
	 * Devuelve la fila elegida
	 * @return fila elegida
	 */
	public int getRowElegida() {
		return this.rowElegida;
	}

	/**
	 * Devuelve la columna elegida
	 * @return columna elegida
	 */
	public int getColElegida() {
		return this.colElegida;
	}
	
	/**
	 * Llama a la funcion que agrega mensajes al cuadro de notificaciones
	 * @param s mensaje a agregar
	 */
	public void mostrar(String s){
		this.panelDialogo.addContent(s);
	}
	
	/**
	 * Llama a la funcion que resetea y agrega un mensaje al cuadro de notificaciones
	 * @param s mensaje a agregar
	 */
	public void reiniciarTexto(String s)
	{
		this.panelDialogo.setContent(s);
	}
	
	/**
	 * Devuelve el estado actual
	 * @return estado
	 */
	public GameState getState1(){// Hemos tenido problemas porque pensaba que hacia override de Frame
		// Por eso hemos puesto getState1
		return this.gameView.getState();
	}
	
	/**
	 * Devuelve el modo de juego actual
	 * @return modo
	 */
	public int mode(){
		return this.panelBotones.mode();
	}

	/**
	 * Devuelve el numero de jugador actual
	 * @return numero de jugador
	 */
	public int getPlayerId() {
		
		return this.playerId;
	}

	/**
	 * Devuelve el panel de la parte derecha
	 * @return panel
	 */
	public Dialog getPanelDialogo() {
		return panelDialogo;
	}
	
	/**
	 * Devuelve el tablero de juego actual
	 * @return tablero
	 */
	public Table getTable(){
		return this.gameView;
	}
	
}
