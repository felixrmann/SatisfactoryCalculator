package SatisfactoryCalculator.View;

import SatisfactoryCalculator.Controller.CalculateController;
import SatisfactoryCalculator.Model.AutoCompleteLogic;
import SatisfactoryCalculator.Model.Cumulator;
import SatisfactoryCalculator.Model.Orderer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-14
 */

public class CalculateView extends BorderPane {

    private final MainFrame mainFrame;
    private AutoCompleteLogic autoCompleteLogic;
    private Separator separatorTop1, separatorTop2, separatorTop3;
    private Label itemLabel, amountLabel, overClockLabel, alternativeRecipeLabel;
    private RadioButton overclockButton, alternativeRecipeButton;
    private Button calculateButton, backButton, exitButton, selectItemButton;
    private TabPane tabPane;
    private TextField amountField;
    private ObservableList<String> items;
    private ComboBox<String> itemBox;

    public CalculateView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();

        setTop(topPart());
        setBottom(botPart());
    }

    private void init() {
        separatorTop1 = new Separator();
        separatorTop2 = new Separator();
        separatorTop3 = new Separator();
        itemLabel = new Label();
        amountLabel = new Label();
        overClockLabel = new Label();
        alternativeRecipeLabel = new Label();
        overclockButton = new RadioButton();
        alternativeRecipeButton = new RadioButton();
        calculateButton = new Button();
        backButton = new Button();
        exitButton = new Button();
        selectItemButton = new Button();
        tabPane = new TabPane();
        amountField = new TextField();
        Vector<Button> buttons = new Vector<>();
        CalculateController calculateController = new CalculateController(mainFrame, buttons, this);
        autoCompleteLogic = new AutoCompleteLogic();
        items = FXCollections.observableArrayList();
        itemBox = new ComboBox<>(items);

        itemBox.setEditable(true);
        itemBox.getItems().addAll(autoCompleteLogic.getAllItemNames());

        buttons.add(calculateButton);
        buttons.add(backButton);
        buttons.add(exitButton);
        buttons.add(selectItemButton);

        backButton.setOnAction(calculateController);
        exitButton.setOnAction(calculateController);
        calculateButton.setOnAction(calculateController);
        selectItemButton.setOnAction(calculateController);
    }

    private VBox topPart() {
        VBox vBox = new VBox();
        ToolBar toolBar = new ToolBar();

        separatorTop1.setPrefWidth(0);
        separatorTop2.setPrefWidth(0);
        separatorTop3.setPrefWidth(0);

        itemLabel.setText("Item: ");
        amountLabel.setText("Amount: ");
        overClockLabel.setText("optimize overclock: ");
        alternativeRecipeLabel.setText("alternative Recipe: ");
        selectItemButton.setText("");
        calculateButton.setText("Calculate");

        ObservableList<Node> list1 = toolBar.getItems();
        list1.addAll(itemLabel, itemBox, separatorTop1, amountLabel, amountField, separatorTop2,
                overClockLabel, overclockButton, alternativeRecipeLabel, alternativeRecipeButton, separatorTop3, calculateButton);

        vBox.setSpacing(30);
        vBox.getChildren().addAll(toolBar);

        itemBox.getEditor().textProperty().addListener((observableValue, oldValue, newValue) -> {
            addItemsToComboBox(autoCompleteLogic.getAllSuggestions(newValue));
        });

        return vBox;
    }

    public void setTabPane(Vector<Orderer> allRecipe) {
        tabPane = new TabPane();
        tabPane.getTabs().removeAll();

        tabPane.getTabs().add(treeShow(allRecipe));
        tabPane.getTabs().add(cumulateView(allRecipe));

        setCenter(tabPane);
    }

    public void addTabToTabPane(Vector<Orderer> allRecipe) {
        tabPane.getTabs().add(treeShow(allRecipe));
        tabPane.getTabs().add(cumulateView(allRecipe));
    }

    private Tab treeShow(Vector<Orderer> allRecipe) {
        Tab tab = new Tab();
        tab.setText("T-V " + allRecipe.get(0).getRecipe().getRecipeName());

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        for (int i = 0; i < allRecipe.size(); i++) {
            String text1 = "Recipe: " +
                    allRecipe.get(i).getRecipe().getRecipeName() + "   ";
            Label label1 = new Label(text1);
            pane.add(label1, allRecipe.get(i).getInset(), i);
            String text2 = "Amount: " +
                    Math.ceil(allRecipe.get(i).getRequiredAmount());
            Label label2 = new Label(text2);
            pane.add(label2, allRecipe.get(i).getInset() + 1, i);
            String text3 = "Building: " +
                    allRecipe.get(i).getBuildingName() + " " +
                    Math.round(allRecipe.get(i).getBuildingAmount() * 100.0) / 100.0;
            Label label3 = new Label(text3);
            pane.add(label3, allRecipe.get(i).getInset() + 2, i);
        }

        VBox vBox = new VBox(pane);
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setPadding(new Insets(10, 10, 10, 10));

        tab.setContent(scrollPane);
        tab.setClosable(false);
        return tab;
    }

    private Tab cumulateView(Vector<Orderer> allRecipe) {
        Tab tab = new Tab();
        tab.setText("Cal Num " + allRecipe.get(0).getRecipe().getRecipeName());

        Cumulator cumulator = new Cumulator();
        Vector<Orderer> cumulatedVector = cumulator.cumulator(allRecipe);


        GridPane pane = new GridPane();
        pane.setHgap(30);
        pane.setVgap(10);

        Label topLabel1 = new Label("Recipe");
        Label topLabel2 = new Label("Amount");
        pane.add(topLabel1, 0, 0);
        pane.add(topLabel2, 1, 0);

        for (int i = 0; i < cumulatedVector.size(); i++) {
            String text1 = cumulatedVector.get(i).getRecipe().getRecipeName() + "   ";
            Label label1 = new Label(text1);
            pane.add(label1, 0, i + 1);
            String text2 = "" + Math.ceil(cumulatedVector.get(i).getAmount());
            Label label2 = new Label(text2);
            pane.add(label2, 1, i + 1);
        }

        VBox vBox = new VBox(pane);
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setPadding(new Insets(10, 10, 10, 10));

        tab.setContent(scrollPane);
        tab.setClosable(false);
        return tab;
    }

    private VBox botPart() {
        VBox vBox = new VBox();
        ToolBar toolBar = new ToolBar();

        backButton.setText("Back");
        exitButton.setText("Exit");

        ObservableList<Node> list2 = toolBar.getItems();
        list2.addAll(backButton, exitButton);

        vBox.setSpacing(30);
        vBox.getChildren().addAll(toolBar);

        return vBox;
    }

    public String getItemFieldText() {
        return itemBox.getValue();
    }

    public String getAmountFieldText() {
        return amountField.getText();
    }

    public boolean getAltEnabled() {
        return alternativeRecipeButton.isSelected();
    }

    private void addItemsToComboBox(Vector<String> allRecipes){
        items.clear();
        items.addAll(allRecipes);
    }
}
