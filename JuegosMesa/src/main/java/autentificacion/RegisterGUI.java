package autentificacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import aaar.juegosmesa.gui.menu.MainMenuGUI;
import aaar.juegosmesa.lang.LanguageManager;

public class RegisterGUI extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton registerButton;
    private MainMenuGUI mainMenu;
    private static final String filePath = "usuarios.csv";

    public RegisterGUI(MainMenuGUI mainMenu) {
        this.mainMenu = mainMenu;

        setTitle(getMessage("registerTitle"));
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        userField = new JTextField(20);
        passField = new JPasswordField(20);
        registerButton = new JButton(getMessage("registerButton"));

        add(new JLabel(getMessage("usernameLabel")));
        add(userField);
        add(new JLabel(getMessage("passwordLabel")));
        add(passField);
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    showMessageDialog(RegisterGUI.this, "emptyFieldsError", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (usuarioExiste(username)) {
                    showMessageDialog(RegisterGUI.this, "userExistsError", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                guardarUsuarioEnCSV(username, password);
                String message = getMessage("userRegistered") + " " + username;
                showMessageDialog(RegisterGUI.this, message, getMessage("successTitle"), JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static void guardarUsuarioEnCSV(String username, String password) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(username).append(",").append(password).append("\n");
        } catch (IOException ex) {
            showMessageDialog(null, "errorSavingUser", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean usuarioExiste(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].equals(username)) {
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
