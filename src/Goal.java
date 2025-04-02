public class Goal implements Comparable<Goal> {
    private String goal;
    private double value;
    private static int s_id = 0;
    private int id;
    private Account account = null;

    Goal(String goal, double value) {
        this.goal = goal;
        this.value = value;

        id = s_id;
        s_id++;
    }

    Goal(String goal, double value, Account account) {
        this.goal = goal;
        this.value = value;
        this.account = account;

        id = s_id;
        s_id++;
    }

    @Override
    public int compareTo(Goal o) {
        return Double.compare(this.value, o.value);
    }

    @Override
    public String toString() {

        if(account != null) {
            return "------------------- GOAL -------------------\n" +
                    "| (ID: " + id + ") \n" +
                    "| Goal: " + goal + "\n" +
                    "| Target: $" + account.getBalance() + " / $" + value + "\n" +
                    "| Attached account: " + account.getName() + "\n" +
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
        this.value = value;
    }

    public int getId() {
        return id;
    }
}
