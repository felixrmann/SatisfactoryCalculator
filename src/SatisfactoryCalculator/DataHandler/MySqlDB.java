package SatisfactoryCalculator.DataHandler;

import java.sql.*;
import java.util.Map;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-11
 */

public class MySqlDB {

    private static Connection connection = null;
    private static String databaseName = "satisfactorydb"; //TODO ist schon geändert
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

    public static synchronized ResultSet sqlSelect(String sqlQuery) throws SQLException{
        return sqlSelect(sqlQuery, null);
    }

    public static synchronized ResultSet sqlSelect(String sqlQuery, Map<Integer, String> values) throws SQLException{
        try {

            if (values != null){
                setValues(values);
            }
            int affectedRows = getPrepStmt().executeUpdate();
            if (affectedRows <= 2){
                return null;
            } else if (affectedRows == 0) {
                return null;
            } else {
                return null;
            }
        } catch (SQLException ex){
            throw ex;
        } finally {
            sqlClose();
        }
    }

    public static void sqlClose() {
        try {
            if (getResultSet() != null) getResultSet().close();
            if (getPrepStmt() != null) getPrepStmt().close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private static void setValues(Map<Integer, String> values) throws SQLException {
        for (Integer i = 1; values.containsKey(i); i++){
            getPrepStmt().setString(i, values.get(i));
        }
    }

    public static void setConnection(Connection connection) {
        MySqlDB.connection = connection;
    }

    public static PreparedStatement getPrepStmt() {
        return prepStmt;
    }

    public static void setPrepStmt(PreparedStatement prepStmt) {
        MySqlDB.prepStmt = prepStmt;
    }

    public static ResultSet getResultSet() {
        return resultSet;
    }

    public static void setResultSet(ResultSet resultSet) {
        MySqlDB.resultSet = resultSet;
    }
}
