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
    
    // Verificar si hay una victoria (filas, columnas, diagonales)
    private boolean checkForWin() {
        // Verificar filas y columnas
        for (int i = 0; i < 3; i++) {
            final char top = board[0][i];
            final char left = board[i][0];
            
            boolean rowLine = (int)left != 0;
            boolean columnLine = (int)top != 0;
            for (int j = 1; (rowLine || columnLine) && (j < 3); j++) {
                rowLine = left == board[i][j];
                columnLine = top == board[j][i];
            }
            if (rowLine || columnLine) return true;
        }
        
        // Verificar diagonales
        final char tl = board[0][0];
        final char bl = board[2][0];
        boolean diagonalLineAsc = (int)tl != 0;
        boolean diagonalLineDesc = (int)bl != 0;
        for (int i = 1; (diagonalLineAsc || diagonalLineDesc) && i < 3; i++) {
            diagonalLineAsc = board[2-i][i] == bl;
            diagonalLineDesc = board[i][i] == tl;
        }
        return diagonalLineAsc || diagonalLineDesc;
    }
    
    private boolean checkForWin(int row, int column) {
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
}
