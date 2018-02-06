package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.extra.jcolor.ColorChooser;

public class PlayerInfo extends JPanel {
	
	private MyTableModel tModel;
	private Map<Integer, Color> colors;
	private ColorChooser colorChooser;
	
	private GameView grande;
	
	private Color color[];
	private Random random;

	/**
	 * Constructora de la parte de informacion de jugadores
	 */
	public PlayerInfo() {
		
			// Creas la informacion de los jugadores		
			this.setPreferredSize(new Dimension(300, 100));
		
			this.colors = new HashMap<>();
			this.colorChooser = new ColorChooser(new JFrame(), "Choose Line Color", Color.BLACK);
			
			this.tModel = new MyTableModel();
			this.tModel.getRowCount();
			final JTable table = new JTable(this.tModel) {
				// THIS IS HOW WE CHANGE THE COLOR OF EACH ROW
				@Override
				public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
					Component comp = super.prepareRenderer(renderer, row, col);

					// the color of row 'row' is taken from the colors table, if
					// 'null' setBackground will use the parent component color.
					if (col == 1){
						/*if(row == 1) {// Poner colores iniciales
							
							colors.put(row, Color.BLUE);
							
						}
						else {
							
							colors.put(row, Color.RED);
							
						}*/	// Ya no lo usamos
						comp.setBackground(colors.get(row));
						}
					else
						comp.setBackground(Color.WHITE);
						
					comp.setForeground(Color.BLACK);
					
					return comp;
				}
				
				
			};
			
			// Array para poder elegir colores aleatorios mas adelante
			this.color = new Color[8];
			color[0] = Color.RED;
			color[1] = Color.BLUE;
			color[2] = Color.YELLOW;
			color[3] = Color.ORANGE;
			color[4] = Color.PINK;
			color[5] = Color.GREEN;
			color[6] = Color.CYAN;
			color[7] = Color.MAGENTA;
			
			// Primer color aleatorio
			this.random = new Random();
			int rand1 = Math.abs(random.nextInt()%8);
			colors.put(0, color[rand1]);
			
			// Segundo color aleatorio
			rand1 = Math.abs(random.nextInt()%8);
			colors.put(1, color[rand1]);
			
			table.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					int row = table.rowAtPoint(evt.getPoint());
					int col = table.columnAtPoint(evt.getPoint());
					if (row >= 0 && col >= 0) {
						changeColor(row);
					}
				}

			});
			
			JScrollPane scroll = new JScrollPane(table);
			scroll.setPreferredSize(new Dimension(300, 100));
			
			this.add(scroll, BorderLayout.CENTER);
			JPanel ctrlPabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			this.add(ctrlPabel, BorderLayout.PAGE_START);
	
	}
	
	/**
	 * Cambia el color del jugador en la tabla y en el tablero
	 * @param row fila del jugador en la tabla
	 */
	private void changeColor(int row) {
		colorChooser.setSelectedColorDialog(colors.get(row));
		colorChooser.openDialog();
		if (colorChooser.getColor() != null) {
			colors.put(row, colorChooser.getColor());
			repaint();
			this.grande.repintaTablero();
		}
	}
	
	/**
	 * Devuelve el color de la ficha del jugador correspondiente a la fila dada
	 * @param row fila del jugador en la tabla
	 * @return color
	 */
	public Color colorFicha(int row){
		return this.colors.get(row);
	}
	
	/**
	 * Da valor a la vista grafica para poder usarla desde aqui
	 * @param grande vista de la parte grafica
	 */
	public void setGrande(GameView grande){
		this.grande = grande;
	}
}
