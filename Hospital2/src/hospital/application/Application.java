package hospital.application;

import hospital.logic.Sesion;
import hospital.logic.Usuario;
import javax.swing.*;
import java.awt.Frame;

public class Application {
    private static JFrame window;

    public static void main(String[] args) {
        // --- FLUJO DE EJECUCIÓN PRINCIPAL ---
        try {
            // 1. Muestra la ventana de login y espera a que se cierre.
            doLogin();

            // 2. Si el login fue exitoso (hay un usuario en la sesión), muestra la ventana principal.
            if (Sesion.getUsuario() != null) {
                doRun();
            }
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
        }
    }

    public static void doLogin() {
        // Este método crea y muestra el diálogo de login de forma modal
        JDialog dialog = new JDialog((Frame) null, "Ingreso al Sistema", true);

        // Creamos el trío MVC para el Login
        hospital.presentation.login.Model loginModel = new hospital.presentation.login.Model();
        hospital.presentation.login.View loginView = new hospital.presentation.login.View();
        // El controller del login necesita que le pasemos el diálogo para poder cerrarlo
        new hospital.presentation.login.Controller(loginView, loginModel, dialog);

        loginView.init();
        dialog.setContentPane(loginView.getPanel());
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true); // El programa se pausa aquí hasta que el diálogo se cierre
    }

    public static void doRun() {
        Usuario usuario = Sesion.getUsuario();
        window = new JFrame("Sistema de Recetas - " + usuario.getNombre());

        // TODOS los usuarios ahora usan la misma vista principal con pestañas.
        // Esta vista será la "inteligente" que decide qué mostrar.
        hospital.presentation.mainview.View principalView = new hospital.presentation.mainview.View();

        window.setContentPane(principalView.getPanel());
        window.setSize(900, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    public static JFrame getWindow() {
        return window;
    }
}