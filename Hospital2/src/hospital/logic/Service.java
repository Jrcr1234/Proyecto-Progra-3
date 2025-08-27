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

    private Service() {
    }
    // --- FIN DEL CÓDIGO DEL SINGLETON ---


    public Usuario autenticar(String id, String clave) throws Exception {
        if (id.equals("admin") && clave.equals("admin")) {
            return new Usuario(id, clave, "Administrador");
        } else {
            throw new Exception("Usuario o clave incorrectos");
        }
    }


    // Este método recibe un OBJETO Usuario y se encarga de la lógica de validación.
    public void cambiarClave(Usuario usuario, String claveActual, String claveNueva) throws Exception {
        // 1. Validar que la clave actual sea correcta
        if (!usuario.getClave().equals(claveActual)) {
            throw new Exception("La clave actual es incorrecta");
        }
        // 2. Actualizar el objeto en memoria
        usuario.setClave(claveNueva);
    }

    // Este método recibe un ID, busca al usuario y luego llama al otro método.
    public void cambiarClave(String id, String claveActual, String claveNueva) throws Exception {
        // Simulamos la búsqueda del usuario. En el futuro esto leerá del XML.
        Usuario usuarioEncontrado = new Usuario(id, id, "Médico");

        if (usuarioEncontrado == null) {
            throw new Exception("Usuario no encontrado");
        }

        // Ahora que encontramos al usuario, llamamos al otro método que sí existe.
        this.cambiarClave(usuarioEncontrado, claveActual, claveNueva);

        // Aquí guardaríamos la lista actualizada al XML.
    }
}