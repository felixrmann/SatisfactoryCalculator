package SatisfactoryCalculator.Service;

import SatisfactoryCalculator.DataHandler.MySqlDB;
import SatisfactoryCalculator.Model.Recipe;
import SatisfactoryCalculator.Model.Result;

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

    public static Result delete(Recipe recipe){
        String sqlQuery = "DELETE FROM recipe " +
                "WHERE recipeUUID='" + recipe.getRecipeUUID() + "'";
        int rowsAffected = executeUpdate(sqlQuery);

        if (rowsAffected == 1) return Result.SUCCESS;
        else if (rowsAffected == 0) return Result.NOACTION;
        else return Result.ERROR;
    }

    public static Result saveChanges(Recipe recipe, String recipeUUID){
        String sqlQuery = "UPDATE recipe " +
                "SET recipeName='" + recipe.getRecipeName() + "', " +
                "outputMaterial1UUID='" + recipe.getOutputMaterial1UUID() + "', " +
                "outputMaterial1Amount='" + recipe.getOutputMaterial1Amount() + "', " +
                "outputMaterial2UUID='" + recipe.getOutputMaterial2UUID() + "', " +
                "outputMaterial2Amount='" + recipe.getOutputMaterial2Amount() + "', " +
                "craftTime='" + recipe.getCraftTime() + "', " +
                "buildingUUID='" + recipe.getBuildingUUID() + "', " +
                "altRecipe='" + recipe.isAltRecipe() + "', " +
                "inputMaterial1UUID='" + recipe.getInputMaterial1UUID() + "', " +
                "inputMaterial1Amount='" + recipe.getInputMaterial1Amount() + "', " +
                "inputMaterial2UUID='" + recipe.getInputMaterial2UUID() + "', " +
                "inputMaterial2Amount='" + recipe.getInputMaterial2Amount() + "', " +
                "inputMaterial3UUID='" + recipe.getInputMaterial3UUID() + "', " +
                "inputMaterial3Amount='" + recipe.getInputMaterial3Amount() + "', " +
                "inputMaterial4UUID='" + recipe.getInputMaterial4UUID() + "', " +
                "inputMaterial4Amount='" + recipe.getInputMaterial4Amount() + "' " +
                "WHERE recipeUUID='" + recipeUUID + "'";
        int rowsAffected = executeUpdate(sqlQuery);

        if (rowsAffected == 1) return Result.SUCCESS;
        else if (rowsAffected == 0) return Result.NOACTION;
        else return Result.ERROR;
    }

    public static Result saveNew(Recipe recipe){
        String sqlQuery = "INSERT INTO recipe " +
                "(recipeUUID, recipeName, outputMaterial1UUID, outputMaterial1Amount, " +
                "outputMaterial2UUID, outputMaterial2Amount, craftTime, buildingUUID, " +
                "altRecipe, inputMaterial1UUID, inputMaterial1Amount, inputMaterial2UUID, " +
                "inputMaterial2Amount, inputMaterial3UUID, inputMaterial3Amount, inputMaterial4UUID, " +
                "inputMaterial4Amount) " +
                "VALUES " +
                "('" + recipe.getRecipeUUID() + "', " +
                "'" + recipe.getRecipeName() + "', " +
                "'" + recipe.getOutputMaterial1UUID() + "', " +
                "'" + recipe.getOutputMaterial1Amount() + "', " +
                "'" + recipe.getOutputMaterial2UUID() + "', " +
                "'" + recipe.getOutputMaterial2Amount() + "', " +
                "'" + recipe.getCraftTime() + "', " +
                "'" + recipe.getBuildingUUID() + "', " +
                "'" + recipe.isAltRecipe() + "', " +
                "'" + recipe.getInputMaterial1UUID() + "', " +
                "'" + recipe.getInputMaterial1Amount() + "', " +
                "'" + recipe.getInputMaterial2UUID() + "', " +
                "'" + recipe.getInputMaterial2Amount() + "', " +
                "'" + recipe.getInputMaterial3UUID() + "', " +
                "'" + recipe.getInputMaterial3Amount() + "', " +
                "'" + recipe.getInputMaterial4UUID() + "', " +
                "'" + recipe.getInputMaterial4Amount() + "')";
        int rowsAffected = executeUpdate(sqlQuery);

        if (rowsAffected == 0) return Result.SUCCESS;
        else if (rowsAffected == 1) return Result.NOACTION;
        else return Result.ERROR;
    }

    private static Vector<Recipe> getRecipes(String sqlQuery){
        Vector<Recipe> recipes = new Vector<>();
        try {
            ResultSet resultSet = executeSelect(sqlQuery);
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
        Recipe recipe = new Recipe();
        try {
            ResultSet resultSet = executeSelect(sqlQuery);
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
