package org.fcyt.modelo.dao;

import org.fcyt.modelo.Conexion;
import org.fcyt.modelo.Iva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IvaDaoImpl implements dao<Iva>{
    Connection conec;
    PreparedStatement sentencia;

    public IvaDaoImpl(){
        Conexion conectar = new Conexion();
        conec = conectar.conectarBD();
    }

    @Override
    public void insertar(Iva obj) {
        String cSQL = "INSERT INTO iva (descripcion) VALUES (?)";
        try {
            sentencia = conec.prepareStatement(cSQL);
            sentencia.setString(1, obj.getDescripcion());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizar(Iva obj) {
        String cSql= "update iva set descripcion=? where id=?";
        try {
            sentencia = conec.prepareStatement(cSql);
            sentencia.setString(1, obj.getDescripcion());
            sentencia.setInt(2, obj.getId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void eliminar(Iva obj) {
        String cSql = "delete from iva where id=?";
        try {
            sentencia = conec.prepareStatement(cSql);
            sentencia.setInt(1, obj.getId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Iva> listar(String textoBuscado) {
        ArrayList<Iva> listaIva = new ArrayList<>();
        try {
            //Preparar sentencia SQL
            String sql = "SELECT * FROM iva";
            sentencia = conec.prepareStatement(sql);
            //Ejecutar sentencia SQL
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()){
                Iva iva = new Iva();
                iva.setId(rs.getInt("id"));
                iva.setDescripcion(rs.getString("descripcion"));
           
                listaIva.add(iva);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Iva: " + e.getMessage());
        }
        return listaIva;
    }
}
