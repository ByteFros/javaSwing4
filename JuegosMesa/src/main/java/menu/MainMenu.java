package menu;

import autentificacion.LoginGUI;
import autentificacion.RegisterGUI;
import juego.GameGui;
import puntuaciones.HighScoresGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainMenu extends JFrame {
    private ResourceBundle messages;
    private boolean isLoggedIn = false;
    private JButton playButton;

    public MainMenu() {
        setTitle("Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cargar el idioma por defecto (Català)
        changeLanguage("Català");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1)); // 7 filas para incluir todos los botones

        playButton = new JButton(messages.getString("playButton"));
        JButton registerButton = new JButton(messages.getString("createUserButton"));
        JButton loginButton = new JButton(messages.getString("loginButton"));
        JButton deleteUserButton = new JButton(messages.getString("deleteUserButton"));
        JButton viewScoresButton = new JButton(messages.getString("viewScoresButton"));
        JButton highScoresButton = new JButton(messages.getString("highScoresButton"));
        JButton languageButton = new JButton(messages.getString("languageButton"));

        playButton.setEnabled(false); // Disabled by default until login

        panel.add(playButton);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(deleteUserButton);
        panel.add(viewScoresButton);
        panel.add(highScoresButton);
        panel.add(languageButton);

        add(panel);

        // Acción para el botón "Jugar"
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameGui().setVisible(true);
            }
        });

        // Acción para el botón "Registrar"
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterGUI().setVisible(true);
            }
        });

        // Acción para el botón "Iniciar Sessió"
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginGUI(MainMenu.this).setVisible(true);
            }
        });

        // Acción para el botón "Veure Highscores"
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoresGUI().setVisible(true);
            }
        });

        // Acción para el botón "Escollir Idioma"
        languageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Español", "Català", "English"};
                String selectedLanguage = (String) JOptionPane.showInputDialog(
                        MainMenu.this,
                        "Escull un idioma",
                        "Idioma",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (selectedLanguage != null) {
                    changeLanguage(selectedLanguage);
                    JOptionPane.showMessageDialog(MainMenu.this, "Idioma seleccionado: " + selectedLanguage);
                    // Actualizar los textos de los botones
                    playButton.setText(messages.getString("playButton"));
                    registerButton.setText(messages.getString("createUserButton"));
                    deleteUserButton.setText(messages.getString("deleteUserButton"));
                    viewScoresButton.setText(messages.getString("viewScoresButton"));
                    highScoresButton.setText(messages.getString("highScoresButton"));
                    languageButton.setText(messages.getString("languageButton"));
                    loginButton.setText(messages.getString("loginButton"));
                }
            }
        });
    }

    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
        playButton.setEnabled(loggedIn);
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
}