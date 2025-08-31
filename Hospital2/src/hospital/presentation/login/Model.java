package hospital.presentation.login;

import hospital.logic.Usuario;
import hospital.presentation.common.AbstractModel;

public class Model extends AbstractModel {
    private Usuario current;

    public Model() {
        this.current = new Usuario() {}; // Instancia anónima de la clase abstracta
    }

    public Usuario getCurrent() { return current; }
    public void setCurrent(Usuario current) { this.current = current; }
}