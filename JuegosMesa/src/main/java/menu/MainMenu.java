package menu;

import autentificacion.AuthGUI;
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
    private JButton playButton, createUserButton, viewScoresButton, highScoresButton, languageButton, loginButton;
    private boolean isLoggedIn = false;  // Estado de inicio de sesión

    public MainMenu() {
        setTitle("Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cargar el idioma por defecto (Català)
        changeLanguage("Català");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1)); // 6 filas para los botones

        playButton = new JButton(messages.getString("playButton"));
        createUserButton = new JButton(messages.getString("createUserButton"));
        viewScoresButton = new JButton(messages.getString("viewScoresButton")); // Cambiar a Ver Puntuaciones
        languageButton = new JButton(messages.getString("languageButton"));
        loginButton = new JButton(messages.getString("loginButton"));

        panel.add(playButton);
        panel.add(createUserButton);
        panel.add(viewScoresButton);  // Cambiar a Ver Puntuaciones
        panel.add(languageButton);
        panel.add(loginButton);

        add(panel);

        // Deshabilitar el botón "Jugar" por defecto
        playButton.setEnabled(false);

        // Acción para el botón "Jugar"
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isLoggedIn) {
                    new GameGui().setVisible(true); // Abre la interfaz del juego
                } else {
                    JOptionPane.showMessageDialog(MainMenu.this, "Debes iniciar sesión primero", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para el botón "Crear Usuario"
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AuthGUI(MainMenu.this).setVisible(true); // Abre la interfaz de autenticación
            }
        });

        // Acción para el botón "Ver Puntuaciones" (renombrado)
        viewScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoresGUI().setVisible(true); // Abre la interfaz de puntuaciones
            }
        });

        // Acción para el botón "Escoger Idioma"
        languageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Español", "Català", "English"};
                String selectedLanguage = (String) JOptionPane.showInputDialog(
                        MainMenu.this,
                        "Escoge un idioma",
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
                    createUserButton.setText(messages.getString("createUserButton"));
                    viewScoresButton.setText(messages.getString("viewScoresButton"));
                    languageButton.setText(messages.getString("languageButton"));
                    loginButton.setText(messages.getString("loginButton"));
                }
            }
        });

        // Acción para el botón "Iniciar Sesión"
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AuthGUI(MainMenu.this).setVisible(true); // Abre la interfaz de autenticación
            }
        });
    }

    // Este método permite habilitar el botón "Jugar" cuando el login es exitoso
    public void updatePlayButtonStatus(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;  // Guardar el estado del login
        playButton.setEnabled(isLoggedIn);  // Habilitar o deshabilitar el botón "Jugar"
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
