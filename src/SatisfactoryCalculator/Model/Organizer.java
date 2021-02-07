package SatisfactoryCalculator.Model;

import SatisfactoryCalculator.Service.ItemService;
import SatisfactoryCalculator.Service.RecipeService;

import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-Dezember-04
 */

public class Organizer {
    private static int globalCount = 0;
    private static Vector<Orderer> allRecipe;

    public static Vector<Orderer> getAllRecipeOfItem(Item item){
        globalCount = 0;
        allRecipe = new Vector<>();

        Vector<Recipe> defaultRecipes = RecipeService.getAllDefaultRecipeOfItemByUUID(item.getItemUUID());
        Recipe bestRecipe = getOptimalRecipe(defaultRecipes);

        addRecipeToVector(bestRecipe, 1, 0);
        getInputRecipeOfRecipe(bestRecipe, 1);

        return allRecipe;
    }

    public static Vector<Orderer> getAllRecipeOfRecipe(Recipe recipe){
        globalCount = 0;
        allRecipe = new Vector<>();

        addRecipeToVector(recipe, 1, 0);
        getInputRecipeOfRecipe(recipe, 1);

        return allRecipe;
    }

    private static void getInputRecipeOfRecipe(Recipe recipe, Integer cnt){
        if (hasInputRecipe(recipe)){
            Vector<Recipe> temp1 = RecipeService.getAllDefaultRecipeOfItemByUUID(ItemService.getItemByUUID(recipe.getInputMaterial1UUID()).getItemUUID());
            Recipe optimalRecipe1 = getOptimalRecipe(temp1);
            addRecipeToVector(optimalRecipe1, 1, cnt);
            if (hasUnderRecipe(optimalRecipe1)){
                getInputRecipeOfRecipe(optimalRecipe1, cnt + 1);
            }
            if (recipe.getInputMaterial2UUID() != null && !recipe.getInputMaterial2UUID().equals("None")){
                Vector<Recipe> temp2 = RecipeService.getAllDefaultRecipeOfItemByUUID(ItemService.getItemByUUID(recipe.getInputMaterial2UUID()).getItemUUID());
                Recipe optimalRecipe2 = getOptimalRecipe(temp2);
                addRecipeToVector(optimalRecipe2, 2, cnt);
                if (hasUnderRecipe(optimalRecipe2)){
                    getInputRecipeOfRecipe(optimalRecipe2, cnt + 1);
                }
            }
            if (recipe.getInputMaterial3UUID() != null && !recipe.getInputMaterial3UUID().equals("None")){
                Vector<Recipe> temp3 = RecipeService.getAllDefaultRecipeOfItemByUUID(ItemService.getItemByUUID(recipe.getInputMaterial3UUID()).getItemUUID());
                Recipe optimalRecipe3 = getOptimalRecipe(temp3);
                addRecipeToVector(optimalRecipe3, 3, cnt);
                if (hasUnderRecipe(optimalRecipe3)){
                    getInputRecipeOfRecipe(optimalRecipe3, cnt + 1);
                }
            }
            if (recipe.getInputMaterial4UUID() != null && !recipe.getInputMaterial4UUID().equals("None")){
                Vector<Recipe> temp4 = RecipeService.getAllDefaultRecipeOfItemByUUID(ItemService.getItemByUUID(recipe.getInputMaterial4UUID()).getItemUUID());
                Recipe optimalRecipe4 = getOptimalRecipe(temp4);
                addRecipeToVector(optimalRecipe4, 4, cnt);
                if (hasUnderRecipe(optimalRecipe4)){
                    getInputRecipeOfRecipe(optimalRecipe4, cnt + 1);
                }
            }
        }

        //TODO save side products
    }

    private static void addRecipeToVector(Recipe recipe, Integer inNum, Integer inset){
        allRecipe.add(new Orderer(recipe, inNum, inset, globalCount));
        globalCount++;
    }

    private static Recipe getOptimalRecipe(Vector<Recipe> tempVector){
        if (tempVector.size() != 0){
            int optimalPosition = 0;
            int leastInput = 10;
            int leastOutput = 10;
            for (int i = 0; i < tempVector.size(); i++) {
                int currentLeastOutput = 0;
                int currentLeastInput = 0;

                if (!tempVector.get(i).getOutputMaterial1UUID().equals("None")) currentLeastOutput++;
                if (!tempVector.get(i).getOutputMaterial2UUID().equals("None")) currentLeastOutput++;

                if (!tempVector.get(i).getInputMaterial1UUID().equals("None")) currentLeastInput++;
                if (!tempVector.get(i).getInputMaterial2UUID().equals("None")) currentLeastInput++;
                if (!tempVector.get(i).getInputMaterial3UUID().equals("None")) currentLeastInput++;
                if (!tempVector.get(i).getInputMaterial4UUID().equals("None")) currentLeastInput++;

                if (currentLeastOutput < leastOutput) optimalPosition = i;
                else if ((currentLeastInput < leastInput)) optimalPosition = i;
            }
            return tempVector.get(optimalPosition);
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
            return RecipeService.isRecipe(recipe.getInputMaterial4UUID());
        }
        return false;
    }

    private static boolean hasUnderRecipe(Recipe recipe){
        if (recipe != null) return RecipeService.isRecipe(recipe.getRecipeUUID());
        return false;
    }
}
