/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.Account;
import java.util.List;

/**
 *
 * @author marcos-medeiros
 */
public class AccountTableModel extends GenericTableModel {
    public AccountTableModel(List v_data) {
        super(v_data, new String[]{"ID", "Agência", "Conta", "Saldo",  "Tipo", "Limite operação", "Aniversário conta", "Limite crédito"});
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
                return Double.class;
            case 4:
                return String.class;
            case 5:
                return Double.class;
            case 6:
                return Integer.class;
            case 7:
                return Double.class;
            default:
                throw new IndexOutOfBoundsException("column_index out of bounds");
        }
    }
    
    @Override
    public Object getValueAt(int row_index, int column_index) {
        Account account = (Account) v_data.get(row_index);
        
        switch (column_index) {
            case 0:
                return account.getId();
            case 1:
                return account.getAgency();
            case 2:
                return account.getAccount();
            case 3:
                return account.getBalance();
            case 4:
                return account.getAccountType();
            case 5:
                return account.getLimitTransaction();
            case 6:
                return account.getBirthdayAccount();
            case 7:
                return account.getCreditLimit();            
            default:
                throw new IndexOutOfBoundsException("column_index out of bounds");
        }
    }
    
    @Override
    public boolean isCellEditable(int row_index, int column_index) {
        return false;
    }
}
