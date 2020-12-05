package SatisfactoryCalculator.Model;

import SatisfactoryCalculator.Service.MaterialService;
import SatisfactoryCalculator.Service.RecipeService;

import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-Dezember-04
 */

public class Organizer {
    private static int globalCount = 0;
    private static Vector<Orderer> allRecipe = new Vector<>();

    public static Vector<Orderer> getAllRecipeOfMaterial(String materialName){
        Material searchMaterial = MaterialService.getMaterialByName(materialName);
        Vector<Recipe> defaultRecipes = RecipeService.getAllDefaultRecipeOfItemByUUID(searchMaterial.getMaterialUUID());
        Recipe bestRecipe = getOptimalRecipe(defaultRecipes);

        addRecipeToVector(bestRecipe, 0);
        getInputRecipeOfRecipe(bestRecipe, 1);

        /*
        for (Orderer orderer : allRecipe) {
            System.out.println("Pos: " + orderer.getPosition());
            System.out.println("Inset: " + orderer.getInset());
            System.out.println("Recipe: " + orderer.getRecipe().toString());
        }

         */


        return allRecipe;
    }

    private static void getInputRecipeOfRecipe(Recipe recipe, Integer cnt){
        if (hasInputRecipe(recipe)){
            Vector<Recipe> temp1 = RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial1UUID()).getMaterialUUID());
            Recipe optimalRecipe1 = getOptimalRecipe(temp1);
            addRecipeToVector(optimalRecipe1, cnt);
            if (hasUnderRecipe(optimalRecipe1)){
                getInputRecipeOfRecipe(optimalRecipe1, cnt + 1);
            }
            if (recipe.getInputMaterial2UUID() != null && !recipe.getInputMaterial2UUID().equals("None")){
                Vector<Recipe> temp2 = RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial2UUID()).getMaterialUUID());
                Recipe optimalRecipe2 = getOptimalRecipe(temp2);
                addRecipeToVector(optimalRecipe2, cnt);
                if (hasUnderRecipe(optimalRecipe2)){
                    getInputRecipeOfRecipe(optimalRecipe2, cnt + 1);
                }
            }
            if (recipe.getInputMaterial3UUID() != null && !recipe.getInputMaterial3UUID().equals("None")){
                Vector<Recipe> temp3 = RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial3UUID()).getMaterialUUID());
                Recipe optimalRecipe3 = getOptimalRecipe(temp3);
                addRecipeToVector(optimalRecipe3, cnt);
                if (hasUnderRecipe(optimalRecipe3)){
                    getInputRecipeOfRecipe(optimalRecipe3, cnt + 1);
                }
            }
            if (recipe.getInputMaterial4UUID() != null && !recipe.getInputMaterial4UUID().equals("None")){
                Vector<Recipe> temp4 = RecipeService.getAllDefaultRecipeOfItemByUUID(MaterialService.getMaterialByUUID(recipe.getInputMaterial4UUID()).getMaterialUUID());
                Recipe optimalRecipe4 = getOptimalRecipe(temp4);
                addRecipeToVector(optimalRecipe4, cnt);
                if (hasUnderRecipe(optimalRecipe4)){
                    getInputRecipeOfRecipe(optimalRecipe4, cnt + 1);
                }
            }
        }

    }

    private static void addRecipeToVector(Recipe recipe, Integer inset){
        allRecipe.add(new Orderer(recipe, inset, globalCount));
        globalCount++;
    }

    private static Recipe getOptimalRecipe(Vector<Recipe> tempVector){
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

    private static int getAmountIn(Recipe recipe){
        int cnt = 0;
        if (!recipe.getInputMaterial1UUID().equals("None")){
            cnt++;
            if (!recipe.getInputMaterial2UUID().equals("None")){
                cnt++;
                if (!recipe.getInputMaterial3UUID().equals("None")){
                    cnt++;
                    if (!recipe.getInputMaterial4UUID().equals("None")){
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    private static int getAmountOut(Recipe recipe){
        int cnt = 1;
        if (!recipe.getOutputMaterial2UUID().equals("None")) cnt++;
        return cnt;
    }

    private static boolean hasInputRecipe(Recipe recipe){
        if (!recipe.getInputMaterial1UUID().equals("None")){
            if (RecipeService.isRecipe(recipe.getInputMaterial1UUID())) return true;
            if (RecipeService.isRecipe(recipe.getInputMaterial2UUID())) return true;
            if (RecipeService.isRecipe(recipe.getInputMaterial3UUID())) return true;
            if (RecipeService.isRecipe(recipe.getInputMaterial4UUID())) return true;
        }
        return false;
    }

    private static boolean hasUnderRecipe(Recipe recipe){
        if (recipe != null) return RecipeService.isRecipe(recipe.getRecipeUUID());
        return false;
    }
}
