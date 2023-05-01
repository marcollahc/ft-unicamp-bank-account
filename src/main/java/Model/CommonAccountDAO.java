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
    public CommonAccount create(int customerId, int bank, int agency, int account, Calendar openDate, double balance, double limitTransaction) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO commonAccount (customerId, bank, agency, account, openDate, balance, limitTransaction) VALUES (?,?,?,?,?,?,?)");
            stmt.setInt(1, customerId);
            stmt.setInt(2, bank);
            stmt.setInt(3, agency);
            stmt.setInt(4, account);
            stmt.setDate(5, new Date(openDate.getTimeInMillis()));
            stmt.setDouble(6, balance);
            stmt.setDouble(7, limitTransaction);
            executeUpdate(stmt);
        } catch (SQLException exception) {
            Logger.getLogger(CommonAccountDAO.class.getName()).log(Level.SEVERE, null, exception);
        }
        return this.retrieveById(lastId("commonAccount","id"));
    }

    private CommonAccount buildObject(ResultSet rs) {
        CommonAccount commonAccount = null;
        try {
            Calendar dt = Calendar.getInstance();
            dt.setTime(rs.getDate("openDate"));
                    
            commonAccount = new CommonAccount(rs.getInt("id"), rs.getInt("customerId"),rs.getInt("bank"), rs.getInt("agency"),rs.getInt("account"),dt,rs.getDouble("balance"),rs.getDouble("limitTransaction"));
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
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
        return this.retrieve("SELECT * FROM commonAccount WHERE id = " + lastId("commonAccount","id"));
    }

    // RetrieveById
    public CommonAccount retrieveById(int id) {
        List<CommonAccount> commonAccount = this.retrieve("SELECT * FROM commonAccount WHERE id = " + id);
        return (commonAccount.isEmpty()?null:commonAccount.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveByCustomerId(int customerId) {
        return this.retrieve("SELECT * FROM commonAccount WHERE customerId = " + customerId);
    }    
        
    // Updade
    public void update(CommonAccount commonAccount) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE commonAccount SET customerId=?, bank=?, agency=?, account=?, openDate=?, balance=?, limitTransaction=? WHERE id=?");
            stmt.setInt(1, commonAccount.getCustomerId());
            stmt.setInt(2, commonAccount.getBank());
            stmt.setInt(3, commonAccount.getAgency());
            stmt.setInt(4, commonAccount.getAccount());
            stmt.setDate(5, new Date(commonAccount.getOpenDate().getTimeInMillis()));
            stmt.setDouble(6, commonAccount.getBalance());
            stmt.setDouble(7, commonAccount.getLimitTransaction());
            stmt.setInt(8, commonAccount.getId());
            executeUpdate(stmt);
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }
    
    // Delete   
    public void delete(CommonAccount commonAccount) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM commonAccount WHERE id = ?");
            stmt.setInt(1, commonAccount.getId());
            executeUpdate(stmt);
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }
    
    public void depositMoney(CommonAccount commonAccount, double amount) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement(
                "UPDATE commonAccount SET balance = balance + ?  WHERE id = ?");
            stmt.setDouble(1, commonAccount.getBalance());
            stmt.setInt(3, commonAccount.getId());
            executeUpdate(stmt);
            
            Calendar operationDate = Calendar.getInstance();
            MovementDAO.getInstance().create(
                    commonAccount.getId(),
                    commonAccount.getBank(),
                    commonAccount.getAgency(),
                    commonAccount.getAccount(),
                    commonAccount.getBalance() + amount,
                    operationDate
            );
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }

    public void withdrawMoney(CommonAccount commonAccount, double amount) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement(
                "UPDATE commonAccount SET balance = balance + ?  WHERE id = ?");
            stmt.setDouble(1, commonAccount.getBalance());
            stmt.setInt(3, commonAccount.getId());
            executeUpdate(stmt);
            
            Calendar operationDate = Calendar.getInstance();
            MovementDAO.getInstance().create(
                    commonAccount.getId(),
                    commonAccount.getBank(),
                    commonAccount.getAgency(),
                    commonAccount.getAccount(),
                    commonAccount.getBalance() - amount,
                    operationDate
            );
        } catch (SQLException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }
}