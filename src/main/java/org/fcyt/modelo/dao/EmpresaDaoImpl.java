package org.fcyt.modelo.dao;

import org.fcyt.modelo.Conexion;
import org.fcyt.modelo.Empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDaoImpl implements dao<Empresa>{
    Connection conec;
    PreparedStatement sentencia;

    public EmpresaDaoImpl(){
        Conexion conectar = new Conexion();
        conec = conectar.conectarBD();
    }

    @Override
    public void insertar(Empresa empresa) {
        String cSQL = "INSERT INTO empresa (ruc, nombre, direccion, telefono) VALUES (?,?,?,?)";
        try {
            sentencia = conec.prepareStatement(cSQL);
            sentencia.setString(1, empresa.getRuc());
            sentencia.setString(2, empresa.getNombre());
            sentencia.setString(3, empresa.getDireccion());
            sentencia.setString(4, empresa.getTelefono());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizar(Empresa empresa) {
        String cSql= "update empresa set ruc=?,nombre=?,direccion=?,telefono=? where id=?";
        try {
            sentencia = conec.prepareStatement(cSql);
            sentencia.setString(1, empresa.getRuc());
            sentencia.setString(2, empresa.getNombre());
            sentencia.setString(3, empresa.getDireccion());
            sentencia.setString(4, empresa.getTelefono());
            sentencia.setInt(5, empresa.getId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void eliminar(Empresa empresa) {
        String cSql = "delete from empresa where id=?";
        try {
            sentencia = conec.prepareStatement(cSql);
            sentencia.setInt(1, empresa.getId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Empresa> listar(String textoBuscado) {
        ArrayList<Empresa> listaEmpresa = new ArrayList<>();
        try {
            //Preparar sentencia SQL
            String sql = "SELECT * FROM empresa";
            sentencia = conec.prepareStatement(sql);
            //Ejecutar sentencia SQL
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()){
                Empresa empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setRuc(rs.getString("ruc"));
                empresa.setNombre(rs.getString("nombre"));
                empresa.setDireccion(rs.getString("direccion"));
                empresa.setTelefono(rs.getString("telefono"));
                empresa.setWeb("www.empresa"+rs.getInt("id")+".com");
                listaEmpresa.add(empresa);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Empresa: " + e.getMessage());
        }
        return listaEmpresa;
    }
}
