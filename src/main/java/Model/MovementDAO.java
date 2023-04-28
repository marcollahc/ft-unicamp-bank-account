/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author Mariana
 */
public class MovementDAO extends DAO{
     private static MovementDAO instance;

    private MovementDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static MovementDAO getInstance() {
        return (instance==null?(instance = new MovementDAO()):instance);
    }

    // CRUD    
    public Movement create(int bank, int agency, int account, double amount, Calendar date) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO movement (bank, agency, account, amount, date) VALUES (?,?,?,?,?)");
            stmt.setInt(1, bank);
            stmt.setInt(2, agency);
            stmt.setInt(3, account);
            stmt.setDouble(4, amount);
            stmt.setDate(5, new Date(date.getTimeInMillis()));
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(MovementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("movement","id"));
    }

    private Movement buildObject(ResultSet rs) {
        Movement movement = null;
        try {
            Calendar dt = Calendar.getInstance();
            dt.setTime(rs.getDate("date"));
                    
            movement = new Movement(rs.getInt("id"),rs.getInt("bank"), rs.getInt("agency"),rs.getInt("account"),rs.getDouble("amount"),dt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return movement;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Movement> movement = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                movement.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return movement;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM movement");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM movement WHERE id = " + lastId("movement","id"));
    }

    // RetrieveById
    public Movement retrieveById(int id) {
        List<Movement> movement = this.retrieve("SELECT * FROM movement WHERE id = " + id);
        return (movement.isEmpty()?null:movement.get(0));
    }   
        
    // Updade
    public void update(Movement movement) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE movement SET bank=?, agency=?, account=?, amount=?, date=? WHERE id=?");
            stmt.setInt(1, movement.getBank());
            stmt.setInt(2, movement.getAgency());
            stmt.setInt(3, movement.getAccount());
            stmt.setDouble(4, movement.getAmount());
            stmt.setDate(5, new Date(movement.getDate().getTimeInMillis()));
            stmt.setInt(6, movement.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
