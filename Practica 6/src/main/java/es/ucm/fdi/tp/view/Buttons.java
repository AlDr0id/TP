package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;










import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.ucm.fdi.tp.base.Utils;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.player.ConcurrentAiPlayer;


public class Buttons extends JPanel {

	private GUIController controlador;
	private GamePlayer randPlayer;
	private ConcurrentAiPlayer smartPlayer;
	private GameView grande;
	private MiHilo hilo;
	private JLabel cerebro;
	
	int filas = 1;
	int columnas = 2;

	int columnasB = 4;// Botones

	int columnasSmart = 7;
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
	public Buttons(GUIController controlador, GamePlayer gP1, ConcurrentAiPlayer gP2) {
		
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
		
		JPanel izq = new JPanel(); // mitad izquierda
		izq.setLayout(new GridLayout(filas, columnas,3,3));
		
		izq.add(Botones);
		
		JPanel playerMode = new JPanel();// Segunda zona: Otra de modos de juego
		playerMode.setLayout(new GridLayout(filas,columnasM,3,3));
		
		JLabel lbl = new JLabel("Player Mode");
		lbl.setFont(new Font("Dialog",Font.PLAIN,12));
		//lbl.setBackground(Color.lightGray);
		lbl.setOpaque(true);
		playerMode.add(lbl);
		
		// Opciones de tipo de jugador
		String[] valores = {"Manual", "Smart", "Random"};
		
		opciones = new JComboBox<String>(valores);
		
		opciones.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				lblTexto.setText((String)opciones.getSelectedItem());// El metodo get cambia.
				if(!grande.isCorriendo() && !grande.getState1().isFinished() && grande.getState1().getTurn() == grande.getPlayerId()){
					if(mode()== RANDOM)
						jugadaRand();
					else if(mode() == SMART)
						jugadaSmart();
				}
			}
		});
		
		playerMode.add(opciones);
		
		izq.add(playerMode);
		
		this.add(izq);
		
		
		JPanel panelSmart = new JPanel();// Zona Smart Moves
		panelSmart.setLayout(new GridLayout(filas,columnasSmart,3,3));
		panelSmart.setBorder(BorderFactory.createTitledBorder("Smart Moves"));
		
		// Imagen cerebro
		this.cerebro = new JLabel(new ImageIcon(Utils.loadImage("brain.png")));
		this.cerebro.setOpaque(false);
		this.cerebro.setBackground(Color.yellow);
		panelSmart.add(this.cerebro);
		
		// Spinner threads
		JSpinner spThreads = creaSpinnerThread();
		panelSmart.add(spThreads);
		
		JLabel lbltThreads = new JLabel("threads");
		lbltThreads.setFont(new Font("Dialog",Font.PLAIN,12));
		lbltThreads.setOpaque(true);
		panelSmart.add(lbltThreads);
		// Imagen cronometro
		JLabel jlC = new JLabel(new ImageIcon(Utils.loadImage("timer.png")));
		panelSmart.add(jlC);
		// Spinner cronometro
		JSpinner spTimer = creaSpinnerTimer();
		panelSmart.add(spTimer);
		
		JLabel lbltTimer = new JLabel("ms.");
		lbltTimer.setFont(new Font("Dialog",Font.PLAIN,12));
		lbltTimer.setOpaque(true);
		panelSmart.add(lbltTimer);
		
		// Boton stop
		JButton botonStop = creaBotonStop();
		
		panelSmart.add(botonStop);
		
		
		this.add(panelSmart);
		
		
	}
	
	/**
	 * Crea el Spinner de hilos a usar
	 * @return Spinner creado
	 */
	private JSpinner creaSpinnerThread() {
		
				// Modelo con valor inicial, minimo, maximo y de cuanto en cuanto va
		SpinnerModel model =
		        new SpinnerNumberModel(1, 1, Runtime.getRuntime
		        		().availableProcessors(), 1);
				// Creacion del JSpinner.
				 JSpinner spinner = new JSpinner(model);
				
				// Nos suscribimos a cambios en el JSpinner
				spinner.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						// Actualizar hilos
						smartPlayer.setMaxThreads((int) spinner.getValue());
						
						// hay que llamar a esa funcion del concurrent player y pasarle como valor el valor actual
						//del spinner
					}
				
				});
			return spinner;
	}
	
	/**
	 * Crea el Spinner del tiempo a emplear
	 * @return Spinner creado
	 */
	private JSpinner creaSpinnerTimer() {
		
		// Modelo con valor inicial, minimo, maximo y de cuanto en cuanto va
		SpinnerModel model =
		        new SpinnerNumberModel(1000, 500, 5000, 500);
				// Creacion del JSpinner.
				JSpinner spinner = new JSpinner(model);
				
				// Nos suscribimos a cambios en el JSpinner
				spinner.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						// Actualizar hilos
						smartPlayer.setTimeout((int) spinner.getValue());
					}
				
				});
				
			return spinner;
	}
	
	/**
	 * Boton que para la ejecucion del hilo
	 * @return boton creado
	 */
	public JButton creaBotonStop() {
		
		JButton boton = new JButton();
		
		boton.setIcon(new ImageIcon(Utils.loadImage("stop.png")));
		
		boton.addActionListener(new ActionListener() {
					
			public void actionPerformed(ActionEvent e) {
					
				if(grande.isCorriendo()){ // Si el hilo existe y esta corriendose
						hilo.interrupt(); // Se interrumpe
						grande.setCorriendo(false); // Se cambia un flag interno de GameView
						opciones.setSelectedItem("Manual"); // Se cambia el modo a Manual
				}
				else 
					grande.mostrar("No hay ningun movimiento que parar\n"); // Si no hay hilo o no se esta corriendo
			}
		});
		return boton;
	}
	
	/**
	 * Para usar el ComboBox desde otros lugares
	 * @return ComboBox
	 */
	public JComboBox<String> getOpciones() {
		return opciones;
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
						
				if(!grande.isCorriendo() && !grande.getState1().isFinished() && grande.getState1().getTurn() == grande.getPlayerId() && mode() == MANUAL) {
					
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
						
				if(!grande.isCorriendo() && !grande.getState1().isFinished() && grande.getState1().getTurn() == grande.getPlayerId() && mode() == MANUAL) { 
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
		
		// crear nuevo hilo
		this.hilo = new MiHilo(this.smartPlayer, this.grande, this.controlador);
		grande.setMiHilo(this.hilo);
		this.cambiarCerebro(true);
		this.hilo.start();
	
	}

	/**
	 * Cambia el color del cerebro
	 * @param ok si se ve o no
	 */
	public void cambiarCerebro(boolean ok) {
		this.cerebro.setOpaque(ok); // Siempre es amarillo, pero segun el valor de ok se ve o no
		repaint();
		
	}
	
	/**
	 * Da valor al hilo
	 * @param hilo
	 */
	public void setHilo(MiHilo hilo){
		this.hilo = hilo;
	}
	
}
