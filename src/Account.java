public class Account {
    private String name;
    private double balance;
    private static int s_id = 0;
    private double initialBalance;
    private final int id;

    Account(String accountName, double balance) {
        this.name = accountName;
        this.balance = balance;
        this.initialBalance = balance;

        Service.registerLog("new_account#name=" + accountName + ";balance=" + balance);

        id = s_id;
        s_id++;
    }

    @Override
    public String toString() {
        return "----------------- ACCOUNT -----------------\n" +
                "| (ID: " + id + ") \n" +
                "| Name: " + name + "\n" +
                "| Balance: " + balance + "\n" +
                "| Initial Balance: " + initialBalance + "\n" +
                "------------------------------------------";
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void updateBalance(double amount) {
        Service.registerLog("update_balance#old=" + this.balance + ";new=" + (balance + amount));

        balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        Service.registerLog("set_balance#old=" + this.balance + ";new=" + balance);
        this.balance = balance;
        this.initialBalance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String accountName) {
        Service.registerLog("set_name#old=" + this.name + ";new=" + accountName);

        this.name = accountName;
    }

    public int getId() {
        return id;
    }

}
