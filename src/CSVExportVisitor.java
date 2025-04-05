import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CSVExportVisitor implements Visitor {

    @Override
    public void visitAccountManager(AccountManager accountManager) {
        File file = Service.createFile("ACCOUNTS.csv");

        List<String> lines = new ArrayList<>();

        lines.add("account_name,balance,initial_balance,id");

        for(Account acc: accountManager.accounts)
        {
            lines.add(acc.getName() + "," + acc.getBalance() + "," + acc.getInitialBalance() + "," + acc.getId());
        }

        Service.writeToFile(file, lines);
    }

    @Override
    public void visitTransactionManager(TransactionManager transactionManager) {
        File file = Service.createFile("TRANSACTIONS.csv");

        List<String> lines = new ArrayList<>();

        // Transaction(double amount, TYPE type, String description, Account account)
        lines.add("amount,type,description,account_name");

        for(Transaction t: transactionManager.transactions)
        {
            lines.add(t.getAmount() + "," +
                    t.getType().toString() + "," +
                    t.getDescription() + "," +
                    ManagerFactory.getAccountManager().getAccountById(t.getAccountId()).getName());
        }

        Service.writeToFile(file, lines);
    }

    @Override
    public void visitGoalManager(GoalManager goalManager) {
        File file = Service.createFile("GOALS.csv");

        List<String> lines = new ArrayList<>();

        // Goal(String goal, double value, @optional Account account)
        lines.add("goal,value,description,account_name");

        for(Goal g: goalManager.goals)
        {
            Account account = ManagerFactory.getAccountManager().getAccountById(g.getAccountId());
            lines.add(g.getGoal() + "," + g.getValue() + "," + account.getName());
        }

        Service.writeToFile(file, lines);
    }

    /// NO CSV EXPORT AVAILABLE FOR MANAGER
    @Override
    public void visitManager() {}
}
