package Controller;
import Model.*;

/**
 *
 * @author marcos-medeiros
 * @author Gustavo Romagnolo
 */

public class AccountController {
	public void depositMoney(Account commonAccount, double amount) {
		AccountDAO.getInstance().updateBalance(
			commonAccount,
			amount,
			"Depósito",
			null,
			null,
			null
		);
	}

	public void withdrawMoney(Account commonAccount, double amount) {
		AccountDAO.getInstance().updateBalance(
			commonAccount,
			-amount,
			"Saque",
			null,
			null,
			null
		);
	}

	public static void moneyTransfer(
		Account senderAccount,
		double amount,
		Integer receiverBank,
		Integer receiverAgency,
		Integer receiverAccountNumber
	) {
		if (receiverBank == null || receiverAgency == null || receiverAccountNumber == null) {
			System.err.println("Erro: informe o banco, agência e conta do destinatário");
			return;
		}

		if (receiverBank == 286) {
			Account receiverAccount = AccountDAO.getInstance().retrieveByAccountNumber(receiverAccountNumber);

			if (receiverAccount == null) {
				System.err.println("Erro: destinatário não encontrado");
				return;
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
	}
}
