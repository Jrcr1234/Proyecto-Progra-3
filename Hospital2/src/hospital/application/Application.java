package hospital.application;

import javax.swing.*;
// Importamos únicamente la vista de administración
import hospital.presentation.admin.View;

public class Application {

    public static void main(String[] args) {
        // Creamos y mostramos directamente la ventana de Administración
        View adminView = new View();

        JFrame window = new JFrame("Panel de Administración");
        window.setContentPane(adminView.getPanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}