/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aaar.juegosmesa.games.tictactoe.core.ai;

import aaar.juegosmesa.games.shared.GameDifficulty;
import aaar.juegosmesa.games.tictactoe.core.TicTacToeGame;

/**
 *
 * @author rmartin
 */
public abstract class TicTacToeAI {
    protected final TicTacToeGame parentGame;
    
    public TicTacToeAI(
        TicTacToeGame parentGame
    ) {
        this.parentGame = parentGame;
    }
    
    public boolean playTurn() {
        return logToConsole();
    }
    protected boolean logToConsole() {
        System.out.println("IA: Iniciando la IA...");
        
        if (isGameOver()) {
            System.out.println("IA: El juego ya terminó, no se puede jugar.");
            return false;
        }
        
        final GameDifficulty difficulty = parentGame.getDifficulty();
        System.out.println(
                "IA: Jugando en dificultad "
                + difficulty.toString().toUpperCase()
        );
        return true;
    }
    
    protected boolean isGameOver() {
        return parentGame.isGameOver();
    }
    /*
    public void playTurn() {
        System.out.println("IA: Iniciando la IA...");
        
        if (parentGame.checkForWin() || parentGame.isBoardFull()) {
            System.out.println("IA: El juego ya terminó, no se puede jugar.");
            return;
        }
        
        final GameDifficulty difficulty = parentGame.getDifficulty();
        System.out.println("IA: Jugando en dificultad " + difficulty.toString());
        
        
        switch (difficulty) {
            case HARD:
                playBestMove();
                break;
            case MEDIUM:
                // Primero, buscar jugada ganadora para la IA (O)
                if (!playWinningMove('O')) {
                    // Luego, intentar bloquear la jugada ganadora del jugador (simulando "X")
                    if (!playWinningMove('X')) {
                        // Si no hay jugada ganadora ni bloqueo, hacer movimiento aleatorio
                        playRandomMove();
                    }
                }
                break;
            case EASY:
            default:
                playRandomMove();
                break;
        }
    }
    */
}
