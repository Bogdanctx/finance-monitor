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

public class PlainTextExportVisitor implements Visitor {

    @Override
    public void visitREST() {
        final File file = Service.createFile("REPORT.txt");
        List<String> lines = new ArrayList<>();

        for(Transaction t: ControllerFactory.transactionController.getTransactions()) {
            lines.add(t.toString());
        }

        for(Account a: ControllerFactory.accountController.getAccounts()) {
            lines.add(a.toString());
        }

        for(Goal g: ControllerFactory.goalController.getGoals()) {
            lines.add(g.toString());
        }

        Service.writeToFile(file, lines);
    }

    @Override
    public void visitAccountController(AccountController accountController) {
        final File file = Service.createFile("ACCOUNTS.txt");
        List<String> lines = new ArrayList<>();


        for(Account acc: accountController.getAccounts())
        {
            lines.add(acc.toString());
        }

        Service.writeToFile(file, lines);
    }

    @Override
    public void visitTransactionController(TransactionController transactionController) {
        final File file = Service.createFile("TRANSACTIONS.txt");
        List<String> lines = new ArrayList<>();

        for(Transaction t: transactionController.getTransactions())
        {
            lines.add(t.toString());
        }

        Service.writeToFile(file, lines);
    }

    @Override
    public void visitGoalController(GoalController goalController) {
        final File file = Service.createFile("GOALS.txt");
        List<String> lines = new ArrayList<>();

        for(Goal g: goalController.getGoals())
        {
            lines.add(g.toString());
        }

        Service.writeToFile(file, lines);
    }
}
