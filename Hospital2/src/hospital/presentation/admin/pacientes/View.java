package hospital.presentation.admin.pacientes;

import hospital.logic.Paciente;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class View implements PropertyChangeListener {
    // --- Atributos con nombres estandarizados ---
    private JPanel panel;
    private JTable pacientesTbl;
    private JTextField idFld;
    private JTextField nombreFld;
    private JTextField telefonoFld;
    // JDateChooser para la fecha
    // private com.toedter.calendar.JDateChooser fechaNacimientoFld;
    private JButton guardarBtn;
    private JButton borrarBtn;
    private JButton buscarBtn;
    private JTextField buscarFld;
    private JButton reporteBtn;
    private JButton limpiarBtn;

    private Controller controller;
    private Model model;

    public View() {
        // --- ActionListeners Completos ---
        guardarBtn.addActionListener(e -> {
            // Recolectamos los datos del formulario
            Paciente paciente = new Paciente();
            paciente.setId(idFld.getText());
            paciente.setNombre(nombreFld.getText());
            paciente.setTelefono(telefonoFld.getText());
            // paciente.setFechaNacimiento(fechaNacimientoFld.getDate());

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
    }

    public void setController(Controller controller) { this.controller = controller; }
    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Actualiza la JTable cuando la lista en el modelo cambia
        if (evt.getPropertyName().equals(Model.LIST)) {
            pacientesTbl.setModel(new TableModel(model.getList()));
        }
        // Actualiza el formulario cuando el 'current' en el modelo cambia
        if (evt.getPropertyName().equals(Model.CURRENT)) {
            Paciente p = model.getCurrent();
            idFld.setText(p.getId());
            nombreFld.setText(p.getNombre());
            telefonoFld.setText(p.getTelefono());
            // fechaNacimientoFld.setDate(p.getFechaNacimiento());

            // Habilita/deshabilita el botón y el campo de ID
            boolean isNew = p.getId().isEmpty();
            guardarBtn.setEnabled(true);

            borrarBtn.setEnabled(!isNew);
            idFld.setEditable(isNew);
        }
    }

    public JPanel getPanel() { return panel; }
}