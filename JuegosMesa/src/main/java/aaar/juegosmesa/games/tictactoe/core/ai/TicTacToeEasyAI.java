/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aaar.juegosmesa.games.tictactoe.core.ai;

import aaar.juegosmesa.games.shared.GameDifficulty;
import static aaar.juegosmesa.games.shared.GameDifficulty.*;
import aaar.juegosmesa.games.tictactoe.core.TicTacToeGame;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rmartin
 */
public class TicTacToeEasyAI extends TicTacToeAI {
    public TicTacToeEasyAI(TicTacToeGame parentGame) {
        super(parentGame);
    }
    
    public boolean playTurn() {
        if (isGameOver()) return false;
        logToConsole();
        
        return playRandomMove();
    }
    
    /**
     * Modo EASY: coloca "O" en una casilla vacía al azar.
     * @return boolean
     */
    protected boolean playRandomMove() {
        System.out.println("IA: Buscando movimiento aleatorio...");
        List<int[]> emptyCells = new ArrayList<>();
        final int size = parentGame.getBoardSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (parentGame.isCellEmpty(row, col)) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }
        
        if (emptyCells.isEmpty()) return false;
        
        final int[] randomCell = emptyCells.get(
                (int) (Math.random() * emptyCells.size())
        );
        final int row = randomCell[0];
        final int col = randomCell[1];
        final String cellCoordsStr = "(" + row + ", " + col + ")";
        System.out.println("IA: Jugando en posición " + cellCoordsStr);

        parentGame.setCell('O', row, col);
        System.out.println(
                "Contenido de la celda " + cellCoordsStr + ": "
                        + parentGame.getCell(row, col)
        );
        
        return true;
    }
}
