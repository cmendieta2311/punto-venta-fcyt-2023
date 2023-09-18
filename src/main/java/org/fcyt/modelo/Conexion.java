package org.fcyt.modelo;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class Conexion {
    String url = "jdbc:postgresql://localhost:5432/punto_venta";
    String usuario = "postgres";
    String password = "12345";

    public Conexion() {
    }

    public Connection conectarBD(){
        Connection conexion = null;
        try {
            //realizar la conexion a BD
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexion exitosa");
        } catch (Exception e) {
            System.out.println("Error al conectar a BD: " + e.getMessage());
        }
        return conexion;
    }
}
