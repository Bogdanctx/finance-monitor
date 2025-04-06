import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Service {
    private static File logs = null;
    private final static String os = System.getProperty("os.name").toLowerCase();


    static {
        logs = createFile("LOGS.csv");

        List<String> csvHeaders = new ArrayList<>();
        csvHeaders.add("nume_actiune,timestamp");
        writeToFile(logs, csvHeaders);
    }

    private Service() {}

    private static void loadAccountsFromDB() {
        ResultSet rs = Database.selectFrom("accounts", "*");

        try {
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                double balance = rs.getDouble(3);

                ManagerFactory.getAccountManager().accounts.add(new Account(id, name, balance));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadGoalsFromDB() {
        ResultSet rs = Database.selectFrom("goals", "*");

        try {
            while (rs.next()) {
                int id = rs.getInt(1);
                String goalDesc = rs.getString(2);
                int accountId = rs.getInt(3);
                double value = rs.getDouble(4);

                // If accountId is NULL
                if (accountId == 0) {
                    accountId = -1;
                }

                ManagerFactory.getGoalManager().goals.add(new Goal(id, goalDesc, value, accountId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadTransactionsFromDB() {
        ResultSet rs = Database.selectFrom("transactions", "*");

        try {
            while (rs.next()) {
                int id = rs.getInt(1);
                Transaction.TYPE type = Transaction.TYPE.valueOf(rs.getString(2));
                double amount = rs.getDouble(3);
                String description = rs.getString(4);
                int accountId = rs.getInt(5);

                ManagerFactory.getTransactionManager().transactions.add(new Transaction(id, type, amount, description, accountId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadDataFromDatabase() {
        loadAccountsFromDB();
        loadGoalsFromDB();
        loadTransactionsFromDB();
    }

    public static void clearConsole() {
        try {
            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("[ERROR] > " + e.getMessage());
        }
    }

    public static File createFile(String fileName) {
        File exportFile = new File(fileName);

        try {
            if (exportFile.exists()) {
                if (!exportFile.delete()) {
                    System.out.println("[ERROR] > " + fileName + " could not be deleted");
                }
            }
            if (!exportFile.createNewFile()) {
                System.out.println("[ERROR] > Could not create " + fileName);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] > " + e.getMessage());
        }

        return exportFile;
    }

    public static void writeToFile(File file, List<String> lines) {
        try {
            Files.write(file.toPath(), lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("[ERROR] > " + e.getMessage());
        }
    }

    public static void registerLog(String log) {
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss");
        String time = timeNow.format(timeFormat);

        writeToFile(logs, new ArrayList<>(Collections.singletonList(log + "," + time)));
    }
}
