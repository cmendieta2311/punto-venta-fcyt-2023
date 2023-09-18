/*
* UNIDAD III - Codificacion de programas de Interfaz
*/
package org.fcyt.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.fcyt.modelo.Empresa;
import org.fcyt.modelo.dao.EmpresaDaoImpl;
import org.fcyt.modelo.tabla.EmpresaTablaModel;
import org.fcyt.vista.GUIEmpresa;

/**
 *
 * @author cmendieta
 */
public class EmpresaController implements ActionListener {

    private GUIEmpresa gui;
    private EmpresaDaoImpl abm;
    private char operacion;

    EmpresaTablaModel modelo = new EmpresaTablaModel();

    public EmpresaController(GUIEmpresa gui, EmpresaDaoImpl abm) {
        this.gui = gui;
        this.abm = abm;

        this.gui.btnGuardar.addActionListener((ActionListener) this);
        this.gui.btn_Nuevo.addActionListener(this);
        this.gui.btnCancelar.addActionListener(this);
        this.gui.btn_Editar.addActionListener(this);
        this.gui.btn_Eliminar.addActionListener(this);

        this.listar();

        gui.tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JTable tabla = (JTable) evt.getSource();
                int row = tabla.rowAtPoint(evt.getPoint());
                EmpresaTablaModel model = (EmpresaTablaModel) tabla.getModel();

                setEmpresaForm(model.getEntityByRow(row));

            }
        });

    }
//ActionPerformed nos referimos al evento que se produce al hacer click en un componente o al pulsar Enter (teniendo el foco en el componente)

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Hola Class");
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
              listar();  
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
                    abm.insertar(getEmpresaForm());
                    break;
                case 'E':
                    abm.actualizar(getEmpresaForm());
                    break;
            }
            
            limpiar();
            listar();
        }

        if (e.getSource() == gui.btnCancelar) {
            habilitarCampos(false);
            limpiar();
        }

    }

    // Funcion encargado de mostrar la ventana
    public void mostrarVentana() {
        gui.setVisible(true);
    }

    //Funcion encargado de recuperar los valos de los textfield en un objeto tipo empresa
    private Empresa getEmpresaForm() {
        Empresa empresa = new Empresa();
        empresa.setId(Integer.valueOf(gui.txt_id.getText()));
        empresa.setRuc(gui.txt_ruc.getText());
        empresa.setNombre(gui.txt_nombre.getText());
        empresa.setDireccion(gui.txt_direccion.getText());
        empresa.setTelefono(gui.txt_telefono.getText());

        return empresa;
    }

    private void setEmpresaForm(Empresa empresa) {
        gui.txt_id.setText(empresa.getId().toString());
        gui.txt_ruc.setText(empresa.getRuc());
        gui.txt_nombre.setText(empresa.getNombre());
        gui.txt_direccion.setText(empresa.getDireccion());
        gui.txt_telefono.setText(empresa.getTelefono());;
    }

    private void limpiar() {
        gui.txt_id.setText("0");
        gui.txt_ruc.setText("");
        gui.txt_nombre.setText("");
        gui.txt_direccion.setText("");
        gui.txt_telefono.setText("");
    }

    public void listar() {
        List<Empresa> lista = this.abm.listar();
        modelo.setLista(lista);
        llenarTabla(gui.tabla);

    }

    public void llenarTabla(JTable tabla) {
        gui.tabla.setModel(modelo);
        gui.tabla.updateUI();
    }

    private void habilitarCampos(Boolean h) {
        gui.txt_ruc.setEnabled(h);
        gui.txt_nombre.setEnabled(h);
        gui.txt_direccion.setEnabled(h);
        gui.txt_telefono.setEnabled(h);
    }
    
    private boolean validarDatos(){
        boolean vacio = false;
        
        if(gui.txt_ruc.getText().isEmpty()){
            vacio = true;
        }
        return vacio;
    }

}
