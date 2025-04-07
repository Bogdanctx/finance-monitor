import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionManager extends Manager {
    private final Scanner scanner = new Scanner(System.in);
    List<Transaction> transactions = new ArrayList<Transaction>();

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
        Transaction.TYPE type = Transaction.TYPE.values()[index];
        scanner.nextLine();

        System.out.print("Enter additional information: ");
        String description = scanner.nextLine();

        System.out.print("Enter account ID [ " + ManagerFactory.getAccountManager().getAccountsString() + " ]: ");
        int account_id = scanner.nextInt();

        int transactionId = Database.insertIntoDatabase("transactions", "(type,amount,description,account_id)",
                                                        String.format("('%s',%.2f,'%s',%d)", type.toString(), amount, description, account_id));
        transactions.add(new Transaction(transactionId, type, amount, description, account_id));

        Service.registerLog("new_transaction#amount=" + amount +
                            ";type=" + Transaction.TYPE.values()[index].toString() +
                            ";description=" + description +
                            ";account=" + ManagerFactory.getAccountManager().getAccountById(account_id).getName());
    }

    private void removeTransactions() {
        showTransactions();

        System.out.print("Enter the ID of the transaction you would like to remove: ");
        int id = scanner.nextInt();

        boolean wasDeleted = false;

        for(int i = 0; i < transactions.size(); i++) {
            if(transactions.get(i).getId() == id) {
                Service.registerLog("delete_transaction#id=" + transactions.get(i).getId());

                int accountId = transactions.get(i).getAccountId();

                Account account = ManagerFactory.getAccountManager().getAccountById(accountId);
                account.updateBalance(transactions.get(i).getAmount()); // account_balance = account_balance - (-transaction_balance) = account_balance + transaction_balance

                Database.updateRow("accounts", "balance = " + account.getBalance(), "id = " + accountId);
                Database.deleteRow("transactions", "id = " + transactions.get(i).getId());

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
        System.out.print("Enter your option:");
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
