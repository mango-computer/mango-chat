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
public class ContextoPreferencia 
{
    private int idContexto;
    private ArrayList<String> arrayLugares;
    private ArrayList<String> arrayProductos;
    private ArrayList<String> arrayCaracteristicas;

    public ContextoPreferencia(int idContexto) 
    {
        this.idContexto = idContexto;
        arrayLugares = new ArrayList<>();
        arrayProductos = new ArrayList<>();
        arrayCaracteristicas = new ArrayList<>();

    }

    public int getIdContexto() 
    {
        return idContexto;
    }

    public ArrayList<String> getArrayLugares() 
    {
        return arrayLugares;
    }
    
    public void addLugar(String lugar)
    {
        arrayLugares.add(lugar);
    }

    public ArrayList<String> getArrayProductos() 
    {
        return arrayProductos;
    }

    public void addProducto(String producto) 
    {
        arrayProductos.add(producto);
    }

    public ArrayList<String> getArrayCaracteristicas() 
    {
        return arrayCaracteristicas;
    }
    
    public void addCaracteristica(String caracteristica)
    {
        arrayCaracteristicas.add(caracteristica);
    }
    
}
