/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose Andres Morales Linares 2018
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 
        /*
        String m = "Restaurante con aire acondicionado y punto de venta";
        System.out.println(m);
        m = m.replaceAll(" ", "-");
        System.out.println(m);
        
        if (m.contains("punto-dede-venta"))
        {
            System.out.println("Si");
        }
        
        System.exit(0);
//*/
/*
        String m = "Restaurante con aire acondicionado y punto de venta ";
        String palabra = "punto de venta";        
        
        if (m.contains(palabra))
        {
            int iniPalabra = m.indexOf(palabra);
            int finalPalabra = palabra.length();
            
            String frase1 = m.substring(0, iniPalabra);
            String frase2 = m.substring(iniPalabra+finalPalabra+1);
            
            
            
            System.out.println(frase1);
            System.out.println(frase2);
            
            System.out.println(m);
            System.out.println(frase1 + "punto-de-venta "+frase2);

        }
        
        
        System.exit(0);
        //*/
        // TODO code application logic here
        ConexionBD test=null;
        Connection conn=null;
 
 
        test = ConexionBD.getConexionBD();
        try {
            conn = test.getOrigenDatos().getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (conn != null)
        {
            System.out.println("Conección: OK");
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            
            System.out.println("Conección: Falló");
            System.exit(0);
        }
        
        SessionChat session = new SessionChat(); 
        session.actualizarCoordenadasGps(10.2491163, -67.5859457);

        MotorMangoChatBot appBot = new MotorMangoChatBot();
        
        if (!appBot.isTieneSession())
        {
            appBot.setSession(session);
        }
        
        boolean seguir = true;
        Scanner teclado = new Scanner(System.in);
        String lineaLeida;
        ArrayList<String> respuestaBot=null;
        Iterator iter = null; 
        System.out.println("Hola soy MangoBot, es para mi un placer ayudarte");
        System.out.println("Por favor puedes decirme tu nombre de usuario");
        do {
            
            lineaLeida = teclado.nextLine();
            if (lineaLeida.equalsIgnoreCase("salir")) break;
            appBot.procesarFrase(lineaLeida);
            respuestaBot = appBot.respuestaChatBot();
            iter = respuestaBot.iterator();
            while (iter.hasNext())
            {
                System.out.println((String)iter.next());
            }

         
        } while(seguir);
    //*/
    
    
    
    System.exit(0);
        
    }
    
}
