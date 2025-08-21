package hospital.presentation.login;

import java.awt.Image;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.*;

public class View {
    private JPanel panel;
    private JTextField idFld;
    private JPasswordField claveFld;
    private JButton ingresarBtn;
    private JButton salirBtn;
    private JButton restaurarBtn;
    private JLabel headerIconLbl;

    private Controller controller;
    private Model model;

    public View() {

        // 1. Cargar las imágenes originales
        ImageIcon originalHeaderIcon = new ImageIcon(getClass().getResource("/Iconos/lock_icon.png"));
        ImageIcon originalIngresarIcon = new ImageIcon(getClass().getResource("/Iconos/inicio-de-sesion.png"));
        ImageIcon originalSalirIcon = new ImageIcon(getClass().getResource("/Iconos/Cancelar.png"));
        ImageIcon originalRestaurarIcon = new ImageIcon(getClass().getResource("/Iconos/contrasena.png"));

// 2. Redimensionarlas usando nuestra nueva función
        int iconSize = 25; // Aqui se hacen  más grandes o pequeños
        ImageIcon ingresarIcon = scaleIcon(originalIngresarIcon, iconSize, iconSize);
        ImageIcon salirIcon = scaleIcon(originalSalirIcon, iconSize, iconSize);
        ImageIcon restaurarIcon = scaleIcon(originalRestaurarIcon, iconSize, iconSize);
        ImageIcon headerIcon = scaleIcon(originalHeaderIcon, 128, 128);

// 3. Asignar los iconos YA REDIMENSIONADOS a los botones
        ingresarBtn.setIcon(ingresarIcon);
        salirBtn.setIcon(salirIcon);
        restaurarBtn.setIcon(restaurarIcon);
        headerIconLbl.setIcon(headerIcon);

        ingresarBtn.addActionListener(e -> {
            // La vista solo le pasa la llamada al controlador
            controller.ingresar();
        });

        salirBtn.addActionListener(e -> {
            controller.salir();
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public JPanel getPanel() {
        return panel;
    }

    // El controlador necesitará estos métodos para obtener los datos
    public String getId() {
        return idFld.getText();
    }

    public String getClave() {
        return new String(claveFld.getPassword());
    }

    /**
     * Método ayudante para redimensionar un ImageIcon a un tamaño específico.
     * @param icon El ImageIcon original que se quiere redimensionar.
     * @param width El nuevo ancho deseado.
     * @param height El nuevo alto deseado.
     * @return Un nuevo ImageIcon ya redimensionado.
     */
    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}
