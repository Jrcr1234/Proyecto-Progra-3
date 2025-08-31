package hospital.presentation.mainview.pacientes;

import hospital.logic.Paciente;
import hospital.logic.Service;
import javax.swing.JOptionPane;
import java.util.List;
import javax.swing.SwingWorker;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setModel(model);
        view.setController(this);
        // Carga inicial de datos
        try {
            model.setList(Service.instance().getPacientes());
            model.setCurrent(new Paciente());
        } catch (Exception e) {
            // Manejar error si el Service no puede iniciar
        }
    }


    public void guardarPaciente(Paciente paciente) {
        try {
            if (paciente.getId().isEmpty() || paciente.getNombre().isEmpty()) {
                throw new Exception("La cédula y el nombre son requeridos.");
            }

            // --- CÓDIGO SwingWorker CORREGIDO ---
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Tarea pesada que se ejecuta en un hilo de fondo (segundo plano)
                    if (model.getCurrent().getId().isEmpty()) {
                        Service.instance().createPaciente(paciente);
                    } else {
                        Service.instance().updatePaciente(paciente);
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
                        buscarPaciente("");

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



    public void borrarPaciente(int rowIndex) {
        try {
            // Obtenemos el paciente a borrar ANTES de iniciar el hilo de fondo
            Paciente pacienteABorrar = model.getList().get(rowIndex);

            // Pedimos confirmación al usuario
            int confirm = JOptionPane.showConfirmDialog(view.getPanel(),
                    "¿Está seguro de que desea borrar al paciente " + pacienteABorrar.getNombre() + "?",
                    "Confirmar Borrado",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Creamos un "trabajador en segundo plano" para la operación de borrado
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        // Trabajo pesado en segundo plano: borrar del archivo
                        Service.instance().deletePaciente(pacienteABorrar.getId());
                        return null;
                    }

                    @Override
                    protected void done() {
                        // Actualización de la interfaz cuando el borrado termina
                        try {
                            get(); // Captura cualquier error que haya ocurrido en doInBackground
                            JOptionPane.showMessageDialog(view.getPanel(), "Paciente eliminado exitosamente.");
                            buscarPaciente(""); // Refresca la tabla
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(view.getPanel(), e.getCause().getMessage(), "Error al borrar", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }.execute();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), "No se pudo seleccionar el paciente a borrar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public void buscarPaciente(String filtro) {
        try {
            List<Paciente> rows = Service.instance().searchPacientes(filtro);
            model.setList(rows);
        } catch (Exception e) {
            // Manejar error si la búsqueda falla
        }
    }

    public void generarReporte() {
        // La lógica para generar el reporte de pacientes irá aquí...
        JOptionPane.showMessageDialog(view.getPanel(), "Funcionalidad de reporte aún no implementada.");
    }

    public void clear() {
        model.setCurrent(new Paciente());
    }

    public void edit(int row) {
        Paciente p = model.getList().get(row);
        model.setCurrent(p);
    }
}