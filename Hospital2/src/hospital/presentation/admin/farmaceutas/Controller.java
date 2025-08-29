package hospital.presentation.admin.farmaceutas;

// Importamos las clases View y Model de ESTE MISMO paquete
import hospital.presentation.admin.farmaceutas.View;
import hospital.presentation.admin.farmaceutas.Model;

public class Controller {
    private View view;
    private Model model;

    // --- EL CONSTRUCTOR  ---
    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        // La lógica para enlazar el trío MVC irá aquí más adelante
    }
}