package View;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
/**
 * @author Prof. Dr. Plinio Vilela - prvilela@unicamp.br
 */
public abstract class GenericTableModel extends AbstractTableModel {
    protected ArrayList<Object> v_data;
    protected String[] columns;

    public GenericTableModel(List v_data, String[] columns) {
        this.columns = columns;
        this.v_data = (ArrayList) v_data;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public int getRowCount() {
        return v_data.size();
    }

    @Override
    public String getColumnName(int column_index) {
        return columns[column_index];
    }

    // Metodos auxiliares:
    public Object getItem(int index_line) {
        if (index_line < 0) {
            return null;
        }
        return v_data.get(index_line);
    }

    public void addItem(Object obj) {
        v_data.add(obj);
        int last_index = getRowCount() - 1;
        fireTableRowsInserted(last_index, last_index);
    }

    public void removeItem(int index_line) {
        v_data.remove(index_line);
        fireTableRowsDeleted(index_line, index_line);
    }

    public void addListOfItems(List<Object> v_itens) {
        this.clear();
        
        for (Object obj : v_itens) {
            this.addItem(obj);
        }
    }

    public void clear() {
        v_data.clear();
        fireTableDataChanged();
    }

    public boolean isEmpty() {
        return v_data.isEmpty();
    }

    public void setColumnWidth(JTable my_table, int[] v_width) {
        my_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < v_width.length; i++) {
            TableColumn col = my_table.getColumnModel().getColumn(i);
            col.setPreferredWidth(v_width[i]);
        }
    }

    // Daqui pra baixo metodos adaptados de ViniGodoy - Curitiba - PR
    public void selectAndScroll(JTable table, int row_index) {
        table.getSelectionModel().setSelectionInterval(row_index, row_index);
        scrollToVisible(table, row_index);
    }

    public void scrollToVisible(JTable table, int row_index) {
        scrollToVisible(table, row_index, 0);
    }

    public void scrollToVisible(JTable table, int row_index, int v_col_index) {
        if (!(table.getParent() instanceof JViewport)) {
            return;
        }
        this.setViewPortPosition((JViewport) table.getParent(), table.getCellRect(row_index, v_col_index, true));
    }

    private void setViewPortPosition(JViewport viewport, Rectangle position) {
        Point pt = viewport.getViewPosition();
        position.setLocation(position.x - pt.x, position.y - pt.y);
        viewport.scrollRectToVisible(position);
    }
}
