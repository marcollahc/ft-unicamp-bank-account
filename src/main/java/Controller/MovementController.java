package Controller;

import Model.Movement;
import Model.MovementDAO;
import java.util.List;

/**
 *
 * @author marcos-medeiros
 */
public class MovementController {    
    public static List<Movement> listMovementsAccount(int accountId) {
        return MovementDAO.getInstance().retrieveByAccountId(accountId);
    }
}
