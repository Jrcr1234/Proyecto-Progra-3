package hospital.presentation.medico;

import hospital.logic.Sesion;
import hospital.logic.Usuario;
import hospital.presentation.util.GuiUtils;
import javax.swing.*;

public class View {
    private JPanel panel;
    private JTabbedPane tabbedPane;

    public View() {
        Usuario usuario = Sesion.getUsuario();
        if (usuario == null) return;

        try {
            int tabIconSize = 16;

            // --- PESTAÑA DE PRESCRIPCIÓN ---
            // Creamos el MVC de Prescripción
            hospital.presentation.prescripcion.Model prescripcionModel = new hospital.presentation.prescripcion.Model();
            hospital.presentation.prescripcion.View prescripcionView = new hospital.presentation.prescripcion.View();
            new hospital.presentation.prescripcion.Controller(prescripcionView, prescripcionModel);
            prescripcionView.init();

            // Creamos su icono y la añadimos como una pestaña
            ImageIcon prescripcionIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/prescripcion.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Prescribir Receta", prescripcionIcon, prescripcionView.getPanel());

            // --- PESTAÑA ACERCA DE ---
            // 1. Creamos la vista de "Acerca de"
            // Reutilizamos la que está en el paquete 'admin' porque es genérica
            hospital.presentation.mainview.about.View aboutView = new hospital.presentation.mainview.about.View();

            // 2. Creamos su icono y la añadimos
            ImageIcon aboutIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/info.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Acerca de", aboutIcon, aboutView.getPanel());
            // Aquí añadiríamos las pestañas de Dashboard e Histórico cuando las creemos...

        } catch (Exception e) {
            System.err.println("Error al crear las pestañas del médico: " + e.getMessage());
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}