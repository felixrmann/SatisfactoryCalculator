package SatisfactoryCalculator.View;

import SatisfactoryCalculator.Model.Item;
import SatisfactoryCalculator.Service.ItemService;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * @author Felix Mann
 * @version 1.0
 * @since 2020-Dezember-15
 */

public class EditItemView {

    private static Item editItem;
    private static String param;
    private static Stage window;
    private static TextField nameInField;
    private static Label nameInLabel;

    private static Button backButton, saveButton, deleteButton;
    private static Separator separator;

    public static void display(Item editItem, MainFrame mainFrame, String param){
        EditItemView.editItem = editItem;
        EditItemView.param = param;

        init();
        if (param.equals("e")) setData();
        else editItem = new Item();

        try {
            window.getIcons().add(new Image(new FileInputStream("C:\\Users\\Felix\\OneDrive\\Felix\\Privat\\Programmieren\\SatisfactoryCalculator\\src\\SatisfactoryCalculator\\img\\satisfactoryIcon.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (editItem.getItemName() != null) window.setTitle("Edit Window of " + editItem.getItemName());
        else window.setTitle("Edit Window of new Building");
        window.setScene(new Scene(windowContent(), 260, 80));
        window.setResizable(false);
        window.initOwner(mainFrame.getStage());
        window.initModality(Modality.WINDOW_MODAL);
        window.setAlwaysOnTop(true);
        window.centerOnScreen();
        window.showAndWait();
        window.setOnCloseRequest(event -> {
            event.consume();
            if (!hasChanged()) window.close();
            else {
                boolean answer = ConfirmView.display("Exit", "Do you want to exit?");
                if (answer) window.close();
            }
        });
    }

    private static void init() {
        window = new Stage();
        nameInField = new TextField();
        nameInLabel = new Label();
        backButton = new Button();
        saveButton = new Button();
        deleteButton = new Button();
        separator = new Separator();

        if (param.equals("a")) deleteButton.setVisible(false);

        setAction();
    }

    private static void setAction() {
        backButton.setOnAction(event -> {
            window.close();
        });
        saveButton.setOnAction(event -> {
            boolean answer = ConfirmView.display("Save", "Save Changes?");
            if (!answer) window.close();
            else {
                if (param.equals("a")){
                    ItemService.saveNew(getInputs());
                } else if (hasChanged()){
                    ItemService.saveChanges(getInputs(), editItem.getItemUUID());
                }
                window.close();
            }
            SectorView.updateItemList();
        });
        deleteButton.setOnAction(event -> {
            boolean answer = ConfirmView.display("Delete", "Delete this Recipe?");
            if (!answer) window.close();
            else {
                ItemService.delete(editItem);
                window.close();
            }
            SectorView.updateItemList();
        });
    }

    private static void setData() {
        nameInField.setText(editItem.getItemName());
    }

    private static BorderPane windowContent(){
        BorderPane borderPane = new BorderPane();

        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(160,160,160), CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setCenter(centerPart());
        borderPane.setBottom(botPart());

        return borderPane;
    }

    private static VBox centerPart(){
        VBox vBox = new VBox();

        nameInLabel.setText("Itemname: ");

        GridPane pane = new GridPane();
        pane.add(nameInLabel, 0,0);
        pane.add(nameInField, 1,0);

        pane.setHgap(30);
        pane.setVgap(10);
        pane.setPadding(new Insets(10,10,10,10));

        vBox.getChildren().add(pane);

        return vBox;
    }

    private static VBox botPart(){
        VBox vBox = new VBox();
        ToolBar toolBar = new ToolBar();

        backButton.setText("Back");
        saveButton.setText("Save");
        deleteButton.setText("Delete");
        separator.setPrefWidth(103);

        ObservableList<Node> list = toolBar.getItems();
        list.addAll(backButton, separator, deleteButton, saveButton);

        vBox.setSpacing(100);
        vBox.getChildren().addAll(toolBar);

        return vBox;
    }

    private static Item getInputs(){
        Item item = new Item();

        if (param.equals("a")) item.setItemUUID(UUID.randomUUID().toString());
        item.setItemName(nameInField.getText());

        return item;
    }

    private static boolean hasChanged(){
        return !nameInField.getText().equals(editItem.getItemName());
    }
}
