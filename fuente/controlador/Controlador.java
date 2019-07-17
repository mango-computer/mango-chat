package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mangocomputer.mangochatbot.ConexionBD;
import mangocomputer.mangochatbot.Main;

import mangocomputer.mangochatbot.MotorMangoChatBot;
import mangocomputer.mangochatbot.SessionChat;



@WebServlet({"/Controlador"})
public class Controlador extends HttpServlet
{
  private static final long serialVersionUID = 1L;

    ConexionBD test=null;
    Connection conn=null;
    SessionChat session;
    MotorMangoChatBot appBot;
    
    
  public Controlador() 
  {

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

      
    appBot = new MotorMangoChatBot();

      
  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    
    try
    {
      
        TimeUnit.SECONDS.sleep(1L);
      
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    HttpSession miSesion = request.getSession(true);
    
    if (miSesion.isNew())
    {
        session = new SessionChat(); 
        
    } else {
    
        session = (SessionChat) miSesion.getAttribute("sessionChat");
    }
    
    if (session==null)
    {
        session = new SessionChat(); 
    }
    
    double lat=0.0;                 //= 10.2491163;
    double lon=0.0;                 // = -67.5859457;

    try {

        lat = Double.parseDouble(request.getParameter("latitud"));
        lon = Double.parseDouble(request.getParameter("longitud"));
        
    } catch (NumberFormatException ex){
        
        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
    }

    session.actualizarCoordenadasGps(lat, lon);
    
    appBot.setSession(session);
    appBot.procesarFrase(request.getParameter("message"));

    ArrayList<String> respuestaBot = appBot.respuestaChatBot();
    Iterator iter = respuestaBot.iterator();
    String respuesta="{\"cantidad\":"+respuestaBot.size() + ", ";
    int i=0;
    while (iter.hasNext())
    {
        respuesta = respuesta + "\"msm"+i+"\":\"" + ((String)iter.next()) + "\"";
        if (iter.hasNext())
        {
            respuesta = respuesta + ", ";
        }
        i++;
    }
    respuesta = respuesta + "}";
    System.out.println("msm: " + respuesta);

    miSesion.setAttribute("sessionChat", session);

    out.print(respuesta);
    out.flush();
  }
  


  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doGet(request, response);
  }
}