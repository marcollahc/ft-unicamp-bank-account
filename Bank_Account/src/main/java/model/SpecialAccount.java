/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Calendar;

/**
 *
 * @author Mariana
 */
public class SpecialAccount{

 
    private int id;
    private double limiteCredito;  
    
    //Construtor
    
       public SpecialAccount(int id, double limiteCredito) {
        this.id = id;
        this.limiteCredito = limiteCredito;
    }

          
    //Getters and Setters

    public int getId() {
        return id;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
       
}
