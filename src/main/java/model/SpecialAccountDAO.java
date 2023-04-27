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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mariana
 */
public class SpecialAccountDAO extends DAO{
    private static SpecialAccountDAO instance;

    private SpecialAccountDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static SpecialAccountDAO getInstance() {
        return (instance==null?(instance = new SpecialAccountDAO()):instance);
    }

// CRUD    
    public SpecialAccount create(int idContaComum, double limiteCredito) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO special_account (idContaComum, limiteCredito) VALUES (?, ?)");
            stmt.setInt(1, idContaComum);
            stmt.setDouble(2, limiteCredito);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(SpecialAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("special_account","idContaComum"));
    }
    
    private SpecialAccount buildObject(ResultSet rs) {
        SpecialAccount special_account = null;
        
        try {                  
            special_account = new SpecialAccount(rs.getInt("idContaComum"),rs.getDouble("limiteCredito"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return special_account;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<SpecialAccount> special_accounts = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                special_accounts.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return special_accounts;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM special_account");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM special_account WHERE idContaComum = " + lastId("special_account","idContaComum"));
    }

    // RetrieveById
    public SpecialAccount retrieveById(int idContaComum) {
        List<SpecialAccount> special_accounts = this.retrieve("SELECT * FROM special_account WHERE idContaComum = " + idContaComum);
        return (special_accounts.isEmpty()?null:special_accounts.get(0));
    }    
        
    // Updade
    public void update(SpecialAccount special_account) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE special_account SET limiteCredito=? WHERE idContaComum=?");
            stmt.setDouble(1, special_account.getLimiteCredito());
            stmt.setInt(2, special_account.getIdContaComum());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(SpecialAccount special_account) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM special_account WHERE limiteCredito = ?");
            stmt.setInt(1, special_account.getIdContaComum());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
  }