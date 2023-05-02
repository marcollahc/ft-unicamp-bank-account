package Controller;
import Model.Account;
import Model.AccountDAO;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author marcos-medeiros
 * @author Gustavo Romagnolo
 */

public class AccountController {
    public static void createAccount (int customerId, int type, int agency, int account, double limit) {
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
                0, 
                0
        );
    }
    
    public static List<Account> retrieveCustomerAccount(int customerId) {
        return AccountDAO.getInstance().retrieveByCustomerId(customerId);
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
    
    public static void deleteAccount (Account accountObject) {
        AccountDAO.getInstance().delete(accountObject);
    }
    
    public static void depositMoney(Account customerAccount, double amount) {
        AccountDAO.getInstance().updateBalance(
            customerAccount,
            amount,
            "Depósito",
            null,
            null,
            null
        );
    }

    public static void withdrawMoney(Account customerAccount, double amount) {
            boolean accountOperationIsValid = AccountController.accountOperationIsValid(customerAccount, amount);
            if (accountOperationIsValid) {
                AccountDAO.getInstance().updateBalance(
                    customerAccount,
                    -amount,
                    "Saque",
                    null,
                    null,
                    null
                );
            } else {
                System.err.println("Saldo insuficiente!");
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
            transferValidations(
                senderAccount,
                amount,
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
                    "Transferência recebida de: " + senderAccount.getAccount(),
                    senderAccount.getBank(),
                    senderAccount.getAgency(),
                    senderAccount.getAccount()
                );
            }

            AccountDAO.getInstance().updateBalance(
                senderAccount,
                -amount,
                "Transferência realizada para: " + receiverAccountNumber,
                receiverBank,
                receiverAgency,
                receiverAccountNumber
            );
        } catch (Exception exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
    }

    private static boolean accountOperationIsValid(Account customerAccount, double amount) {
        boolean isCommonAccount = customerAccount.getAccountType() == 0;
        boolean isSpecialAccount = customerAccount.getAccountType() == 1;
        boolean operationGreaterThanBalance = amount > customerAccount.getBalance();
        boolean operationGreaterThanBalanceAndLimit = amount > customerAccount.getBalance() + customerAccount.getCreditLimit();

        boolean commonAccountOperationIsValid = isCommonAccount && !operationGreaterThanBalance;
        boolean specialAccountOperationIsValid = isSpecialAccount && !operationGreaterThanBalanceAndLimit;

        return commonAccountOperationIsValid || specialAccountOperationIsValid;
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

        if (accountOperationIsValid) {
            throw new Exception("Erro: Saldo insuficiente!");
        }

        return true;
    }
}
