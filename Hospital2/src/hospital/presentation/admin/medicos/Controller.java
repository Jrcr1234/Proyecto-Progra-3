package hospital.presentation.admin.medicos;

// Importamos las clases View y Model de ESTE MISMO paquete
import hospital.presentation.admin.medicos.View;
import hospital.presentation.admin.medicos.Model;

public class Controller {
    private View view;
    private Model model;

    // --- EL CONSTRUCTOR QUE FALTABA ---
    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        // La lógica para enlazar el trío MVC irá aquí más adelante
    }
}