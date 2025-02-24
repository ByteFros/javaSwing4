package autentificacion;

import javax.swing.*;

import menu.MainMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private MainMenu mainMenu;  // Variable para referenciar el MainMenu

    // Constructor que acepta el objeto MainMenu
    public LoginGUI(MainMenu mainMenu) {
        this.mainMenu = mainMenu;

        // Configuración de la interfaz
        setTitle("Iniciar Sesión");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Cambiar a DISPOSE_ON_CLOSE para solo cerrar la ventana de login
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        userField = new JTextField(20);
        passField = new JPasswordField(20);
        loginButton = new JButton("Iniciar Sesión");

        add(new JLabel("Usuario:"));
        add(userField);
        add(new JLabel("Contraseña:"));
        add(passField);
        add(loginButton);

        // Acción para iniciar sesión
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                // Verificar las credenciales
                if (verificarCredenciales(username, password)) {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Inicio de sesión exitoso: " + username);
                    mainMenu.updatePlayButtonStatus(true);  // Habilitar el botón "Jugar" en el MainMenu
                    dispose(); // Solo cierra esta ventana, no el menú principal
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private static boolean verificarCredenciales(String username, String password) {
        // Lógica para verificar las credenciales (puedes implementarlo como en tu código original)
        return true;
    }
}
