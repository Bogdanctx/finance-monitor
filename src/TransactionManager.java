import java.util.ArrayList;
import java.util.List;

public class TransactionManager extends Manager {
    List<Transaction> transactions = new ArrayList<Transaction>();
    private static TransactionManager instance = null;

    private TransactionManager() {}

    public static TransactionManager getInstance() {
        if(instance == null) {
            instance = new TransactionManager();
        }

        return instance;
    }

    @Override
    protected void NVImenuList() {
        System.out.println("1. Add a new transaction");
        System.out.println("2. Remove a transaction");
        System.out.println("3. Show transactions");
        System.out.println("0. Exit");
    }

    @Override
    protected void NVIrender() {
        System.out.println("============ Manage Transactions ============");
        displayMenu();
        System.out.println("======================================");
    }

    @Override
    protected void handleMenuOption(int option)
    {
        switch(option)
        {
            case 1 -> addTransactions();
            case 2 -> removeTransactions();
            case 3 -> showTransactions();
            case 0 -> shouldRun = false;
        }
    }


    private void addTransactions() {
        System.out.print("Enter transaction amount: ");
        double amount = scanner.nextDouble();

        System.out.print("Enter transaction type [ " + Transaction.getTypesString() + "]: ");
        int index = scanner.nextInt();

        System.out.print("Enter additional information: ");
        String info = scanner.next();

        transactions.add(new Transaction(amount, Transaction.TYPE.values()[index], info));
    }

    private void removeTransactions() {
        showTransactions();
    }

    private void showTransactions() {

        System.out.println("1. Show all transactions");
        System.out.println("2. Show transactions by type");
        System.out.println("Enter your option:");
        int option = scanner.nextInt();

        switch(option) {
            case 1:
            {
                System.out.println("Your transactions:");

                for(Transaction transaction : transactions) {
                    System.out.println(transaction);
                }

                break;
            }
            case 2:
            {
                System.out.print("Enter transaction type [ " + Transaction.getTypesString() + "]: ");
                int index = scanner.nextInt();

                Transaction.TYPE[] types = Transaction.TYPE.values();

                System.out.println("Your transactions:");

                for(Transaction transaction : transactions) {
                    if(transaction.getType() == types[index]) {
                        System.out.println(transaction);
                    }
                }

                break;
            }
        }
    }
}
