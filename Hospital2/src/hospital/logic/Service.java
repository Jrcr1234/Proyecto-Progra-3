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
    // Aquí irían los data managers y listas para medicos, etc.

    private Service() {
        try {
            // Le decimos dónde guardar el archivo XML de medicamentos
            dataManagerMedicamentos = new XmlDataManager("medicamentos.xml");
            dataManagerPacientes = new XmlDataManager("pacientes.xml");

            // Cargamos la lista de medicamentos desde el XML al iniciar la aplicación
            listaMedicamentos = dataManagerMedicamentos.cargarMedicamentos();
            listaPacientes = dataManagerPacientes.cargarPacientes();
        } catch (Exception e) {
            // Si hay un error (ej. el archivo no existe), empezamos con una lista vacía
            listaMedicamentos = new ArrayList<>();
            listaPacientes = new ArrayList<>();
        }
    }

    // --- MÉTODOS DE USUARIO  ---
    public Usuario autenticar(String id, String clave) throws Exception {
        if (id.equals("admin") && clave.equals("admin")) {
            return new Usuario(id, clave, "Administrador");
        } else { // <-- Este 'else' es crucial
            throw new Exception("Usuario o clave incorrectos");
        }
    }
    public void cambiarClave(Usuario usuario, String claveActual, String claveNueva) throws Exception {
        // ...
    }
    public void cambiarClave(String id, String claveActual, String claveNueva) throws Exception {
        // ...
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
}