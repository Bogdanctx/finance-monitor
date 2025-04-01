public class Account {
    private String name;
    private double balance;
    private static int s_id = 0;
    private int id;

    Account(String accountName, double balance) {
        this.name = accountName;
        this.balance = balance;

        id = s_id;
        s_id++;
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
