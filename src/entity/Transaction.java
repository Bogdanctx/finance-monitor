package entity;

import controller.ControllerFactory;

public class Transaction {
    private int id;
    private TYPE type;
    private double amount;
    private String description;
    private int account_id;

    public enum TYPE {
        Household,
        Utilities,
        Shopping,
        Essentials,
        Transportation,
        Food,
        Health,
        Education,
        Entertainment,
        Other
    }

    public Transaction(int id, TYPE type, double amount, String description, int account_id) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.account_id = account_id;
        this.id = id;

        Account attachedAccount = ControllerFactory.accountController.getAccountById(account_id);
        attachedAccount.updateBalance(-amount);

    }

    public String getDescription() {
        return description;
    }

    public int getAccountId() {
        return account_id;
    }

    public static String getTypesString() {
        String types = "";
        int index = 0;
        for (TYPE type : TYPE.values()) {
            types += "(" + index + ") " + type.name() + " | ";
            index++;
        }
        return types.substring(0, types.length() - 2);
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        Account account = ControllerFactory.accountController.getAccountById(account_id);

        if(account == null) {
            return "---------------- TRANSACTION ----------------\n" +
                    "| (ID: " + id + ") \n" +
                    "| Type: " + type.toString() + "\n" +
                    "| Amount: $" + amount + "\n" +
                    "| Description: " + description + "\n" +
                    "| Account: No account attached.\n" +
                    "--------------------------------------------";
        }

        return "---------------- TRANSACTION ----------------\n" +
                "| (ID: " + id + ") \n" +
                "| Type: " + type.toString() + "\n" +
                "| Amount: $" + amount + "\n" +
                "| Description: " + description + "\n" +
                "| Account: " + ControllerFactory.accountController.getAccountById(account_id).getName() + "\n" +
                "--------------------------------------------";
    }

    public int getId() {
        return id;
    }

    public TYPE getType() {
        return type;
    }
}
