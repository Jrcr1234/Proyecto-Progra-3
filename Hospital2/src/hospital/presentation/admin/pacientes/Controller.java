package hospital.presentation.admin.pacientes;

import hospital.logic.Paciente;
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

    // === AÑADE ESTOS MÉTODOS 'ESQUELETO' ===
    public void guardarPaciente(Paciente paciente) {
        // La lógica para guardar irá aquí...
        System.out.println("Guardando paciente: " + paciente.getNombre());
    }

    public void borrarPaciente(int rowIndex) {
        // La lógica para borrar irá aquí...
        System.out.println("Borrando paciente en la fila: " + rowIndex);
    }

    public void buscarPaciente(String filtro) {
        // La lógica para buscar irá aquí...
        System.out.println("Buscando paciente con filtro: " + filtro);
    }

    public void generarReporte() {
        // La lógica para el reporte irá aquí...
        System.out.println("Generando reporte...");
    }

    public void clear() {
        model.setCurrent(new Paciente());
    }

    public void edit(int row) {
        Paciente p = model.getList().get(row);
        model.setCurrent(p);
    }
}
