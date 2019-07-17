/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose Andres Morales Linares
 */
public class SessionChat 
{
    private ArrayList<DataResultado> arrayListDataResultados;
    private int indiceArrayListDataResultados;
    private int parteIndiceArrayListDataResultados;


    private double latitud;
    private double longitud;
    private int radioBusqueda;
    private int idMensajeHistorial;
    private int intentosIniSession;
    
    private int limiteResultados;   
    
    private String palabraDesconocida;
    private String palabraSugerida;
    
    private EstadoConversacion estadoConversacion;
    private ArrayList<String> arrayrespuesta;
    
    private EstadoChat estadoChatBot;
    private boolean esValida = false;
    
    private String nombreUser;
    private String clave;
    
    private String nombre;
    private int idUser;
    
    private String nuevoUser;
    private String claveNuevoUser;
    private String nombreCompletoNuevoUser;
    private int paisNuevoUser;
    private String correoNuevoUser;
    
    private ArrayList<NodoFrase> historialFrases; 
    private ArrayList<String> palabrasNuevas ;
    private ArrayList<String> palabrasBolsa ;
    
    public SessionChat() 
    {
        estadoChatBot = EstadoChat.ESPERANDO_NOMBRE_USER;
        
        arrayListDataResultados = new ArrayList<>(20);
        indiceArrayListDataResultados=0;
        parteIndiceArrayListDataResultados=0;


        esValida=false;
        historialFrases = new ArrayList(100);
        palabrasNuevas  = new ArrayList<>(100);
        palabrasBolsa  = new ArrayList<>(100);
        
        arrayrespuesta = new ArrayList<>(100);
        estadoConversacion = new EstadoConversacion();
        
        radioBusqueda = 10000;
        latitud = 0;
        longitud =0;
        intentosIniSession=0;
    }
    
    public SessionChat(String correo, String clave) 
    {
        estadoChatBot = EstadoChat.ESPERANDO_NOMBRE_USER;
        arrayListDataResultados = new ArrayList<>(20);
        indiceArrayListDataResultados=0;
        parteIndiceArrayListDataResultados=0;


        esValida=false;
        historialFrases = new ArrayList(100);
        palabrasNuevas  = new ArrayList<>(100);
        palabrasBolsa  = new ArrayList<>(100);
        
        arrayrespuesta = new ArrayList<>(100);
        estadoConversacion = new EstadoConversacion();
        
        radioBusqueda = 10000;
        latitud = 10.2491163;
        longitud =-67.5859457;
        intentosIniSession =0;
       
        validarInicioSession(correo, clave);
        if (esValida)
        {
            estadoChatBot = EstadoChat.SIN_ESTADO;
        }
    }

    public ArrayList<DataResultado> getArrayListDataResultados() {
        return arrayListDataResultados;
    }

    public int getIndiceArrayListDataResultados() {
        return indiceArrayListDataResultados;
    }

    public int getParteIndiceArrayListDataResultados() {
        return parteIndiceArrayListDataResultados;
    }

    public void setIndiceArrayListDataResultados(int indiceArrayListDataResultados) {
        this.indiceArrayListDataResultados = indiceArrayListDataResultados;
    }

    public void setParteIndiceArrayListDataResultados(int parteIndiceArrayListDataResultados) {
        this.parteIndiceArrayListDataResultados = parteIndiceArrayListDataResultados;
    }

    public void resetArrayListDataResultados()
    {
        arrayListDataResultados.clear();
        indiceArrayListDataResultados=0;
        parteIndiceArrayListDataResultados=0;
    }
    
    public int getLimiteResultados() {
        return limiteResultados;
    }

    public void setLimiteResultados(int limiteResultados) {
        this.limiteResultados = limiteResultados;
    
        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            PreparedStatement sentencia = null;
            
            String query = "UPDATE user SET limiteResultados=? WHERE idUser=?";
            
            sentencia = conn.prepareStatement(query);
            sentencia.setInt(1, limiteResultados);
            sentencia.setInt(2, this.idUser);
            
            sentencia.executeUpdate();

            sentencia.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public String getCorreoNuevoUser() {
        return correoNuevoUser;
    }

    public void setCorreoNuevoUser(String correoNuevoUser) {
        this.correoNuevoUser = correoNuevoUser;
    }

    public String getNombreCompletoNuevoUser() {
        return nombreCompletoNuevoUser;
    }

    public void setNombreCompletoNuevoUser(String nombreCompletoNuevoUser) {
        this.nombreCompletoNuevoUser = nombreCompletoNuevoUser;
    }

    public int getPaisNuevoUser() {
        return paisNuevoUser;
    }

    public void setPaisNuevoUser(int paisNuevoUser) {
        this.paisNuevoUser = paisNuevoUser;
    }

    public String getNuevoUser() {
        return nuevoUser;
    }

    public void setNuevoUser(String nuevoUser) {
        this.nuevoUser = nuevoUser;
    }

    public String getClaveNuevoUser() {
        return claveNuevoUser;
    }

    public void setClaveNuevoUser(String claveNuevoUser) {
        this.claveNuevoUser = claveNuevoUser;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    

    public int getIntentosIniSession() {
        return intentosIniSession;
    }

    public void contarIntentoIniSession() {
        this.intentosIniSession++;
    }
    
    public void resetIntentoIniSession() {
        this.intentosIniSession=0;
    }

    public int getIdMensajeHistorial() {
        return idMensajeHistorial;
    }

    public void setIdMensajeHistorial(int idMensajeHistorial) {
        this.idMensajeHistorial = idMensajeHistorial;
    }

    public String getPalabraDesconocida() {
        return palabraDesconocida;
    }

    public void setPalabraDesconocida(String palabraDesconocida) {
        this.palabraDesconocida = palabraDesconocida;
    }

    public String getPalabraSugerida() {
        return palabraSugerida;
    }

    public void setPalabraSugerida(String palabraSugerida) {
        this.palabraSugerida = palabraSugerida;
    }
    
    public void actualizarCoordenadasGps(double latitud, double longitud)
    {
        this.latitud = (latitud==0?10.2491163:latitud);
        this.longitud = (longitud==0?-67.5859457:longitud);
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getRadioBusqueda() {
        return radioBusqueda;
    }

    public void setRadioBusqueda(int radioBusqueda) {
        this.radioBusqueda = radioBusqueda;
        
        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            PreparedStatement sentencia = null;
            
            String query = "UPDATE user SET radioBusqueda=? WHERE idUser=?";
            
            sentencia = conn.prepareStatement(query);
            sentencia.setInt(1, radioBusqueda);
            sentencia.setInt(2, this.idUser);
            
            sentencia.executeUpdate();

            sentencia.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    

    public EstadoConversacion getEstadoConversacion() {
        return estadoConversacion;
    }

    
    public ArrayList<String> getArrayrespuesta() 
    {
        return arrayrespuesta;
    }

    public ArrayList<String> getPalabrasNuevas() {
        return palabrasNuevas;
    }
    
    public void resetArrayPalabrasNuevas()
    {
        palabrasNuevas.clear();
    }
    
    public void addPalabraNueva(String palabra)
    {
        palabrasNuevas.add(palabra);
    }

    public ArrayList<String> getPalabrasBolsa() {
        return palabrasBolsa;
    }

    public void addPalabrasBolsa(String palabra) {
        palabrasBolsa.add(palabra);
    }
    
    public void resetArrayPalabraBolsa()
    {
        palabrasBolsa.clear();
    }
    

    public NodoFrase getNodoFrase() 
    {
        
        NodoFrase resul=null;
        
        if (historialFrases.size()>0)
        {
            resul = (NodoFrase)historialFrases.get(historialFrases.size()-1);
        } 
        return resul;
    }
    
    public NodoFrase getArrayFrases(int pos) {
        
        NodoFrase resul=null;
        
        resul = (NodoFrase)historialFrases.get(pos);
        return resul;
    }

    public int getCantidadFrases()
    {
        return historialFrases.size();
    }
 
    public void addFrases(ArrayList<String> palabras, ArrayList<String> palabrasAplanadas, String frase) 
    {
        historialFrases.add(new NodoFrase(frase, palabrasAplanadas, palabras));
    }

    public String getNombre() {
        return nombre;
    }

    
    public void iniciarSession()
    {
        if (!esValida)
        {
            if ((nombreUser!=null) && (clave!=null))
            {
                validarInicioSession(nombreUser, clave);
            }
        }
    }

    private void validarInicioSession(String nombreUser, String clave)
    {
        
        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            //Statement stmt = conn.createStatement();
            PreparedStatement sentencia = null;
            
            //String query = "SELECT * FROM user WHERE userName=\"" + nombreUser + "\" AND clave=\"" + clave + "\"";
            String query = "SELECT * FROM user WHERE userName=? AND clave=?";
            
            sentencia = conn.prepareStatement(query);
            sentencia.setString(1, nombreUser);
            sentencia.setString(2, clave);
            
            ResultSet rs = sentencia.executeQuery();
            //ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next())
            {
                esValida=true;
                this.nombre = rs.getString("nombre");
                this.nombreUser = nombreUser;
                this.idUser = rs.getInt("idUser");
                this.radioBusqueda = rs.getInt("radioBusqueda");
                this.limiteResultados = rs.getInt("limiteResultados");
            } 
            
            rs.close();
            sentencia.close();
            //stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public int getIdUser() {
        return idUser;
    }
    
    
    
    public String getnombreUser() {
        return nombreUser;
    }


    public boolean isEsValida() {
        return esValida;
    }

    public void setEsValida(boolean esValida) {
        this.esValida = esValida;
    }

    public EstadoChat getEstadoChatBot() {
        return estadoChatBot;
    }

    public void setEstadoChatBot(EstadoChat estadoChatBot) {
        this.estadoChatBot = estadoChatBot;
    }
    
}
