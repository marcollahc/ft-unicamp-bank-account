/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ft.unicamp.bank.account;

import Model.Client;
import java.util.Calendar;
import Model.ClientDAO;
import Model.CommonAccount;
import Model.CommonAccountDAO;
import Model.Movement;
import Model.MovementDAO;
import java.util.List;

/**
 *
 * @author marcos-medeiros
 */
public class FtUnicampBankAccount {
    public static void main(String[] args) {
        Calendar birthdate = Calendar.getInstance();
        birthdate.set(2023, Calendar.APRIL, 26);
    
        //Client
        System.out.println("Client");
        ClientDAO.getInstance().create("Antônio José da Silva", "12345678981", birthdate);
        ClientDAO.getInstance().create("José Antônio da Silva", "12345678982", birthdate);
        System.out.println(ClientDAO.getInstance().retrieveAll());
        
        Client client_last = (Client) ClientDAO.getInstance().retrieveLast().get(0);
        
        Client client_by_id = ClientDAO.getInstance().retrieveById(client_last.getId());
        
        System.out.println(client_by_id.getName());
        System.out.println(ClientDAO.getInstance().retrieveBySimilarName("Antônio"));
        
        client_by_id.setName("Josué Antônio da Silva");
        ClientDAO.getInstance().update(client_by_id);
        
        client_by_id = ClientDAO.getInstance().retrieveById(client_last.getId());
        System.out.println(client_by_id.getName());
        
        // ClientDAO.getInstance().delete(client_by_id);
        
        // CommonAccount
        System.out.println("CommonAccount");
        Calendar openDate = Calendar.getInstance();
        openDate.set(2023, Calendar.APRIL, 26);
        
        CommonAccountDAO.getInstance().create(client_by_id.getId(), 1, 1, 1, openDate, 0, 1000);
        System.out.println(CommonAccountDAO.getInstance().retrieveAll());
        
        CommonAccount common_account_last = (CommonAccount) CommonAccountDAO.getInstance().retrieveLast().get(0);
        
        CommonAccount common_account_by_id = CommonAccountDAO.getInstance().retrieveById(common_account_last.getId());
        
        System.out.println(common_account_by_id.getAccount());
        System.out.println(CommonAccountDAO.getInstance().retrieveByClientId(client_by_id.getId()));
        
        common_account_by_id.setAccount(123);
        CommonAccountDAO.getInstance().update(common_account_by_id);
        
        common_account_by_id = CommonAccountDAO.getInstance().retrieveById(common_account_last.getId());
        System.out.println(common_account_by_id.getAccount());
        
        CommonAccountDAO.getInstance().delete(common_account_by_id);
        
        // Movement
        System.out.println("Movement");
        Calendar operationDate = Calendar.getInstance();
        openDate.set(2023, Calendar.APRIL, 26);
        
        MovementDAO.getInstance().create(common_account_by_id.getId(), 1, 1, 2, 2.00f, operationDate);
        System.out.println(MovementDAO.getInstance().retrieveAll());
        
        Movement movement_last = (Movement) MovementDAO.getInstance().retrieveLast().get(0);
        
        Movement movement_by_id = MovementDAO.getInstance().retrieveById(movement_last.getId());
        
        System.out.println(movement_by_id.getAmount());
        System.out.println(MovementDAO.getInstance().retrieveByAccountId(common_account_by_id.getId()));
        
        MovementDAO.getInstance().cancel(movement_by_id);
        System.out.println(movement_by_id.getAmount());
    }
}
