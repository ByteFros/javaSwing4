package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import autentificacion.LoginGUI;
import autentificacion.RegisterGUI;
import com.formdev.flatlaf.FlatDarkLaf;
import idiomas.LanguageManager;
import puntuaciones.HighScoresGUI;
import juego.GameGui;

public class MainMenu extends JFrame {
    private JButton playButton, registerButton, viewScoresButton, languageButton, loginButton;
    private boolean isLoggedIn = false;

    public MainMenu() {
        setTitle(getMessage("mainMenuTitle"));
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBackground(new Color(45, 45, 45));

        playButton = createButton("Jugar", "icons/play.svg");
        registerButton = createButton("Crear Usuario", "icons/user_add.svg");
        viewScoresButton = createButton("Puntuaciones", "icons/score.svg");
        languageButton = createButton("Idioma", "icons/language.svg");
        loginButton = createButton("Iniciar Sesión", "icons/login.svg");

        panel.add(playButton);
        panel.add(registerButton);
        panel.add(viewScoresButton);
        panel.add(languageButton);
        panel.add(loginButton);

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
                        new GameGui(selectedDifficulty).setVisible(true);
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

    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text);
        try {
            // Crear un icono SVG con tamaño personalizado
            FlatSVGIcon icon = new FlatSVGIcon(iconPath);

            // Establecer un tamaño específico para el icono (ajusta estos valores según necesites)
            // Alternativamente puedes usar un tamaño fijo:
            icon =  icon.derive(15, 15);  // Establece el tamaño a 24x24 píxeles

            button.setIcon(icon);
            // Estilo adicional para el botón
            button.setFocusPainted(false);
            button.setFont(new Font("SansSerif", Font.BOLD, 14));
            button.setIconTextGap(10);

            // Opcional: centrar el icono y el texto
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setIconTextGap(15); // Espacio entre icono y texto

            // Opcional: añadir padding para que el botón se vea mejor
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());

            // Configurar fuente personalizada
            Font customFont = new Font("SansSerif", Font.BOLD, 16);
            UIManager.put("Button.font", customFont);
            UIManager.put("Label.font", customFont);
            UIManager.put("Panel.font", customFont);
            UIManager.put("OptionPane.messageFont", customFont);
            UIManager.put("OptionPane.buttonFont", customFont);

            // Opcional: Botones redondeados
            UIManager.put("Button.arc", 20);
            UIManager.put("Component.arc", 15);

        } catch (Exception ex) {
            System.err.println("Error al aplicar FlatLaf: " + ex.getMessage());
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
}