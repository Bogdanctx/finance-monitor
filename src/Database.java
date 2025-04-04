import java.sql.*;

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

    public static int insertIntoDatabase(String table, String valuesOrder, String values) {
        int generatedId = 0;

        try {
            String sql = "INSERT INTO " + table + " " + valuesOrder + " VALUES " + values + ";";

            Statement st = connection.createStatement();
            st.executeUpdate(sql);

            ResultSet rs = st.getGeneratedKeys();

            rs.next();
            generatedId = rs.getInt(1);

        } catch (SQLException e) {
            System.out.println("[ERROR] > " + e);
        }

        return generatedId;
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
