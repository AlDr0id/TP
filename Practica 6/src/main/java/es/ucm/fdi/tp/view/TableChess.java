package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import es.ucm.fdi.tp.base.Utils;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.chess.ChessAction;
import es.ucm.fdi.tp.chess.ChessBoard;
import es.ucm.fdi.tp.chess.ChessAction.Special;
import es.ucm.fdi.tp.chess.ChessBoard.Piece;
import es.ucm.fdi.tp.chess.ChessState;
import es.ucm.fdi.tp.view.Table.Shape;
import es.ucm.fdi.tp.was.WolfAndSheepAction;


public class TableChess extends Table {
	
	int numOfRows = 8;
	int numOfCols = 8;
	// Tablero
	private ChessState tablero;
	private GameView grande;
   
	private int jugador;
	
	private int click1Row;
	private int click1Col;
	//private int click2Row;
	//private int click2Col;
	
	private GUIController controlador;
	
	public TableChess (GUIController controlador, int player, GameState tablero) {
		
		this.jugador = player;
    	
    	this.tablero = (ChessState) tablero;
 
        this.controlador = controlador;
        
        this.click1Col = -1;
		this.click1Row = -1;
	}

	@Override
	public void setGrande(GameView grande) {
		this.grande = grande;
		
	}

	@Override
	public GameState getState() {

		return this.tablero;
	}

	@Override
	protected void keyTyped(int keyCode) {
		//System.out.println("Key " + keyCode + " pressed ..");
				// Lo quitamos porque en la parte grafica no se mira la consola
				// Si se quiere usar descomentar y ya
				
				this.click1Col = -1;
				this.click1Row = -1;
				
				grande.mostrar("Ninguna ficha seleccionada\n");
		
	}

	@Override
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		if(!tablero.isFinished() && tablero.getTurn() == jugador){// Quitar isFinished si se quiere 
			// que salte excepcion	
			
				if(mouseButton == 1) {
				
					// Si == -1 significa que no hay ficha seleccionada
					if(this.click1Col == -1) {
						if(getPosition(row, col) == Integer.valueOf(jugador)) {
							this.click1Col = col;
							this.click1Row = row;
							grande.mostrar("Has seleccionado " + Piece.valueOf(tablero.getBoard().get(row, col)) + " en la casilla " + row + " " + col + "\n");
							// Aqui poner el setBorder(BorderFactory.createLineBorder(Color.black));
						}
					}
					else {
						int i = 0;
						boolean encontrado = false;
						ChessAction accion = new ChessAction(this.jugador, this.click1Row, this.click1Col, row, col);
					
						while(i < tablero.validActions(jugador).size() && !encontrado) {
							if(tablero.validActions(jugador).get(i).getDstCol() == col &&
							tablero.validActions(jugador).get(i).getDstRow() == row &&
							tablero.validActions(jugador).get(i).getSrcCol() == this.click1Col &&
							tablero.validActions(jugador).get(i).getSrcRow() == this.click1Row) {
								accion = tablero.validActions(jugador).get(i);
								encontrado = true;
							}
							i++;
						}
						
						if(Piece.valueOf(tablero.getBoard().get(this.click1Row, this.click1Col)) == Piece.Pawn && 
								(row == 0 || row == 7)) {
							String[] optionsAmostrar = new String[]{
									"Reina", "Torre", "Alfil", "Caballo"};
							
							Special[] options = new Special[] {
									Special.QueenQ, Special.QueenR, Special.QueenB, Special.QueenN };
							
							int res = JOptionPane.showOptionDialog(TableChess.this, "Elige ficha", "Elige",
												JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
												null, optionsAmostrar, optionsAmostrar[0]);
							Special special;
							if(res == JOptionPane.CLOSED_OPTION)// Si cancela se le pone reina
								special = options [0];
							else 
								special = options[res];
							
							accion = new ChessAction(this.jugador, this.click1Row, this.click1Col, row, col, special);
						}
							
						
						
						this.click1Col = -1;
						this.click1Row = -1;

						controlador.makeMove(accion);
						
					}
				}
				else {
					this.click1Col = -1;
					this.click1Row = -1;
					
					grande.mostrar("Ninguna ficha seleccionada\n");
				}
				
			}
		
	}

	@Override
	protected Shape getShape(int player) {
		return Shape.CIRCLE;
	}

	@Override
	protected Color getColor(int player) {
		return this.getColorAux(player);
	}
	
	protected Color getColorAux(int player) {
		//return player == 0 ? Color.BLUE : Color.RED;
		
		if(player == 0)
			return grande.getPanelDialogo().getInfo().colorFicha(player);
		else if (player == 1)
			return grande.getPanelDialogo().getInfo().colorFicha(player);
		else return null;
	}

	@Override
	// Tiene que devolver Integer porque Table necesita un Integer
	protected Integer getPosition(int row, int col) {
		if(((ChessState) this.tablero).getBoard().white(((ChessState) this.tablero).getBoard().get(row, col)))
			return 0;
		else {
			if(((ChessState) this.tablero).getBoard().black(((ChessState) this.tablero).getBoard().get(row, col)))
				return 1;
			else
				return null;
		}
	}

	/*protected int getPosition2(int row, int col) {
		if(((ChessState) this.tablero).getBoard().white(((ChessState) this.tablero).getBoard().get(row, col)))
			return 0;
		else {
			if(((ChessState) this.tablero).getBoard().black(((ChessState) this.tablero).getBoard().get(row, col)))
				return 1;
			else
				return null;
		}
	}*/

	@Override
	protected Color getBackground(int row, int col) {
		return (row+col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
	}

	@Override
	protected int getNumRows() {
		return numOfRows;
	}

	@Override
	protected int getNumCols() {
		return numOfCols;
	}

	@Override
	public void update(GameState state) {
		this.tablero = (ChessState) state;
		repaint();
		if(tablero.isInCheck() && !tablero.isFinished()) {
				grande.mostrar("Jaque\n");
			
		}
		
	}

	@Override
	protected Image ponerImagen(int row, int col) {
		
		return new ImageIcon("src/main/resources/chess/" + ChessBoard.Piece.iconName(this.tablero.getBoard().get(row, col))).getImage();
		
	}

}
