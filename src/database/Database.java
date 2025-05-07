package database;

import controller.ControllerFactory;
import entity.Account;
import entity.Goal;
import entity.Transaction;

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
        ResultSet rs = Database.selectFrom("accounts", "*");

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
        ResultSet rs = selectFrom("transactions", "*");

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
        ResultSet rs = Database.selectFrom("goals", "*");

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

    public static ResultSet selectFrom(String table, String values) {
        ResultSet rs = null;

        try {
            rs = connection.createStatement().executeQuery("SELECT " + values + " FROM " + table + ";");
        } catch (SQLException e) {
            System.out.println("[ERROR] > " + e);
        }

        return rs;
    }

    /// This function inserts a row into a table and then return the last ID used as primary key for that row
    /// INSERT INTO @table @valuesOrder VALUES @values;
    public static int insertIntoDatabase(String table, String valuesOrder, String values) {
        int generatedId = 1;

        try {
            String sql = "INSERT INTO " + table + " " + valuesOrder + " VALUES " + values + ";";

            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            if(rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] > " + e);
        }

        return generatedId;
    }

    public static void updateRow(String table, String newValues, String whereClause) {
        try {
            String sql = "UPDATE " + table + " SET " + newValues + " WHERE " + whereClause + ";";

            Statement st = connection.createStatement();
            st.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println("[ERROR] > " + e);
        }
    }

    public static void deleteRow(String table, String whereClause) {
        String sql = "DELETE FROM " + table + " WHERE " + whereClause;

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("[ERROR] > " + e);
        }
    }

}
