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
public class TicTacToeMediumAI extends TicTacToeEasyAI {
    public TicTacToeMediumAI(TicTacToeGame parentGame) {
        super(parentGame);
    }
    
    public boolean playTurn() {
        if (isGameOver()) return false;
        logToConsole();
        
        // Primero, buscar jugada ganadora para la IA (O)
        if (playWinningMove('O')) return true;
        // Luego, intentar bloquear la jugada ganadora del jugador (simulando "X")
        if (playWinningMove('X')) return true;
        // Si no hay jugada ganadora ni bloqueo, hacer movimiento aleatorio
        return playRandomMove();
    }
    
    /**
     * Modo MEDIUM:
     * Si se pasa "O", busca una jugada que le haga ganar a la IA.
     * Si se pasa "X", simula la jugada del jugador y, si detecta que esa jugada ganaría,
     * la revierte y coloca una "O" para bloquear.
     */
    protected boolean playWinningMove(char symbol) {
        System.out.println("IA: Buscando movimiento ganador para '" + symbol + "'...");
        final int size = parentGame.getBoardSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (parentGame.isCellEmpty(row, col)) {
                    // Simular la jugada
                    parentGame.setCell(symbol, row, col);
                    if (parentGame.checkForWin()) {
                        if (symbol == 'X') {
                            // Bloquear: revertir el "X" y colocar "O"
                            parentGame.setCell('O',row,col);
                            System.out.println("IA: Bloqueo jugada ganadora del jugador en (" + row + ", " + col + ")");
                        } else {
                            System.out.println("IA: Jugada ganadora para O en (" + row + ", " + col + ")");
                        }
                        return true;
                    } else {
                        // Revertir la jugada simulada
                        parentGame.setCell('\0', row, col);
                    }
                }
            }
        }
        System.out.println("IA: No se encontraron movimientos ganadores para " + symbol);
        return false;
    }
}
