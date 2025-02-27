/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aaar.juegosmesa.gui.game;

import aaar.juegosmesa.logic.tictactoe.TicTacToeGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.IntStream;

public class GameGUI extends JFrame {
    private TicTacToeGame gameLogic = new TicTacToeGame();
    private JButton[][] buttons = new JButton[3][3];
    private Object board;

    public GameGUI() {
        setTitle("Tres en Raya");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        
        final Font buttonFont = new Font("Arial", Font.PLAIN, 40);
        IntStream.range(0, 9)
            .forEach(cellIndex -> {
                final int row = cellIndex / 3;
                final int col = cellIndex % 3;
                
                final JButton button = new JButton();
                button.setFont(buttonFont);   // Establecer tipografía
                button.setFocusPainted(false);  // Remover el foco de los botones
                button.addActionListener(
                    (ActionEvent e) -> {
                        JButton buttonSource = (JButton) e.getSource();
                        if (gameLogic.isCellEmpty(row, col) && !gameOver()) {
                            // buttonSource.setText(isXTurn() ? "X" : "O");
                            gameLogic.setCell(isXTurn() ? 'X' : 'O', row, col);
                            if (checkForWin(row,col)) {
                                this.refreshBoardUI();
                                
                                final String winner = isXTurn() ? "X" : "O";
                                JOptionPane.showMessageDialog(
                                        GameGUI.this,
                                        "¡El jugador " + winner + " ha ganado!"
                                );
                                disableBoard();
                            }
                            gameLogic.startNextTurn();
                            this.refreshBoardUI();
                        }
                    }
                );
                buttons[row][col] = button;
                panel.add(buttons[row][col]);
            }
        );
        /*
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].setFocusPainted(false); // Remover el foco de los botones
                buttons[i][j].addActionListener((ActionEvent e) -> {
                    JButton button = (JButton) e.getSource();
                    if (button.getText().isEmpty() && !gameOver()) {
                        button.setText(isXTurn() ? "X" : "O");
                        gameLogic.setCell(isXTurn() ? 'X' : 'O', i, j);
                        if (checkForWin()) {
                            String winner = isXTurn() ? "X" : "O";
                            JOptionPane.showMessageDialog(
                                    GameGUI.this,
                                    "¡El jugador " + winner + " ha ganado!"
                            );
                            disableBoard();
                        }
                        gameLogic.startNextTurn();
                    }
                });
                panel.add(buttons[i][j]);
            }
        }
        */

        add(panel);
    }

    private boolean isXTurn() {
        return gameLogic.isXTurn();
    }
    
    // Verificar si hay una victoria (filas, columnas, diagonales)
    private boolean checkForWin() {
        return gameLogic.checkForWin();
        /*
        // Verificar filas y columnas
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) && buttons[i][1].getText().equals(buttons[i][2].getText()) && !buttons[i][0].getText().isEmpty()) {
                return true;
            }
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) && buttons[1][i].getText().equals(buttons[2][i].getText()) && !buttons[0][i].getText().isEmpty()) {
                return true;
            }
        }
        
        // Verificar diagonales
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][2].getText()) && !buttons[0][0].getText().isEmpty()) {
            return true;
        }
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][0].getText()) && !buttons[0][2].getText().isEmpty()) {
            return true;
        }

        return false;
        */
    }
    private boolean checkForWin(int row, int column) {
        return gameLogic.checkForWin(row, column);
    }


    // Deshabilitar los botones cuando el juego haya terminado
    private void disableBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    // Verificar si el juego ya terminó (victoria o empate)
    private boolean gameOver() {
        return checkForWin();
    }

    // Reiniciar el tablero
    public void resetBoard() {
        gameLogic.resetBoard();
        refreshBoardUI();
    }
    
    public void refreshBoardUI() {
        boolean isEnabled = !checkForWin();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = buttons[row][col];
                button.setText(
                        getCell(row,col)
                );
                button.setEnabled(isEnabled);  // Volver a habilitar los botones al reiniciar
            }
        }
    }
    
    public String getCell(int row, int column) {
        char c = gameLogic.getCell(row, column);
        return switch (c) {
            case '\0'
                -> "";
            default
                -> String.valueOf(c);
        };
    }
}