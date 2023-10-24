package org.fcyt.modelo.tabla;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.fcyt.modelo.Cliente;

/**
 *
 * @author cmendieta
 * AbstractTableModel es una clase en Java que forma parte del paquete javax.swing.table y se utiliza para proporcionar una implementación abstracta de la interfaz TableModel. 
 * La interfaz TableModel se utiliza para definir el modelo de datos para tablas en aplicaciones Java Swing, como las que se encuentran en aplicaciones de escritorio.
 */
public class ClienteTablaModel extends AbstractTableModel{
    List<Cliente> lista;
    private String[] columnNames = new String []{"ID","RUC","NOMBRE","TELEFONO"}; //Definimos los nombres de la columna
    
    Class []  columnClass = new Class[]{Integer.class,String.class,String.class,String.class};

    public String getColumnName(int i) {
        return columnNames[i];
    }

//    public void setColumnNames(String[] columnNames) {
//        this.columnNames = columnNames;
//    }

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
        Cliente  empresa = lista.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return empresa.getId();
            case 1:
                return empresa.getRuc();
            case 2:
                return empresa.getNombre();
            case 3:
                return empresa.getTelefono();
        }
        return "";
    }

    public List<Cliente> getLista() {
        return lista;
    }

    public void setLista(List<Cliente> lista) {
        this.lista = lista;
    }
    
    public Cliente getEntityByRow(int index){
        return lista.get(index);
    }
    
    
}
