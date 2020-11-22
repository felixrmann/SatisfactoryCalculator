package SatisfactoryCalculator.Controller;

import SatisfactoryCalculator.View.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-14
 */

public class MenuController implements EventHandler<ActionEvent> {

    private MainFrame mainFrame;
    private Vector<Button> buttons;

    public MenuController(MainFrame mainFrame, Vector<Button> buttons){
        this.mainFrame = mainFrame;
        this.buttons = buttons;
    }

    @Override
    public void handle(ActionEvent event) {
        if (buttons.get(0).equals(event.getSource())) mainFrame.setNewScene(new CalculateView(mainFrame), 800, 800);
        else if (buttons.get(1).equals(event.getSource())) mainFrame.setNewScene(new ItemView(), 600, 600);
        else if (buttons.get(2).equals(event.getSource())) mainFrame.setNewScene(new RecipeView(), 600, 600);
        else if (buttons.get(3).equals(event.getSource())) mainFrame.setNewScene(new BuildingView(), 600, 600);
        else if (buttons.get(4).equals(event.getSource())) mainFrame.closeProgram();
    }
}
