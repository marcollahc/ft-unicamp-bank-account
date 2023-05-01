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
       
        Customer customer = Model.CustomerDAO.getInstance().create(name, cpf, birthdate);
        
    }
}
