/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Calendar;

/**
 *
 * @author Mariana
 */
public class CommonAccount {
    private int id;
    private int clientId;
    private int bank;
    private int agency;
    private int account;
    private Calendar openDate;
    private double balance;
    private double limitTransaction;
    
    //Construtor
    public CommonAccount(int id, int clientId, int bank, int agency, int account, Calendar openDate, double balance, double limitTransaction) {
        this.id = id;
        this.clientId = clientId;
        this.bank = bank;
        this.agency = agency;
        this.account = account;
        this.openDate = openDate;
        this.balance = balance;
        this.limitTransaction = limitTransaction;
    }
    
    //Getters and Setters
    public int getId() {
        return id;
    }
    
    public int getClientId() {
        return clientId;
    }
    
    public int setClientId() {
        return clientId;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public int getAgency() {
        return agency;
    }

    public void setAgency(int agency) {
        this.agency = agency;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public Calendar getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Calendar openDate) {
        this.openDate = openDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getLimitTransaction() {
        return limitTransaction;
    }

    public void setLimitTransaction(double limitTransaction) {
        this.limitTransaction = limitTransaction;
    }
}
