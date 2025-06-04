package controller;

import entity.Goal;
import entity.Service;
import repositories.GoalRepository;
import visitor.Visitor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GoalController extends REST {
    private List<Goal> goals = new ArrayList<>();

    public List<Goal> getGoals() {
        return goals;
    }

    public void addGoal(Goal g) {
        goals.add(g);
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
            case 0 -> shouldRun = false;
        }
    }

    public void export(Visitor visitor) {
        visitor.visitGoalController(this);
    }

    private void addGoals() {
        String accountsString = ControllerFactory.accountController.getAccountsString();

        if(accountsString == null) {
            System.out.println("You have no accounts! An account is required to create a goal!");
            return;
        }

        System.out.print("Enter goal: ");
        String goalText = scanner.nextLine();

        if(goalText.isEmpty()) {
            System.out.println("Goal description can't be empty!");
            return;
        }

        System.out.print("Enter value: ");
        Double goalValue = Service.readDouble(scanner);

        if(goalValue == null) {
            System.out.println("Invalid number");
            return;
        }
        if(goalValue <= 0) {
            System.out.println("Invalid value");
            return;
        }

        System.out.print("Enter account ID [ " + ControllerFactory.accountController.getAccountsString() + " ]: ");

        Integer accountID = Service.readInt(scanner);

        if(accountID == null) {
            System.out.println("Invalid ID");
            return;
        }

        if(ControllerFactory.accountController.getAccountById(accountID) == null) {
            System.out.println("Invalid account ID");
            return;
        }

        int databaseKey = GoalRepository.insertGoal(goalText, goalValue, accountID);

        goals.add(new Goal(databaseKey, goalText, goalValue, accountID));
        Service.registerLog("new_goal#goal=" + goalText + ";value=" + goalValue);
    }

    private void removeGoals() {
        if(ControllerFactory.goalController.getGoals().isEmpty()) {
            System.out.println("You don't have any goals!");
            return;
        }

        for(Goal goal: goals) {
            System.out.println(goal);
        }

        System.out.print("Enter the ID of the goal to be deleted: ");
        Integer id = Service.readInt(scanner);

        if(id == null) {
            System.out.println("Invalid value");
            return;
        }

        boolean wasDeleted = false;

        for(int i = 0; i < goals.size(); i++) {
            if(goals.get(i).getId() == id) {
                Service.registerLog("delete_goal#description=" + goals.get(i).getGoal());

                GoalRepository.removeGoal(goals.get(i).getId());
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

        if(ControllerFactory.goalController.getGoals().isEmpty()) {
            System.out.println("You don't have any goals!");
            return;
        }

        System.out.print("Enter the ID of the goal to be updated: ");
        Integer id = Service.readInt(scanner);

        if(id == null) {
            System.out.println("Invalid value");
            return;
        }

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

        if(sortedGoals.isEmpty()) {
            System.out.println("You don't have any goals");
            return;
        }

        sortedGoals.sort(Comparator.comparingDouble(Goal::getValue).reversed());

        for(Goal goal: sortedGoals) {
            System.out.println(goal);
        }
    }

    private void updateGoal(Goal goal) {
        boolean wasUpdated = false;

        System.out.println("1. Update goal description");
        System.out.println("2. Update goal value");
        System.out.print("Enter your choice: ");

        Integer choice = Service.readInt(scanner);

        if(choice == null) {
            System.out.println("Invalid value");
            return;
        }

        switch(choice) {
            case 1:
            {
                System.out.print("Enter new goal description: ");
                String newDescription = scanner.nextLine();

                if(newDescription.isEmpty()) {
                    System.out.println("Goal description can't be empty");
                    return;
                }

                GoalRepository.updateDescription(goal.getId(), newDescription);
                Service.registerLog("set_goal#old_goal=" + goal.getGoal() + ";new=" + newDescription);
                goal.setGoal(newDescription);

                wasUpdated = true;

                break;
            }
            case 2:
            {
                System.out.print("Enter new goal value: ");
                Double newGoalValue = Service.readDouble(scanner);

                if(newGoalValue == null) {
                    System.out.println("Invalid value");
                    return;
                }

                if(newGoalValue <= 0) {
                    System.out.println("Invalid value.");
                    return;
                }

                GoalRepository.updateValue(goal.getId(), newGoalValue);
                Service.registerLog("set_goal#old_value=" + goal.getValue() + ";new=" + newGoalValue);
                goal.setValue(newGoalValue);

                wasUpdated = true;

                break;
            }

            default:
                System.out.println("Invalid choice");
        }

        if(wasUpdated) {
            System.out.println("The goal with ID #" + goal.getId() + " was updated.");
        }
        else {
            System.out.println("Unable to update goal with ID #" + goal.getId() + ".");
        }
    }

}
