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
public class ClientDAO extends DAO{
    private static ClientDAO instance;

    private ClientDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static ClientDAO getInstance() {
        return (instance==null?(instance = new ClientDAO()):instance);
    }

// CRUD    
    public Client create(String name, String cpf, Calendar birthdate) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO client (name, cpf, birthdate) VALUES (?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, cpf);
            stmt.setDate(3, new Date(birthdate.getTimeInMillis()));
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("client","id"));
    }
    
    private Client buildObject(ResultSet rs) {
        Client client = null;
        
        try {
            Calendar dt = Calendar.getInstance();
            dt.setTime(rs.getDate("birthdate"));                    
            client = new Client(rs.getInt("id"),rs.getString("name"), rs.getString("cpf"),dt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return client;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Client> clients = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                clients.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return clients;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM client");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM client WHERE id = " + lastId("client","id"));
    }

    // RetrieveById
    public Client retrieveById(int id) {
        List<Client> clients = this.retrieve("SELECT * FROM client WHERE id = " + id);
        return (clients.isEmpty()?null:clients.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String name) {
        return this.retrieve("SELECT * FROM client WHERE name LIKE '%" + name + "%'");
    }    
        
    // Updade
    public void update(Client client) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE client SET name=?, cpf=?, birthdate=? WHERE id=?");
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getCpf());
            stmt.setDate(3, new Date(client.getBirthdate().getTimeInMillis()));
            stmt.setInt(4, client.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    // Delete   
    public void delete(Client client) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM client WHERE id = ?");
            stmt.setInt(1, client.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}