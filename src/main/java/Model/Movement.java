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
public class Movement {
    private int id;
    private int bank;
    private int agency;
    private int account;
    private double amount;
    private Calendar date;

    //Construtor
    public Movement(int id, int bank, int agency, int account, double amount, Calendar date) {
        this.id = id;
        this.bank = bank;
        this.agency = agency;
        this.account = account;
        this.amount = amount;
        this.date = date;
    }

    //Getters and Setters
    public int getId() {
        return id;
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

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}