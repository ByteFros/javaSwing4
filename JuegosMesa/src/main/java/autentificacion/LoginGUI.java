package autentificacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import menu.MainMenu;

public class LoginGUI extends JFrame {
    private MainMenu mainMenu;

    public LoginGUI(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
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

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginButton);
        panel.add(new JLabel()); // Empty label for grid alignment

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                
                // Here you would validate the credentials
                boolean loginSuccessful = true; // Replace with actual validation
                
                if (loginSuccessful) {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Inici de sessió exitós: " + username);
                    mainMenu.setLoggedIn(true); // Enable play button
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Error d'inici de sessió", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
