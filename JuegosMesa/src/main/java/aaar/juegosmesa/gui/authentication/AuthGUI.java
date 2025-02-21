package aaar.juegosmesa.gui.authentication;

import aaar.juegosmesa.gui.menu.MainMenuGUI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AuthGUI extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton registerButton, loginButton;
    private MainMenuGUI mainMenu;

    public AuthGUI(MainMenuGUI mainMenu) {
        this.mainMenu = mainMenu;

        // Configuración de la interfaz
        setTitle("Registro de Usuario");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        userField = new JTextField(20);
        passField = new JPasswordField(20);
        registerButton = new JButton("Registrar");
        loginButton = new JButton("Iniciar Sesión");

        add(new JLabel("Usuario:"));
        add(userField);
        add(new JLabel("Contraseña:"));
        add(passField);
        add(registerButton);
        add(loginButton);

        // Acción para registrar un nuevo usuario
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                // Validar que los campos no estén vacíos
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(AuthGUI.this, "Usuario y contraseña no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar si el usuario ya existe
                if (usuarioExiste(username)) {
                    JOptionPane.showMessageDialog(AuthGUI.this, "El nombre de usuario ya existe. Elige otro.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Guardar el usuario en el archivo CSV
                guardarUsuarioEnCSV(username, password);
                JOptionPane.showMessageDialog(AuthGUI.this, "Usuario registrado: " + username);
                dispose(); // Cerrar la ventana de registro
            }
        });

        // Acción para iniciar sesión
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                if (verificarCredenciales(username, password)) {
                    JOptionPane.showMessageDialog(AuthGUI.this, "Inicio de sesión exitoso: " + username);
                    dispose(); // Cerrar la ventana de autenticación
                    mainMenu.updatePlayButtonStatus(true);  // Actualizar el estado del botón "Jugar"
                } else {
                    JOptionPane.showMessageDialog(AuthGUI.this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private static final String filePath = "usuarios.csv";

    // Método para guardar el usuario en el archivo CSV
    public static void guardarUsuarioEnCSV(String username, String password) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(username).append(",").append(password).append("\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Verificar si el usuario ya existe en el archivo CSV
    public static boolean usuarioExiste(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].equals(username)) {
                    return true;  // El usuario ya existe
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de usuarios", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;  // El usuario no existe
    }

    // Verificar las credenciales para iniciar sesión
    public static boolean verificarCredenciales(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true;  // Las credenciales son correctas
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de usuarios", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;  // Las credenciales son incorrectas
    }
}
