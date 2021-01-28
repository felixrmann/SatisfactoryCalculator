package SatisfactoryCalculator.Model;

import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2021-Januar-21
 */

public class Cumulator {

    public Vector<Orderer> allRecipe, returnVector;

    public Cumulator(){
        returnVector = new Vector<>();
    }

    public Vector<Orderer> cumulator(Vector<Orderer> allRecipe){
        this.allRecipe = allRecipe;

        for (Orderer value : allRecipe) {
            boolean newEntry = true;
            for (Orderer orderer : returnVector) {
                if (orderer.getRecipe().getRecipeName().equals(value.getRecipe().getRecipeName()) && orderer.getRecipe().getRecipeName() != null) {
                    newEntry = false;
                    orderer.addAmount(value.getRequiredAmount());
                    break;
                }
            }
            if (newEntry) returnVector.add(new Orderer(value.getRecipe(), value.getRequiredAmount()));
        }
        return  returnVector;
    }
}
