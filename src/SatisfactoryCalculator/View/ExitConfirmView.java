package SatisfactoryCalculator.View;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
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
        borderPane.setPadding(new Insets(5,5,5,5));
        buttonAction();

        return borderPane;
    }

    private static BorderPane bottomPart(){
        BorderPane pane = new BorderPane();

        noButton.setText("No");
        yesButton.setText("Yes");

        noButton.setStyle("-fx-font-size: 15");
        yesButton.setStyle("-fx-font-size: 15");

        pane.setRight(yesButton);
        pane.setLeft(noButton);

        return pane;
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
