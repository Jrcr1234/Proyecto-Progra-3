package hospital.data;

import hospital.logic.Medicamento;
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
}