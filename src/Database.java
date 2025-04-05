import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Database {
    private static String url = "jdbc:mysql://localhost:3306/finance_monitor";
    private static Connection connection = null;

    static {
        try {
            connection = DriverManager.getConnection(url, "root", "root");
        } catch (SQLException e) {
            System.out.println("[ERROR] > " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
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
