package autentificacion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AuthGUI {
    private static final String filePath = "usuarios.csv";

    // Método para guardar el usuario en el archivo CSV
    public static void guardarUsuarioEnCSV(String username, String password) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(username).append(",").append(password).append("\n");
        } catch (IOException ex) {
            System.out.println("Error al guardar el usuario");
        }
    }

    // Verificar si el usuario ya existe en el archivo CSV
    public static boolean usuarioExiste(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].equals(username)) {
                    return true;  // El usuario ya existe
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo de usuarios");
        }
        return false;  // El usuario no existe
    }

    // Verificar las credenciales para iniciar sesión
    public static boolean verificarCredenciales(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true;  // Las credenciales son correctas
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo de usuarios");
        }
        return false;  // Las credenciales son incorrectas
    }
}
