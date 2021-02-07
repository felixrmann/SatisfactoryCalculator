package SatisfactoryCalculator.Model;

import SatisfactoryCalculator.Service.BuildingService;

import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-18
 */

public class Calculator {

    private Vector<Orderer> allRecipe;
    private Vector<Orderer> calculatedOfRecipe;
    private Double amountPerMinute;

    public Calculator() {
        calculatedOfRecipe = new Vector<>();
    }

    public Vector<Orderer> getRecipeAmount(Vector<Orderer> allRecipe, Double amountPerMinute) {
        calculatedOfRecipe.removeAllElements();
        this.allRecipe = null;
        this.allRecipe = allRecipe;
        this.amountPerMinute = amountPerMinute;

        calculateNumbers();

        return calculatedOfRecipe;
    }

    private void calculateNumbers() {
        calculatedOfRecipe.add(new Orderer(
                allRecipe.get(0).getRecipe(),
                amountPerMinute,
                getRate(allRecipe.get(0).getRecipe(), amountPerMinute),
                allRecipe.get(0).getInset(),
                allRecipe.get(0).getPosition(),
                BuildingService.getBuildingByUUID(allRecipe.get(0).getRecipe().getBuildingUUID()).getBuildingName(),
                amountPerMinute / allRecipe.get(0).getRecipe().getOutputMaterial1Amount()));
        for (int i = 1; i < allRecipe.size(); i++) {
            Orderer bos = calculatedOfRecipe.get(getBos(allRecipe.get(i)));
            Recipe tempRecipe = allRecipe.get(i).getRecipe();
            double requiredAmount = bos.getRate() * getRequiredAmount(bos.getRecipe(), allRecipe.get(i).getInNum());
            double rate = getRate(tempRecipe, requiredAmount);
            int inset = allRecipe.get(i).getInset();
            int position = allRecipe.get(i).getPosition();
            String buildingName = BuildingService.getBuildingByUUID(tempRecipe.getBuildingUUID()).getBuildingName();
            if (buildingName == null) buildingName = "Miner";
            double buildingAmount = requiredAmount / tempRecipe.getOutputMaterial1Amount();
            calculatedOfRecipe.add(new Orderer(tempRecipe, requiredAmount, rate, inset, position, buildingName, buildingAmount));
        }
    }

    private double getRate(Recipe recipe, Double amountPerMinute) {
        return amountPerMinute / recipe.getOutputMaterial1Amount();
    }

    private double getRequiredAmount(Recipe recipe, Integer inNum) {
        return switch (inNum) {
            case 1 -> recipe.getInputMaterial1Amount();
            case 2 -> recipe.getInputMaterial2Amount();
            case 3 -> recipe.getInputMaterial3Amount();
            case 4 -> recipe.getInputMaterial4Amount();
            default -> 0.0;
        };
    }

    private Integer getBos(Orderer orderer) {
        try {
            int subtract = 1;
            for (int i = orderer.getPosition(); i > 0; i--) {
                if (orderer.getPosition() - subtract <= allRecipe.size()){
                    if (allRecipe.get(orderer.getPosition() - subtract).getInset() == (orderer.getInset() - 1)) {
                        return allRecipe.get(orderer.getPosition() - subtract).getPosition();
                    }
                    subtract++;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        return 0;
    }
}