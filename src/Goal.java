public class Goal {
    private String goal;
    private double value;
    private static int s_id = 0;
    private int id;

    Goal(String goal, double value) {
        this.goal = goal;
        this.value = value;

        id = s_id;
        s_id++;
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
        this.value = value;
    }

    public int getId() {
        return id;
    }
}
