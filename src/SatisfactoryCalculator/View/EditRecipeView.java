package SatisfactoryCalculator.View;

import SatisfactoryCalculator.Model.Recipe;
import SatisfactoryCalculator.Service.BuildingService;
import SatisfactoryCalculator.Service.ItemService;
import SatisfactoryCalculator.Service.RecipeService;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-Dezember-08
 */

public class EditRecipeView {

    private static Recipe editRecipe;
    private static String param;
    private static Stage window;
    private static TextField nameInField, craftTimeIn, buildingField;
    private static TextField outMatName1, outMatName2, inMatName1, inMatName2, inMatName3, inMatName4;
    private static TextField outMatAmnt1, outMatAmnt2, inMatAmnt1, inMatAmnt2, inMatAmnt3, inMatAmnt4;
    private static Label nameInLbl, craftTimeLbl, outputLabel, inputLabel, altRecipeLabel, buildingLabel;
    private static Label outMatName1Lbl, outMatName2Lbl, inMatName1Lbl, inMatName2Lbl, inMatName3Lbl, inMatName4Lbl;
    private static Label outMatAmnt1Lbl, outMatAmnt2Lbl, inMatAmnt1Lbl, inMatAmnt2Lbl, inMatAmnt3Lbl, inMatAmnt4Lbl;
    private static RadioButton altRecipeButton;
    private static Button backButton, saveButton, deleteButton;
    private static Separator separator;

    public static void display(Recipe editRecipe, MainFrame mainFrame, String param){
        EditRecipeView.editRecipe = editRecipe;
        EditRecipeView.param = param;

        init();
        initTextField();
        if (param.equals("e")) setData();
        else editRecipe = new Recipe();

        try {
            window.getIcons().add(new Image(new FileInputStream("C:\\Users\\Felix\\OneDrive\\Felix\\Privat\\Programmieren\\SatisfactoryCalculator\\src\\SatisfactoryCalculator\\img\\satisfactoryIcon.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (editRecipe.getRecipeName() != null) window.setTitle("Edit Window of " + editRecipe.getRecipeName());
        else window.setTitle("Edit Window of new Recipe");
        window.setScene(new Scene(windowContent(), 550, 460));
        window.setResizable(false);
        window.initOwner(mainFrame.getStage());
        window.initModality(Modality.WINDOW_MODAL);
        window.setAlwaysOnTop(true);
        window.centerOnScreen();
        window.showAndWait();
        window.setOnCloseRequest(event -> {
            event.consume();
            if (!hasChanged()) window.close();
            else {
                boolean answer = ConfirmView.display("Exit", "Do you want to exit?");
                if (answer) window.close();
            }
        });
    }

    private static void init() {
        window = new Stage();
        nameInField = new TextField();
        craftTimeIn = new TextField();
        buildingField = new TextField();
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
        buildingLabel = new Label();
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
        backButton = new Button();
        saveButton = new Button();
        deleteButton = new Button();
        separator = new Separator();

        if (param.equals("a")) deleteButton.setVisible(false);

        setAction();
    }

    private static void setAction(){
        backButton.setOnAction(event -> {
            window.close();
        });
        saveButton.setOnAction(event -> {
            boolean answer = ConfirmView.display("Save", "Save Changes?");
            if (!answer) window.close();
            else {
                if (param.equals("a")) {
                    RecipeService.saveNew(getInputs());
                } else if (hasChanged()) {
                    RecipeService.saveChanges(getInputs(), editRecipe.getRecipeUUID());
                }
                window.close();
            }
            SectorView.updateRecipeList();
        });
        deleteButton.setOnAction(event -> {
            boolean answer = ConfirmView.display("Delete", "Delete this Recipe?");
            if (!answer) window.close();
            else {
                RecipeService.delete(editRecipe);
                window.close();
            }
            SectorView.updateRecipeList();
        });
    }

    private static void initTextField() {
        outMatAmnt1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                outMatAmnt1.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        outMatAmnt2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                outMatAmnt2.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        inMatAmnt1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                inMatAmnt1.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        inMatAmnt2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                inMatAmnt2.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        inMatAmnt3.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                inMatAmnt3.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        inMatAmnt4.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                inMatAmnt4.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private static void setData(){
        nameInField.setText(editRecipe.getRecipeName());
        craftTimeIn.setText(String.valueOf(editRecipe.getCraftTime()));
        altRecipeButton.setSelected(true);
        if (editRecipe.getBuildingUUID() != null){
            buildingField.setText(BuildingService.getBuildingByUUID(editRecipe.getBuildingUUID()).getBuildingName());
        }
        if (editRecipe.getOutputMaterial1UUID() != null && !editRecipe.getOutputMaterial1UUID().equals("None")){
            outMatName1.setText(ItemService.getItemByUUID(editRecipe.getOutputMaterial1UUID()).getItemName());
            outMatAmnt1.setText(String.valueOf(editRecipe.getOutputMaterial1Amount()));
        }
        if (editRecipe.getOutputMaterial2UUID() != null && !editRecipe.getOutputMaterial2UUID().equals("None")){
            outMatName2.setText(ItemService.getItemByUUID(editRecipe.getOutputMaterial2UUID()).getItemName());
            outMatAmnt2.setText(String.valueOf(editRecipe.getOutputMaterial2Amount()));
        }
        if (editRecipe.getInputMaterial1UUID() != null && !editRecipe.getInputMaterial1UUID().equals("None")){
            inMatName1.setText(ItemService.getItemByUUID(editRecipe.getInputMaterial1UUID()).getItemName());
            inMatAmnt1.setText(String.valueOf(editRecipe.getInputMaterial1Amount()));
        }
        if (editRecipe.getInputMaterial2UUID() != null && !editRecipe.getInputMaterial2UUID().equals("None")){
            inMatName2.setText(ItemService.getItemByUUID(editRecipe.getInputMaterial2UUID()).getItemName());
            inMatAmnt2.setText(String.valueOf(editRecipe.getInputMaterial2Amount()));
        }
        if (editRecipe.getInputMaterial3UUID() != null && !editRecipe.getInputMaterial3UUID().equals("None")){
            inMatName3.setText(ItemService.getItemByUUID(editRecipe.getInputMaterial3UUID()).getItemName());
            inMatAmnt3.setText(String.valueOf(editRecipe.getInputMaterial3Amount()));
        }
        if (editRecipe.getInputMaterial4UUID() != null && !editRecipe.getInputMaterial4UUID().equals("None")){
            inMatName4.setText(ItemService.getItemByUUID(editRecipe.getInputMaterial4UUID()).getItemName());
            inMatAmnt4.setText(String.valueOf(editRecipe.getInputMaterial4Amount()));
        }
    }

    private static BorderPane windowContent(){
        BorderPane borderPane = new BorderPane();

        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(160,160,160), CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setTop(topPart());
        borderPane.setCenter(centerPart());
        borderPane.setBottom(botPart());

        return borderPane;
    }

    private static VBox topPart(){
        VBox vBox = new VBox();

        nameInLbl.setText("Recipename: ");
        craftTimeLbl.setText("Crafttime: ");
        altRecipeLabel.setText("Alt Recipe: ");
        buildingLabel.setText("Building: ");

        GridPane pane = new GridPane();
        pane.add(nameInLbl, 0,0);
        pane.add(nameInField, 1,0);
        pane.add(craftTimeLbl, 0,1);
        pane.add(craftTimeIn, 1,1);
        pane.add(altRecipeLabel, 0, 2);
        pane.add(altRecipeButton, 1, 2);
        pane.add(buildingLabel, 2,2);
        pane.add(buildingField, 3,2);

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

    private static VBox botPart(){
        VBox vBox = new VBox();
        ToolBar toolBar = new ToolBar();

        backButton.setText("Back");
        saveButton.setText("Save");
        deleteButton.setText("Delete");
        separator.setPrefWidth(393);

        ObservableList<Node> list = toolBar.getItems();
        list.addAll(backButton, separator, deleteButton, saveButton);

        vBox.setSpacing(100);
        vBox.getChildren().addAll(toolBar);

        return vBox;
    }

    private static boolean hasChanged(){
        if (!nameInField.getText().equals(editRecipe.getRecipeName())) return true;
        if (!craftTimeIn.getText().equals(editRecipe.getCraftTime())) return true;
        if (!altRecipeButton.isSelected() == editRecipe.isAltRecipe()) return true;
        if (!buildingField.getText().equals(editRecipe.getBuildingUUID())) return true;
        if (!outMatName1.getText().equals(editRecipe.getOutputMaterial1UUID())) return true;
        if (!outMatAmnt1.getText().equals(editRecipe.getOutputMaterial1Amount())) return true;
        if (!outMatName2.getText().equals(editRecipe.getOutputMaterial2UUID())) return true;
        if (!outMatAmnt2.getText().equals(editRecipe.getOutputMaterial2Amount())) return true;
        if (!inMatName1.getText().equals(editRecipe.getInputMaterial1UUID())) return true;
        if (!inMatAmnt1.getText().equals(editRecipe.getInputMaterial1Amount())) return true;
        if (!inMatName2.getText().equals(editRecipe.getInputMaterial2UUID())) return true;
        if (!inMatAmnt2.getText().equals(editRecipe.getInputMaterial2Amount())) return true;
        if (!inMatName3.getText().equals(editRecipe.getInputMaterial3UUID())) return true;
        if (!inMatAmnt3.getText().equals(editRecipe.getInputMaterial3Amount())) return true;
        if (!inMatName4.getText().equals(editRecipe.getInputMaterial4UUID())) return true;
        if (!inMatAmnt4.getText().equals(editRecipe.getInputMaterial4Amount())) return true;
        return false;
    }

    private static Recipe getInputs(){
        Recipe recipe = new Recipe();

        if (param.equals("a")) recipe.setRecipeUUID(UUID.randomUUID().toString());

        recipe.setRecipeName(nameInField.getText()); //TODO have to und nicht have to
        recipe.setCraftTime(craftTimeIn.getText());
        recipe.setAltRecipe(getSelectedAltRecipe());
        recipe.setBuildingUUID(buildingField.getText());
        recipe.setOutputMaterial1UUID(outMatName1.getText());
        recipe.setOutputMaterial1Amount(outMatAmnt1.getText());
        recipe.setOutputMaterial2UUID(outMatName2.getText());
        recipe.setOutputMaterial2Amount(outMatAmnt2.getText());
        recipe.setInputMaterial1UUID(inMatName1.getText());
        recipe.setInputMaterial1Amount(inMatAmnt1.getText());
        recipe.setInputMaterial1UUID(inMatName2.getText());
        recipe.setInputMaterial1Amount(inMatAmnt2.getText());
        recipe.setInputMaterial1UUID(inMatName3.getText());
        recipe.setInputMaterial1Amount(inMatAmnt3.getText());
        recipe.setInputMaterial1UUID(inMatName4.getText());
        recipe.setInputMaterial1Amount(inMatAmnt4.getText());

        return recipe;
    }

    private static String getSelectedAltRecipe(){
        if (altRecipeButton.isSelected()) return "true";
        else return "false";
    }
}
