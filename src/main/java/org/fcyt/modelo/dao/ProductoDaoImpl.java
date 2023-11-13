package org.fcyt.modelo.dao;

import org.fcyt.modelo.Conexion;
import org.fcyt.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.fcyt.modelo.Iva;
import org.fcyt.modelo.Marca;

public class ProductoDaoImpl implements dao<Producto> {

    Connection conec;
    PreparedStatement sentencia;

    public ProductoDaoImpl() {
        Conexion conectar = new Conexion();
        conec = conectar.conectarBD();
    }

    @Override
    public void insertar(Producto obj) {
        String cSQL = "insert into producto(descripcion,precio_compra,precio_venta,idproducto,idiva,idempresa) values(?,?,?,?,?,?)";
        try {
            sentencia = conec.prepareStatement(cSQL);
            sentencia.setString(1, obj.getDescripcion());
            sentencia.setInt(2, obj.getPrecioCompra());
            sentencia.setInt(3, obj.getPrecioVenta());
            sentencia.setInt(4, obj.getMarca().getId());
            sentencia.setInt(5, obj.getIva().getId());
            sentencia.setInt(6, obj.getIdEmpresa());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizar(Producto obj) {
        String cSql = "update producto set descripcion=?,precio_compra=?,precio_venta=?,idproducto=?,idiva=? where id=?";
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
    public void eliminar(Producto obj) {
        String cSql = "delete from producto where id=?";
        try {
            sentencia = conec.prepareStatement(cSql);
            sentencia.setInt(1, obj.getId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Producto> listar(String textBuscado) {
        System.out.println(textBuscado);
        ArrayList<Producto> listaProducto = new ArrayList<>();
        try {
            //Preparar sentencia SQL
            String sql = "select p.*,m.descripcion as marca,i.descripcion as iva\n"
                    + "from producto p\n"
                    + "inner join marca m on p.idmarca = m.id\n"
                    + "inner join iva i on p.idiva = i.id\n"
                    + "where p.descripcion ilike '%"+textBuscado+"%'";

        
            sentencia = conec.prepareStatement(sql);
            //sentencia.setString(1, textBuscado);
            //Ejecutar sentencia SQL
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()) {
                //Incializando las variables 
                Marca marca = new Marca();
                Iva iva = new Iva();
                Producto producto = new Producto();

                marca.setId(rs.getInt("idmarca"));
                marca.setDescripcion(rs.getString("marca"));

                iva.setId(rs.getInt("idiva"));
                iva.setDescripcion(rs.getString("iva"));

                producto.setId(rs.getInt("id"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecioCompra(rs.getInt("precio_compra"));
                producto.setPrecioVenta(rs.getInt("precio_venta"));
                producto.setMarca(marca);
                producto.setIva(iva);

                listaProducto.add(producto);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Producto: " + e.getMessage());
        }
        return listaProducto;
    }
}
