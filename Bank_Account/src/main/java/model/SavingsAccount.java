/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Mariana
 */
public class SavingsAccount{
    
    private int id;
    private int aniversarioConta;  
    
    //Construtor
    
       public SavingsAccount(int id, int aniversarioConta) {
        this.id = id;
        this.aniversarioConta = aniversarioConta;
    }
       
    //Getters and Setters

    public int getId() {
        return id;
    }

    public int getAniversarioConta() {
        return aniversarioConta;
    }

    public void setAniversarioConta(int aniversarioConta) {
        this.aniversarioConta = aniversarioConta;
    }

 
       
}