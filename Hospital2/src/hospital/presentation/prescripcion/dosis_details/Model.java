package hospital.presentation.prescripcion.dosis_details;

import hospital.logic.Medicamento;
import hospital.logic.LineaDetalle;

public class Model {
    private Medicamento seleccionado;
    private LineaDetalle lineaExistente;

    // Constructor original (para AGREGAR)
    public Model(Medicamento seleccionado) {
        this.seleccionado = seleccionado;
        this.lineaExistente = null;
    }

    // NUEVO CONSTRUCTOR (para MODIFICAR)
    public Model(LineaDetalle linea) {
        this.seleccionado = linea.getMedicamento();
        this.lineaExistente = linea;
    }

    public Medicamento getSeleccionado() { return seleccionado; }
    public LineaDetalle getLineaExistente() { return lineaExistente; }
}