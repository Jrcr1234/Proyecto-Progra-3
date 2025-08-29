package hospital.logic;

import java.util.Date;

public class Paciente {
    private String id;
    private String nombre;
    private Date fechaNacimiento;
    private String telefono;

    // --- ASEGÚRATE DE QUE AMBOS CONSTRUCTORES SEAN 'public' ---
    public Paciente() { // <--- CORRECCIÓN AQUÍ
        this.id = "";
        this.nombre = "";
        this.fechaNacimiento = new Date();
        this.telefono = "";
    }

    public Paciente(String id, String nombre, Date fechaNacimiento, String telefono) { // <--- Y AQUÍ
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