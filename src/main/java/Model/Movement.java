package Model;

import java.util.Calendar;

/**
 *
 * @author Mariana
 */
public class Movement {
    private int id;
    private int accountId;
    private int bank;
    private int agency;
    private int account;
    private double amount;
    private Calendar operationDate;
    private int situation;
    String movementType;


    //Construtor
    public Movement(
        int id,
        int accountId,
        int bank,
        int agency,
        int account,
        double amount,
        Calendar operationDate,
        int situation,
        String movementType
    ) {
        this.id = id;
        this.accountId = accountId;
        this.bank = bank;
        this.agency = agency;
        this.account = account;
        this.amount = amount;
        this.operationDate = operationDate;
        this.situation = situation;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }
    
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Calendar getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Calendar operationDate) {
        this.operationDate = operationDate;
    }
    
    public int getSituation() {
        return situation;
    }

    public void setSituation(int situation) {
        this.situation = situation;
    }
    
    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }
}