package SatisfactoryCalculator.Service;

import SatisfactoryCalculator.DataHandler.MySqlDB;
import SatisfactoryCalculator.Model.Building;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-11
 */

public class BuildingService {

    public static Vector<Building> getAllBuilding(){
        Connection connection;
        PreparedStatement prepStmt;
        ResultSet resultSet;
        Vector<Building> allBuildings = new Vector<>();
        String sqlQuery = "SELECT buildingUUID, buildingName FROM building";
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            resultSet = prepStmt.executeQuery();
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
        return  allBuildings;
    }

    private static Building getBuilding(String sqlQuery){
        Connection connection;
        PreparedStatement prepStmt;
        ResultSet resultSet;
        Building building = new Building();
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            resultSet = prepStmt.executeQuery();
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
                "FROM building WHERE buildingName='"+buildingUUID+"'";
        return getBuilding(sqlQuery);
    }

    private static void setValues(ResultSet resultSet, Building building) throws SQLException {
        building.setBuildingUUID(resultSet.getString("buildingUUID"));
        building.setBuildingName(resultSet.getString("buildingName"));
    }
}
