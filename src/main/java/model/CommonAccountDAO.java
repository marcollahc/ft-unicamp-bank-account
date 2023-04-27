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
import static model.DAO.getConnection;

/**
 *
 * @author Mariana
 */
public class CommonAccountDAO extends DAO {
         private static CommonAccountDAO instance;

    private CommonAccountDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static CommonAccountDAO getInstance() {
        return (instance==null?(instance = new CommonAccountDAO()):instance);
    }

// CRUD    
    public CommonAccount create(int banco, int agencia, int conta, Calendar dataAbertura, double saldo, double limiteTransacao) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO commonAccount (banco, agencia, conta, dataAbertura, saldo, limiteTransacao) VALUES (?,?,?,?,?,?)");
            stmt.setInt(1, banco);
            stmt.setInt(2, agencia);
            stmt.setInt(3, conta);
            stmt.setDate(4, new Date(dataAbertura.getTimeInMillis()));
            stmt.setDouble(5, saldo);
            stmt.setDouble(6, limiteTransacao);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(CommonAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("commonAccount","id"));
    }

    private CommonAccount buildObject(ResultSet rs) {
        CommonAccount commonAccount = null;
        try {
            Calendar dt = Calendar.getInstance();
            dt.setTime(rs.getDate("data"));
                    
            commonAccount = new CommonAccount(rs.getInt("id"),rs.getInt("banco"), rs.getInt("agencia"),rs.getInt("conta"),dt,rs.getDouble("saldo"),rs.getDouble("limiteTransacao"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return commonAccount;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<CommonAccount> commonAccount = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                commonAccount.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return commonAccount;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM commonAccount");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM commonAccount WHERE id = " + lastId("commonAccount","id"));
    }

    // RetrieveById
    public CommonAccount retrieveById(int id) {
        List<CommonAccount> commonAccount = this.retrieve("SELECT * FROM commonAccount WHERE id = " + id);
        return (commonAccount.isEmpty()?null:commonAccount.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM commonAccount WHERE nome LIKE '%" + nome + "%'");
    }    
        
    // Updade
    public void update(CommonAccount commonAccount) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE movement SET banco=?, agencia=?, conta=?, dataAbertura=?, saldo=?, limiteTransacao=? WHERE id=?");
            stmt.setInt(1, commonAccount.getBanco());
            stmt.setInt(2, commonAccount.getAgencia());
            stmt.setInt(3, commonAccount.getConta());
            stmt.setDate(5, new Date(commonAccount.getDataAbertura().getTimeInMillis()));
            stmt.setDouble(4, commonAccount.getSaldo());
            stmt.setDouble(4, commonAccount.getLimiteTransacao());
                stmt.setInt(6, commonAccount.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(CommonAccount commonAccount) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM commonAccount WHERE conta = ?");
            stmt.setInt(1, commonAccount.getConta());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
}
}
