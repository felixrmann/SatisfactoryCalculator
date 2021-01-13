package SatisfactoryCalculator.View;

import SatisfactoryCalculator.Model.Building;
import SatisfactoryCalculator.Service.BuildingService;
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

public class EditBuildingView {

    private static Building editBuilding;
    private static String param;
    private static Stage window;
    private static TextField nameInField;
    private static Label nameInLabel;

    private static Button backButton, saveButton, deleteButton;
    private static Separator separator;

    public static void display(Building editBuilding, MainFrame mainFrame, String param){
        EditBuildingView.editBuilding = editBuilding;
        EditBuildingView.param = param;

        init();
        if (param.equals("e")) setData();
        else editBuilding = new Building();

        try {
            window.getIcons().add(new Image(new FileInputStream("C:\\Users\\Felix\\OneDrive\\Felix\\Privat\\Programmieren\\SatisfactoryCalculator\\src\\SatisfactoryCalculator\\img\\satisfactoryIcon.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (editBuilding.getBuildingName() != null) window.setTitle("Edit Window of " + editBuilding.getBuildingName());
        else window.setTitle("Edit Window of new Building");
        window.setScene(new Scene(windowContent(), 280, 80));
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

    private static void setAction(){
        backButton.setOnAction(event -> {
            window.close();
        });
        saveButton.setOnAction(event -> {
            boolean answer = ConfirmView.display("Save", "Save Changes?");
            if (!answer) window.close();
            else {
                if (param.equals("a")){
                    BuildingService.saveNew(getInputs());
                } else if (hasChanged()){
                    BuildingService.saveChanges(getInputs(), editBuilding.getBuildingUUID());
                }
                window.close();
            }
            SectorView.updateBuildingList();
        });
        deleteButton.setOnAction(event -> {
            boolean answer = ConfirmView.display("Delete", "Delete this Recipe?");
            if (!answer) window.close();
            else {
                BuildingService.delete(editBuilding);
                window.close();
            }
            SectorView.updateBuildingList();
        });
    }

    private static void setData() {
        nameInField.setText(editBuilding.getBuildingName());
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

        nameInLabel.setText("Buildingname: ");

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
        separator.setPrefWidth(123);

        ObservableList<Node> list = toolBar.getItems();
        list.addAll(backButton, separator, deleteButton, saveButton);

        vBox.setSpacing(100);
        vBox.getChildren().addAll(toolBar);

        return vBox;
    }

    private static Building getInputs(){
        Building building = new Building();

        if (param.equals("a")) building.setBuildingUUID(UUID.randomUUID().toString());
        building.setBuildingName(nameInField.getText());

        return building;
    }

    private static boolean hasChanged(){
        return !nameInField.getText().equals(editBuilding.getBuildingName());
    }
}
