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
            addRecipeToMap(recipes, temp1, cnt);
            if (hasUnderRecipe(temp1)){
                for (Recipe value : temp1) {
                    recipes.putAll(getInputRecipeOfRecipe(value, cnt + 1));
                }
            }
            if (recipe.getInputMaterial2UUID() != null){
                Vector<Recipe> temp2 = RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial2UUID()).getMaterialUUID());
                addRecipeToMap(recipes, temp2, cnt);
                if (hasUnderRecipe(temp2)){
                    for (Recipe value : temp2) {
                        recipes.putAll(getInputRecipeOfRecipe(value, cnt + 1));
                    }
                }
            }
            if (recipe.getInputMaterial3UUID() != null){
                Vector<Recipe> temp3 = RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial3UUID()).getMaterialUUID());
                addRecipeToMap(recipes, temp3, cnt);
                if (hasUnderRecipe(temp3)){
                    for (Recipe value : temp3) {
                        recipes.putAll(getInputRecipeOfRecipe(value, cnt + 1));
                    }
                }
            }
            if (recipe.getInputMaterial4UUID() != null){
                Vector<Recipe> temp4 = RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial4UUID()).getMaterialUUID());
                addRecipeToMap(recipes, temp4, cnt);
                if (hasUnderRecipe(temp4)){
                    for (Recipe value : temp4) {
                        recipes.putAll(getInputRecipeOfRecipe(value, cnt + 1));
                    }
                }
            }
        }
        return recipes;
    }

    private boolean hasUnderRecipe(Vector<Recipe> recipes){
        for (Recipe recipe : recipes) {
            if (RecipeService.isRecipe(recipe.getRecipeUUID())) return true;
        }
        return false;
    }

    private void addRecipeToMap(Map<Recipe, Integer> recipeMap, Vector<Recipe> recipesVector, Integer cnt){
        for (Recipe recipe : recipesVector) {
            recipeMap.put(recipe, cnt);
        }
    }

    private boolean hasInputRecipe(Recipe recipe){
        if (RecipeService.isRecipe(recipe.getInputMaterial1UUID())) return true;
        if (RecipeService.isRecipe(recipe.getInputMaterial2UUID())) return true;
        if (RecipeService.isRecipe(recipe.getInputMaterial3UUID())) return true;
        return RecipeService.isRecipe(recipe.getInputMaterial4UUID());
    }

    private Vector<Recipe> getInputRecipeOfRecipe(Recipe recipe) {
        Vector<Recipe> temp1 = RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial1UUID()).getMaterialUUID());
        Vector<Recipe> inputRecipe = new Vector<>(temp1);
        if (recipe.getInputMaterial2UUID() != null) {
            inputRecipe.addAll(RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial2UUID()).getMaterialUUID()));
        }
        if (recipe.getInputMaterial3UUID() != null) {
            inputRecipe.addAll(RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial3UUID()).getMaterialUUID()));
        }
        if (recipe.getInputMaterial4UUID() != null) {
            inputRecipe.addAll(RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial4UUID()).getMaterialUUID()));
        }
        return inputRecipe;
    }

    public Map<String, Integer> calculateNumbers() {
        Map<String, Integer> data = new HashMap<>();
        Material finalMaterial = MaterialService.getMaterialByName(materialName);
        Vector<Recipe> recipes = RecipeService.getAllRecipeOfMaterialByUUID(finalMaterial.getMaterialUUID());
        Recipe defaultRecipe = recipes.get(0);

        System.out.println(defaultRecipe.toString());

        double rate = materialAmountPerMinute / defaultRecipe.getOutputMaterial1Amount();
        System.out.println(rate);

        Vector<Recipe> allRecipesOfmaterial = getAllRecipesOfMaterial(defaultRecipe);
        recipeOfmaterial(defaultRecipe);

        return data;
    }

    private Vector<Recipe> getAllRecipesOfMaterial(Recipe recipe) {
        int cnt = 0;
        Vector<Recipe> allRecipes = new Vector<>();
        Vector<String> recipeOfmaterial = new Vector<>();

        return null;
    }

    private int getAmountOfInputRecipe(Recipe recipe) {
        return 0;
    }

    private Vector<String> recipeOfmaterial(Recipe recipe) {
        Vector<Recipe> recipeOfmaterial = new Vector<>();
        Vector<String> materialUUIDsOfRecipe = cleanVectorOfmaterials(recipe);
        Vector<Vector<Recipe>> temp = new Vector<>();

        for (int i = 0; i < materialUUIDsOfRecipe.size(); i++) {
            if (RecipeService.isRecipe(materialUUIDsOfRecipe.get(i))) {

                //TODO return all recipes of material
                temp.add(getAlUnderRecipe(materialUUIDsOfRecipe.get(i)));

            }
        }

        System.out.println(materialUUIDsOfRecipe.toString());
        System.out.println(temp.toString());

        return null;
    }

    private Vector<Recipe> getAlUnderRecipe(String materialUUID) {
        return RecipeService.getAllRecipeOfMaterialByUUID(materialUUID);
    }

    private Vector<String> cleanVectorOfmaterials(Recipe recipe) {
        Vector<String> materialUUIDs = new Vector<>();
        materialUUIDs.add(recipe.getInputMaterial1UUID());
        materialUUIDs.add(recipe.getInputMaterial2UUID());
        materialUUIDs.add(recipe.getInputMaterial3UUID());
        materialUUIDs.add(recipe.getInputMaterial4UUID());
        removeNoneFromVector(materialUUIDs);
        return materialUUIDs;
    }

    private void removeNoneFromVector(Vector<String> inputVector) {
        for (int i = 3; i > 0; i--) {
            if (inputVector.get(i).equals("None")) inputVector.remove(i);
        }
    }
}