package hospital.presentation.admin;

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
        int tabIconSize = 16; // Definimos el tamaño para los iconos de las pestañas

        try {
            // --- MÓDULO DE MEDICAMENTOS ---
            hospital.presentation.admin.medicamentos.Model medModel = new hospital.presentation.admin.medicamentos.Model();
            hospital.presentation.admin.medicamentos.View medView = new hospital.presentation.admin.medicamentos.View();
            new hospital.presentation.admin.medicamentos.Controller(medView, medModel);
            // Cargamos el icono y lo usamos inmediatamente en el método addTab correcto
            ImageIcon medicamentosIcon = scaleIcon(new ImageIcon(getClass().getResource("/icons/medicamento.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Medicamentos", medicamentosIcon, medView.getPanel());

            // --- MÓDULO DE PACIENTES ---
            hospital.presentation.admin.pacientes.Model pacModel = new hospital.presentation.admin.pacientes.Model();
            hospital.presentation.admin.pacientes.View pacView = new hospital.presentation.admin.pacientes.View();
            new hospital.presentation.admin.pacientes.Controller(pacView, pacModel);
            ImageIcon pacientesIcon = scaleIcon(new ImageIcon(getClass().getResource("/icons/paciente.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Pacientes", pacientesIcon, pacView.getPanel());

            // --- MÓDULO DE FARMACEUTAS ---
            hospital.presentation.admin.farmaceutas.Model farModel = new hospital.presentation.admin.farmaceutas.Model();
            hospital.presentation.admin.farmaceutas.View farView = new hospital.presentation.admin.farmaceutas.View();
            new hospital.presentation.admin.farmaceutas.Controller(farView, farModel);
            ImageIcon farmaceutasIcon = scaleIcon(new ImageIcon(getClass().getResource("/icons/farmaceuta.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Farmaceutas", farmaceutasIcon, farView.getPanel());

            // --- MÓDULO DE MÉDICOS ---
            hospital.presentation.admin.medicos.Model medicosModel = new hospital.presentation.admin.medicos.Model();
            hospital.presentation.admin.medicos.View medicosView = new hospital.presentation.admin.medicos.View();
            new hospital.presentation.admin.medicos.Controller(medicosView, medicosModel);
            ImageIcon medicosIcon = scaleIcon(new ImageIcon(getClass().getResource("/icons/medico.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Médicos", medicosIcon, medicosView.getPanel());

            // --- PESTAÑA ACERCA DE ---
            hospital.presentation.admin.about.View aboutView = new hospital.presentation.admin.about.View();
            ImageIcon aboutIcon = scaleIcon(new ImageIcon(getClass().getResource("/icons/info.png")), tabIconSize, tabIconSize);
            tabbedPane.addTab("Acerca de", aboutIcon, aboutView.getPanel());

        } catch (Exception e) {
            // Un error aquí puede significar que un icono no fue encontrado
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