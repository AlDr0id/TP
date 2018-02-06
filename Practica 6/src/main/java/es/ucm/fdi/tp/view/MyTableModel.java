package es.ucm.fdi.tp.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

class MyTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] colNames;
	List<String> names;

	/**
	 * Constructora de la tabla de jugadores
	 */
	MyTableModel() {
		this.colNames = new String[] { "#Player", "Color" };
		names = new ArrayList<>();
		names.add("");
		names.add("");
	}

	@Override
	/**
	 * Devuelve el nombre de la columna dada
	 * @param col columna elegida
	 * @return nombre
	 */
	public String getColumnName(int col) {
		return colNames[col];
	}

	@Override
	/**
	 * Devuelve el numero de columnas
	 * @return numero de columnas
	 */
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	/**
	 * Devuelve el numero de filas
	 * @return numero de filas
	 */
	public int getRowCount() {
		return names != null ? names.size() : 0;
	}

	@Override
	/**
	 * Devuelve el valor de lo que se encuentre en la posicion dada
	 * @param rowIndex fila elegida
	 * @param columnIndex columna elegida
	 * @return valor
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return rowIndex;
		} else {
			return names.get(rowIndex);
		}
	}

	/**
	 * Agrega un nombre a la tabla
	 * @param name nombre a agregar
	 */
	public void addName(String name) {
		names.add(name);
		refresh();
	}

	/**
	 * Refresca la tabla
	 */
	public void refresh() {
		fireTableDataChanged();
	}

};