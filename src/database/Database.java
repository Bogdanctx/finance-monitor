package database;

import controller.ControllerFactory;
import entity.Account;
import entity.Goal;
import entity.Transaction;
import repositories.AccountRepository;
import repositories.GoalRepository;
import repositories.TransactionRepository;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

public class Database {
    private static Connection connection = null;
    private static ControllerFactory controllerFactory;

    public static void connect() {
        try {
            FileInputStream fis = new FileInputStream("database_conf.properties");
            Properties props = new Properties();
            props.load(fis);

            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("[ERROR] > " + e.getMessage());
        }
    }

    public static void setControllers(ControllerFactory registry) {
        controllerFactory = registry;
    }

    public static void loadData() {
        loadAccountsFromDB();
        loadGoalsFromDB();
        loadTransactionsFromDB();
    }

    public static Connection getConnection() {
        return connection;
    }

    private static void loadAccountsFromDB() {
        ResultSet rs = AccountRepository.getAccounts();

        try {
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                double balance = rs.getDouble(3);

                controllerFactory.accountController.addAccount(new Account(id, name, balance));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadTransactionsFromDB() {
        ResultSet rs = TransactionRepository.getTransactions();

        try {
            while (rs.next()) {
                int id = rs.getInt(1);
                Transaction.TYPE type = Transaction.TYPE.valueOf(rs.getString(2));
                double amount = rs.getDouble(3);
                String description = rs.getString(4);
                int accountId = rs.getInt(5);

                controllerFactory.transactionController.addTransaction(new Transaction(id, type, amount, description, accountId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadGoalsFromDB() {
        ResultSet rs = GoalRepository.getGoals();

        try {
            while (rs.next()) {
                int id = rs.getInt(1);
                String goalDesc = rs.getString(2);
                double value = rs.getDouble(3);
                int accountId = rs.getInt(4);

                controllerFactory.goalController.addGoal(new Goal(id, goalDesc, value, accountId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
