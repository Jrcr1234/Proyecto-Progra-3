package hospital.presentation.prescripcion.dosis_details;

import hospital.logic.Medicamento;
import hospital.logic.LineaDetalle;
import hospital.presentation.util.GuiUtils;

import javax.swing.*;

public class View {
    private JPanel panel;
    private JComboBox<Integer> cantidadCmb;
    private JComboBox<Integer> duracionCmb;
    private JTextArea indicacionesFld;
    private JButton guardarBtn;
    private JButton cancelarBtn;

    private Controller controller;
    private Model model;

    public View() {}

    public void init() {

        // === CÓDIGO PARA AÑADIR ICONOS A LOS BOTONES ===
        try {
            int iconSize = 24;


            ImageIcon guardarIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/guardar.png")), iconSize, iconSize);
            guardarBtn.setIcon(guardarIcon);

            ImageIcon cancelarIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/cancel.png")), iconSize, iconSize);
            cancelarBtn.setIcon(cancelarIcon);

        } catch (Exception e) {
            System.err.println("Error al cargar los iconos de los botones: " + e.getMessage());
        }
        // =======================================================

        // Conectamos los botones
        guardarBtn.addActionListener(e -> controller.guardar());
        cancelarBtn.addActionListener(e -> controller.cancelar());

        // Llenamos el ComboBox de cantidad con números del 1 al 10
        for (int i = 1; i <= 10; i++) {
            cantidadCmb.addItem(i);
        }

        // Llenamos el ComboBox de duración con números del 1 al 30
        for (int i = 1; i <= 30; i++) {
            duracionCmb.addItem(i);
        }
    }

    // --- Getters para que el Controller lea los datos ---
    public int getCantidad() {
        // 1. Obtenemos el ítem seleccionado del JComboBox
        Object itemSeleccionado = cantidadCmb.getSelectedItem();
        // 2. Lo convertimos a String y luego a un número entero
        return Integer.parseInt(itemSeleccionado.toString());
    }

    public int getDuracion() {
        Object itemSeleccionado = duracionCmb.getSelectedItem();
        return Integer.parseInt(itemSeleccionado.toString());
    }
    public String getIndicaciones() { return indicacionesFld.getText(); }
    public void mostrarError(String msg) { JOptionPane.showMessageDialog(panel, msg, "Error", JOptionPane.ERROR_MESSAGE); }

    // --- Métodos de enlace MVC ---
    public JPanel getPanel() { return panel; }
    public void setController(Controller controller) { this.controller = controller; }
    public void setModel(Model model) { this.model = model;
        // --- LÓGICA PARA RELLENAR DATOS ---
        if (model.getLineaExistente() != null) {
            LineaDetalle linea = model.getLineaExistente();
            cantidadCmb.setSelectedItem(linea.getCantidad());
            duracionCmb.setSelectedItem(linea.getDuracionTratamiento());
            indicacionesFld.setText(linea.getIndicaciones());
        }
    }
}