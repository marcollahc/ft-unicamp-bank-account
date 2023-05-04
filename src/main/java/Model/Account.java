package Model;

import java.util.Calendar;

/**
 *
 * @author Mariana
 * @author Gustavo Romagnolo
 */

public class Account {
    private int id;
    private int customerId;
    private int bank;
    private int agency;
    private int account;
    private Calendar openDate;
    private double balance;
    private int accountType;
    private double limitTransaction;
    private int birthdayAccount;
    private double creditLimit;
    private boolean active;
            
    //Construtor
    public Account(
        int id,
        int customerId,
        int bank,
        int agency,
        int account,
        Calendar openDate,
        double balance,
        int accountType,
        double limitTransaction,
        int birthdayAccount,
        double creditLimit,
        boolean active
    ) {
        this.id = id;
        this.customerId = customerId;
        this.bank = bank;
        this.agency = agency;
        this.account = account;
        this.openDate = openDate;
        this.balance = balance;
        this.accountType = accountType;
        this.limitTransaction = limitTransaction;
        this.birthdayAccount = birthdayAccount;
        this.creditLimit = creditLimit;
        this.active = active;
    }
    
    //Getters and Setters
    public int getId() {
        return id;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public int setCustomerId() {
        return customerId;
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
    
    public int getAccountType() {
        return accountType;
    }
    
    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public double getLimitTransaction() {
        return limitTransaction;
    }

    public void setLimitTransaction(double limitTransaction) {
        this.limitTransaction = limitTransaction;
    }
    
    public int getBirthdayAccount() {
        return birthdayAccount;
    }

    public void setBirthdayAccount(int birthdayAccount) {
        this.birthdayAccount = birthdayAccount;
    }
    
    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }
    
    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
