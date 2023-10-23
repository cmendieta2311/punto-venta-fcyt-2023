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
import org.fcyt.modelo.Usuario;
import org.fcyt.modelo.dao.UsuarioDaoImpl;
import org.fcyt.modelo.tabla.UsuarioTablaModel;
import org.fcyt.vista.GUIUsuario;

/**
 *
 * @author cmendieta
 */
public class UsuarioController implements ActionListener {

    private GUIUsuario gui;
    private UsuarioDaoImpl abm;
    private char operacion;

    UsuarioTablaModel modelo = new UsuarioTablaModel();

    public UsuarioController(GUIUsuario gui, UsuarioDaoImpl abm) {
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
                UsuarioTablaModel model = (UsuarioTablaModel) tabla.getModel();

                setUsuarioForm(model.getEntityByRow(row));

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
            gui.txt_descripcion.requestFocus();
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
                    abm.insertar(getUsuarioForm());
                    break;
                case 'E':
                    abm.actualizar(getUsuarioForm());
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

    //Funcion encargado de recuperar los valos de los textfield en un objeto tipo usuario
    private Usuario getUsuarioForm() {
        Usuario usuario = new Usuario();
        usuario.setId(Integer.valueOf(gui.txt_id.getText()));
        usuario.setUsuario(gui.txt_descripcion.getText());
        usuario.setClave("12345");
        usuario.setEmpresaId(23); //Cambiar esta linea

        return usuario;
    }

    private void setUsuarioForm(Usuario usuario) {
        gui.txt_id.setText(usuario.getId().toString());
        gui.txt_descripcion.setText(usuario.getUsuario());
    }

    private void limpiar() {
        gui.txt_id.setText("0");
        gui.txt_descripcion.setText("");
    }

    public void listar() {
        List<Usuario> lista = this.abm.listar();
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

}
