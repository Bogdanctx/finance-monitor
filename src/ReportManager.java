import java.util.Scanner;

public class ReportManager extends Manager {
    private Visitor[] listOfExports;

    private enum EXPORT_TYPE {
        CSV,
        PlainText
    }

    ReportManager() {
        listOfExports = new Visitor[]{new CSVExportVisitor(), new PlainTextExportVisitor()};
    }

    @Override
    protected void NVImenuList() {
        System.out.println("1. Export accounts");
        System.out.println("2. Export goals");
        System.out.println("3. Export transactions");
        System.out.println("4. General report");
        System.out.println("0. Exit");
    }

    @Override
    protected void render() {
        System.out.println("============ Generate a report ============");
        displayMenu();
        System.out.println("===========================================");
    }

    @Override
    protected void handleMenuOption(int option)
    {
        if(option != 0) {

            if(option == 4) {
                ManagerFactory.getMainManager().export(listOfExports[EXPORT_TYPE.PlainText.ordinal()]);
            }
            else {
                System.out.println("1. Export as CSV");
                System.out.println("2. Export as Plain Text");
                System.out.print("Enter your choice: ");
                int typeOfExport = scanner.nextInt();

                EXPORT_TYPE type = EXPORT_TYPE.values()[typeOfExport - 1];

                switch(option)
                {
                    case 1 -> ManagerFactory.getAccountManager().export(listOfExports[type.ordinal()]);
                    case 2 -> ManagerFactory.getGoalManager().export(listOfExports[type.ordinal()]);
                    case 3 -> ManagerFactory.getTransactionManager().export(listOfExports[type.ordinal()]);
                }
            }



        }
        else
        {
            shouldRun = false;
            Service.clearConsole();
        }

    }
}
