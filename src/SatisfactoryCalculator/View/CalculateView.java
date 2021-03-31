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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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
    private Label itemLabel, amountLabel, alternativeRecipeLabel;
    private RadioButton alternativeRecipeButton;
    private Button calculateButton, backButton, exitButton;
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
        alternativeRecipeLabel = new Label();
        alternativeRecipeButton = new RadioButton();
        calculateButton = new Button();
        backButton = new Button();
        exitButton = new Button();
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

        backButton.setOnAction(calculateController);
        exitButton.setOnAction(calculateController);
        calculateButton.setOnAction(calculateController);
    }

    private VBox topPart() {
        VBox vBox = new VBox();
        ToolBar toolBar = new ToolBar();

        separatorTop1.setPrefWidth(25);
        separatorTop1.setVisible(false);
        separatorTop2.setPrefWidth(25);
        separatorTop2.setVisible(false);
        separatorTop3.setPrefWidth(50);
        separatorTop3.setVisible(false);

        itemLabel.setText("Item: ");
        itemLabel.setStyle("-fx-font-size: 15");
        amountLabel.setText("Amount: ");
        amountLabel.setStyle("-fx-font-size: 15");
        alternativeRecipeLabel.setText("alternative Recipe: ");
        alternativeRecipeLabel.setStyle("-fx-font-size: 15");

        calculateButton.setText("Calculate");
        calculateButton.setStyle("-fx-font-size: 15");

        itemBox.setStyle("-fx-font-size: 15");
        amountField.setStyle("-fx-font-size: 15");

        ObservableList<Node> list1 = toolBar.getItems();
        list1.addAll(itemLabel, itemBox, separatorTop1, amountLabel, amountField, separatorTop2,
                alternativeRecipeLabel, alternativeRecipeButton, separatorTop3, calculateButton);

        vBox.setSpacing(30);
        vBox.getChildren().addAll(toolBar);
        toolBar.setBackground(new Background(new BackgroundFill(Color.rgb(160,160,160), CornerRadii.EMPTY, Insets.EMPTY)));
        toolBar.setPadding(new Insets(10,5,10,5));

        itemBox.getEditor().textProperty().addListener((observableValue, oldValue, newValue) -> {
            addItemsToComboBox(autoCompleteLogic.getAllItemSuggestions(newValue));
        });

        return vBox;
    }

    public void setTabPane(Vector<Orderer> allRecipe) {
        tabPane = new TabPane();
        tabPane.getTabs().removeAll();
        tabPane.setPadding(new Insets(10,10,10,10));
        tabPane.setBackground(new Background(new BackgroundFill(Color.rgb(181,181,181), CornerRadii.EMPTY, Insets.EMPTY)));

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
            pane.add(createItemBoxTree(allRecipe.get(i)), 0, i);
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

        Label topLabel1 = new Label("Amount & ItemName");
        topLabel1.setStyle("-fx-font-size: 20");
        pane.add(topLabel1, 0, 0);

        for (int i = 0; i < cumulatedVector.size(); i++) {
            pane.add(createItemBoxCumulated(cumulatedVector.get(i)), 0, i + 1);
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
        backButton.setStyle("-fx-font-size: 15");
        exitButton.setText("Exit");
        exitButton.setStyle("-fx-font-size: 15");

        ObservableList<Node> list2 = toolBar.getItems();
        list2.addAll(backButton, exitButton);

        vBox.setSpacing(30);
        vBox.getChildren().addAll(toolBar);
        toolBar.setBackground(new Background(new BackgroundFill(Color.rgb(160,160,160), CornerRadii.EMPTY, Insets.EMPTY)));
        toolBar.setPadding(new Insets(10,5,10,5));

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

    private VBox createItemBoxTree(Orderer orderer){
        VBox vBox = new VBox();
        ToolBar toolBar = new ToolBar();

        String text = "\t\t".repeat(Math.max(0, orderer.getInset())) +
                Math.ceil(orderer.getRequiredAmount()) +
                "x \t" + orderer.getRecipe().getRecipeName() +
                ",  " + orderer.getBuildingName() +
                " : " + Math.round(orderer.getBuildingAmount() * 100.0) / 100.0;
        Label textLabel = new Label(text);

        ObservableList<Node> list = toolBar.getItems();
        list.add(textLabel);

        textLabel.setStyle("-fx-font-size: 15");
        vBox.getChildren().addAll(toolBar);
        toolBar.setBackground(new Background(new BackgroundFill(Color.rgb(245,245,245), CornerRadii.EMPTY, Insets.EMPTY)));
        toolBar.setPadding(new Insets(3,3,3,3));

        return vBox;
    }

    private VBox createItemBoxCumulated(Orderer orderer){
        VBox vBox = new VBox();
        ToolBar toolBar = new ToolBar();

        String text = "" + Math.ceil(orderer.getAmount()) + "x \t\t" +
                orderer.getRecipe().getRecipeName();
        Label textLabel = new Label(text);

        ObservableList<Node> list = toolBar.getItems();
        list.add(textLabel);

        textLabel.setStyle("-fx-font-size: 15");
        vBox.getChildren().addAll(toolBar);
        toolBar.setBackground(new Background(new BackgroundFill(Color.rgb(245,245,245), CornerRadii.EMPTY, Insets.EMPTY)));
        toolBar.setPadding(new Insets(3,3,3,3));

        return vBox;
    }
}
