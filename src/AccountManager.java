import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountManager extends Manager {
    private final Scanner scanner = new Scanner(System.in);
    List<Account> accounts = new ArrayList<Account>();

    public Account getAccountById(int id) {
        for(Account account : accounts) {
            if(account.getId() == id) {
                return account;
            }
        }
        return null;
    }

    @Override
    protected void NVImenuList() {
        System.out.println("1. Add a new account");
        System.out.println("2. Remove an account");
        System.out.println("3. Update an account");
        System.out.println("4. Show all accounts");
        System.out.println("0. Exit");
    }

    @Override
    protected void render() {
        System.out.println("============ Manage Accounts ============");
        displayMenu();
        System.out.println("======================================");
    }


    @Override
    protected void handleMenuOption(int option)
    {
        switch(option)
        {
            case 1 -> addAccounts();
            case 2 -> removeAccounts();
            case 3 -> updateAccounts();
            case 4 -> showAccounts();
            case 0 -> {
                shouldRun = false;
                Service.clearConsole();
            }
        }

    }

    public String getAccountsString() {
        if(accounts.isEmpty()) {
            return null;
        }

        StringBuilder accs = new StringBuilder();
        for (Account account: accounts) {
            accs.append("(").append(account.getId()).append(") ").append(account.getName()).append(" | ");
        }
        return accs.substring(0, accs.length() - 2);
    }

    public void export(Visitor visitor) {
        visitor.visitAccountManager(this);
    }


    private void addAccounts() {
        System.out.print("Enter account name: ");
        String accountName = scanner.next();

        System.out.print("Enter account balance: ");
        double accountBalance = scanner.nextDouble();
        scanner.nextLine();

        String values = String.format("('%s', %.2f, %.2f)", accountName, accountBalance, accountBalance);
        int id = Database.insertIntoDatabase("accounts", "(name, balance, initial_balance)", values);

        accounts.add(new Account(id, accountName, accountBalance));
        Service.registerLog("new_account#name=" + accountName + ";balance=" + accountBalance);
    }

    private void removeAccounts() {
        showAccounts();
        System.out.print("Enter the ID of the account to be deleted: ");
        int id = scanner.nextInt();

        boolean wasDeleted = false;

        for(int i = 0; i < accounts.size(); i++) {
            if(accounts.get(i).getId() == id) {
                Service.registerLog("delete_account#name=" + accounts.get(i).getName());

                Database.deleteRow("accounts", "name = '" + accounts.get(i).getName() + "'");
                accounts.remove(i);

                wasDeleted = true;
            }
        }

        if(wasDeleted) {
            System.out.println("The account with ID #" + id + " was deleted.");
        }
        else {
            System.out.println("Unable to find account with ID #" + id + ".");
        }
    }

    private void updateAccounts() {
        showAccounts();
        System.out.print("Enter the ID of the account to be updated: ");
        int id = scanner.nextInt();

        boolean wasFound = false;
        for (Account account: accounts) {
            if (account.getId() == id) {
                updateAccount(account);
                wasFound = true;
            }
        }

        if(!wasFound) {
            System.out.println("Unable to find goal with ID #" + id + ".");
        }
    }

    private void showAccounts() {
        System.out.println("Your accounts:");
        for(Account account: accounts) {
            System.out.println(account);
        }
    }

    private void updateAccount(Account account) {
        boolean wasUpdated = false;

        System.out.println("1. Update account name");
        System.out.println("2. Add/Remove funds to account");
        System.out.println("3. Set balance of an account");
        System.out.print("Enter your choice: ");
        int choice;
        choice = scanner.nextInt();

        switch(choice) {
            case 1:
            {
                System.out.print("Enter new account name: ");
                String newAccountName = scanner.next();

                account.setName(newAccountName);

                Database.updateRow("accounts", "name = '" + newAccountName + "'", "id = " + account.getId());

                wasUpdated = true;

                break;
            }
            case 2:
            {
                System.out.print("Enter amount of funds to add or remove: ");
                double newFunds = scanner.nextDouble();

                account.updateBalance(newFunds);

                Database.updateRow("accounts", "balance = " + account.getBalance() + newFunds, "id = " + account.getId());

                wasUpdated = true;

                break;
            }
            case 3:
            {
                System.out.print("Enter new balance: ");
                double newBalance = scanner.nextDouble();

                account.setBalance(newBalance);

                Database.updateRow("accounts", "balance = " + newBalance + ", initial_balance = " + newBalance, "id = " + account.getId());

                wasUpdated = true;

                break;
            }
        }

        if(wasUpdated) {
            System.out.println("Account #" + account.getId() + " was updated.");
        }
        else {
            System.out.println("Unable to update account #" + account.getId() + ".");
        }
    }
}
