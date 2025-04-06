public class Account {
    private int id;
    private String name;
    private double balance;
    private double initial_balance;

    Account(int id, String accountName, double balance) {
        this.name = accountName;
        this.balance = balance;
        this.initial_balance = balance;
        this.id = id;
    }

    @Override
    public String toString() {
        return "----------------- ACCOUNT -----------------\n" +
                "| (ID: " + id + ") \n" +
                "| Name: " + name + "\n" +
                "| Balance: $" + balance + "\n" +
                "| Initial Balance: $" + initial_balance + "\n" +
                "------------------------------------------";
    }

    public double getInitialBalance() {
        return initial_balance;
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
        this.initial_balance = balance;
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
