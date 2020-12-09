package SatisfactoryCalculator.View;

import SatisfactoryCalculator.Model.Recipe;
import SatisfactoryCalculator.Service.MaterialService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-Dezember-08
 */

public class EditView {

    private static Recipe editRecipe;
    private static boolean changed;
    private static Stage window;
    private static TextField nameInField, craftTimeIn;
    private static TextField outMatName1, outMatName2, inMatName1, inMatName2, inMatName3, inMatName4;
    private static TextField outMatAmnt1, outMatAmnt2, inMatAmnt1, inMatAmnt2, inMatAmnt3, inMatAmnt4;
    private static Label nameInLbl, craftTimeLbl, outputLabel, inputLabel, altRecipeLabel;
    private static Label outMatName1Lbl, outMatName2Lbl, inMatName1Lbl, inMatName2Lbl, inMatName3Lbl, inMatName4Lbl;
    private static Label outMatAmnt1Lbl, outMatAmnt2Lbl, inMatAmnt1Lbl, inMatAmnt2Lbl, inMatAmnt3Lbl, inMatAmnt4Lbl;
    private static RadioButton altRecipeButton;


    public static void display(Recipe editRecipe, MainFrame mainFrame){
        EditView.editRecipe = editRecipe;

        init();
        setData();

        window.setTitle("Edit Window of " + editRecipe.getRecipeName());
        window.setScene(new Scene(windowContent(), 550, 420));
        window.setResizable(false);
        window.initOwner(mainFrame.getStage());
        window.initModality(Modality.WINDOW_MODAL);
        window.setAlwaysOnTop(true);
        window.centerOnScreen();
        window.showAndWait();

        //TODO close only if nothing changed
    }

    private static void setData(){
        nameInField.setText(editRecipe.getRecipeName());
        craftTimeIn.setText(String.valueOf(editRecipe.getCraftTime()));
        if (editRecipe.isAltRecipe()) altRecipeButton.setSelected(true);
        outMatName1.setText(MaterialService.getMaterialByUUID(editRecipe.getOutputMaterial1UUID()).getMaterialName());
        outMatAmnt1.setText(String.valueOf(editRecipe.getOutputMaterial1Amount()));
        if (!editRecipe.getOutputMaterial2UUID().equals("None")){
            outMatName2.setText(MaterialService.getMaterialByUUID(editRecipe.getOutputMaterial2UUID()).getMaterialName());
            outMatAmnt2.setText(String.valueOf(editRecipe.getOutputMaterial2Amount()));
        }
        if (!editRecipe.getInputMaterial1UUID().equals("None")){
            inMatName1.setText(MaterialService.getMaterialByUUID(editRecipe.getInputMaterial1UUID()).getMaterialName());
            inMatAmnt1.setText(String.valueOf(editRecipe.getInputMaterial1Amount()));
        }
        if (!editRecipe.getInputMaterial2UUID().equals("None")){
            inMatName2.setText(MaterialService.getMaterialByUUID(editRecipe.getInputMaterial2UUID()).getMaterialName());
            inMatAmnt2.setText(String.valueOf(editRecipe.getInputMaterial2Amount()));
        }
        if (!editRecipe.getInputMaterial3UUID().equals("None")){
            inMatName3.setText(MaterialService.getMaterialByUUID(editRecipe.getInputMaterial3UUID()).getMaterialName());
            inMatAmnt3.setText(String.valueOf(editRecipe.getInputMaterial3Amount()));
        }
        if (!editRecipe.getInputMaterial4UUID().equals("None")){
            inMatName4.setText(MaterialService.getMaterialByUUID(editRecipe.getInputMaterial4UUID()).getMaterialName());
            inMatAmnt4.setText(String.valueOf(editRecipe.getInputMaterial4Amount()));
        }
    }

    private static void init() {
        window = new Stage();
        nameInField = new TextField();
        craftTimeIn = new TextField();
        outMatName1 = new TextField();
        outMatName2 = new TextField();
        inMatName1 = new TextField();
        inMatName2 = new TextField();
        inMatName3 = new TextField();
        inMatName4 = new TextField();
        outMatAmnt1 = new TextField();
        outMatAmnt2 = new TextField();
        inMatAmnt1 = new TextField();
        inMatAmnt2 = new TextField();
        inMatAmnt3 = new TextField();
        inMatAmnt4 = new TextField();
        nameInLbl = new Label();
        craftTimeLbl = new Label();
        outputLabel = new Label();
        inputLabel = new Label();
        altRecipeLabel = new Label();
        outMatName1Lbl = new Label();
        outMatName2Lbl = new Label();
        inMatName1Lbl = new Label();
        inMatName2Lbl = new Label();
        inMatName3Lbl = new Label();
        inMatName4Lbl = new Label();
        outMatAmnt1Lbl = new Label();
        outMatAmnt2Lbl = new Label();
        inMatAmnt1Lbl = new Label();
        inMatAmnt2Lbl = new Label();
        inMatAmnt3Lbl = new Label();
        inMatAmnt4Lbl = new Label();
        altRecipeButton = new RadioButton();
    }

    private static BorderPane windowContent(){
        BorderPane borderPane = new BorderPane();

        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(160,160,160), CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setTop(topPart());
        borderPane.setCenter(centerPart());

        return borderPane;
    }

    private static VBox topPart(){
        VBox vBox = new VBox();

        nameInLbl.setText("Recipename: ");
        craftTimeLbl.setText("Crafttime: ");
        altRecipeLabel.setText("Alt Recipe: ");

        GridPane pane = new GridPane();
        pane.add(nameInLbl, 0,0);
        pane.add(nameInField, 1,0);
        pane.add(craftTimeLbl, 0,1);
        pane.add(craftTimeIn, 1,1);
        pane.add(altRecipeLabel, 0, 2);
        pane.add(altRecipeButton, 1, 2);

        pane.setHgap(30);
        pane.setVgap(10);
        pane.setPadding(new Insets(10,10,10,10));

        vBox.getChildren().add(pane);

        return vBox;
    }

    private static VBox centerPart(){
        VBox vBox = new VBox();


        GridPane leftGrid = new GridPane();
        outputLabel.setText("Output");
        outputLabel.setStyle("-fx-font-size: 16");
        outMatName1Lbl.setText("Output Material 1: ");
        outMatName2Lbl.setText("Output Material 2: ");
        outMatAmnt1Lbl.setText("Output Amount 1: ");
        outMatAmnt2Lbl.setText("Output Amount 2: ");

        leftGrid.add(outputLabel, 0,0);
        leftGrid.add(outMatName1Lbl, 0,1);
        leftGrid.add(outMatName1, 1,1);
        leftGrid.add(outMatAmnt1Lbl, 2,1);
        leftGrid.add(outMatAmnt1, 3,1);
        leftGrid.add(outMatName2Lbl, 0,2);
        leftGrid.add(outMatName2, 1,2);
        leftGrid.add(outMatAmnt2Lbl, 2,2);
        leftGrid.add(outMatAmnt2, 3,2);

        leftGrid.setHgap(10);
        leftGrid.setVgap(10);
        leftGrid.setPadding(new Insets(10,10,10,10));

        GridPane rightGrid = new GridPane();
        inputLabel.setText("Input");
        inputLabel.setStyle("-fx-font-size: 16");
        inMatName1Lbl.setText("Input Material 1: ");
        inMatName2Lbl.setText("Input Material 2: ");
        inMatName3Lbl.setText("Input Material 3: ");
        inMatName4Lbl.setText("Input Material 4: ");
        inMatAmnt1Lbl.setText("Input Amount 1: ");
        inMatAmnt2Lbl.setText("Input Amount 2: ");
        inMatAmnt3Lbl.setText("Input Amount 3: ");
        inMatAmnt4Lbl.setText("Input Amount 4: ");

        rightGrid.add(inputLabel, 0,0);
        rightGrid.add(inMatName1Lbl, 0,1);
        rightGrid.add(inMatName1, 1,1);
        rightGrid.add(inMatAmnt1Lbl, 2,1);
        rightGrid.add(inMatAmnt1, 3,1);
        rightGrid.add(inMatName2Lbl, 0,2);
        rightGrid.add(inMatName2, 1,2);
        rightGrid.add(inMatAmnt2Lbl, 2,2);
        rightGrid.add(inMatAmnt2, 3,2);
        rightGrid.add(inMatName3Lbl, 0,3);
        rightGrid.add(inMatName3, 1,3);
        rightGrid.add(inMatAmnt3Lbl, 2,3);
        rightGrid.add(inMatAmnt3, 3,3);
        rightGrid.add(inMatName4Lbl, 0,4);
        rightGrid.add(inMatName4, 1,4);
        rightGrid.add(inMatAmnt4Lbl, 2,4);
        rightGrid.add(inMatAmnt4, 3,4);

        rightGrid.setHgap(10);
        rightGrid.setVgap(10);
        rightGrid.setPadding(new Insets(10,10,10,10));

        GridPane mainGrid = new GridPane();
        mainGrid.add(leftGrid, 0,0);
        mainGrid.add(rightGrid, 0,1);

        vBox.getChildren().add(mainGrid);

        return vBox;
    }
}
