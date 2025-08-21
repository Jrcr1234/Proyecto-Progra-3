package hospital.presentation.login;
import hospital.logic.Usuario;

public class Model { // Para este caso simple, no necesita extender AbstractModel
    private Usuario loggedUser;

    public Model() {
        this.loggedUser = null; // Al inicio, no hay nadie logueado
    }

    public Usuario getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
    }
}