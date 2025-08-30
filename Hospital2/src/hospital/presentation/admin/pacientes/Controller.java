package hospital.presentation.admin.pacientes;

import hospital.logic.Paciente;
import hospital.logic.Service;
import javax.swing.JOptionPane;
import java.util.List;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setModel(model);
        view.setController(this);
        // Carga inicial de datos
        try {
            model.setList(Service.instance().getPacientes());
            model.setCurrent(new Paciente());
        } catch (Exception e) {
            // Manejar error si el Service no puede iniciar
        }
    }

    public void guardarPaciente(Paciente paciente) {
        try {
            // Validaciones de la interfaz
            if (paciente.getId().isEmpty() || paciente.getNombre().isEmpty()) {
                throw new Exception("La cédula y el nombre son requeridos.");
            }

            // Lógica para decidir si es CREAR o ACTUALIZAR
            if (model.getCurrent().getId().isEmpty()) {
                // El paciente es nuevo
                Service.instance().createPaciente(paciente);
                JOptionPane.showMessageDialog(view.getPanel(), "Paciente agregado exitosamente.");
            } else {
                // El paciente ya existía, es una modificación
                Service.instance().updatePaciente(paciente);
                JOptionPane.showMessageDialog(view.getPanel(), "Paciente modificado exitosamente.");
            }

            this.clear();
            this.buscarPaciente("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void borrarPaciente(int rowIndex) {
        try {
            Paciente paciente = model.getList().get(rowIndex);
            Service.instance().deletePaciente(paciente.getId());
            this.buscarPaciente(""); // Refresca la tabla
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarPaciente(String filtro) {
        try {
            List<Paciente> rows = Service.instance().searchPacientes(filtro);
            model.setList(rows);
        } catch (Exception e) {
            // Manejar error si la búsqueda falla
        }
    }

    public void generarReporte() {
        // La lógica para generar el reporte de pacientes irá aquí...
        JOptionPane.showMessageDialog(view.getPanel(), "Funcionalidad de reporte aún no implementada.");
    }

    public void clear() {
        model.setCurrent(new Paciente());
    }

    public void edit(int row) {
        Paciente p = model.getList().get(row);
        model.setCurrent(p);
    }
}