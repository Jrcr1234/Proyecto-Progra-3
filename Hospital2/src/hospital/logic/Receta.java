package hospital.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Receta {
    private String codigo;
    private Medico medico;
    private Paciente paciente;
    private Date fechaConfeccion;
    private Date fechaRetiro;
    private String estado; // "Confeccionada", "Proceso", "Lista", "Entregada"
    private List<LineaDetalle> lineasDetalle;

    public Receta() {
        this.codigo = "";
        this.medico = null;
        this.paciente = null;
        this.fechaConfeccion = new Date(); // Fecha actual
        this.fechaRetiro = new Date();
        this.estado = "Confeccionada";
        this.lineasDetalle = new ArrayList<>();
    }

    // --- Getters y Setters para todos los atributos ---
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Date getFechaConfeccion() { return fechaConfeccion; }
    public void setFechaConfeccion(Date fechaConfeccion) { this.fechaConfeccion = fechaConfeccion; }
    public Date getFechaRetiro() { return fechaRetiro; }
    public void setFechaRetiro(Date fechaRetiro) { this.fechaRetiro = fechaRetiro; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<LineaDetalle> getLineasDetalle() { return lineasDetalle; }
    public void setLineasDetalle(List<LineaDetalle> lineasDetalle) { this.lineasDetalle = lineasDetalle; }
}