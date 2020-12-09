package SatisfactoryCalculator.Model;

import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-18
 */

public class Calculator {

    private static Vector<Orderer> amountOfRecipe;
    private static Vector<Orderer> allRecipe;
    private static Vector<Orderer> calculatedOfRecipe;

    public static Vector<Orderer> getRecipeAmount(Vector<Orderer> allRecipe, Integer materialAmountPerMinute){
        Calculator.allRecipe = allRecipe;
        amountOfRecipe = new Vector<>();
        calculatedOfRecipe = new Vector<>();

        getAmountOfEachRecipe();
        getClaculatedAmountOfRecipe();


        for (Orderer orderer: amountOfRecipe) {
            System.out.println("Name: " + orderer.getRecipe().getRecipeName());
            System.out.println("Amount: " + orderer.getAmount());
        }

        return null;
    }


    private static void getClaculatedAmountOfRecipe() {
        calculatedOfRecipe.add(new Orderer(amountOfRecipe.get(0).getAmount(), amountOfRecipe.get(0).getInset(), amountOfRecipe.get(0).getPosition()));
        for (int i = 1; i < amountOfRecipe.size(); i++) {
            if ((i+1) <= amountOfRecipe.size()){
                //calculatedOfRecipe.add(new Orderer())
            }
        }
    }

    private static boolean isBos(Orderer upperOrderer, Orderer underOrderer){
        return getBos(underOrderer).equals(upperOrderer.getPosition());
    }

    private static Integer getBos(Orderer orderer){
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

    private static void getAmountOfEachRecipe(){
        for (Orderer orderer : allRecipe) {
            amountOfRecipe.add(new Orderer(
                    orderer.getRecipe(),
                    orderer.getRecipe().getInputMaterial1Amount(),
                    orderer.getInset(),
                    orderer.getPosition()));
        }
    }
}