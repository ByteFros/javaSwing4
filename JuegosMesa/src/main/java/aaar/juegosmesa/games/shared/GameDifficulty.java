/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package aaar.juegosmesa.games.shared;

import aaar.juegosmesa.lang.LanguageManager;

/**
 *
 * @author sini
 */
public enum GameDifficulty {
    EASY,
    MEDIUM,
    HARD;
    
    public String toString() {
        return LanguageManager.getInstance().getString(
                this.name().toLowerCase()
        );
    }
}
