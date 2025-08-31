package hospital.presentation.prescripcion.paciente_search;

import hospital.logic.Paciente;
import hospital.logic.Service;
import javax.swing.JDialog;

public class Controller {
    private View view;
    private Model model;
    private JDialog dialog;
    // Guardamos una referencia al modelo de la ventana de prescripción principal
    private hospital.presentation.prescripcion.Model prescripcionModel;

    public Controller(View view, Model model, JDialog dialog, hospital.presentation.prescripcion.Model prescripcionModel) {
        this.view = view;
        this.model = model;
        this.dialog = dialog;
        this.prescripcionModel = prescripcionModel;
        view.setModel(model);
        view.setController(this);
    }

    // Este método es llamado por los listeners de la Vista
    public void buscar() {
        String criterio = view.getTerminoBusqueda();
        model.setList(Service.instance().searchPacientes(criterio));
    }

    public void seleccionar() {
        Paciente seleccionado = view.getSelectedPatient();
        if (seleccionado != null) {
            // Ponemos el paciente seleccionado en el modelo de la ventana de prescripción
            prescripcionModel.setPaciente(seleccionado);
            this.cancelar(); // Cerramos la ventana de búsqueda
        }
    }

    public void cancelar() {
        dialog.dispose();
    }
}