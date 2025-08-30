package hospital.presentation.admin.medicos;

import hospital.logic.Medico;
import hospital.logic.Service;
import javax.swing.*;
import java.util.List;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setModel(model);
        view.setController(this);
        try {
            model.setList(Service.instance().getMedicos());
            model.setCurrent(new Medico());
        } catch (Exception e) {}
    }

    public void guardar(Medico medico) {
        try {
            if (medico.getId().isEmpty() || medico.getNombre().isEmpty()) {
                throw new Exception("La cédula y el nombre son requeridos.");
            }
            // La clave por defecto es la cédula al crear un nuevo médico
            if (model.getCurrent().getId().isEmpty()) {
                medico.setClave(medico.getId());
            }

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Lógica de guardado en segundo plano
                    if (model.getCurrent().getId().isEmpty()) {
                        Service.instance().createMedico(medico); // <-- CORREGIDO
                    } else {
                        Service.instance().updateMedico(medico); // <-- CORREGIDO
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
            Medico med = model.getList().get(row);
            int confirm = JOptionPane.showConfirmDialog(view.getPanel(), "¿Está seguro de que desea borrar al médico " + med.getNombre() + "?");
            if (confirm == JOptionPane.YES_OPTION) {
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        Service.instance().deleteMedico(med.getId());
                        return null;
                    }
                    @Override
                    protected void done() {
                        try {
                            get();
                            buscar("");
                        } catch (Exception ex) {
                            // Manejar error
                        }
                    }
                }.execute();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), "No se pudo seleccionar el médico a borrar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscar(String filtro) {
        try {
            model.setList(Service.instance().searchMedicos(filtro));
        } catch (Exception e) {}
    }

    public void editar(int row) {
        model.setCurrent(model.getList().get(row));
    }

    public void limpiar() {
        model.setCurrent(new Medico());
    }
    public void reporte() {
        // La lógica para generar el reporte de médicos irá aquí en el futuro.
        JOptionPane.showMessageDialog(view.getPanel(), "Funcionalidad de reporte aún no implementada.");
    }
}