package aaar.juegosmesa.games.tictactoe.gui;

import aaar.juegosmesa.games.shared.GameDifficulty;
import aaar.juegosmesa.games.tictactoe.core.TicTacToeGame;
import aaar.juegosmesa.storage.StorageManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
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
public class TicTacToeGameGUI extends JFrame {
    private TicTacToeGame gameLogic;
    private JButton[][] buttons = new JButton[3][3];
    
    private int wins = 0;
    private int losses = 0;
    private int totalGames = 0;
    private String currentUsername;

    public TicTacToeGameGUI(GameDifficulty difficulty, String username) {
        this.gameLogic = TicTacToeGame.getInstance(difficulty);
        this.currentUsername = username;
        setTitle(
                "Tres en Raya - "
                + this.gameLogic.getDifficulty().toString().toUpperCase()
        );
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel con GridLayout para los 9 botones
        JPanel panel = new JPanel(new GridLayout(3, 3));

        // Fuente de los botones.
        final Font buttonFont = new Font("Arial", Font.PLAIN, 40);
        final int boardSize = gameLogic.getBoardSize();
        
        // Crear los botones y agregar el ActionListener
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(buttonFont);
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if ( !(gameLogic.isXTurn() && gameLogic.isCellEmpty(row, col)) )
                        {
                            return;
                        }
                        
                        // Jugada del jugador
                        gameLogic.setCell('X', row, col);
                        repaintCell(row, col);

                        // PLAYER WINS
                        if (checkForWin()) {
                            wins++;
                            totalGames++;
                            saveStatsToCSV();
                            JOptionPane.showMessageDialog(
                                null,
                                "¡Has ganado!",
                                "Game Over",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                            resetGame();
                            return;
                        }
                        // DRAW
                        if (isBoardFull()) {
                            totalGames++;
                            saveStatsToCSV();
                            JOptionPane.showMessageDialog(
                                null,
                                "Empate",
                                "Game Over",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                            resetGame();
                            return;
                        }
                        // Cambiar turno a la IA y jugar
                        gameLogic.startNextTurn();
                        playAI();
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
        // let the AI player play its turn.
        gameLogic.playAI();
        repaintBoard();

        // AI WINS
        if (checkForWin()) {
            losses++;
            totalGames++;
            saveStatsToCSV();
            JOptionPane.showMessageDialog(
                    this, "La IA ha ganado", "Game Over", JOptionPane.INFORMATION_MESSAGE
            );
            resetGame();
            return;
        }
        // DRAW
        if (isBoardFull()) {
            totalGames++;
            saveStatsToCSV();
            JOptionPane.showMessageDialog(
                    this, "Empate", "Game Over", JOptionPane.INFORMATION_MESSAGE
            );
            resetGame();
            return;
        }

        imprimirTablero();
        gameLogic.startNextTurn(); // devolver el turno al jugador.
        repaintBoard();
        
        System.out.println("IA: Turno de la IA completado. Es el turno del jugador.");
        revalidate();
        repaint();
    }

    /**
     * Imprime el estado actual del tablero en la consola.
     */
    private void imprimirTablero() {
        System.out.println("Estado del tablero:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char txt = gameLogic.getCell(i, j);
                System.out.print(txt == '\0' ? "-" : txt);
            }
            System.out.println();
        }
    }

    /* GAME LOGIC-RELATED METHODS*/
    /**
     * Verifica si hay 3 en raya en filas, columnas o diagonales.
     */
    private boolean checkForWin() {
        return gameLogic.checkForWin();
    }

    /**
     * Comprueba si el tablero está lleno (empate).
     */
    private boolean isBoardFull() {
        return gameLogic.isBoardFull();
    }

    /**
     * Reinicia el tablero y asigna el turno al jugador.
     */
    private void resetGame() {
        gameLogic.reset();
        repaintBoard();
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
    
    /* GUI METHODS */
    private void repaintCell(int row, int column) {
        buttons[row][column].setText(
            getCell(row, column)
        );
    }
    private void repaintCell(int row, int column, boolean isEnabled) {
        repaintCell(row, column);
        buttons[row][column].setEnabled(isEnabled);
    }
    private void repaintBoard() {
        final boolean isEnabled = !gameLogic.isGameOver();
        final int size = gameLogic.getBoardSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                repaintCell(row, col, isEnabled);
            }
        }
    }


    private void saveStatsToCSV() {
        StorageManager sm = StorageManager.getInstance();
        List<String> lines = new ArrayList<>();
        boolean userFound = false;
    
        try (
            BufferedReader br = new BufferedReader(
                new FileReader(sm.getHighscoresFilePath().toFile())
            )
        ) {
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
    
        //final String separator = File.separator;
        try (
                FileWriter writer = new FileWriter(
                        sm.getHighscoresFilePath().toString()
                )
        ) {
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