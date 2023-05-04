/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mariana
 */
public class DAO {
    public static final String DB = "jdbc:sqlite:FtUnicampBankAccount.db";
    
    private static Connection con;
    
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // Gera a conexão com o banco SQLite
    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(DB);
                if (con != null) {
                    DatabaseMetaData meta = con.getMetaData();
                }
            } catch (SQLException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
        return con;
    }

    protected ResultSet getResultSet(String query) {
        Statement s;
        ResultSet rs = null;
        try {
            s = (Statement) con.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return rs;
    }

    protected int executeUpdate(PreparedStatement queryStatement) throws SQLException {
        int update;
        update = queryStatement.executeUpdate();
        return update;
    }

    protected int lastId(String tableName, String primaryKey) {
        Statement s;
        int lastId = -1;
        try {
            s = (Statement) con.createStatement();
            ResultSet rs = s.executeQuery("SELECT MAX(" + primaryKey + ") AS id FROM " + tableName);
            if (rs.next()) {
                lastId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return lastId;
    }

    public static void terminar() {
        try {
            (DAO.getConnection()).close();
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Create table SQLite
    protected final boolean createTable() {
        try {
            PreparedStatement stmt;
            // Table customer:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS customer(
                                                        id INTEGER PRIMARY KEY,
                                                        name VARCHAR,
                                                        cpf VARCHAR,
                                                        active BOOLEAN,
                                                        birthdate DATE);
                                                        """);
            executeUpdate(stmt);
            // Table movement:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS movement( 
                                                        id INTEGER PRIMARY KEY,
                                                        accountId INTEGER,
                                                        bank INTEGER,
                                                        agency INTEGER,
                                                        account INTEGER,                                                 
                                                        amount DOUBLE,
                                                        operationDate DATE,
                                                        movementDescription TEXT,
                                                        situation INTEGER); 
                                                        """);
            executeUpdate(stmt);
            // Table account:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS account(
                                                        id INTEGER PRIMARY KEY,
                                                        customerId INTEGER,
                                                        bank INTEGER,
                                                        agency INTEGER,
                                                        account INTEGER,
                                                        openDate DATE,
                                                        balance DOUBLE,
                                                        accountType INTEGER,
                                                        limitTransaction DOUBLE,
                                                        birthdayAccount INTEGER,
                                                        creditLimit DOUBLE,
                                                        active BOOLEAN);
                                                        """);
            executeUpdate(stmt);
            // Table savingsAccount:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS savingsAccount( 
                                                        idCommonAccount INTEGER PRIMARY KEY, 
                                                        birthdayAccount INTEGER); 
                                                        """);
            executeUpdate(stmt);        
            // Table specialAccount:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS specialAccount( 
                                                        idCommonAccount INTEGER PRIMARY KEY, 
                                                        creditLimit DOUBLE); 
                                                        """);
            executeUpdate(stmt);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}

