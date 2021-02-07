package SatisfactoryCalculator.Model;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-Dezember-04
 */

public class Orderer {
    private String buildingName;
    private Recipe recipe;
    private Double requiredAmount, rate, amount, buildingAmount;
    private Integer inNum, inset, position;

    public Orderer(Recipe recipe, Double amount){
        this.recipe = recipe;
        this.amount = amount;
    }

    public Orderer(Recipe recipe, Integer inNum, Integer inset, Integer position){
        this.recipe = recipe;
        this.inNum = inNum;
        this.inset = inset;
        this.position = position;
    }

    public Orderer(Recipe recipe, Double requiredAmount, Double rate, Integer inset, Integer position, String buildingName, Double buildingAmount){
        this.recipe = recipe;
        this.requiredAmount = requiredAmount;
        this.rate = rate;
        this.inset = inset;
        this.position = position;
        this.buildingName = buildingName;
        this.buildingAmount = buildingAmount;
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

    public Double getAmount() {
        return amount;
    }

    public void addAmount(Double addAmount){
        amount = amount + addAmount;
    }

    public Double getBuildingAmount() {
        return buildingAmount;
    }

    public String getBuildingName() {
        return buildingName;
    }
}
