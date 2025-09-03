package hospital.presentation.prescripcion;

import hospital.presentation.util.GuiUtils;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import javax.swing.SwingUtilities;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JPanel controlPanel;
    private JButton buscarPacienteBtn;
    private JButton agregarMedicamentoBtn;
    private JPanel recetaPanel;
    private JPanel infoPanel;
    private JLabel pacienteLbl;
    private JTable medicamentosTbl;
    private JPanel ajustePanel;
    private JButton guardarRecetaBtn;
    private JButton limpiarBtn;
    private JButton descartarMedBtn;
    private JButton detallesBtn;
    private JPanel dateChooserPanelPrescripcion;
    private com.toedter.calendar.JDateChooser fechaRetiroFld;

    private Controller controller;
    private Model model;
    private TableModel tableModel;

    public View() {}

    public void init() {

        // === CÓDIGO PARA AÑADIR ICONOS A LOS BOTONES ===
        try {
            int iconSize = 24;


            ImageIcon buscarPacienteIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/buscarPaciente.png")), iconSize, iconSize);
            buscarPacienteBtn.setIcon(buscarPacienteIcon);

            ImageIcon agregarMedicamentoIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/agregar.png")), iconSize, iconSize);
            agregarMedicamentoBtn.setIcon(agregarMedicamentoIcon);

            ImageIcon limpiarIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/limpiar.png")), iconSize, iconSize);
            limpiarBtn.setIcon(limpiarIcon);

            ImageIcon guardarIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/guardar.png")), iconSize, iconSize);
            guardarRecetaBtn.setIcon(guardarIcon);

            ImageIcon descartarMedIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/descartar.png")), iconSize, iconSize);
            descartarMedBtn.setIcon(descartarMedIcon);

            ImageIcon detallesIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/detalles.png")), iconSize, iconSize);
            detallesBtn.setIcon(detallesIcon);

        } catch (Exception e) {
            System.err.println("Error al cargar los iconos de los botones: " + e.getMessage());
        }
        // =======================================================
        buscarPacienteBtn.addActionListener(e -> controller.buscarPaciente());
        agregarMedicamentoBtn.addActionListener(e -> controller.agregarMedicamento());
        guardarRecetaBtn.addActionListener(e -> controller.registrarReceta());
        descartarMedBtn.addActionListener(e -> {
            int selectedRow = medicamentosTbl.getSelectedRow();
            controller.eliminarMedicamento(selectedRow);
        });
        detallesBtn.addActionListener(e -> {
            int selectedRow = medicamentosTbl.getSelectedRow();
            controller.modificarMedicamento(selectedRow);
        });
        limpiarBtn.addActionListener(e -> controller.limpiar());
        pacienteLbl.setText("Paciente: (Ninguno seleccionado)");

        // === CÓDIGO PARA CREAR Y AÑADIR EL JDateChooser ===
        try {
            this.fechaRetiroFld = new com.toedter.calendar.JDateChooser();
            this.dateChooserPanelPrescripcion.setLayout(new java.awt.BorderLayout());
            this.dateChooserPanelPrescripcion.add(this.fechaRetiroFld, java.awt.BorderLayout.CENTER);
        } catch (Exception e) {
            System.err.println("Error al crear el JDateChooser: " + e.getMessage());
        }
        // =============================================================
    }

    public JPanel getPanel() { return panel; }
    public void setController(Controller controller) { this.controller = controller; }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        tableModel = new TableModel(model.getLineas());
        medicamentosTbl.setModel(tableModel);
    }

    public Date getFechaRetiro() {
        return fechaRetiroFld.getDate();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Model.PACIENTE)) {
            if (model.getPaciente() != null) {
                pacienteLbl.setText("Paciente: " + model.getPaciente().getNombre());
            } else {
                pacienteLbl.setText("Paciente: (Ninguno seleccionado)");
            }
        }
        if (evt.getPropertyName().equals(Model.LINEAS)) {
                tableModel.setRows(model.getLineas());
        }
    }
}
