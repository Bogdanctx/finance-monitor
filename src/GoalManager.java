import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GoalManager extends Manager {
    List<Goal> goals = new ArrayList<>();

    @Override
    protected void NVImenuList() {
        System.out.println("1. Add a new goal");
        System.out.println("2. Remove a goal");
        System.out.println("3. Update a goal");
        System.out.println("4. Show all goals");
        System.out.println("0. Exit");
    }

    @Override
    protected void render() {
        System.out.println("============ Manage Goals ============");
        displayMenu();
        System.out.println("======================================");
    }

    @Override
    protected void handleMenuOption(int option)
    {
        switch(option)
        {
            case 1 -> addGoals();
            case 2 -> removeGoals();
            case 3 -> updateGoals();
            case 4 -> showGoals();
            case 0 -> {
                shouldRun = false;
                Service.clearConsole();
            }
        }
    }

    public void export(Visitor visitor) {
        visitor.visitGoalManager(this);
    }

    private void addGoals() {
        System.out.print("Enter goal: ");
        String goalText = scanner.nextLine();

        System.out.print("Enter value: ");
        double goalValue = scanner.nextDouble();
        scanner.nextLine();

        String accountsString = ManagerFactory.getAccountManager().getAccountsString();

        if(accountsString == null) {
            System.out.print("Enter account ID [ (-1) No account ]: ");
        }
        else {
            System.out.print("Enter account ID [ (-1) No account | " + ManagerFactory.getAccountManager().getAccountsString() + " ]: ");
        }

        int accountID = scanner.nextInt();

        int databaseKey;

        if(accountID == -1) { // No account attached to this goal
            String values = String.format("('%s',%.2f)", goalText, goalValue);
            databaseKey = Database.insertIntoDatabase("goals", "(goal,value)", values);
        }
        else { // An account has been attached to this goal
            String values = String.format("('%s', %.2f, %d)", goalText, goalValue, accountID);
            databaseKey = Database.insertIntoDatabase("goals", "(goal,value,account_id)", values);
        }

        goals.add(new Goal(databaseKey, goalText, goalValue, accountID));

        Service.registerLog("new_goal#goal=" + goalText + ";value=" + goalValue);
    }

    private void removeGoals() {
        showGoals();
        System.out.print("Enter the ID of the goal to be deleted: ");
        int id = scanner.nextInt();

        boolean wasDeleted = false;

        for(int i = 0; i < goals.size(); i++) {
            if(goals.get(i).getId() == id) {
                Service.registerLog("delete_goal#description=" + goals.get(i).getGoal());

                Database.deleteRow("goals", "id = " + goals.get(i).getId());
                goals.remove(i);

                wasDeleted = true;
            }
        }

        if(wasDeleted) {
            System.out.println("The goal with ID #" + id + " was deleted.");
        }
        else {
            System.out.println("Unable to find goal with ID #" + id + ".");
        }
    }

    private void updateGoals() {
        showGoals();
        System.out.print("Enter the ID of the goal to be updated: ");
        int id = scanner.nextInt();

        boolean wasFound = false;
        for (Goal goal : goals) {
            if (goal.getId() == id) {
                updateGoal(goal);
                wasFound = true;
            }
        }

        if(!wasFound) {
            System.out.println("Unable to find goal with ID #" + id + ".");
        }
    }

    private void showGoals() {
        System.out.println("Your goals:");

        List<Goal> sortedGoals = new ArrayList<>(List.copyOf(goals));

        sortedGoals.sort(Comparator.comparingDouble(Goal::getValue).reversed());

        for(Goal goal: sortedGoals) {
            System.out.println(goal);
        }
    }

    private void updateGoal(Goal goal) {
        boolean wasUpdated = false;

        System.out.println("1. Update goal text");
        System.out.println("2. Update goal value");
        System.out.print("Enter your choice: ");
        int choice;
        choice = scanner.nextInt();

        switch(choice) {
            case 1:
            {
                System.out.print("Enter new goal description: ");
                scanner.nextLine();
                String newGoalText = scanner.nextLine();

                Database.updateRow("goals", "goal = '" + newGoalText + "'", "id = " + goal.getId());
                Service.registerLog("set_goal#old_goal=" + goal.getGoal() + ";new=" + newGoalText);
                goal.setGoal(newGoalText);

                wasUpdated = true;

                break;
            }
            case 2:
            {
                System.out.print("Enter new goal value: ");
                double newGoalValue = scanner.nextDouble();

                if(newGoalValue < 0) {
                    System.out.println("Invalid value.");
                }
                else {
                    Database.updateRow("goals", "value = '" + newGoalValue + "'", "id = " + goal.getId());
                    Service.registerLog("set_goal#old_value=" + goal.getValue() + ";new=" + newGoalValue);
                    goal.setValue(newGoalValue);

                    wasUpdated = true;
                }

                break;
            }
        }

        if(wasUpdated) {
            System.out.println("The goal with ID #" + goal.getId() + " was updated.");
        }
        else {
            System.out.println("Unable to update goal with ID #" + goal.getId() + ".");
        }
    }

}
