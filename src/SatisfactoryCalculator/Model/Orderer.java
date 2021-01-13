package SatisfactoryCalculator.Model;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-Dezember-04
 */

public class Orderer {
    private Recipe recipe;
    private Double requiredAmount, rate;
    private Integer inNum, inset, position;

    public Orderer(Recipe recipe, Integer inNum, Integer inset, Integer position){
        this.recipe = recipe;
        this.inNum = inNum;
        this.inset = inset;
        this.position = position;
    }

    public Orderer(Recipe recipe, Double requiredAmount, Double rate, Integer inset, Integer position){
        this.recipe = recipe;
        this.requiredAmount = requiredAmount;
        this.rate = rate;
        this.inset = inset;
        this.position = position;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Integer getInNum() {
        return inNum;
    }

    public Double getRequiredAmount() {
        return requiredAmount;
    }

    public Double getRate() {
        return rate;
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getInset() {
        return inset;
    }
}
