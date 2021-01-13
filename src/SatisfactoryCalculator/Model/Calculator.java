package SatisfactoryCalculator.Model;

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

    public Calculator(){
        calculatedOfRecipe = new Vector<>();
    }

    public Vector<Orderer> getRecipeAmount(Vector<Orderer> allRecipe, Double amountPerMinute){
        this.allRecipe = allRecipe;
        this.amountPerMinute = amountPerMinute;

        calculateNumbers();

        /*
        for (Orderer orderer : calculatedOfRecipe) {
            System.out.println("Recipe: " + orderer.getRecipe().getRecipeName());
            System.out.println("Pos:    " + orderer.getPosition());
            System.out.println("Inset:  " + orderer.getInset());
            System.out.println("Amount: " + orderer.getRequiredAmount());
        }

         */

        return calculatedOfRecipe;
    }

    private void calculateNumbers(){
        calculatedOfRecipe.add(new Orderer(allRecipe.get(0).getRecipe(), amountPerMinute, getRate(allRecipe.get(0).getRecipe(), amountPerMinute), allRecipe.get(0).getInset(), allRecipe.get(0).getPosition()));
        for (int i = 1; i < allRecipe.size(); i++) {
            Orderer bos = calculatedOfRecipe.get(getBos(allRecipe.get(i)));
            Recipe tempRecipe = allRecipe.get(i).getRecipe();
            double requiredAmount = bos.getRate() * getRequiredAmount(bos.getRecipe(), allRecipe.get(i).getInNum());
            double rate = getRate(tempRecipe, requiredAmount);
            int inset = allRecipe.get(i).getInset();
            int position = allRecipe.get(i).getPosition();
            calculatedOfRecipe.add(new Orderer(tempRecipe, requiredAmount, rate, inset, position));
        }
    }

    private double getRate(Recipe recipe, Double amountPerMinute){
        return amountPerMinute / recipe.getOutputMaterial1Amount();
    }

    private double getRequiredAmount(Recipe recipe, Integer inNum){
        return switch (inNum) {
            case 1 -> recipe.getInputMaterial1Amount();
            case 2 -> recipe.getInputMaterial2Amount();
            case 3 -> recipe.getInputMaterial3Amount();
            case 4 -> recipe.getInputMaterial4Amount();
            default -> 0.0;
        };
    }

    private Integer getBos(Orderer orderer){
        try {
            int subtract = 1;
            for (int i = orderer.getPosition(); i > 0; i--) {
                if (allRecipe.get(orderer.getPosition() - subtract).getInset() == (orderer.getInset() - 1)){
                    return allRecipe.get(orderer.getPosition() - subtract).getPosition();
                }
                subtract++;
            }
        } catch (ArrayIndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
        return 0;
    }
}