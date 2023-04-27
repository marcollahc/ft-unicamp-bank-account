/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ft.unicamp.bank.account;

import java.util.Calendar;
import model.ClientDAO;

/**
 *
 * @author marcos-medeiros
 */
public class FtUnicampBankAccount {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 26);
    
        //TESTE - Criando um novo cliente      
        ClientDAO.getInstance().create("José Antônio da Silva", "12345678982", calendar);
        System.out.println(ClientDAO.getInstance().retrieveAll());
    }
}
