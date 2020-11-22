package SatisfactoryCalculator.Service;

import SatisfactoryCalculator.DataHandler.MySqlDB;
import SatisfactoryCalculator.Model.Material;

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

public class MaterialService {

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

    private static Material getMaterial(String sqlQuery){
        Connection connection;
        PreparedStatement prepStmt;
        ResultSet resultSet;
        Material material = new Material();
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            resultSet = prepStmt.executeQuery();
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
