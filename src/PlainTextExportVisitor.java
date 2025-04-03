import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlainTextExportVisitor implements Visitor {

    @Override
    public void visitManager() {
        final File file = Service.createFile("REPORT.txt");
        List<String> lines = new ArrayList<>();

        for(Transaction t: ManagerFactory.getTransactionManager().transactions) {
            lines.add(t.toString());
        }

        for(Account a: ManagerFactory.getAccountManager().accounts) {
            lines.add(a.toString());
        }

        for(Goal g: ManagerFactory.getGoalManager().goals) {
            lines.add(g.toString());
        }

        Service.writeToFile(file, lines);
    }

    @Override
    public void visitAccountManager(AccountManager accountManager) {
        final File file = Service.createFile("ACCOUNTS.txt");
        List<String> lines = new ArrayList<>();

        for(Account acc: accountManager.accounts)
        {
            lines.add(acc.toString());
        }

        Service.writeToFile(file, lines);
    }

    @Override
    public void visitTransactionManager(TransactionManager transactionManager) {
        final File file = Service.createFile("TRANSACTIONS.txt");
        List<String> lines = new ArrayList<>();

        for(Transaction t: transactionManager.transactions)
        {
            lines.add(t.toString());
        }

        Service.writeToFile(file, lines);
    }

    @Override
    public void visitGoalManager(GoalManager goalManager) {
        final File file = Service.createFile("GOALS.txt");
        List<String> lines = new ArrayList<>();

        for(Goal g: goalManager.goals)
        {
            lines.add(g.toString());
        }

        Service.writeToFile(file, lines);
    }
}
