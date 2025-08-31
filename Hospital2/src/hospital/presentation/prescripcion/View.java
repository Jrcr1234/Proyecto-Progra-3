package hospital.presentation.prescripcion;

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
        buscarPacienteBtn.addActionListener(e -> controller.buscarPaciente());
        agregarMedicamentoBtn.addActionListener(e -> controller.agregarMedicamento());
        guardarRecetaBtn.addActionListener(e -> controller.registrarReceta());
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
            SwingUtilities.invokeLater(() -> {
                tableModel.setRows(model.getLineas());
                panel.revalidate();
                panel.repaint();
            });
        }
    }
}
