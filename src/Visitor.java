
public interface Visitor {
    void visitAccountManager(AccountManager accountManager);
    void visitTransactionManager(TransactionManager transactionManager);
    void visitGoalManager(GoalManager goalManager);
    void visitManager();
}
