/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.fcyt.modelo.tabla;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.fcyt.modelo.Empresa;

/**
 *
 * @author cmendieta
 */
public class EmpresaTablaModel extends AbstractTableModel{
    List<Empresa> lista;
    private String[] columnNames = new String []{"ID","NOMBRE","TELEFONO"}; //Definimos los nombres de la columna
    
    Class []  columnClass = new Class[]{Integer.class,String.class,String.class};

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
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
        Empresa  empresa = lista.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return empresa.getId();
            case 1:
                return empresa.getNombre();
            case 2:
                return empresa.getTelefono();
        }
        return "";
    }

    public List<Empresa> getLista() {
        return lista;
    }

    public void setLista(List<Empresa> lista) {
        this.lista = lista;
    }
    
    public Empresa getEntityByRow(int index){
        return lista.get(index);
    }
    
    
}
