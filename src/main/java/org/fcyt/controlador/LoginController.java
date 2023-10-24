/*
* UNIDAD III - Codificacion de programas de Interfaz
*/
package org.fcyt.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.fcyt.modelo.Usuario;
import org.fcyt.modelo.dao.UsuarioDaoImpl;
import org.fcyt.modelo.tabla.UsuarioTablaModel;
import org.fcyt.vista.GUILogin;
import org.fcyt.vista.GUILogin;
import org.fcyt.vista.GUIVentanaPrincipal;

/**
 *
 * @author cmendieta
 */
public class LoginController implements ActionListener {

    private GUILogin gui;
    private UsuarioDaoImpl abm;
    private char operacion;

    UsuarioTablaModel modelo = new UsuarioTablaModel();

    public LoginController(GUILogin gui, UsuarioDaoImpl abm) {
        this.gui = gui;
        this.abm = abm;

        this.gui.btnIngresar.addActionListener((ActionListener) this);
        this.gui.registrarNuevo.addActionListener(this);

    }
//ActionPerformed nos referimos al evento que se produce al hacer click en un componente o al pulsar Enter (teniendo el foco en el componente)

    @Override
    public void actionPerformed(ActionEvent e) {     
Boolean v_ingresar = false;
        if (e.getSource() == gui.btnIngresar) {
            boolean v_control = validarDatos();
            if(v_control == true){
                JOptionPane.showMessageDialog(gui, "Favor completar los datos");
                return;
            }
             v_ingresar = abm.login(getUsuarioForm());
             System.out.println(v_ingresar);
             if(v_ingresar){
                 //Este campo será compartido por todas las instancias de la clase,
                 //Obs:Los métodos estáticos se pueden invocar utilizando el nombre de la clase y no requieren una instancia de la clase.
                  Usuario user = Usuario.getSessionUser();
                  
                 GUIVentanaPrincipal guiVentanaPrincipal = new GUIVentanaPrincipal();
                 guiVentanaPrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
                 guiVentanaPrincipal.setTitle("Sistema de punto de venta/"+ user.getUsuario());
                 guiVentanaPrincipal.setVisible(true);
                 
                 this.gui.dispose();
                 
                 
             }
            
            limpiar();
        }
        if(e.getSource() == gui.registrarNuevo){
            gui.jpanelRegistro.setVisible(true);
            gui.jpanelLogin.setVisible(false);
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
        usuario.setUsuario(gui.txt_usuario.getText());
        usuario.setClave(gui.txt_clave.getText());

        return usuario;
    }

    private void limpiar() {
        gui.txt_usuario.setText("");
        gui.txt_clave.setText("");
    }

    
    private boolean validarDatos(){
        boolean vacio = false;
        
        if(gui.txt_usuario.getText().isEmpty()){
            vacio = true;
        }
          if(gui.txt_clave.getText().isEmpty()){
            vacio = true;
        }
        return vacio;
    }

}
