package controller;

public class ControllerFactory {
    public static AccountController accountController = new AccountController();
    public static GoalController goalController = new GoalController();
    public static TransactionController transactionController = new TransactionController();
    public static ReportController reportController = new ReportController();
    public static REST RESTController = new REST();
}
