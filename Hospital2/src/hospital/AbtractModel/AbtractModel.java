package hospital.AbtractModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

//Esta clase AbtrackModel es para heredarla en los demas Models para ahorrar codigo

public abstract class AbtractModel {
    private final PropertyChangeSupport support;

    public AbtractModel() {
        support = new PropertyChangeSupport(this);
    }

    // Permite registrar un listener que escuche cambios en el modelo.

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    //Permite remover un listener registrado

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Notifica a los listeners que una propiedad del modelo ha cambiado.
     *
     * @param propertyName nombre de la propiedad cambiada
     * @param oldValue     valor anterior
     * @param newValue     valor nuevo
     */
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }
}