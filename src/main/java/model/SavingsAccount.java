/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Mariana
 */
public class SavingsAccount {
    
    private int idContaComum;
    private int aniversarioConta;  
    
    //Construtor
    
    public SavingsAccount(int idContaComum, int aniversarioConta) {
        this.idContaComum = idContaComum;
        this.aniversarioConta = aniversarioConta;
    }
       
    //Getters and Setters
    public int getIdContaComum() {
        return idContaComum;
    }

    public int getAniversarioConta() {
        return aniversarioConta;
    }

    public void setAniversarioConta(int aniversarioConta) {
        this.aniversarioConta = aniversarioConta;
    }

 
       
}