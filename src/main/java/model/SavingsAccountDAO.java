package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class SavingsAccountDAO extends DAO{
    private static SavingsAccountDAO instance;

    private SavingsAccountDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static SavingsAccountDAO getInstance() {
        return (instance==null?(instance = new SavingsAccountDAO()):instance);
    }

// CRUD    
    public SavingsAccount create(int idContaComum, int aniversarioConta) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO savings_account (idContaComum, aniversarioConta) VALUES (?, ?)");
            stmt.setInt(1, idContaComum);
            stmt.setInt(2, aniversarioConta);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(SavingsAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("savings_account","idContaComum"));
    }
    
    private SavingsAccount buildObject(ResultSet rs) {
        SavingsAccount savings_account = null;
        
        try {                 
            savings_account = new SavingsAccount(rs.getInt("idContaComum"), rs.getInt("aniversarioConta"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return savings_account;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<SavingsAccount> savings_accounts = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                savings_accounts.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return savings_accounts;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM savings_account");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM savings_account WHERE id = " + lastId("savings_account","idContaComum"));
    }

    // RetrieveById
    public SavingsAccount retrieveById(int idContaComum) {
        List<SavingsAccount> savings_accounts = this.retrieve("SELECT * FROM savings_account WHERE idContaComum = " + idContaComum);
        return (savings_accounts.isEmpty()?null:savings_accounts.get(0));
    }   
        
    // Updade
    public void update(SavingsAccount savings_account) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE savings_account SET aniversarioConta=? WHERE idContaComum=?");
            stmt.setInt(1, savings_account.getAniversarioConta());
            stmt.setInt(2, savings_account.getIdContaComum());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(SavingsAccount savings_account) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM savings_account WHERE idContaComum = ?");
            stmt.setInt(1, savings_account.getIdContaComum());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
  }