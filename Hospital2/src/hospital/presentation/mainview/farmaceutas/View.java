package hospital.presentation.mainview.farmaceutas;

import hospital.logic.Farmaceuta;
import hospital.presentation.util.GuiUtils;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.SwingUtilities;

public class View implements PropertyChangeListener {
    // --- Atributos de la Interfaz ---
    private JPanel panel;
    private JPanel formularioPanel;
    private JPanel busquedaPanel;
    private JPanel tablaPanel;
    private JTable farmaceutasTbl;
    private JTextField idFld;
    private JTextField nombreFld;
    private JButton guardarBtn;
    private JButton borrarBtn;
    private JButton limpiarBtn;
    private JButton buscarBtn;
    private JTextField buscarFld;

    // --- Variables para el MVC ---
    private Controller controller;
    private Model model;
    private TableModel farmaceutasTableModel;

    public View() {
        // El constructor se deja vacío a propósito para evitar errores de inicialización.
    }

    // Este método es llamado por el Controller DESPUÉS de que todo está conectado.
    public void init() {
        // --- ActionListeners para los botones ---
        guardarBtn.addActionListener(e -> {
            Farmaceuta f = new Farmaceuta();
            f.setId(idFld.getText());
            f.setNombre(nombreFld.getText());
            controller.guardar(f);
        });

        borrarBtn.addActionListener(e -> {
            int selectedRow = farmaceutasTbl.getSelectedRow();
            if (selectedRow != -1) controller.borrar(selectedRow);
        });

        limpiarBtn.addActionListener(e -> controller.limpiar());
        buscarBtn.addActionListener(e -> controller.buscar(buscarFld.getText()));

        // --- Listener para la selección de la tabla ---
        farmaceutasTbl.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = farmaceutasTbl.getSelectedRow();
                if (selectedRow >= 0) controller.editar(selectedRow);
            }
        });

        // --- Código para los iconos ---
        try {
            int iconSize = 24;
            guardarBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/guardar.png")), iconSize, iconSize));
            borrarBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/Cancelar.png")), iconSize, iconSize));
            limpiarBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/limpiar.png")), iconSize, iconSize));
            buscarBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/buscar.png")), iconSize, iconSize));
        } catch (Exception e) { System.err.println("Error al cargar iconos: " + e.getMessage()); }
    }

    public JPanel getPanel() { return panel; }

    public void setController(Controller controller) { this.controller = controller; }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        farmaceutasTableModel = new TableModel(model.getList());
        farmaceutasTbl.setModel(farmaceutasTableModel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Model.LIST)) {
            SwingUtilities.invokeLater(() -> {
                farmaceutasTableModel.setRows(model.getList());
                panel.revalidate();
                panel.repaint();
            });
        }
        if (evt.getPropertyName().equals(Model.CURRENT)) {
            Farmaceuta f = model.getCurrent();
            idFld.setText(f.getId());
            nombreFld.setText(f.getNombre());
            boolean isNew = f.getId().isEmpty();
            guardarBtn.setEnabled(true);
            borrarBtn.setEnabled(!isNew);
            idFld.setEditable(isNew);
        }
    }
}