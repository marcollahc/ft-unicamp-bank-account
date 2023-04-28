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
import static Model.DAO.getConnection;

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
    public CommonAccount create(int bank, int agency, int account, Calendar openDate, double balance, double limitTransaction) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO commonAccount (bank, agency, account, openDate, balance, limitTransaction) VALUES (?,?,?,?,?,?)");
            stmt.setInt(1, bank);
            stmt.setInt(2, agency);
            stmt.setInt(3, account);
            stmt.setDate(4, new Date(openDate.getTimeInMillis()));
            stmt.setDouble(5, balance);
            stmt.setDouble(6, limitTransaction);
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
                    
            commonAccount = new CommonAccount(rs.getInt("id"),rs.getInt("bank"), rs.getInt("agency"),rs.getInt("account"),dt,rs.getDouble("balance"),rs.getDouble("limitTransaction"));
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
            stmt = DAO.getConnection().prepareStatement("UPDATE movement SET bank=?, agency=?, account=?, openDate=?, balance=?, limitTransaction=? WHERE id=?");
            stmt.setInt(1, commonAccount.getBank());
            stmt.setInt(2, commonAccount.getAgency());
            stmt.setInt(3, commonAccount.getAccount());
            stmt.setDate(5, new Date(commonAccount.getOpenDate().getTimeInMillis()));
            stmt.setDouble(4, commonAccount.getBalance());
            stmt.setDouble(4, commonAccount.getLimitTransaction());
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
            stmt = DAO.getConnection().prepareStatement("DELETE FROM commonAccount WHERE account = ?");
            stmt.setInt(1, commonAccount.getAccount());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
