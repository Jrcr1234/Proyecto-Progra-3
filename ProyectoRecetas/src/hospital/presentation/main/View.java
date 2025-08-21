package hospital.presentation.main;

import hospital.application.Application;
import hospital.logic.Usuario;
import javax.swing.*;

public class View {
        private JPanel panel;
        private JMenuBar menuBar;
        // Declaramos el JMenuItem como un atributo de la clase
        private JMenuItem cambiarClaveMenuItem;
        private JMenuItem salirMenuItem;
        // Aquí irían los otros menús y items...

        public View() {
                panel = new JPanel(); // Creamos el panel principal
                menuBar = new JMenuBar(); // Creamos la barra de menú

                // --- Creación del Menú "Archivo" ---
                JMenu archivoMenu = new JMenu("Archivo");

                cambiarClaveMenuItem = new JMenuItem("Cambiar Clave");
                salirMenuItem = new JMenuItem("Salir");

                archivoMenu.add(cambiarClaveMenuItem);
                archivoMenu.add(salirMenuItem);

                menuBar.add(archivoMenu);

                // --- Aquí irían los otros menús: Catálogos, Procesos, etc. ---

                // === LÓGICA DEL ACTION LISTENER (LA QUE TENÍAMOS ANTES) ===
                cambiarClaveMenuItem.addActionListener(e -> {
                        // PRUEBA 1: ¿Se está disparando el evento?
                        System.out.println("1. Click en 'Cambiar Clave' detectado.");

                        try {
                                JDialog dialog = new JDialog(Application.getWindow(), "Cambio de Clave", true);
                                Usuario currentUser = Application.getCurrentUser();

                                // PRUEBA 2: ¿Tenemos un usuario válido?
                                System.out.println("2. Usuario actual: " + (currentUser != null ? currentUser.getId() : "¡¡NULO!!"));
                                if (currentUser == null) {
                                        System.out.println("Error: No se puede cambiar la clave si no hay un usuario logueado.");
                                        return; // Detenemos la ejecución si no hay usuario
                                }

                                hospital.presentation.chpass.View chpassView = new hospital.presentation.chpass.View();

                                // PRUEBA 3: ¿Se creó la vista y su panel correctamente?
                                if (chpassView.getPanel() == null) {
                                        System.out.println("¡ERROR GRAVE! El panel de la vista 'chpass' es nulo. Revisa el 'fieldName' en el diseñador.");
                                        return;
                                }
                                System.out.println("3. Vista de 'Cambio de Clave' creada correctamente.");

                                hospital.presentation.chpass.Model chpassModel = new hospital.presentation.chpass.Model(currentUser);
                                new hospital.presentation.chpass.Controller(chpassModel, chpassView, dialog);

                                System.out.println("4. MVC creado. Mostrando la ventana...");
                                dialog.setContentPane(chpassView.getPanel());
                                dialog.pack();
                                dialog.setLocationRelativeTo(Application.getWindow());
                                dialog.setVisible(true);

                                System.out.println("5. La ventana de cambio de clave se ha cerrado.");

                        } catch (Exception ex) {
                                System.out.println("¡¡HA OCURRIDO UNA EXCEPCIÓN INESPERADA!!");
                                ex.printStackTrace(); // Imprime el error completo en la consola
                        }
                });

                salirMenuItem.addActionListener(e -> System.exit(0));
        }

        public JPanel getPanel() {
                return panel;
        }

        public JMenuBar getMenuBar() {
                return menuBar;
        }
}