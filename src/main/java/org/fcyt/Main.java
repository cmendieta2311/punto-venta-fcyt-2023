package org.fcyt;
import org.fcyt.modelo.dao.EmpresaDaoImpl;
import org.fcyt.controlador.EmpresaController;
import org.fcyt.controlador.IvaController;
import org.fcyt.controlador.MarcaController;
import org.fcyt.modelo.dao.IvaDaoImpl;
import org.fcyt.modelo.dao.MarcaDaoImpl;
import org.fcyt.vista.GUIEmpresa;
import org.fcyt.vista.GUIIva;
import org.fcyt.vista.GUIMarca;
import org.fcyt.vista.GUIVentanaPrincipal;

/**
 * Este metodo se encarga de iniciar la ejecucion del programa
 * Este es el metodo principal del proyecto
 * @param args[] es un arreglo con los parametros que el reciba por consola
 * @return void
 **/
public class Main {
    public static void main(String[] args) {
        GUIVentanaPrincipal gui = new GUIVentanaPrincipal();
        gui.setVisible(true);
        
       
    }
}