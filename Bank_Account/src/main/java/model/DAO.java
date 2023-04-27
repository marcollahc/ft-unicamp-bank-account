/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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
    public static final String DB = "jdbc:sqlite:BankAccount.db";
    
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
            // Table client:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS client( 
                                                        id INTEGER PRIMARY KEY, 
                                                        nome VARCHAR, 
                                                        cpf VARCHAR, 
                                                        dataNasc DATE); 
                                                        """);
            executeUpdate(stmt);
            // Table movement:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS movement( 
                                                        id INTEGER PRIMARY KEY, 
                                                        banco INTEGER, 
                                                        conta INTEGER,
                                                        agencia INTEGER,
                                                        valor DOUBLE,
                                                        data DATE); 
                                                        """);
            executeUpdate(stmt);
            // Table commonAccount:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS commonAccount( 
                                                        id INTEGER PRIMARY KEY, 
                                                        banco INTEGER, 
                                                        agencia INTEGER,
                                                        conta INTEGER,
                                                        dataAbertura DATE,
                                                        saldo DOUBLE,
                                                        limiteTransacao DOUBLE);
                                                        """);
            executeUpdate(stmt);
            // Table savingsAccount:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS savingsAccount( 
                                                        id INTEGER PRIMARY KEY, 
                                                        aniversarioConta INTEGER); 
                                                        """);
            executeUpdate(stmt);        
            // Table specialAccount:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS specialAccount( 
                                                        id INTEGER PRIMARY KEY, 
                                                        limiteCredito DOUBLE); 
                                                        """);
            executeUpdate(stmt);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}

