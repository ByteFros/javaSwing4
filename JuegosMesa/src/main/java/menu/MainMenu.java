package menu;

import autentificacion.LoginGUI;
import autentificacion.RegisterGUI;
import juego.GameGui;
import puntuaciones.HighScoresGUI;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainMenu extends JFrame {
    private ResourceBundle messages;
    private JButton playButton, createUserButton, viewScoresButton, languageButton, loginButton;
    private boolean isLoggedIn = false;

    public MainMenu() {
        // Aplicar tema FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Menú Principal");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        changeLanguage("Català");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(30, 30, 30));

        // Crear botones con iconos
        playButton = createStyledButton(messages.getString("playButton"), "icons/play.svg");
        createUserButton = createStyledButton(messages.getString("createUserButton"), "icons/user_add.svg");
        viewScoresButton = createStyledButton(messages.getString("viewScoresButton"), "icons/score.svg");
        languageButton = createStyledButton(messages.getString("languageButton"), "icons/language.svg");
        loginButton = createStyledButton(messages.getString("loginButton"), "icons/login.svg");

        playButton.setEnabled(false); // Deshabilitado por defecto

        panel.add(playButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciador
        panel.add(createUserButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(viewScoresButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(languageButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(loginButton);

        add(panel);

        // Acciones de los botones
        playButton.addActionListener(e -> {
            if (isLoggedIn) {
                new GameGui().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Debes iniciar sesión primero", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        createUserButton.addActionListener(e -> new RegisterGUI(this).setVisible(true));
        viewScoresButton.addActionListener(e -> new HighScoresGUI().setVisible(true));

        languageButton.addActionListener(e -> {
            String[] options = {"Español", "Català", "English"};
            String selectedLanguage = (String) JOptionPane.showInputDialog(
                    this, "Escoge un idioma", "Idioma", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (selectedLanguage != null) {
                changeLanguage(selectedLanguage);
                updateButtonLabels();
            }
        });

        loginButton.addActionListener(e -> new LoginGUI(this).setVisible(true));
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setIcon(new FlatSVGIcon(iconPath, 20, 20));
        return button;
    }

    private void updateButtonLabels() {
        playButton.setText(messages.getString("playButton"));
        createUserButton.setText(messages.getString("createUserButton"));
        viewScoresButton.setText(messages.getString("viewScoresButton"));
        languageButton.setText(messages.getString("languageButton"));
        loginButton.setText(messages.getString("loginButton"));
    }

    public void updatePlayButtonStatus(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
        playButton.setEnabled(isLoggedIn);
    }

    private void changeLanguage(String language) {
        Locale locale;
        switch (language) {
            case "Español":
                locale = new Locale("es", "ES");
                break;
            case "Català":
                locale = new Locale("ca", "ES");
                break;
            case "English":
                locale = new Locale("en", "US");
                break;
            default:
                locale = new Locale("ca", "ES");
        }
        messages = ResourceBundle.getBundle("messages", locale);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}
