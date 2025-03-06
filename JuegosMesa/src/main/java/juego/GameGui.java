package juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Tres en Raya con IA en tres dificultades.
 * Ejecuta el main para probar la funcionalidad.
 */
public class GameGui extends JFrame {

    private JButton[][] buttons = new JButton[3][3];
    private boolean isXTurn = true;     // Indica si es el turno del jugador
    private String difficulty;          // "easy", "medium" o "hard"
    private int wins = 0;
    private int losses = 0;
    private int totalGames = 0;
    private String currentUsername;

    public GameGui(String difficulty, String username) {
        this.difficulty = difficulty.toLowerCase().trim();
        this.currentUsername = username;
        setTitle("Tres en Raya - " + this.difficulty.toUpperCase());
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel con GridLayout para los 9 botones
        JPanel panel = new JPanel(new GridLayout(3, 3));

        // Crear los botones y agregar el ActionListener
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (buttons[row][col].getText().isEmpty() && isXTurn) {
                            // Jugada del jugador
                            buttons[row][col].setText("X");

                            if (checkForWin()) {
                                wins++;
                                totalGames++;
                                saveStatsToCSV();
                                JOptionPane.showMessageDialog(null, "¡Has ganado!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                                resetGame();
                                return;
                            }
                            if (isBoardFull()) {
                                totalGames++;
                                saveStatsToCSV();
                                JOptionPane.showMessageDialog(null, "Empate", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                                resetGame();
                                return;
                            }
                            // Cambiar turno a la IA y jugar
                            isXTurn = false;
                            playAI();
                        }
                    }
                });
                panel.add(buttons[i][j]);
            }
        }
        add(panel);
        setVisible(true);
    }

    /**
     * Lógica de la IA según la dificultad seleccionada.
     */
    private void playAI() {
        System.out.println("IA: Iniciando la IA...");

        if (checkForWin() || isBoardFull()) {
            System.out.println("IA: El juego ya terminó, no se puede jugar.");
            return;
        }

        System.out.println("IA: Jugando en dificultad " + difficulty);
        switch (difficulty) {
            case "easy":
                playRandomMove();
                break;
            case "medium":
                // Primero, buscar jugada ganadora para la IA (O)
                if (!playWinningMove("O")) {
                    // Luego, intentar bloquear la jugada ganadora del jugador (simulando "X")
                    if (!playWinningMove("X")) {
                        // Si no hay jugada ganadora ni bloqueo, hacer movimiento aleatorio
                        playRandomMove();
                    }
                }
                break;
            case "hard":
                playBestMove();
                break;
            default:
                playRandomMove();
                break;
        }

        if (checkForWin()) {
            losses++;
            totalGames++;
            saveStatsToCSV();
            JOptionPane.showMessageDialog(this, "La IA ha ganado", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
            return;
        }
        if (isBoardFull()) {
            totalGames++;
            saveStatsToCSV();
            JOptionPane.showMessageDialog(this, "Empate", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
            return;
        }

        imprimirTablero();
        isXTurn = true;
        System.out.println("IA: Turno de la IA completado. Es el turno del jugador.");
        revalidate();
        repaint();
    }

    /**
     * Modo EASY: coloca "O" en una casilla vacía al azar.
     */
    private boolean playRandomMove() {
        System.out.println("IA: Buscando movimiento aleatorio...");
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
        if (!emptyCells.isEmpty()) {
            int[] randomCell = emptyCells.get((int) (Math.random() * emptyCells.size()));
            int row = randomCell[0];
            int col = randomCell[1];
            System.out.println("IA: Jugando en posición (" + row + ", " + col + ")");
            buttons[row][col].setText("O");
            System.out.println("Contenido del botón (" + row + ", " + col + "): " + buttons[row][col].getText());
            return true;
        }
        return false;
    }

    /**
     * Modo MEDIUM:
     * Si se pasa "O", busca una jugada que le haga ganar a la IA.
     * Si se pasa "X", simula la jugada del jugador y, si detecta que esa jugada ganaría,
     * la revierte y coloca una "O" para bloquear.
     */
    private boolean playWinningMove(String symbol) {
        System.out.println("IA: Buscando movimiento ganador para '" + symbol + "'...");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    // Simular la jugada
                    buttons[i][j].setText(symbol);
                    if (checkForWin()) {
                        if (symbol.equals("X")) {
                            // Bloquear: revertir el "X" y colocar "O"
                            buttons[i][j].setText("O");
                            System.out.println("IA: Bloqueo jugada ganadora del jugador en (" + i + ", " + j + ")");
                        } else {
                            System.out.println("IA: Jugada ganadora para O en (" + i + ", " + j + ")");
                        }
                        return true;
                    } else {
                        // Revertir la jugada simulada
                        buttons[i][j].setText("");
                    }
                }
            }
        }
        System.out.println("IA: No se encontraron movimientos ganadores para " + symbol);
        return false;
    }

    /**
     * Modo HARD: usa el algoritmo minimax para buscar la mejor jugada.
     */
    private void playBestMove() {
        System.out.println("IA: Buscando el mejor movimiento (minimax)...");
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;
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

    /**
     * Algoritmo minimax para evaluar el tablero.
     * @param isMaximizing true si es turno de la IA, false si es turno del jugador.
     * @return puntuación del tablero.
     */
    private int minimax(boolean isMaximizing) {
        if (checkForWin()) {
            return isMaximizing ? -1 : 1;
        }
        if (isBoardFull()) {
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
        return bestScore;
    }

    /**
     * Imprime el estado actual del tablero en la consola.
     */
    private void imprimirTablero() {
        System.out.println("Estado del tablero:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String txt = buttons[i][j].getText();
                System.out.print(txt.isEmpty() ? "-" : txt);
            }
            System.out.println();
        }
    }

    /**
     * Verifica si hay 3 en raya en filas, columnas o diagonales.
     */
    private boolean checkForWin() {
        // Filas
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().isEmpty() &&
                buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][0].getText().equals(buttons[i][2].getText())) {
                System.out.println("IA: ¡Victoria detectada en la fila " + i + "!");
                return true;
            }
        }
        // Columnas
        for (int i = 0; i < 3; i++) {
            if (!buttons[0][i].getText().isEmpty() &&
                buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                buttons[0][i].getText().equals(buttons[2][i].getText())) {
                System.out.println("IA: ¡Victoria detectada en la columna " + i + "!");
                return true;
            }
        }
        // Diagonal principal
        if (!buttons[0][0].getText().isEmpty() &&
            buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[0][0].getText().equals(buttons[2][2].getText())) {
            System.out.println("IA: ¡Victoria detectada en la diagonal principal!");
            return true;
        }
        // Diagonal secundaria
        if (!buttons[0][2].getText().isEmpty() &&
            buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[0][2].getText().equals(buttons[2][0].getText())) {
            System.out.println("IA: ¡Victoria detectada en la diagonal secundaria!");
            return true;
        }
        return false;
    }

    /**
     * Comprueba si el tablero está lleno (empate).
     */
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Reinicia el tablero y asigna el turno al jugador.
     */
    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        isXTurn = true;
    }


    private void saveStatsToCSV() {
        List<String> lines = new ArrayList<>();
        boolean userFound = false;
    
        try (BufferedReader br = new BufferedReader(new FileReader("c:\\Users\\Admin\\Documents\\GitHub\\javaSwing4\\highscore.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(currentUsername + ",")) {
                    String[] parts = line.split(",");
                    int existingWins = Integer.parseInt(parts[1]);
                    int existingLosses = Integer.parseInt(parts[2]);
    
                    // Calcular nuevas estadísticas
                    int totalWins = existingWins + wins;
                    int totalLosses = existingLosses + losses;
                    int totalGames = totalWins + totalLosses;
                    double winPercentage = (totalGames > 0) ? ((double) totalWins / totalGames) * 100 : 0;
    
                    // Formatear el porcentaje correctamente con punto decimal
                    line = String.format(Locale.US, "%s,%d,%d,%.2f%%", currentUsername, totalWins, totalLosses, winPercentage);
                    userFound = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        if (!userFound) {
            double winPercentage = totalGames > 0 ? (double) wins / totalGames * 100 : 0;
            lines.add(String.format(Locale.US, "%s,%d,%d,%.2f%%", currentUsername, wins, losses, winPercentage));
        }
    
        try (FileWriter writer = new FileWriter("c:\\Users\\Admin\\Documents\\GitHub\\javaSwing4\\highscore.csv")) {
            for (String line : lines) {
                writer.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Reset wins and losses after saving stats
        wins = 0;
        losses = 0;
    }
    

    // Método main para ejecutar la aplicación
}
