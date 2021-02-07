package SatisfactoryCalculator.Controller;

import SatisfactoryCalculator.Model.*;
import SatisfactoryCalculator.Service.ItemService;
import SatisfactoryCalculator.Service.RecipeService;
import SatisfactoryCalculator.View.CalculateView;
import SatisfactoryCalculator.View.MainFrame;
import SatisfactoryCalculator.View.MenuView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-13
 */

public class CalculateController implements EventHandler<ActionEvent> {

    private MainFrame mainFrame;
    private Vector<Button> buttons;
    private CalculateView calculateView;

    public CalculateController(MainFrame mainFrame, Vector<Button> buttons, CalculateView calculateView){
        this.mainFrame = mainFrame;
        this.buttons = buttons;
        this.calculateView = calculateView;
    }

    @Override
    public void handle(ActionEvent event) {
        if (buttons.get(0).equals(event.getSource())) {
            if (!calculateView.getAmountFieldText().equals("") && !calculateView.getItemFieldText().equals("") && !calculateView.getAltEnabled()){
                getNormalRecipe();
            } else if (calculateView.getAltEnabled()){
                getAllRecipe();
            }
        }
        else if (buttons.get(1).equals(event.getSource())) mainFrame.setNewScene(new MenuView(mainFrame), 350, 429);
        else if (buttons.get(2).equals(event.getSource())) mainFrame.closeProgram();
    }

    private void getNormalRecipe(){
        Calculator calculator = new Calculator();
        Item item = ItemService.getItemByName(calculateView.getItemFieldText());
        Vector<Orderer> allRecipe = calculator.getRecipeAmount(Organizer.getAllRecipeOfItem(item), Double.valueOf(calculateView.getAmountFieldText()));
        calculateView.setTabPane(allRecipe);
    }

    private void getAllRecipe(){
        Calculator calculator = new Calculator();

        Item item = ItemService.getItemByName(calculateView.getItemFieldText());
        Vector<Recipe> allRecipes = RecipeService.getAllRecipeOfMaterialByUUID(item.getItemUUID());

        for (int i = 0; i < allRecipes.size(); i++) {
            Vector<Orderer> tempVector = calculator.getRecipeAmount(Organizer.getAllRecipeOfRecipe(allRecipes.get(i)), Double.valueOf(calculateView.getAmountFieldText()));
            if (i == 0){
                calculateView.setTabPane(tempVector);
            } else {
                calculateView.addTabToTabPane(tempVector);
            }
        }
    }
}
