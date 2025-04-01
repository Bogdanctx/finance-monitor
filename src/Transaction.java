import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private TYPE type;
    private String description;
    private double amount;
    private static int s_id;
    private int id;

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

    Transaction(double amount, TYPE type, String description) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        id = s_id;
        s_id++;
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
                "--------------------------------------------";
    }

    public int getId() {
        return id;
    }

    public TYPE getType() {
        return type;
    }
}
