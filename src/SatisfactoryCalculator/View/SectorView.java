package SatisfactoryCalculator.View;

import SatisfactoryCalculator.Controller.SectorController;
import SatisfactoryCalculator.Model.Building;
import SatisfactoryCalculator.Model.Item;
import SatisfactoryCalculator.Model.Recipe;
import SatisfactoryCalculator.Service.BuildingService;
import SatisfactoryCalculator.Service.ItemService;
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

public class SectorView extends BorderPane {

    private MainFrame mainFrame;
    private String sector;
    private static Item selectedItem;
    private static Recipe selectedRecipe;
    private static Building selectedBuilding;
    private static ListView<String> listView;
    private static Button editButton;
    private Button backButton;
    private Button addButton;
    private Button exitButton;
    private Separator separator;
    private Vector<Button> buttons;
    private SectorController sectorController;

    public SectorView(MainFrame mainFrame, String sector){
        this.mainFrame = mainFrame;
        this.sector = sector;

        init();

        setCenter(centerPart());
        setBottom(botPart());

        setBackground(new Background(new BackgroundFill(Color.rgb(160,160,160), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void init() {
        listView = new ListView<>();
        editButton = new Button();
        backButton = new Button();
        addButton = new Button();
        exitButton = new Button();
        separator = new Separator();
        buttons = new Vector<>();
        sectorController = new SectorController(mainFrame, buttons, this, sector);

        buttons.add(editButton);
        buttons.add(addButton);
        buttons.add(backButton);
        buttons.add(exitButton);

        editButton.setOnAction(sectorController);
        backButton.setOnAction(sectorController);
        addButton.setOnAction(sectorController);
        exitButton.setOnAction(sectorController);
    }

    private VBox centerPart(){
        return switch (sector) {
            case "item" -> loadItem();
            case "recipe" -> loadRecipe();
            case "building" -> loadBuilding();
            default -> null;
        };
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

    private VBox loadItem(){
        Vector<Item> allItems = ItemService.getAllItem();
        Vector<String> allItemNames = new Vector<>();
        for (Item item: allItems) {
            allItemNames.add(item.getItemName());
        }
        ObservableList<String> itemNameList = FXCollections.observableList(allItemNames);
        listView.setItems(itemNameList);
        listView.setPrefSize(600, 700);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().add(listView);

        listView.setOnMouseClicked(mouseEvent -> {
            selectedItem = allItems.get(listView.getSelectionModel().getSelectedIndex());
            editButton.setVisible(true);
        });
        return vBox;
    }

    private VBox loadRecipe(){
        Vector<Recipe> allRecipes = RecipeService.getAllRecipe();
        Vector<String> allRecipeNames = new Vector<>();
        for (Recipe recipe: allRecipes) {
            allRecipeNames.add(recipe.getRecipeName());
        }
        ObservableList<String> recipeNameList = FXCollections.observableList(allRecipeNames);
        listView.setItems(recipeNameList);
        listView.setPrefSize(600, 700);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().add(listView);

        listView.setOnMouseClicked(mouseEvent -> {
            selectedRecipe = allRecipes.get(listView.getSelectionModel().getSelectedIndex());
            editButton.setVisible(true);
        });
        return vBox;
    }

    private VBox loadBuilding(){
        Vector<Building> allBuilding = BuildingService.getAllBuilding();
        Vector<String> allBuildingNames = new Vector<>();
        for (Building building: allBuilding) {
            allBuildingNames.add(building.getBuildingName());
        }
        ObservableList<String> recipeNameList = FXCollections.observableList(allBuildingNames);
        listView.setItems(recipeNameList);
        listView.setPrefSize(600, 700);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().add(listView);

        listView.setOnMouseClicked(mouseEvent -> {
            selectedBuilding = allBuilding.get(listView.getSelectionModel().getSelectedIndex());
            editButton.setVisible(true);
        });
        return vBox;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public Recipe getSelectedRecipe() {
        return selectedRecipe;
    }

    public Building getSelectedBuilding(){
        return selectedBuilding;
    }

    public static void updateItemList(){
        Vector<Item> allItems = ItemService.getAllItem();
        Vector<String> allItemNames = new Vector<>();
        for (Item item: allItems) {
            allItemNames.add(item.getItemName());
        }
        ObservableList<String> itemNameList = FXCollections.observableList(allItemNames);
        listView.setItems(itemNameList);
        listView.setOnMouseClicked(mouseEvent -> {
            selectedItem = allItems.get(listView.getSelectionModel().getSelectedIndex());
            editButton.setVisible(true);
        });
    }

    public static void updateRecipeList(){
        Vector<Recipe> allRecipes = RecipeService.getAllRecipe();
        Vector<String> allRecipeNames = new Vector<>();
        for (Recipe recipe: allRecipes) {
            allRecipeNames.add(recipe.getRecipeName());
        }
        ObservableList<String> recipeNameList = FXCollections.observableList(allRecipeNames);
        listView.setItems(recipeNameList);
        listView.setOnMouseClicked(mouseEvent -> {
            selectedRecipe = allRecipes.get(listView.getSelectionModel().getSelectedIndex());
            editButton.setVisible(true);
        });
    }

    public static void updateBuildingList(){
        Vector<Building> allBuilding = BuildingService.getAllBuilding();
        Vector<String> allBuildingNames = new Vector<>();
        for (Building building: allBuilding) {
            allBuildingNames.add(building.getBuildingName());
        }
        ObservableList<String> recipeNameList = FXCollections.observableList(allBuildingNames);
        listView.setItems(recipeNameList);
        listView.setOnMouseClicked(mouseEvent -> {
            selectedBuilding = allBuilding.get(listView.getSelectionModel().getSelectedIndex());
            editButton.setVisible(true);
        });
    }
}
