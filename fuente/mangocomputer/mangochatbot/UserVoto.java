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
public class UserVoto 
{
    private int idVoto;
    private int idUser;
    private int idDt;
    private int voto;
    private String fecha;
    private boolean existeData;

    public UserVoto() 
    {
        idVoto=0;
        idUser=0;
        idDt=0;
        voto=0;
        fecha="";
        existeData=false;
    }

    public int getIdVoto() {
        return idVoto;
    }

    public void setIdVoto(int idVoto) {
        this.idVoto = idVoto;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDt() {
        return idDt;
    }

    public void setIdDt(int idDt) {
        this.idDt = idDt;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isExisteData() {
        return existeData;
    }

    public void setExisteData(boolean existeData) {
        this.existeData = existeData;
    }
    
    
    
}
