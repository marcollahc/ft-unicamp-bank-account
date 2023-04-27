/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Mariana
 */
public class Client {
    private int id;
    private String nome;
    private String cpf;
    private Calendar dataNasc;

//Construtor

    public Client(int id, String nome, String cpf, Calendar dataNasc) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNasc = dataNasc;
    }
    
//Getters and Setters

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Calendar getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Calendar dataNasc) {
        this.dataNasc = dataNasc;
    }
  
}


  