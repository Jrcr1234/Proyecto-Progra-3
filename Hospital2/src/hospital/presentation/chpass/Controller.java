package hospital.presentation.chpass;

import javax.swing.JDialog;

public class Controller {
    private Model model;
    private View view;
    private JDialog dialog;


    public Controller(Model model, View view, JDialog dialog) {
        this.model = model;
        this.view = view;
        this.dialog = dialog;

        // Suponiendo que tu chpass/View tendrá este método para enlazar el controlador
        // view.setController(this);
    }

    // Aquí irán los métodos que los botones llaman, como cambiarClave() y cerrarVentana()
}