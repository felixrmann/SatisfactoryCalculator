package SatisfactoryCalculator.Main;

import SatisfactoryCalculator.Model.Recipe;
import SatisfactoryCalculator.Service.RecipeService;

import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-12
 */

public class ConnectionTest {
    public static void main(String[] args) {
        Vector<Recipe> recipe = RecipeService.getAllRecipe();
        for (Recipe value : recipe) {
            System.out.println(value.toString());
        }
    }
}
