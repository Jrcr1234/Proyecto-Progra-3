package hospital.presentation.prescripcion.medicamento_add;

import hospital.logic.Medicamento;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JComboBox<String> filtroCmb;
    private JTextField searchFld;
    private JTable medicamentosTbl;
    private JButton cancelarBtn;
    private JButton seleccionarBtn;

    private JTextField cantidadFld;
    private JTextArea indicacionesFld;
    private JTextField duracionFld;
    private JButton agregarBtn;

    private Controller controller;
    private Model model;
    private TableModel tableModel;


    public View() {
        // Constructor vacío a propósito
    }

    public void init(){
        filtroCmb.setModel(new DefaultComboBoxModel<>(new String[]{"Nombre", "Código"}));

        // El botón 'Seleccionar' ahora se encarga de la acción
        seleccionarBtn.addActionListener(e -> controller.seleccionar());

        cancelarBtn.addActionListener(e -> controller.cancelar());

        // Lógica de búsqueda automática al escribir o cambiar el filtro
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { controller.buscar(); }
            public void removeUpdate(DocumentEvent e) { controller.buscar(); }
            public void changedUpdate(DocumentEvent e) {}
        };
        searchFld.getDocument().addDocumentListener(listener);
        filtroCmb.addActionListener(e -> controller.buscar());
    }

    public void setController(Controller controller) { this.controller = controller; }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        tableModel = new TableModel(model.getList());
        medicamentosTbl.setModel(tableModel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Model.LIST)) {
            tableModel.setRows(model.getList());
            medicamentosTbl.revalidate();
        }
    }

    // --- Métodos para que el Controller interactúe con la Vista ---
    public JPanel getPanel() { return panel; }
    public String getFiltro() { return (String) filtroCmb.getSelectedItem(); }
    public String getTerminoBusqueda() { return searchFld.getText(); }

    // Métodos para que el Controller obtenga los datos de la dosis
    public int getCantidad() {
        // Convertimos el texto a un número entero
        return Integer.parseInt(cantidadFld.getText());
    }

    public String getIndicaciones() {
        return indicacionesFld.getText();
    }

    public int getDuracion() {
        return Integer.parseInt(duracionFld.getText());
    }

    // Método para que el Controller pueda mostrar errores
    public void mostrarError(String msg) {
        JOptionPane.showMessageDialog(panel, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public Medicamento getSelected() {
        int selectedRow = medicamentosTbl.getSelectedRow();
        if (selectedRow >= 0) {
            return model.getList().get(selectedRow);
        }
        return null;
    }
}