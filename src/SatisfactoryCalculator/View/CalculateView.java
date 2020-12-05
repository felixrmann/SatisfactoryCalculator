package SatisfactoryCalculator.View;

import SatisfactoryCalculator.Controller.CalculateController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
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
    private Separator separatorTop1, separatorTop2, separatorTop3;
    private Label itemLabel, amountLabel, overClockLabel, alternativeRecipeLabel;
    private RadioButton overclockButton, alternativeRecipeButton;
    private Button calculateButton, backButton, exitButton;
    private TextField itemField, amountField;
    private Vector<Button> buttons;

    public CalculateView(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        init();

        setTop(topPart());
        //setCenter();
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
        itemField = new TextField();
        amountField = new TextField();
        buttons = new Vector<>();
        calculateController = new CalculateController(mainFrame, buttons, this);

        //TODO remove this
        itemField.setText("Computer");
        amountField.setText("123");

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

        separatorTop1.setPrefWidth(15);
        separatorTop2.setPrefWidth(15);
        separatorTop3.setPrefWidth(15);

        itemLabel.setText("Item: ");
        amountLabel.setText("Amount: ");
        overClockLabel.setText("optimize overclock: ");
        alternativeRecipeLabel.setText("alternative Recipe: ");
        calculateButton.setText("Calculate");

        ObservableList<Node> list1 = toolBar.getItems();
        list1.addAll(itemLabel, itemField, separatorTop1, amountLabel, amountField, separatorTop2,
                overClockLabel, overclockButton, alternativeRecipeLabel, alternativeRecipeButton, separatorTop3, calculateButton);

        vBox.setSpacing(30);
        vBox.getChildren().addAll(toolBar);

        itemField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")){
                itemField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")){
                amountField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        return vBox;
    }

    private void centerPart(){
        //TODO
    }

    private VBox botPart(){
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

    public String getItemFieldText(){
        return itemField.getText();
    }

    public String getAmountFieldText(){
        return amountField.getText();
    }
}
