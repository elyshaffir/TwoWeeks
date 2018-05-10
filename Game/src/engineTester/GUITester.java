package engineTester;

import javafx.application.Application;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class GUITester extends Application implements EventHandler<ActionEvent> {

    private Button connectButton;
    private TextField IPField;
    private TextField IDField;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Label IPLabel = new Label("IP:");
        IPLabel.setLayoutX(10);
        IPLabel.setLayoutY(20);

        IPField = new TextField();
        IPField.setPrefWidth(294);

        NumberBinding IPFieldXBinding =
                IPLabel.layoutXProperty().add(IPLabel.widthProperty().add(10));
        IPField.layoutXProperty().bind(IPFieldXBinding);
        IPField.layoutYProperty().bind(IPLabel.layoutYProperty());

        Label IDLabel = new Label("ID Number:");
        IPLabel.setLayoutX(0);
        IPLabel.setLayoutY(50);

        IDField = new TextField();
        IDField.setPromptText("Make sure you're the only one using that ID!");
        IDField.setPrefWidth(245);

        NumberBinding IDFieldXBinding =
                IDLabel.layoutXProperty().add(IDLabel.widthProperty().add(10));
        IDField.layoutXProperty().bind(IDFieldXBinding);
        IDField.layoutYProperty().bind(IDLabel.layoutYProperty());


        connectButton = new Button("Connect");

        connectButton.setLayoutX(120);
        connectButton.setLayoutY(90);
        connectButton.setOnAction(this);

        Group root = new Group();
        root.getChildren().addAll(IPLabel, IPField, IDLabel, IDField, connectButton);

        Scene scene = new Scene(root, 310, 115);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Two Weeks");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == connectButton) {
            int id;
            try{
                id = Integer.parseInt(IDField.getText());
            } catch (NumberFormatException e){
                IDField.setText("");
                IDField.setPromptText("Invalid ID number! try again.");
                return;
            }
            RunningPlayer runningPlayer = new RunningPlayer(IPField.getText(), id, false);
            runningPlayer.start();
        }
    }
}