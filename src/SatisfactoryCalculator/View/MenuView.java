package SatisfactoryCalculator.View;

import SatisfactoryCalculator.Controller.MenuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Vector;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-November-14
 */

public class MenuView extends BorderPane {

    private MainFrame mainFrame;
    private MenuController menuController;
    private GridPane gridPane;
    private Vector<Button> buttons;
    private Button calculateButton, itemButton, recipeButton, buildingButton, exitButton;

    public MenuView(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        init();

        setGridPane();

        setCenter(gridPane);
        setPadding(new Insets(20,20,20,20));
        setBackground(new Background(new BackgroundFill(Color.rgb(160,160,160), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void init() {
        buttons = new Vector<>();
        menuController = new MenuController(mainFrame, buttons);
        gridPane = new GridPane();

        calculateButton = new Button();
        itemButton = new Button();
        recipeButton = new Button();
        buildingButton = new Button();
        exitButton = new Button();

        buttons.add(calculateButton);
        buttons.add(itemButton);
        buttons.add(recipeButton);
        buttons.add(buildingButton);
        buttons.add(exitButton);
    }

    private void setGridPane() {
        calculateButton.setText("Calculate");
        itemButton.setText("Items");
        recipeButton.setText("Recipes");
        buildingButton.setText("Buildings");
        exitButton.setText("Exit");

        setImage();

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setPrefSize(120,40);
            buttons.get(i).setStyle("-fx-font-size: 20");
            buttons.get(i).setOnAction(menuController);
            gridPane.add(buttons.get(i), 0, i + 1);
            gridPane.setAlignment(Pos.BOTTOM_LEFT);
        }

        gridPane.setVgap(15);
    }

    private void setImage() {
        Image image = null;
        try {
            image = new Image(new FileInputStream("C:\\Users\\Felix\\OneDrive\\Felix\\Privat\\Programmieren\\SatisfactoryCalculator\\src\\SatisfactoryCalculator\\img\\satisfactoryLogo.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(310);
        imageView.setFitHeight(89);
        setTop(imageView);
    }
}
