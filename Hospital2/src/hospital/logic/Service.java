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

    // =======================================================
    // ===        NUEVOS ATRIBUTOS Y CONSTRUCTOR           ===
    // =======================================================
    private XmlDataManager dataManagerMedicamentos;
    private List<Medicamento> listaMedicamentos;
    // Aquí irían los data managers y listas para pacientes, etc.

    private Service() {
        try {
            // Le decimos dónde guardar el archivo XML de medicamentos
            dataManagerMedicamentos = new XmlDataManager("medicamentos.xml");
            // Cargamos la lista de medicamentos desde el XML al iniciar la aplicación
            listaMedicamentos = dataManagerMedicamentos.cargarMedicamentos();
        } catch (Exception e) {
            // Si hay un error (ej. el archivo no existe), empezamos con una lista vacía
            listaMedicamentos = new ArrayList<>();
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

    public void createMedicamento(Medicamento med) throws Exception {
        // 1. Valida la regla de negocio: que el código no esté repetido
        if (listaMedicamentos.stream().anyMatch(m -> m.getCodigo().equals(med.getCodigo()))) {
            throw new Exception("El código del medicamento ya existe.");
        }
        // 2. Agrega el nuevo medicamento a la lista en memoria
        listaMedicamentos.add(med);
        // 3. Guarda la lista completa y actualizada en el archivo XML
        dataManagerMedicamentos.guardarMedicamentos(listaMedicamentos);
    }

    public List<Medicamento> searchMedicamentos(String filtro) {
        // Busca por código o nombre (insensible a mayúsculas) y devuelve una lista con los resultados
        return listaMedicamentos.stream()
                .filter(m -> m.getCodigo().contains(filtro) || m.getNombre().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Medicamento> getMedicamentos() {
        // Devuelve la lista completa de medicamentos
        return listaMedicamentos;
    }

    public void updateMedicamento(Medicamento med) throws Exception {
        Medicamento medActual = this.searchMedicamentos(med.getCodigo()).stream().findFirst().orElse(null);
        if (medActual == null) {
            throw new Exception("Medicamento no existe.");
        }
        // Actualizamos los datos del objeto que ya está en la lista
        medActual.setNombre(med.getNombre());
        medActual.setPresentacion(med.getPresentacion());
        // Guardamos la lista completa con los cambios
        dataManagerMedicamentos.guardarMedicamentos(listaMedicamentos);
    }

    public void deleteMedicamento(String codigo) throws Exception {
        Medicamento med = this.searchMedicamentos(codigo).stream().findFirst().orElse(null);
        if (med == null) {
            throw new Exception("Medicamento no existe.");
        }
        listaMedicamentos.remove(med);
        dataManagerMedicamentos.guardarMedicamentos(listaMedicamentos);
    }
}