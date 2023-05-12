/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Customer;
import Model.Customer;
import Model.CustomerDAO;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author marcos-medeiros
 */
public class CustomerTableModel extends GenericTableModel {
    public CustomerTableModel(List v_data) {
        super(v_data, new String[]{"ID", "Nome", "CPF", "Data de nascimento"});
    }
    
    @Override
    public Class<?> getColumnClass(int column_index) {
        switch (column_index) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("column_index out of bounds");
        }
    }
    
    @Override
    public Object getValueAt(int row_index, int column_index) {
        Customer customer = (Customer) v_data.get(row_index);
        
        switch (column_index) {
            case 0:
                return customer.getId();
            case 1:
                return customer.getName();
            case 2:
                return customer.getCpf();
            case 3:
                return customer.getBirthdate().getTime();
            default:
                throw new IndexOutOfBoundsException("column_index out of bounds");
        }
    }
    
    public void setValueAt(Object a_value, int row_index, int column_index) {
        Customer customer = (Customer) v_data.get(row_index);
        
        switch (column_index) {
            case 0:
                break;
            case 1:
                customer.setName((String) a_value);
                break;
            case 2:
                customer.setCpf((String) a_value);
                break;
            case 3:
                customer.setBirthdate((Calendar) a_value);
                break;
            default:
                throw new IndexOutOfBoundsException("column_index out of bounds");
        }
        
        CustomerDAO.getInstance().update(customer);
    }
    
    @Override
    public boolean isCellEditable(int row_index, int column_index) {
        return true;
    }
}
