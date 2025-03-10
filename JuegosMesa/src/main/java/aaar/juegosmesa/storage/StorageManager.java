/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aaar.juegosmesa.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rmartin
 */
public class StorageManager {
    static final String USER_PREFS_FILENAME = ".userPrefs"; // user prefs file (relative path).
    static final String HIGHSCORES_FILENAME = ".highscores.csv"; // highscores file (relative path).
    static final String USERS_FILENAME = ".users.csv"; // highscores file (relative path).

    private static StorageManager instance;
    
    private Path storageDirectoryPath;
    private Path highscoresFilePath;
    private Path usersFilePath;
    
    private StorageManager() {}
    
    public static StorageManager getInstance() {
        if (instance == null) {
            instance = new StorageManager();
            try {
                final File userPrefsFile = Paths.get(
                        StorageManager.getUserPrefsFileName()
                ).toFile();
                if (userPrefsFile.isFile()) {
                    String databaseDirectory = null;
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
                    
                    if (new File(databaseDirectory).isDirectory()) {
                        instance.setStorageDirectoryPath(
                                Paths.get(databaseDirectory)
                        );
                        instance.setHighscoresFilePath(
                            instance.storageDirectoryPath.resolve(
                                StorageManager.getHighscoresFileName()
                            )
                        );
                        instance.setUsersFilePath(
                            instance.storageDirectoryPath.resolve(
                                StorageManager.getUsersFileName()
                            )
                        );
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return instance;
    }
    
    public static String getUserPrefsFileName()
    {
        return USER_PREFS_FILENAME;
    }
    
    public Path getStorageDirectoryPath()
    {
        return storageDirectoryPath;
    }
    public boolean setStorageDirectoryPath(Path path)
    {
        boolean success = path.toFile().isDirectory();
        if (success) storageDirectoryPath = path;
        return success;
    }
    
    public static String getHighscoresFileName()
    {
        return HIGHSCORES_FILENAME;
    }
    public Path getHighscoresFilePath()
    {
        return highscoresFilePath.toAbsolutePath();
    }
    public boolean setHighscoresFilePath(Path path)
    {
        boolean success = path.toFile().isFile();
        if (success) highscoresFilePath = path;
        return success;
    }
    
    public static String getUsersFileName()
    {
        return USERS_FILENAME;
    }
    public Path getUsersFilePath()
    {
        return usersFilePath.toAbsolutePath();
    }
    public boolean setUsersFilePath(Path path)
    {
        boolean success = path.toFile().isFile();
        if (success) usersFilePath = path;
        return success;
    }
    
}
