package idiomas;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LanguageManager {
    private static LanguageManager instance;
    private ResourceBundle messages;
    private Locale currentLocale;

    private LanguageManager() {
        // Idioma por defecto (Català)
        changeLanguage("Català");
    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    public void changeLanguage(String language) {
        switch (language) {
            case "Español":
                currentLocale = new Locale("es", "ES");
                break;
            case "Català":
                currentLocale = new Locale("ca", "ES");
                break;
            case "English":
                currentLocale = new Locale("en", "US");
                break;
            default:
                currentLocale = new Locale("ca", "ES");
        }

        try {
            messages = ResourceBundle.getBundle("messages", currentLocale);
        } catch (MissingResourceException e) {
            System.err.println("Archivo de idioma no encontrado: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public ResourceBundle getMessages() {
        return messages;
    }

    public String getString(String key) {
        return messages.getString(key);
    }
}