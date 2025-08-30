package hospital.logic;

// --- IMPORTS AÑADIDOS PARA XML ---
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
// ---------------------------------
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class Paciente {
    private String id;
    private String nombre;
    private Date fechaNacimiento;
    private String telefono;

    public Paciente() {
        this.id = "";
        this.nombre = "";
        this.fechaNacimiento = null;
        this.telefono = "";
    }

    public Paciente(String id, String nombre, Date fechaNacimiento, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
    }

    // GETTERS Y SETTERS
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}