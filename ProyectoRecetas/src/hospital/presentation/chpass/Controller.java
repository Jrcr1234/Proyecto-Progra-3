package hospital.presentation.chpass;

import hospital.logic.Service;
import javax.swing.*;

public class Controller {
    private Model model;
    private View view;
    private JDialog dialog; // El diálogo que contiene la vista

    public Controller(Model model, View view, JDialog dialog) {
        this.model = model;
        this.view = view;
        this.dialog = dialog;
        view.setController(this);
    }

    public void cambiarClave() {
        try {
            String actual = view.getActual();
            String nueva = view.getNueva();
            String confirmar = view.getConfirmar();

            if (!nueva.equals(confirmar)) {
                throw new Exception("La clave nueva y la confirmación no coinciden");
            }
            // Llama al Service para que haga el trabajo
            Service.instance().cambiarClave(model.getUser(), actual, nueva);

            JOptionPane.showMessageDialog(view.getPanel(), "Clave cambiada exitosamente");
            this.cerrarVentana();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cerrarVentana() {
        dialog.dispose();
    }
}