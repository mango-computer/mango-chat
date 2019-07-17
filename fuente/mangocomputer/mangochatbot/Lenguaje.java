
package mangocomputer.mangochatbot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose Andres Morales Linares
 */
public class Lenguaje {

    private HashMap<Integer, String> arrayContexto;
    private HashSet<String> arrayArticulos;
    private HashMap<String, String> pSiNo;
    private HashMap<String, String> arrayPalabrasLargas;

    public Lenguaje() 
    {
        pSiNo = new HashMap<>(100);
        arrayArticulos = new HashSet<>(100);
        arrayContexto = new HashMap<>(100);
        arrayPalabrasLargas = new HashMap<>(100);
    }
    
    public void cargarDiccionarios()
    {
        cargarArticulos();
        cargarContexto();
        cargarSiNo();
        cargarPalasbraLargas();
    }

    
    private void cargarPalasbraLargas()
    {
       try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM palabraLarga";
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                arrayPalabrasLargas.put(rs.getString("palabra"), rs.getString("palabraCorta"));
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HashMap<String, String> getArrayPalabrasLargas() {
        return arrayPalabrasLargas;
    }
    
    
    
    public String getRespuestaFrase(int codFrase)
    {
        String res="";
        ArrayList<String> arrayRespuestas = new ArrayList<>(); 
        
        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            Statement stmt = conn.createStatement();
            
            String query = "SELECT respuesta FROM respuestaFrases WHERE codFrase=" + codFrase;
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next())
            {
                arrayRespuestas.add(rs.getString("respuesta"));
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (!arrayRespuestas.isEmpty())
        {
            res = (String)arrayRespuestas.get((int)Math.floor(Math.random()*arrayRespuestas.size()));
        }
        
        return res;
    }
    
    private void cargarSiNo()
    {
        try {
            
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            Statement stmt = conn.createStatement();
            
            
            String query = "SELECT palabra, semantica FROM semantica_sino";
            ResultSet rs = stmt.executeQuery(query);
            
            String palabra="";
            String semantica="";
            
            while (rs.next())
            {
                palabra = rs.getString("palabra");
                semantica = rs.getString("semantica");
                pSiNo.put(palabra, semantica);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public HashMap<String, String> getSiNo()
    {
        return pSiNo;
    }
    
    public int[] totalSiNoRespuesta(ArrayList<String> lista, String patron)
    {
        int res[]= new int[2];
        res[0]=0;
        res[1]=0;
        
        String temp="";
        String llave="";
        
        Iterator<String> iter = lista.iterator();
        while(iter.hasNext())
        {
            llave = (String)iter.next();
            if (pSiNo.containsKey(llave))
            {
                temp = pSiNo.getOrDefault(llave,patron);
                if (temp.equals(patron))
                {
                    res[0]++;
                    
                } else {
                    
                    res[1]++;
                }
            } 
        }
        
        return res;
    }

    private void cargarArticulos()
    {
        try {
            
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            Statement stmt = conn.createStatement();
            
            String query = "SELECT articulo FROM articulos";
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                arrayArticulos.add(rs.getString("articulo"));
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cargarContexto()
    {
        try {
            
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM contexto";
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                arrayContexto.put(rs.getInt("idContexto") , rs.getString("contexto"));
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    
    public String getTraduccionDiccionarioContexto(String palabra)
    {
        String res=palabra;
        
        try {
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            ResultSet rs;
            PreparedStatement sentencia = null;

            String query = 
                    "SELECT bolsaPalabras.idContexto, bolsaPalabras.palabra, bolsaPalabras.palabraMostrar, bolsaPalabras.semantica, bolsaPalabras.tipo, contexto.contexto " + 
                    "FROM bolsaPalabras, contexto " + 
                    "WHERE bolsaPalabras.idContexto = contexto.idContexto AND bolsaPalabras.palabras=?";
            
            sentencia = conn.prepareStatement(query);
            sentencia.setString(1, palabra);
            
            rs = sentencia.executeQuery();

            if (rs.next())
            {
                res = rs.getString("contexto.contexto");
            }
            
            rs.close();
            sentencia.close();
            //stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return res;
    }
    
    
    public String getTraduccionDiccionarioSemantica(String palabra)
    {
        String res=palabra;
        
        try {
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            ResultSet rs;
            PreparedStatement sentencia = null;
            //Statement stmt = conn.createStatement();
/*        
            String query = 
                    "SELECT semantica " + 
                    "FROM bolsaPalabras " + 
                    "WHERE palabra=\"" + palabra + "\"";
            ResultSet rs = stmt.executeQuery(query);
//*/      
            String query = 
                    "SELECT semantica " + 
                    "FROM bolsaPalabras " + 
                    "WHERE palabra=?";

            sentencia = conn.prepareStatement(query);
            sentencia.setString(1, palabra);
            
            rs = sentencia.executeQuery();
            
            if (rs.next())
            {
                res = rs.getString("semantica");
            }
            
            rs.close();
            //stmt.close();
            sentencia.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return res;
    }
    
    public ArrayList<String> getArrayCambioSemantico(ArrayList<String> lista)
    {
        Iterator iter = lista.iterator();
        int i=0;
        while(iter.hasNext())
        {
            lista.set(i, getTraduccionDiccionarioSemantica((String)iter.next()));
            i++;
        }
        
        return lista;
    }
    
   
    //************************** Articulo ****************************************
    public boolean existeArticulo(String strPalabra)
    {
        return arrayArticulos.contains(strPalabra);
    }
    
    public String getTraduccionDiccionarioArticulo(String palabra)
    {
        String resul=" ";
        resul = (String)arrayArticulos.stream().filter(String -> {
            return String.equals(palabra);
        }).findFirst().orElse(" ");
    
        return resul;
    }

    public Iterator<String> getArticulos()
    {
        return arrayArticulos.iterator();
    }


    //************************** Contrexto ****************************************
    public boolean existePalabra(String palabra)
    {
        boolean res=false;
        
        try {
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            ResultSet rs;
            PreparedStatement sentencia = null;
            //Statement stmt = conn.createStatement();
/*        
            String query = 
                    "SELECT palabra " + 
                    "FROM bolsaPalabras " + 
                    "WHERE palabra=\"" + palabra + "\"";
            ResultSet rs = stmt.executeQuery(query);
//*/            

            String query = 
                    "SELECT palabra " + 
                    "FROM bolsaPalabras " + 
                    "WHERE palabra=?";

            sentencia = conn.prepareStatement(query);
            sentencia.setString(1, palabra);
            
            rs = sentencia.executeQuery();
            
            if (rs.next())
            {
                res = true;
            }
            
            rs.close();
            //stmt.close();
            sentencia.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
        return res;

    }
    
    public void actualizarPalabrasNuevas(ArrayList<String> arrayPalabrasNuevas)
    {
        if (arrayPalabrasNuevas.isEmpty()) return;

        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            PreparedStatement sentencia_select = null; 
            PreparedStatement sentencia_insert = null; 
            PreparedStatement sentencia_update = null; 

            //Statement stmt = conn.createStatement();
            //String query=null;
            ResultSet rs=null;
            String palabraNueva;

            String query_select = "SELECT palabraNueva, frecuencia FROM nuevasPalabras WHERE palabraNueva=?"; 
            sentencia_select = conn.prepareStatement(query_select);

            String query_insert = "INSERT INTO nuevasPalabras(palabraNueva, frecuencia) VALUES(?, 1)"; 
            sentencia_insert = conn.prepareStatement(query_insert);
            
            String query_update = "UPDATE nuevasPalabras SET frecuencia=(nuevasPalabras.frecuencia+1) WHERE nuevasPalabras.palabraNueva=?"; 
            sentencia_update = conn.prepareStatement(query_update);
            
            Iterator iter = arrayPalabrasNuevas.iterator();
            while (iter.hasNext())
            {
                palabraNueva = (String) iter.next();
                //query = "SELECT palabraNueva, frecuencia FROM nuevasPalabras WHERE palabraNueva=\"" + palabraNueva + "\"";
                //rs = stmt.executeQuery(query);

                sentencia_select.setString(1, palabraNueva);
                rs = sentencia_select.executeQuery();
                
                if (rs.next()) //Existe actualizar la frecuencia
                {
                   // stmt.executeUpdate("UPDATE nuevasPalabras SET frecuencia=(nuevasPalabras.frecuencia+1) WHERE nuevasPalabras.palabraNueva=\""+palabraNueva+"\"");
                   sentencia_update.setString(1, palabraNueva);
                   sentencia_update.executeUpdate();
                   
                } else { //No existe agregar

                    //stmt.executeUpdate("INSERT INTO nuevasPalabras(palabraNueva, frecuencia) VALUES(\""+palabraNueva+"\", 1)"); 
                    sentencia_insert.setString(1, palabraNueva);
                    sentencia_insert.executeUpdate();
                }

                if (rs!=null)
                {
                    rs.close();
                }

            }
            //stmt.close();
            sentencia_select.close();
            sentencia_insert.close();
            sentencia_update.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarFrecuenciaFrasesUsuarios(String frase, ArrayList<String> arrayPalabrasLimpias)
    {
        if (frase.isEmpty()) return;

        try {

            String limpio=null;
            
            Iterator iter = arrayPalabrasLimpias.iterator();
            while (iter.hasNext())
            {
                if (limpio==null)
                {
                    limpio="";
                    
                } else {
                    
                    limpio=limpio+" ";
                }
                
                limpio = limpio + ((String)iter.next());
            }
            
            
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            //Statement stmt = conn.createStatement();
            PreparedStatement sentencia_select = null; 
            PreparedStatement sentencia_actualizar = null; 

            String query_select;
            String query_actualizar;
            
            ResultSet rs=null;
 
            //query = "SELECT frecuencia FROM frasesUsuarios WHERE frase=\"" + frase + "\"";
            //rs = stmt.executeQuery(query);
            query_select = "SELECT frecuencia FROM frasesUsuarios WHERE frase=?";
            sentencia_select = conn.prepareStatement(query_select);
            sentencia_select.setString(1, frase);
            rs = sentencia_select.executeQuery();
            
            if (rs.next()) //Existe actualizar la frecuencia
            {
                //stmt.executeUpdate("UPDATE frasesUsuarios SET frecuencia=(frasesUsuarios.frecuencia+1) " + " WHERE frasesUsuarios.frase=\""+frase+"\"");
                query_actualizar = "UPDATE frasesUsuarios SET frecuencia=(frasesUsuarios.frecuencia+1) WHERE frasesUsuarios.frase=?";
                sentencia_actualizar = conn.prepareStatement(query_actualizar);
                sentencia_actualizar.setString(1, frase);
                sentencia_actualizar.executeUpdate();
                        
            } else { //No existe agregar

                //stmt.executeUpdate("INSERT INTO frasesUsuarios(frase, fraseLimpia, frecuencia) VALUES(\""+frase+"\",\""+ limpio+"\", 1)");                    
                query_actualizar = "INSERT INTO frasesUsuarios(frase, fraseLimpia, frecuencia) VALUES(?,?,1)";
                
                sentencia_actualizar = conn.prepareStatement(query_actualizar);
                sentencia_actualizar.setString(1, frase);
                sentencia_actualizar.setString(2, limpio);
                sentencia_actualizar.executeUpdate();
            }

            rs.close();
            //stmt.close();
            sentencia_select.close(); 
            sentencia_actualizar.close(); 
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void actualizarFrecuenciaBolsaPalabras(ArrayList<String> arrayPalabras)
    {
        if (arrayPalabras.isEmpty()) return;

        try {

            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            //Statement stmt = conn.createStatement();
            PreparedStatement sentencia_select = null; 
            PreparedStatement sentencia_actualizar = null; 
            
            String query_select;
            String query_actualizar;
            
            ResultSet rs=null;
            String palabra;
            
            query_select = "SELECT palabra, frecuencia FROM bolsaPalabras WHERE palabra=?";
            sentencia_select = conn.prepareStatement(query_select);
            
            query_actualizar = "UPDATE bolsaPalabras SET frecuencia=(bolsaPalabras.frecuencia+1) WHERE bolsaPalabras.palabra=?";
            sentencia_actualizar = conn.prepareStatement(query_actualizar);
                    
            Iterator iter = arrayPalabras.iterator();
            while (iter.hasNext())
            {
                palabra = (String) iter.next();
//                query = "SELECT palabra, frecuencia FROM bolsaPalabras WHERE palabra=\"" + palabra + "\"";
//                rs = stmt.executeQuery(query);
                sentencia_select.setString(1, palabra);
                rs = sentencia_select.executeQuery();

                if (rs.next()) //Existe actualizar la frecuencia
                {
//                    stmt.executeUpdate("UPDATE bolsaPalabras SET frecuencia=(bolsaPalabras.frecuencia+1) WHERE bolsaPalabras.palabra=\""+palabra+"\"");
                    sentencia_actualizar.setString(1, palabra);
                    sentencia_actualizar.executeUpdate();
                }

                if (rs!=null)
                {
                    rs.close();
                }
            }
            
            //stmt.close();
            sentencia_select.close();
            sentencia_actualizar.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Palabra getObjPalabra(String palabra)
    {
       Palabra res=null;
       int tipo=0;
       int idBolsaPalabra=0;
        
        try {
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            //Statement stmt = conn.createStatement();
            ResultSet rs;
            PreparedStatement sentencia = null; 

            String query = 
                    "SELECT bolsaPalabras.idCategoria, bolsaPalabras.idBolsaPalabra, bolsaPalabras.idContexto, bolsaPalabras.palabra, bolsaPalabras.palabraMostrar, bolsaPalabras.semantica, bolsaPalabras.tipo " + 
                    " FROM bolsaPalabras " + 
                    "WHERE bolsaPalabras.palabra=?";

            sentencia = conn.prepareStatement(query);
            sentencia.setString(1, palabra);
            
            rs = sentencia.executeQuery();
            
            if (rs.next())
            {
                tipo = rs.getInt("bolsaPalabras.tipo");
                idBolsaPalabra = rs.getInt("bolsaPalabras.idBolsaPalabra");
                
                res = new Palabra(palabra, rs.getString("bolsaPalabras.palabraMostrar"), rs.getString("bolsaPalabras.semantica"),
                            tipo, 
                            rs.getInt("bolsaPalabras.idCategoria"),
                            rs.getInt("bolsaPalabras.idContexto"), 
                            idBolsaPalabra);
                
                if (tipo==4)
                {
                    Ubigeo u = getUbigeo(idBolsaPalabra);
                    if (u != null)
                    {
                        res.setUbigeo(u);
                    }
                }
            }
            
            rs.close();
            sentencia.close();
            //stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return res;
        
    }

    
    private Ubigeo getUbigeo(int idBolsaPalabra)
    {
        Ubigeo res = null;
        try {
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs;

            String query = "SELECT codEntidad, codParroquia, codMunicipio FROM ubigeo WHERE idBolsaPalabra=" +idBolsaPalabra;  

            rs = stmt.executeQuery(query);
            
            if (rs.next())
            {
                res = new Ubigeo();
                res.setCodEntidad(rs.getInt("codEntidad"));
                res.setCodMunicipio(rs.getInt("codParroquia"));
                res.setCodParroquia(rs.getInt("codMunicipio"));
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return res;
    }
    
    public String sugerenciaCorreccion(String palabraDesconocida)
    {
        String sugerencia = null;
        
        try {
            ConexionBD bd= ConexionBD.getConexionBD();
            Connection conn = bd.getOrigenDatos().getConnection();
            ResultSet rs;
            PreparedStatement sentencia = null; 
            
            //Statement stmt = conn.createStatement();
/*       
            String query = "SELECT palabra from bolsaPalabras where levenshtein(\"" + palabraDesconocida + "\", bolsaPalabras.palabra) = 1 ORDER BY frecuencia DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(query);
 //*/           
            String query = "SELECT palabra from bolsaPalabras where levenshtein(?, bolsaPalabras.palabra) = 1 ORDER BY frecuencia DESC LIMIT 1";
            sentencia = conn.prepareStatement(query);
            sentencia.setString(1, palabraDesconocida);
            
            rs = sentencia.executeQuery();

            if (rs.next())
            {
                sugerencia = rs.getString("palabra");
            }
            
            rs.close();
            sentencia.close();
            //stmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Lenguaje.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sugerencia;
    }

    
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

. INT. AL EST. DEL TUR. Y VIAJES
2. CLASIFICACIÓN DE LOS ATRACTIVOS TURÍSTICOS POR CATEGORIAS.
3. Sitios naturales: Montañas, planicies, valles, quebradas, cañones, lagos, lagunas, pantanos, ríos, caídas de agua, manantiales, costas, grutas, cavernas, áreas protegidas, entre otros.
4. Manifestaciones culturales: Museos, arquitectura y espacios urbanos, lugares históricos, restos y lugares arqueológicos y pueblos
5. Folklore: Manifestaciones religiosas y populares, ferias, mercados, música, danza, artes, artesanías, gastronomía
6. Etnológico: Costa, sierra y selva.
7. SERVICIOS • Alojamiento • Hoteles • Apart-Hoteles • Hostales • Albergues • Casas de Hospedajes • Alimentación: • Restaurantes • Bares • Cafeterías • Snacks • Fuentes de soda • kioscos de venta de comida y/o bebidas. • Venta de comida rápida.
8. TIPO DE MEDIO DE TRANSPORTE • TERRESTRE: • A Pie • Automóvil Particular • Automóvil Particular y A Pie • Automóvil Particular y en Bestia • Bestia • Bestia y Bus Público • Bus de Transporte Terrestre • Bus Público • Bus Turístico • Bus Turístico y en Bestia • Camioneta de Doble Tracción 

 Clasificación de Atractivos Turísticos
Los Atractivos Turísticos son aquellas características y puntos de interés para los turístas que llaman su atención y atrae a los viajeros. Es una parte del Producto Turístico, ya que junto con la Planta Turística y la infraestructura, es lo que el destino turístico ofrece a quien lo visita.

Ana García Silberman, los clasifica en Naturales y Culturales.

Naturales Son aquellos creados por la naturaleza sin que el hombre haya intervenido en ello.

Geomorfológicos: Formados por la naturaleza durante la evolución del planeta.

1. Litorales

    Rocas e islas
    Playas
    Acantilados
    Desembocaduras de ríos

2. Lagunas y depósitos de agua

3. Corrientes de agua

    Superficiales (cañones y cascadas)
    Subterráneas (manantiales, grutas y cenotes)
    4. Vulcanismo
        Cráteres
        Aguas termales y minerales
        Geisers
    5. Relieves
        Montañas
        Barrancas y cañones
        Planicies
        Dunas
    Biogeográficos.: Son aquellos con manifestaciones de vida animal o vegetal.

    1. Agrupaciones vegetales
        Selvas
        Bosques
    2. Agrupaciones animales
        Fauna Silvestre
        Zonas de caza
    CULTURALES. Elementos en que intervienen o ha intervenido la acción humana en el devenir histórico, o se han establecido por razones culturales o comerciales.

    Históricos Son obras que han dejado las civilizaciones y que son estudiadas por diferentes ramas de la ciencia.

    1. Atractivos históricos
        Zonas arqueológicas
        Arquitectura antigua
        Lugares históricos
        Poblados típicos
        Folklore
        Fistas tradicionales
    Contemporáneos

    1. Atractivos contemporáneos (no comerciales) Son aquellas manifestaciones de un país o región que conservan y fometan el patrimonio cultural. 
        Centro de convenciones
        Instituciones de enseñanza
        Bibliotecs
        Museos y pinturas murales
        Obras monumentales
        Invernaderos
        Zoológicos
    2. Atractivos contemporáneos (comerciales) Son aquellas manifestaciones por agrupaciones públicas o privadas que, por lo general, tienen carácter temporal y se crean para atraer visitantes ofreciendoles distracción, esparcimiento, mercancías, salud, etc.
        Parque de diversiones
        Balnearios
        Espectáculos culturales y deportivos
        Campos deportivos
        Exposiciones nacionales e internacionales
        Mercados de artesanías
        Comercios
        Centros de salud
        Ferias y carnavales
        Celebraciones religiosas
        Casinos
        Concursos y competencias


 */