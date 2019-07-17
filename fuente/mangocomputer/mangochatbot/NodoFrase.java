/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author mm
 */
public class NodoFrase {
    
    
    private String fraseOriginal;
    private ArrayList<String> arrayPalabras;
    private ArrayList<String> arrayPalabrasAplanadas;
   
    private String fraseVector[];
    private String fraseVectorAplanado[];

    public NodoFrase(String fraseOriginal, ArrayList<String> arrayPalabrasAplanadas, ArrayList<String> arrayPalabras) {

        this.fraseOriginal = fraseOriginal;
        this.arrayPalabras = (ArrayList<String>) arrayPalabras.clone();
        this.arrayPalabrasAplanadas = (ArrayList<String>) arrayPalabrasAplanadas.clone();
        
        fraseVector = new String[arrayPalabras.size()];
        Iterator iter = arrayPalabras.iterator();
        int i=0;
        while(iter.hasNext())
        {
            fraseVector[i] = (String) iter.next();
            i++;
        }

        fraseVectorAplanado = new String[arrayPalabrasAplanadas.size()];
        iter = arrayPalabrasAplanadas.iterator();
        i=0;
        while(iter.hasNext())
        {
            fraseVectorAplanado[i] = (String) iter.next();
            i++;
        }

    }

    public String[] getFraseVectorAplanado() {
        return fraseVectorAplanado;
    }
    
    
    
    public String[] getFraseVector()
    {
        return fraseVector;
    }

    public String getFraseOriginal() {
        return fraseOriginal;
    }

    public ArrayList<String> getArrayPalabras() {
        return arrayPalabras;
    }

    public void setArrayPalabras(ArrayList<String> arrayPalabras) {
        this.arrayPalabras = arrayPalabras;
    }
    
        public ArrayList<String> getArrayPalabrasAplanadas() {
        return arrayPalabrasAplanadas;
    }
    
    
    
}
