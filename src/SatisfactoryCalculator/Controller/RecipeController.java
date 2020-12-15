package SatisfactoryCalculator.Controller;

import SatisfactoryCalculator.Model.Recipe;
import SatisfactoryCalculator.View.EditView;
import SatisfactoryCalculator.View.MainFrame;
import SatisfactoryCalculator.View.MenuView;
import SatisfactoryCalculator.View.SectorView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-14
 */

public class RecipeController implements EventHandler<ActionEvent> {

    private MainFrame mainFrame;
    private Vector<Button> buttons;
    private SectorView recipeView;

    public RecipeController(MainFrame mainFrame, Vector<Button> buttons, SectorView recipeView){
        this.mainFrame = mainFrame;
        this.buttons = buttons;
        this.recipeView = recipeView;
    }

    @Override
    public void handle(ActionEvent event) {
        if (buttons.get(0).equals(event.getSource())){
            EditView.display(recipeView.getSelectedRecipe(), mainFrame, "e");
        } else if (buttons.get(1).equals(event.getSource())){
            EditView.display(new Recipe(), mainFrame, "a");
        }
        else if (buttons.get(2).equals(event.getSource())) mainFrame.setNewScene(new MenuView(mainFrame), 350, 429);
        else if (buttons.get(3).equals(event.getSource())) mainFrame.closeProgram();
    }
}
