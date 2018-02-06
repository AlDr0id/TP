package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import es.ucm.fdi.tp.base.Utils;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;


public class Buttons extends JPanel {

	private GUIController controlador;
	private GamePlayer randPlayer;
	private GamePlayer smartPlayer;
	private GameView grande;
	int filas = 1;
	int columnas = 3;

	int columnasB = 4;// Botones

	int columnasM = 2;// Mode
	 final static int MANUAL = 0;
	 final static int RANDOM = 1;
	 final static int SMART = 2;
	
	JComboBox<String> opciones;
	JLabel lblTexto = new JLabel("");
	
	/**
	 * Constructora de la barra de botones
	 * @param controlador
	 * @param gP1 posible jugador (rand o smart)
	 * @param gP2 posible jugador (rand o smart)
	 */
	public Buttons(GUIController controlador, GamePlayer gP1, GamePlayer gP2) {
		
		this.controlador = controlador;
		this.randPlayer = gP1;
		this.smartPlayer = gP2;
		
		this.setLayout(new GridLayout(filas,columnas,3,3)); // La barra de arriba se divide en 3 zonas
		
		JPanel Botones = new JPanel();// Primera zona: una de botones propiamente
		Botones.setLayout(new GridLayout(filas,columnasB,3,3));
		
		// Creamos el boton de jugada aleatoria
		JButton boton1 = creaBotonRand();
		boton1.setIcon(new ImageIcon(Utils.loadImage("0.png")));
		boton1.setPreferredSize(new Dimension(40, 40));
		Botones.add(boton1);
		
		// Creamos el boton de jugada inteligente
		JButton boton2 = creaBotonSmart();
		boton2.setIcon(new ImageIcon(Utils.loadImage("1.png")));
		boton2.setPreferredSize(new Dimension(40, 40));
		Botones.add(boton2);
		
		// Creamos el boton de reiniciar partida
		JButton boton3 = creaBotonRestart();
		boton3.setIcon(new ImageIcon(Utils.loadImage("2.png")));
		boton3.setPreferredSize(new Dimension(40, 40));
		Botones.add(boton3);
		
		// Creamos el boton de apagar
		JButton boton4 = creaBotonApagar();
		boton4.setIcon(new ImageIcon(Utils.loadImage("3.png")));
		boton4.setPreferredSize(new Dimension(40, 40));
		Botones.add(boton4);
		
		this.add(Botones);
		
		JPanel playerMode = new JPanel();// Segunda zona: Otra de modos de juego
		playerMode.setLayout(new GridLayout(filas,columnasM,3,3));
		
		JLabel lbl = new JLabel("Player Mode");
		lbl.setFont(new Font("Dialog",Font.PLAIN,15));
		lbl.setBackground(Color.lightGray);
		lbl.setOpaque(true);
		playerMode.add(lbl);
		
		// Opciones de tipo de jugador
		String[] valores = {"Manual", "Smart", "Random"};
		
		opciones = new JComboBox<String>(valores);
		
		opciones.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				lblTexto.setText((String)opciones.getSelectedItem());// El metodo get cambia.
				if( !grande.getState1().isFinished() && grande.getState1().getTurn() == grande.getPlayerId()){
					if(mode()== RANDOM)
						jugadaRand();
					else if(mode() == SMART)
						jugadaSmart();
				}
			}
		});
		
		playerMode.add(opciones);
		
		this.add(playerMode);
		JPanel vacio = new JPanel();// Tercera zona: Una en blanco;
		this.add(vacio);
		
		
	}
	
	/**
	 * Devuelve el modo de jugador elegido
	 * @return modo de jugador
	 */
	public int mode() {// 0 manual, 1 rand, 2 smart
		int a = -1;
		if(((String)opciones.getSelectedItem()).equalsIgnoreCase("Manual"))
			a = MANUAL;
		else if(((String)opciones.getSelectedItem()).equalsIgnoreCase("Random"))
			a = RANDOM;
		else if(((String)opciones.getSelectedItem()).equalsIgnoreCase("Smart"))
			a = SMART;
		return a;
	}
	
	/**
	 * Devuelve un boton para realizar una jugada aleatoria
	 * @return boton de jugada aleatoria
	 */
	private JButton creaBotonRand() {
		
		JButton boton = new JButton();
		
		boton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
						
				if(!grande.getState1().isFinished() && grande.getState1().getTurn() == grande.getPlayerId() && mode() == MANUAL) {
					
					jugadaRand();
					
				}
					
			}
				});
		return boton;
	}
	
	/**
	 * Devuelve un boton para realizar una jugada inteligente
	 * @return boton de jugada inteligente
	 */
	private JButton creaBotonSmart() {
		
		JButton boton = new JButton();
		
		boton.addActionListener(new ActionListener() {
					
			public void actionPerformed(ActionEvent e) {
						
				if(!grande.getState1().isFinished() && grande.getState1().getTurn() == grande.getPlayerId() && mode() == MANUAL) { 
					jugadaSmart();
					
				}
					
			}
		});
		return boton;
	}
	
	/**
	 * Devuelve un boton para reiniciar una partida
	 * @return boton de reiniciar partida
	 */
	private JButton creaBotonRestart() {
	
		JButton boton = new JButton();
		
		boton.addActionListener(new ActionListener() {
					
					
			public void actionPerformed(ActionEvent e) {
				
				controlador.startGame();
			}
		});
		return boton;
	}
	
	/**
	 * Devuelve un boton para apagar
	 * @return boton de apagar
	 */
	private JButton creaBotonApagar() {
		
		JButton boton = new JButton();
				
		boton.addActionListener(new ActionListener() {
							
			public void actionPerformed(ActionEvent e) {
				
				int res = JOptionPane.showConfirmDialog(
						Buttons.this, 
						"¿Seguro que quieres salir?", 
						"Salir",  
						JOptionPane.OK_CANCEL_OPTION, 
						JOptionPane.QUESTION_MESSAGE);
				
				// Si quiere salir
				if(res == 0) {
				
					System.exit(0);
				}
				
			}
		});
		return boton;
	}
	
	/**
	 * Da valor a la vista para poder usarla desde aqui
	 * @param grande vista
	 */
	public void setGrande(GameView grande){
		this.grande = grande;
	}
	
	/**
	 * Hace una jugada aleatoria
	 */
	public void jugadaRand(){
		GameAction action = randPlayer.requestAction(grande.getState1());
		
		grande.mostrar("Has hecho una jugada aleatoria\n");
		
		controlador.makeMove(action);
	}
	
	/**
	 * Hace una jugada inteligente
	 */
	public void jugadaSmart(){
		GameAction action = smartPlayer.requestAction(grande.getState1());
		
		grande.mostrar("Has hecho una jugada inteligente\n");
		
		controlador.makeMove(action);
	
	}
	
}
