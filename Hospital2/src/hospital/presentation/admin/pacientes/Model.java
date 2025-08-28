package hospital.presentation.admin.pacientes;

import hospital.logic.Paciente;
import hospital.AbtractModel.AbtractModel;

public class Model extends AbtractModel {

    private Paciente current;
    public static final String CURRENT = "current";

    // Constructor recibe un Paciente desde el Controlador

    public Model(Paciente paciente) {
        this.current = paciente;
        firePropertyChange(CURRENT, null, this.current); // notifica inicialización
    }

    // Getter de la persona actual
    public Paciente getCurrent() {
        return current;
    }

    // Setter de la persona actual con notificación a las vistas
    public void setCurrent(Paciente current) {
        Paciente old = this.current;
        this.current = current;
        firePropertyChange(CURRENT, old, this.current);
    }

    // Método auxiliar opcional: información resumida del paciente
    public String getPacienteInfo() {
        if (current == null) return "No hay paciente cargado.";
        return "ID: " + current.getId() +
                ", Nombre: " + current.getNombre() +
                ", Numero telefonico: " + current.getNumeroTelefonico();
    }

}