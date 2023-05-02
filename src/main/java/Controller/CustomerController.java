package Controller;

import Model.Customer;
import Model.CustomerDAO;
import java.util.Calendar;
import java.util.List;


/**
 *
 * @author marcos-medeiros
 */

public class CustomerController {    
    public static void newCustomer(String name, String cpf, Calendar birthdate) {       
        CustomerDAO.getInstance().create(name, cpf, birthdate);        
    }
    
    public static List<Customer> retrieveAllCustumers() {
        return CustomerDAO.getInstance().retrieveAll();
    }
    
    public static Customer retrieveByCPF(String cpf) {
        return CustomerDAO.getInstance().retrieveByCPF(cpf);
    }
    
    public static void editCustomer(Customer customer) {
        CustomerDAO.getInstance().update(customer);
    }
    
    public static void deleteCustomer(Customer customer) {
        CustomerDAO.getInstance().delete(customer);
    }
}