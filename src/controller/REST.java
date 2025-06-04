package controller;

import entity.Service;
import visitor.Visitor;

import java.util.Scanner;

public class REST {
    protected final Scanner scanner = new Scanner(System.in);
    protected Integer menuOption;
    protected boolean shouldRun = true;

    public final void run() {
        shouldRun = true;

        while (shouldRun) {
            render();
        }
    }

    protected void render() {
        displayMenu();
    }

    protected void displayMenu() {
        System.out.println("Select an action from the list below:");

        // Non Virtual Interface
        NVImenuList();

        System.out.print("Enter your choice: ");
        menuOption = Service.readInt(scanner);

        if(menuOption == null) {
            System.out.println("Invalid choice");
            return;
        }

        handleMenuOption(menuOption);
    }

    public void export(Visitor visitor) {
        visitor.visitREST();
    }

    protected void NVImenuList() {
        System.out.println("1. Manage goals");
        System.out.println("2. Manage accounts");
        System.out.println("3. Manage transactions");
        System.out.println("4. Generate a report");
        System.out.println("0. Exit");
    }

    protected void handleMenuOption(int option)
    {
        switch (option)
        {
            case 1: // Manage goals
            {
                ControllerFactory.goalController.run();
                break;
            }
            case 2: // Manage accounts
            {
                ControllerFactory.accountController.run();
                break;
            }
            case 3: // Manage transactions
            {
                ControllerFactory.transactionController.run();
                break;
            }
            case 4: // Generate a report
            {
                ControllerFactory.reportController.run();
                break;
            }
            case 0:
            {
                shouldRun = false;
                break;
            }
            default:
                System.out.println("Invalid input");
        }
    }
}
