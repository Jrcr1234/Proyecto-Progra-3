package hospital.presentation.prescripcion;

import hospital.logic.LineaDetalle;
import hospital.logic.Paciente;
import hospital.presentation.common.AbstractModel;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    public static final String PACIENTE = "prescripcion.paciente";
    public static final String LINEAS = "prescripcion.lineas";

    private Paciente paciente;
    private List<LineaDetalle> lineas;

    public Model() {
        this.paciente = null;
        this.lineas = new ArrayList<>();
    }

    public void clear() {
        setPaciente(null);
        setLineas(new ArrayList<>());
    }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) {
        Paciente old = this.paciente;
        this.paciente = paciente;
        firePropertyChange(PACIENTE, old, this.paciente);
    }

    public List<LineaDetalle> getLineas() { return lineas; }
    public void setLineas(List<LineaDetalle> lineas) {
        List<LineaDetalle> old = this.lineas;
        this.lineas = lineas;
        firePropertyChange(LINEAS, old, this.lineas);
    }
}