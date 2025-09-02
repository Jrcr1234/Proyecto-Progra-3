package hospital.presentation.prescripcion.dosis_details;

import hospital.logic.Medicamento;

public class Model {
    private Medicamento seleccionado;

    public Model(Medicamento seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Medicamento getSeleccionado() {
        return seleccionado;
    }
}