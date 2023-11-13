package org.fcyt.modelo.dao;

import org.fcyt.modelo.Conexion;
import org.fcyt.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.fcyt.modelo.Usuario;

public class ClienteDaoImpl implements dao<Cliente>{
    Connection conec;
    PreparedStatement sentencia;

    public ClienteDaoImpl(){
        Conexion conectar = new Conexion();
        conec = conectar.conectarBD();
    }

    @Override
    public void insertar(Cliente cliente) {
        String cSQL = "INSERT INTO cliente (ruc, nombre, direccion, telefono, idempresa) VALUES (?,?,?,?,?)";
        try {
            sentencia = conec.prepareStatement(cSQL);
            sentencia.setString(1, cliente.getRuc());
            sentencia.setString(2, cliente.getNombre());
            sentencia.setString(3, cliente.getDireccion());
            sentencia.setString(4, cliente.getTelefono());
            sentencia.setInt(5, cliente.getIdEmpresa());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizar(Cliente cliente) {
        String cSql= "update cliente set ruc=?,nombre=?,direccion=?,telefono=? where id=?";
        try {
            sentencia = conec.prepareStatement(cSql);
            sentencia.setString(1, cliente.getRuc());
            sentencia.setString(2, cliente.getNombre());
            sentencia.setString(3, cliente.getDireccion());
            sentencia.setString(4, cliente.getTelefono());
            sentencia.setInt(5, cliente.getId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void eliminar(Cliente cliente) {
        String cSql = "delete from cliente where id=?";
        try {
            sentencia = conec.prepareStatement(cSql);
            sentencia.setInt(1, cliente.getId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Cliente> listar(String textoBuscado) {
        ArrayList<Cliente> listaCliente = new ArrayList<>();
        try {
            //Preparar sentencia SQL
            Usuario user = Usuario.getSessionUser();
            String sql = "SELECT * FROM cliente where idempresa=?";
            sentencia = conec.prepareStatement(sql);
            sentencia.setInt(1, user.getEmpresaId());
            //Ejecutar sentencia SQL
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()){
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setRuc(rs.getString("ruc"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setIdEmpresa(rs.getInt("idempresa"));
                listaCliente.add(cliente);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Cliente: " + e.getMessage());
        }
        return listaCliente;
    }
}
