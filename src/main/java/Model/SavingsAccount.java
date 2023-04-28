/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Mariana
 */
public class SavingsAccount {
    
    private int idCommonAccount;
    private int birthdayAccount;  
    
    //Construtor
    
    public SavingsAccount(int idCommonAccount, int birthdayAccount) {
        this.idCommonAccount = idCommonAccount;
        this.birthdayAccount = birthdayAccount;
    }
       
    //Getters and Setters
    public int getIdCommonAccount() {
        return idCommonAccount;
    }

    public int getAniversarioConta() {
        return birthdayAccount;
    }

    public void setAniversarioConta(int birthdayAccount) {
        this.birthdayAccount = birthdayAccount;
    }
}