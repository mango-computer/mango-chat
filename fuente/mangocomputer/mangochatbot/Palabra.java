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
public class Palabra 
{
    private String palabra="";
    private String palabraMostrar="";
    private int idContexto=0;
    private String semantica="";
    private int tipo=0;
    private String strIdContexto="0";
    private int idBolsaPalabra=0;
    private int idCategoria;
    private Ubigeo ubigeo;
    
    /*
        tipo:   1 lugar, 2 producto, 3 caracteristica, 4 ubicacion-ubigeo-entidad, 
                5 ubicacion-ubigeo-entidad-municipio, 6 ubicacion-ubigeo-entidad-municipio-parroquia
    */
    
    public Palabra(String palabra, String palabraMostrar, String semantica, int tipo, int idCategoria, int idContexto, int idBolsaPalabra)
    {
        this.palabra            = palabra;
        this.palabraMostrar     = palabraMostrar;
        this.semantica          = semantica;
        this.tipo               = tipo;
        this.idContexto         = idContexto;
        this.strIdContexto      = Integer.toString(idContexto);
        this.idBolsaPalabra     = idBolsaPalabra;
        this.idCategoria        = idCategoria;
        ubigeo = null;
    }

    public boolean isTieneUbigeo()
    {
        return (ubigeo!=null);
    }

    public Ubigeo getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(Ubigeo ubigeo) {
        this.ubigeo = ubigeo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public int getIdBolsaPalabra() {
        return idBolsaPalabra;
    }
    
    public boolean isCategoria()
    {
        return (idCategoria>0);
    }
    
    public String getStrIdContexto() {
        return strIdContexto;
    }
    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getPalabraMostrar() {
        return palabraMostrar;
    }

    public void setPalabraMostrar(String palabraMostrar) {
        this.palabraMostrar = palabraMostrar;
    }

    public int getIdContexto() {
        return idContexto;
    }

    public void setIdContexto(int idContexto) {
        this.idContexto = idContexto;
    }

    public String getSemantica() {
        return semantica;
    }

    public void setSemantica(String semantica) {
        this.semantica = semantica;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }


    
}
