package org.fcyt.modelo.tabla;

import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.fcyt.modelo.Producto;

/**
 *
 * @author cmendieta AbstractTableModel es una clase en Java que forma parte del
 * paquete javax.swing.table y se utiliza para proporcionar una implementación
 * abstracta de la interfaz TableModel. La interfaz TableModel se utiliza para
 * definir el modelo de datos para tablas en aplicaciones Java Swing, como las
 * que se encuentran en aplicaciones de escritorio.
 */
public class ProductoTablaModel extends AbstractTableModel {
    
    //DecimalFormat formatea = new DecimalFormat("###,###.##");

    List<Producto> lista;
    private String[] columnNames = new String[]{"ID", "DESCRIPCION", "MARCA", "IVA", "COMPRA", "VENTA"}; //Definimos los nombres de la columna

    Class[] columnClass = new Class[]{Integer.class, String.class, String.class, String.class, String.class, Double.class};

    public String getColumnName(int i) {
        return columnNames[i];
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto item = lista.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return item.getId();
            case 1:
                return item.getDescripcion();
            case 2:
                return item.getMarca().getDescripcion();
            case 3:
                return item.getIva().getDescripcion();
            case 4:
                return item.getPrecioCompra();
            case 5:
                return item.getPrecioVenta();
        }
        return "";
    }

    public List<Producto> getLista() {
        return lista;
    }

    public void setLista(List<Producto> lista) {
        this.lista = lista;
    }

    public Producto getEntityByRow(int index) {
        return lista.get(index);
    }

}
