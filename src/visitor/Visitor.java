package visitor;

import controller.AccountController;
import controller.GoalController;
import controller.TransactionController;

public interface Visitor {
    void visitAccountController(AccountController accountController);
    void visitTransactionController(TransactionController transactionController);
    void visitGoalController(GoalController goalController);
    void visitREST();
}
