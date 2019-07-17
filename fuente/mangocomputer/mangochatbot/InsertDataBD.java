
package mangocomputer.mangochatbot;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mm
 */
public class InsertDataBD {

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private Lenguaje lengua=null;
    
    public InsertDataBD() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException 
    {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://localhost/mangochatbot?user=mangochatbot&password=666666");
        stmt = conn.createStatement();
        lengua = new Lenguaje();
        lengua.cargarDiccionarios();
    }
    
    public void insertData() throws SQLException
    {
        //String query = "INSERT INTO `articulos`(`articulo`) VALUES (?);";
        String query = "INSERT INTO `semantica_sino`(`palabra`, `semantica`) VALUES (?,?);";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
//*        
        String p;
        String v;
        
        for (Map.Entry<String, String> l : lengua.getSiNo().entrySet())
        {
            p = (String)l.getKey();
            v=(String)l.getValue();
            System.out.println("palabra: " + p + " semantica " + v);
            preparedStmt.setString (1, p);
            preparedStmt.setString (2, v);
            preparedStmt.execute();
        }
//*/        
/*
        Iterator e = lengua.getArticulos();
        String valor;
        while(e.hasNext())
        {
            valor = (String)e.next();
            System.out.println("Valor: " + valor);
            preparedStmt.setString (1, valor);
            preparedStmt.execute();
        }
*/
                
    }
    


    public void cerrar() throws SQLException
    {
        if (rs!=null)       rs.close();
        if (stmt!=null)     stmt.close();
        if (conn!=null)     conn.close();

    }
    
    
    
}
