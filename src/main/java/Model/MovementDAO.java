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
    
    public final static int MOVEMENT_CANCELED = 0;
    public final static int MOVEMENT_ACTIVE = 1;

    private MovementDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static MovementDAO getInstance() {
        return (instance==null?(instance = new MovementDAO()):instance);
    }

    // CRUD    
    public Movement create(int accountId, int bank, int agency, int account, double amount, Calendar operationDate, String movementType) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO movement (accountId, bank, agency, account, amount, operationDate, movementType, situation) VALUES (?,?,?,?,?,?,?,?)");
            stmt.setInt(1, accountId);
            stmt.setInt(2, bank);
            stmt.setInt(3, agency);
            stmt.setInt(4, account);
            stmt.setDouble(5, amount);
            stmt.setDate(6, new Date(operationDate.getTimeInMillis()));
            stmt.setString(7, movementType);
            stmt.setInt(8, MOVEMENT_ACTIVE);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(MovementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("movement","id"));
    }

    private Movement buildObject(ResultSet rs) {
        Movement movement = null;
        try {
            Calendar operationDate = Calendar.getInstance();
            operationDate.setTime(rs.getDate("operationDate"));
                    
            movement = new Movement(
                rs.getInt("id"),
                rs.getInt("accountId"),
                rs.getInt("bank"),
                rs.getInt("agency"),
                rs.getInt("account"),
                rs.getDouble("amount"),
                operationDate,
                rs.getInt("situation"),
                rs.getString("movementType")
            );
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
    
    // RetrieveById
    public List retrieveByAccountId(int accountId) {
        return this.retrieve("SELECT * FROM movement WHERE accountId = " + accountId);
    }   
        
    // Updade
    public void cancel(Movement movement) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE movement SET situation=? WHERE id=?");
            stmt.setInt(1, MOVEMENT_CANCELED);
            stmt.setInt(2, movement.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
