package Controller;

import Model.Customer;
import Model.CustomerDAO;
import java.util.Calendar;


/**
 *
 * @author marcos-medeiros
 */

public class CustomerController {    
    public static void newCustomer(String name, String cpf, Calendar birthdate) {       
        Model.CustomerDAO.getInstance().create(name, cpf, birthdate);        
    }
    
    public static void retrieveByCPF(String cpf) {
        Model.CustomerDAO.getInstance().retrieveByCPF(cpf);
    }
}