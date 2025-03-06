package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import autentificacion.LoginGUI;
import autentificacion.RegisterGUI;
import idiomas.LanguageManager;
import puntuaciones.HighScoresGUI;
import juego.GameGui;
import autentificacion.CurrentUser;

public class MainMenu extends JFrame {
    private JButton playButton, registerButton, viewScoresButton, languageButton, loginButton;
    private boolean isLoggedIn = false;

    public MainMenu() {
        setTitle(getMessage("mainMenuTitle"));
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        playButton = new JButton(getMessage("playButton"));
        registerButton = new JButton(getMessage("registerButton"));
        viewScoresButton = new JButton(getMessage("viewScoresButton"));
        languageButton = new JButton(getMessage("languageButton"));
        loginButton = new JButton(getMessage("loginButton"));

        panel.add(playButton);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(viewScoresButton);
        panel.add(languageButton);

        add(panel);

        playButton.setEnabled(false);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isLoggedIn) {
                    String[] difficulties = {
                            LanguageManager.getInstance().getString("easy"),
                            LanguageManager.getInstance().getString("medium"),
                            LanguageManager.getInstance().getString("hard")
                    };
                    String selectedDifficulty = (String) JOptionPane.showInputDialog(
                            MainMenu.this,
                            getMessage("chooseDifficulty"),
                            "Dificultad",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            difficulties,
                            difficulties[0]);

                    if (selectedDifficulty != null) {
                        new GameGui(selectedDifficulty, CurrentUser.getInstance().getUsername()).setVisible(true);
                    }
                } else {
                    showMessageDialog(MainMenu.this, "loginRequired", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterGUI(MainMenu.this).setVisible(true);
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
                        MainMenu.this,
                        getMessage("chooseLanguage"),
                        "Idioma",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (selectedLanguage != null) {
                    LanguageManager.getInstance().changeLanguage(selectedLanguage);
                    dispose();  // Cierra la ventana actual
                    new MainMenu().setVisible(true);  // Abre una nueva instancia de la interfaz gráfica
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginGUI(MainMenu.this).setVisible(true);
            }
        });
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
}