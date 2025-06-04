package repositories;

import database.Database;

import java.sql.*;

public class TransactionRepository {
    public static int insertTransaction(String type, double amount, String description, int account_id) {
        String insertStatement = "INSERT INTO transactions (type,amount,description,account_id) VALUES (?,?,?,?)";
        int generatedId = -1;
        Connection connection = Database.getConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement(insertStatement, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, type);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, description);
            pstmt.setInt(4, account_id);

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

    public static void removeTransaction(int id) {
        String sql = "DELETE FROM transactions WHERE id = (?)";

        Connection connection = Database.getConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getTransactions() {
        String sql = "SELECT * FROM transactions";

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
