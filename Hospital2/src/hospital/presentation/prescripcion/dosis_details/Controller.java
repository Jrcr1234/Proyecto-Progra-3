package hospital.presentation.prescripcion.dosis_details;

import hospital.logic.LineaDetalle;
import javax.swing.JDialog;
import java.util.List;

public class Controller {
    private Model model;
    private View view;
    private JDialog dialog;
    private hospital.presentation.prescripcion.Model prescripcionModel;

    public Controller(View view, Model model, JDialog dialog, hospital.presentation.prescripcion.Model prescripcionModel) {
        this.view = view;
        this.model = model;
        this.dialog = dialog;
        this.prescripcionModel = prescripcionModel;
        view.setModel(model);
        view.setController(this);
    }

    public void guardar() {
        try {
            LineaDetalle nuevaLinea = new LineaDetalle();
            nuevaLinea.setMedicamento(model.getSeleccionado());
            nuevaLinea.setCantidad(view.getCantidad());
            nuevaLinea.setIndicaciones(view.getIndicaciones());
            nuevaLinea.setDuracionTratamiento(view.getDuracion());

            // 1. Obtenemos la lista actual del modelo principal
            List<LineaDetalle> lineasActuales = prescripcionModel.getLineas();
            // 2. Le añadimos la nueva línea
            lineasActuales.add(nuevaLinea);
            // 3. Volvemos a establecer la lista en el modelo.
            //    ESTO FORZARÁ a que se ejecute firePropertyChange y se notifique a la vista.
            prescripcionModel.setLineas(lineasActuales);

            this.cancelar(); // Cerramos el diálogo
        } catch (Exception e) {
            view.mostrarError("Datos de dosis inválidos.");
        }
    }

    public void cancelar() {
        dialog.dispose();
    }
}