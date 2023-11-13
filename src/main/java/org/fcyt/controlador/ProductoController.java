/*
* UNIDAD III - Codificacion de programas de Interfaz
*/
package org.fcyt.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.fcyt.modelo.Iva;
import org.fcyt.modelo.Marca;
import org.fcyt.modelo.Producto;
import org.fcyt.modelo.dao.IvaDaoImpl;
import org.fcyt.modelo.dao.MarcaDaoImpl;
import org.fcyt.modelo.dao.ProductoDaoImpl;
import org.fcyt.modelo.tabla.ProductoTablaModel;
import org.fcyt.vista.GUIProducto;

/**
 *
 * @author cmendieta
 */
public class ProductoController implements ActionListener {

    private GUIProducto gui;
    private ProductoDaoImpl abm;
    private char operacion;
    
    private MarcaDaoImpl abmMarca;
    private IvaDaoImpl abmIva;

    ProductoTablaModel modelo = new ProductoTablaModel();

    public ProductoController(GUIProducto gui, ProductoDaoImpl abm) {
        this.gui = gui;
        this.abm = abm;
        
        abmMarca = new MarcaDaoImpl();
        abmIva = new IvaDaoImpl();

        this.gui.btnGuardar.addActionListener((ActionListener) this);
        this.gui.btn_Nuevo.addActionListener(this);
        this.gui.btnCancelar.addActionListener(this);
        this.gui.btn_Editar.addActionListener(this);
        this.gui.btn_Eliminar.addActionListener(this);
        this.gui.txt_buscar.addActionListener(this);

        this.listar("");
        
        llenarComboMarca(gui.cboMarca);
        llenarComboIva(gui.cboIva);

        gui.tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JTable tabla = (JTable) evt.getSource();
                int row = tabla.rowAtPoint(evt.getPoint());
                ProductoTablaModel model = (ProductoTablaModel) tabla.getModel();

                setProductoForm(model.getEntityByRow(row));

            }
        });

    }
//ActionPerformed nos referimos al evento que se produce al hacer click en un componente o al pulsar Enter (teniendo el foco en el componente)

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Hola Class");
        if(e.getSource() == gui.txt_buscar){
            this.listar(gui.txt_buscar.getText());
        }
        if (e.getSource() == gui.btn_Nuevo) {
            operacion = 'N';
            System.out.println("Soy el boton nuevo");
            habilitarCampos(true);
            limpiar();
        }

        if (e.getSource() == gui.btn_Editar) {
            operacion = 'E';
            System.out.println("Soy el boton EDITAR");
            habilitarCampos(true);
        }
        
        if (e.getSource() == gui.btn_Eliminar) {
          int fila =  gui.tabla.getSelectedRow();
          if(fila >= 0){
              int ok =  JOptionPane.showConfirmDialog(gui, "Realmente desea eliminar el registro?","Confirmar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
              if(ok == 0){
                abm.eliminar(modelo.getEntityByRow(fila));
              listar("");  
              }
             
          }else{
              JOptionPane.showMessageDialog(gui, "Debe seleccionar una fila");
          }
        } 

        if (e.getSource() == gui.btnGuardar) {
            boolean v_control = validarDatos();
            if(v_control == true){
                JOptionPane.showMessageDialog(gui, "Favor completar los datos");
                return;
            }
            switch (operacion) {
                case 'N':
                    abm.insertar(getProductoForm());
                    break;
                case 'E':
                    abm.actualizar(getProductoForm());
                    break;
            }
            
            limpiar();
            listar("");
        }

        if (e.getSource() == gui.btnCancelar) {
            habilitarCampos(false);
            limpiar();
        }

    }

    // Funcion encargado de mostrar la ventana
    public void mostrarVentana() {
        gui.setLocationRelativeTo(gui);
        gui.setTitle("Producto");
        gui.setVisible(true);
    }

    //Funcion encargado de recuperar los valos de los textfield en un objeto tipo producto
    private Producto getProductoForm() {
        Producto producto = new Producto();
        producto.setId(Integer.valueOf(gui.txt_id.getText()));
        producto.setDescripcion(gui.txt_descripcion.getText());

        return producto;
    }

    private void setProductoForm(Producto producto) {
        gui.txt_id.setText(String.valueOf(producto.getId()));
        gui.txt_descripcion.setText(producto.getDescripcion());
        gui.txt_precio_compra.setText(String.valueOf(producto.getPrecioCompra()));
        gui.txt_precio_vta.setText(String.valueOf(producto.getPrecioVenta()));
        gui.cboMarca.setSelectedItem(producto.getMarca());
        gui.cboIva.setSelectedItem(producto.getIva());
    }

    private void limpiar() {
        gui.txt_id.setText("0");
        gui.txt_descripcion.setText("");
    }

    public void listar(String valorBuscado) {
        List<Producto> lista = this.abm.listar(valorBuscado);
        modelo.setLista(lista);
        llenarTabla(gui.tabla);

    }

    public void llenarTabla(JTable tabla) {
        gui.tabla.setModel(modelo);
        gui.tabla.updateUI();
    }

    private void habilitarCampos(Boolean h) {
        gui.txt_descripcion.setEnabled(h);
    }
    
    private boolean validarDatos(){
        boolean vacio = false;
        
        if(gui.txt_descripcion.getText().isEmpty()){
            vacio = true;
        }
        return vacio;
    }
    
    private void llenarComboMarca(JComboBox cbo){
        DefaultComboBoxModel<Marca> model = new DefaultComboBoxModel();
        List<Marca> lista = abmMarca.listar("");
        for(int i=0; i < lista.size(); i++){
            Marca producto =  lista.get(i);
            model.addElement(producto);
        }
        cbo.setModel(model);
        
    }
    
      private void llenarComboIva(JComboBox cbo){
        DefaultComboBoxModel<Iva> model = new DefaultComboBoxModel();
        List<Iva> lista = abmIva.listar("");
        for(int i=0; i < lista.size(); i++){
            Iva iva =  lista.get(i);
            model.addElement(iva);
        }
        cbo.setModel(model);
        
    }

}
