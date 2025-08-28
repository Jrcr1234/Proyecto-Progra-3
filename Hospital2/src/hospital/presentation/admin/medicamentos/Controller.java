package hospital.presentation.admin.medicamentos;

import hospital.logic.Medicamento;
import hospital.logic.Service;
import javax.swing.JOptionPane;
import java.util.List;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.model = model;
        this.view = view;
        // Carga inicial de datos desde el Service
        model.setList(Service.instance().getMedicamentos());
        model.setCurrent(new Medicamento());
        view.setModel(model);
        view.setController(this);
    }

    public void search(String filtro) {
        List<Medicamento> rows = Service.instance().searchMedicamentos(filtro);
        model.setList(rows);
    }

    public void save(Medicamento med) {
        try {
            // Llama al Service para que aplique la lógica de negocio y guarde en XML
            Service.instance().createMedicamento(med);
            // Refresca la tabla con la lista completa y actualizada
            this.search("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void edit(int row) {
        // Pone el medicamento de la fila seleccionada en el formulario para editarlo
        Medicamento med = model.getList().get(row);
        model.setCurrent(med);
    }

    public void update(Medicamento med) {
        try {
            Service.instance().updateMedicamento(med);
            this.search(""); // Refresca la tabla
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void delete(int row) {
        try {
            Medicamento med = model.getList().get(row);
            Service.instance().deleteMedicamento(med.getCodigo());
            this.search(""); // Refresca la tabla
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clear() {
        model.setCurrent(new Medicamento());
    }
}