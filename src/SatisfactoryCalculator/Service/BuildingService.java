package SatisfactoryCalculator.Service;

import SatisfactoryCalculator.DataHandler.MySqlDB;
import SatisfactoryCalculator.Model.Building;
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

public class BuildingService {

    private static ResultSet executeSelect(String sqlQuery){
        try {
            Connection connection = MySqlDB.getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(sqlQuery);
            return prepStmt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static int executeUpdate(String sqlQuery){
        try {
            Connection connection = MySqlDB.getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(sqlQuery);
            return prepStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static Result saveChanges(Building building, String buildingUUID){
        String sqlQuery = "UPDATE building " +
                "SET buildingName='" + building.getBuildingName() + "' " +
                "WHERE buildingUUID='" + buildingUUID + "'";
        int rowsAffected = executeUpdate(sqlQuery);

        if (rowsAffected == 1) return Result.SUCCESS;
        else if (rowsAffected == 0) return Result.NOACTION;
        else return Result.ERROR;
    }

    public static Result delete(Building building){
        String sqlQuery = "DELETE FROM building " +
                "WHERE buildingUUID='" + building.getBuildingUUID() + "'";
        int rowsAffected = executeUpdate(sqlQuery);

        if (rowsAffected == 1) return Result.SUCCESS;
        else if (rowsAffected == 0) return Result.NOACTION;
        else return Result.ERROR;
    }

    public static Result saveNew(Building building){
        String sqlQuery = "INSERT INTO building " +
                "(buildingUUID, buildingName) " +
                "VALUES " +
                "('" + UUID.randomUUID().toString() + "', " +
                "'" + building.getBuildingName() + "')";
        int rowsAffected = executeUpdate(sqlQuery);

        if (rowsAffected == 1) return Result.SUCCESS;
        else if (rowsAffected == 0) return Result.NOACTION;
        else return Result.ERROR;
    }



    public static Vector<Building> getAllBuilding(){
        Vector<Building> allBuildings = new Vector<>();
        String sqlQuery = "SELECT buildingUUID, buildingName FROM building";
        try {
            ResultSet resultSet = executeSelect(sqlQuery);
            while (resultSet.next()){
                Building building = new Building();
                setValues(resultSet, building);
                allBuildings.add(building);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            MySqlDB.sqlClose();
        }
        return allBuildings;
    }

    private static Building getBuilding(String sqlQuery){
        Building building = new Building();
        try {
            ResultSet resultSet = executeSelect(sqlQuery);
            while (resultSet.next()){
                setValues(resultSet, building);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            MySqlDB.sqlClose();
        }
        return building;
    }

    public static Building getBuildingByName(String buildingName){
        String sqlQuery = "SELECT buildingUUID, buildingName " +
                "FROM building WHERE buildingName='"+buildingName+"'";
        return getBuilding(sqlQuery);
    }

    public static Building getBuildingByUUID(String buildingUUID){
        String sqlQuery = "SELECT buildingUUID, buildingName " +
                "FROM building WHERE buildingUUID='"+buildingUUID+"'";
        return getBuilding(sqlQuery);
    }

    private static void setValues(ResultSet resultSet, Building building) throws SQLException {
        building.setBuildingUUID(resultSet.getString("buildingUUID"));
        building.setBuildingName(resultSet.getString("buildingName"));
    }
}
