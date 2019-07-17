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
public class DataResultado 
{
    private String nombreOficial;
    private String popularidad;
    private String status;
    private String horario;
    private String telefono;
    private boolean tieneTelefono;
    private boolean tieneHorario;
    private String comentario;
    private boolean tieneComentario;
    private String foto;
    private boolean tieneFoto;
    private String direccion;
    private String mapa;
    private String distancia;
    private String paginaWeb;
    private boolean tienePaginaWeb;

    public DataResultado() 
    {
        tieneHorario = false;
        tieneComentario = false;
        tieneFoto = false;
        tienePaginaWeb = false;
        nombreOficial="";
        popularidad="";
        status="";
        horario="";
        comentario="";
        foto="";
        direccion="";
        mapa="";
        distancia="";
        paginaWeb="";
    }
    
    public String getParte(int parte)
    {
        String resul="";
        
        if (parte==1)
        {
            resul = nombreOficial + "<br>"+popularidad+"<br>"+status+"<br>"+distancia;
        
        } else {
            
            if (tieneHorario)
            {
                resul = horario + "<br>";
            }

            if (tieneFoto)
            {
                if (resul.isEmpty())
                {
                    resul = foto + "<br>";
                    
                } else {
                    
                    resul = resul + foto + "<br>";
                }
            }
            
            if (tieneTelefono)
            {
                if (resul.isEmpty())
                {
                    resul = telefono + "<br>";
                    
                } else {
                    
                    resul = resul + telefono + "<br>";
                }
            }

            if (tieneComentario)
            {
                if (resul.isEmpty())
                {
                    resul = comentario + "<br>";
                    
                } else {
                    
                    resul = resul + comentario + "<br>";
                }
            }
            
            resul = resul + direccion+"<br>"+mapa+"<br>";
            
            if (tienePaginaWeb)
            {
                resul = resul + paginaWeb;
            }
        }
        
        return resul;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
        this.tieneTelefono = true;
    }
    
    

    public boolean isTieneHorario() {
        return tieneHorario;
    }

    public boolean isTieneComentario() {
        return tieneComentario;
    }

    public boolean isTieneFoto() {
        return tieneFoto;
    }

    public boolean isTienePaginaWeb() {
        return tienePaginaWeb;
    }
    
    public String getNombreOficial() {
        return nombreOficial;
    }

    public void setNombreOficial(String nombreOficial) {
        this.nombreOficial = nombreOficial;
    }

    public String getPopularidad() {
        return popularidad;
    }

    public void setPopularidad(String popularidad) {
        this.popularidad = popularidad;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
        this.tieneHorario=true;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
        this.tieneComentario=true;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
        this.tieneFoto=true;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMapa() {
        return mapa;
    }

    public void setMapa(String mapa) {
        this.mapa = mapa;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
        this.tienePaginaWeb=true;
    }
    
    
    
    
}
