/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

/**
 *
 * @author Jose Andres Morales Linares
 */
public class PalabraCorregida {
    
    private String palabraOriginal;
    private String palabraCorregida;

    public PalabraCorregida(String palabraOriginal, String palabraCorregida) {
        this.palabraOriginal = palabraOriginal;
        this.palabraCorregida = palabraCorregida;
    }

    public String getPalabraOriginal() {
        return palabraOriginal;
    }

    public void setPalabraOriginal(String palabraOriginal) {
        this.palabraOriginal = palabraOriginal;
    }

    public String getPalabraCorregida() {
        return palabraCorregida;
    }

    public void setPalabraCorregida(String palabraCorregida) {
        this.palabraCorregida = palabraCorregida;
    }

    
    
    
}
