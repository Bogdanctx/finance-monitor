public class Goal implements Comparable<Goal> {
    private String goal;
    private double value;
    private int id;
    private int accountId = -1;

    Goal(int id, String goal, double value, int accountId) {
        this.goal = goal;
        this.value = value;
        this.accountId = accountId;
        this.id = id;
    }

    @Override
    public int compareTo(Goal o) {
        return Double.compare(this.value, o.value);
    }

    @Override
    public String toString() {

        if(accountId != -1) {
            Account attachedAccount = ManagerFactory.getAccountManager().getAccountById(accountId);

            return "------------------- GOAL -------------------\n" +
                    "| (ID: " + id + ") \n" +
                    "| Goal: " + goal + "\n" +
                    "| Target: $" + attachedAccount.getBalance() + " / $" + value + "\n" +
                    "| Attached account: " + attachedAccount.getName() + "\n" +
                    "-------------------------------------------";
        }

        double moneyInAccounts = 0;
        for(Account account: ManagerFactory.getAccountManager().accounts) {
            moneyInAccounts += account.getBalance();
        }

        return "------------------- GOAL -------------------\n" +
                "| (ID: " + id + ") \n" +
                "| Goal: " + goal + "\n" +
                "| Target: $" + moneyInAccounts + " / $" + value + " ($" + moneyInAccounts + " from all accounts)\n" +
                "| Attached account: No account attached\n" +
                "-------------------------------------------";

    }

    public int getAccountId() {
        return accountId;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        Service.registerLog("new_value#old=" + this.value + ";new=" + this.value);

        this.value = value;
    }

    public int getId() {
        return id;
    }

}
