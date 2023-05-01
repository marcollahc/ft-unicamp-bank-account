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
                INSERT INTO account (
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
        
        return this.retrieveById(lastId("account","id"));
    }

    private Account buildObject(ResultSet rs) {
        Account account = null;
        
        try {
            Calendar openDate = Calendar.getInstance();
            openDate.setTime(rs.getDate("openDate"));
                    
            account = new Account(
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
        
        return account;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Account> account = new ArrayList();
        ResultSet rs = getResultSet(query);
        
        try {
            while (rs.next()) {
                account.add(buildObject(rs));
            }
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
        
        return account;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM account");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM account WHERE id = " + lastId("account", "id"));
    }

    // RetrieveById
    public Account retrieveById(int id) {
        List<Account> account = this.retrieve("SELECT * FROM account WHERE id = " + id);
        return (account.isEmpty()?null:account.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveByCustomerId(int customerId) {
        return this.retrieve("SELECT * FROM account WHERE customerId = " + customerId);
    }    
        
    // Updade
    public void update(Account account) {
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement("""
                UPDATE
                    account
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
            stmt.setInt(1, account.getCustomerId());
            stmt.setInt(2, account.getBank());
            stmt.setInt(3, account.getAgency());
            stmt.setInt(4, account.getAccount());
            stmt.setDate(5, new Date(account.getOpenDate().getTimeInMillis()));
            stmt.setDouble(6, account.getBalance());
            stmt.setDouble(7, account.getLimitTransaction());
            stmt.setInt(8, account.getBirthdayAccount());
            stmt.setDouble(9, account.getCreditLimit());
            stmt.setInt(10, account.getId());
            executeUpdate(stmt);
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }
    
    // Delete   
    public void delete(Account account) {
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM account WHERE id = ?");
            stmt.setInt(1, account.getId());
            executeUpdate(stmt);
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }
    
    public void depositMoney(Account account, double amount) {
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement(
            "UPDATE account SET balance = balance + ?  WHERE id = ?"
            );
            stmt.setDouble(1, account.getBalance());
            stmt.setInt(2, account.getId());
            executeUpdate(stmt);
            
            Calendar operationDate = Calendar.getInstance();
            MovementDAO.getInstance().create(
                    account.getId(),
                    account.getBank(),
                    account.getAgency(),
                    account.getAccount(),
                    account.getBalance() + amount,
                    operationDate,
                    "Depósito"
            );
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }

    public void withdrawMoney(Account account, double amount) {
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement(
            "UPDATE account SET balance = balance + ? WHERE id = ?"
            );
            stmt.setDouble(1, account.getBalance());
            stmt.setInt(2, account.getId());
            executeUpdate(stmt);
            
            Calendar operationDate = Calendar.getInstance();
            MovementDAO.getInstance().create(
                    account.getId(),
                    account.getBank(),
                    account.getAgency(),
                    account.getAccount(),
                    account.getBalance() - amount,
                    operationDate,
                    "Saque"
            );
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }
}