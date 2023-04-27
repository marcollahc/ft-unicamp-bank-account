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
public class Movement {

    private int id;
    private int banco;
    private int conta;
    private int agencia;
    private double valor;
    private Calendar data;


//Construtor

    public Movement(int id, int banco, int conta, int agencia, double valor, Calendar data) {
        this.id = id;
        this.banco = banco;
        this.conta = conta;
        this.agencia = agencia;
        this.valor = valor;
        this.data = data;
    }

//Getters and Setters

    public int getId() {
        return id;
    }

    public int getBanco() {
        return banco;
    }

    public void setBanco(int banco) {
        this.banco = banco;
    }

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }  
    
}