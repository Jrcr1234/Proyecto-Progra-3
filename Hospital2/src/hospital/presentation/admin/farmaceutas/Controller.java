package hospital.presentation.admin.farmaceutas;

import hospital.logic.Farmaceuta;
import hospital.logic.Service;
import hospital.presentation.admin.farmaceutas.Model;
import hospital.presentation.admin.farmaceutas.View;

import javax.swing.*;
import java.util.List;

public class Controller {

    private hospital.presentation.admin.farmaceutas.View view;
    private hospital.presentation.admin.farmaceutas.Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setModel(model);
        view.setController(this);
        try {
            model.setList(Service.instance().getFarmaceutas());
            model.setCurrent(new Farmaceuta());
        } catch (Exception e) {}
    }

    public void guardar(Farmaceuta farmaceuta) {
        try {
            if (farmaceuta.getId().isEmpty() || farmaceuta.getNombre().isEmpty()) {
                throw new Exception("La cédula y el nombre son requeridos.");
            }
            // La clave por defecto es la cédula al crear un nuevo médico
            if (model.getCurrent().getId().isEmpty()) {
                farmaceuta.setClave(farmaceuta.getId());
            }

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Lógica de guardado en segundo plano
                    if (model.getCurrent().getId().isEmpty()) {
                        Service.instance().createFarmaceuta(farmaceuta); // <-- CORREGIDO
                    } else {
                        Service.instance().updateFarmaceuta(farmaceuta); // <-- CORREGIDO
                    }
                    return null;
                }

                @Override
                protected void done() {
                    // Actualización de la interfaz al terminar
                    try {
                        get();
                        JOptionPane.showMessageDialog(view.getPanel(), "Datos guardados exitosamente.");
                        limpiar();
                        buscar("");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view.getPanel(), ex.getCause().getMessage(), "Error al guardar", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void borrar(int row) {
        try {
            Farmaceuta far = model.getList().get(row);
            int confirm = JOptionPane.showConfirmDialog(view.getPanel(),
                    "¿Está seguro de que desea borrar al farmaceuta " + far.getNombre() + "?", // <-- CORREGIDO
                    "Confirmar Borrado",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        Service.instance().deleteFarmaceuta(far.getId());
                        return null;
                    }
                    @Override
                    protected void done() {
                        try {
                            get();
                            buscar("");
                        } catch (Exception ex) {
                            // Manejar error si es necesario
                        }
                    }
                }.execute();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), "No se pudo seleccionar el farmaceuta a borrar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void buscar(String filtro) {
        try {
            model.setList(Service.instance().searchFarmaceutas(filtro));
        } catch (Exception e) {}
    }

    public void editar(int row) {
        model.setCurrent(model.getList().get(row));
    }

    public void limpiar() {
        model.setCurrent(new Farmaceuta());
    }
    public void reporte() {
        // La lógica para generar el reporte de farmaceutas irá aquí en el futuro.
        JOptionPane.showMessageDialog(view.getPanel(), "Funcionalidad de reporte aún no implementada.");
    }
}