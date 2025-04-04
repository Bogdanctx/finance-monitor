public class ManagerFactory {
    private static final GoalManager goalManager = new GoalManager();
    private static final AccountManager accountManager = new AccountManager();
    private static final TransactionManager transactionManager = new TransactionManager();
    private static final ReportManager reportManager = new ReportManager();
    private static final Manager manager = new Manager();

    public static GoalManager getGoalManager() {
        return goalManager;
    }

    public static AccountManager getAccountManager() {
        return accountManager;
    }

    public static TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public static ReportManager getReportManager() {
        return reportManager;
    }

    public static Manager getMainManager() {
        return manager;
    }
}
