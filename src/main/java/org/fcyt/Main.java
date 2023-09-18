package org.fcyt;

import org.fcyt.modelo.Conexion;
import org.fcyt.modelo.Empresa;
import org.fcyt.modelo.dao.EmpresaDaoImpl;

import java.sql.Connection;
import java.util.List;
import org.fcyt.controlador.EmpresaController;
import org.fcyt.vista.GUIEmpresa;

public class Main {
    public static void main(String[] args) {
//        Conexion conectar = new Conexion();
//        Connection conec = conectar.conectarBD();

        EmpresaDaoImpl dao = new EmpresaDaoImpl();
        GUIEmpresa gui = new GUIEmpresa(null, true);
        EmpresaController ctrl = new EmpresaController(gui, dao);
        ctrl.mostrarVentana();
       
    }
}