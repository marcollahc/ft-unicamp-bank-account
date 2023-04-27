/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bank_account;

import java.util.Calendar;
import model.Client;
import model.ClientDAO;
import model.Movement;
import model.MovementDAO;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 *
 * @author Mariana
 */
public class Bank_Account {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 26);
    
    //TESTE - Criando um novo cliente
//        
//        ClientDAO.getInstance().create("José Antônio da Silva", "12345678982", calendar);
//        System.out.println(ClientDAO.getInstance().retrieveAll());
    }
}
