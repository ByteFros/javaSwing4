package puntuaciones;

import javax.swing.*;
import java.awt.*;

public class HighScoresGUI extends JFrame {
    public HighScoresGUI() {
        setTitle("Highscores");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea scoresArea = new JTextArea();
        scoresArea.setEditable(false);
        scoresArea.setText("1. Jugador1 - 1000\n2. Jugador2 - 900\n3. Jugador3 - 800");

        panel.add(new JScrollPane(scoresArea), BorderLayout.CENTER);
        add(panel);
    }
}