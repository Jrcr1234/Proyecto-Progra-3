package hospital.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "administrador")
@XmlAccessorType(XmlAccessType.FIELD)
public class Administrador extends Usuario {

    public Administrador() {
        super("", "", "", "Administrador");
    }

    public Administrador(String id, String clave, String nombre) {
        super(id, clave, nombre, "Administrador");
    }
}