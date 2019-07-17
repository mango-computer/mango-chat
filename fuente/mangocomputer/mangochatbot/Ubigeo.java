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
public class Ubigeo 
{
    private int codEntidad;
    private int codMunicipio;
    private int codParroquia;

    public Ubigeo() 
    {
        codEntidad=0;
        codMunicipio=0;
        codParroquia=0;
    }

    public int getCodEntidad() {
        return codEntidad;
    }

    public void setCodEntidad(int codEntidad) {
        this.codEntidad = codEntidad;
    }

    public int getCodMunicipio() {
        return codMunicipio;
    }

    public void setCodMunicipio(int codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    public int getCodParroquia() {
        return codParroquia;
    }

    public void setCodParroquia(int codParroquia) {
        this.codParroquia = codParroquia;
    }

    public boolean isTieneCodEntidad()
    {
        return (codEntidad>0);
    }
    
    public boolean isTieneCodMunicipio()
    {
        return (codMunicipio>0);
    }
    
    public boolean isTieneCodParroquia()
    {
        return (codParroquia>0);
    }

}
