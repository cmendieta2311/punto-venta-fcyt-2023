package org.fcyt;
import javax.swing.JFrame;
import org.fcyt.controlador.LoginController;
import org.fcyt.modelo.dao.UsuarioDaoImpl;
import org.fcyt.vista.GUILogin;
import org.fcyt.vista.GUIVentanaPrincipal;

/**
 * Este metodo se encarga de iniciar la ejecucion del programa
 * Este es el metodo principal del proyecto
 * @param args[] es un arreglo con los parametros que el reciba por consola
 * @return void
 **/
public class Main {
    public static void main(String[] args) {
        //GUIVentanaPrincipal gui = new GUIVentanaPrincipal();
//        La función setExtendedState en Java se utiliza para establecer el estado extendido de una ventana en una aplicación de interfaz gráfica de usuario (GUI).
//        Esta función permite configurar el estado de una ventana, como maximizar, minimizar o restaurar, además de otros estados extendidos
        //gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //gui.setVisible(true);
        
        
        UsuarioDaoImpl dao = new UsuarioDaoImpl();
        GUILogin gui = new GUILogin(null, true);
        LoginController ctrl = new LoginController(gui, dao);
        ctrl.mostrarVentana();
        
       
    }
}