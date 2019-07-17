/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

import java.util.ArrayList;

/**
 *
 * @author mm
 */
public class Preferencias {
    
    private ArrayList<ContextoPreferencia> arrayContexto;

    public Preferencias() 
    {
        arrayContexto = new ArrayList<>();
    }


    public ArrayList<ContextoPreferencia> getArrayContexto() 
    {
        return arrayContexto;
    }
    
    public void addContexto(ContextoPreferencia contex)
    {
        arrayContexto.add(contex);
    }
}
