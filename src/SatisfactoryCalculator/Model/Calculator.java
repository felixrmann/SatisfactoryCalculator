package SatisfactoryCalculator.Model;

import SatisfactoryCalculator.Service.MaterialService;
import SatisfactoryCalculator.Service.RecipeService;
import SatisfactoryCalculator.View.CalculateView;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-18
 */

public class Calculator {

    private CalculateView calculateView;
    private String materialName;
    private Integer materialAmountPerMinute;

    public Calculator(CalculateView calculateView, String materialName, Integer materialAmountPerMinute) {
        this.calculateView = calculateView;
        this.materialName = materialName;
        this.materialAmountPerMinute = materialAmountPerMinute;

        allRecipesOfMaterial();
    }

    public Map<Recipe, Integer> allRecipesOfMaterial() {
        //TODO only default recipe
        Map<Recipe, Integer> allRecipe = new HashMap<>();
        String materialUUID = MaterialService.getMaterialByName(materialName).getMaterialUUID();
        Vector<Recipe> allPossibleRecipe = RecipeService.getAllDefaultRecipeOfItemByUUID(materialUUID);

        for (Recipe recipe : allPossibleRecipe) {
            allRecipe.putAll(getAllRecipeOfMaterial(recipe));
        }


        return allRecipe;
    }

    private Map<Recipe, Integer> getAllRecipeOfMaterial(Recipe recipe) {
        Map<Recipe, Integer> recipes = new HashMap<>();
        recipes.put(recipe, 0);

        recipes.putAll(getInputRecipeOfRecipe(recipe, 1));

        recipes.forEach((key, value) -> System.out.println(value + ": \n" + key));

        return recipes;
    }

    private Map<Recipe, Integer> getInputRecipeOfRecipe(Recipe recipe, Integer cnt){
        Map<Recipe, Integer> recipes = new HashMap<>();
        if (hasInputRecipe(recipe)){
            Vector<Recipe> temp1 = RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial1UUID()).getMaterialUUID());
            Recipe optimalRecipe1 = getOptimalRecipe(temp1);
            addRecipeToMap(recipes, optimalRecipe1, cnt);
            if (hasUnderRecipe(optimalRecipe1)){
                recipes.putAll(getInputRecipeOfRecipe(optimalRecipe1, cnt + 1));
            }
            if (recipe.getInputMaterial2UUID() != null && !recipe.getInputMaterial2UUID().equals("None")){
                Vector<Recipe> temp2 = RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial2UUID()).getMaterialUUID());
                Recipe optimalRecipe2 = getOptimalRecipe(temp2);
                addRecipeToMap(recipes, optimalRecipe2, cnt);
                if (hasUnderRecipe(optimalRecipe2)){
                    recipes.putAll(getInputRecipeOfRecipe(optimalRecipe2, cnt + 1));
                }
            }
            if (recipe.getInputMaterial3UUID() != null && !recipe.getInputMaterial3UUID().equals("None")){
                Vector<Recipe> temp3 = RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial3UUID()).getMaterialUUID());
                Recipe optimalRecipe3 = getOptimalRecipe(temp3);
                addRecipeToMap(recipes, optimalRecipe3, cnt);
                if (hasUnderRecipe(optimalRecipe3)){
                    recipes.putAll(getInputRecipeOfRecipe(optimalRecipe3, cnt + 1));
                }
            }
            if (recipe.getInputMaterial4UUID() != null && !recipe.getInputMaterial4UUID().equals("None")){
                Vector<Recipe> temp4 = RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial4UUID()).getMaterialUUID());
                Recipe optimalRecipe4 = getOptimalRecipe(temp4);
                addRecipeToMap(recipes, optimalRecipe4, cnt);
                if (hasUnderRecipe(optimalRecipe4)){
                    recipes.putAll(getInputRecipeOfRecipe(optimalRecipe4, cnt + 1));
                }
            }
        }
        return recipes;
    }

    private Recipe getOptimalRecipe(Vector<Recipe> tempVector){
        if (tempVector.size() != 0){
            int outAmount = 0;
            int inAmount = 0;
            int pos = 0;
            for (int i = 0; i < tempVector.size(); i++) {
                int tempOut = getAmountOut(tempVector.get(i));
                if (outAmount > tempOut) {
                    pos = i;
                    outAmount = tempOut;
                    int tempIn = getAmountIn(tempVector.get(i));
                    if (inAmount > tempIn) {
                        inAmount = tempIn;
                        pos = i;
                    }
                }
            }
            return tempVector.get(pos);
        }
        return null;
    }

    private int getAmountIn(Recipe recipe){
        int cnt = 1;
        if (!recipe.getInputMaterial2UUID().equals("None")){
            cnt++;
            if (!recipe.getInputMaterial3UUID().equals("None")){
                cnt++;
                if (!recipe.getInputMaterial4UUID().equals("None")){
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private int getAmountOut(Recipe recipe){
        int cnt = 1;
        if (!recipe.getOutputMaterial2UUID().equals("None")) cnt++;
        return cnt;
    }

    private boolean hasUnderRecipe(Recipe recipe){
        if (recipe != null){
            return RecipeService.isRecipe(recipe.getRecipeUUID());
        }
        return false;
    }

    private void addRecipeToMap(Map<Recipe, Integer> recipeMap, Recipe recipe, Integer cnt){
        recipeMap.put(recipe, cnt);
    }

    private boolean hasInputRecipe(Recipe recipe){
        if (RecipeService.isRecipe(recipe.getInputMaterial1UUID())) return true;
        if (RecipeService.isRecipe(recipe.getInputMaterial2UUID())) return true;
        if (RecipeService.isRecipe(recipe.getInputMaterial3UUID())) return true;
        return RecipeService.isRecipe(recipe.getInputMaterial4UUID());
    }
}