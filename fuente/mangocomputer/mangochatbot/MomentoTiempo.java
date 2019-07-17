/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author mm
 */
public class MomentoTiempo {

    public static enum MOMENTO_DIA {MANANA, MEDIO_DIA, TARDE, NOCHE, MADRUGADA};
    public static enum DIAS {LUNES, MARTES, MEIRCOLES, JUEVES, VIERNES, SABADO, DOMINGO};
    private Date date;
    private Calendar cal;
    
    public MomentoTiempo() {
        
        date = new Date();
        cal = Calendar.getInstance();
    }

    public Date getDate() {
        return date;
    }
    

    public MOMENTO_DIA getMomentoDia()
    {
        MOMENTO_DIA respuesta = MOMENTO_DIA.MANANA;
        
        int hora = cal.get(Calendar.HOUR_OF_DAY);
        if ((hora>4) && (hora<12)) respuesta = MOMENTO_DIA.MANANA;
        if ((hora>11) && (hora<14)) respuesta = MOMENTO_DIA.MEDIO_DIA;
        if ((hora>13) && (hora<18)) respuesta = MOMENTO_DIA.TARDE;
        if ((hora>17) && (hora<25)) respuesta = MOMENTO_DIA.NOCHE;
        if ((hora>=0) && (hora<5)) respuesta = MOMENTO_DIA.MADRUGADA;
        
        return respuesta;
    }
    
    
}
