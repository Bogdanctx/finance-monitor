package controller;

import entity.Service;
import visitor.CSVExportVisitor;
import visitor.PlainTextExportVisitor;
import visitor.Visitor;

public class ReportController extends REST {
    private Visitor[] listOfExports;

    private enum EXPORT_TYPE {
        CSV,
        PlainText
    }

    ReportController() {
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
        if(option == 0) {
            shouldRun = false;
        }

        switch (option)
        {
            case 0: {
                shouldRun = false;
                break;
            }
            case 4: {
                ControllerFactory.RESTController.export(listOfExports[EXPORT_TYPE.PlainText.ordinal()]);
                break;
            }
            case 1, 2, 3:
            {
                System.out.println("1. Export as CSV");
                System.out.println("2. Export as Plain Text");
                System.out.print("Enter your choice: ");
                Integer typeOfExport = Service.readInt(scanner);

                if(typeOfExport == null) {
                    System.out.println("Invalid choice");
                    return;
                }
                if(typeOfExport < 0 || typeOfExport - 1 >= EXPORT_TYPE.values().length) {
                    System.out.println("Invalid choice");
                    return;
                }

                EXPORT_TYPE type = EXPORT_TYPE.values()[typeOfExport - 1];

                switch(option)
                {
                    case 1 -> ControllerFactory.accountController.export(listOfExports[type.ordinal()]);
                    case 2 -> ControllerFactory.goalController.export(listOfExports[type.ordinal()]);
                    case 3 -> ControllerFactory.transactionController.export(listOfExports[type.ordinal()]);
                    default -> System.out.println("Invalid choice");
                }

                break;
            }
            default:
                System.out.println("Invalid choice");
        }

    }
}
