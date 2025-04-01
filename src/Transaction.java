import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private TYPE type;
    private String description;
    private double amount;
    private static int s_id;
    private int id;
    private Account account;

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

    Transaction(double amount, TYPE type, String description, Account account) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.account = account;
        id = s_id;
        s_id++;

        account.updateBalance(-amount);
    }

    static String getTypesString() {
        String types = "";
        int index = 0;
        for (TYPE type : TYPE.values()) {
            types += "(" + index + ") " + type.name() + " | ";
            index++;
        }
        return types.substring(0, types.length() - 2);
    }

    @Override
    public String toString() {
        return "---------------- TRANSACTION ----------------\n" +
                "| (ID: " + id + ") \n" +
                "| Type: " + type.toString() + "\n" +
                "| Amount: $" + amount + "\n" +
                "| Description: " + description + "\n" +
                "| Account: " + account.getName() + "\n" +
                "--------------------------------------------";
    }

    public int getId() {
        return id;
    }

    public TYPE getType() {
        return type;
    }
}
