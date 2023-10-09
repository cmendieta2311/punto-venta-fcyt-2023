package org.fcyt.modelo.dao;

import org.fcyt.modelo.Conexion;
import org.fcyt.modelo.Marca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarcaDaoImpl implements dao<Marca>{
    Connection conec;
    PreparedStatement sentencia;

    public MarcaDaoImpl(){
        Conexion conectar = new Conexion();
        conec = conectar.conectarBD();
    }

    @Override
    public void insertar(Marca obj) {
        String cSQL = "INSERT INTO marca (descripcion) VALUES (?)";
        try {
            sentencia = conec.prepareStatement(cSQL);
            sentencia.setString(1, obj.getDescripcion());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizar(Marca obj) {
        String cSql= "update marca set descripcion=? where id=?";
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
    public void eliminar(Marca obj) {
        String cSql = "delete from marca where id=?";
        try {
            sentencia = conec.prepareStatement(cSql);
            sentencia.setInt(1, obj.getId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Marca> listar() {
        ArrayList<Marca> listaMarca = new ArrayList<>();
        try {
            //Preparar sentencia SQL
            String sql = "SELECT * FROM marca";
            sentencia = conec.prepareStatement(sql);
            //Ejecutar sentencia SQL
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()){
                Marca marca = new Marca();
                marca.setId(rs.getInt("id"));
                marca.setDescripcion(rs.getString("descripcion"));
           
                listaMarca.add(marca);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Marca: " + e.getMessage());
        }
        return listaMarca;
    }
}
