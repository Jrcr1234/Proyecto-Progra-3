package hospital.presentation.admin.medicamentos;

import hospital.logic.Medicamento;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel {
    private List<Medicamento> rows;
    // Define los nombres que aparecerán en las cabeceras de la tabla
    private final String[] cols = {"Código", "Nombre", "Presentación"};

    public TableModel(List<Medicamento> rows) {
        this.rows = rows;
    }

    @Override
    public int getRowCount() { // Le dice a la tabla cuántas filas de datos hay
        return rows.size();
    }

    @Override
    public int getColumnCount() { // Le dice a la tabla cuántas columnas debe dibujar
        return cols.length;
    }

    @Override
    public String getColumnName(int col) { // Le da el nombre a cada cabecera de columna
        return cols[col];
    }

    @Override
    public Object getValueAt(int row, int col) { // Pide el valor para una celda específica
        Medicamento med = rows.get(row);
        switch (col) {
            case 0: return med.getCodigo();
            case 1: return med.getNombre();
            case 2: return med.getPresentacion();
            default: return "";
        }
    }
}