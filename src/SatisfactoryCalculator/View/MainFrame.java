package SatisfactoryCalculator.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-14
 */

public class MainFrame extends Application {

    Stage window;
    Scene menuScene;

    @Override
    public void start(Stage primaryStage) {
        init(primaryStage);
        try {
            window.getIcons().add(new Image(new FileInputStream("C:\\Users\\Felix\\OneDrive\\Felix\\Privat\\Programmieren\\SatisfactoryCalculator\\src\\SatisfactoryCalculator\\img\\satisfactoryIcon.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        window.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });
        window.setResizable(false);
        window.setTitle("Menu");
        window.setScene(menuScene);
        window.show();
    }

    public void init(Stage primaryStage){
        window = primaryStage;

        menuScene = new Scene(new MenuView(this), 350, 429);
    }

    public void setNewScene(BorderPane borderPane, double width, double height){
        window.setScene(new Scene(borderPane, width, height));
        window.centerOnScreen();
    }

    public void closeProgram(){
        boolean answer = ConfirmView.display("Exit", "Do you want to exit the program?");
        if (answer) window.close();
    }

    public Stage getStage(){
        return window;
    }

    public void setSceneSize(double width, double height){
        window.setScene(new Scene(window.getScene().getRoot() , width, height));
    }
}
