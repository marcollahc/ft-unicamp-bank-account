package Controller;
import Model.Account;
import Model.AccountDAO;
import Model.MovementDAO;

import java.util.Calendar;
import java.util.List;

/**
 *
 * @author marcos-medeiros
 * @author Gustavo Romagnolo
 */

public class AccountController {
    public static Account tempAccount = null;
    
    public static Account getTempAccount() {
        return AccountController.tempAccount;
    }
    
    public static void setTempAccount(Object tempAccount) {
        AccountController.tempAccount = (Account) tempAccount;
    }
    
    public static void createAccount (
        int customerId,
        int type,
        int agency,
        int account,
        double limit,
        int birthdayAccount,
        double creditLimit
    ) {
        Calendar openDate = Calendar.getInstance();
        openDate.getTime();
        
        AccountDAO.getInstance().create(
                customerId, 
                286, 
                agency, 
                account, 
                openDate, 
                0, 
                limit, 
                type, 
                birthdayAccount, 
                creditLimit,
                true
        );
    }
    
    public static List<Account> retrieveCustomerAccount(int customerId) {
        return AccountDAO.getInstance().retrieveByCustomerId(customerId);
    }
    
    public static Account retrieveAccountById(int accountId) {
        return AccountDAO.getInstance().retrieveById(accountId);
    }
    
    public static void updateAccount (Account accountObject, int agency, int account, double limit, double creditLimit, int birthdayAccount) {
        accountObject.setAgency(agency);
        accountObject.setAccount(account);
        accountObject.setLimitTransaction(limit);
        
        if (accountObject.getAccountType() == 1) {
            accountObject.setCreditLimit(creditLimit);
        }
        
        if (accountObject.getAccountType() == 2) {
            accountObject.setBirthdayAccount(birthdayAccount);
        }
        
        AccountDAO.getInstance().update(accountObject);
    }
    
    public static void inactiveAccount (Account accountObject) {
        AccountDAO.getInstance().inactiveAccount(accountObject);
    }
    
    public static void depositMoney(Account customerAccount, double amount) {
        AccountDAO.getInstance().updateBalance(
            customerAccount,
            amount,
            MovementDAO.MOVEMENT_DEPOSIT,
            null,
            null,
            null
        );
    }

    public static void withdrawMoney(Account customerAccount, double amount) throws Exception {
        boolean accountOperationIsValid = AccountController.accountOperationIsValid(customerAccount, amount);
        
        if (accountOperationIsValid) {
            AccountDAO.getInstance().updateBalance(
                customerAccount,
                amount,
                MovementDAO.MOVEMENT_WITHDRAW,
                null,
                null,
                null
            );
        } else {
            throw new Exception("Erro: Saldo insuficiente!");
        }
    }

    public static void moneyTransfer(
        Account senderAccount,
        double amount,
        Integer receiverBank,
        Integer receiverAgency,
        Integer receiverAccountNumber
    ) {
        try {
            /* System.out.println("amount: " + amount);
            System.out.println("receiverBank: " + receiverBank);
            System.out.println("receiverAgency: " + receiverAgency);
            System.out.println("receiverAccountNumber: " + receiverAccountNumber); */
        
            transferValidations(
                senderAccount,
                amount,
                receiverBank,
                receiverAgency,
                receiverAccountNumber
            );
            
            AccountDAO.getInstance().updateBalance(
                senderAccount,
                amount,
                MovementDAO.MOVEMENT_WITHDRAW,
                receiverBank,
                receiverAgency,
                receiverAccountNumber
            );

            if (receiverBank == 286) {
                Account receiverAccount = AccountDAO.getInstance().retrieveByAccountNumber(receiverAccountNumber);
                
                if (receiverAccount == null) {
                    throw new Exception("Erro: destinatário não encontrado");
                }	

                AccountDAO.getInstance().updateBalance(
                    receiverAccount,
                    amount,
                    MovementDAO.MOVEMENT_DEPOSIT,
                    senderAccount.getBank(),
                    senderAccount.getAgency(),
                    senderAccount.getAccount()
                );
            }
        } catch (Exception exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }

    private static boolean accountOperationIsValid(Account customerAccount, double amount) {
        boolean isCommonAccount = (customerAccount.getAccountType() == AccountDAO.ACCOUNT_COMMON);
        boolean isSpecialAccount = (customerAccount.getAccountType() == AccountDAO.ACCOUNT_SPECIAL);
        boolean isSavingsAccount = (customerAccount.getAccountType() == AccountDAO.ACCOUNT_SAVINGS);
        
        System.out.println("isCommonAccount: " + isCommonAccount);
        System.out.println("isSpecialAccount: " + isSpecialAccount);
        System.out.println("isSavingsAccount: " + isSavingsAccount);

        boolean operationGreaterThanBalance = (amount > customerAccount.getBalance());
        boolean operationGreaterThanBalanceAndLimit = (amount > customerAccount.getBalance() + customerAccount.getCreditLimit());
        boolean operationLimitTransfer = (amount > customerAccount.getLimitTransaction());
        
        System.out.println("operationGreaterThanBalance: " + operationGreaterThanBalance);
        System.out.println("operationGreaterThanBalanceAndLimit: " + operationGreaterThanBalanceAndLimit);
        System.out.println("operationLimitTransfer: " + operationLimitTransfer);

        boolean commonAccountOperationIsValid = (isCommonAccount && !operationGreaterThanBalance && !operationLimitTransfer);
        boolean specialAccountOperationIsValid = (isSpecialAccount && !operationGreaterThanBalanceAndLimit && !operationLimitTransfer);
        boolean savingsAccountOperationIsValid = (isSavingsAccount && !operationGreaterThanBalance && !operationLimitTransfer);
        
        System.out.println("commonAccountOperationIsValid: " + commonAccountOperationIsValid);
        System.out.println("specialAccountOperationIsValid: " + specialAccountOperationIsValid);
        System.out.println("savingsAccountOperationIsValid: " + savingsAccountOperationIsValid);

        return (commonAccountOperationIsValid || specialAccountOperationIsValid || savingsAccountOperationIsValid);
    }

    private static boolean transferValidations(
        Account senderAccount,
        double amount,
        Integer receiverBank,
        Integer receiverAgency,
        Integer receiverAccountNumber
    ) throws Exception {
        boolean accountOperationIsValid = AccountController.accountOperationIsValid(senderAccount, amount);

        if (receiverBank == null || receiverAgency == null || receiverAccountNumber == null) {
            throw new Exception("Erro: informe o banco, agência e conta do destinatário");
        }

        if (!accountOperationIsValid) {
            throw new Exception("Erro 1: Saldo insuficiente!");
        }

        return true;
    }
}
