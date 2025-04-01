import java.util.ArrayList;
import java.util.List;

public class GoalManager extends Manager {
    List<Goal> goals = new ArrayList<Goal>();
    private static GoalManager instance = null;

    private GoalManager() {}

    public static GoalManager getInstance() {
        if(instance == null) {
            instance = new GoalManager();
        }

        return instance;
    }

    @Override
    protected void NVImenuList() {
        System.out.println("1. Add a new goal");
        System.out.println("2. Remove a goal");
        System.out.println("3. Update a goal");
        System.out.println("4. Show all goals");
        System.out.println("0. Exit");
    }

    @Override
    protected void NVIrender() {
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
            case 0 -> shouldRun = false;
        }
    }

    private void addGoals() {
        System.out.print("Enter goal: ");
        String goalText = scanner.next();

        System.out.print("Enter value: ");
        double goalValue = scanner.nextDouble();
        scanner.nextLine();

        goals.add(new Goal(goalText, goalValue));
    }

    private void removeGoals() {
        showGoals();
        System.out.print("Enter the ID of the goal to be deleted: ");
        int id = scanner.nextInt();

        boolean wasDeleted = false;

        for(int i = 0; i < goals.size(); i++) {
            if(goals.get(i).getId() == id) {
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
        for(Goal goal: goals) {
            System.out.format("(ID: %1$s) %2$s - %3$f\n", goal.getId(), goal.getGoal(), goal.getValue());
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
                String newGoalText = scanner.next();

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
