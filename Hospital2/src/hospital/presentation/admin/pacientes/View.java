package hospital.presentation.admin.pacientes;

import hospital.logic.Paciente;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import hospital.presentation.util.GuiUtils;

public class View implements PropertyChangeListener {
    // --- Atributos con nombres estandarizados ---
    private JPanel panel;
    private JPanel formularioPanel;
    private JPanel busquedaPanel;
    private JPanel tablaPanel;
    private JTable pacientesTbl;
    private JTextField idFld;
    private JTextField nombreFld;
    private JTextField telefonoFld;
    private JPanel dateChooserPanel;
    // JDateChooser para la fecha
    private com.toedter.calendar.JDateChooser fechaNacimientoFld;
    private JButton guardarBtn;
    private JButton borrarBtn;
    private JButton buscarBtn;
    private JTextField buscarFld;
    private JButton limpiarBtn;
    private JButton reporteBtn;

    private Controller controller;
    private Model model;
    private TableModel pacientesTableModel;

    public View() {

        // === CÓDIGO PARA AÑADIR ICONOS A LOS BOTONES ===
        try {
            int iconSize = 24;


            ImageIcon guardarIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/guardar.png")), iconSize, iconSize);
            guardarBtn.setIcon(guardarIcon);

            ImageIcon borrarIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/Cancelar.png")), iconSize, iconSize);
            borrarBtn.setIcon(borrarIcon);

            ImageIcon limpiarIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/limpiar.png")), iconSize, iconSize);
            limpiarBtn.setIcon(limpiarIcon);

            ImageIcon buscarIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/buscar.png")), iconSize, iconSize);
            buscarBtn.setIcon(buscarIcon);

            ImageIcon reporteIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/reporte.png")), iconSize, iconSize);
            reporteBtn.setIcon(reporteIcon);

        } catch (Exception e) {
            System.err.println("Error al cargar los iconos de los botones: " + e.getMessage());
        }
        // =======================================================

        // --- ActionListeners Completos ---
        guardarBtn.addActionListener(e -> {
            // Recolectamos los datos del formulario
            Paciente paciente = new Paciente();
            paciente.setId(idFld.getText());
            paciente.setNombre(nombreFld.getText());
            paciente.setTelefono(telefonoFld.getText());
            paciente.setFechaNacimiento(fechaNacimientoFld.getDate());

            // Llamamos al controller CON el paciente
            controller.guardarPaciente(paciente);
        });

        borrarBtn.addActionListener(e -> {
            int filaSeleccionada = pacientesTbl.getSelectedRow();
            if (filaSeleccionada != -1) {
                controller.borrarPaciente(filaSeleccionada);
            }
        });

        buscarBtn.addActionListener(e -> {
            // Llamamos al controller CON el filtro de búsqueda
            controller.buscarPaciente(buscarFld.getText());
        });

        limpiarBtn.addActionListener(e -> controller.clear());

        // --- Listener para la selección de la tabla ---
        pacientesTbl.getSelectionModel().addListSelectionListener((ListSelectionEvent ev) -> {
            if (!ev.getValueIsAdjusting()) {
                int selectedRow = pacientesTbl.getSelectedRow();
                if (selectedRow >= 0) {
                    controller.edit(selectedRow); // Avisa al controller qué fila se seleccionó
                }
            }
        });

        // === CÓDIGO PARA CREAR Y AÑADIR EL JDateChooser MANUALMENTE ===
        fechaNacimientoFld = new com.toedter.calendar.JDateChooser();
        dateChooserPanel.setLayout(new BorderLayout()); // Asegura el layout
        dateChooserPanel.add(fechaNacimientoFld, BorderLayout.CENTER);
        // ==============================================================
    }

    public void setController(Controller controller) { this.controller = controller; }
    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this); // La vista se suscribe a futuros cambios

        // AHORA QUE TENEMOS EL MODELO, INICIALIZAMOS LA TABLA
        this.pacientesTableModel = new TableModel(model.getList());
        this.pacientesTbl.setModel(pacientesTableModel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Actualiza la JTable cuando la lista en el modelo cambia
        if (evt.getPropertyName().equals(Model.LIST)) {
            pacientesTableModel.setRows(model.getList());
        }
        // Actualiza el formulario cuando el 'current' en el modelo cambia
        if (evt.getPropertyName().equals(Model.CURRENT)) {
            Paciente p = model.getCurrent();
            idFld.setText(p.getId());
            nombreFld.setText(p.getNombre());
            telefonoFld.setText(p.getTelefono());
            fechaNacimientoFld.setDate(p.getFechaNacimiento());

            // Habilita/deshabilita el botón y el campo de ID
            boolean isNew = p.getId().isEmpty();
            guardarBtn.setEnabled(true);

            borrarBtn.setEnabled(!isNew);
            idFld.setEditable(isNew);
        }

        // === LÍNEA AÑADIDA PARA FORZAR EL REFRESCO VISUAL ===
        panel.revalidate();
        panel.repaint();
        // ===================================================
    }


    public JPanel getPanel() { return panel; }
}