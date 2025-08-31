package hospital.presentation.admin;

import hospital.presentation.util.GuiUtils;
import javax.swing.*;
import java.awt.*;

public class View {
    private JPanel panel;
    private JTabbedPane tabbedPane;

    // --- Atributos para cada vista hija ---
    private hospital.presentation.admin.medicos.View medicosView;
    private hospital.presentation.admin.pacientes.View pacientesView;
    private hospital.presentation.admin.farmaceutas.View farmaceutasView;
    private hospital.presentation.admin.medicamentos.View medicamentosView;
    private hospital.presentation.admin.about.View aboutView;

    public View() {
        int tabIconSize = 16;

        try {
            // --- MÓDULO DE MEDICAMENTOS ---
            // Creamos la vista y la guardamos en el ATRIBUTO de la clase
            this.medicamentosView = new hospital.presentation.admin.medicamentos.View();
            hospital.presentation.admin.medicamentos.Model medModel = new hospital.presentation.admin.medicamentos.Model();
            new hospital.presentation.admin.medicamentos.Controller(this.medicamentosView, medModel);
            ImageIcon medicamentosIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/medicamento.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Medicamentos", medicamentosIcon, this.medicamentosView.getPanel());

            // --- MÓDULO DE PACIENTES ---
            this.pacientesView = new hospital.presentation.admin.pacientes.View();
            hospital.presentation.admin.pacientes.Model pacModel = new hospital.presentation.admin.pacientes.Model();
            new hospital.presentation.admin.pacientes.Controller(this.pacientesView, pacModel);
            ImageIcon pacientesIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/paciente.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Pacientes", pacientesIcon, this.pacientesView.getPanel());

            // --- MÓDULO DE FARMACEUTAS ---
            this.farmaceutasView = new hospital.presentation.admin.farmaceutas.View();
            hospital.presentation.admin.farmaceutas.Model farModel = new hospital.presentation.admin.farmaceutas.Model();
            new hospital.presentation.admin.farmaceutas.Controller(this.farmaceutasView, farModel);
            ImageIcon farmaceutasIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/farmaceuta.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Farmaceutas", farmaceutasIcon, this.farmaceutasView.getPanel());

            // --- MÓDULO DE MÉDICOS ---
            this.medicosView = new hospital.presentation.admin.medicos.View();
            hospital.presentation.admin.medicos.Model medicosModel = new hospital.presentation.admin.medicos.Model();
            new hospital.presentation.admin.medicos.Controller(this.medicosView, medicosModel);
            ImageIcon medicosIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/medico.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Médicos", medicosIcon, this.medicosView.getPanel());

            // --- PESTAÑA ACERCA DE ---
            this.aboutView = new hospital.presentation.admin.about.View();
            ImageIcon aboutIcon = GuiUtils.scaleIcon(new ImageIcon(getClass().getResource("/icons/info.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Acerca de", this.aboutView.getPanel());

        } catch (Exception e) {
            System.err.println("Error al crear las pestañas o cargar sus iconos: " + e.getMessage());
        }
    }

    public JPanel getPanel() {
        return panel;
    }
    // === MÉTODO 'scaleIcon' QUE FALTABA ===
    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}