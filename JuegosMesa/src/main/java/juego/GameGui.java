package juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameGui extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private boolean isXTurn = true; // Indica si es el turno del jugador
    private String difficulty;
    private Random random = new Random();

    public GameGui(String difficulty) {
        this.difficulty = difficulty;
        setTitle("Tres en Raya - " + difficulty.toUpperCase());
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                final int row = i, col = j;

                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (buttons[row][col].getText().isEmpty() && isXTurn) {
                            buttons[row][col].setText("X");

                            if (checkForWin()) { // Verifica si el jugador gana
                                JOptionPane.showMessageDialog(null, "¡Has ganado!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                                resetGame();
                                return;
                            }

                            if (isBoardFull()) { // Verifica si hay empate
                                JOptionPane.showMessageDialog(null, "Empate", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                                resetGame();
                                return;
                            }

                            isXTurn = false; // Cambia el turno a la IA
                            playAI(); // La IA juega
                        }
                    }
                });

                panel.add(buttons[i][j]);
            }
        }
        add(panel);
    }

    private void playAI() {
        System.out.println("Iniciando la IA...");
        if (checkForWin() || isBoardFull()) {
            System.out.println("IA: El juego ya terminó, no se puede jugar.");
            return;
        }

        System.out.println("IA: Jugando en dificultad " + difficulty);

        if (difficulty.equals("easy")) {
            playRandomMove();
        } else if (difficulty.equals("medium")) {
            if (!playWinningMove("O")) {
                if (!playWinningMove("X")) {
                    playRandomMove();
                }
            }
        } else if (difficulty.equals("hard")) {
            playBestMove();
        }


        if (checkForWin()) {
            JOptionPane.showMessageDialog(this, "La IA ha ganado", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
            return;
        }

        if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "Empate", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
            return;
        }

        if (playRandomMove()) {
            imprimirTablero();  // Ver el estado actualizado
            isXTurn = true;  // Solo cambiar el turno después de jugar
        }
        System.out.println("IA: Turno de la IA completado. Es el turno del jugador.");

    }



    // Función para imprimir el tablero
    private void imprimirTablero() {
        System.out.println("Estado del tablero:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String texto = buttons[i][j].getText();
                if (texto.isEmpty()) {
                    System.out.print("-");
                } else {
                    System.out.print(texto);
                }
            }
            System.out.println();
        }
    }

    private boolean playRandomMove() {
        System.out.println("IA: Buscando movimiento aleatorio...");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    System.out.println("IA: Jugando en posición (" + i + ", " + j + ")");

                    // Realizar la jugada de la IA
                    buttons[i][j].setText("O");  // Marcar la jugada
                    System.out.println("Contenido del botón (" + i + ", " + j + "): " + buttons[i][j].getText());

                    return true;  // Retornar true si se hizo una jugada
                }
            }
        }
        return false;  // Si no hay movimiento disponible
    }

    private void realizarJugadaIA() {
        if (playRandomMove()) {  // La IA realiza su jugada
            imprimirTablero();  // Ver el estado del tablero en consola

            // Actualizar la interfaz gráfica


            // Verificar si la IA gana
            if (checkForWin()) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "La IA ha ganado!");
                });
                return;  // Detener el juego inmediatamente
            }

            // Si la IA no gana, cambiar el turno al jugador
            isXTurn = true;
        }
    }


    private boolean playWinningMove(String symbol) {
        System.out.println("IA: Buscando movimiento ganador para '" + symbol + "'...");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    final int row = i;
                    final int col = j;

                    SwingUtilities.invokeLater(() -> {
                        buttons[row][col].setText(symbol);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        buttons[row][col].repaint();
                        buttons[row][col].revalidate();
                    });

                    System.out.println("IA: Movimiento ganador en (" + row + ", " + col + ")");
                    return true;
                }
            }
        }
        System.out.println("IA: No se encontraron movimientos ganadores.");
        return false;
    }


    private void playBestMove() {
        System.out.println("IA: Buscando el mejor movimiento...");
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1, bestCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    buttons[i][j].setText("O");
                    int score = minimax(false);
                    buttons[i][j].setText("");
                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }
        buttons[bestRow][bestCol].setText("O");
        System.out.println("IA: Mejor movimiento en (" + bestRow + ", " + bestCol + ")");
    }

    private int minimax(boolean isMaximizing) {
        if (checkForWin()) {
            System.out.println("IA: Minimax - Juego terminado. Puntuación: " + (isMaximizing ? -1 : 1)); // Mensaje de depuración
            return isMaximizing ? -1 : 1;
        }
        if (isBoardFull()) {
            System.out.println("IA: Minimax - Empate. Puntuación: 0"); // Mensaje de depuración
            return 0;
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    buttons[i][j].setText(isMaximizing ? "O" : "X");
                    int score = minimax(!isMaximizing);
                    buttons[i][j].setText("");
                    bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
                }
            }
        }

        System.out.println("IA: Minimax - Mejor puntuación encontrada: " + bestScore); // Mensaje de depuración
        return bestScore;
    }

    private boolean checkForWin() {
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().isEmpty() &&
                    buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][0].getText().equals(buttons[i][2].getText())) {
                System.out.println("IA: ¡Victoria detectada en la fila " + i + "! Contenido: "
                        + buttons[i][0].getText() + buttons[i][1].getText() + buttons[i][2].getText());
                return true;
            }
            if (!buttons[0][i].getText().isEmpty() &&
                    buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[0][i].getText().equals(buttons[2][i].getText())) {
                System.out.println("IA: ¡Victoria detectada en la columna " + i + "! Contenido: "
                        + buttons[0][i].getText() + buttons[1][i].getText() + buttons[2][i].getText());
                return true;
            }
        }
        if (!buttons[0][0].getText().isEmpty() &&
                buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[0][0].getText().equals(buttons[2][2].getText())) {
            System.out.println("IA: ¡Victoria detectada en la diagonal principal! Contenido: "
                    + buttons[0][0].getText() + buttons[1][1].getText() + buttons[2][2].getText());
            return true;
        }
        if (!buttons[0][2].getText().isEmpty() &&
                buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[0][2].getText().equals(buttons[2][0].getText())) {
            System.out.println("IA: ¡Victoria detectada en la diagonal secundaria! Contenido: "
                    + buttons[0][2].getText() + buttons[1][1].getText() + buttons[2][0].getText());
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    System.out.println("IA: Tablero no está lleno."); // Mensaje de depuración
                    return false;
                }
            }
        }
        System.out.println("IA: ¡Tablero lleno! Empate."); // Mensaje de depuración
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        isXTurn = true;
    }
}