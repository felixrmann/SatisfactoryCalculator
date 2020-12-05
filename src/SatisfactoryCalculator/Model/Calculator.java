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

        /*
        for (Orderer orderer: amountOfRecipe) {
            System.out.println("Amount: " + orderer.getAmount());
            System.out.println("Position: " + orderer.getPosition());
        }

         */

        return null;
    }


    private static void getClaculatedAmountOfRecipe() {
        for (int i = 0; i < allRecipe.size(); i++) {
            double itemsPerMinute = allRecipe.get(i).getRecipe().getOutputMaterial1Amount();
            if (isBos(allRecipe.get(i), allRecipe.get(i + 1)) && (i + 1) <= allRecipe.size()){
                double nextItemsPerMinute = allRecipe.get(i + 1).getRecipe().getOutputMaterial1Amount();
                //TODO calculate it
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
                    orderer.getRecipe().getOutputMaterial1Amount(),
                    orderer.getInset(),
                    orderer.getPosition()));
        }
    }
}