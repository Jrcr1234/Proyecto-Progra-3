package hospital.presentation.mainview;

import hospital.presentation.util.GuiUtils;
import hospital.logic.Usuario;
import hospital.logic.Sesion;
import javax.swing.*;

public class View {
    private JPanel panel;
    private JTabbedPane tabbedPane;

    // --- Atributos para cada vista hija ---
    private hospital.presentation.mainview.medicos.View medicosView;
    private hospital.presentation.mainview.pacientes.View pacientesView;
    private hospital.presentation.mainview.farmaceutas.View farmaceutasView;
    private hospital.presentation.mainview.medicamentos.View medicamentosView;
    private hospital.presentation.mainview.about.View aboutView;

    public View() {
        Usuario usuario = Sesion.getUsuario();
        if (usuario == null) return;
        int tabIconSize = 16;

        try {
            // PESTAÑAS PARA EL ROL "Administrador"
            if (usuario.getTipo().equals("Administrador")) {
                // Módulo de Medicamentos
                hospital.presentation.mainview.medicamentos.Model medModel = new hospital.presentation.mainview.medicamentos.Model();
                hospital.presentation.mainview.medicamentos.View medView = new hospital.presentation.mainview.medicamentos.View();
                new hospital.presentation.mainview.medicamentos.Controller(medView, medModel);
                ImageIcon medicamentosIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/medicamento.png")), tabIconSize, tabIconSize);
                tabbedPane.addTab("Medicamentos", medicamentosIcon, medView.getPanel());

                // Módulo de Pacientes
                hospital.presentation.mainview.pacientes.Model pacModel = new hospital.presentation.mainview.pacientes.Model();
                hospital.presentation.mainview.pacientes.View pacView = new hospital.presentation.mainview.pacientes.View();
                new hospital.presentation.mainview.pacientes.Controller(pacView, pacModel);
                ImageIcon pacientesIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/paciente.png")), tabIconSize, tabIconSize);
                tabbedPane.addTab("Pacientes", pacientesIcon, pacView.getPanel());

                // Módulo de Farmaceutas
                hospital.presentation.mainview.farmaceutas.Model farModel = new hospital.presentation.mainview.farmaceutas.Model();
                hospital.presentation.mainview.farmaceutas.View farView = new hospital.presentation.mainview.farmaceutas.View();
                new hospital.presentation.mainview.farmaceutas.Controller(farView, farModel);
                ImageIcon farmaceutasIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/farmaceuta.png")), tabIconSize, tabIconSize);
                tabbedPane.addTab("Farmaceutas", farmaceutasIcon, farView.getPanel());

                // Módulo de Médicos
                hospital.presentation.mainview.medicos.Model medicosModel = new hospital.presentation.mainview.medicos.Model();
                hospital.presentation.mainview.medicos.View medicosView = new hospital.presentation.mainview.medicos.View();
                new hospital.presentation.mainview.medicos.Controller(medicosView, medicosModel);
                ImageIcon medicosIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/medico.png")), tabIconSize, tabIconSize);
                tabbedPane.addTab("Médicos", medicosIcon, medicosView.getPanel());
            }

            // === LÓGICA AÑADIDA PARA EL MÉDICO ===
            if (usuario.getTipo().equals("Medico")) {
                // Creamos y añadimos la pestaña de Prescripción
                hospital.presentation.prescripcion.Model prescModel = new hospital.presentation.prescripcion.Model();
                hospital.presentation.prescripcion.View prescView = new hospital.presentation.prescripcion.View();
                new hospital.presentation.prescripcion.Controller(prescView, prescModel);
                prescView.init();

                ImageIcon prescIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/prescripcion.png")), tabIconSize, tabIconSize);
                tabbedPane.addTab("Prescribir Receta", prescIcon, prescView.getPanel());
            }
            // ===================================

            // PESTAÑAS COMUNES PARA TODOS (Dashboard, Histórico, Acerca de...)
            // (El tab de "Acerca de" que ya tenías)
            hospital.presentation.mainview.about.View aboutView = new hospital.presentation.mainview.about.View();
            ImageIcon aboutIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/info.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Acerca de", aboutIcon, aboutView.getPanel());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}