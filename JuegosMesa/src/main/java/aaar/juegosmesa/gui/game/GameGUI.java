/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aaar.juegosmesa.gui.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private boolean isXTurn = true;

    public GameGUI() {
        setTitle("Tres en Raya");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].setFocusPainted(false); // Remover el foco de los botones
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        if (button.getText().isEmpty() && !gameOver()) {
                            button.setText(isXTurn ? "X" : "O");
                            if (checkForWin()) {
                                String winner = isXTurn ? "X" : "O";
                                JOptionPane.showMessageDialog(
                                        GameGUI.this,
                                        "¡El jugador " + winner + " ha ganado!"
                                );
                                disableBoard();
                            }
                            isXTurn = !isXTurn;
                        }
                    }
                });
                panel.add(buttons[i][j]);
            }
        }

        add(panel);
    }

    // Verificar si hay una victoria (filas, columnas, diagonales)
    private boolean checkForWin() {
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);  // Volver a habilitar los botones al reiniciar
            }
        }
        isXTurn = true;
    }
}