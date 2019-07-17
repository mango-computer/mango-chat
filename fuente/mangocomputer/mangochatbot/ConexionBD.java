/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;


/**
 *
 * @author mm
 */

public class ConexionBD 
{
    
    private String bd;
    private String url;
    private String user;
    private String password;
    private DataSource origenDatos;
    private static ConexionBD instancia=null;

    private ConexionBD(String bd, String url, String user, String password) 
    {
        this.bd = bd;
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    
    public static ConexionBD getConexionBD()
    {
        if (instancia==null)
        {
            instancia = new ConexionBD("mangochatbot", "jdbc:mysql://localhost/", "root","");
            instancia.iniciar();
        }
        
        return instancia;
    }
    
    
    private void iniciar()
    {
        BasicDataSource data = new BasicDataSource();
        data.setDriverClassName("com.mysql.cj.jdbc.Driver");
        data.setUsername(user);
        data.setPassword(password);
        data.setUrl(url+bd);
        data.setMaxActive(15);
        
        origenDatos = data;
        
    }

    public DataSource getOrigenDatos() {
        return origenDatos;
    }


    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    
}
