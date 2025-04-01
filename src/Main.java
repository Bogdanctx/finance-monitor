import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean shouldExit = false;

        while(!shouldExit)
        {
            System.out.println("Select an action from the list below:");
            System.out.println("1. Manage goals");
            System.out.println("2. Manage accounts");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();

            switch(option)
            {
                case 1:
                {
                    GoalManager.getInstance().run();
                    break;
                }
                case 0:
                {
                    shouldExit = true;
                    break;
                }
            }
        }


    }
}