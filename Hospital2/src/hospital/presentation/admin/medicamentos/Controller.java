package hospital.presentation.admin.medicamentos;

import hospital.logic.Medicamento;
import hospital.logic.Service;
import javax.swing.JOptionPane;
import java.util.List;
import javax.swing.SwingWorker;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.model = model;
        this.view = view;
        // --- ORDEN CORREGIDO ---
        // 1. Conectamos la Vista al Modelo y al Controlador.
        //    A partir de aquí, la Vista está "escuchando" cualquier notificación.
        view.setModel(model);
        view.setController(this);

        // 2. AHORA SÍ, cargamos los datos iniciales en el Modelo.
        //    El Modelo notificará a la Vista (que ya está escuchando) y la tabla se llenará.
        try {
            model.setList(Service.instance().getMedicamentos());
            model.setCurrent(new Medicamento());
        } catch (Exception e) {
            // En caso de que el Service falle al iniciar
        }
    }

    public void search(String filtro) {
        List<Medicamento> rows = Service.instance().searchMedicamentos(filtro);
        model.setList(rows);
    }

    // === MÉTODO 'SAVE' REFINADO ===
    public void save(Medicamento medicamento) { // o save(Medicamento med)
        try {
            if (medicamento.getCodigo().isEmpty() || medicamento.getNombre().isEmpty()) {
                throw new Exception("El código y el nombre son requeridos.");
            }

            // --- CÓDIGO SwingWorker CORREGIDO ---
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Tarea pesada que se ejecuta en un hilo de fondo (segundo plano)
                    if (model.getCurrent().getCodigo().isEmpty()) {
                        Service.instance().createMedicamento(medicamento);
                    } else {
                        Service.instance().updateMedicamento(medicamento);
                    }
                    return null; // Este método debe devolver algo (en este caso, nada 'Void')
                }

                @Override
                protected void done() {
                    // Tarea ligera que se ejecuta de vuelta en el hilo de la interfaz
                    // una vez que 'doInBackground' ha terminado.
                    try {
                        get(); // El método get() captura cualquier excepción que haya ocurrido en doInBackground
                        JOptionPane.showMessageDialog(view.getPanel(), "Datos guardados exitosamente.");

                        // Refrescamos la interfaz de forma segura
                        clear();
                        search(""); // O search("") para medicamentos

                    } catch (Exception ex) {
                        // Muestra el error original que ocurrió en el hilo de fondo
                        JOptionPane.showMessageDialog(view.getPanel(), ex.getCause().getMessage(), "Error al guardar", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute(); // Inicia el trabajador

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void edit(int row) {
        Medicamento med = model.getList().get(row);
        model.setCurrent(med);
    }

    // método delete

    public void delete(int row) {
        try {
            // Obtenemos el medicamento a borrar ANTES de iniciar el hilo de fondo
            Medicamento med = model.getList().get(row);

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Trabajo pesado en segundo plano: borrar del archivo
                    Service.instance().deleteMedicamento(med.getCodigo());
                    return null;
                }

                @Override
                protected void done() {
                    // Actualización de la interfaz cuando el borrado termina
                    try {
                        get();
                        JOptionPane.showMessageDialog(view.getPanel(), "Medicamento eliminado exitosamente.");
                        search(""); // Refresca la tabla
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(view.getPanel(), e.getCause().getMessage(), "Error al borrar", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), "No se pudo seleccionar el medicamento a borrar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clear() {
        model.setCurrent(new Medicamento());
    }
}