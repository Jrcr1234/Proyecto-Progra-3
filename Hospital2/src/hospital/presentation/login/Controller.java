package hospital.presentation.login;

import hospital.logic.Service;
import hospital.logic.Usuario;
import hospital.logic.Sesion;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Controller {
    private View view;
    private Model model;
    private JDialog dialog;

    public Controller(View view, Model model, JDialog dialog) {
        this.dialog = dialog;
        this.view = view;
        this.model = model;
        view.setModel(model);
        view.setController(this);
    }

    // --- MÉTODO INGRESAR CORREGIDO ---
    public void ingresar(String id, String clave) {
        try {
            // Usamos los parámetros 'id' y 'clave' que recibe el método
            Usuario validado = Service.instance().autenticar(id, clave);
            Sesion.setUsuario(validado);
            dialog.dispose();
        } catch (Exception e) {
            view.mostrarError(e.getMessage());
        }
    }
//Metodo salir
    public void salir() {
        System.exit(0);
    }
//Metodo para el cambio de clave
    public void showChangePasswordView() {
        // La lógica para abrir la ventana de cambio de clave irá aquí
        System.out.println("Abriendo ventana de cambio de clave...");
    }
    // ===============================
}