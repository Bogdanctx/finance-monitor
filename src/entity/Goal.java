package entity;

import controller.ControllerFactory;

import java.lang.module.Configuration;

public class Goal implements Comparable<Goal> {
    private int id;
    private String goal;
    private double value;
    private int account_id = -1;

    public Goal(int id, String goal, double value, int account_id) {
        this.goal = goal;
        this.value = value;
        this.account_id = account_id;
        this.id = id;
    }

    @Override
    public int compareTo(Goal o) {
        return Double.compare(this.value, o.value);
    }

    @Override
    public String toString() {
        Account attachedAccount = ControllerFactory.accountController.getAccountById(account_id);

        return "------------------- GOAL -------------------\n" +
                "| (ID: " + id + ") \n" +
                "| Goal: " + goal + "\n" +
                "| Target: $" + attachedAccount.getBalance() + " / $" + value + "\n" +
                "| Attached account: " + attachedAccount.getName() + "\n" +
                "-------------------------------------------";

    }

    public int getAccountId() {
        return account_id;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        Service.registerLog("new_value#old=" + this.value + ";new=" + this.value);

        this.value = value;
    }

    public int getId() {
        return id;
    }

}
