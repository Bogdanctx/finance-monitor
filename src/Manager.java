import java.util.Scanner;

public class Manager {
    protected Scanner scanner = new Scanner(System.in);
    protected int menuOption;
    protected boolean shouldRun = true;
    private static Manager instance = null;

    protected Manager() {}

    public static Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }


    /////////////////////////////////////
    /// Purpose: Run a manager
    /////////////////////////////////////
    public final void run() {
        // final String os = System.getProperty("os.name");
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

        NVImenuList();

        System.out.print("Enter your choice: ");
        menuOption = scanner.nextInt();

        handleMenuOption(menuOption);
    }

    /////////////////////////////////////////////////////////////
    /// Each class that inherits Manager must override NVImenuList() with a custom menu
    /////////////////////////////////////////////////////////////
    protected void NVImenuList() {
        System.out.println("1. Manage goals");
        System.out.println("2. Manage accounts");
        System.out.println("3. Manage transactions");
        System.out.println("0. Exit");
    }

    protected void handleMenuOption(int option)
    {
        switch (option)
        {
            case 1: // Manage goals
            {
                GoalManager.getInstance().run();

                break;
            }
            case 2: // Manage accounts
            {
                AccountManager.getInstance().run();

                break;
            }
            case 3: // Manage transactions
            {
                TransactionManager.getInstance().run();

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
