/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fcyt.modelo;

/**
 *
 * @author cmendieta
 */
public class Usuario {
    Integer id;
    String usuario;
    String clave;
    Integer empresaId;
    Boolean cambiarClave;
    private static Usuario sessionUser;

    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public static Usuario getSessionUser() {
        return sessionUser;
    }

    public static void setSessionUser(Usuario sessionUser) {
        Usuario.sessionUser = sessionUser;
    }

    public Boolean getCambiarClave() {
        return cambiarClave;
    }

    public void setCambiarClave(Boolean cambiarClave) {
        this.cambiarClave = cambiarClave;
    }
    
    
     
}
