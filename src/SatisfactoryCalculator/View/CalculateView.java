package SatisfactoryCalculator.View;

import SatisfactoryCalculator.Controller.CalculateController;
import SatisfactoryCalculator.Model.AutoCompleteLogic;
import SatisfactoryCalculator.Model.Orderer;
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
    private Button calculateButton, backButton, exitButton;
    private ComboBox<String> itemBox;
    private TextField itemField, amountField;
    private Vector<Button> buttons;
    private Vector<Orderer> allRecipe;
    private int globalCnt;

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
        itemBox = new ComboBox<>();
        itemField = new TextField();
        amountField = new TextField();
        buttons = new Vector<>();
        calculateController = new CalculateController(mainFrame, buttons, this);
        autoCompleteLogic = new AutoCompleteLogic();

        //TODO remove this
        itemField.setText("Reinforced Iron Plate");
        amountField.setText("20");

        buttons.add(calculateButton);
        buttons.add(backButton);
        buttons.add(exitButton);

        backButton.setOnAction(calculateController);
        exitButton.setOnAction(calculateController);
        calculateButton.setOnAction(calculateController);

        itemBox.setEditable(true);
        //itemBox.getItems().setAll(autoCompleteLogic.getAllItemNames());
        //TODO set height of dropdown
    }

    private VBox topPart() {
        VBox vBox = new VBox();
        ToolBar toolBar = new ToolBar();

        separatorTop1.setPrefWidth(15);
        separatorTop2.setPrefWidth(15);
        separatorTop3.setPrefWidth(15);

        itemLabel.setText("Item: ");
        amountLabel.setText("Amount: ");
        overClockLabel.setText("optimize overclock: ");
        alternativeRecipeLabel.setText("alternative Recipe: ");
        calculateButton.setText("Calculate");

        ObservableList<Node> list1 = toolBar.getItems();
        list1.addAll(itemLabel, itemBox, separatorTop1, amountLabel, amountField, separatorTop2,
                overClockLabel, overclockButton, alternativeRecipeLabel, alternativeRecipeButton, separatorTop3, calculateButton);

        vBox.setSpacing(30);
        vBox.getChildren().addAll(toolBar);

        itemField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                itemField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        itemBox.getEditor().textProperty().addListener((observableValue, oldValue, newValue) -> {
            addItemsToComboBox(autoCompleteLogic.getAllSuggestions(itemBox.getValue()));
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
        this.allRecipe = allRecipe;

        GridPane pane = new GridPane();
        pane.setHgap(30);
        pane.setVgap(10);

        for (int i = 0; i < allRecipe.size(); i++) {
            String text1 = "Recipe: " +
                    allRecipe.get(i).getRecipe().getRecipeName() + "   ";
            Label label1 = new Label(text1);
            pane.add(label1, allRecipe.get(i).getInset(), i);
            String text2 = "Amount: " +
                    allRecipe.get(i).getRequiredAmount();
            Label label2 = new Label(text2);
            pane.add(label2, allRecipe.get(i).getInset() + 1, i);
        }

        VBox vBox = new VBox(pane);
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setPadding(new Insets(10,10,10,10));
        setCenter(scrollPane);
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
