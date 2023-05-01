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
public class AccountDAO extends DAO {    
    private static AccountDAO instance;
    
    public final static int ACCOUNT_COMMON = 0;
    public final static int ACCOUNT_SAVINGS = 1;
    public final static int ACCOUNT_SPECIAL = 2;

    private AccountDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static AccountDAO getInstance() {
        return (instance == null ? (instance = new AccountDAO()) : instance);
    }

    // CRUD    
    public Account create(
            int customerId,
            int bank,
            int agency,
            int account,
            Calendar openDate,
            double balance,
            double limitTransaction,
            int accountType,
            int birthdayAccount,
            double creditLimit
    ) {
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement("""
                INSERT INTO commonAccount (
                    customerId,
                    bank,
                    agency,
                    account,
                    openDate,
                    balance,
                    accountType,
                    limitTransaction,
                    birthdayAccount,
                    creditLimit
                ) VALUES (
                    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
                )
            """);
            stmt.setInt(1, customerId);
            stmt.setInt(2, bank);
            stmt.setInt(3, agency);
            stmt.setInt(4, account);
            stmt.setDate(5, new Date(openDate.getTimeInMillis()));
            stmt.setDouble(6, balance);
            stmt.setInt(7, accountType);
            stmt.setDouble(8, limitTransaction);
            stmt.setInt(9, birthdayAccount);
            stmt.setDouble(10, creditLimit);
            executeUpdate(stmt);
        } catch (SQLException exception) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, exception);
        }
        
        return this.retrieveById(lastId("commonAccount","id"));
    }

    private Account buildObject(ResultSet rs) {
        Account commonAccount = null;
        
        try {
            Calendar openDate = Calendar.getInstance();
            openDate.setTime(rs.getDate("openDate"));
                    
            commonAccount = new Account(
                    rs.getInt("id"),
                    rs.getInt("customerId"),
                    rs.getInt("bank"),
                    rs.getInt("agency"),
                    rs.getInt("account"),
                    openDate,
                    rs.getDouble("balance"),
                    rs.getInt("accountType"),
                    rs.getDouble("limitTransaction"),
                    rs.getInt("birthdayAccount"),
                    rs.getDouble("creditLimit")
            );
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
        
        return commonAccount;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Account> commonAccount = new ArrayList();
        ResultSet rs = getResultSet(query);
        
        try {
            while (rs.next()) {
                commonAccount.add(buildObject(rs));
            }
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
        
        return commonAccount;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM commonAccount");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM commonAccount WHERE id = " + lastId("commonAccount", "id"));
    }

    // RetrieveById
    public Account retrieveById(int id) {
        List<Account> commonAccount = this.retrieve("SELECT * FROM commonAccount WHERE id = " + id);
        return (commonAccount.isEmpty()?null:commonAccount.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveByCustomerId(int customerId) {
        return this.retrieve("SELECT * FROM commonAccount WHERE customerId = " + customerId);
    }    
        
    // Updade
    public void update(Account commonAccount) {
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement("""
                UPDATE
                    commonAccount
                SET
                    customerId = ?,
                    bank = ?,
                    agency = ?,
                    account = ?,
                    openDate = ?,
                    balance = ?,
                    limitTransaction = ?,
                    birthdayAccount = ?,
                    creditLimit = ?
                WHERE 
                    id = ?
            """);
            stmt.setInt(1, commonAccount.getCustomerId());
            stmt.setInt(2, commonAccount.getBank());
            stmt.setInt(3, commonAccount.getAgency());
            stmt.setInt(4, commonAccount.getAccount());
            stmt.setDate(5, new Date(commonAccount.getOpenDate().getTimeInMillis()));
            stmt.setDouble(6, commonAccount.getBalance());
            stmt.setDouble(7, commonAccount.getLimitTransaction());
            stmt.setInt(8, commonAccount.getBirthdayAccount());
            stmt.setDouble(9, commonAccount.getCreditLimit());
            stmt.setInt(10, commonAccount.getId());
            executeUpdate(stmt);
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }
    
    // Delete   
    public void delete(Account commonAccount) {
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM commonAccount WHERE id = ?");
            stmt.setInt(1, commonAccount.getId());
            executeUpdate(stmt);
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }
    
    public void depositMoney(Account commonAccount, double amount) {
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement(
            "UPDATE commonAccount SET balance = balance + ?  WHERE id = ?"
            );
            stmt.setDouble(1, commonAccount.getBalance());
            stmt.setInt(2, commonAccount.getId());
            executeUpdate(stmt);
            
            Calendar operationDate = Calendar.getInstance();
            MovementDAO.getInstance().create(
                    commonAccount.getId(),
                    commonAccount.getBank(),
                    commonAccount.getAgency(),
                    commonAccount.getAccount(),
                    commonAccount.getBalance() + amount,
                    operationDate,
                    "Depósito"
            );
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }

    public void withdrawMoney(Account commonAccount, double amount) {
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement(
            "UPDATE commonAccount SET balance = balance + ? WHERE id = ?"
            );
            stmt.setDouble(1, commonAccount.getBalance());
            stmt.setInt(2, commonAccount.getId());
            executeUpdate(stmt);
            
            Calendar operationDate = Calendar.getInstance();
            MovementDAO.getInstance().create(
                    commonAccount.getId(),
                    commonAccount.getBank(),
                    commonAccount.getAgency(),
                    commonAccount.getAccount(),
                    commonAccount.getBalance() - amount,
                    operationDate,
                    "Saque"
            );
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }
}