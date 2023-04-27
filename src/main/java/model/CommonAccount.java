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
public class CommonAccount {
    private int id;
    private int banco;
    private int agencia;
    private int conta;
    private Calendar dataAbertura;
    private double saldo;
    private double limiteTransacao;
    
    //Construtor

    public CommonAccount(int id, int banco, int agencia, int conta, Calendar dataAbertura, double saldo, double limiteTransacao) {
        this.id = id;
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
        this.dataAbertura = dataAbertura;
        this.saldo = saldo;
        this.limiteTransacao = limiteTransacao;
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

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    public Calendar getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Calendar dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimiteTransacao() {
        return limiteTransacao;
    }

    public void setLimiteTransacao(double limiteTransacao) {
        this.limiteTransacao = limiteTransacao;
    }

}
