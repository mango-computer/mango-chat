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
public class EstadoConversacion {
    
    
    private EstructCoincidenciaNombre coincidenciaNombre;

    private int idContexto=0;
    private int idCategoria=0;
    private String strContexto="";
    private ArrayList<Integer> arrayLugares;
    private ArrayList<Integer> arrayProductos;
    private ArrayList<Integer> arrayCaracteristicas;
    private boolean variosContextos=false;
    private Ubigeo ubigeo;
        

    public EstadoConversacion() {
        arrayProductos = new ArrayList<>();
        arrayCaracteristicas = new ArrayList<>();
        arrayLugares = new ArrayList<>();
        ubigeo = null;
    }

    public Ubigeo getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(Ubigeo ubigeo) {
        this.ubigeo = ubigeo;
    }
    
    public boolean isTieneUbigeo()
    {
        return (ubigeo!=null);
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    public boolean isCategoria()
    {
        return (idCategoria>0);
    }

    public EstructCoincidenciaNombre getCoincidenciaNombre() {
        return coincidenciaNombre;
    }

    public void setCoincidenciaNombre(EstructCoincidenciaNombre coincidenciaNombre) {
        this.coincidenciaNombre = coincidenciaNombre;
    }
    
    public void resetCoincidenciaNombre()
    {
        this.coincidenciaNombre = null;
    }


    public String getStrContexto() {
        return strContexto;
    }

    public void setStrContexto(String strContexto) {
        this.strContexto = strContexto;
    }
       

    public boolean isVariosContextos() {
        return variosContextos;
    }

    public void setVariosContextos(boolean variosContextos) {
        this.variosContextos = variosContextos;
    }
    
    public int getIdContexto() {
        return idContexto;
    }

    public void setIdContexto(int idContexto) {
        this.idContexto = idContexto;
    }
    
    public boolean isContexto()
    {
        return (idContexto>0);
    }
    
    public void resetContexto()
    {
        idContexto=0;
    }
    
    public void resetCategoria()
    {
        idCategoria=0;
    }
    
    public void addProducto(int producto)
    {
        if (!arrayProductos.contains(producto))
        {
            arrayProductos.add(producto);
        }
    }
    
    public ArrayList<Integer> getArrayProductos()
    {
        return arrayProductos;
    }
    
    public boolean isProductos()
    {
        return (arrayProductos.size()>0);
    }
    
    public void addCaracteristica(int corrimiento)
    {
        if (!arrayCaracteristicas.contains(corrimiento))
        {
            arrayCaracteristicas.add(corrimiento);
        }
    }
    
    public ArrayList<Integer> getArrayCaracteristicas()
    {
        return arrayCaracteristicas;
    }
    
    public boolean isCaracteristicas()
    {
        return (arrayCaracteristicas.size()>0);
    }

    public void addLugar(int lugar)
    {
        if (!arrayLugares.contains(lugar))
        {
            arrayLugares.add(lugar);
        }
    }
    
    public ArrayList<Integer> getArrayLugares()
    {
        return arrayLugares;
    }
    
    public boolean isLugar()
    {
        return (arrayLugares.size()>0);
    }

    public void resetEstadoConversacion()
    {
        idContexto =0;
        idCategoria=0;
        arrayProductos.clear();
        arrayCaracteristicas.clear();
        arrayLugares.clear();
        strContexto="";
        variosContextos=false;
        ubigeo = null;
    }
}
