/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aaar.juegosmesa.storage;

import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author rmartin
 */
public class StorageManager {
    static final String USER_PREFS_FILENAME = ".userPrefs"; // user prefs file (relative path).
    static final String HIGHSCORES_FILENAME = ".highscores.csv"; // highscores file (relative path).
    static final String USERS_FILENAME = ".users.csv"; // highscores file (relative path).

    private static StorageManager instance;
    
    private Path highscoresFilePath;
    private Path usersFilePath;
    
    private StorageManager() {}
    
    public static StorageManager getInstance() {
        if (instance == null) instance = new StorageManager();
        return instance;
    }
    
    public static String getUserPrefsFileName()
    {
        return USER_PREFS_FILENAME;
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
