package Model;

import java.util.Calendar;

/**
 *
 * @author Mariana
 */
public class Movement {
    private final int id;
    private int accountId;
    private double amount;
    private Calendar operationDate;
    private int situation;
    private String movementDescription;
    private Integer bank;
    private Integer agency;
    private Integer account;

    //Construtor
    public Movement(
        int id,
        int accountId,
        double amount,
        Calendar operationDate,
        int situation,
        String movementDescription,
        Integer bank,
        Integer agency,
        Integer account
    ) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.operationDate = operationDate;
        this.situation = situation;
        this.movementDescription = movementDescription;
        this.bank = bank;
        this.agency = agency;
        this.account = account;
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

    public Integer getBank() {
        return bank;
    }

    public void setBank(Integer bank) {
        this.bank = bank;
    }

    public Integer getAgency() {
        return agency;
    }

    public void setAgency(Integer agency) {
        this.agency = agency;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
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
        return movementDescription;
    }

    public void setMovementType(String movementDescription) {
        this.movementDescription = movementDescription;
    }
}