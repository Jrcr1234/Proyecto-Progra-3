package hospital.logic;

public class Service {

    // --- CÓDIGO DEL SINGLETON ---
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    // Constructor privado para asegurar que nadie más pueda crear instancias
    private Service() {
    }
    // --- FIN DEL CÓDIGO DEL SINGLETON ---


    public Usuario autenticar(String id, String clave) throws Exception {
        // Método de autenticación
        if (id.equals("admin") && clave.equals("admin")) {
            return new Usuario(id, clave, "Administrador");
        } else {
            throw new Exception("Usuario o clave incorrectos");
        }
    }

    public void cambiarClave(Usuario usuario, String claveActual, String claveNueva) throws Exception {
        //Método para cambiar clave
        if (!usuario.getClave().equals(claveActual)) {
            throw new Exception("La clave actual es incorrecta");
        }
        usuario.setClave(claveNueva);
        // La lógica de persistencia en XML la agregaremos después.
    }
}