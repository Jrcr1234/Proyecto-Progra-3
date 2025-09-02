package hospital.presentation.prescripcion.medicamento_add;

import hospital.application.Application;
import hospital.logic.LineaDetalle;
import hospital.logic.Medicamento;
import hospital.logic.Service;
import javax.swing.JDialog;
import java.util.List;

public class Controller {
    private View view;
    private Model model;
    private JDialog dialog;
    private hospital.presentation.prescripcion.Model prescripcionModel; // El modelo de la ventana principal

    public Controller(View view, Model model, JDialog dialog, hospital.presentation.prescripcion.Model prescripcionModel) {
        this.view = view;
        this.model = model;
        this.dialog = dialog;
        this.prescripcionModel = prescripcionModel;
        view.setModel(model);
        view.setController(this);
        // Carga inicial de todos los medicamentos
        model.setList(Service.instance().getMedicamentos());
    }

    public void buscar() {
        String filtro = view.getFiltro(); // ej. "Nombre" o "Código"
        String termino = view.getTerminoBusqueda(); // Lo que el usuario ha escrito

        // El Service ya sabe buscar por nombre o código con el mismo método
        List<Medicamento> resultado = Service.instance().searchMedicamentos(termino);
        model.setList(resultado);
    }

    public void seleccionar() {
        Medicamento seleccionado = view.getSelected();
        if (seleccionado != null) {
            // 1. Cerramos la ventana de búsqueda actual
            this.dialog.dispose();

            // 2. Y AHORA ABRIMOS LA NUEVA VENTANA DE DETALLES
            JDialog detailsDialog = new JDialog(Application.getWindow(), "Detalles de Dosis", true);

            // Creamos el MVC para el diálogo de detalles
            hospital.presentation.prescripcion.dosis_details.Model detailsModel = new hospital.presentation.prescripcion.dosis_details.Model(seleccionado);
            hospital.presentation.prescripcion.dosis_details.View detailsView = new hospital.presentation.prescripcion.dosis_details.View();
            new hospital.presentation.prescripcion.dosis_details.Controller(detailsView, detailsModel, detailsDialog, this.prescripcionModel);

            // Inicializamos la vista de detalles
            detailsView.init();

            // Configuramos y mostramos el nuevo diálogo
            detailsDialog.setContentPane(detailsView.getPanel());
            detailsDialog.pack();
            detailsDialog.setResizable(false);
            detailsDialog.setLocationRelativeTo(Application.getWindow());
            detailsDialog.setVisible(true);
        }
    }

    public void agregar() {
        if (model.getCurrent() == null) {
            view.mostrarError("Debe seleccionar un medicamento de la lista.");
            return;
        }
        try {
            // Creamos la nueva línea de detalle con los datos
            LineaDetalle nuevaLinea = new LineaDetalle();
            nuevaLinea.setMedicamento(model.getCurrent());
            nuevaLinea.setCantidad(view.getCantidad());
            nuevaLinea.setIndicaciones(view.getIndicaciones());
            nuevaLinea.setDuracionTratamiento(view.getDuracion());

            // La añadimos a la lista de líneas en el modelo de la prescripción principal
            prescripcionModel.getLineas().add(nuevaLinea);
            // Forzamos una notificación para que la tabla principal se actualice
            prescripcionModel.setLineas(prescripcionModel.getLineas());

            this.cancelar(); // Cerramos el diálogo
        } catch(Exception e) {
            view.mostrarError("Datos de dosis inválidos.");
        }
    }

    public void cancelar() {
        dialog.dispose();
    }
}