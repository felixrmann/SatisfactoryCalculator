package SatisfactoryCalculator.Controller;

import SatisfactoryCalculator.Model.Calculator;
import SatisfactoryCalculator.Model.Orderer;
import SatisfactoryCalculator.Model.Organizer;
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
            if (!calculateView.getAmountFieldText().equals("") && !calculateView.getItemFieldText().equals("")){
                Calculator calculator = new Calculator();
                Vector<Orderer> allRecipe = calculator.getRecipeAmount(Organizer.getAllRecipeOfMaterial(calculateView.getItemFieldText()), Double.valueOf(calculateView.getAmountFieldText()));
                calculateView.centerPart(allRecipe);
            }
        }
        else if (buttons.get(1).equals(event.getSource())) mainFrame.setNewScene(new MenuView(mainFrame), 350, 429);
        else if (buttons.get(2).equals(event.getSource())) mainFrame.closeProgram();
    }
}
