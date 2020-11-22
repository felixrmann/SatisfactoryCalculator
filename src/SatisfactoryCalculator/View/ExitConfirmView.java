package SatisfactoryCalculator.View;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-14
 */

public class ExitConfirmView {

    private static boolean answer;
    private static Stage window;
    private static Separator separator;
    private static Label textLabel;
    private static Button noButton, yesButton;

    public static boolean display(){
        init();

        try {
            window.getIcons().add(new Image(new FileInputStream("C:\\Users\\Felix\\OneDrive\\Felix\\Privat\\Programmieren\\SatisfactoryCalculator\\src\\SatisfactoryCalculator\\img\\satisfactoryIcon.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        window.setTitle("Exit?");
        window.setScene(new Scene(windowContent(), 250, 100));
        window.centerOnScreen();
        window.showAndWait();

        return answer;
    }

    private static void init(){
        window = new Stage();
        separator = new Separator();
        textLabel = new Label();
        noButton = new Button();
        yesButton = new Button();
    }

    private static BorderPane windowContent(){
        BorderPane borderPane = new BorderPane();

        textLabel.setText("Do you want to exit the program?");
        textLabel.setStyle("-fx-font-size: 15");

        borderPane.setCenter(textLabel);
        borderPane.setBottom(bottomPart());
        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(160,160,160), CornerRadii.EMPTY, Insets.EMPTY)));
        buttonAction();

        return borderPane;
    }

    private static VBox bottomPart(){
        VBox vBox = new VBox();
        ToolBar toolBar = new ToolBar();

        noButton.setText("No");
        yesButton.setText("Yes");

        noButton.setStyle("-fx-font-size: 15");
        yesButton.setStyle("-fx-font-size: 15");

        separator.setPrefWidth(125);

        ObservableList<Node> list = toolBar.getItems();
        list.addAll(noButton, separator, yesButton);

        vBox.setSpacing(30);
        vBox.getChildren().addAll(toolBar);

        return vBox;
    }

    private static void buttonAction(){
        noButton.setOnAction(event -> {
            answer = false;
            window.close();
        });
        yesButton.setOnAction(event -> {
            answer = true;
            window.close();
        });
    }
}
