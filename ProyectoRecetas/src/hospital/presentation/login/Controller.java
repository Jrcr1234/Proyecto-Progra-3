package hospital.presentation.login;

import hospital.application.Application;
import hospital.logic.Service;
import hospital.logic.Usuario;

import javax.swing.*;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
    }

    public void ingresar() {
        try {
            // 1. Pide al Service que autentique al usuario
            Usuario usuario = Service.instance().autenticar(view.getId(), view.getClave());

            // 2. Si es correcto, guarda el usuario en el modelo y avisa a la aplicación principal
            model.setLoggedUser(usuario);
            Application.onLoginSuccess(usuario); // Le avisamos a la app que el login fue exitoso

        } catch (Exception e) {
            // 3. Si falla, muestra un error
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error de Ingreso", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void salir() {
        System.exit(0);
    }
}
