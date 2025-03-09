/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aaar.juegosmesa.helpers;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
 *
 * @author sini
 */
public class PathHelperFunctions {
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return !path.isBlank();
    }
}
