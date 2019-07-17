/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mm
 */
public class FrasesFabricadas {
    
    private HashMap<String, Integer> frases;

    public FrasesFabricadas() 
    {
        frases = new HashMap<>();
        cargar();
        
    }
    
    private void cargar()
    {
        
        try {
            
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            Statement stmt = conn.createStatement();
            
            String query = "SELECT codFrase, frase FROM fabricaFrases";
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                frases.put(rs.getString("frase"), rs.getInt("codFrase"));
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(FrasesFabricadas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getTotalFrases() {
        return frases.size();
    }

    public HashMap<String, Integer> getFrases() {
        return frases;
    }
    
    
    
    
    
}
