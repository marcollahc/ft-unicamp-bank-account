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

 
    private int idContaComum;
    private double limiteCredito;  
    
    //Construtor
    
    public SpecialAccount(int idContaComum, double limiteCredito) {
        this.idContaComum = idContaComum;
        this.limiteCredito = limiteCredito;
    }

          
    //Getters and Setters
    public int getIdContaComum() {
        return idContaComum;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
       
}
