import java.util.Scanner;

public class Manager {
    protected final Scanner scanner = new Scanner(System.in);
    protected int menuOption;
    protected boolean shouldRun = true;

    /////////////////////////////////////
    /// Purpose: Run a manager
    /////////////////////////////////////
    public final void run() {
        shouldRun = true;

        while (shouldRun) {
            render();
        }
    }

    ///////////////////////////////////////////////
    /// Each class that inherits Manager can override render() in case of custom text printing
    //////////////////////////////////////////////
    protected void render() {
        displayMenu();
    }

    protected void displayMenu() {
        System.out.println("Select an action from the list below:");

        // Non Virtual Interface
        NVImenuList();

        System.out.print("Enter your choice: ");
        menuOption = scanner.nextInt();
        scanner.nextLine();

        handleMenuOption(menuOption);
    }

    public void export(Visitor visitor) {
        visitor.visitManager();
    }

    /////////////////////////////////////////////////////////////
    /// Each class that inherits Manager must override NVImenuList() with a custom menu
    /////////////////////////////////////////////////////////////
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
                ManagerFactory.getGoalManager().run();

                break;
            }
            case 2: // Manage accounts
            {
                ManagerFactory.getAccountManager().run();

                break;
            }
            case 3: // Manage transactions
            {
                ManagerFactory.getTransactionManager().run();

                break;
            }
            case 4: // Generate a report
            {
                ManagerFactory.getReportManager().run();

                break;
            }
            case 0:
            {
                shouldRun = false;

                break;
            }
        }
    }
}
