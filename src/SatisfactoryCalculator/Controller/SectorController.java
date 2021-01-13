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

public class SectorController implements EventHandler<ActionEvent> {

    private MainFrame mainFrame;
    private Vector<Button> buttons;
    private SectorView sectorView;
    private String sector;

    public SectorController(MainFrame mainFrame, Vector<Button> buttons, SectorView sectorView, String sector){
        this.mainFrame = mainFrame;
        this.buttons = buttons;
        this.sectorView = sectorView;
        this.sector = sector;
    }

    @Override
    public void handle(ActionEvent event) {
        if (buttons.get(0).equals(event.getSource())){
            switch (sector) {
                case "item" -> EditItemView.display(sectorView.getSelectedItem(), mainFrame, "e");
                case "recipe" -> EditRecipeView.display(sectorView.getSelectedRecipe(), mainFrame, "e");
                case "building" -> EditBuildingView.display(sectorView.getSelectedBuilding(), mainFrame, "e");
            }
        } else if (buttons.get(1).equals(event.getSource())){
            switch (sector) {
                case "item" -> EditItemView.display(sectorView.getSelectedItem(), mainFrame, "a");
                case "recipe" -> EditRecipeView.display(sectorView.getSelectedRecipe(), mainFrame, "a");
                case "building" -> EditBuildingView.display(sectorView.getSelectedBuilding(), mainFrame, "a");
            }
        }
        else if (buttons.get(2).equals(event.getSource())) mainFrame.setNewScene(new MenuView(mainFrame), 350, 429);
        else if (buttons.get(3).equals(event.getSource())) mainFrame.closeProgram();
    }
}
