package visitor;

import controller.AccountController;
import controller.ControllerFactory;
import controller.GoalController;
import controller.TransactionController;
import entity.Account;
import entity.Goal;
import entity.Service;
import entity.Transaction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CSVExportVisitor implements Visitor {

    @Override
    public void visitAccountController(AccountController accountController) {
        File file = Service.createFile("ACCOUNTS.csv");

        List<String> lines = new ArrayList<>();

        lines.add("account_name,balance,id");

        for(Account acc: accountController.getAccounts())
        {
            lines.add(acc.getName() + "," + acc.getBalance() + "," + acc.getId());
        }

        Service.writeToFile(file, lines);
    }

    @Override
    public void visitTransactionController(TransactionController transactionController) {
        File file = Service.createFile("TRANSACTIONS.csv");

        List<String> lines = new ArrayList<>();

        // entity.Transaction(double amount, TYPE type, String description, entity.Account account)
        lines.add("amount,type,description,account_name");

        for(Transaction t: transactionController.getTransactions())
        {
            lines.add(t.getAmount() + "," +
                    t.getType().toString() + "," +
                    t.getDescription() + "," +
                    ControllerFactory.accountController.getAccountById(t.getAccountId()).getName());
        }

        Service.writeToFile(file, lines);
    }

    @Override
    public void visitGoalController(GoalController goalController) {
        File file = Service.createFile("GOALS.csv");

        List<String> lines = new ArrayList<>();

        // entity.Goal(String goal, double value, @optional entity.Account account)
        lines.add("goal,value,description,account_name");

        for(Goal g: goalController.getGoals())
        {
            Account account = ControllerFactory.accountController.getAccountById(g.getAccountId());
            lines.add(g.getGoal() + "," + g.getValue() + "," + account.getName());
        }

        Service.writeToFile(file, lines);
    }

    /// NO CSV EXPORT AVAILABLE FOR MANAGER
    @Override
    public void visitREST() {}
}
