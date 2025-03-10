/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aaar.juegosmesa;

import aaar.juegosmesa.gui.dblocation.JDatabaseFilePathFrame;
import aaar.juegosmesa.gui.menu.MainMenuGUI;
import aaar.juegosmesa.storage.StorageManager;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author rmartin
 */
public class JuegosMesa {
    public static void main(String[] args) {
        // FlatLaf
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
        
        // get userPrefs file's relative path
        final String userPrefsFileName = StorageManager.getUserPrefsFileName();
        Path userPrefsRelativePath = Paths.get(
                userPrefsFileName
        );
        File userPrefsFile = userPrefsRelativePath.toFile();
        boolean userPrefsFileExists = userPrefsFile.isFile();
        
        String databaseDirectory = null;
        if (userPrefsFileExists) {
            // read user prefs file to get database path
            List<String> userPrefsFileLines = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(
                        new FileReader(
                                userPrefsFile.getAbsolutePath()
                        )
                );
                String line;
                while ((line = br.readLine()) != null) {
                    userPrefsFileLines.add(line);
                }
                databaseDirectory = userPrefsFileLines.get(0);
                br.close();
            } catch (Exception e) {
                System.err.println(
                        "Error al intentar leer las preferencias de usuario: "
                        + e.getMessage()
                );
            }
        } else {
            // try to create user prefs file.
            try {
                String initialContent = "";
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(userPrefsFileName)
                );
                writer.write(initialContent);
                writer.close();
            } catch (Exception e) {
                System.err.println(
                        "Error al intentar crear archivo de preferencias de usuario: "
                        + e.getMessage()
                );
            }
        }
        
        final boolean dbDirectoryMissing =
                databaseDirectory == null
                || !(new File(databaseDirectory).isDirectory())
        ;
        // if database directory path was not found in user prefs OR no database file exists there,
        // then ask user to set a new location for the DB file.
        if ( dbDirectoryMissing ) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new JDatabaseFilePathFrame().setVisible(true);
                }
            });
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MainMenuGUI().setVisible(true);
                }
            });
        }
    }
}
