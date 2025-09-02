package hospital.presentation.prescripcion;

import hospital.logic.Receta;
import hospital.logic.Service;
import hospital.application.Application;
import javax.swing.*;
import javax.swing.JDialog;



public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setModel(model);
        view.setController(this);
    }

    // === MÉTODO PARA ABRIR EL DIÁLOGO DE BÚSQUEDA ===
    public void buscarPaciente() {
        // Creamos un JDialog modal. El 'true' al final hace que la ventana
        // de prescripción se bloquee hasta que esta se cierre.
        JDialog dialog = new JDialog(Application.getWindow(), "Buscar Paciente", true);

        // Creamos el MVC del módulo de búsqueda
        hospital.presentation.prescripcion.paciente_search.Model searchModel = new hospital.presentation.prescripcion.paciente_search.Model();
        hospital.presentation.prescripcion.paciente_search.View searchView = new hospital.presentation.prescripcion.paciente_search.View();

        // Le pasamos nuestro 'model' principal al nuevo controlador para que
        // pueda devolvernos el paciente seleccionado.
        new hospital.presentation.prescripcion.paciente_search.Controller(searchView, searchModel, dialog, this.model);

        // Inicializamos la vista de búsqueda
        searchView.init();

        // Configuramos y mostramos el diálogo
        dialog.setContentPane(searchView.getPanel());
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this.view.getPanel()); // Para que aparezca centrado
        dialog.setVisible(true);
        // El código se detendrá aquí hasta que el usuario cierre el diálogo
    }

    public void agregarMedicamento() {
        // Creamos un JDialog modal para la nueva ventana
        JDialog dialog = new JDialog(Application.getWindow(), "Agregar Medicamento", true);

        // Creamos el trío MVC del módulo 'medicamento_add'
        hospital.presentation.prescripcion.medicamento_add.Model addModel = new hospital.presentation.prescripcion.medicamento_add.Model();
        hospital.presentation.prescripcion.medicamento_add.View addView = new hospital.presentation.prescripcion.medicamento_add.View();

        // Le pasamos el 'model' de la prescripción principal para que nos pueda devolver la LineaDetalle
        new hospital.presentation.prescripcion.medicamento_add.Controller(addView, addModel, dialog, this.model);

        // Inicializamos la vista
        addView.init();

        // Configuramos y mostramos el diálogo
        dialog.setContentPane(addView.getPanel());
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this.view.getPanel()); // Para que aparezca centrado
        dialog.setVisible(true); }

    public void registrarReceta() {
        Receta receta = new Receta();
        receta.setPaciente(model.getPaciente());
        receta.setLineasDetalle(model.getLineas());
        receta.setFechaRetiro(view.getFechaRetiro());
        // El médico se asignará a partir del usuario logueado
        // receta.setMedico(...);

        try {
            Service.instance().createReceta(receta);
            JOptionPane.showMessageDialog(view.getPanel(), "Receta registrada con éxito. Código: " + receta.getCodigo());
            model.clear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiar() {
        model.clear();
    }
}