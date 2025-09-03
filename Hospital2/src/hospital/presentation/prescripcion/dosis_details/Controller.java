package hospital.presentation.prescripcion.dosis_details;

import hospital.logic.LineaDetalle;
import javax.swing.JDialog;
import java.util.ArrayList;
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
            if (model.getLineaExistente() != null) {
                // MODO EDICIÓN: Actualizamos el objeto existente
                LineaDetalle linea = model.getLineaExistente();
                linea.setCantidad(view.getCantidad());
                linea.setIndicaciones(view.getIndicaciones());
                linea.setDuracionTratamiento(view.getDuracion());

                // Forzamos la actualización de la tabla principal
                prescripcionModel.setLineas(new ArrayList<>(prescripcionModel.getLineas()));

            } else {
                // MODO AGREGAR: La lógica que ya tenías y que corregimos
                LineaDetalle nuevaLinea = new LineaDetalle();
                nuevaLinea.setMedicamento(model.getSeleccionado());
                nuevaLinea.setCantidad(view.getCantidad());
                nuevaLinea.setIndicaciones(view.getIndicaciones());
                nuevaLinea.setDuracionTratamiento(view.getDuracion());

                List<LineaDetalle> nuevaListaLineas = new ArrayList<>(prescripcionModel.getLineas());
                nuevaListaLineas.add(nuevaLinea);
                prescripcionModel.setLineas(nuevaListaLineas);
            }

            this.cancelar(); // Cerramos el diálogo

        } catch (Exception e) {
            view.mostrarError("Datos de dosis inválidos.");
        }
    }

    public void cancelar() {
        dialog.dispose();
    }
}