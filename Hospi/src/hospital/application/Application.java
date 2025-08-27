package hospital.application;

import hospital.logic.Usuario;
import hospital.presentation.login.Controller;
import hospital.presentation.login.Model;
import hospital.presentation.login.View;

import javax.swing.*;

public class Application {

    // Tendremos una referencia a la ventana actual y al usuario logueado
    private static JFrame window;
    private static Usuario currentUser = null;

    public static void main(String[] args) {
        // --- Inicia la aplicación mostrando la ventana de Login ---

        // 1. Crear el trío MVC para el Login
        Model loginModel = new Model();
        View loginView = new View();
        // Al crear el Controller, él mismo se encarga de enlazar el Model y la View
        new Controller(loginModel, loginView);

        // 2. Crear la ventana (JFrame) y poner el panel del login adentro
        window = new JFrame("Ingreso al Sistema de Recetas");
        window.setContentPane(loginView.getPanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 600); //Ajusta el tamaño de la ventana al que queramos
        window.setResizable(false); // Para que no se pueda cambiar el tamaño
        window.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        window.setVisible(true);
    }

    /**
     * Este método será llamado por el Controller del login cuando el ingreso sea exitoso.
     * @param user El usuario que ha sido autenticado.
     */
    public static void onLoginSuccess(Usuario user) {
        currentUser = user;
        window.dispose(); // Cierra la ventana de login

        // --- Crear y mostrar la ventana principal (Dashboard) ---
        hospital.presentation.main.View mainView = new hospital.presentation.main.View();
        // NOTA: Aquí irían el Model y Controller de la ventana principal
        window = new JFrame("Sistema de Recetas - Bienvenido, " + currentUser.getId());
        window.setJMenuBar(mainView.getMenuBar()); // <-- AÑADIMOS LA BARRA DE MENÚ
        window.setContentPane(mainView.getPanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //window.pack();
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // 2. TODO: Aquí es donde crearemos y mostraremos la ventana principal (Dashboard)
        // Por ahora, terminamos la ejecución.
        System.out.println("Mostrando ventana principal...");
    }

    public static JFrame getWindow() {
        return window;
    }

    public static Usuario getCurrentUser() {
        return currentUser;
    }
}
