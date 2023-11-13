package org.fcyt.modelo.dao;

import org.fcyt.modelo.Conexion;
import org.fcyt.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDaoImpl implements dao<Usuario> {

    Connection conec;
    PreparedStatement sentencia;

    public UsuarioDaoImpl() {
        Conexion conectar = new Conexion();
        conec = conectar.conectarBD();
    }

    @Override
    public void insertar(Usuario usuario) {
        String cSQL = "INSERT INTO usuario (usuario, clave, idempresa) VALUES (?,md5(?),?)";
        try {
            sentencia = conec.prepareStatement(cSQL);
            sentencia.setString(1, usuario.getUsuario());
            sentencia.setString(2, usuario.getClave());
            sentencia.setInt(3, usuario.getEmpresaId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        String cSql = "update usuario set usuario=?,clave=? where id=?";
        try {
            sentencia = conec.prepareStatement(cSql);
            sentencia.setString(1, usuario.getUsuario());
            sentencia.setString(2, usuario.getClave());
            sentencia.setInt(3, usuario.getId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void eliminar(Usuario usuario) {
        String cSql = "delete from usuario where id=?";
        try {
            sentencia = conec.prepareStatement(cSql);
            sentencia.setInt(1, usuario.getId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Usuario> listar(String textoBuscado) {
        ArrayList<Usuario> listaUsuario = new ArrayList<>();
        try {
            //Preparar sentencia SQL
            Usuario user = Usuario.getSessionUser();
            String sql = "SELECT * FROM usuario where idempresa=?";
            sentencia = conec.prepareStatement(sql);
             sentencia.setInt(1, user.getEmpresaId());
            //Ejecutar sentencia SQL
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setEmpresaId(rs.getInt("idempresa"));
                listaUsuario.add(usuario);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Usuario: " + e.getMessage());
        }
        return listaUsuario;
    }

    public Boolean login(Usuario usuario) {
        Boolean v_control = false;
        //Preparar sentencia SQL
        String sql = "select * from usuario where usuario=? and clave=md5(?);";
        try {
            sentencia = conec.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE);
            sentencia.setString(1, usuario.getUsuario());
            sentencia.setString(2, usuario.getClave());
            //Ejecutar sentencia SQL
            ResultSet rs = sentencia.executeQuery();
            rs.first();
            if(rs.getRow()>0){
                System.out.print("usuario encontrado");
                v_control = true;
                Usuario user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setUsuario(rs.getString("usuario"));
                user.setEmpresaId(rs.getInt("idempresa"));
                user.setCambiarClave(rs.getBoolean("cambiar_clave"));
                Usuario.setSessionUser(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v_control;
    }
}
