package SatisfactoryCalculator.View;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-13
 */

public class CalculatorWindow extends Application {

    private ObservableList<javafx.scene.Node> list;
    private Separator separatorTop1, separatorTop2;
    private Label itemLabel, amountLabel;
    private Button calculateButton;
    private TextField itemField, amountField;
    private BorderPane borderPane;
    private ToolBar toolBar;

    public void allInitialize(){
        separatorTop1 = new Separator();
        separatorTop2 = new Separator();
        itemLabel = new Label();
        amountLabel = new Label();
        calculateButton = new Button();
        itemField = new TextField();
        amountField = new TextField();
        borderPane = new BorderPane();
        toolBar = new ToolBar();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Satisfactory Calculator");
        allInitialize();

        borderPane.setTop(setTopPane());

        Scene scene = new Scene(borderPane, 800, 800);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox setTopPane(){
        VBox vBox = new VBox();

        itemLabel.setText("Item");
        amountLabel.setText("Amount");
        calculateButton.setText("Calculate");

        separatorTop1.setPrefWidth(100);
        separatorTop2.setPrefWidth(100);

        list = toolBar.getItems();
        list.addAll(itemLabel, itemField, separatorTop1, amountLabel, amountField, separatorTop2, calculateButton);

        vBox.setSpacing(30);
        vBox.getChildren().addAll(toolBar);

        return vBox;
    }
}
