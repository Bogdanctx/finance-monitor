import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionManager extends Manager {
    private final Scanner scanner = new Scanner(System.in);
    List<Transaction> transactions = new ArrayList<Transaction>();

    public void testTransaction() {

        transactions.add(new Transaction(200, Transaction.TYPE.Education, "curs retele", ManagerFactory.getAccountManager().accounts.get(0)));
        transactions.add(new Transaction(100, Transaction.TYPE.Entertainment, "cities skylines", ManagerFactory.getAccountManager().accounts.get(0)));
        transactions.add(new Transaction(800, Transaction.TYPE.Education, "operatie genunchi", ManagerFactory.getAccountManager().accounts.get(2)));
        transactions.add(new Transaction(80, Transaction.TYPE.Entertainment, "m-am distrat", ManagerFactory.getAccountManager().accounts.get(1)));
        transactions.add(new Transaction(150, Transaction.TYPE.Education, "curs java 8", ManagerFactory.getAccountManager().accounts.get(0)));
        transactions.add(new Transaction(1000, Transaction.TYPE.Other, "reparatie masina", ManagerFactory.getAccountManager().accounts.get(0)));
        transactions.add(new Transaction(2000, Transaction.TYPE.Other, "masina de spalat", ManagerFactory.getAccountManager().accounts.get(0)));
        transactions.add(new Transaction(170, Transaction.TYPE.Other, "gaze", ManagerFactory.getAccountManager().accounts.get(0)));

    }

    @Override
    protected void NVImenuList() {
        System.out.println("1. Add a new transaction");
        System.out.println("2. Remove a transaction");
        System.out.println("3. Show transactions");
        System.out.println("4. Show statistics");
        System.out.println("0. Exit");
    }

    @Override
    protected void render() {
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
            case 4 -> showStatistics();
            case 0 -> {
                shouldRun = false;
                Service.clearConsole();
            }
        }
    }

    @Override
    public void export(Visitor visitor) {
        visitor.visitTransactionManager(this);
    }

    private void showStatistics() {
        Transaction.TYPE[] types = Transaction.TYPE.values();
        int totalTransactions = transactions.size();

        System.out.println("You have " + totalTransactions + " transactions.");

        for(Transaction.TYPE type : types)
        {
            int count = 0;
            double sum = 0;

            for(Transaction transaction : transactions) {
                if(transaction.getType() == type) {
                    count++;
                    sum += transaction.getAmount();
                }
            }

            if(count > 0) {
                // 37% of transactions are related to Food and you've spent $500.
                System.out.printf("%.2f%% transactions are related to %s and you've spent $%.2f%n", count * 100.0 / totalTransactions, type.name(), sum);
            }
        }
    }

    private void addTransactions() {
        System.out.print("Enter transaction amount: ");
        double amount = scanner.nextDouble();

        System.out.print("Enter transaction type [ " + Transaction.getTypesString() + "]: ");
        int index = scanner.nextInt();

        System.out.print("Enter additional information: ");
        String info = scanner.next();

        System.out.print("Enter account ID [ " + ManagerFactory.getAccountManager().getAccountsString() + " ]: ");
        int accountID = scanner.nextInt();

        transactions.add(new Transaction(amount, Transaction.TYPE.values()[index],
                                        info, ManagerFactory.getAccountManager().accounts.get(accountID)));
    }

    private void removeTransactions() {
        showTransactions();

        System.out.println("Enter the ID of the transaction you would like to remove: ");
        int id = scanner.nextInt();

        boolean wasDeleted = false;

        for(int i = 0; i < transactions.size(); i++) {
            if(transactions.get(i).getId() == id) {
                Service.registerLog("delete_transaction#id=" + transactions.get(i).getId());

                transactions.remove(i);

                wasDeleted = true;
            }
        }

        if(wasDeleted) {
            System.out.println("The transaction with ID #" + id + " was deleted.");
        }
        else {
            System.out.println("Unable to find transaction with ID #" + id + ".");
        }
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
