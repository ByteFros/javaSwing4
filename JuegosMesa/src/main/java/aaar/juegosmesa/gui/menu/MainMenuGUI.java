package aaar.juegosmesa.gui.menu;

import aaar.juegosmesa.games.shared.GameDifficulty;
import aaar.juegosmesa.lang.LanguageManager;
import aaar.juegosmesa.games.tictactoe.gui.TicTacToeGameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import aaar.juegosmesa.gui.authentication.LoginGUI;
import aaar.juegosmesa.gui.authentication.RegisterGUI;
import puntuaciones.HighScoresGUI;
import aaar.juegosmesa.authentication.CurrentUser;

public class MainMenuGUI extends JFrame {
    private JButton playButton, registerButton, viewScoresButton, languageButton, loginButton;
    private boolean isLoggedIn = false;  // Estado de inicio de sesión

    public MainMenuGUI() {
        setTitle(getMessage("mainMenuTitle"));
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBackground(new Color(45, 45, 45));

        // Usar los mensajes localizados para los textos de los botones
        playButton = createButton(
                getMessage("playButton"),
                "icons/play.svg"
        );
        loginButton = createButton(
                getMessage("loginButton"),
                "icons/login.svg"
        );
        registerButton = createButton(
                getMessage("registerButton"),
                "icons/user_add.svg"
        );
        viewScoresButton = createButton(
                getMessage("viewScoresButton"),
                "icons/score.svg"
        );
        languageButton = createButton(
                getMessage("languageButton"),
                "icons/language.svg"
        );

        panel.add(playButton);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(viewScoresButton);
        panel.add(languageButton);

        add(panel);
        // Deshabilitar el botón "Jugar" por defecto
        playButton.setEnabled(false);

        // Acción para el botón "Jugar"
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isLoggedIn) {
                    final GameDifficulty[] difficulties = {
                            GameDifficulty.EASY,
                            GameDifficulty.MEDIUM,
                            GameDifficulty.HARD
                    };
                    GameDifficulty selectedDifficulty = (GameDifficulty) JOptionPane.showInputDialog(
                            MainMenuGUI.this,
                            getMessage("chooseDifficulty"),
                            getMessage("chooseDifficulty"),
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            difficulties,
                            difficulties[0]
                    );

                    if (selectedDifficulty == null) return;
                    
                    // Abre la interfaz del juego
                    new TicTacToeGameGUI(
                            selectedDifficulty,
                            CurrentUser.getInstance().getUsername()
                    ).setVisible(true);
                } else {
                    showMessageDialog(
                        MainMenuGUI.this, 
                        "loginRequired", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterGUI(MainMenuGUI.this).setVisible(true);
            }
        });

        viewScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoresGUI().setVisible(true);
            }
        });

        languageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = { "Español", "Català", "English" };
                String selectedLanguage = (String) JOptionPane.showInputDialog(
                        MainMenuGUI.this,
                        getMessage("chooseLanguage"),
                        getMessage("languageButton"),
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (selectedLanguage != null) {
                    LanguageManager.getInstance().changeLanguage(selectedLanguage);
                    dispose();  // Cierra la ventana actual
                    new MainMenuGUI().setVisible(true);  // Abre una nueva instancia de la interfaz gráfica
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginGUI(MainMenuGUI.this).setVisible(true);
            }
        });
    }

    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text);
        try {
            // Crear un icono SVG con tamaño personalizado
            FlatSVGIcon icon = new FlatSVGIcon(iconPath);

            // Establecer un tamaño específico para el icono
            icon = icon.derive(15,15);  // Escala el icono a un 80% de su tamaño original

            button.setIcon(icon);

            // Estilo adicional para el botón
            button.setFocusPainted(false);
            button.setFont(new Font("SansSerif", Font.BOLD, 14));
            button.setIconTextGap(15);

            // Centrar el icono y el texto
            button.setHorizontalAlignment(SwingConstants.LEFT);

            // Añadir padding para que el botón se vea mejor
            button.setMargin(new Insets(10, 15, 10, 15));
        } catch (Exception ex) {
            System.err.println("Error cargando icono: " + iconPath + " - " + ex.getMessage());
        }
        return button;
    }

    private String getMessage(String key) {
        return LanguageManager.getInstance().getString(key);
    }

    private void showMessageDialog(Component parentComponent, String key, String title, int messageType) {
        String message = getMessage(key);
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
    }

    public void updatePlayButtonStatus(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
        playButton.setEnabled(isLoggedIn);
    }
}