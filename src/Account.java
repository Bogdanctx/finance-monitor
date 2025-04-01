public class Account {
    private String name;
    private double balance;
    private static int s_id = 0;
    private final double initialBalance;
    private int id;

    Account(String accountName, double balance) {
        this.name = accountName;
        this.balance = balance;
        this.initialBalance = balance;

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
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String accountName) {
        this.name = accountName;
    }

    public int getId() {
        return id;
    }

}
