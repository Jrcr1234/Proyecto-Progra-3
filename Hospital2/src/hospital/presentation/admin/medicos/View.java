package hospital.presentation.admin.medicos;

import hospital.logic.Medico;
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
    private JTextField especialidadFld;
    private JTextField nombreFld;
    private JButton limpiarBtn;
    private JButton guardarBtn;
    private JButton borrarBtn;
    private JTextField buscarFld;
    private JButton reporteBtn;
    private JButton buscarBtn;
    private JTable medicosTbl;

    private Controller controller;
    private Model model;
    private TableModel medicosTableModel;

    public View() {

        // === CÓDIGO DE DIAGNÓSTICO TEMPORAL ===
        System.out.println("--- Verificando rutas de iconos para Médicos ---");
        System.out.println("Buscando /icons/guardar.png:  " + getClass().getResource("/icons/guardar.png"));
        System.out.println("Buscando /icons/Cancelar.png: " + getClass().getResource("/icons/Cancelar.png"));
        System.out.println("Buscando /icons/limpiar.png: " + getClass().getResource("/icons/limpiar.png"));
        System.out.println("Buscando /icons/buscar.png:  " + getClass().getResource("/icons/buscar.png"));
        System.out.println("Buscando /icons/reporte.png: " + getClass().getResource("/icons/reporte.png"));
        System.out.println("----------------------------------------------");
        // =======================================
        // === CÓDIGO PARA AÑADIR ICONOS A LOS BOTONES ===
        try {
            int iconSize = 24;
            guardarBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/guardar.png")), iconSize, iconSize));
            borrarBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/Cancelar.png")), iconSize, iconSize));
            limpiarBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/limpiar.png")), iconSize, iconSize));
            buscarBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/buscar.png")), iconSize, iconSize));
            reporteBtn.setIcon(GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/reporte.png")), iconSize, iconSize));
        } catch (Exception e) {
            System.err.println("Error al cargar los iconos de los botones: " + e.getMessage());
        }

        // =======================================================
        // ===       LOS ACTION LISTENERS AHORA ESTÁN AQUÍ     ===
        // =======================================================
        guardarBtn.addActionListener(e -> {
            Medico medico = new Medico();
            medico.setId(idFld.getText());
            medico.setNombre(nombreFld.getText());
            medico.setEspecialidad(especialidadFld.getText());
            // Si es un médico nuevo, la clave por defecto será su ID. El Controller se encargará de esto.
            controller.guardar(medico);
        });

        borrarBtn.addActionListener(e -> {
            int selectedRow = medicosTbl.getSelectedRow();
            if (selectedRow != -1) {
                controller.borrar(selectedRow);
            }
        });

        limpiarBtn.addActionListener(e -> controller.limpiar());
        buscarBtn.addActionListener(e -> controller.buscar(buscarFld.getText()));
        reporteBtn.addActionListener(e -> controller.reporte());

        // --- Listener para la selección de la tabla ---
        medicosTbl.getSelectionModel().addListSelectionListener((ListSelectionEvent ev) -> {
            if (!ev.getValueIsAdjusting()) {
                int selectedRow = medicosTbl.getSelectedRow();
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
        model.addPropertyChangeListener(this);
        medicosTableModel = new TableModel(model.getList());
        medicosTbl.setModel(medicosTableModel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Model.LIST)) {
            SwingUtilities.invokeLater(() -> {
                medicosTableModel.setRows(model.getList());
                panel.revalidate();
                panel.repaint();
            });
        }
        if (evt.getPropertyName().equals(Model.CURRENT)) {
            Medico medico = model.getCurrent();
            idFld.setText(medico.getId());
            nombreFld.setText(medico.getNombre());
            especialidadFld.setText(medico.getEspecialidad());
          //  claveFld.setText("");

            boolean isNew = medico.getId().isEmpty();
            guardarBtn.setEnabled(true);
            borrarBtn.setEnabled(!isNew);
            idFld.setEditable(isNew);
        }
    }
}