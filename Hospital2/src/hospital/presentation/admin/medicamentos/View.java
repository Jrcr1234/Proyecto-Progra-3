package hospital.presentation.admin.medicamentos;

import hospital.logic.Medicamento;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.event.ListSelectionEvent;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JTable medicamentosTbl;
    private JPanel formularioPanel;
    private JPanel busquedaPanel;
    private JPanel tablaPanel;
    private JTextField codigoFld;
    private JTextField nombreFld;
    private JTextField presentacionFld;
    private JButton guardarBtn;
    private JButton borrarBtn;
    private JButton limpiarBtn;
    private JButton buscarBtn;
    private JTextField buscarFld;

    private Controller controller;
    private Model model;

    public View() {
        // === CÓDIGO PARA AÑADIR ICONOS A LOS BOTONES ===
        try {
            int iconSize = 24;


            ImageIcon guardarIcon = scaleIcon(new ImageIcon(getClass().getResource("/icons/guardar.png")), iconSize, iconSize);
            guardarBtn.setIcon(guardarIcon);

            ImageIcon borrarIcon = scaleIcon(new ImageIcon(getClass().getResource("/icons/Cancelar.png")), iconSize, iconSize);
            borrarBtn.setIcon(borrarIcon);

            ImageIcon limpiarIcon = scaleIcon(new ImageIcon(getClass().getResource("/icons/limpiar.png")), iconSize, iconSize);
            limpiarBtn.setIcon(limpiarIcon);

            ImageIcon buscarIcon = scaleIcon(new ImageIcon(getClass().getResource("/icons/buscar.png")), iconSize, iconSize);
            buscarBtn.setIcon(buscarIcon);

        } catch (Exception e) {
            System.err.println("Error al cargar los iconos de los botones: " + e.getMessage());
        }
// =======================================================


        // --- ActionListeners para los botones ---
        guardarBtn.addActionListener(e -> {
            Medicamento med = new Medicamento();
            med.setCodigo(codigoFld.getText());
            med.setNombre(nombreFld.getText());
            med.setPresentacion(presentacionFld.getText());
            controller.save(med); // Llama al controller para que decida si guardar o modificar
        });

        borrarBtn.addActionListener(e -> {
            int selectedRow = medicamentosTbl.getSelectedRow();
            if (selectedRow >= 0) {
                controller.delete(selectedRow);
            }
        });

        buscarBtn.addActionListener(e -> controller.search(buscarFld.getText()));
        limpiarBtn.addActionListener(e -> controller.clear());

        // --- Listener para la selección de la tabla ---
        medicamentosTbl.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = medicamentosTbl.getSelectedRow();
                if (selectedRow >= 0) {
                    controller.edit(selectedRow);
                }
            }
        });
    }

    public void setController(Controller controller) { this.controller = controller; }
    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    // --- propertyChange (Corregido para usar guardarBtn) ---
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Model.LIST)) {
            medicamentosTbl.setModel(new TableModel(model.getList()));
        }
        if (evt.getPropertyName().equals(Model.CURRENT)) {
            codigoFld.setText(model.getCurrent().getCodigo());
            nombreFld.setText(model.getCurrent().getNombre());
            presentacionFld.setText(model.getCurrent().getPresentacion());

            boolean isNew = model.getCurrent().getCodigo().isEmpty();
            guardarBtn.setEnabled(true); // Usamos el nombre correcto
            borrarBtn.setEnabled(!isNew);  // Usamos el nombre correcto
            codigoFld.setEditable(isNew);

            // Borramos la referencia a modificarBtn que no existe
        }
        panel.revalidate();
    }

    // === MÉTODO 'scaleIcon' QUE FALTABA ===
    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    public JPanel getPanel() { return panel; }
}
