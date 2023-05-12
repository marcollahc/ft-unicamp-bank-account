/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Movement;
import Model.Movement;
import java.util.List;

/**
 *
 * @author marcos-medeiros
 */
public class MovementTableModel extends GenericTableModel {
    public MovementTableModel(List v_data) {
        super(v_data, new String[]{"ID", "Origem", "Banco destino", "Agência destino", "Conta destino", "Tipo", "Valor"});
    }
    
    @Override
    public Class<?> getColumnClass(int column_index) {
        switch (column_index) {
            case 0:
                return Integer.class;
            case 1:
                return Integer.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
            case 4:
                return Integer.class;
            case 5:
                return String.class;
            case 6:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("column_index out of bounds");
        }
    }
    
    @Override
    public Object getValueAt(int row_index, int column_index) {
        Movement movement = (Movement) v_data.get(row_index);
        
        switch (column_index) {
            case 0:
                return movement.getId();
            case 1:
                return movement.getAccountId();
            case 2:
                return movement.getBank();
            case 3:
                return movement.getAgency();
            case 4:
                return movement.getAccount();
            case 5:
                return movement.getMovementType();
            case 6:
                return movement.getAmount();
            default:
                throw new IndexOutOfBoundsException("column_index out of bounds");
        }
    }
    
    @Override
    public boolean isCellEditable(int row_index, int column_index) {
        return false;
    }
}
