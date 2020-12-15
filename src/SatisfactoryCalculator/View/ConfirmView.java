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
 * @since 2020-Dezember-11
 */

public class ConfirmView {

    private static boolean answer;
    private static Stage window;
    private static Label textLabel;
    private static Button noButton, yesButton;
    private static String text;

    public static boolean display(String title, String text){
        ConfirmView.text = text;

        init();

        try {
            window.getIcons().add(new Image(new FileInputStream("C:\\Users\\Felix\\OneDrive\\Felix\\Privat\\Programmieren\\SatisfactoryCalculator\\src\\SatisfactoryCalculator\\img\\satisfactoryIcon.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        window.setTitle(title);
        window.setScene(new Scene(windowContent(), 250, 100));
        window.setAlwaysOnTop(true);
        window.centerOnScreen();
        window.showAndWait();

        return answer;
    }

    private static void init() {
        window = new Stage();
        textLabel = new Label();
        noButton = new Button();
        yesButton = new Button();
    }

    private static BorderPane windowContent(){
        BorderPane borderPane = new BorderPane();

        textLabel.setText("Save the changes?");
        textLabel.setStyle("-fx-font-size: 15");

        borderPane.setCenter(textLabel);
        borderPane.setBottom(botPart());
        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(160,160,160), CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setPadding(new Insets(5,5,5,5));
        buttonAction();

        return borderPane;
    }

    private static BorderPane botPart() {
        BorderPane borderPane = new BorderPane();

        noButton.setText("No");
        yesButton.setText("Yes");

        noButton.setStyle("-fx-font-size: 15");
        yesButton.setStyle("-fx-font-size: 15");

        borderPane.setLeft(noButton);
        borderPane.setRight(yesButton);

        return borderPane;
    }

    private static void buttonAction() {
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
