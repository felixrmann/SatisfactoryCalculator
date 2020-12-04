package SatisfactoryCalculator.Model;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-17
 */

public class Recipe {

    private String recipeUUID;
    private String recipeName;
    private String outputMaterial1UUID, outputMaterial2UUID;
    private double outputMaterial1Amount, outputMaterial2Amount;
    private int craftTime;
    private String inputMaterial1UUID, inputMaterial2UUID, inputMaterial3UUID, inputMaterial4UUID;
    private double inputMaterial1Amount, inputMaterial2Amount, inputMaterial3Amount, inputMaterial4Amount;
    private String buildingUUID;
    private boolean altRecipe;

    public Recipe(){}

    public String getRecipeUUID() {
        return recipeUUID;
    }

    public void setRecipeUUID(String recipeUUID) {
        this.recipeUUID = recipeUUID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getOutputMaterial1UUID() {
        return outputMaterial1UUID;
    }

    public void setOutputMaterial1UUID(String outputMaterial1UUID) {
        this.outputMaterial1UUID = outputMaterial1UUID;
    }

    public String getOutputMaterial2UUID() {
        return outputMaterial2UUID;
    }

    public void setOutputMaterial2UUID(String outputMaterial2UUID) {
        this.outputMaterial2UUID = outputMaterial2UUID;
    }

    public double getOutputMaterial1Amount() {
        return outputMaterial1Amount;
    }

    public void setOutputMaterial1Amount(String outputMaterial1Amount) {
        if (!outputMaterial1Amount.equals("None")){
            this.outputMaterial1Amount = Double.parseDouble(outputMaterial1Amount);
        }
    }

    public double getOutputMaterial2Amount() {
        return outputMaterial2Amount;
    }

    public void setOutputMaterial2Amount(String outputMaterial2Amount) {
        if (!outputMaterial2Amount.equals("None")){
            this.outputMaterial2Amount = Double.parseDouble(outputMaterial2Amount);
        }
    }

    public int getCraftTime() {
        return craftTime;
    }

    public void setCraftTime(String craftTime) {
        this.craftTime = Integer.parseInt(craftTime);
    }

    public String getInputMaterial1UUID() {
        return inputMaterial1UUID;
    }

    public void setInputMaterial1UUID(String inputMaterial1UUID) {
        this.inputMaterial1UUID = inputMaterial1UUID;
    }

    public String getInputMaterial2UUID() {
        return inputMaterial2UUID;
    }

    public void setInputMaterial2UUID(String inputMaterial2UUID) {
        this.inputMaterial2UUID = inputMaterial2UUID;
    }

    public String getInputMaterial3UUID() {
        return inputMaterial3UUID;
    }

    public void setInputMaterial3UUID(String inputMaterial3UUID) {
        this.inputMaterial3UUID = inputMaterial3UUID;
    }

    public String getInputMaterial4UUID() {
        return inputMaterial4UUID;
    }

    public void setInputMaterial4UUID(String inputMaterial4UUID) {
        this.inputMaterial4UUID = inputMaterial4UUID;
    }

    public double getInputMaterial1Amount() {
        return inputMaterial1Amount;
    }

    public void setInputMaterial1Amount(String inputMaterial1Amount) {
        if (!inputMaterial1Amount.equals("None")){
            this.inputMaterial1Amount = Double.parseDouble(inputMaterial1Amount);
        }
    }

    public double getInputMaterial2Amount() {
        return inputMaterial2Amount;
    }

    public void setInputMaterial2Amount(String inputMaterial2Amount) {
        if (!inputMaterial2Amount.equals("None")){
            this.inputMaterial2Amount = Double.parseDouble(inputMaterial2Amount);
        }
    }

    public double getInputMaterial3Amount() {
        return inputMaterial3Amount;
    }

    public void setInputMaterial3Amount(String inputMaterial3Amount) {
        if (!inputMaterial3Amount.equals("None")){
            this.inputMaterial3Amount = Double.parseDouble(inputMaterial3Amount);
        }
    }

    public double getInputMaterial4Amount() {
        return inputMaterial4Amount;
    }

    public void setInputMaterial4Amount(String inputMaterial4Amount) {
        if (!inputMaterial4Amount.equals("None")){
            this.inputMaterial4Amount = Double.parseDouble(inputMaterial4Amount);
        }
    }

    public boolean isAltRecipe() {
        return altRecipe;
    }

    public void setAltRecipe(String altRecipe) {
        this.altRecipe = Boolean.parseBoolean(altRecipe);
    }

    public String getBuildingUUID() {
        return buildingUUID;
    }

    public void setBuildingUUID(String buildingUUID) {
        this.buildingUUID = buildingUUID;
    }

    @Override
    public String toString() {
        return "Recipe: \n" +
                "recipeUUID: " + recipeUUID + "\n" +
                "recipeName: " + recipeName + "\n" +
                "outputMaterial1UUID: " + outputMaterial1UUID + "\n" +
                "outputMaterial1Amount: " + outputMaterial1Amount + "\n" +
                "outputMaterial2UUID: " + outputMaterial2UUID + "\n" +
                "outputMaterial2Amount: " + outputMaterial2Amount + "\n" +
                "craftTime: " + craftTime + "\n" +
                "inputMaterial1UUID: " + inputMaterial1UUID + "\n" +
                "inputMaterial1Amount: " + inputMaterial1Amount + "\n" +
                "inputMaterial2UUID: " + inputMaterial2UUID + "\n" +
                "inputMaterial2Amount: " + inputMaterial2Amount + "\n" +
                "inputMaterial3UUID: " + inputMaterial3UUID + "\n" +
                "inputMaterial3Amount: " + inputMaterial3Amount + "\n" +
                "inputMaterial4UUID: " + inputMaterial4UUID + "\n" +
                "inputMaterial4Amount. " + inputMaterial4Amount + "\n" +
                "altRecipe: " + altRecipe + "\n" +
                "buildingUUID: " + buildingUUID;
    }
}
