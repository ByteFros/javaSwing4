/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aaar.juegosmesa;

import aaar.juegosmesa.gui.menu.MainMenuGUI;
import javax.swing.SwingUtilities;

/**
 *
 * @author sini
 */
public class JuegosMesa {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenuGUI().setVisible(true);
            }
        });
    }
}
