package repositories;

import database.Database;

import java.sql.*;

public class AccountRepository {
    public static int insertAccount(String name, double balance) {
        String insertStatement = "INSERT INTO accounts (name, balance) VALUES (?,?)";
        int generatedId = -1;

        Connection connection = Database.getConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement(insertStatement, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setDouble(2, balance);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()) {
                    generatedId = rs.getInt(1);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    public static void removeAccount(int id) {
        String sql = "DELETE FROM accounts WHERE id = (?)";

        Connection connection = Database.getConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateName(String name, int id) {
        String sql = "UPDATE accounts SET name = (?) WHERE id = (?)";

        Connection connection = Database.getConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBalance(double balance, int id) {
        String sql = "UPDATE accounts SET balance = (?) WHERE id = (?)";

        Connection connection = Database.getConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, balance);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getAccounts() {
        String sql = "SELECT * FROM accounts";

        ResultSet rs = null;
        Connection connection = Database.getConnection();

        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }
}
