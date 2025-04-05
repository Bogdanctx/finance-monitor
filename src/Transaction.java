public class Transaction {
    private TYPE type;
    private String description;
    private double amount;
    private int id;
    private int accountId;

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

    Transaction(int id, double amount, TYPE type, String description, int accountId) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.accountId = accountId;
        this.id = id;

        Account attachedAccount = ManagerFactory.getAccountManager().getAccountById(accountId);
        attachedAccount.updateBalance(-amount);

    }

    public String getDescription() {
        return description;
    }

    public int getAccountId() {
        return accountId;
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

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "---------------- TRANSACTION ----------------\n" +
                "| (ID: " + id + ") \n" +
                "| Type: " + type.toString() + "\n" +
                "| Amount: $" + amount + "\n" +
                "| Description: " + description + "\n" +
                "| Account: " + ManagerFactory.getAccountManager().getAccountById(accountId).getName() + "\n" +
                "--------------------------------------------";
    }

    public int getId() {
        return id;
    }

    public TYPE getType() {
        return type;
    }
}
