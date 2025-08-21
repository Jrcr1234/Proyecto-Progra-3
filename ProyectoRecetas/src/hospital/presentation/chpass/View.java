package hospital.presentation.chpass;

import javax.swing.*;

public class View {
    private JPanel panel;
    private JPasswordField actualFld;
    private JPasswordField nuevaFld;
    private JPasswordField confirmarFld;
    private JButton aceptarBtn;
    private JButton cancelarBtn;
    private Controller controller;

    public View() {
        aceptarBtn.addActionListener(e -> controller.cambiarClave());
        cancelarBtn.addActionListener(e -> controller.cerrarVentana());
    }

    // Getters para que el Controller pueda leer los datos
    public String getActual() { return new String(actualFld.getPassword()); }
    public String getNueva() { return new String(nuevaFld.getPassword()); }
    public String getConfirmar() { return new String(confirmarFld.getPassword()); }
    public JPanel getPanel() { return panel; }
    public void setController(Controller controller) { this.controller = controller; }
}