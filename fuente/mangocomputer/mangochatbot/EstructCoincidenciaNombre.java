/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

/**
 *
 * @author mm
 */
public class EstructCoincidenciaNombre 
{
    
    private int idDt;
    private String nombreDt;
    private boolean existe;

    public EstructCoincidenciaNombre()
    {
        idDt = 0;
        nombreDt="";
        existe=false;
    }

    public EstructCoincidenciaNombre(int idDt, String nombreDt) 
    {
        this.idDt = idDt;
        this.nombreDt = nombreDt;
        this.existe = false;
    }

    public int getIdDt() {
        return idDt;
    }

    public void setIdDt(int idDt) {
        this.idDt = idDt;
    }

    public String getNombreDt() {
        return nombreDt;
    }

    public void setNombreDt(String nombreDt) {
        this.nombreDt = nombreDt;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }
    
    
    
    
}
