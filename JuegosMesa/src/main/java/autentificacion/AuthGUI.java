package autentificacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthGUI extends JFrame {
    public AuthGUI() {
        setTitle("Iniciar Sessió");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Usuari:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Contrasenya:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Iniciar Sessió");
        JButton registerButton = new JButton("Registrar");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                // Aquí puedes agregar la lógica para iniciar sesión
                JOptionPane.showMessageDialog(AuthGUI.this, "Inici de sessió exitós: " + username);
                dispose();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterGUI().setVisible(true); // Abre la interfaz de registro
            }
        });
    }
}