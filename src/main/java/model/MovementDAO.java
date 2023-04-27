/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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
    public Movement create(int banco, int conta, int agencia, double valor, Calendar data) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO movement (banco, conta, agencia, valor, data) VALUES (?,?,?,?,?)");
            stmt.setInt(1, banco);
            stmt.setInt(2, conta);
            stmt.setInt(3, agencia);
            stmt.setDouble(4, valor);
            stmt.setDate(5, new Date(data.getTimeInMillis()));
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
            dt.setTime(rs.getDate("data"));
                    
            movement = new Movement(rs.getInt("id"),rs.getInt("banco"), rs.getInt("conta"),rs.getInt("agencia"),rs.getDouble("valor"),dt);
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

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM movement WHERE nome LIKE '%" + nome + "%'");
    }    
        
    // Updade
    public void update(Movement movement) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE movement SET banco=?, conta=?, agencia=?, valor=?, data=? WHERE id=?");
            stmt.setInt(1, movement.getBanco());
            stmt.setInt(2, movement.getConta());
            stmt.setInt(3, movement.getAgencia());
            stmt.setDouble(4, movement.getValor());
            stmt.setDate(5, new Date(movement.getData().getTimeInMillis()));
                stmt.setInt(6, movement.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Movement movement) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM movement WHERE conta = ?");
            stmt.setInt(1, movement.getConta());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
}
}
