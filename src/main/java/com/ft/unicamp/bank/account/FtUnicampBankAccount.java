/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ft.unicamp.bank.account;

import Model.Customer;
import java.util.Calendar;
import Model.CustomerDAO;
import Model.Account;
import Model.AccountDAO;
import static Model.AccountDAO.ACCOUNT_COMMON;
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
    
        //Customer
        System.out.println("Customer");
        CustomerDAO.getInstance().create("Antônio José da Silva", "12345678981", birthdate);
        CustomerDAO.getInstance().create("José Antônio da Silva", "12345678982", birthdate);
        System.out.println(CustomerDAO.getInstance().retrieveAll());
        
        Customer customer_last = (Customer) CustomerDAO.getInstance().retrieveLast().get(0);
        
        Customer customer_by_id = CustomerDAO.getInstance().retrieveById(customer_last.getId());
        
        System.out.println(customer_by_id.getName());
        System.out.println(CustomerDAO.getInstance().retrieveBySimilarName("Antônio"));
        
        customer_by_id.setName("Josué Antônio da Silva");
        CustomerDAO.getInstance().update(customer_by_id);
        
        customer_by_id = CustomerDAO.getInstance().retrieveById(customer_last.getId());
        System.out.println(customer_by_id.getName());
        
        // CustomerDAO.getInstance().delete(customer_by_id);
        
        // Account
        System.out.println("CommonAccount");
        Calendar openDate = Calendar.getInstance();
        openDate.set(2023, Calendar.APRIL, 26);
        
        AccountDAO.getInstance().create(customer_by_id.getId(), 286, 1, 1, openDate, 0, 0, ACCOUNT_COMMON, 0, 0);
        System.out.println(AccountDAO.getInstance().retrieveAll());
        
        Account common_account_last = (Account) AccountDAO.getInstance().retrieveLast().get(0);
        
        Account common_account_by_id = AccountDAO.getInstance().retrieveById(common_account_last.getId());
        
        System.out.println(common_account_by_id.getAccount());
        System.out.println(AccountDAO.getInstance().retrieveByCustomerId(customer_by_id.getId()));
        
        common_account_by_id.setAccount(123);
        AccountDAO.getInstance().update(common_account_by_id);
        
        common_account_by_id = AccountDAO.getInstance().retrieveById(common_account_last.getId());
        System.out.println(common_account_by_id.getAccount());
        
        // AccountDAO.getInstance().delete(common_account_by_id);
        
        // Movement
        System.out.println("Movement");
        Calendar operationDate = Calendar.getInstance();
        openDate.set(2023, Calendar.APRIL, 26);
        
        MovementDAO.getInstance().create(common_account_by_id.getId(), 50.00, operationDate, "Depósito", null, null, null);
        System.out.println(MovementDAO.getInstance().retrieveAll());
        
        Movement movement_last = (Movement) MovementDAO.getInstance().retrieveLast().get(0);
        
        Movement movement_by_id = MovementDAO.getInstance().retrieveById(movement_last.getId());
        
        System.out.println(movement_by_id.getAmount());
        //System.out.println(MovementDAO.getInstance().retrieveByAccountId(common_account_by_id.getId()));
        
        MovementDAO.getInstance().cancel(movement_by_id);
        System.out.println(movement_by_id.getAmount());

        //Deposit and Withdraw
        System.out.println("Deposit");
        //AccountDAO.getInstance().depositMoney(common_account_by_id, 50.00);
        System.out.println(MovementDAO.getInstance().retrieveAll());
        
        System.out.println("Withdraw");
        //AccountDAO.getInstance().withdrawMoney(common_account_by_id, 30.00);
        System.out.println(MovementDAO.getInstance().retrieveAll());
    }
}
