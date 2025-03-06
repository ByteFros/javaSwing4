package puntuaciones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HighScoresGUI extends JFrame {
    public HighScoresGUI() {
        setTitle("Highscores");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Jugador", "Ganadas", "Perdidas", "% Victorias"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (BufferedReader br = new BufferedReader(new FileReader("highscore.csv"))) {
            String line;
            System.out.println("Reading highscore.csv...");
            while ((line = br.readLine()) != null) {
                System.out.println("Read line: " + line);
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    System.out.println("Parsed parts: " + java.util.Arrays.toString(parts));
                    if (parts.length == 4) {
                        System.out.println("Adding row: " + java.util.Arrays.toString(parts));
                        model.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3]});
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading highscore.csv: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al cargar los puntajes", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

 
}