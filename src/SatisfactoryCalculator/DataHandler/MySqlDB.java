package SatisfactoryCalculator.DataHandler;

import java.sql.*;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-11
 */

public class MySqlDB {

    private static Connection connection = null;
    private static String databaseName = "satisfactorydb";
    private static String url = "jdbc:mysql://localhost:3306/"+databaseName+"?serverTimezone=UTC&useLegacyDatetimeCode=falseuseSSL=true";

    private static String userName = "felix";
    private static String password = "12345";

    private static PreparedStatement prepStmt;
    private static ResultSet resultSet;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connection == null || !connection.isValid(2)) {
                connection = DriverManager.getConnection(url, userName, password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void sqlClose() {
        try {
            if (getResultSet() != null) getResultSet().close();
            if (getPrepStmt() != null) getPrepStmt().close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static PreparedStatement getPrepStmt() {
        return prepStmt;
    }

    public static ResultSet getResultSet() {
        return resultSet;
    }
}
