package hospital.presentation.main;

import hospital.application.Application;
import hospital.logic.Usuario;
// Importamos solo las clases de chpass que NO se llaman 'View'
import hospital.presentation.chpass.Controller;
import hospital.presentation.chpass.Model;

import javax.swing.*;

public class View {
    private JPanel panel;
    private JMenuBar menuBar;
    private JMenuItem cambiarClaveMenuItem;
    private JMenuItem salirMenuItem;

    public View() {
        panel = new JPanel();
        menuBar = new JMenuBar();

        // Creación del Menú "Archivo"
        JMenu archivoMenu = new JMenu("Archivo");
        cambiarClaveMenuItem = new JMenuItem("Cambiar Clave");
        salirMenuItem = new JMenuItem("Salir");

        archivoMenu.add(cambiarClaveMenuItem);
        archivoMenu.add(salirMenuItem);
        menuBar.add(archivoMenu);

        // ActionListener para Cambiar Clave
        /*
        cambiarClaveMenuItem.addActionListener(e -> {
            JDialog dialog = new JDialog(Application.getWindow(), "Cambio de Clave", true);
            Usuario currentUser = Application.getCurrentUser();


            // Usamos el nombre completo para la vista de chpass para evitar la ambigüedad
            hospital.presentation.chpass.View chpassView = new hospital.presentation.chpass.View();

            Model chpassModel = new Model(currentUser);
            new Controller(chpassModel, chpassView, dialog);

            dialog.setContentPane(chpassView.getPanel());
            dialog.pack();
            dialog.setLocationRelativeTo(Application.getWindow());
            dialog.setVisible(true);
        });
        */
        // ActionListener para Salir
        salirMenuItem.addActionListener(e -> System.exit(0));
    }

    public JPanel getPanel() {
        return panel;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}