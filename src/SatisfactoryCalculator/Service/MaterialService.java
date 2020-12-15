package SatisfactoryCalculator.Service;

import SatisfactoryCalculator.DataHandler.MySqlDB;
import SatisfactoryCalculator.Model.Material;
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

public class MaterialService {

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

    public static Vector<Material> getAllMaterial(){
        Connection connection;
        PreparedStatement prepStmt;
        ResultSet resultSet;
        Vector<Material> allMaterial = new Vector<>();
        String sqlQuery = "SELECT materialName, materialUUID FROM material";
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            resultSet = prepStmt.executeQuery();
            while (resultSet.next()){
                Material material = new Material();
                setValues(resultSet, material);
                allMaterial.add(material);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            MySqlDB.sqlClose();
        }
        return allMaterial;
    }

    public static Result saveChanges(Material material){
        String sqlQuery = "UPDATE material " +
                "SET materialName='" + material.getMaterialName() + "', " +
                "WHERE materialUUID='" + material.getMaterialUUID() + "'";
        int rowsAffected = executeUpdate(sqlQuery);

        if (rowsAffected == 1) return Result.SUCCESS;
        else if (rowsAffected == 0) return Result.NOACTION;
        else return Result.ERROR;
    }

    public static Result saveNew(Material material){
        String sqlQuery = "INSERT INTO material " +
                "(materialUUID, materialName) " +
                "VALUES " +
                "('" + UUID.randomUUID().toString() + "', " +
                "'" + material.getMaterialName() + "')";
        int rowsAffected = executeUpdate(sqlQuery);

        if (rowsAffected == 1) return Result.SUCCESS;
        else if (rowsAffected == 0) return Result.NOACTION;
        else return Result.ERROR;
    }

    private static Material getMaterial(String sqlQuery){
        Material material = new Material();
        try {
            ResultSet resultSet = executeSelect(sqlQuery);
            while (resultSet.next()){
                setValues(resultSet, material);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            MySqlDB.sqlClose();
        }
        return material;
    }

    public static Material getMaterialByName(String materialName){
        String sqlQuery = "SELECT materialName, materialUUID FROM material " +
                "WHERE materialName='"+materialName+"'";
        return getMaterial(sqlQuery);
    }

    public static Material getMaterialByUUID(String materialUUID){
        String sqlQuery = "SELECT materialName, materialUUID FROM material " +
                "WHERE materialUUID='"+materialUUID+"'";
        return getMaterial(sqlQuery);
    }

    private static void setValues(ResultSet resultSet, Material material) throws SQLException {
        material.setMaterialUUID(resultSet.getString("materialUUID"));
        material.setMaterialName(resultSet.getString("materialName"));
    }
}
