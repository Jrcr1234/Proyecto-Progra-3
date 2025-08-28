package hospital.presentation.admin.medicamentos;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener; // <-- Nuevo import
import javax.swing.*;

// Ya no implementa Observer, ahora es un PropertyChangeListener
public class View implements PropertyChangeListener {
    private JPanel panel;
    private JPanel formularioPanel;
    private JPanel busquedaPanel;
    private JPanel tablaPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton borrarBtn;
    private JButton guardarBtn;
    private JLabel codigoFld;
    private JLabel nombreFld;
    private JLabel presentacionFld;
    private JButton limpiarBtn;
    private JTextField buscarFld;
    private JButton buscarBtn;
    private JTable medicamentosTbl;
    // ... otros componentes ...

    private Controller controller;
    private Model model;

    public View() {
        // ... ActionListeners de los botones ...
    }

    public void setController(Controller controller) { this.controller = controller; }

    public void setModel(Model model) {
        this.model = model;
        // La vista se suscribe usando el nuevo método
        model.addPropertyChangeListener(this);
    }

    // El método update() se reemplaza por propertyChange()
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Verificamos qué propiedad cambió
        if (evt.getPropertyName().equals(Model.LIST)) {
            // Actualizamos la tabla
            medicamentosTbl.setModel(new TableModel(model.getList()));
        }
        if (evt.getPropertyName().equals(Model.CURRENT)) {
            // Actualizamos el formulario
            codigoFld.setText(model.getCurrent().getCodigo());
            nombreFld.setText(model.getCurrent().getNombre());
            presentacionFld.setText(model.getCurrent().getPresentacion());
        }
        this.panel.revalidate();
    }

    public JPanel getPanel() { return panel; }
}