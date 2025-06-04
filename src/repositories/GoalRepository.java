package repositories;

import database.Database;

import java.sql.*;

public class GoalRepository {
    public static int insertGoal(String goal, double value, int account_id) {
        String insertStatement = "INSERT INTO goals (goal,value,account_id) VALUES (?,?,?)";

        int generatedId = -1;
        Connection connection = Database.getConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement(insertStatement, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, goal);
            pstmt.setDouble(2, value);
            pstmt.setInt(3, account_id);

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

    public static ResultSet getGoals() {
        String sql = "SELECT * FROM goals";

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

    public static void removeGoal(int id) {
        String sql = "DELETE FROM goals WHERE id = (?)";

        Connection connection = Database.getConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDescription(int id, String description) {
        String sql = "UPDATE goals SET description = (?) WHERE id = (?)";

        Connection connection = Database.getConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, description);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateValue(int id, double value) {
        String sql = "UPDATE goals SET value = (?) WHERE id = (?)";

        Connection connection = Database.getConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setDouble(2, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
