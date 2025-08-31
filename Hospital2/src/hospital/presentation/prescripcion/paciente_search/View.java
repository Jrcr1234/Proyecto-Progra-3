package hospital.presentation.prescripcion.paciente_search;

import hospital.logic.Paciente;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JComboBox<String> filtroCmb;
    private JTextField searchFld;
    private JTable pacientesTbl;
    private JButton cancelarBtn;
    private JButton seleccionarBtn;

    private Controller controller;
    private Model model;
    private TableModel tableModel;

    public View() {
        // ESTE CONSTRUCTOR AHORA SE QUEDA VACÍO A PROPÓSITO
    }

    public void init() {
        // TODA LA LÓGICA DEL CONSTRUCTOR SE MUEVE AQUÍ
        filtroCmb.setModel(new DefaultComboBoxModel<>(new String[]{"Nombre", "Cédula"}));

        seleccionarBtn.addActionListener(e -> controller.seleccionar());
        cancelarBtn.addActionListener(e -> controller.cancelar());

        // Lógica de búsqueda automática
        searchFld.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { controller.buscar(); }
            public void removeUpdate(DocumentEvent e) { controller.buscar(); }
            public void changedUpdate(DocumentEvent e) {}
        });

        filtroCmb.addActionListener(e -> controller.buscar());
    }

    public void setController(Controller controller) { this.controller = controller; }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        // Inicializamos la tabla aquí, una vez que tenemos el modelo
        tableModel = new TableModel(model.getList());
        pacientesTbl.setModel(tableModel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Model.LIST)) {
            // Actualizamos la tabla, pero ahora usando nuestro atributo 'tableModel'
            tableModel.setRows(model.getList());
            pacientesTbl.revalidate();
        }
    }

    public JPanel getPanel() { return panel; }

    public String getFiltro() { return (String) filtroCmb.getSelectedItem(); }
    public String getTerminoBusqueda() { return searchFld.getText(); }

    public Paciente getSelectedPatient() {
        int selectedRow = pacientesTbl.getSelectedRow();
        if (selectedRow >= 0) {
            return model.getList().get(selectedRow);
        }
        return null;
    }
}