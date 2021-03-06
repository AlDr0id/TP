package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

public abstract class Table<S extends GameState<S, A>, A extends GameAction<S, A>> extends JComponent {

	//private static final long serialVersionUID = -4518722262994516431L;

	private int _CELL_HEIGHT = 50;
	private int _CELL_WIDTH = 50;
	private int _SEPARATOR = -2;

	public enum Shape {
		CIRCLE, RECTANGLE
	}

	/**
	 * Constructora del tablero
	 */
	public Table() {
		initGUI();
	}
	
	public abstract void update(S state);
	
	/**
	 * Repinta el tablero
	 */
	public void repintar(){
		repaint();
	}
	
	
	public abstract void setGrande(GameView grande);
	
	public abstract GameState getState();

	/**
	 * Inicia la parte grafica
	 */
	private void initGUI() {
		setBorder(BorderFactory.createRaisedBevelBorder());

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				Table.this.keyTyped(e.getExtendedKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Table.this.requestFocus();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int col = (e.getX() / _CELL_WIDTH);
				int row = (e.getY() / _CELL_HEIGHT);

				int mouseButton = 0;

				if (SwingUtilities.isLeftMouseButton(e))
					mouseButton = 1;
				else if (SwingUtilities.isMiddleMouseButton(e))
					mouseButton = 2;
				else if (SwingUtilities.isRightMouseButton(e))
					mouseButton = 3;

				if (mouseButton == 0)
					return; // Unknown button, don't know if it is possible!

				Table.this.mouseClicked(row, col, e.getClickCount(), mouseButton);
			}
		});
		
		_SEPARATOR = getSepPixels();
		if ( _SEPARATOR < 0 ) _SEPARATOR = 0;

		this.setPreferredSize(new Dimension(400, 400));
		repaint();
	}

	/**
	 * Pinta el componente dado
	 * @param g componente a pintar
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		fillBoard(g);
	}

	/**
	 * Rellena el tablero
	 * @param g elemento grafico
	 */
	private void fillBoard(Graphics g) {
		int numCols = getNumCols();
		int numRows = getNumRows();

		if (numCols <= 0 || numRows <= 0) {
			g.setColor(Color.red);
			g.drawString("Waiting for game to start!", 20, this.getHeight() / 2);
			return;
		}

		_CELL_WIDTH = this.getWidth() / numCols;
		_CELL_HEIGHT = this.getHeight() / numRows;

		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numCols; j++)
				drawCell(i, j, g);
	}

	/**
	 * Rellena la celda dada por fila y columna
	 * @param row fila de la celda
	 * @param col columna de la celda
	 * @param g elemento grafico
	 */
	private void drawCell(int row, int col, Graphics g) {
		int x = col * _CELL_WIDTH;
		int y = row * _CELL_HEIGHT;

		g.setColor( getBackground(row, col));
		g.fillRect(x + _SEPARATOR, y + _SEPARATOR, _CELL_WIDTH - 2*_SEPARATOR, _CELL_HEIGHT - 2*_SEPARATOR);

		Integer p = getPosition(row, col);

		if (p != null) {
			Color c = getColor(p);
			Shape s = getShape(p);

			g.setColor(c);

			switch (s) {
			case CIRCLE:
				g.fillOval(x + _SEPARATOR+2, y + _SEPARATOR+2, _CELL_WIDTH - 2*_SEPARATOR-4, _CELL_HEIGHT - 2*_SEPARATOR-4);
				g.setColor(Color.black);
				g.drawOval(x + _SEPARATOR+2, y + _SEPARATOR+2, _CELL_WIDTH - 2*_SEPARATOR-4, _CELL_HEIGHT - 2*_SEPARATOR-4);
				break;
			case RECTANGLE:
				g.fillRect(x + _SEPARATOR+2, y + _SEPARATOR+2, _CELL_WIDTH - 2*_SEPARATOR-4, _CELL_HEIGHT - 2*_SEPARATOR-4);
				g.setColor(Color.black);
				g.drawRect(x + _SEPARATOR+2, y + _SEPARATOR+2, _CELL_WIDTH - 2*_SEPARATOR-4, _CELL_HEIGHT - 2*_SEPARATOR-4);
				break;
			default:
				break;

			}
			
			Image image = ponerImagen(row, col);
			if(image != null)
				g.drawImage(image ,x + _SEPARATOR+2, y + _SEPARATOR+2, _CELL_WIDTH - 2*_SEPARATOR-4, _CELL_HEIGHT - 2*_SEPARATOR-4, null);
		}


	}

	protected abstract Image ponerImagen(int row, int col);		

	protected abstract void keyTyped(int keyCode);

	protected abstract void mouseClicked(int row, int col, int clickCount, int mouseButton);

	protected abstract Shape getShape(int player);

	protected abstract Color getColor(int player);

	protected abstract Integer getPosition(int row, int col);

	protected abstract Color getBackground(int row, int col);

	protected abstract int getNumRows();

	protected abstract int getNumCols();
	
	protected int getSepPixels() {
		return 2;
	}

}