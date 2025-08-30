package hospital.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Farmaceuta extends Usuario {

    public Farmaceuta() {
        super("", "", "", "Farmaceuta");
    }

    public Farmaceuta(String id, String clave, String nombre) {
        super(id, clave, nombre, "Farmaceuta");
    }
}