package autentificacion;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JFrame {
    public RegisterGUI() {
        setTitle("Registre");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("Usuari:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Contrasenya:");
        JPasswordField passField = new JPasswordField();
        JButton registerButton = new JButton("Registrar");
        JButton cancelButton = new JButton("Cancel·lar");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(registerButton);
        panel.add(cancelButton);

        add(panel);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                // Aquí puedes agregar la lógica para registrar el usuario
                JOptionPane.showMessageDialog(RegisterGUI.this, "Usuari registrat: " + username);
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
