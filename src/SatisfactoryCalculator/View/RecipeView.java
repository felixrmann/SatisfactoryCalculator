package SatisfactoryCalculator.View;

import SatisfactoryCalculator.Controller.RecipeController;
import SatisfactoryCalculator.Model.Recipe;
import SatisfactoryCalculator.Service.RecipeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-14
 */

public class RecipeView extends BorderPane {

    private MainFrame mainFrame;
    private Recipe selectedRecipe;
    private ListView<String> listView;
    private Button editButton, backButton, addButton, exitButton;
    private Separator separator;
    private Vector<Button> buttons;
    private RecipeController recipeController;

    public RecipeView(MainFrame mainFrame){
        this.mainFrame = mainFrame;

        init();

        setTop(topPart());
        setCenter(centerPart());
        setBottom(botPart());

        setBackground(new Background(new BackgroundFill(Color.rgb(160,160,160), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void init() {
        editButton = new Button();
        backButton = new Button();
        addButton = new Button();
        exitButton = new Button();
        separator = new Separator();
        buttons = new Vector<>();
        recipeController = new RecipeController(mainFrame, buttons, this);

        buttons.add(editButton);
        buttons.add(addButton);
        buttons.add(backButton);
        buttons.add(exitButton);

        editButton.setOnAction(recipeController);
        backButton.setOnAction(recipeController);
        addButton.setOnAction(recipeController);
        exitButton.setOnAction(recipeController);
    }

    private VBox topPart(){
        VBox vBox = new VBox();
        ToolBar toolBar = new ToolBar();

        return vBox;
    }

    private VBox centerPart(){
        Vector<Recipe> recipes = RecipeService.getAllRecipe();
        Vector<String> recipeNames = new Vector<>();
        for (Recipe recipe : recipes) {
            recipeNames.add(recipe.getRecipeName());
        }
        ObservableList<String> recipeNameList = FXCollections.observableList(recipeNames);
        listView = new ListView<>(recipeNameList);
        listView.setPrefSize(600, 700);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().add(listView);

        listView.setOnMouseClicked(mouseEvent -> {
            selectedRecipe = recipes.get(listView.getSelectionModel().getSelectedIndex());
            editButton.setVisible(true);
        });

        return vBox;
    }

    private VBox botPart(){
        VBox vBox = new VBox();
        ToolBar toolBar = new ToolBar();

        editButton.setVisible(false);

        editButton.setText("Edit");
        backButton.setText("Back");
        addButton.setText("Add");
        exitButton.setText("Exit");

        separator.setPrefWidth(420);

        ObservableList<Node> list2 = toolBar.getItems();
        list2.addAll(backButton, exitButton, separator, editButton, addButton);

        vBox.setSpacing(30);
        vBox.getChildren().addAll(toolBar);

        return vBox;
    }

    public Recipe getSelectedRecipe() {
        return selectedRecipe;
    }
}
