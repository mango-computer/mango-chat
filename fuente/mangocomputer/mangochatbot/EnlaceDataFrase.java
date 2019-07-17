/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

/**
 *
 * @author Jose Andres Morales Linares
 */


/*


Palabras claves a buscar
Ejemplo: Restauran

Zona:           Restaurant (Desde grandes restaurantes hasta el kiosco de empanadas)
Seccion:        Sopa (Sopas, Licores, Pastas, Empanadas, Arepas, Tortas, Heledos, etc.)
Producto:       pollo, res, pescado, marisco, cangrejo, etc.

*/
public class EnlaceDataFrase {
    
    
    public static enum TIPO {ACCION, LUGAR, SERVICIO, PRODUCTO, PROPIEDAD};
    
    private TIPO tipo;
    private int cuenta;
    private String palabraOriginal;
    private String palabraOriginalCorregida;
    
    private String contexto;
    private String titulos[] = new String[128];
    private String propiedades[] = new String[128];
    
    private int indexTitulo=0;
    private int indexPropiedad=0;
    

    public int getCatidadTitulos() {
        return indexTitulo;
    }
    
    public int getCatidadPropiedades() {
        return indexPropiedad;
    }

    public String getPalabraOriginalCorregida() {
        return palabraOriginalCorregida;
    }

    public TIPO getTipo() {
        return tipo;
    }

    public void setTipo(TIPO tipo) {
        this.tipo = tipo;
    }
    
    public void setPalabraOriginalCorregida(String palabraOriginalCorregida) {
        this.palabraOriginalCorregida = palabraOriginalCorregida;
    }

    
    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public String[] getTitulos() {
        return titulos;
    }

    public String[] getPropiedades() {
        return propiedades;
    }

    public String getPrimerTitulo() {
        String res="";
        if (indexTitulo>0)
            res = titulos[0];
        
        return res;
    }

    public String getPrimeraPropiedad() {
        String res="";
        if (indexPropiedad>0)
            res = propiedades[0];
        
        return res;
    }
    
    public void addTitulo(String titulo) {
        boolean agregar=true;
        if (indexTitulo>0)
        {
            for (String t: titulos)
            {
                if (t==null) break;
                if (t.equals(titulo))
                {
                    agregar = false;
                    break;
                }
            }
        }
        
        if (agregar)
        {
            titulos[indexTitulo++] = titulo;
        }
    }

    public void addPropiedad(String propiedad) {
        
        boolean agregar=true;
        if (indexPropiedad>0)
        {
            for (String t: propiedades)
            {
                if (t==null)break;
                if (t.equals(propiedad))
                {
                    agregar = false;
                    break;
                }
            }
        }

        if (agregar)
        {
            propiedades[indexPropiedad++] = propiedad;
        }
    }


    public EnlaceDataFrase(String palabraOriginal, TIPO tipo, String contexto, String titulo, String propiedad) {
        this.cuenta = 0;
        this.palabraOriginal = palabraOriginal;
        this.contexto = contexto;
        this.titulos[indexTitulo++] = titulo;
        this.tipo = tipo;
        this.propiedades[indexPropiedad++] = propiedad;
    }

    public EnlaceDataFrase(int cuenta, String palabraOriginal, TIPO tipo, String contexto, String titulo) {
        this.cuenta = cuenta;
        this.palabraOriginal = palabraOriginal;
        this.contexto = contexto;
        this.titulos[indexTitulo++] = titulo;
        this.tipo = tipo;
    }

    public EnlaceDataFrase(String palabraOriginal, TIPO tipo, String contexto, String titulo) {
        this.cuenta = 0;
        this.palabraOriginal = palabraOriginal;
        this.contexto = contexto;
        this.titulos[indexTitulo++] = titulo;
        this.tipo = tipo;
    }

    public EnlaceDataFrase(String palabraOriginal, TIPO tipo, String contexto) {
        this.cuenta = 0;
        this.palabraOriginal = palabraOriginal;
        this.contexto = contexto;
        this.tipo = tipo;
    }

    public EnlaceDataFrase(EnlaceDataFrase data) {
        this.cuenta = 0;
        this.palabraOriginal = data.getPalabraOriginal();
        this.contexto = data.getContexto();
        this.tipo = data.getTipo();
    }

    public boolean isTieneTitulo()
    {
        return (indexTitulo>0);
    }
    
    public boolean isTienePropiedad()
    {
        return (indexPropiedad>0);
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public String getPalabraOriginal() {
        return palabraOriginal;
    }

    public void setPalabraOriginal(String palabraOriginal) {
        this.palabraOriginal = palabraOriginal;
    }
    
    public void contar()
    {
        this.cuenta++;
    }

}
