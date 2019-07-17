/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mm
 */
public class MotorMangoChatBot 
{
    
    private AplanarPalabras aplana;
    private Lenguaje lenguaje;
    private ArrayList<PalabraCorregida> ArrayCorregida;
    private MomentoTiempo momento;
    private SessionChat session=null;
    
    private static final String[] noTengoCuenta = {"no","ni", "que", "nuevo", "crear", "nueva"};
    
    /*
        Constructor
    */
    public MotorMangoChatBot() 
    {
        lenguaje = new Lenguaje();
        ArrayCorregida = new ArrayList<>(); 
        momento = new MomentoTiempo(); 
        lenguaje.cargarDiccionarios();
        aplana =  new AplanarPalabras(lenguaje);
    }

    public void setSession(SessionChat session)
    {
        this.session = session;
    }

    public SessionChat getSession() {
        return session;
    }
    
    public boolean isTieneSession()
    {
        return (session!=null);
    }
    
    public String getSaludoInicioSession()
    {
        MomentoTiempo.MOMENTO_DIA m = momento.getMomentoDia();
        String resul="";
                
        if (null!=m)
        {
            switch (m) 
            {
                case MANANA:
                    resul = "Buenos dias <b>" + session.getNombre() + "</b>, ¿ En que podemos ayudarte esta mañana ?";
                    break;
                case MEDIO_DIA:
                    resul = "Hola <b>" + session.getNombre() + "</b>, acabo de terminar de almorzar, en que podemos ayudar";
                    break;
                case TARDE:
                    resul = "Buenas tardes <b>" + session.getNombre() + "</b>, que bueno estes por aqui para ayudarte";
                    break;
                case NOCHE:
                    resul = "Buenas noches <b>" + session.getNombre() + "</b>, ¿ Que estas buscando ?";
                    break;
                case MADRUGADA:
                    resul = "Saludos <b>" + session.getNombre() + "</b>, recuerda que estamos 24 horas para ayudarte";
                    break;
            }
        }

        return resul;
    }
    
        private void aplicarFiltro(ArrayList<String> frase)
    {
        ArrayList<String> array = new ArrayList<>(frase.size());
        
        Iterator iter = frase.iterator();
        String palabra="";
        while (iter.hasNext())
        {
            palabra = (String)iter.next();
            if (palabra!=null)
            {
                if (!lenguaje.existePalabra(palabra))
                {
                    iter.remove();
                    session.addPalabraNueva(palabra);
                    
                } else if (array.contains(palabra)) {
                    
                    iter.remove();
                    session.addPalabrasBolsa(palabra);
                    
                } else {
                    
                    array.add(palabra);
                    session.addPalabrasBolsa(palabra);
                }
            }
        }
        
        array.clear();
    }

   
    private void verificarCambioContexto(ArrayList<String> frase)
    {
        Iterator iter = frase.iterator();
        String palabra="";
        Palabra objPalabra;
        
        while (iter.hasNext())
        {
            palabra = (String)iter.next();
            if (palabra!=null)
            {
                objPalabra = lenguaje.getObjPalabra(palabra);
                if (objPalabra != null)
                {
                    if (objPalabra.getIdContexto() != session.getEstadoConversacion().getIdContexto())
                    {
                        //Reset estado de la conversacion porque hubo cambio del contexto del hilo
                        session.getEstadoConversacion().resetEstadoConversacion();
                        break;
                    }
                }
            }
        }
    }
    
    private void metabolizarFrase(String frase)
    {
        aplana.setFrase(frase);
        aplana.quitarCaracteresEspeciales();
        aplana.quitarRepeticionesLetrasPorPalabra();
        aplana.convertirPalabrasLargas();
        
        ArrayList<String> fraseAplanada = aplana.getFraseAplanada();
        ArrayList<String> arrayLimpio = lenguaje.getArrayCambioSemantico(aplana.eliminarLiteralesArray(fraseAplanada));
        session.addFrases(arrayLimpio, fraseAplanada, frase);
        arrayLimpio.clear();
        arrayLimpio = null;
    }
    
    private boolean procesarPorFabricaFrases(NodoFrase nodo)
    {
        boolean procesado = false;
        
        int cod = reconocerFrase(nodo);
        
        if ((cod > 0) && (cod <1000)) // Respuesta Directa
        {
            session.getArrayrespuesta().add(lenguaje.getRespuestaFrase(cod));
            session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
            procesado = true;
            
        } else if (cod > 1000) { //Respuesta de Base de Datos
            
            session.getArrayrespuesta().add(lenguaje.getRespuestaFrase(cod));
            procesarFabricaFrases2BD(cod);
            session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
            procesado = true;
        } 
        
        return procesado;
    }
    
    private void procesarFabricaFrases2BD(int cod)
    {
        System.out.println("consulta a la base de datos el " + cod);
    }
    

    private boolean interpretarLenguajeNatural(NodoFrase nodo)
    {
        boolean interpretado=false;
        
        ArrayList<String> ArrayFrase = nodo.getArrayPalabras();
       
        //Quita repeticiones y agrega las palabras nuevas (desconocidas) a la base de datos
        aplicarFiltro(ArrayFrase);
        
        //Reset el estado de la conversacion si el usuario cambia el contexto de la conversacion
        verificarCambioContexto(ArrayFrase);
        
        Iterator iter = ArrayFrase.iterator();

        String palabra;
        Palabra objPalabra=null;
        int idContex;
        int idContexPalabra;
        
        while (iter.hasNext())
        {
            palabra = (String)iter.next();

            objPalabra = lenguaje.getObjPalabra(palabra);
            if (objPalabra==null) continue;
            
            if (objPalabra.isCategoria())
            {
                session.getEstadoConversacion().setIdCategoria(objPalabra.getIdCategoria());
            }
            
            if (objPalabra.isTieneUbigeo() && !session.getEstadoConversacion().isTieneUbigeo())
            {
                session.getEstadoConversacion().setUbigeo(objPalabra.getUbigeo());
            }
            
            if (session.getEstadoConversacion().isContexto())
            {
                idContex = session.getEstadoConversacion().getIdContexto();
                idContexPalabra = objPalabra.getIdContexto();
                if ((idContex==idContexPalabra) || (idContexPalabra==0))
                {
                    switch (objPalabra.getTipo()) {
                        case 1:
                            session.getEstadoConversacion().addLugar(objPalabra.getIdBolsaPalabra());
                            break;
                        case 2:
                            session.getEstadoConversacion().addProducto(objPalabra.getIdBolsaPalabra());
                            break;
                        case 3:
                            session.getEstadoConversacion().addCaracteristica(objPalabra.getIdBolsaPalabra());
                            break;
                        default:
                            break;
                    }

                } else {

                    session.getEstadoConversacion().setVariosContextos(true);
                }

            } else { //Aun no se ha definido un contexto

                if (objPalabra.getIdContexto()>0)
                {
                    session.getEstadoConversacion().setIdContexto(objPalabra.getIdContexto());
                }

                if (objPalabra.getTipo()==1)
                {
                    session.getEstadoConversacion().addLugar(objPalabra.getIdBolsaPalabra());

                } else if (objPalabra.getTipo()==2) {

                    session.getEstadoConversacion().addProducto(objPalabra.getIdBolsaPalabra());

                } else if (objPalabra.getTipo()>2) {

                    session.getEstadoConversacion().addCaracteristica(objPalabra.getIdBolsaPalabra());
                }
            }   
        }
        
        if (!session.getEstadoConversacion().isVariosContextos() && session.getEstadoConversacion().isContexto())
        {
            interpretado = true;            
        }
 
        return interpretado;
    }
    
    private ArrayList<Integer> getBolsaPalabrasPerfil()
    {
        ArrayList<Integer> array = new ArrayList<>();
        
        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            ResultSet rs;
            Statement stmt = conn.createStatement();
            
            String query = "SELECT idBolsaPalabra FROM perfilUser WHERE idUser=" + session.getIdUser() +
                    " AND idContexto=" + session.getEstadoConversacion().getIdContexto() + " AND " +
                    "idCategoria="+session.getEstadoConversacion().getIdCategoria();

            rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                array.add(rs.getInt("idBolsaPalabra"));
            } 

            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return array;
    }

    private String crear_ConsultaSQL_SegunContexto()
    {
        String sql;
        String contexto="";
        
        //Se llega aqui cuando hay un solo contexto
        if (session.getEstadoConversacion().isCategoria())
        {
            contexto = " ((idContexto=" + session.getEstadoConversacion().getIdContexto() + ") AND (idCategoria="+ session.getEstadoConversacion().getIdCategoria()+")) ";

        } else {

            contexto = " (idContexto=" + session.getEstadoConversacion().getIdContexto() + ")";
        }

        String lista="";
        
        if (session.getEstadoConversacion().isLugar())
        {
            ArrayList<Integer> arrayLugar = session.getEstadoConversacion().getArrayLugares();
            Iterator<Integer> iter = arrayLugar.iterator();
            int v=0;
            while (iter.hasNext())
            {
                v = (int) iter.next();
                lista = lista + "dtBolsaPalabras.idBolsaPalabra=" + String.valueOf(v);
                if (iter.hasNext())
                {
                    lista = lista+" OR ";
                }
           }
        }

        if (session.getEstadoConversacion().isProductos())
        {
            ArrayList<Integer> arrayProductos = session.getEstadoConversacion().getArrayProductos();
            Iterator<Integer> iter = arrayProductos.iterator();
            int v=0;
            boolean primeraVez=true;

            while (iter.hasNext())
            {
                if (primeraVez)
                {
                    primeraVez = false;
                    if (session.getEstadoConversacion().isLugar())
                    {
                        lista = lista+" OR ";
                    }
                }
                
                v = (int) iter.next();
                lista = lista + "dtBolsaPalabras.idBolsaPalabra=" + String.valueOf(v);
                if (iter.hasNext())
                {
                    lista = lista + " OR ";
                }
            }
        }
        
        ArrayList<Integer> array = getBolsaPalabrasPerfil();
        if (!array.isEmpty())
        {
            Iterator iter = array.iterator();
            boolean primeraVez=true;
            int v=0;
            
            while (iter.hasNext())
            {
                if (primeraVez)
                {
                    primeraVez = false;
                    if (session.getEstadoConversacion().isLugar() || session.getEstadoConversacion().isProductos())
                    {
                        lista = lista + " OR ";
                    }
                }
                v = (int) iter.next();
                lista = lista + "dtBolsaPalabras.idBolsaPalabra=" + String.valueOf(v);
                if (iter.hasNext())
                {
                    lista = lista + " OR ";
                }
            }
        }

        if (session.getEstadoConversacion().isCaracteristicas())
        {
            ArrayList<Integer> arrayCaracteristicas = session.getEstadoConversacion().getArrayCaracteristicas();
            Iterator<Integer> iter = arrayCaracteristicas.iterator();
            int v=0;
            
            boolean primeraVez=true;

            while (iter.hasNext())
            {
                if (primeraVez)
                {
                    primeraVez = false;
                    if (session.getEstadoConversacion().isLugar() || session.getEstadoConversacion().isProductos() || (!array.isEmpty()))
                    {
                        lista = lista + " OR ";
                    }
                }
                v = (int) iter.next();
                lista = lista + "dtBolsaPalabras.idBolsaPalabra=" + String.valueOf(v);
                if (iter.hasNext())
                {
                    lista = lista + " OR ";
                }
            }
        }

/*
        sql = "SELECT dt.*, getDistanciaKms(" + session.getLatitud() + "," + session.getLongitud() + ",latitud,longitud) as distancia " +
                " FROM dt INNER JOIN dtBolsaPalabras ON (dt.idDt = dtBolsaPalabras.idDt) WHERE " + 
        contexto + " AND (dtBolsaPalabras.idBolsaPalabra IN (" +
        lista+"))" +
        " AND latitud BETWEEN (" + (session.getLatitud()-0.5) + 
        ") AND (" + (session.getLatitud()+0.5) + 
        ") AND longitud BETWEEN (" + (session.getLongitud()-0.5) + 
        ") AND (" + (session.getLongitud()+0.5) + ") GROUP BY dt.idDt HAVING distancia < ( " + session.getRadioBusqueda() + "/1000) " + 
        " LIMIT " + session.getLimiteResultados();
//*/    


        if (session.getEstadoConversacion().isTieneUbigeo())
        {
            
            Ubigeo ubi = session.getEstadoConversacion().getUbigeo();

            sql = "SELECT dt.*,getDistanciaKms(" + session.getLatitud() + "," + session.getLongitud() + ",latitud,longitud) as distancia ,COUNT(dtBolsaPalabras.idDt) as cantidad " +
                "FROM dt INNER JOIN dtBolsaPalabras ON (dt.idDt = dtBolsaPalabras.idDt) " + 
                "WHERE " +
                contexto +
                " AND " +
                " dt.codUbigeoEntidad= " + ubi.getCodEntidad() +
                " AND " + 
                "("+lista+") " + 
                "GROUP BY dt.idDt " +
                "ORDER by cantidad DESC, dt.calificacion DESC " +    
                "LIMIT " + session.getLimiteResultados();
            
            
        } else {

            sql = "SELECT dt.*, getDistanciaKms(" + session.getLatitud() + "," + session.getLongitud() + ",latitud,longitud) as distancia, COUNT(dtBolsaPalabras.idDt) as cantidad " +
                "FROM dt INNER JOIN dtBolsaPalabras ON (dt.idDt = dtBolsaPalabras.idDt) " + 
                "WHERE " +
                contexto +
                " AND " + 
                "("+lista+") "+
                " GROUP BY dt.idDt HAVING distancia < ( " + session.getRadioBusqueda() + "/1000) " +
                "ORDER by cantidad DESC, dt.calificacion DESC, distancia ASC " +    
                " LIMIT " + session.getLimiteResultados();
        
        }

        System.out.println(sql);
        
        return sql;
    }
    
    private int get_idDt_NombreLugar(String nombre)
    {
        int idDt = 0;

        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            PreparedStatement sentencia = null;
            ResultSet rs;
                    
            String query = "SELECT idDt FROM dtNombres WHERE nombre=?";
            //rs = stmt.executeQuery(query);
            sentencia = conn.prepareStatement(query);
            sentencia.setString(1, nombre);
            rs = sentencia.executeQuery();

            if (rs.next())
            {
                idDt = rs.getInt("idDt");
            } 

            rs.close();
            sentencia.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idDt;
    }
    
    private String getEstrellasHtml(int cantidad)
    {
        String codigo="";
        
        switch(cantidad)
        {
            case 0:
            {
                codigo = "&#9734;&#9734;&#9734;&#9734;&#9734;";
            } break;
            
            case 1:
            {
                codigo = "&#9733;&#9734;&#9734;&#9734;&#9734;";
            } break;

            case 2:
            {
                codigo = "&#9733;&#9733;&#9734;&#9734;&#9734;";
            } break;

            case 3:
            {
                codigo = "&#9733;&#9733;&#9733;&#9734;&#9734;";
            } break;

            case 4:
            {
                codigo = "&#9733;&#9733;&#9733;&#9733;&#9734;";
            } break;

            case 5:
            {
                codigo = "&#9733;&#9733;&#9733;&#9733;&#9733;";
            } break;
            
        }
        
        return codigo;
    }
    
    private boolean procesarConsultaSql(String sql)
    {
        boolean procesado = false;
        
      //  ArrayList<String> resulSQl = new ArrayList<>(20); 
        session.resetArrayListDataResultados();
        int contador=0;
        int status;
        String horario;
        String comentario;
        Blob foto;
        
        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            double distancia;
                        
            while (rs.next())
            {
                contador++;
                DataResultado resultado = new DataResultado();
                
                //Nombre del local o lugar
                //resulSQl.add("Nombre oficial <b>" + rs.getString("nombreOficial") + "</b>");
                resultado.setNombreOficial("Nombre oficial <b>" + rs.getString("nombreOficial") + "</b>");
                //Calificacion en la comunidad
                //resulSQl.add(String.valueOf("Popularidad " + getEstrellasHtml(rs.getInt("calificacion"))));
                resultado.setPopularidad(String.valueOf("Popularidad " + getEstrellasHtml(rs.getInt("calificacion"))));
                
                status = rs.getInt("status");
                
                switch(status)
                {
                    case 0:
                    {
                        //resulSQl.add("En este momento el lugar esta <b>cerrado</b>");
                        resultado.setStatus("En este momento el lugar esta <b>cerrado</b>");
                        
                    } break;
                    
                    case 1:
                    {
                        //resulSQl.add("EL sitio esta <b>abierto</b>");
                        resultado.setStatus("El sitio esta <b>abierto</b>");
                                
                    } break;

                    case 2:
                    {
                        //resulSQl.add("EL sitio esta sin capacidad <b>porque esta lleno</b>");
                        resultado.setStatus("El sitio esta sin capacidad <b>porque esta lleno</b>");
                                
                    } break;

                    case 3:
                    {
                        //resulSQl.add("En este momento el lugar esta <b>cerrado por remodelaci&oacute;n</b>");
                        resultado.setStatus("En este momento el lugar esta <b>cerrado por remodelaci&oacute;n</b>");
                                
                    } break;
                }
                
                horario = rs.getString("horario");
                if ((horario!= null) && (!horario.isEmpty()))
                {
                    //resulSQl.add("Horario <b>" + horario + "</b>");
                    resultado.setHorario("Horario " + horario);
                }
                
                comentario = getMejorComentarioLugar(rs.getInt("idDt"), rs.getInt("calificacion"));
                
                foto = rs.getBlob("foto");
                if (foto.length()>0)
                {
                    String base64 = java.util.Base64.getEncoder().encodeToString(foto.getBytes(1, (int) foto.length()));
                    //resulSQl.add("<img src='data:image/jpg;base64,"+base64+"'>");
                    resultado.setFoto("<img src='data:image/jpg;base64,"+base64+"'>");
                }
                
                // Comentarios
                if (comentario.length()>0)
                {
                    //resulSQl.add(comentario);
                    resultado.setComentario(comentario);
                }
                
                resultado.setTelefono("Telefono " + rs.getString("telefono"));
                
                //Direccion
                //resulSQl.add("Direcci&oacute;n " + rs.getString("direccion"));
                resultado.setDireccion("Direcci&oacute;n " + rs.getString("direccion"));
                //Mapa
                //resulSQl.add("<a href=https://maps.google.com/?q="+rs.getString("latitud")+","+rs.getString("longitud")+" target=_blank>Ver el mapa</a>");
                resultado.setMapa("<a href=https://maps.google.com/?q="+rs.getString("latitud")+","+rs.getString("longitud")+" target=_blank>Ver el mapa</a>");
                //Distancia
                distancia = rs.getDouble("distancia");
                if (distancia<((double)1.0))
                {
                    if (distancia<((double)0.5))
                    {
                        //resulSQl.add("A pocas calles de ti");
                        resultado.setDistancia("A pocas calles de ti");
                    } else {

                        //resulSQl.add("A un poco mas de 4 calles de ti");
                        resultado.setDistancia("A un poco mas de 4 calles de ti");
                    }
                    
                } else {
                    
                    //resulSQl.add(String.valueOf("Esta a " + distancia + " Kms de distancia en linea recta"));
                    resultado.setDistancia(String.valueOf("Esta a " + distancia + " Kms de distancia en linea recta"));
                }
                //Pagina web
                if (rs.getInt("tieneUrl")==1)
                {
                    //resulSQl.add("P&aacute;gina web <a href="+ rs.getString("url") +" target=_blank>"+rs.getString("url")+"</a>");
                    resultado.setPaginaWeb("P&aacute;gina web <a href="+ rs.getString("url") +" target=_blank>"+rs.getString("url")+"</a>");
                }
                
                session.getArrayListDataResultados().add(resultado);
                
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if (contador==0)
        {
            session.getArrayrespuesta().add("Uff, no tengo informacion que compartir en mi base de datos &#9785;");
            session.getArrayrespuesta().add("Actualmente el radio de busqueda es de " + String.valueOf(session.getRadioBusqueda()/1000) + " Kms.");
            session.getArrayrespuesta().add("Recuerda que podemos modifcarlo las veces que creas necesario");
            session.getArrayrespuesta().add("Tambien, puedes cambiar la forma como haces la pregunta");
            
            
        } else {
            
            if (contador==1)
            {
                session.getArrayrespuesta().add("Solo puedo darte una unica sugerencia");
            
            } else {

                session.getArrayrespuesta().add("Tengo " + contador + " buenas sugerencias");
            }
            
            session.setIndiceArrayListDataResultados(1);
            session.setParteIndiceArrayListDataResultados(1);
            session.setEstadoChatBot(EstadoChat.SI_NO_MOSTRAR_MAS_RESULTADOS);
            
            procesado = true;
        }
        
        return procesado;
        
    }
    
    private boolean construirRespuestaSegunContexto()
    {
        String sql = crear_ConsultaSQL_SegunContexto();
        return procesarConsultaSql(sql);
    }
    
    private EstructCoincidenciaNombre procesarCoincidenciaNombre(NodoFrase nodo)
    {
        EstructCoincidenciaNombre respuesta= new EstructCoincidenciaNombre();

        try {
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            PreparedStatement sentencia = null;
            
            ArrayList<EstructCoincidenciaNombre> arrayData = new ArrayList<>();

            String query = "SELECT dtNombres.idDt, dtNombres.nombre, length(dtNombres.nombre) as largo FROM dtNombres order by largo desc";
            sentencia = conn.prepareStatement(query);
            ResultSet rs = sentencia.executeQuery();
            
            while (rs.next())
            {
                EstructCoincidenciaNombre data = new EstructCoincidenciaNombre(rs.getInt("dtNombres.idDt") ,rs.getString("dtNombres.nombre"));
                arrayData.add(data);
            }
            
            if (!arrayData.isEmpty())
            {
                //Statement stmt2 = conn.createStatement();
                ResultSet rs2=null;
                PreparedStatement sentencia2 = null;
                Iterator iter = arrayData.iterator();
                String query2;
                EstructCoincidenciaNombre d;
                
                query2 = "SELECT conversacion.mensaje, conversacion.idConversacion FROM conversacion " +
                 "WHERE conversacion.idConversacion = ?" +
                 " AND MATCH(conversacion.mensaje) AGAINST(? IN BOOLEAN MODE)";

                sentencia2 = conn.prepareStatement(query2);
                
                while (iter.hasNext())
                {
                    d = (EstructCoincidenciaNombre) iter.next();

                    sentencia2.setInt(1, session.getIdMensajeHistorial());
                    sentencia2.setString(2, "\\\""+ d.getNombreDt() +"\\\"");
                    rs2 = sentencia2.executeQuery();
                    
                    if (rs2.next())
                    {
                        respuesta = d;
                        respuesta.setExiste(true);
                        break;
                    }

                    rs2.close();
                }
                
                if (rs2!=null)
                {
                    rs2.close();
                }
                
                //stmt2.close();
                sentencia2.close();
            }
            
            rs.close();
            sentencia.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuesta;
    }
    
    private boolean procesarPorNombre(NodoFrase nodo)
    {
        boolean procesado=false;
        
        EstructCoincidenciaNombre d=procesarCoincidenciaNombre(nodo);
        if (d.isExiste())
        {
            procesado = true;
            session.getArrayrespuesta().add("Conozco un lugar conocido por <b>"+d.getNombreDt()+"</b>");
            session.getArrayrespuesta().add("¿ Que deseas hacer ?");
            session.getArrayrespuesta().add("Que te muestre <b>informaci&oacute;n</b>");
            session.getArrayrespuesta().add("<b>Calificar</b> el lugar");
            session.getArrayrespuesta().add("Buscar <b>todo</b> lo relacionado con el tema en tu zona");
            session.getArrayrespuesta().add("<b>Nada</b>");
            
            session.getEstadoConversacion().setCoincidenciaNombre(d);
            session.setEstadoChatBot(EstadoChat.SI_NO_CONOCIDO);
        }
        
        return procesado;
    }
    
    private boolean procesarGrupoFabricaFrases(NodoFrase nodo)
    {
        boolean procesado=false;
        String palabraOriginal=null; 
        procesado = procesarPorFabricaFrases(nodo);
        if (!procesado)  //Entra si no ha sido procesado por la fabrica
        {
            if (session.getNodoFrase().getArrayPalabras().size()==1)
            {
                palabraOriginal = (String)session.getNodoFrase().getArrayPalabras().get(0);

            } else if (session.getNodoFrase().getArrayPalabrasAplanadas().size()==1) {

                palabraOriginal = (String)nodo.getArrayPalabrasAplanadas().get(0);
            }

            if (palabraOriginal != null) //Una sola palabra
            {
                if (!lenguaje.existePalabra(palabraOriginal)) //No existe en la bolsa de palabras
                {
                    String palabraSugerida = lenguaje.sugerenciaCorreccion(palabraOriginal);
                    if (palabraSugerida != null) //Existe una sugerencia
                    {
                        procesado = true;
                        session.getArrayrespuesta().add("Será que en vez de <b>" + palabraOriginal + "</b> tal vez querias decir <b>" + palabraSugerida + "</b>, <b>Si</b> o <b>No</b> ");
                        session.setPalabraDesconocida(palabraOriginal);
                        session.setPalabraSugerida(palabraSugerida);
                        session.setEstadoChatBot(EstadoChat.SI_NO_CORREGIR);
                    } 
                } 
            }

            if (!procesado)
            {
                if (interpretarLenguajeNatural(nodo)) //Devuelve verdadero si interpreto un contexto unico
                {
                    if (construirRespuestaSegunContexto()) //Entra si fue procesado
                    {
                        procesado = true;
                    }

                } else {
                    
                    session.getArrayrespuesta().add(FrasesEstaticas.finalRecorrido[FrasesEstaticas.getNumeroAleatorio(10)] );
                }
            }
        }
        
        return procesado;
    }
    
    private boolean verificarPrimeraPalabra(NodoFrase nodo, String palabra)
    {
        return  verificarPosPalabra(nodo,palabra,0);
    }
    
    private boolean verificarPosPalabra(NodoFrase nodo, String palabra, int pos)
    {
        boolean verificar = false;
        
        if (nodo.getArrayPalabrasAplanadas().size()>0)
        {
            String pp = (String)nodo.getArrayPalabrasAplanadas().get(pos);
            verificar = pp.equals(palabra);
        }
        
        return  verificar;
    }

    private boolean validarCalificar(NodoFrase nodo)
    {
        boolean procesado = false;
        
        if (verificarPrimeraPalabra(nodo, "calificar"))
        {
            if (nodo.getArrayPalabras().size() > 1)
            {
                Iterator iter = nodo.getArrayPalabras().iterator();
                String lugar = null;
                
                //sacar la palabra calificar
                if (iter.hasNext())
                {
                    iter.next();
                }
                
                while (iter.hasNext())
                {
                    if (lugar==null)
                    {
                        lugar = "";

                    } else {
                        
                        lugar = lugar + " ";
                    }

                    lugar = lugar + (String)iter.next();
                }
                
                int idDt = get_idDt_NombreLugar(lugar);
                if (idDt > 0)
                {
                    session.getArrayrespuesta().add("Por favor dime cuantas estrellas del 1 al 5 le darias a <b>" + lugar + "</b>");
                    EstructCoincidenciaNombre c = new EstructCoincidenciaNombre(idDt, lugar);
                    c.setExiste(true);
                    session.getEstadoConversacion().setCoincidenciaNombre(c);
                    session.getEstadoConversacion().getCoincidenciaNombre().setIdDt(idDt);
                    session.setEstadoChatBot(EstadoChat.CALIFICAR);
                    
                    procesado = true;

                } else {

                    session.getArrayrespuesta().add("No conozco un lugar conocido con ese nombre exactamente");
                    session.getArrayrespuesta().add("Dejame seguir buscando informacion relacionada a ver que puedo conseguir...");
                }
            }
        }
        
        
        return procesado;
    }
    
    private boolean validarCambioLimiteResultados(NodoFrase nodo, String frase)
    {
        boolean procesado = false;

        if (
            frase.contains("limite de resultados") || 
            frase.contains("limites de resultados") || 
            frase.contains("limite de los resultados") ||
            frase.contains("limites de los resultados") ||
            frase.contains("limite resultado") ||
            frase.contains("tope de resultados") || 
            frase.contains("tope de resultados") || 
            frase.contains("tope de los resultados") ||
            frase.contains("tope de los resultados") ||
            frase.contains("tope resultado") ||
            frase.contains("cantidad de resultados") || 
            frase.contains("cantidad de resultados") || 
            frase.contains("cantidad de los resultados") ||
            frase.contains("cantidad de los resultados") ||
            frase.contains("cantidad resultado") )
            {
            procesado = true;

            session.getArrayrespuesta().add("Muy bien, vamos a cambiar el limite de los resultados que te muestro");
            session.getArrayrespuesta().add("En este momento el limite es de " + session.getLimiteResultados());
            session.getArrayrespuesta().add("Por favor, dime cuanto resultados deseas ver en una sola consulta");
            session.getArrayrespuesta().add("El rango valido va desde <b>1 hasta 20</b>");
            session.setEstadoChatBot(EstadoChat.CAMBIO_LIMITE_BUSQUEDA);
        }
        
        return procesado;
    }
    
    private boolean validarPedirSugerenciaGeneral(NodoFrase nodo, String frase)
    {
        boolean procesado = false;
        if (
            frase.contains("me sugieres") || 
            frase.contains("una sugerencia") || 
            frase.contains("puedes sugerir") ||
            frase.contains("bueno para hoy") ||
            frase.contains("hacer hoy") ||
            frase.contains("sitios turisticos") || 
            frase.contains("sitio turistico") || 
            frase.contains("lugares turisticos") ||
            frase.contains("lugar turistico") ||
            frase.contains("lugares de moda") ||
            frase.contains("lugares mas visitados") || 
            frase.contains("lugares de interes") || 
            frase.contains("lugar de moda") ||
            frase.contains("lugar mas visitado") ||
            frase.contains("mostrar turismo") ||
            frase.contains("mostrar lugares") ||
            frase.contains("hazme unas sugerencias") ||
            frase.contains("hazme sugerencias") ||
            frase.contains("sugiereme algo") ||
            frase.contains("que puede hacer") ||
            frase.contains("que tenemos para hoy") ||
            frase.contains("dime cual es el mejor destino") ||
            frase.contains("destino turistico") ||
            frase.contains("que planes tenemos hoy") ||
            frase.contains("lugar de interes"))
            {
                System.out.println("Sugerencia General");
                procesado = true;
                
            }
        
        
        return procesado;
    }
    
    
    
    private void procesarSinEstado(String frase)
    {
        metabolizarFrase(frase);
        NodoFrase nodo = session.getNodoFrase();
        insertHistorialChatUser(nodo);
        session.setIdMensajeHistorial(getIdMensajeHistorial());
        
        String fraseCompleta = String.join(" ",nodo.getFraseVectorAplanado());
        
        if (!validarPedirSugerenciaGeneral(nodo, fraseCompleta))
        {
            if (!validarCambioLimiteResultados(nodo, fraseCompleta))
            {
                if (!validarCambioRadioBusqueda(nodo, fraseCompleta))
                {
                    if (!validarCalificar(nodo))
                    {
                        if (!procesarPorNombre(nodo))
                        {
                            procesarGrupoFabricaFrases(nodo);
                        }
                    }
                }
            }
        }
    }
    
    private EnumInterrogacion procesarSiNo(String frase, String patron)
    {
        EnumInterrogacion res = EnumInterrogacion.RS_FUERTE_NO;

        aplana.setFrase(frase);
        aplana.quitarCaracteresEspeciales();
        aplana.quitarRepeticionesLetrasPorPalabra();

        ArrayList<String> temporalArray = aplana.getFraseAplanada();
        int cuenta[] = lenguaje.totalSiNoRespuesta(temporalArray, patron);
        temporalArray.clear();

        if ((cuenta[0]>cuenta[1]) && (cuenta[1]==0)) //Si rotundo
        {
            res = EnumInterrogacion.RS_FUERTE_SI;
            
        } else if ((cuenta[0]>cuenta[1]) && (cuenta[1]>0)) { //Si pero no muy seguro

            res = EnumInterrogacion.RS_DEBIL_SI;
           
        } else if ((cuenta[0]<cuenta[1]) && (cuenta[0]==0)) { //No rotundo

            res = EnumInterrogacion.RS_FUERTE_NO;
            
        } else if ((cuenta[0]<cuenta[1]) && (cuenta[0]>0)) { //No dudoso pero es un no

            res = EnumInterrogacion.RS_DEBIL_NO;

        } else { // No sabe

            res = EnumInterrogacion.RS_NO_SE;
        }
        
        return res;
    }
    
    private void procesarSiNoCorregir(String frase)
    {
        EnumInterrogacion respuesta =  procesarSiNo(frase, "si");
        
        switch (respuesta)
        {
            case RS_FUERTE_SI:
            {
                session.getArrayrespuesta().add("Ok perfecto... ");
                procesarSinEstado(session.getPalabraSugerida());
                
            } break;
            
            case RS_DEBIL_SI:
            {
                session.getArrayrespuesta().add("No estas muy seguro, pero creo que es un si");
                procesarSinEstado(session.getPalabraSugerida());
                
            } break;

            case RS_FUERTE_NO:
            {
                session.getArrayrespuesta().add("¿ no ?");
                session.getArrayrespuesta().add("Entonces no entiendo que significa ");
                session.getArrayrespuesta().add(session.getPalabraDesconocida());
                session.setEstadoChatBot(EstadoChat.SIN_ESTADO);

            } break;

            case RS_DEBIL_NO:
            {
                session.getArrayrespuesta().add("No dices un no muy claro");
                session.getArrayrespuesta().add("Pero de ser así, no puedo entederte");
                session.getArrayrespuesta().add("Es que no tengo idea de lo que significa");
                session.getArrayrespuesta().add(session.getPalabraDesconocida());
                session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
                
            } break;

            case RS_NO_SE:
            {
                session.getArrayrespuesta().add("Qué !!!");
                session.getArrayrespuesta().add("Entonces...");
                session.getArrayrespuesta().add("Ahora no entiendo nada...");
                session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
                
            } break;
        }
    }

    private void procesarSiNoBuscarMas(String frase)
    {
        EnumInterrogacion respuesta =  procesarSiNo(frase, "si");
        
        switch (respuesta)
        {
            case RS_FUERTE_SI:
            {

                if (session.getRadioBusqueda()<1000)
                {
                    session.getArrayrespuesta().add("Ok, tu radio actual de busqueda es " + String.valueOf(session.getRadioBusqueda())+ " metros" );
                    
                } else {
                
                    session.getArrayrespuesta().add("Ok, tu radio actual de busqueda es " + String.valueOf((int)(session.getRadioBusqueda()/1000)) + " kilometros" );
                }
                
                session.getArrayrespuesta().add("Por favor introduce un radio en kilometros");
                session.getArrayrespuesta().add("Recuerda que un kilometro son como unas 10 calles");
                
                session.setEstadoChatBot(EstadoChat.CAMBIAR_RANGO_BUSQUEDA);
                
            } break;
            
            case RS_DEBIL_SI:
            {
                
                session.getArrayrespuesta().add("Debo interpretar eso como un si");
                session.getArrayrespuesta().add("Por favor introduce un radio en kilometros");
                session.getArrayrespuesta().add("Recuerda que un kilometro son como unas 10 calles");
                session.setEstadoChatBot(EstadoChat.CAMBIAR_RANGO_BUSQUEDA);

            } break;

            case RS_FUERTE_NO:
            {
                session.getArrayrespuesta().add("Muy bien...");
                session.getArrayrespuesta().add("Recuerda luego de visitar un lugar calificarlo");
                session.getArrayrespuesta().add("La forma de hacer es escribiendo el nombre del lugar o escribir <b>calificar</b> seguido del nombre exacto del lugar");

                session.getEstadoConversacion().resetCoincidenciaNombre();
                session.getEstadoConversacion().resetContexto();
                session.getEstadoConversacion().resetEstadoConversacion();
                session.setEstadoChatBot(EstadoChat.SIN_ESTADO);

            } break;

            case RS_DEBIL_NO:
            {
                session.getArrayrespuesta().add("Bueno, recuerda que si quieres seguir buscando mas información");
                session.getArrayrespuesta().add("lo unico que tienes que hacer es preguntarme cuando quieras");

                session.getEstadoConversacion().resetCoincidenciaNombre();
                session.getEstadoConversacion().resetContexto();
                session.getEstadoConversacion().resetEstadoConversacion();
                session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
                
            } break;

            case RS_NO_SE:
            {
                session.getArrayrespuesta().add("Si no sabes, espera a visitar esos lugares y podras decirme, y luego no olvides calificar el lugar");

                session.getEstadoConversacion().resetCoincidenciaNombre();
                session.getEstadoConversacion().resetContexto();
                session.getEstadoConversacion().resetEstadoConversacion();
                session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
                
            } break;
        }
        
    }
    
    private String getMejorComentarioLugar(int idDt, int popularidad)
    {
        String salida = "";
 
        try {
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            ResultSet rs = null;
            PreparedStatement sentencia = null;
            
            String query = "SELECT dtVoto.fecha, dtVoto.comentario, user.nombre FROM dtVoto INNER JOIN user ON dtVoto.idUser= user.idUser  WHERE dtVoto.idDt=? AND dtVoto.voto=? ORDER by dtVoto.fecha DESC LIMIT 1";

            sentencia = conn.prepareStatement(query);
            sentencia.setInt(1, idDt);
            sentencia.setInt(2, popularidad);
    
            rs = sentencia.executeQuery();
            
            if (rs.next())
            {
                salida = "Un usuario llamado " + rs.getString("user.nombre") + " dijo<br><b>" + rs.getString("dtVoto.comentario") + "</b><br>" +  
                        "El " + rs.getString("dtVoto.fecha");
            }

            rs.close();
            sentencia.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return salida;
    }
    
    private boolean mostrarLugar(int idDt)
    {
        boolean procesado = false;
        
        try {
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            ResultSet rs = null;
            PreparedStatement sentencia = null;
            
            //Statement stmt = conn.createStatement();
            
            double distancia;
/*            
            String query = "SELECT *, getDistanciaKms(" + session.getLatitud() +"," + session.getLongitud() + ", latitud, longitud) as distancia " +
            " FROM dt WHERE idDt=" + idDt;
            ResultSet rs = stmt.executeQuery(query);
//*/            
            String query = "SELECT *, getDistanciaKms(?,?,latitud,longitud) as distancia " +
            " FROM dt WHERE idDt=?";

            sentencia = conn.prepareStatement(query);
            sentencia.setDouble(1, session.getLatitud());
            sentencia.setDouble(2, session.getLongitud());
            sentencia.setInt(3, idDt);
    
            rs = sentencia.executeQuery();
            String statusMensaje="";
            
            if (rs.next())
            {
                procesado = true;
                
                int status = rs.getInt("status");
                
                switch(status)
                {
                    case 0:
                    {
                        statusMensaje = "En este momento el lugar esta <b>cerrado</b>";
                        
                    } break;
                    
                    case 1:
                    {
                        statusMensaje = "El sitio esta <b>abierto</b>";
                        
                    } break;

                    case 2:
                    {
                        statusMensaje = "El sitio esta sin capacidad <b>porque esta lleno</b>";
                        
                    } break;

                    case 3:
                    {
                        statusMensaje = "En este momento el lugar esta <b>cerrado por remodelaci&oacute;n</b>";
                        
                    } break;
                }
                
                //Nombre del local o lugar
                session.getArrayrespuesta().add("Nombre oficial <b>" + rs.getString("nombreOficial") +
                            "</b><br>Popularidad " + getEstrellasHtml(rs.getInt("calificacion"))+
                            "<br>"+statusMensaje);
                

                String comentario = getMejorComentarioLugar(idDt, rs.getInt("calificacion"));
                
                // Comentarios
                if (comentario.length()>0)
                {
                    session.getArrayrespuesta().add(comentario);
                }
                
                //*
                String horario = rs.getString("horario");
                if ((horario!= null) && (!horario.isEmpty()))
                {
                    session.getArrayrespuesta().add("Horario " + horario);
                }
                //*/
                
                Blob foto = rs.getBlob("foto");
                if (foto.length()>0)
                {
                    String base64 = java.util.Base64.getEncoder().encodeToString(foto.getBytes(1, (int) foto.length()));
                    session.getArrayrespuesta().add("<img src='data:image/jpg;base64,"+base64+"'>");
                }
                
                //Direccion
                session.getArrayrespuesta().add("Direcci&oacute;n " + rs.getString("direccion"));
                //Mapa
                session.getArrayrespuesta().add("<a href=https://maps.google.com/?q="+rs.getString("latitud")+","+rs.getString("longitud")+" target=_blank>Ver el mapa</a>");
                //Distancia
                distancia = rs.getDouble("distancia");
                if (distancia<((double)1.0))
                {
                    if (distancia<((double)0.5))
                    {
                        session.getArrayrespuesta().add("A pocas calles de ti");

                    } else {

                        session.getArrayrespuesta().add("A un poco mas de 4 calles de ti");
                        
                    }
                } else {
                    
                    session.getArrayrespuesta().add(String.valueOf("Esta a " + distancia + " Kms de distancia en linea recta"));
                }
                //Pagina web
                if (rs.getInt("tieneUrl")==1)
                {
                    session.getArrayrespuesta().add("P&aacute;gina web <a href="+ rs.getString("url") +" target=_blank>"+rs.getString("url")+"</a>");
                }
                
            } else {

                session.getArrayrespuesta().add("uff, talvez me equivoque, no lo conozco &#9785");
                
            }
            rs.close();
//            stmt.close();
            sentencia.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return procesado;
    }
    
    private int getNumeroPalabra(String palabra, int limite)
    {
        int num=0;
        int numTemporal=0;
        
        try{
            
            numTemporal = Integer.valueOf(palabra);
            
        } catch (NumberFormatException ex){
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.INFO, null, ex);
        }
        
        if ((numTemporal>0) && (numTemporal<=limite))
        {
            num = numTemporal;
            
        } else {
            
            if (palabra.equals("uno") || palabra.equals("una") || palabra.equals("un"))
            {
                num = 1;

            } else if (palabra.equals("dos")) {

                num = 2;

            } else if (palabra.equals("tres")) {

                num = 3;

            } else if (palabra.equals("cuatro")) {

                num = 4;

            } else if (palabra.equals("cinco")) {

                num = 5;
            }
        }
        
        return num;
    }

    private int getNumeroDeMensaje(String frase, int limite)
    {
        int num=0;
        
        aplana.setFrase(frase);
        aplana.quitarCaracteresEspeciales();
        aplana.quitarRepeticionesLetrasPorPalabra();

        ArrayList<String> temporalArray = aplana.getFraseAplanada();

        String palabra;
        Iterator iter = temporalArray.iterator();
        
        while (iter.hasNext())
        {
            palabra = (String) iter.next();
            num = getNumeroPalabra(palabra,limite);
            if (num>0) break;
        }

        temporalArray.clear();
        
        
        return num;
    }
    
    private void procesarSiNoConocido(String frase)
    {
        String fraseAplanada = frase.trim().toLowerCase();
        
        fraseAplanada = fraseAplanada.replace('á', 'a');
        fraseAplanada = fraseAplanada.replace('é', 'e');
        fraseAplanada = fraseAplanada.replace('í', 'i');
        fraseAplanada = fraseAplanada.replace('ó', 'o');
        fraseAplanada = fraseAplanada.replace('ú', 'u');
        
        if (fraseAplanada.contains("informacion") || fraseAplanada.contains("info") || fraseAplanada.contains("informame") 
                || fraseAplanada.contains("informa") || fraseAplanada.contains("conocer"))
        {
            session.getArrayrespuesta().add("Ok perfecto, te muestro la informacion que tengo sobre el lugar");
            mostrarLugar(session.getEstadoConversacion().getCoincidenciaNombre().getIdDt() );
            session.getEstadoConversacion().resetCoincidenciaNombre();
            session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
            
        } else if (fraseAplanada.contains("calificar") || fraseAplanada.contains("califica") || fraseAplanada.contains("votar")) {
            
            session.getArrayrespuesta().add("Califiquemos entonces, del 1 al 5, cuantas estrellas le das ???");
            session.setEstadoChatBot(EstadoChat.CALIFICAR);
            
        } else if (fraseAplanada.contains("buscar") || fraseAplanada.contains("encontrar") || 
                    fraseAplanada.contains("localizar") || fraseAplanada.contains("todo") || fraseAplanada.contains("mas")) {
            
            session.getArrayrespuesta().add("Seguimos buscando entonces...");
            session.getEstadoConversacion().resetCoincidenciaNombre();
            session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
            procesarGrupoFabricaFrases(session.getNodoFrase());
        
        } else {

            session.getArrayrespuesta().add("Ok dale perfecto, olvidemos la busqueda...");
            session.getEstadoConversacion().resetCoincidenciaNombre();
            session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
        }   
    }
    
    private UserVoto getVoto(int idUser, int idDt)
    {
        UserVoto voto = new UserVoto();
        
        try {
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            PreparedStatement sentencia = null;
            ResultSet rs=null;
            
//            Statement stmt = conn.createStatement();
/*            
            String query = "SELECT dtVoto.id, dtVoto.voto, dtVoto.fecha FROM dtVoto WHERE dtVoto.idUser=" + 
                    idUser + " and dtVoto.idDt="+idDt;
            ResultSet rs = stmt.executeQuery(query);
//*/        
            String query = "SELECT dtVoto.id, dtVoto.voto, dtVoto.fecha FROM dtVoto WHERE dtVoto.idUser=? and dtVoto.idDt=?";

            sentencia = conn.prepareStatement(query);
            sentencia.setInt(1, idUser);
            sentencia.setInt(2, idDt);
            rs = sentencia.executeQuery();
            
            if (rs.next())
            {
                voto.setExisteData(true);
                voto.setFecha(rs.getString("dtVoto.fecha"));
                voto.setVoto(rs.getInt("dtVoto.voto"));
                voto.setIdVoto(rs.getInt("dtVoto.id"));
                voto.setIdDt(session.getEstadoConversacion().getCoincidenciaNombre().getIdDt());
                voto.setIdUser(session.getIdUser());
            }
            
            rs.close();
            sentencia.close();
            //stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return voto;
    }
    
    private void actualizarCalificacionLugar(int idDt)
    {
        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            ResultSet rs=null;
            PreparedStatement sentencia = null;
            PreparedStatement sentencia_update = null;
            int estrellas = 0;
            //Statement stmt = conn.createStatement();

/*
            String query = "SELECT AVG(voto) as promedio FROM dtVoto WHERE dtVoto.idDt=" + idDt;
            ResultSet rs = stmt.executeQuery(query);
//*/            
            String query = "SELECT AVG(voto) as promedio FROM dtVoto WHERE dtVoto.idDt=?";
            sentencia = conn.prepareStatement(query);
            sentencia.setInt(1, idDt);
            
            rs = sentencia.executeQuery();

            if (rs.next())
            {
                estrellas = (int) rs.getDouble("promedio");
            }

            //query = "UPDATE dt SET calificacion=" + estrellas + " WHERE dt.idDt = " + idDt;
            query = "UPDATE dt SET calificacion=? WHERE dt.idDt = ?";
            sentencia_update = conn.prepareStatement(query);
            sentencia_update.setInt(1, estrellas);
            sentencia_update.setInt(2, idDt);
            
            sentencia_update.executeUpdate();
            
            //stmt.executeUpdate(query);

            rs.close();
            sentencia.close();
            sentencia_update.close();
            //stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void votar(int voto, int idDt)
    {
        UserVoto uservoto = getVoto(session.getIdUser(), idDt);

        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            PreparedStatement sentencia = null;
            String query="";
            
            
            if (uservoto.isExisteData())
            {
                query = "UPDATE dtVoto SET voto=?, fecha=CURRENT_DATE WHERE dtVoto.id=?";
                sentencia = conn.prepareStatement(query);
                sentencia.setInt(1, voto);
                sentencia.setInt(2, uservoto.getIdVoto());

            } else {

                query = "INSERT INTO dtVoto(idUser, idDt, voto, fecha) VALUES (?,?,?,CURRENT_DATE)";

                sentencia = conn.prepareStatement(query);
                sentencia.setInt(1, session.getIdUser());
                sentencia.setInt(2, session.getEstadoConversacion().getCoincidenciaNombre().getIdDt());
                sentencia.setInt(3, voto);
            }
            
            sentencia.executeUpdate();
            sentencia.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void procesarCalificar(String frase)
    {
        int voto = getNumeroDeMensaje(frase, 5);
        if (voto >0)
        {
            session.getArrayrespuesta().add("Perfecto, lo hemos calificado con " + voto + " estrellas");
            session.getArrayrespuesta().add("Por favor, deja un comentario sobre el lugar. Es muy importante saber tu opini&oacute;n sincera");
            session.getArrayrespuesta().add("Recuerda que tu comentario es publico y muchas personas lo podrian leer junto a tu nombre.");
            session.getArrayrespuesta().add(getEstrellasHtml(voto));
            votar(voto, session.getEstadoConversacion().getCoincidenciaNombre().getIdDt());
            actualizarCalificacionLugar(session.getEstadoConversacion().getCoincidenciaNombre().getIdDt());
            session.setEstadoChatBot(EstadoChat.COMENTAR_LUGAR);

        } else {

            session.getArrayrespuesta().add("No es una calificaci&oacute;n valida, recuerde que es un n&uacute;mero del 1 hasta el 5");
        }
    }
    
    private void procesarCambioLimiteBusqueda(String frase)
    {
        int limite = getNumeroDeMensaje(frase, 20);
        if (limite>0)
        {
            session.getArrayrespuesta().add("Ok, he modificado el limite de los resultados que te muestro, ahora es de <b>" + limite + "</b> en cada consulta");
            session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
            
        } else {

            session.getArrayrespuesta().add("Disculpa, como te mencione antes, existen unos limites, <b>los valores posibles van desde 1 hasta 20</b>");
            session.getArrayrespuesta().add("Por favor, dame un valor en ese rango, y disculpame");
        }
    }
    
    private void procesarCambioRango(String frase, boolean buscar)
    {
        int radio = getNumeroDeMensaje(frase, 10000);
        if (radio>0)
        {
            session.getArrayrespuesta().add("Ok, voy a cambiar el radio de busqueda");
            session.getArrayrespuesta().add(
                    "Voy a llevarlo de " + String.valueOf((int)(session.getRadioBusqueda()/1000)) + " kilometros a " + String.valueOf(radio));
            session.setRadioBusqueda((radio*1000));
            if (buscar)
            {
                if (construirRespuestaSegunContexto()) //Entra si fue procesado
                {
                    session.getArrayrespuesta().add(session.getNombre() + ", cada vez que desees volver a cambiar el radio de busqueda hazmelo saber asi ");
                    session.getArrayrespuesta().add("Escribe <b>cambiar radio de busqueda</b>");
                }
                
            } else {

                session.getEstadoConversacion().resetCoincidenciaNombre();
                session.getEstadoConversacion().resetContexto();
                session.getEstadoConversacion().resetEstadoConversacion();
                session.setEstadoChatBot(EstadoChat.SIN_ESTADO);

            }
            
        } else {

            session.getArrayrespuesta().add("Epa, eso no es un numero valido,  el rango es de 1 hasta 10.000");
            
        }
    }
    
    private boolean validarCambioRadioBusqueda(NodoFrase nodo, String frase)
    {
        boolean activar = false;
        
        if (
            frase.contains("radio de busqueda") || 
            frase.contains("radio de la busqueda") ||
            frase.contains("radio busqueda") || 
            frase.contains("radio de las busquedas") || 
            frase.contains("radio de las busqueda") || 
            frase.contains("ampliar radio") ||
            frase.contains("disminuir radio") ||
            frase.contains("cambiar radio") ||
            frase.contains("modificar radio") ||
            frase.contains("establecer radio") ||

            frase.contains("alcance de busqueda") || 
            frase.contains("alcance de la busqueda") ||
            frase.contains("alcance busqueda") || 
            frase.contains("alcance de las busquedas") || 
            frase.contains("alcance de las busqueda") || 
            frase.contains("ampliar alcance") ||
            frase.contains("disminuir alcance") ||
            frase.contains("cambiar alcance") ||
            frase.contains("modificar alcance") ||
            frase.contains("establecer alcance") ||
                
            frase.contains("limite de busqueda") || 
            frase.contains("limite de la busqueda") ||
            frase.contains("limite busqueda") || 
            frase.contains("limite de las busquedas") || 
            frase.contains("limite de las busqueda") || 
            frase.contains("ampliar limite") ||
            frase.contains("disminuir limite") ||
            frase.contains("cambiar limite") ||
            frase.contains("modificar limite") ||
            frase.contains("establecer limite") ||
                
            frase.contains("distancia de busqueda") || 
            frase.contains("distancia de la busqueda") ||
            frase.contains("distancia busqueda") || 
            frase.contains("distancia de las busquedas") ||
            frase.contains("distancia de las busqueda") ||
            frase.contains("ampliar distancia") ||
            frase.contains("disminuir distancia") ||
            frase.contains("cambiar distancia") ||
            frase.contains("modificar distancia") ||
            frase.contains("establecer distancia") ||
                
            frase.contains("amplitud de busqueda") || 
            frase.contains("amplitud de la busqueda") ||
            frase.contains("amplitud busqueda") || 
            frase.contains("amplitud de las busquedas") ||
            frase.contains("amplitud de las busqueda") ||
            frase.contains("ampliar amplitud") ||
            frase.contains("disminuir amplitud") ||
            frase.contains("cambiar amplitud") ||
            frase.contains("modificar amplitud") ||
            frase.contains("establecer amplitud") ||

            frase.contains("rango de busqueda") || 
            frase.contains("rango de la busqueda") ||
            frase.contains("rango busqueda") || 
            frase.contains("rango de las busquedas") ||
            frase.contains("rango de las busqueda") ||
            frase.contains("ampliar rango") ||
            frase.contains("disminuir rango") ||
            frase.contains("cambiar rango") ||
            frase.contains("modificar rango") ||
            frase.contains("establecer rango"))
            {
            activar = true;
            if (session.getRadioBusqueda()<1000)
            {
                session.getArrayrespuesta().add("Ok, tu radio actual de busqueda es " + String.valueOf(session.getRadioBusqueda())+ " metros" );

            } else {

                session.getArrayrespuesta().add("Ok, tu radio actual de busqueda es " + String.valueOf((int)(session.getRadioBusqueda()/1000)) + " kilometros" );
            }

            session.getArrayrespuesta().add("Por favor introduce un radio en kilometros");
            session.getArrayrespuesta().add("Recuerda que un kilometro son como unas 10 calles");

            session.setEstadoChatBot(EstadoChat.CAMBIAR_RANGO_BUSQUEDA_SIN_BUSCAR);
        }
        return activar;
    }
    
    private void procesarMostrarMasResultados(String frase)
    {
        EnumInterrogacion respuesta =  procesarSiNo(frase, "si");
        int indice = session.getIndiceArrayListDataResultados();
        int parte = session.getParteIndiceArrayListDataResultados();
        
        switch (respuesta)
        {
            case RS_FUERTE_SI:
            case RS_DEBIL_SI:
            {
                if (parte==1)
                {
                    parte++;
                    session.setParteIndiceArrayListDataResultados(parte);
                    
                } else {
                    
                    if (indice<session.getArrayListDataResultados().size())
                    {
                        session.setParteIndiceArrayListDataResultados(1);
                        indice++;
                        session.setIndiceArrayListDataResultados(indice);
                    }
                }
                
            } break;

            case RS_FUERTE_NO:
            case RS_DEBIL_NO:
            case RS_NO_SE:
            {
                if ((indice<session.getArrayListDataResultados().size()) && (parte==1))
                {
                    indice++;
                    session.setIndiceArrayListDataResultados(indice);

                } else {

                    session.getArrayrespuesta().add("Ok, voy a dejar de mostrarte informaci&oacute;n");
                    session.resetArrayListDataResultados();
                    session.getEstadoConversacion().resetCoincidenciaNombre();
                    session.getEstadoConversacion().resetContexto();
                    session.getEstadoConversacion().resetEstadoConversacion();
                    session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
                }
                
            } break;
        }
    }
    
    private void actualizarComentarioLugar(String comentario)
    {

        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            PreparedStatement sentencia = null;
            String query="";
            
            
            query = "UPDATE dtVoto SET comentario=? WHERE idUser=? AND idDt=?";
            sentencia = conn.prepareStatement(query);
            sentencia.setString(1, comentario);
            sentencia.setInt(2, session.getIdUser());
            sentencia.setInt(3, session.getEstadoConversacion().getCoincidenciaNombre().getIdDt());

            sentencia.executeUpdate();
            sentencia.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void procesarComentarLugar(String frase)
    {
        frase = frase.trim().toLowerCase();
        
        if (frase.isEmpty())
        {
            session.getArrayrespuesta().add("Si asi lo prefieres, dejemos el comentario en blanco");
            
        } else {
            
            session.getArrayrespuesta().add("Excelente, hemos procesado tu comentario, muchas gracias por hacerlo");
        }

        actualizarComentarioLugar(frase);
        
        session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
    }
    
    public void procesarFrase(String frase) 
    {
        session.getArrayrespuesta().clear();
        
        if (session.isEsValida())
        {
            switch (session.getEstadoChatBot()) 
            {
                case SIN_ESTADO:
                {
                    procesarSinEstado(frase);

                } break;

                case SI_NO_MOSTRAR_MAS_RESULTADOS:
                {
                    procesarMostrarMasResultados(frase);

                } break;

                case SI_NO_CORREGIR:
                {
                    procesarSiNoCorregir(frase);

                } break;

                case SI_NO_BUSCAR_MAS:
                {
                    procesarSiNoBuscarMas(frase);

                } break;

                case CAMBIAR_RANGO_BUSQUEDA:
                {
                    procesarCambioRango(frase, true);

                } break;

                case CAMBIAR_RANGO_BUSQUEDA_SIN_BUSCAR:
                {
                    procesarCambioRango(frase, false);

                } break;

                case CAMBIO_LIMITE_BUSQUEDA:
                {
                    procesarCambioLimiteBusqueda(frase);

                } break;
                
                case SI_NO_CONOCIDO:
                {
                    procesarSiNoConocido(frase);

                } break;

                case CALIFICAR:
                {
                    procesarCalificar(frase);

                } break;
                
                case COMENTAR_LUGAR:
                {
                    procesarComentarLugar(frase);

                } break;

            }
            
        } else { // El usuario no ha iniciado session
            
            
            frase = frase.trim().toLowerCase();

            switch (session.getEstadoChatBot()) 
            {
                case ESPERANDO_NOMBRE_USER:
                {
                    procesarInicioSessionNombre(frase);

                } break;
            
                case ESPERANDO_PWD_USER:
                {
                    procesarInicioSessionClave(frase);

                } break;
                
                case SI_NO_RECUPERAR_CUENTA:
                {
                    recuperarCuentaUser(frase);

                } break;
                
                case SI_NO_CREAR_USUARIO:
                {
                    procesarSiNoCrearUsuario(frase);

                } break;
                
                case CREAR_CUENTA_NOMBRE:
                {
                    procesarCrearCuentaNombre(frase);

                } break;
                
                case CREAR_CUENTA_CLAVE:
                {
                    procesarCrearCuentaClave(frase);

                } break;

                case CREAR_CUENTA_NOMBRE_COMPLETO:
                {
                    procesarCrearCuentaNombreCompleto(frase);

                } break;

                case CREAR_CUENTA_PAIS:
                {
                    procesarCrearCuentaPais(frase);

                } break;
                
                case CREAR_CUENTA_CORREO:
                {
                    procesarCrearCuentaCorreo(frase);

                } break;

            }
        }
    }
    
    private boolean crearNuevoUsuario()
    {
        boolean procesado = false;
    
        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            PreparedStatement sentencia = null;
            
            //Statement stmt = conn.createStatement();
/*
            String query = "INSERT INTO user(nombre, clave, correo, userName, codPais) VALUES (\""+
                    session.getNombreCompletoNuevoUser() + "\",\"" +
                    session.getClaveNuevoUser() + "\",\"" +
                    session.getCorreoNuevoUser() + "\",\"" +
                    session.getNuevoUser() + "\"," + 
                    session.getPaisNuevoUser() + ")";
            stmt.executeUpdate(query);
//*/

            String query = "INSERT INTO user(nombre, clave, correo, userName, codPais) VALUES (?,?,?,?,?)";
            sentencia = conn.prepareStatement(query);
            sentencia.setString(1, session.getNombreCompletoNuevoUser());
            sentencia.setString(2, session.getClaveNuevoUser());
            sentencia.setString(3, session.getCorreoNuevoUser());
            sentencia.setString(4, session.getNuevoUser());
            sentencia.setInt(5, session.getPaisNuevoUser());
            
            sentencia.executeUpdate();
            
            procesado = true;
            
//            stmt.close();
            sentencia.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return procesado;
    }
    
    private void procesarCrearCuentaCorreo(String frase)
    {
        session.setCorreoNuevoUser(frase);
        if (crearNuevoUsuario())
        {
            
            session.setClave(session.getClaveNuevoUser());
            session.setNombreUser(session.getNuevoUser());
            session.iniciarSession();
            
            if (session.isEsValida())
            {
            
                session.getArrayrespuesta().add("Hemos terminado el proceso de registro");
                session.getArrayrespuesta().add(this.getSaludoInicioSession());
                session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
                session.resetIntentoIniSession();
            
            } else {

                session.getArrayrespuesta().add("Uff, he tenido un problema con la red...  &#9785");
                session.getArrayrespuesta().add("No puedo iniciar sesion");
                session.setEstadoChatBot(EstadoChat.ESPERANDO_NOMBRE_USER);
                session.resetIntentoIniSession();
            }
            
        } else {
            
        }
    }
    
    private int getCodigoPais(String pais)
    {
        int resul=0;
        
        return 1;
    }
    
    private void procesarCrearCuentaPais(String frase)
    {
        int codPais = getCodigoPais(frase);
        
        if (codPais>0)
        {
            session.getArrayrespuesta().add(frase + ", es un pais muy hermoso");
            session.getArrayrespuesta().add("Por ultimo, dame tu correo");

            session.setPaisNuevoUser(codPais);
            session.setEstadoChatBot(EstadoChat.CREAR_CUENTA_CORREO);

        } else {
            
            session.getArrayrespuesta().add("no conozco ese pais, podrias darme otro nombre con se conoce a tu pais");
        }
        
    }
    
    private void procesarCrearCuentaNombreCompleto(String frase)
    {
        if (frase.matches("[a-zA-ZñÑ ]*"))
        {
            session.getArrayrespuesta().add("Bonito nombre.... ;-)");
            session.getArrayrespuesta().add("Ahora necesito que me digas de que pais eres");
           
            session.setNombreCompletoNuevoUser(frase);
            session.setEstadoChatBot(EstadoChat.CREAR_CUENTA_PAIS);

        } else {

            session.getArrayrespuesta().add("No puedo registrar el nombre, solo puedo grabar nombres que contengan letras");
        }
    }
    
    private void procesarCrearCuentaClave(String frase)
    {
        int largo = frase.length();
        
        if (largo < 9)
        {
            if (largo>3)
            {
                if (frase.matches("[a-zA-Z0-9]*"))
                {
                    session.setClaveNuevoUser(frase);
                    session.getArrayrespuesta().add("Excelente, ahora dime tus apellidos y nombres");
                    session.setEstadoChatBot(EstadoChat.CREAR_CUENTA_NOMBRE_COMPLETO);
                    
                } else {

                    session.getArrayrespuesta().add("Recuerda que solo letas y numeros");
                    session.getArrayrespuesta().add("Piensa en otra clave y escribelo por favor");

                }
                
            } else {

                session.getArrayrespuesta().add("La clave  debe tener minimo 4 caracteres");
                session.getArrayrespuesta().add("Escribe una un poco mas larga, pero no tanto, maximo 8 caracteres");

            }
            
            
        } else {
            
            session.getArrayrespuesta().add("Clave de usuario muy larga... :-O");
            session.getArrayrespuesta().add("Encuentra una mas corta pero no tan corta, minimo 4 caracteres");

        }
        
    }
    
    private void procesarCrearCuentaNombre(String frase)
    {
        int largo = frase.length();
        
        if (largo < 16)
        {
            if (largo>5)
            {
                if (frase.matches("[a-zA-Z0-9]*"))
                {
                    if (!existeNombreUser(frase))
                    {
                        session.setNuevoUser(frase);
                        session.getArrayrespuesta().add("Muy bien, a continuaci&oacute;n dime una clave");
                        session.setEstadoChatBot(EstadoChat.CREAR_CUENTA_CLAVE);
                        
                    } else {

                        session.getArrayrespuesta().add("El nombre " + frase + " lo esta usando otra persona");
                        session.getArrayrespuesta().add("Por favor dime otro");
                    }
                    
                } else {

                    session.getArrayrespuesta().add("Recuerda que solo letas y numeros");
                    session.getArrayrespuesta().add("Piensa en otro nombre y escribelo por favor");

                }
                
            } else {

                session.getArrayrespuesta().add("El nombre de usuario debe tener minimo 6 caracteres");
                session.getArrayrespuesta().add("Escribe uno un poco mas largo, pero no tanto, maximo 15 caracteres");

            }
            
            
        } else {
            
            session.getArrayrespuesta().add("Nombre de usuario muy largo... :-O");
            session.getArrayrespuesta().add("Encuentra un nombre mas corto pero no tan corto, minimo 6 caracteres");

        }
    }
    
    private void procesarSiNoCrearUsuario(String frase)
    {
        EnumInterrogacion respuesta =  procesarSiNo(frase, "si");
        
        switch (respuesta)
        {
            case RS_FUERTE_SI:
            {
                session.getArrayrespuesta().add("Perfecto");
                session.getArrayrespuesta().add("Dime un nombre de usuario");
                session.getArrayrespuesta().add("Solo letras y numeros sin espacios");
                session.getArrayrespuesta().add("Como maximo 15 caracteres");
                session.setEstadoChatBot(EstadoChat.CREAR_CUENTA_NOMBRE);
               
            } break;
            
            case RS_DEBIL_SI:
            {
                session.getArrayrespuesta().add("No te preocupes es divertido...");
                session.getArrayrespuesta().add("Dime un nombre de usuario");
                session.getArrayrespuesta().add("Solo letras y numeros sin espacios");
                session.getArrayrespuesta().add("Como maximo 15 caracteres");
                session.setEstadoChatBot(EstadoChat.CREAR_CUENTA_NOMBRE);
                
                
            } break;

            case RS_FUERTE_NO:
            {
                session.getArrayrespuesta().add("¿ no ?");
                session.getArrayrespuesta().add("Ok, pero recuerda que para poder usar el chat debes tener una cuenta");
                session.setEstadoChatBot(EstadoChat.ESPERANDO_NOMBRE_USER);

            } break;

            case RS_DEBIL_NO:
            {
                session.getArrayrespuesta().add("No eres muy claro en tus palabras");
                session.getArrayrespuesta().add("Pero voy a interpretar que no quieres abrir una cuenta");
                session.getArrayrespuesta().add("Ok, pero recuerda que para poder usar el chat debes tener una cuenta");
                session.setEstadoChatBot(EstadoChat.ESPERANDO_NOMBRE_USER);
                
            } break;

            case RS_NO_SE:
            {
                session.getArrayrespuesta().add("Esta bien, como ested quiera !!!");
                session.getArrayrespuesta().add("Aqui estaré 24 horas al dia, 365 y pico de dias al año");
                session.setEstadoChatBot(EstadoChat.ESPERANDO_NOMBRE_USER);
                
            } break;
        }

    }
    
    private void recuperarCuentaUser(String frase)
    {
        session.getArrayrespuesta().add("Uff, disculpame...  &#9785");
        session.getArrayrespuesta().add("Me estan diciendo que la opción de recuperar tu cuenta no esta habilitada");
        session.getArrayrespuesta().add("Espero sepas disculparme  :-)");
        session.setEstadoChatBot(EstadoChat.ESPERANDO_NOMBRE_USER);
        
    }
    
    private boolean iniciarSession(String frase)
    {
        boolean correcto = false;
        
        session.setClave(frase);
        session.iniciarSession();
        
        correcto = session.isEsValida();
        
        return correcto;
    }
    
    private void procesarInicioSessionClave(String frase)
    {
       String corte[] = frase.split(" ");
       session.contarIntentoIniSession();

       if (session.getIntentosIniSession()<3)
        {
            if (corte.length==1)
            {
                if (iniciarSession(frase))
                {
                        session.getArrayrespuesta().add(this.getSaludoInicioSession());
                        session.setEstadoChatBot(EstadoChat.SIN_ESTADO);
                        session.resetIntentoIniSession();
                    
                } else {
                    
                    if (session.getIntentosIniSession()==1)
                    {
                        session.getArrayrespuesta().add("Tu clave no es la que tengo guardada");
                        session.setEstadoChatBot(EstadoChat.ESPERANDO_PWD_USER);
 
                    } else {

                        session.getArrayrespuesta().add("Igual, esta tampoco es");
                        session.setEstadoChatBot(EstadoChat.ESPERANDO_PWD_USER);
                    }
                }

            } else {

                
                if (session.getIntentosIniSession()==1)
                {
                    session.getArrayrespuesta().add("Debes decirme solamente la clave de usuario");
                    session.getArrayrespuesta().add("Te recuerdo que no debe contener espacios en blanco, solo letras y numeros");
                    session.setEstadoChatBot(EstadoChat.ESPERANDO_PWD_USER);

                } else {

                    session.getArrayrespuesta().add("No entiendo tu clave");
                    session.getArrayrespuesta().add("Necesito que me digas solamente tu clave");
                    session.setEstadoChatBot(EstadoChat.ESPERANDO_PWD_USER);
                }
            }

        } else {

            session.getArrayrespuesta().add("Llevas 3 intentos fallidos con tu contraseña");
            session.getArrayrespuesta().add("¿ Deseas recuperar tu contraseña, <b>Si</b> o <b>No</b> ?");
            session.setEstadoChatBot(EstadoChat.SI_NO_RECUPERAR_CUENTA);
            session.resetIntentoIniSession();
            
        }

    }

    private void procesarInicioSessionNombre(String frase)
    {
        String corte[] = frase.split(" ");

        session.contarIntentoIniSession();
        
        if (session.getIntentosIniSession()<3)
        {
            if (corte.length==1)
            {
                if (existeNombreUser(frase))
                {
                    if (session.getIntentosIniSession()==1)
                    {
                        session.getArrayrespuesta().add("Hola <b>" + frase + "</b>");
                        session.getArrayrespuesta().add("Ahora, necesito tu palabra de paso o clave");
                        session.setEstadoChatBot(EstadoChat.ESPERANDO_PWD_USER);
                        session.resetIntentoIniSession();
                        session.setNombreUser(frase);

                    } else {

                        session.getArrayrespuesta().add("Hola <b>" + frase + "</b> que bueno que recordaste tu nombre");
                        session.getArrayrespuesta().add("Ahora, dime cual es tu contraseña");
                        session.setEstadoChatBot(EstadoChat.ESPERANDO_PWD_USER);
                        session.resetIntentoIniSession();
                        session.setNombreUser(frase);
                    }
                    
                } else {
                    
                    if (session.getIntentosIniSession()==1)
                    {
                        session.getArrayrespuesta().add("El nombre de usuario <b>" + frase  + "</b> no lo conozco");
                        session.getArrayrespuesta().add("Por favor verificalo...");
                        session.setEstadoChatBot(EstadoChat.ESPERANDO_NOMBRE_USER);
 
                    } else {

                        session.getArrayrespuesta().add("Este otro nombre <b>" + frase  + "</b> tampoco lo conozco");
                        session.setEstadoChatBot(EstadoChat.ESPERANDO_NOMBRE_USER);
                    }
                }

            } else {

                boolean noTengoInferido = false;
                
                for (String simbolo : noTengoCuenta)
                {
                    if (frase.contains(simbolo))
                    {
                        noTengoInferido = true;
                        break;
                    }
                }

                if (noTengoInferido)
                {

                    session.getArrayrespuesta().add("Si lo deseas, puedes crear un usuario");
                    session.getArrayrespuesta().add("un usuario te permite interactuar con tu chat");
                    session.getArrayrespuesta().add("¿ Quieres crear un usuario, <b>Si</b> o <b>No</b> ?");
                    session.setEstadoChatBot(EstadoChat.SI_NO_CREAR_USUARIO);
                    session.resetIntentoIniSession();
                
                } else {
                
                    if (session.getIntentosIniSession()==1)
                    {
                        session.getArrayrespuesta().add("Debes decirme solamente el nombre de usuario");
                        session.getArrayrespuesta().add("Te recuerdo que no debe contener espacios en blanco");
                        session.setEstadoChatBot(EstadoChat.ESPERANDO_NOMBRE_USER);

                    } else {

                        session.getArrayrespuesta().add("Eso que me dices puedo interpretarlo de varias formas");
                        session.getArrayrespuesta().add("Pero, la verdad es que nombre de usuarios hay muchos");
                        session.getArrayrespuesta().add("Por eso necesito que seas preciso con el nombre de usuario");
                        session.setEstadoChatBot(EstadoChat.ESPERANDO_NOMBRE_USER);
                    }
                }
            }

        } else {

            session.getArrayrespuesta().add("Creo que se te olvido tu nombre de usuario, si quieres podemos tratar de recuperar tu cuenta");
            session.getArrayrespuesta().add("¿ Deseas recuperar tu cuenta, <b>Si</b> o <b>No</b> ?");
            session.setEstadoChatBot(EstadoChat.SI_NO_RECUPERAR_CUENTA);
            session.resetIntentoIniSession();
        }
    }
    
    private boolean existeNombreUser(String nombreUser)
    {
        boolean existe = false;

        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            ResultSet rs;
            PreparedStatement sentencia = null;
            //Statement stmt = conn.createStatement();
/*        
            String query = "SELECT * FROM user WHERE userName=\"" + nombreUser + "\""; 
            ResultSet rs = stmt.executeQuery(query);
//*/  
            String query = "SELECT * FROM user WHERE userName=?"; 
            sentencia = conn.prepareStatement(query);
            sentencia.setString(1, nombreUser);
            
            rs = sentencia.executeQuery();

            if (rs.next())
            {
                existe = true;
            } 
            
            rs.close();
            sentencia.close();
            //stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return existe;
    }
    
    private int reconocerFrase(NodoFrase nodo)
    {
                
        int codFrase = 0;

        try {
            
            if (nodo.getArrayPalabrasAplanadas().isEmpty()) return codFrase;
            
            boolean encontrada=false;
            
            HashMap<Integer, Integer> mapaReconocimiento = new HashMap<>();
            
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            Statement stmt = conn.createStatement();
//*        
            String query = "SELECT codFrase, frase FROM fabricaFrases ORDER BY codFrase";
            ResultSet rs = stmt.executeQuery(query);
//*/            
            int distancia = 2;
            
            while (rs.next())
            {
                distancia = LevenshteinDistance.computeLevenshteinDistancePalabras(rs.getString("frase").split(" "), nodo.getFraseVectorAplanado());
                
                if (distancia==0)
                {
                    codFrase = rs.getInt("codFrase");
                    encontrada = true;
                    break;
                    
                } else if ((distancia==1) && (nodo.getArrayPalabrasAplanadas().size()>1)) {
                    
                    codFrase = rs.getInt("codFrase");
                    mapaReconocimiento.put(codFrase, mapaReconocimiento.getOrDefault(codFrase, 0)+1);
                }
            }
            
            if ((codFrase>0) && (!encontrada))
            {
                int mayor=0;
                int valor=0;
                for (Map.Entry<Integer, Integer> lista: mapaReconocimiento.entrySet())
                {
                    valor = lista.getValue();
                    if (mayor<valor)
                    {
                        mayor = valor;
                        codFrase = lista.getKey();
                    }
                }
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }

        return codFrase;

    }

    /*
    Base de datos dt, el campo tipo puede ser 1 Lugar, 2 Producto y 3 Caracteristica
    Para el caso 3 o mas, el valor se le resta 3, es decir:
    si es 3 entonces 3 -2 = 1 y 1 es el numero de corrrimiento para el flag 1<<1 y asi sucesivamente
    */
    
    private void insertHistorialChatUser(NodoFrase nodo)
    {
        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            PreparedStatement sentencia = null;
//            Statement stmt = conn.createStatement();
            String query;
            
            String mensaje;
            String mensajeAplanado;
            String mensajeLimpio;

            mensaje = nodo.getFraseOriginal();
            mensajeAplanado = String.join(" ", nodo.getFraseVectorAplanado());
            mensajeLimpio = String.join(" ", nodo.getFraseVector());
            
/*
            query = "INSERT INTO conversacion(idUser, respuestaBot, mensaje, mensajeAplanado, mensajeLimpio, fecha, hora) "+ 
                    "VALUES (" +
                    session.getIdUser() + ",\"\",\"" + 
                    mensaje + "\",\"" + 
                    mensajeAplanado + "\",\"" + 
                    mensajeLimpio + "\", CURRENT_DATE, CURRENT_TIME)";
                    
            stmt.executeUpdate(query);
//*/


            query = "INSERT INTO conversacion(idUser, respuestaBot, mensaje, mensajeAplanado, mensajeLimpio, fecha, hora) "+ 
                    "VALUES (?,?,?,?,?,CURRENT_DATE, CURRENT_TIME)";
            
            sentencia = conn.prepareStatement(query);
            sentencia.setInt(1, session.getIdUser());
            sentencia.setString(2, "");
            sentencia.setString(3, mensaje);
            sentencia.setString(4, mensajeAplanado);
            sentencia.setString(5, mensajeLimpio);
            
            sentencia.executeUpdate();

//            stmt.close();
            sentencia.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int getIdMensajeHistorial()
    {
        int id=0;
        
        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM conversacion WHERE idUser=" + session.getIdUser() + " ORDER BY idConversacion DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next())
            {
                id = rs.getInt("idConversacion");
            } 
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        return id;
    }
    
    private void insertHistorialChatBot(String respuestaBot)
    {
        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
          //  Statement stmt = conn.createStatement();
            String query;
            PreparedStatement sentencia = null;

            query = "UPDATE conversacion SET respuestaBot=? WHERE idConversacion=?";
             
            sentencia = conn.prepareStatement(query);
            
            sentencia.setString(1, respuestaBot);
            sentencia.setInt(2, session.getIdMensajeHistorial());
            
            sentencia.executeUpdate();
            
            //stmt.executeUpdate(query);

            
            sentencia.close();
           // stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MotorMangoChatBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public ArrayList<String> respuestaChatBot()
    {
        String respuesta= "";
        lenguaje.actualizarPalabrasNuevas(session.getPalabrasNuevas());
        lenguaje.actualizarFrecuenciaBolsaPalabras(session.getPalabrasBolsa());
        
        session.resetArrayPalabrasNuevas();
        session.resetArrayPalabraBolsa();
  
        if (session.getEstadoChatBot()==EstadoChat.SI_NO_MOSTRAR_MAS_RESULTADOS)
        {
            int indice = session.getIndiceArrayListDataResultados();
            int parte = session.getParteIndiceArrayListDataResultados();
            int total = session.getArrayListDataResultados().size();
            
            DataResultado data = session.getArrayListDataResultados().get(indice-1);
            if (parte==1)
            {
                if (indice>1)
                {
                    session.getArrayrespuesta().add("La siguiente es");
                }
            }
            session.getArrayrespuesta().add(data.getParte(parte));
            
            if ((indice==total) && (parte==2))
            {
                session.getArrayrespuesta().add("Podemos modificar el radio de busqueda, para el caso que quieras ver mas o menos informaci&oacute;n");
                if (session.getRadioBusqueda() < 1000)
                {
                    session.getArrayrespuesta().add("Para el momento esta en " + String.valueOf(session.getRadioBusqueda()) + " metros");

                } else {

                    session.getArrayrespuesta().add("Para el momento esta en " + String.valueOf((int)(session.getRadioBusqueda()/1000)) + " kilometros");
                }

                session.getArrayrespuesta().add("¿ Quieres cambiar el radio de busqueda, <b>Si</b> o <b>No</b> ?");
                session.resetArrayListDataResultados();
                session.setEstadoChatBot(EstadoChat.SI_NO_BUSCAR_MAS);

            } else {
                
                if (parte==1)
                {
                    session.getArrayrespuesta().add("Deseas ampliar la informaci&oacute;n sobre el lugar, <b>Si</b> o <b>No</b> ");
                    
                } else {
                    
                    session.getArrayrespuesta().add("Te muestro la siguiente recomendaci&oacute;n, <b>Si</b> o <b>No</b>");
                }
            }
        }
        
        Iterator iter = session.getArrayrespuesta().iterator();
        String mensaje;
        while(iter.hasNext())
        {
            mensaje = (String)iter.next();
            if (mensaje.contains("data:image/jpg;base64")) continue;

            respuesta = respuesta + mensaje + " ";
        }
        
        if (session.isEsValida())
        {
            NodoFrase n = session.getNodoFrase();
            if (n!=null)
            {
                lenguaje.actualizarFrecuenciaFrasesUsuarios(n.getFraseOriginal(), n.getArrayPalabras());
                insertHistorialChatBot(respuesta);
            }
        }
        
        return session.getArrayrespuesta();
    }
    
}
