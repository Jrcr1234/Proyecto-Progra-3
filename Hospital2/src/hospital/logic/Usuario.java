package hospital.logic;

public class Usuario {
    // --- Atributos ---
    private String id;
    private String clave;
    private String tipo; // Guardará si es "Administrador", "Médico" o "Farmaceuta"

    // --- Constructores ---

    /**
     * Constructor vacío
     */
    public Usuario() {
        this.id = "";
        this.clave = "";
        this.tipo = "";
    }

    /**
     * Constructor con los parámetros básicos para un usuario.
     * @param id Cédula o identificador del usuario.
     * @param clave Contraseña para el ingreso.
     * @param tipo Rol del usuario dentro del sistema.
     */
    public Usuario(String id, String clave, String tipo) {
        this.id = id;
        this.clave = clave;
        this.tipo = tipo;
    }

    // --- Getters y Setters ---
    // Métodos para acceder y modificar los atributos de forma controlada.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}