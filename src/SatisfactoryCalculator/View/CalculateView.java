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

    private MainFrame mainFrame;
    private CalculateController calculateController;
    private AutoCompleteLogic autoCompleteLogic;
    private Separator separatorTop1, separatorTop2, separatorTop3;
    private Label itemLabel, amountLabel, overClockLabel, alternativeRecipeLabel;
    private RadioButton overclockButton, alternativeRecipeButton;
    private Button calculateButton, backButton, exitButton, selectItemButton;
    private ComboBox<String> itemBox;
    private TabPane tabPane;
    private TextField itemField, amountField;
    private Vector<Button> buttons;
    private Vector<Orderer> allRecipe;
    private Vector<String> possibilities;
    private ObservableList<String> observableList;

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
        itemField = new TextField();
        amountField = new TextField();
        buttons = new Vector<>();
        possibilities = new Vector<>();
        observableList = FXCollections.observableList(possibilities);
        itemBox = new ComboBox<>(observableList);
        calculateController = new CalculateController(mainFrame, buttons, this);
        autoCompleteLogic = new AutoCompleteLogic();

        //TODO remove this
        itemField.setText("Reinforced Iron Plate");
        amountField.setText("20");

        buttons.add(calculateButton);
        buttons.add(backButton);
        buttons.add(exitButton);
        buttons.add(selectItemButton);

        backButton.setOnAction(calculateController);
        exitButton.setOnAction(calculateController);
        calculateButton.setOnAction(calculateController);
        selectItemButton.setOnAction(calculateController);

        itemBox.setEditable(true);
        itemBox.getItems().setAll(autoCompleteLogic.getAllItemNames());
        //TODO set height of dropdown
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
        list1.addAll(itemLabel, itemField, separatorTop1, amountLabel, amountField, separatorTop2,
                overClockLabel, overclockButton, alternativeRecipeLabel, alternativeRecipeButton, separatorTop3, calculateButton);

        vBox.setSpacing(30);
        vBox.getChildren().addAll(toolBar);

        itemField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                itemField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        itemBox.getEditor().textProperty().addListener((observableValue, oldValue, newValue) -> {
            addItemsToComboBox(autoCompleteLogic.getAllSuggestions(newValue));
        });

        //TODO only numbers and one point

        /*
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            String text = oldValue + newValue;
            if (!text.matches("\\d*\\.?")){
                amountField.setText(text.substring(0, text.length() - 1));
            }
        });
         */

        return vBox;
    }

    public void centerPart(Vector<Orderer> allRecipe) {
        this.allRecipe = null;
        this.allRecipe = allRecipe;

        tabPane = new TabPane();
        tabPane.getTabs().removeAll();
        tabPane.getTabs().add(treeShow());
        tabPane.getTabs().add(cumulateView());

        setCenter(tabPane);
    }

    private Tab treeShow(){
        Tab tab = new Tab();
        tab.setText("Tree - View");

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
                    allRecipe.get(i).getBuildingAmount();
            Label label3 = new Label(text3);
            pane.add(label3, allRecipe.get(i).getInset() + 2, i);
        }

        VBox vBox = new VBox(pane);
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setPadding(new Insets(10,10,10,10));

        tab.setContent(scrollPane);
        tab.setClosable(false);
        return tab;
    }

    private Tab cumulateView(){
        Tab tab = new Tab();
        tab.setText("Cumulated Numbers");

        Cumulator cumulator = new Cumulator();
        Vector<Orderer> cumulatedVector = cumulator.cumulator(allRecipe);

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        for (int i = 0; i < cumulatedVector.size(); i++) {
            String text1 = "Recipe: " + cumulatedVector.get(i).getRecipe().getRecipeName() + "   ";
            Label label1 = new Label(text1);
            pane.add(label1, 0, i);
            String text2 = "Amount: " + Math.ceil(cumulatedVector.get(i).getAmount());
            Label label2 = new Label(text2);
            pane.add(label2, 1, i);
        }

        VBox vBox = new VBox(pane);
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setPadding(new Insets(10,10,10,10));

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
        return itemField.getText();
    }

    public String getAmountFieldText() {
        return amountField.getText();
    }

    public int getMaxInset(Vector<Orderer> allRecipe) {
        int maxInset = allRecipe.get(0).getInset();
        for (Orderer orderer : allRecipe) {
            if (maxInset < orderer.getInset()) maxInset = orderer.getInset();
        }
        return maxInset;
    }

    public void addItemsToComboBox(Vector<String> allSuggestions){
        itemBox.getItems().clear();
        for (String s : allSuggestions){
            itemBox.getItems().add(s);
        }
    }
}
