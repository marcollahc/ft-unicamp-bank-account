package Model;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mariana
 */
public class CustomerDAO extends DAO{
    private static CustomerDAO instance;

    private CustomerDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static CustomerDAO getInstance() {
        return (instance == null?(instance = new CustomerDAO()):instance);
    }

// CRUD    
    public Customer create(String name, String cpf, Calendar birthdate, boolean active) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO customer (name, cpf, birthdate, active) VALUES (?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, cpf);
            stmt.setDate(3, new Date(birthdate.getTimeInMillis()));
            stmt.setBoolean(4, active);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("customer","id"));
    }
    
    private Customer buildObject(ResultSet rs) {
        Customer customer = null;
        
        try {
            Calendar dt = Calendar.getInstance();
            dt.setTime(rs.getDate("birthdate"));                    
            customer = new Customer(rs.getInt("id"),rs.getString("name"), rs.getString("cpf"),dt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return customer;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Customer> customers = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                customers.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return customers;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM customer");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM customer WHERE id = " + lastId("customer","id"));
    }

    // RetrieveById
    public Customer retrieveById(int id) {
        List<Customer> customers = this.retrieve("SELECT * FROM customer WHERE id = " + id);
        return (customers.isEmpty()?null:customers.get(0));
    }
    
    public Customer retrieveByCPF(String cpf) {
        List<Customer> customers = this.retrieve("SELECT * FROM customer WHERE cpf = " + cpf);
        return (customers.isEmpty()?null:customers.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String name) {
        return this.retrieve("SELECT * FROM customer WHERE name LIKE '%" + name + "%'");
    }    
        
    // Updade
    public void update(Customer customer) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE customer SET name=?, cpf=?, birthdate=? WHERE id=?");
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getCpf());
            stmt.setDate(3, new Date(customer.getBirthdate().getTimeInMillis()));
            stmt.setInt(4, customer.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    // Inactivate   
    public void inactivateByCPF(String customerCPF) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("UPDATE customer SET active = 0 WHERE cpf = ?");
            stmt.setString(1, customerCPF);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}