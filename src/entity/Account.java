package entity;

public class Account {
    private int id;
    private String name;
    private double balance;

    public Account(int id, String accountName, double balance) {
        this.name = accountName;
        this.balance = balance;
        this.id = id;
    }

    @Override
    public String toString() {
        return "----------------- ACCOUNT -----------------\n" +
                "| (ID: " + id + ") \n" +
                "| Name: " + name + "\n" +
                "| Balance: $" + balance + "\n" +
                "------------------------------------------";
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
