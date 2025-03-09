/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aaar.juegosmesa.games.tictactoe.core;

import aaar.juegosmesa.games.shared.GameDifficulty;
import static aaar.juegosmesa.games.shared.GameDifficulty.*;
import aaar.juegosmesa.games.tictactoe.core.ai.TicTacToeAI;
import aaar.juegosmesa.games.tictactoe.core.ai.TicTacToeEasyAI;
import aaar.juegosmesa.games.tictactoe.core.ai.TicTacToeHardAI;
import aaar.juegosmesa.games.tictactoe.core.ai.TicTacToeMediumAI;

/**
 *
 * @author debi12
 */
public class TicTacToeGame {
    private final char[][] board;
    private boolean isXTurn = true;                             // Indica si es el turno del jugador
    private GameDifficulty difficulty = GameDifficulty.MEDIUM;  // "easy", "medium" o "hard"
    private TicTacToeAI aiPlayer;
    
    /* CONSTRUCTORS */
    private TicTacToeGame() {
        this.board = new char[3][3];
    }
    
    public static TicTacToeGame getInstance(
            GameDifficulty difficulty
    ) {
        TicTacToeGame game = new TicTacToeGame();
        game.setDifficulty(difficulty);
        game.initializeAI();
        return game;
    }
    
    /* DIFFICULTY */
    public GameDifficulty getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(GameDifficulty difficulty) {
        if (difficulty != null) this.difficulty = difficulty;
    }
    
    private void initializeAI() {
        this.aiPlayer = switch (difficulty) {
            case HARD -> new TicTacToeHardAI(this);
            case EASY -> new TicTacToeEasyAI(this);
            default -> new TicTacToeMediumAI(this);
        };
    }
    
    /* BOARD SIZE */
    public int getBoardSize() {
        return getBoardRowCount();
    }
    public int getBoardColumnCount() {
        return this.board[0].length;
    }
    public int getBoardRowCount() {
        return this.board.length;
    }
    
    /* turn methods */
    public boolean isXTurn() {
        return isXTurn;
    }
    public boolean startNextTurn() {
        isXTurn = !isXTurn;
        return isXTurn();
    }
    
    
    /* cell methods */
    public char getCell(int row, int column) {
        return board[row][column];
    }    
    public char setCell(char c, int row, int column) {
        board[row][column] = c;
        return c;
    }
    public char clearCell(int row, int column) {
        char c = getCell(row, column);
        setCell('\0', row, column);
        return c;
    }
    public boolean isCellEmpty(int row, int column) {
        return (int)getCell(row,column) == 0;
    }
    
    // Verificar si el juego ya terminó (victoria o empate)
    public boolean isGameOver() {
        return isBoardFull() || checkForWin();
    }
    
    // Verificar si hay una victoria (filas, columnas, diagonales)
    public boolean checkForWin() {
        // Verificar filas y columnas
        final int size = getBoardSize();
        for (int i = 0; i < size; i++) {
            // get topmost cell of column i
            final char top = getCell(0,i);
            // get leftmost cell of row i
            final char left = getCell(i,0);
            
            boolean isFullRow = (int)left != 0;
            boolean isFullColumn = (int)top != 0;
            for (int j = 1; (isFullRow || isFullColumn) && (j < size); j++) {
                isFullRow &= left == getCell(i,j);
                isFullColumn &= top == getCell(j,i);
            }
            if (isFullRow || isFullColumn) return true;
        }
        
        // Verificar diagonales
        final int lastIndex = size - 1;
        final char tl = getCell(0,0);
        final char bl = getCell(lastIndex,0);
        boolean diagonalLineAsc = (int)bl != 0;
        boolean diagonalLineDesc = (int)tl != 0;
        for (int i = 1; (diagonalLineAsc || diagonalLineDesc) && i < size; i++) {
            diagonalLineAsc &= getCell(lastIndex-i, i) == bl;
            diagonalLineDesc &= getCell(i,i) == tl;
        }
        return diagonalLineAsc || diagonalLineDesc;
    }
    
    // verificando si hay una victoria en la celda especificada.
    public boolean checkForWin(int row, int column) {
        final char checkedSymbol = getCell(row, column);
        if ((int)checkedSymbol == 0) return false;
        
        // row line check
        boolean rowLine = true;
        for (int c = 0; rowLine && (c < getBoardColumnCount()); c++) {
            rowLine = getCell(row,c) == checkedSymbol;
        }
        if (rowLine) return true;
        
        // column line check
        boolean columnLine = true;
        for (int r = 0; columnLine && (r < getBoardRowCount()); r++) {
            columnLine = getCell(r,column) == checkedSymbol;
        }
        if (columnLine) return true;
        
        // diagonals check
        final int size = getBoardSize();
        final int centerIndex = size/2;
        boolean diagonalLineAsc = getCell(centerIndex,centerIndex) == checkedSymbol;
        boolean diagonalLineDesc = diagonalLineAsc;
        for (int r = 0; (diagonalLineAsc || diagonalLineDesc) && (r < size); r++) {
            diagonalLineAsc = getCell(size-1-r,r) == checkedSymbol;
            diagonalLineDesc = getCell(r,r) == checkedSymbol;
        }
        return (diagonalLineAsc || diagonalLineDesc);
    }
    
    public boolean isBoardFull() {
        int rowCount = getBoardRowCount();
        int colCount = getBoardColumnCount();
        int cellCount = rowCount * colCount;
        for (int ci = 0; (ci < cellCount); ci++) {
            if (isCellEmpty(
                    ci / colCount,  // row
                    ci % colCount   // column
            )) return false;
        }
        return true;
    }
    
    public void playAI() {
        aiPlayer.playTurn();
    }
    
    /**
     * Reinicia el tablero.
     */
    public void resetBoard() {
        for (int row = 0; row < getBoardRowCount(); row++) {
            for (int col = 0; col < getBoardColumnCount(); col++) {
                setCell('\0', row,col); // replace this cell with a null character..
            }
        }
    }
    /**
     * Reinicia el tablero y asigna el turno al jugador.
     */
    public void reset() {
        resetBoard();
        isXTurn = true;
    }
    
}
