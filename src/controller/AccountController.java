package controller;

import entity.Account;
import entity.Goal;
import entity.Service;
import visitor.Visitor;
import database.Database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountController extends REST {
    List<Account> accounts = new ArrayList<Account>();

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account a) {
        accounts.add(a);
    }

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
            case 0 -> shouldRun = false;
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
        return accs.substring(0, accs.length() - 3);
    }

    public void export(Visitor visitor) {
        visitor.visitAccountController(this);
    }


    private void addAccounts() {
        System.out.print("Enter account name: ");
        String accountName = scanner.nextLine();

        if(accountName.isEmpty()) {
            System.out.println("An account must have a name");
            return;
        }

        System.out.print("Enter account balance: ");
        Double accountBalance = Service.readDouble(scanner);
        if(accountBalance == null) {
            System.out.println("Invalid number");
            return;
        }

        String values = String.format("('%s', %.2f)", accountName, accountBalance);
        int id = Database.insertIntoDatabase("accounts", "(name, balance)", values);

        accounts.add(new Account(id, accountName, accountBalance));
        Service.registerLog("new_account#name=" + accountName + ";balance=" + accountBalance);
    }

    private void removeAccounts() {
        for(Account account: accounts) {
            System.out.println(account);
        }

        if(accounts.isEmpty()) {
            System.out.println("You don't have any accounts!");
            return;
        }

        System.out.print("Enter the ID of the account to be deleted: ");
        Integer id = Service.readInt(scanner);

        if(id == null) {
            System.out.println("Invalid ID");
            return;
        }

        boolean wasDeleted = false;

        for (int i = accounts.size() - 1; i >= 0; i--) {
            if (accounts.get(i).getId() == id) {

                Iterator<Goal> iterator = ControllerFactory.goalController.getGoals().iterator();
                while (iterator.hasNext()) {
                    Goal goal = iterator.next();
                    if (goal.getAccountId() == accounts.get(i).getId()) {
                        iterator.remove();
                    }
                }

                Service.registerLog("delete_account#name=" + accounts.get(i).getName());
                Database.deleteRow("accounts", "name = '" + accounts.get(i).getName() + "'");
                accounts.remove(i);
                wasDeleted = true;
            }
        }

        if(wasDeleted) {
            System.out.println("The account with ID #" + id + " and all associated goals were deleted.");
        }
        else {
            System.out.println("Unable to find account with ID #" + id + ".");
        }
    }

    private void updateAccounts() {
        for(Account account: accounts) {
            System.out.println(account);
        }

        if(accounts.isEmpty()) {
            System.out.println("You don't have any accounts!");
            return;
        }

        System.out.print("Enter the ID of the account to be updated: ");

        Integer id = Service.readInt(scanner);
        if(id == null) {
            System.out.println("Invalid ID");
            return;
        }

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
        if(accounts.isEmpty()) {
            System.out.println("You don't have accounts.");
        }
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

        Integer choice = Service.readInt(scanner);

        if(choice == null) {
            System.out.println("Invalid choice");
            return;
        }

        switch(choice) {
            case 1:
            {
                System.out.print("Enter new account name: ");
                String newAccountName = scanner.nextLine();

                if(newAccountName.isEmpty()) {
                    System.out.println("Account name can't be empty.");
                    return;
                }

                account.setName(newAccountName);
                Database.updateRow("accounts", "name = '" + newAccountName + "'", "id = " + account.getId());
                wasUpdated = true;

                break;
            }
            case 2:
            {
                System.out.print("Enter amount of funds to add or remove: ");
                Double newFunds = Service.readDouble(scanner);

                if(newFunds == null) {
                    System.out.println("Invalid value");
                    return;
                }

                account.updateBalance(newFunds);
                Database.updateRow("accounts", "balance = " + (account.getBalance() + newFunds), "id = " + account.getId());
                wasUpdated = true;

                break;
            }
            case 3:
            {
                System.out.print("Enter new balance: ");
                Double newBalance = Service.readDouble(scanner);

                if(newBalance == null) {
                    System.out.println("Invalid value");
                    return;
                }

                account.setBalance(newBalance);
                Database.updateRow("accounts", "balance = " + newBalance, "id = " + account.getId());
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
