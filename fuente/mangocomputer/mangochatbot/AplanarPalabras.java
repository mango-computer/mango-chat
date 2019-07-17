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
public class AplanarPalabras 
{
    private String frase;
    private ArrayList<String> listaPalabras;
    private Lenguaje lengua;
            
    public AplanarPalabras(Lenguaje lengua) 
    {
        frase = "";
        this.lengua = lengua;
        listaPalabras = new ArrayList<>();
    }

    public void setFrase(String frase) 
    {
        this.frase = frase.toLowerCase();
        this.frase = this.frase.trim();
    }
    
    public void convertirPalabrasLargas()
    {
        Iterator iter = lengua.getArrayPalabrasLargas().keySet().iterator();
        String palabraLarga;
        int iniPalabra;
        String frase1, frase2, palabraCorta;

        
        while (iter.hasNext())
        {
            palabraLarga = (String)iter.next();
            
            if (frase.contains(palabraLarga))
            {
                iniPalabra = frase.indexOf(palabraLarga);
            
                frase1 = frase.substring(0, iniPalabra);
                frase2 = frase.substring(iniPalabra + palabraLarga.length());
                palabraCorta = lengua.getArrayPalabrasLargas().get(palabraLarga);
                frase = frase1 + palabraCorta + " " + frase2;             
            }
        }
    }
    
    public ArrayList<String> getFraseAplanada()
    {
        //quitarCaracteresEspeciales();
        //quitarRepeticionesLetrasPorPalabra();
        listaPalabras.clear();
        
        String temp[] = frase.split(" ");
        for (String palabra : temp)
        {
            if (!palabra.isEmpty())
            {
                listaPalabras.add(palabra);
            }
        }
        
        return listaPalabras;
    }

    public void quitarRepeticionesLetrasPorPalabra()
    {

        //Letras repetidas
        String splitFrase[] = frase.split(" ");
        frase="";
        
        for (int i=0;i< splitFrase.length;i++)
        {
            if (i>0)
                frase = frase + " " + quitarLetrasRepetidas(splitFrase[i]);
            else
                frase = frase + quitarLetrasRepetidas(splitFrase[i]);
        }        
    }
    
    public ArrayList<String> eliminarLiteralesArray(ArrayList<String>  a)
    {
        ArrayList<String> array = (ArrayList<String>)a.clone();
        
        Iterator<String> iter = array.iterator();
        String palabra;
        
        while(iter.hasNext())
        {
            palabra = (String) iter.next();
            
            if (lengua.existeArticulo(palabra))
            {
                iter.remove();
            }
        }
        return array;
    }
    
    public void quitarCaracteresEspeciales()
    {
        
        frase = frase.replace('á', 'a');
        frase = frase.replace('é', 'e');
        frase = frase.replace('í', 'i');
        frase = frase.replace('ó', 'o');
        frase = frase.replace('ú', 'u');
        
        //Quita todo lo que no sea letras o numeros
        frase = frase.replaceAll("[^a-zñ0-9]", " ");
        
    }
    
    private String quitarLetrasRepetidas(String palabra)
    {
//        System.out.println("antes (" + palabra + ") len:"+ palabra.length());
        
        if (palabra.length()==0) return "";

        palabra = palabra.trim();
        
        StringBuilder palabraNueva = new StringBuilder();
        
        char letraLeida=' ';
        int contador=0;

        char arrayPalabra[] = palabra.toCharArray();
        for (int i=0;i<arrayPalabra.length;i++)
        {
            if ((letraLeida == arrayPalabra[i]) && (!Character.isDigit(letraLeida)))
            {
                contador++;

                if (contador<3)  //((contador==2) && ((letraLeida == 'r') || (letraLeida == 'c') || (letraLeida == 'l'))) 
                {
                    palabraNueva.append(arrayPalabra[i]);
                }

            } else {

                palabraNueva.append(arrayPalabra[i]);
                contador=1;
            }
            
            letraLeida = arrayPalabra[i];
        }
        
//        System.out.println("despues (" + palabraNueva.toString()+")");

        return palabraNueva.toString();
        
    }
}
