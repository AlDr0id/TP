package es.ucm.fdi.tp.view;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.player.ConcurrentAiPlayer;

public class MiHilo extends Thread {
	private ConcurrentAiPlayer ia;
	private GameView estado;
	private GUIController controlador;
	
	public MiHilo(ConcurrentAiPlayer ia, GameView estado, GUIController controlador){
		this.ia = ia;
		this.estado = estado;
		this.controlador = controlador;
	}
	
	/**
	 * Funcion principal del hilo
	 */
	public void run() {
		
		double tiempoIni = System.currentTimeMillis();
		estado.setCorriendo(true);
		GameAction action = ia.requestAction(estado.getState1());
		double tiempo = System.currentTimeMillis() - tiempoIni;

			if(action != null) {		
				
			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					
					estado.mostrar("Has hecho una jugada inteligente con " + ia.getEvaluationCount() +
							" nodos en " + tiempo + " ms (" + ia.getEvaluationCount()/ tiempo +
							" n/ms) value = " + ia.getValue() + "\n");
					
					controlador.makeMove(action);
					estado.setCorriendo(false);
				}
				});
			
			}
			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					estado.cambiaCerebro(false);
				}
			});
		}
}
