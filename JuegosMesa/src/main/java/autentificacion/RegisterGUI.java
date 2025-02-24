package autentificacion;

import javax.swing.*;

import menu.MainMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterGUI extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton registerButton;
    // Variable para referenciar el MainMenu
        private MainMenu mainMenu;
    
        // Constructor que acepta el objeto MainMenu
        public RegisterGUI(MainMenu mainMenu) {
            this.mainMenu = mainMenu;

        // Configuración de la interfaz
        setTitle("Registro de Usuario");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Cambiar a DISPOSE_ON_CLOSE para solo cerrar la ventana de registro
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        userField = new JTextField(20);
        passField = new JPasswordField(20);
        registerButton = new JButton("Registrar");

        add(new JLabel("Usuario:"));
        add(userField);
        add(new JLabel("Contraseña:"));
        add(passField);
        add(registerButton);

        // Acción para registrar un nuevo usuario
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                // Validar que los campos no estén vacíos
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterGUI.this, "Usuario y contraseña no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Guardar el usuario en el archivo CSV
                guardarUsuarioEnCSV(username, password);
                JOptionPane.showMessageDialog(RegisterGUI.this, "Usuario registrado: " + username);
                dispose(); // Solo cierra esta ventana, no el menú principal
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
}
