package SatisfactoryCalculator.Model;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-Dezember-04
 */

public class Orderer {
    private Recipe recipe;
    private Double amount;
    private Integer inset, position;

    public Orderer(Recipe recipe, Integer inset, Integer position){
        this.recipe = recipe;
        this.inset = inset;
        this.position = position;
    }

    public Orderer(Double amount, Integer inset, Integer position){
        this.amount = amount;
        this.inset = inset;
        this.position = position;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getInset() {
        return inset;
    }
}
