/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aaar.juegosmesa.logic.tictactoe;

/**
 *
 * @author debi12
 */
public class TicTacToeGame {
    private char[][] board = new char[3][3];
    private boolean isXTurn = true;
    
    public TicTacToeGame() {}
    
    public boolean isXTurn() {
        return isXTurn;
    }
    public boolean startNextTurn() {
        isXTurn = !isXTurn;
        return isXTurn();
    }
    
    public char getCell(int row, int column) {
        return board[row][column];
    }    
    public char setCell(char c, int row, int column) {
        board[row][column] = c;
        return c;
    }
    public boolean isCellEmpty(int row, int column) {
        return (int)getCell(row,column) == 0;
    }
    
    // Verificar si hay una victoria (filas, columnas, diagonales)
    public boolean checkForWin() {
        // Verificar filas y columnas
        for (int i = 0; i < 3; i++) {
            // get topmost cell of column i
            final char top = getCell(0,i);
            // get leftmost cell of row i
            final char left = getCell(i,0);
            
            boolean isFullRow = (int)left != 0;
            boolean isFullColumn = (int)top != 0;
            for (int j = 1; (isFullRow || isFullColumn) && (j < 3); j++) {
                isFullRow &= left == getCell(i,j);
                isFullColumn &= top == getCell(j,i);
            }
            if (isFullRow || isFullColumn) return true;
        }
        
        // Verificar diagonales
        final char tl = getCell(0,0);
        final char bl = getCell(2,0);
        boolean diagonalLineAsc = (int)bl != 0;
        boolean diagonalLineDesc = (int)tl != 0;
        for (int i = 1; (diagonalLineAsc || diagonalLineDesc) && i < 3; i++) {
            diagonalLineAsc &= getCell(2-i, i) == bl;
            diagonalLineDesc &= getCell(i,i) == tl;
        }
        return diagonalLineAsc || diagonalLineDesc;
    }
    
    public boolean checkForWin(int row, int column) {
        final char checkedSymbol = board[row][column];
        if ((int)checkedSymbol == 0) return false;
        
        boolean rowLine = true;
        for (int c = 0; rowLine && (c < board[0].length); c++) {
            rowLine = board[row][c] == checkedSymbol;
        }
        if (rowLine) return true;
        
        boolean columnLine = true;
        for (int r = 0; columnLine && (r < board.length); r++) {
            columnLine = board[r][column] == checkedSymbol;
        }
        if (columnLine) return true;
        
        boolean diagonalLineAsc = board[1][1] == checkedSymbol;
        boolean diagonalLineDesc = diagonalLineAsc;
        for (int r = 0; (diagonalLineAsc || diagonalLineDesc) && (r < board.length); r++) {
            diagonalLineAsc = board[2-r][r] == checkedSymbol;
            diagonalLineDesc = board[r][r] == checkedSymbol;
        }
        return (diagonalLineAsc || diagonalLineDesc);
    }
    
    // Verificar si el juego ya terminó (victoria o empate)
    public boolean isGameOver() {
        return isBoardFull() || checkForWin();
    }
    public boolean isBoardFull() {
        int rowCount = board.length;
        int colCount = board[0].length;
        int cellCount = rowCount * colCount;
        for (int ci = 0; (ci < cellCount); ci++) {
            char c = board[ci / colCount][ci % colCount];
            if ((int)c != 0) return false;
        }
        return true;
    }
    
    // Reiniciar el tablero
    public void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = '\0'; // replace this cell with a null character..
            }
        }
        isXTurn = true;
    }
}
