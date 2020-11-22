package SatisfactoryCalculator.Service;

import SatisfactoryCalculator.DataHandler.MySqlDB;
import SatisfactoryCalculator.Model.Recipe;

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

public class RecipeService {

    public static Vector<Recipe> getAllDefaultRecipeOfItemByUUID(String itemUUID){
        String sqlQuery = "SELECT " +
                "recipeUUID, recipeName, " +
                "outputMaterial1UUID, outputMaterial1Amount, " +
                "outputMaterial2UUID, outputMaterial2Amount, " +
                "craftTime, buildingUUID, altRecipe, " +
                "inputMaterial1UUID, inputMaterial1Amount, " +
                "inputMaterial2UUID, inputMaterial2Amount, " +
                "inputMaterial3UUID, inputMaterial3Amount, " +
                "inputMaterial4UUID, inputMaterial4Amount " +
                "FROM recipe WHERE (outputMaterial1UUID='"+itemUUID+"' " +
                "OR outputMaterial2UUID='"+itemUUID+"') " +
                "AND altRecipe='False'";
        return getRecipes(sqlQuery);
    }

    public static Vector<Recipe> getAllRecipe(){
        String sqlQuery = "SELECT " +
                "recipeUUID, recipeName, " +
                "outputMaterial1UUID, outputMaterial1Amount, " +
                "outputMaterial2UUID, outputMaterial2Amount, " +
                "craftTime, buildingUUID, altRecipe, " +
                "inputMaterial1UUID, inputMaterial1Amount, " +
                "inputMaterial2UUID, inputMaterial2Amount, " +
                "inputMaterial3UUID, inputMaterial3Amount, " +
                "inputMaterial4UUID, inputMaterial4Amount " +
                "FROM recipe";
        return getRecipes(sqlQuery);
    }

    private static Vector<Recipe> getRecipes(String sqlQuery){
        Connection connection;
        PreparedStatement prepStmt;
        ResultSet resultSet;
        Vector<Recipe> recipes = new Vector<>();
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            resultSet = prepStmt.executeQuery();
            while (resultSet.next()){
                Recipe recipe = new Recipe();
                setValues(resultSet, recipe);
                recipes.add(recipe);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            MySqlDB.sqlClose();
        }
        return recipes;
    }

    private static Recipe getRecipe(String sqlQuery){
        Connection connection;
        PreparedStatement prepStmt;
        ResultSet resultSet;
        Recipe recipe = new Recipe();
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            resultSet = prepStmt.executeQuery();
            while (resultSet.next()){
                setValues(resultSet, recipe);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            MySqlDB.sqlClose();
        }
        return recipe;
    }

    public static boolean isRecipe(String itemUUID){
        int cnt = 0;
        String sqlQuery = "SELECT COUNT(*)" +
                " FROM recipe WHERE outputMaterial1UUID='"+itemUUID+"' OR outputMaterial2UUID='"+itemUUID+"'";
        Connection connection;
        PreparedStatement prepStmt;
        ResultSet resultSet;
        try {
            connection = MySqlDB.getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            resultSet = prepStmt.executeQuery();
            while (resultSet.next()){
                cnt++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            MySqlDB.sqlClose();
        }
        return cnt > 0;
    }

    public static Vector<Recipe> getAllRecipeOfMaterialByUUID(String itemUUID){
        String sqlQuery = "SELECT " +
                "recipeUUID, recipeName, " +
                "outputMaterial1UUID, outputMaterial1Amount, " +
                "outputMaterial2UUID, outputMaterial2Amount, " +
                "craftTime, buildingUUID, altRecipe, " +
                "inputMaterial1UUID, inputMaterial1Amount, " +
                "inputMaterial2UUID, inputMaterial2Amount, " +
                "inputMaterial3UUID, inputMaterial3Amount, " +
                "inputMaterial4UUID, inputMaterial4Amount " +
                "FROM recipe WHERE outputMaterial1UUID='"+itemUUID+"' " +
                "OR outputMaterial2UUID='"+itemUUID+"'";
        return getRecipes(sqlQuery);
    }

    public static Recipe getRecipeByName(String recipeName){
        String sqlQuery = "SELECT " +
                "recipeUUID, recipeName, " +
                "outputMaterial1UUID, outputMaterial1Amount, " +
                "outputMaterial2UUID, outputMaterial2Amount, " +
                "craftTime, buildingUUID, altRecipe, " +
                "inputMaterial1UUID, inputMaterial1Amount, " +
                "inputMaterial2UUID, inputMaterial2Amount, " +
                "inputMaterial3UUID, inputMaterial3Amount, " +
                "inputMaterial4UUID, inputMaterial4Amount " +
                "FROM recipe WHERE recipeName='"+recipeName+"'";
        return getRecipe(sqlQuery);
    }

    public static Recipe getRecipeByUUID(String recipeUUID){
        String sqlQuery = "SELECT " +
                "recipeUUID, recipeName, " +
                "outputMaterial1UUID, outputMaterial1Amount, " +
                "outputMaterial2UUID, outputMaterial2Amount, " +
                "craftTime, buildingUUID, altRecipe, " +
                "inputMaterial1UUID, inputMaterial1Amount, " +
                "inputMaterial2UUID, inputMaterial2Amount, " +
                "inputMaterial3UUID, inputMaterial3Amount, " +
                "inputMaterial4UUID, inputMaterial4Amount " +
                "FROM recipe WHERE recipeUUID='"+recipeUUID+"'";
        return getRecipe(sqlQuery);
    }

    private static void setValues(ResultSet resultSet, Recipe recipe) throws SQLException {
        recipe.setRecipeUUID(resultSet.getString("recipeUUID"));
        recipe.setRecipeName(resultSet.getString("recipeName"));
        recipe.setOutputMaterial1UUID(resultSet.getString("outputMaterial1UUID"));
        recipe.setOutputMaterial1Amount(resultSet.getString("outputMaterial1Amount"));
        recipe.setOutputMaterial2UUID(resultSet.getString("outputMaterial2UUID"));
        recipe.setOutputMaterial2Amount(resultSet.getString("outputMaterial2Amount"));
        recipe.setCraftTime(resultSet.getString("craftTime"));
        recipe.setBuildingUUID(resultSet.getString("buildingUUID"));
        recipe.setAltRecipe(resultSet.getString("altRecipe"));
        recipe.setInputMaterial1UUID(resultSet.getString("inputMaterial1UUID"));
        recipe.setInputMaterial2UUID(resultSet.getString("inputMaterial2UUID"));
        recipe.setInputMaterial3UUID(resultSet.getString("inputMaterial3UUID"));
        recipe.setInputMaterial4UUID(resultSet.getString("inputMaterial4UUID"));
        recipe.setInputMaterial1Amount(resultSet.getString("inputMaterial1Amount"));
        recipe.setInputMaterial2Amount(resultSet.getString("inputMaterial2Amount"));
        recipe.setInputMaterial3Amount(resultSet.getString("inputMaterial3Amount"));
        recipe.setInputMaterial4Amount(resultSet.getString("inputMaterial4Amount"));
    }
}
