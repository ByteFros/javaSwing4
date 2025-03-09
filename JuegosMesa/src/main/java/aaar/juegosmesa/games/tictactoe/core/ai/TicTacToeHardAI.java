/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aaar.juegosmesa.games.tictactoe.core.ai;

import aaar.juegosmesa.games.tictactoe.core.TicTacToeGame;

/**
 *
 * @author rmartin
 */
public class TicTacToeHardAI extends TicTacToeAI {
    public TicTacToeHardAI(TicTacToeGame parentGame) {
        super(parentGame);
    }
    
    public boolean playTurn() {
        if (isGameOver()) return false;
        logToConsole();
        
        return playBestMove();
    }
    
    /**
     * Modo HARD: usa el algoritmo minimax para buscar la mejor jugada.
     */
    private boolean playBestMove() {
        final int size = parentGame.getBoardSize();
        
        System.out.println("IA: Buscando el mejor movimiento (minimax)...");
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (parentGame.isCellEmpty(row, col)) {
                    parentGame.setCell('O', row, col);
                    int score = minimax(false);
                    parentGame.clearCell(row, col);
                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = row;
                        bestCol = col;
                    }
                }
            }
        }
        parentGame.setCell('O', bestRow, bestCol);
        System.out.println("IA: Mejor movimiento en (" + bestRow + ", " + bestCol + ")");
        return true;
    }

    /**
     * Algoritmo minimax para evaluar el tablero.
     * @param isMaximizing true si es turno de la IA, false si es turno del jugador.
     * @return puntuación del tablero.
     */
    private int minimax(boolean isMaximizing) {
        if (parentGame.isBoardFull()) return 0;
        if (parentGame.checkForWin()) return isMaximizing ? -1 : 1;
        final int size = parentGame.getBoardSize();
        
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (parentGame.isCellEmpty(row, col)) {
                    parentGame.setCell(
                            isMaximizing ? 'O' : 'X',
                            row, col
                    );
                    int score = minimax(!isMaximizing);
                    parentGame.clearCell(row, col);
                    bestScore = isMaximizing
                            ? Math.max(score, bestScore)
                            : Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }
}
