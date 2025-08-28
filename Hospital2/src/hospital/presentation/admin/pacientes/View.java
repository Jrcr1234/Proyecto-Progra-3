package hospital.presentation.admin.pacientes;

import hospital.logic.Paciente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {

    private JPanel panel;

    private JTextField ID;
    private JTextField Nombre;
    private JTextField Numero;
    private JTextField IDbusqueda;

    private JButton guardarButton;
    private JButton borrarButton;
    private JButton buscarButton;
    private JButton reporteButton;

    private JTable TablaLista;

    private Controller controller;
    private Model model;

    public View() {
        // Inicialización de los botones
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) controller.guardarPaciente();
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) controller.borrarPaciente();
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) controller.buscarPaciente();
            }
        });

        reporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) controller.generarReporte();
            }
        });
    }

    // Registrar el Controller
    public void setController(Controller controller) {
        this.controller = controller;
    }

    // Registrar el Modelo
    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this); // La Vista escucha los cambios del Modelo
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Model.CURRENT.equals(evt.getPropertyName())) {
            Paciente p = (Paciente) evt.getNewValue();
            if (p != null) {
                ID.setText(p.getId());
                Nombre.setText(p.getNombre());
                Numero.setText(p.getNumeroTelefonico());
            } else {
                // Limpiar campos si no hay paciente
                ID.setText("");
                Nombre.setText("");
                Numero.setText("");
            }
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    // Métodos  para obtener datos de los campos
    public String getID() {
        return ID.getText();
    }

    public String getNombre() {
        return Nombre.getText();
    }

    public String getTelefono() {
        return Numero.getText();
    }
}