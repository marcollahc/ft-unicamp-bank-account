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
public class SpecialAccount{ 
    private int idCommonAccount;
    private double creditLimit;  
    
    //Construtor    
    public SpecialAccount(int idCommonAccount, double creditLimit) {
        this.idCommonAccount = idCommonAccount;
        this.creditLimit = creditLimit;
    }
          
    //Getters and Setters
    public int getIdCommonAccount() {
        return idCommonAccount;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }       
}
