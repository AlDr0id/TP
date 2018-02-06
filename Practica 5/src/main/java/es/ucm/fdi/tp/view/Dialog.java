package es.ucm.fdi.tp.view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.extra.jcolor.ColorChooser;


public class Dialog extends JPanel{

	private int filas = 4;
	private int columnas = 1;
	private GameView grande;
	
	private JTextArea textArea;
	private PlayerInfo info;
	
	/**
	 * Constructora del panel de la parte de la derecha
	 */
	public Dialog() {
		this.setLayout(new GridLayout(filas,columnas,0,0));
		
		//Creamos etiqueta
		JLabel status = new JLabel("Status Messages");
		status.setHorizontalAlignment(JLabel.CENTER);
		this.add(status);
		
		// Creamos el dialogo
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);

		// Creamos el scroll
		JScrollPane scroll = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER/*AS_NEEDED*/);

		// Le damos un area preferida al scroll
		scroll.setPreferredSize(new Dimension(250, 200));
		this.add(scroll);
		
		// Creamos etiqueta
		JLabel pInfo = new JLabel("Player Information");
		pInfo.setHorizontalAlignment(JLabel.CENTER);
		this.add(pInfo);
		
		this.info = new PlayerInfo();
		
		// Ya no es necesario al hacerlo desde PlayerInfo
		/*info.setLayout(new GridLayout(3,2,1,1));
		
		JLabel player = new JLabel("#Player"); // Jugadores
		//player.setBackground(Color.lightGray);
		player.setOpaque(true);
		info.add(player);
		
		JLabel color = new JLabel("Color"); // Colores
		//color.setBackground(Color.lightGray);
		color.setOpaque(true);
		info.add(color);
		
		JLabel jug0 = new JLabel("0"); // jugador 0
		jug0.setOpaque(true);
		info.add(jug0);
		
		JLabel color0 = new JLabel("Color 0"); // Color 0
		// Cambiar por un recuadro del color que corresponde
		color0.setOpaque(true);
		info.add(color0);
		
		JLabel jug1 = new JLabel("1"); // jugador 1
		jug1.setOpaque(true);
		info.add(jug1);
		
		JLabel color1 = new JLabel("Color 1"); // Color 1
		// Cambiar por un recuadro del color que corresponde
		color1.setOpaque(true);
		info.add(color1);*/
		
		
		this.add(info);
		
	}
	
	/**
	 * Agrega informacion al cuadro de mensajes
	 * @param m mensaje
	 */
	public void addContent(String m) {
		
		this.textArea.append(m);
		
	}
	
	/**
	 * Resetea el cuadro de mensajes y agrega un mensaje
	 * @param m mensaje
	 */
	public void setContent(String m) {
		
		this.textArea.setText(m);
		
	}
	
	/**
	 * Permite usar la vista desde aqui al darle el valor que tiene
	 * @param grande vista
	 */
	public void setGrande(GameView grande){
		this.grande = grande;
	}

	/**
	 * Devuelve la informacion del jugador
	 * @return informacion
	 */
	public PlayerInfo getInfo() {
		return info;
	}

	public  PlayerInfo getPlayerInfo() {
		// TODO Auto-generated method stub
		return this.info;
	}
	
}
