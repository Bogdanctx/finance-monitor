package controller;

import database.Database;
import entity.Account;
import entity.Service;
import entity.Transaction;
import visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class TransactionController extends REST {
    List<Transaction> transactions = new ArrayList<Transaction>();

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    protected void NVImenuList() {
        System.out.println("1. Add a new transaction");
        System.out.println("2. Remove a transaction");
        System.out.println("3. Show transactions");
        System.out.println("4. Show statistics");
        System.out.println("0. Exit");
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
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
            case 0 -> shouldRun = false;
            default -> System.out.println("Invalid choice");
        }
    }

    @Override
    public void export(Visitor visitor) {
        visitor.visitTransactionController(this);
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

    public void addTransactions() {
        String accountsString = ControllerFactory.accountController.getAccountsString();

        if(accountsString == null) {
            System.out.println("You don't have any accounts!");
            return;
        }

        System.out.print("Enter transaction amount: ");
        Double amount = Service.readDouble(scanner);

        if(amount == null) {
            System.out.println("Invalid value");
            return;
        }

        System.out.print("Enter transaction type [ " + Transaction.getTypesString() + "]: ");
        Integer index = Service.readInt(scanner);

        if(index == null) {
            System.out.println("Invalid type");
            return;
        }

        if(index < 0 || index >= Transaction.TYPE.values().length) {
            System.out.println("Invalid type");
            return;
        }

        Transaction.TYPE type = Transaction.TYPE.values()[index];

        System.out.print("Enter additional information: ");
        String description = scanner.nextLine();

        System.out.print("Enter account ID [ " + ControllerFactory.accountController.getAccountsString() + " ]: ");

        Integer account_id = Service.readInt(scanner);

        if(ControllerFactory.accountController.getAccountById(account_id) == null) {
            System.out.println("Invalid account ID");
            return;
        }

        if(account_id == null) {
            System.out.println("Invalid ID");
            return;
        }

        int transactionId = Database.insertIntoDatabase("transactions", "(type,amount,description,account_id)",
                                                        String.format("('%s',%.2f,'%s',%d)", type.toString(), amount, description, account_id));
        transactions.add(new Transaction(transactionId, type, amount, description, account_id));

        Service.registerLog("new_transaction#amount=" + amount +
                            ";type=" + Transaction.TYPE.values()[index].toString() +
                            ";description=" + description +
                            ";account=" + ControllerFactory.accountController.getAccountById(account_id).getName());
    }

    public void removeTransactions() {
        for(Transaction transaction: transactions) {
            System.out.println(transaction);
        }

        if(transactions.isEmpty()) {
            System.out.println("You don't have any transactions!");
            return;
        }

        System.out.print("Enter the ID of the transaction you want to remove: ");
        Integer id = Service.readInt(scanner);

        if(id == null) {
            System.out.println("Invalid ID");
            return;
        }

        boolean wasDeleted = false;

        for(int i = 0; i < transactions.size(); i++) {
            if(transactions.get(i).getId() == id) {
                Service.registerLog("delete_transaction#id=" + transactions.get(i).getId());

                int accountId = transactions.get(i).getAccountId();

                Account account = ControllerFactory.accountController.getAccountById(accountId);

                if(account != null) {
                    account.updateBalance(transactions.get(i).getAmount()); // account_balance = account_balance - (-transaction_balance) = account_balance + transaction_balance
                    Database.updateRow("accounts", "balance = " + account.getBalance(), "id = " + accountId);
                }

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
        Integer option = Service.readInt(scanner);

        if(option == null) {
            System.out.println("Invalid choice");
            return;
        }

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
                Integer index = Service.readInt(scanner);

                if(index == null) {
                    System.out.println("Invalid type");
                    return;
                }

                if(index < 0 || index >= Transaction.TYPE.values().length) {
                    System.out.println("Invalid transaction type");
                    return;
                }

                Transaction.TYPE[] types = Transaction.TYPE.values();

                System.out.println("Your transactions:");

                for(Transaction transaction : transactions) {
                    if(transaction.getType() == types[index]) {
                        System.out.println(transaction);
                    }
                }

                break;
            }
            default:
                System.out.println("Invalid choice");
        }
    }
}
