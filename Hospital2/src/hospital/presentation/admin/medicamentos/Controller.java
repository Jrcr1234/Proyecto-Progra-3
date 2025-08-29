package hospital.presentation.admin.medicamentos;

import hospital.logic.Medicamento;
import hospital.logic.Service;
import javax.swing.JOptionPane;
import java.util.List;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.model = model;
        this.view = view;
        // --- ORDEN CORREGIDO ---
        // 1. Conectamos la Vista al Modelo y al Controlador.
        //    A partir de aquí, la Vista está "escuchando" cualquier notificación.
        view.setModel(model);
        view.setController(this);

        // 2. AHORA SÍ, cargamos los datos iniciales en el Modelo.
        //    El Modelo notificará a la Vista (que ya está escuchando) y la tabla se llenará.
        try {
            model.setList(Service.instance().getMedicamentos());
            model.setCurrent(new Medicamento());
        } catch (Exception e) {
            // En caso de que el Service falle al iniciar
        }
    }

    public void search(String filtro) {
        List<Medicamento> rows = Service.instance().searchMedicamentos(filtro);
        model.setList(rows);
    }

    // === MÉTODO 'SAVE' REFINADO ===
    public void save(Medicamento medFromForm) {
        try {
            // Validaciones básicas de la interfaz
            if (medFromForm.getCodigo().isEmpty() || medFromForm.getNombre().isEmpty()) {
                throw new Exception("El código y el nombre son requeridos.");
            }

            // Lógica para decidir si es CREAR o ACTUALIZAR
            if (model.getCurrent().getCodigo().isEmpty()) {
                // El 'current' del modelo estaba vacío, por lo tanto, es un medicamento NUEVO
                Service.instance().createMedicamento(medFromForm);
                JOptionPane.showMessageDialog(view.getPanel(), "Medicamento agregado exitosamente.");
            } else {
                // El 'current' del modelo tenía datos, por lo tanto, es una MODIFICACIÓN
                Service.instance().updateMedicamento(medFromForm);
                JOptionPane.showMessageDialog(view.getPanel(), "Medicamento modificado exitosamente.");
            }

            // Limpia el formulario y refresca la tabla después de cualquier operación exitosa
            this.clear();
            this.search("");

        } catch (Exception e) {
            // Atrapa cualquier error del Service (ej. "código ya existe") o de las validaciones
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void edit(int row) {
        Medicamento med = model.getList().get(row);
        model.setCurrent(med);
    }

    public void delete(int row) {
        try {
            Medicamento med = model.getList().get(row);
            Service.instance().deleteMedicamento(med.getCodigo());
            this.search(""); // Refresca la tabla
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clear() {
        model.setCurrent(new Medicamento());
    }
}