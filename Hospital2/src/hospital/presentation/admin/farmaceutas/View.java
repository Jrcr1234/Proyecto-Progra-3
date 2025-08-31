package hospital.presentation.admin.farmaceutas;

import hospital.logic.Farmaceuta;
import javax.swing.SwingUtilities;
import hospital.presentation.util.GuiUtils;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.ListSelectionEvent;


public class View implements PropertyChangeListener {
    private JPanel panel;
    private JPanel formularioPanel;
    private JPanel busquedaPanel;
    private JPanel tablaPanel;
    private JTextField idFld;
    private JTextField nombreFld;
    private JButton guardarBtn;
    private JButton limpiarBtn;
    private JButton borrarBtn;
    private JTextField buscarFld;
    private JButton buscarBtn;
    private JTable farmaceutasTbl;

    // --- Variables para el MVC ---
    private Controller controller;
    private Model model;
    private TableModel farmaceutasTableModel;

    public View() {
        // === CÓDIGO PARA AÑADIR ICONOS A LOS BOTONES ===
        try {
            int iconSize = 24;
            guardarBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/guardar.png")), iconSize, iconSize));
            borrarBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/Cancelar.png")), iconSize, iconSize));
            limpiarBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/limpiar.png")), iconSize, iconSize));
            buscarBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/buscar.png")), iconSize, iconSize));
        } catch (Exception e) {
            System.err.println("Error al cargar los iconos de los botones: " + e.getMessage());
        }
        // =======================================================
        // ===       AÑADIMOS LOS ACTION LISTENERS             ===
        // =======================================================
        guardarBtn.addActionListener(e -> {
            Farmaceuta farmaceuta = new Farmaceuta();
            farmaceuta.setId(idFld.getText());
            farmaceuta.setNombre(nombreFld.getText());
            controller.guardar(farmaceuta);
        });

        borrarBtn.addActionListener(e -> {
            int selectedRow = farmaceutasTbl.getSelectedRow();
            if (selectedRow != -1) {
                controller.borrar(selectedRow);
            }
        });

        limpiarBtn.addActionListener(e -> controller.limpiar());
        buscarBtn.addActionListener(e -> controller.buscar(buscarFld.getText()));

        // --- Listener para la selección de la tabla ---
        farmaceutasTbl.getSelectionModel().addListSelectionListener((ListSelectionEvent ev) -> {
            if (!ev.getValueIsAdjusting()) {
                int selectedRow = farmaceutasTbl.getSelectedRow();
                if (selectedRow >= 0) {
                    controller.editar(selectedRow);
                }
            }
        });
    }

    public JPanel getPanel() { return panel; }
    public void setController(Controller controller) { this.controller = controller; }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this); // La Vista se suscribe a los cambios

        // Inicializamos la tabla una vez que tenemos el modelo
        farmaceutasTableModel = new TableModel(model.getList());
        farmaceutasTbl.setModel(farmaceutasTableModel);
    }

    // =======================================================
    // ===   AÑADIMOS EL MÉTODO propertyChange             ===
    // =======================================================
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
            Farmaceuta farmaceuta = model.getCurrent();
            idFld.setText(farmaceuta.getId());
            nombreFld.setText(farmaceuta.getNombre());

            boolean isNew = farmaceuta.getId().isEmpty();
            guardarBtn.setEnabled(true);
            borrarBtn.setEnabled(!isNew);
            idFld.setEditable(isNew);
        }
    }
}
