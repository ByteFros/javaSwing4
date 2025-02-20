package juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGui extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private boolean isXTurn = true;

    public GameGui() {
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
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        if (button.getText().isEmpty()) {
                            button.setText(isXTurn ? "X" : "O");
                            isXTurn = !isXTurn;
                            if (checkForWin()) {
                                JOptionPane.showMessageDialog(GameGui.this, "¡Tenemos un ganador!");
                                resetBoard();
                            }
                        }
                    }
                });
                panel.add(buttons[i][j]);
            }
        }

        add(panel);
    }

    private boolean checkForWin() {
        // Verificar filas, columnas y diagonales (código existente)
        // ...
        return false;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        isXTurn = true;
    }
}