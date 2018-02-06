package es.ucm.fdi.tp.was;

import static org.junit.Assert.*;

import org.junit.Test;


public class WolfAndSheepStateTest {
	
	 final static int EMPTY = -1;
	 final static int ILEGAL = -2;
	 final static int LOBO = 1;
	 final static int OVEJA = 0;

	 @Test   
	public void testIsWinner(){
		int[][] board = new int[8][8];
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++){
            	if((i + j) % 2 == 0) board[i][j] = ILEGAL;
            	else board[i][j] = EMPTY;
            }
        }
        
        for (int i = 1;i < 8; i +=2)
        	board[0][i] = OVEJA;
        board[7][0] = LOBO;
        
        
        

        
        //Estado inicial sin ganador
        
		assertEquals(WolfAndSheepState.isWinner(board, LOBO), false);
		
		
		//Lobo gana al quedarse las ovejas sin movimientos
		for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++){
            	if((i + j) % 2 == 0) board[i][j] = ILEGAL;
            	else board[i][j] = EMPTY;
            }
        }
        for(int k = 0; k < 4; k++) board[7][2 * k + 1] = OVEJA;
        board[5][0] = LOBO;
        assertEquals(WolfAndSheepState.isWinner(board, LOBO), true);
        
        
        
        //Lobo gana al llegar a la fila 0
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++){
            	if((i + j) % 2 == 0) board[i][j] = ILEGAL;
            	else board[i][j] = EMPTY;
            }
        }
        for(int k = 0; k < 4; k++) board[5][2 * k + 1] = OVEJA;
        board[0][1] = LOBO;
        assertEquals(WolfAndSheepState.isWinner(board, LOBO), true);
        
        //Ovejas ganan al encerrar al Lobo y dejarle sin movimientos
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++){
            	if((i + j) % 2 == 0) board[i][j] = ILEGAL;
            	else board[i][j] = EMPTY;
            }
        }
        for(int k = 0; k < 3; k++) board[0][2 * k + 1] = OVEJA;
        board[7][0] = LOBO;
        board[6][1] = OVEJA;
        assertEquals(WolfAndSheepState.isWinner(board, OVEJA), true);
        
        //Falso que alguien haya ganado
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++){
            	if((i + j) % 2 == 0) board[i][j] = ILEGAL;
            	else board[i][j] = EMPTY;
            }
        }
        for(int k = 0; k < 4; k++) board[2][2 * k + 1] = OVEJA;
        board[3][0] = LOBO;
        assertNotSame(WolfAndSheepState.isWinner(board, OVEJA), true);
        assertNotSame(WolfAndSheepState.isWinner(board, LOBO), true);
        //assertNotSame(WolfAndSheepState.isWinner(board, LOBO), false); si el código está bien debe dar failure
	}
	

	

}
