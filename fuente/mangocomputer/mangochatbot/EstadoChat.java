/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

/**
 *
 * @author mm
 */
public enum EstadoChat 
{
    SIN_ESTADO, 
    CON_ESTADO, 
    SI_NO_CORREGIR, 
    SI_NO_BUSCAR_MAS, 
    SI_NO_CONOCIDO, 
    CALIFICAR,
    COMENTAR_LUGAR,
    ESPERANDO_NOMBRE_USER, 
    ESPERANDO_PWD_USER, 
    SI_NO_CREAR_USUARIO,
    SI_NO_RECUPERAR_CUENTA,
    CREAR_CUENTA_NOMBRE,
    CREAR_CUENTA_CLAVE,
    CREAR_CUENTA_NOMBRE_COMPLETO,
    CREAR_CUENTA_CORREO,
    CREAR_CUENTA_PAIS,
    CAMBIAR_RANGO_BUSQUEDA,
    CAMBIAR_RANGO_BUSQUEDA_SIN_BUSCAR,
    CAMBIO_LIMITE_BUSQUEDA,
    SI_NO_MOSTRAR_MAS_RESULTADOS
}
