package aaar.juegosmesa.gui.authentication;

import aaar.juegosmesa.authentication.CurrentUser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import aaar.juegosmesa.gui.menu.MainMenuGUI;
import aaar.juegosmesa.lang.LanguageManager;
import aaar.juegosmesa.storage.StorageManager;

public class LoginGUI extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private MainMenuGUI mainMenu;

    public LoginGUI(MainMenuGUI mainMenu) {
        this.mainMenu = mainMenu;

        setTitle(getMessage("loginTitle"));
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        userField = new JTextField(20);
        passField = new JPasswordField(20);
        loginButton = new JButton(getMessage("loginButton"));

        add(new JLabel(getMessage("usernameLabel")));
        add(userField);
        add(new JLabel(getMessage("passwordLabel")));
        add(passField);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                if (verificarCredenciales(username, password)) {
                    CurrentUser.getInstance().setUsername(username);
                    String message = getMessage("loginSuccess") + " " + username;
                    showMessageDialog(LoginGUI.this, message, getMessage("successTitle"), JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    mainMenu.updatePlayButtonStatus(true);
                } else {
                    showMessageDialog(LoginGUI.this, "loginError", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static boolean verificarCredenciales(String username, String password) {
        StorageManager sm = StorageManager.getInstance();
        try (
                BufferedReader reader = new BufferedReader(
                        new FileReader(sm.getUsersFilePath().toFile())
                )
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            showMessageDialog(null, "errorReadingFile", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private static String getMessage(String key) {
        try {
            return LanguageManager.getInstance().getString(key);
        } catch (java.util.MissingResourceException e) {
            return "[" + key + "]";
        }
    }

    private static void showMessageDialog(Component parentComponent, String key, String title, int messageType) {
        String message = getMessage(key);
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
    }
}
