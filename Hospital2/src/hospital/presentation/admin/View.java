package hospital.presentation.admin;

import javax.swing.*;



public class View {
    private JPanel panel;
    private JTabbedPane tabbedPane;


    // Vistas para cada una de las pestañas
    private hospital.presentation.admin.medicos.View medicosView;
    private hospital.presentation.admin.pacientes.View pacientesView;
    private hospital.presentation.admin.farmaceutas.View farmaceutasView;
    private hospital.presentation.admin.medicamentos.View medicamentosView;

    // Aquí irían los Modelos y Controladores de cada módulo
    // ...

    public View() {
        // 1. Crear las instancias de las vistas de cada módulo
        medicosView = new hospital.presentation.admin.medicos.View();
        pacientesView = new hospital.presentation.admin.pacientes.View();
        farmaceutasView = new hospital.presentation.admin.farmaceutas.View();
        medicamentosView = new hospital.presentation.admin.medicamentos.View();

        // 2. Añadir cada vista como una pestaña al JTabbedPane
        // El método addTab recibe el título de la pestaña y el panel a mostrar
        tabbedPane.addTab("Médicos", medicosView.getPanel());
        tabbedPane.addTab("Pacientes", pacientesView.getPanel());
        tabbedPane.addTab("Farmaceutas", farmaceutasView.getPanel());
        tabbedPane.addTab("Medicamentos", medicamentosView.getPanel());
        // Aquí es donde en un futuro crearemos los MVC de cada módulo
        // new hospital.presentation.admin.medicos.Controller(...);
        // new hospital.presentation.admin.pacientes.Controller(...);
        // ... etc.
    }

    public JPanel getPanel() {
        return panel;
    }
}