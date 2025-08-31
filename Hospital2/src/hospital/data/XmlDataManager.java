package hospital.data;

import hospital.logic.Medicamento;
import hospital.logic.Paciente;
import hospital.logic.Usuario;
import hospital.logic.Receta;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "lista-medicamentos")
class MedicamentosWrapper {
    private List<Medicamento> medicamentos;

    public MedicamentosWrapper() {
        medicamentos = new ArrayList<>();
    }

    @XmlElement(name = "medicamento")
    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }
}

// ===NUEVA CLASE WRAPPER PARA PACIENTES ===
@XmlRootElement(name = "lista-pacientes")
class PacientesWrapper {
    private List<Paciente> pacientes;

    public PacientesWrapper() { pacientes = new ArrayList<>(); }

    @XmlElement(name = "paciente")
    public List<Paciente> getPacientes() { return pacientes; }
    public void setPacientes(List<Paciente> pacientes) { this.pacientes = pacientes; }
}
// =======================================================

// === NUEVO WRAPPER PARA USUARIOS ===
@XmlRootElement(name = "lista-usuarios")
class UsuariosWrapper {
    private List<Usuario> usuarios;
    public UsuariosWrapper() { usuarios = new ArrayList<>(); }
    @XmlElement(name = "usuario")
    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
}

// === AÑADE ESTA NUEVA CLASE WRAPPER PARA RECETAS ===
@XmlRootElement(name = "lista-recetas")
class RecetasWrapper {
    private List<Receta> recetas;

    public RecetasWrapper() {
        recetas = new ArrayList<>();
    }

    @XmlElement(name = "receta")
    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }
}
// =======================================================

public class XmlDataManager {
    private String filePath;
    private JAXBContext context;

    public XmlDataManager(String filePath) throws Exception {
        this.filePath = filePath;
        this.context = JAXBContext.newInstance(MedicamentosWrapper.class);
    }

    public List<Medicamento> cargarMedicamentos() throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>(); // Si el archivo no existe, devuelve una lista vacía
        }
        Unmarshaller unmarshaller = context.createUnmarshaller();
        MedicamentosWrapper wrapper = (MedicamentosWrapper) unmarshaller.unmarshal(file);
        return wrapper.getMedicamentos();
    }

    public void guardarMedicamentos(List<Medicamento> medicamentos) throws Exception {
        MedicamentosWrapper wrapper = new MedicamentosWrapper();
        wrapper.setMedicamentos(medicamentos);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(wrapper, new File(filePath));
    }

    // === NUEVOS MÉTODOS PARA PACIENTES ===
    public List<Paciente> cargarPacientes() throws Exception {
        JAXBContext context = JAXBContext.newInstance(PacientesWrapper.class);
        File file = new File("pacientes.xml");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PacientesWrapper wrapper = (PacientesWrapper) unmarshaller.unmarshal(file);
        return wrapper.getPacientes();
    }

    public void guardarPacientes(List<Paciente> pacientes) throws Exception {
        JAXBContext context = JAXBContext.newInstance(PacientesWrapper.class);
        PacientesWrapper wrapper = new PacientesWrapper();
        wrapper.setPacientes(pacientes);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(wrapper, new File("pacientes.xml"));
    }
    // ========================================================

    // === NUEVOS MÉTODOS PARA USUARIOS ===
    public List<Usuario> cargarUsuarios() throws Exception {
        JAXBContext context = JAXBContext.newInstance(UsuariosWrapper.class);
        File file = new File("usuarios.xml");
        if (!file.exists()) return new ArrayList<>();
        Unmarshaller unmarshaller = context.createUnmarshaller();
        UsuariosWrapper wrapper = (UsuariosWrapper) unmarshaller.unmarshal(file);
        return wrapper.getUsuarios();
    }

    public void guardarUsuarios(List<Usuario> usuarios) throws Exception {
        JAXBContext context = JAXBContext.newInstance(UsuariosWrapper.class);
        UsuariosWrapper wrapper = new UsuariosWrapper();
        wrapper.setUsuarios(usuarios);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(wrapper, new File("usuarios.xml"));
    }

    // === NUEVOS MÉTODOS PARA RECETAS ===
    public List<Receta> cargarRecetas() throws Exception {
        JAXBContext context = JAXBContext.newInstance(RecetasWrapper.class);
        File file = new File("recetas.xml");
        if (!file.exists()) {
            return new ArrayList<>(); // Si no hay archivo, devuelve una lista vacía
        }
        Unmarshaller unmarshaller = context.createUnmarshaller();
        RecetasWrapper wrapper = (RecetasWrapper) unmarshaller.unmarshal(file);
        return wrapper.getRecetas();
    }

    public void guardarRecetas(List<Receta> recetas) throws Exception {
        JAXBContext context = JAXBContext.newInstance(RecetasWrapper.class);
        RecetasWrapper wrapper = new RecetasWrapper();
        wrapper.setRecetas(recetas);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(wrapper, new File("recetas.xml"));
    }
    // ========================================================
}
