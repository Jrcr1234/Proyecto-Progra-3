package hospital.logic;

import hospital.data.XmlDataManager; // <-- Importante
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    // --- CÓDIGO DEL SINGLETON (sin cambios) ---
    private static Service theInstance;
    public static Service instance() {
        if (theInstance == null) { theInstance = new Service(); }
        return theInstance;
    }
    private XmlDataManager dataManagerMedicamentos;
    private List<Medicamento> listaMedicamentos;
    private XmlDataManager dataManagerPacientes;
    private List<Paciente> listaPacientes;
    private XmlDataManager dataManagerUsuarios;
    private List<Usuario> listaUsuarios;
    // Aquí irían los data managers y listas para farmaceutas, etc.

    private Service() {
        try {
            // Le decimos dónde guardar el archivo XML de medicamentos
            dataManagerMedicamentos = new XmlDataManager("medicamentos.xml");
            dataManagerPacientes = new XmlDataManager("pacientes.xml");
            dataManagerUsuarios = new XmlDataManager("usuarios.xml");

            // Cargamos la lista de medicamentos desde el XML al iniciar la aplicación
            listaMedicamentos = dataManagerMedicamentos.cargarMedicamentos();
            listaPacientes = dataManagerPacientes.cargarPacientes();
            listaUsuarios = dataManagerUsuarios.cargarUsuarios();
        } catch (Exception e) {
            // Si hay un error (ej. el archivo no existe), empezamos con una lista vacía
            listaMedicamentos = new ArrayList<>();
            listaPacientes = new ArrayList<>();
            listaUsuarios = new ArrayList<>();
        }
    }

    // --- MÉTODOS DE USUARIO ---

    public Usuario autenticar(String id, String clave) throws Exception {
        // Buscamos en la lista en memoria un usuario con el ID proporcionado.
        Usuario usuario = listaUsuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);

        // Si encontramos al usuario Y su clave coincide, el login es exitoso.
        if (usuario != null && usuario.getClave().equals(clave)) {
            return usuario;
        } else {
            // Si el usuario no existe o la clave es incorrecta, lanzamos un error.
            throw new Exception("Usuario o clave incorrectos");
        }
    }

    // Este método es para un usuario que YA ESTÁ DENTRO del sistema (ej. desde el menú principal).
    public void cambiarClave(Usuario usuario, String claveActual, String claveNueva) throws Exception {
        // 1. Validar que la clave actual sea correcta.
        if (!usuario.getClave().equals(claveActual)) {
            throw new Exception("La clave actual es incorrecta");
        }

        // 2. Actualizar el objeto en memoria.
        usuario.setClave(claveNueva);

        // 3. Persistir el cambio guardando la lista completa de usuarios al XML.
        dataManagerUsuarios.guardarUsuarios(listaUsuarios);
    }

    // Este método es para el flujo de "Restaurar Clave" DESDE LA PANTALLA DE LOGIN.
    public void cambiarClave(String id, String claveActual, String claveNueva) throws Exception {
        // 1. Buscamos al usuario por su ID en la lista en memoria.
        Usuario usuarioEncontrado = listaUsuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (usuarioEncontrado == null) {
            throw new Exception("Usuario no encontrado");
        }

        // 2. Reutilizamos el otro método para hacer la validación y el cambio.
        this.cambiarClave(usuarioEncontrado, claveActual, claveNueva);
    }

    // =======================================================
    // ===     NUEVOS MÉTODOS CRUD PARA MEDICAMENTOS       ===
    // =======================================================

    // En hospital.logic/Service.java

// --- Métodos CRUD para Medicamentos ---

    public void createMedicamento(Medicamento med) throws Exception {
        if (listaMedicamentos.stream().anyMatch(m -> m.getCodigo().equals(med.getCodigo()))) {
            throw new Exception("El código del medicamento ya existe.");
        }
        // PASO 1: AÑADE EL NUEVO MEDICAMENTO A LA LISTA EN MEMORIA
        listaMedicamentos.add(med);

        // PASO 2: GUARDA LA LISTA COMPLETA Y ACTUALIZADA AL ARCHIVO XML
        dataManagerMedicamentos.guardarMedicamentos(listaMedicamentos);
    }

    public void updateMedicamento(Medicamento med) throws Exception {
        Medicamento medActual = this.readMedicamento(med.getCodigo()); // readMedicamento busca en la lista en memoria

        // PASO 1: MODIFICA EL OBJETO EN LA LISTA EN MEMORIA
        medActual.setNombre(med.getNombre());
        medActual.setPresentacion(med.getPresentacion());

        // PASO 2: GUARDA LA LISTA ACTUALIZADA
        dataManagerMedicamentos.guardarMedicamentos(listaMedicamentos);
    }

    public void deleteMedicamento(String codigo) throws Exception {
        Medicamento med = this.readMedicamento(codigo);

        // PASO 1: ELIMINA DE LA LISTA EN MEMORIA
        listaMedicamentos.remove(med);

        // PASO 2: GUARDA LA LISTA ACTUALIZADA
        dataManagerMedicamentos.guardarMedicamentos(listaMedicamentos);
    }
    public Medicamento readMedicamento(String codigo) throws Exception {
        Medicamento med = listaMedicamentos.stream()
                .filter(m -> m.getCodigo().equals(codigo))
                .findFirst().orElse(null);
        if (med == null) {
            throw new Exception("Medicamento no existe.");
        }
        return med;
    }
    public List<Medicamento> getMedicamentos() {
        // Devuelve la lista completa de medicamentos que está en memoria
        return listaMedicamentos;
    }

    public List<Medicamento> searchMedicamentos(String filtro) {
        // Busca en la lista en memoria por código o por nombre
        return listaMedicamentos.stream()
                .filter(m -> m.getCodigo().contains(filtro) || m.getNombre().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }

    // =======================================================
// ===       MÉTODOS CRUD COMPLETOS Y CORRECTOS PARA PACIENTES     ===
// =======================================================

    public void createPaciente(Paciente p) throws Exception {
        if (p.getId().isEmpty() || p.getNombre().isEmpty()) {
            throw new Exception("Cédula y Nombre son requeridos.");
        }
        if (listaPacientes.stream().anyMatch(pa -> pa.getId().equals(p.getId()))) {
            throw new Exception("La cédula del paciente ya existe.");
        }
        // 1. Modifica la lista en memoria
        listaPacientes.add(p);
        // 2. Guarda la lista actualizada en el archivo
        dataManagerPacientes.guardarPacientes(listaPacientes);
    }

    public Paciente readPaciente(String id) throws Exception {
        // Busca en la lista en memoria
        Paciente p = listaPacientes.stream()
                .filter(pa -> pa.getId().equals(id))
                .findFirst().orElse(null);
        if (p == null) {
            throw new Exception("Paciente no existe.");
        }
        return p;
    }

    public void updatePaciente(Paciente p) throws Exception {
        Paciente pacienteActual;
        try {
            // 1. Busca en la lista en memoria
            pacienteActual = this.readPaciente(p.getId());
        } catch (Exception e) {
            throw new Exception("Paciente a modificar no existe.");
        }

        // 2. Modifica el objeto que ya está en la lista en memoria
        pacienteActual.setNombre(p.getNombre());
        pacienteActual.setTelefono(p.getTelefono());
        pacienteActual.setFechaNacimiento(p.getFechaNacimiento());

        // 3. Guarda la lista completa y actualizada en el archivo
        dataManagerPacientes.guardarPacientes(listaPacientes);
    }

    public void deletePaciente(String id) throws Exception {
        // 1. readPaciente valida que exista y lo devuelve
        Paciente p = this.readPaciente(id);

        // 2. Modifica la lista en memoria
        listaPacientes.remove(p);

        // 3. Guarda la lista actualizada en el archivo
        dataManagerPacientes.guardarPacientes(listaPacientes);
    }
    public List<Paciente> getPacientes() {
        // Devuelve la lista completa de pacientes que está en memoria
        return listaPacientes;
    }

    public List<Paciente> searchPacientes(String filtro) {
        // Busca en la lista en memoria por cédula o por nombre
        return listaPacientes.stream()
                .filter(p -> p.getId().contains(filtro) || p.getNombre().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }
    // === MÉTODOS CRUD COMPLETOS PARA MÉDICOS ===

    public void createMedico(Medico m) throws Exception {
        if (listaUsuarios.stream().anyMatch(u -> u.getId().equals(m.getId()))) {
            throw new Exception("La cédula del usuario ya existe.");
        }
        listaUsuarios.add(m);
        dataManagerUsuarios.guardarUsuarios(listaUsuarios);
    }

    // === MÉTODO 'READ' AÑADIDO ===
    public Medico readMedico(String id) throws Exception {
        Usuario u = listaUsuarios.stream()
                .filter(us -> us.getId().equals(id) && us instanceof Medico)
                .findFirst().orElse(null);
        if (u == null) {
            throw new Exception("Médico no existe.");
        }
        return (Medico) u;
    }

    // === MÉTODO 'UPDATE' AÑADIDO ===
    public void updateMedico(Medico m) throws Exception {
        Medico medActual = this.readMedico(m.getId());
        medActual.setNombre(m.getNombre());
        medActual.setEspecialidad(m.getEspecialidad());
        // La clave se actualiza con la funcionalidad de "cambiar clave"
        dataManagerUsuarios.guardarUsuarios(listaUsuarios);
    }

    // === MÉTODO 'DELETE' AÑADIDO ===
    public void deleteMedico(String id) throws Exception {
        Usuario u = this.readMedico(id);
        listaUsuarios.remove(u);
        dataManagerUsuarios.guardarUsuarios(listaUsuarios);
    }

    public List<Medico> searchMedicos(String filtro) {
        return listaUsuarios.stream()
                .filter(u -> u instanceof Medico)
                .map(u -> (Medico) u)
                .filter(m -> m.getId().contains(filtro) || m.getNombre().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }

    // === MÉTODO 'GET' AÑADIDO ===
    public List<Medico> getMedicos() {
        return listaUsuarios.stream()
                .filter(u -> u instanceof Medico)
                .map(u -> (Medico) u)
                .collect(Collectors.toList());
    }

    // === MÉTODOS CRUD COMPLETOS PARA FARMACEUTAS ===

    public void createFarmaceuta(Farmaceuta f) throws Exception {
        if (listaUsuarios.stream().anyMatch(u -> u.getId().equals(f.getId()))) {
            throw new Exception("La cédula del usuario ya existe.");
        }
        listaUsuarios.add(f);
        dataManagerUsuarios.guardarUsuarios(listaUsuarios);
    }

    public Farmaceuta readFarmaceuta(String id) throws Exception {
        Usuario u = listaUsuarios.stream()
                .filter(us -> us.getId().equals(id) && us instanceof Farmaceuta)
                .findFirst().orElse(null);
        if (u == null) {
            throw new Exception("Farmaceuta no existe.");
        }
        return (Farmaceuta) u;
    }

    public void updateFarmaceuta(Farmaceuta f) throws Exception {
        Farmaceuta farmaceutaActual = this.readFarmaceuta(f.getId());
        farmaceutaActual.setNombre(f.getNombre());
        // La clave se actualiza con la funcionalidad de "cambiar clave"
        dataManagerUsuarios.guardarUsuarios(listaUsuarios);
    }

    public void deleteFarmaceuta(String id) throws Exception {
        Usuario u = this.readFarmaceuta(id);
        listaUsuarios.remove(u);
        dataManagerUsuarios.guardarUsuarios(listaUsuarios);
    }

    public List<Farmaceuta> searchFarmaceutas(String filtro) {
        return listaUsuarios.stream()
                .filter(u -> u instanceof Farmaceuta)
                .map(u -> (Farmaceuta) u)
                .filter(f -> f.getId().contains(filtro) || f.getNombre().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }
    public List<Farmaceuta> getFarmaceutas() {
        return listaUsuarios.stream()
                .filter(u -> u instanceof Farmaceuta)
                .map(u -> (Farmaceuta) u)
                .collect(Collectors.toList());
    }

}