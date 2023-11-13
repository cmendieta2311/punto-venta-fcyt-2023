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
import org.fcyt.modelo.Cliente;
import org.fcyt.modelo.Usuario;
import org.fcyt.modelo.dao.ClienteDaoImpl;
import org.fcyt.modelo.tabla.ClienteTablaModel;
import org.fcyt.vista.GUICliente;

/**
 *
 * @author cmendieta
 */
public class ClienteController implements ActionListener {

    private GUICliente gui;
    private ClienteDaoImpl abm;
    private char operacion;

    ClienteTablaModel modelo = new ClienteTablaModel();

    public ClienteController(GUICliente gui, ClienteDaoImpl abm) {
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
                ClienteTablaModel model = (ClienteTablaModel) tabla.getModel();

                setClienteForm(model.getEntityByRow(row));

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
            gui.txt_ruc.requestFocus();
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
                    abm.insertar(getClienteForm());
                    break;
                case 'E':
                    abm.actualizar(getClienteForm());
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
        gui.setLocationRelativeTo(gui);
        gui.setVisible(true);
    }

    //Funcion encargado de recuperar los valos de los textfield en un objeto tipo cliente
    private Cliente getClienteForm() {
        Cliente cliente = new Cliente();
        Usuario user = Usuario.getSessionUser();
        cliente.setId(Integer.valueOf(gui.txt_id.getText()));
        cliente.setRuc(gui.txt_ruc.getText());
        cliente.setNombre(gui.txt_nombre.getText());
        cliente.setDireccion(gui.txt_direccion.getText());
        cliente.setTelefono(gui.txt_telefono.getText());
        cliente.setIdEmpresa(user.getEmpresaId());

        return cliente;
    }

    private void setClienteForm(Cliente cliente) {
        gui.txt_id.setText(cliente.getId().toString());
        gui.txt_ruc.setText(cliente.getRuc());
        gui.txt_nombre.setText(cliente.getNombre());
        gui.txt_direccion.setText(cliente.getDireccion());
        gui.txt_telefono.setText(cliente.getTelefono());;
    }

    private void limpiar() {
        gui.txt_id.setText("0");
        gui.txt_ruc.setText("");
        gui.txt_nombre.setText("");
        gui.txt_direccion.setText("");
        gui.txt_telefono.setText("");
    }

    public void listar() {
        List<Cliente> lista = this.abm.listar("");
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
