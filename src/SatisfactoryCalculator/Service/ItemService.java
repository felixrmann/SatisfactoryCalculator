package SatisfactoryCalculator.Service;

import SatisfactoryCalculator.DataHandler.MySqlDB;
import SatisfactoryCalculator.Model.Item;
import SatisfactoryCalculator.Model.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-11
 */

public class ItemService {

    public static ResultSet executeSelect(String sqlQuery){
        try {
            Connection connection = MySqlDB.getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(sqlQuery);
            return prepStmt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static int executeUpdate(String sqlQuery){
        try {
            Connection connection = MySqlDB.getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(sqlQuery);
            return prepStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static Vector<Item> getAllItem(){
        Connection connection;
        PreparedStatement prepStmt;
        ResultSet resultSet;
        Vector<Item> allItem = new Vector<>();
        String sqlQuery = "SELECT itemName, itemUUID FROM item";
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            resultSet = prepStmt.executeQuery();
            while (resultSet.next()){
                Item item = new Item();
                setValues(resultSet, item);
                allItem.add(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            MySqlDB.sqlClose();
        }
        return allItem;
    }

    public static Result saveChanges(Item item){
        String sqlQuery = "UPDATE item " +
                "SET itemName='" + item.getItemName() + "', " +
                "WHERE itemUUID='" + item.getItemUUID() + "'";
        int rowsAffected = executeUpdate(sqlQuery);

        if (rowsAffected == 1) return Result.SUCCESS;
        else if (rowsAffected == 0) return Result.NOACTION;
        else return Result.ERROR;
    }

    public static Result saveNew(Item item){
        String sqlQuery = "INSERT INTO item " +
                "(itemUUID, itemName) " +
                "VALUES " +
                "('" + UUID.randomUUID().toString() + "', " +
                "'" + item.getItemName() + "')";
        int rowsAffected = executeUpdate(sqlQuery);

        if (rowsAffected == 1) return Result.SUCCESS;
        else if (rowsAffected == 0) return Result.NOACTION;
        else return Result.ERROR;
    }

    private static Item getItem(String sqlQuery){
        Item item = new Item();
        try {
            ResultSet resultSet = executeSelect(sqlQuery);
            while (resultSet.next()){
                setValues(resultSet, item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            MySqlDB.sqlClose();
        }
        return item;
    }

    public static Item getItemByName(String itemName){
        String sqlQuery = "SELECT itemName, itemUUID FROM item " +
                "WHERE itemName='"+itemName+"'";
        return getItem(sqlQuery);
    }

    public static Item getItemByUUID(String itemUUID){
        String sqlQuery = "SELECT itemName, itemUUID FROM item " +
                "WHERE itemUUID='"+itemUUID+"'";
        return getItem(sqlQuery);
    }

    private static void setValues(ResultSet resultSet, Item item) throws SQLException {
        item.setItemUUID(resultSet.getString("itemUUID"));
        item.setItemName(resultSet.getString("itemName"));
    }
}
