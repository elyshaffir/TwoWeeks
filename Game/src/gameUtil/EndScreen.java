package gameUtil;

import engineTester.GUITester;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EndScreen extends Application implements EventHandler<ActionEvent>{

    private Button playAgain;
    private Button exit;
    private Stage primaryStage;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        playAgain = new Button("Play Again");
        playAgain.setOnAction(this);
        playAgain.setLayoutX(69);
        playAgain.setLayoutY(340);

        exit = new Button("Exit Game");
        exit.setOnAction(this);
        exit.setLayoutX(70);
        exit.setLayoutY(370);


        Group root = new Group();
        root.getChildren().addAll(playAgain, exit);

        addWinnersToScreen(root);

        Scene scene = new Scene(root, 200, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Two Weeks");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void playAgain(){
        Platform.runLater(() -> {
            GUITester s = new GUITester();
            try {
                s.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void exitWindow(){
        Platform.exit();
    }

    private void addWinnersToScreen(Group root){
        int winnerCounter = 1;
        final double DISTANCE_FROM_WINNERS = 10;
        for (String winner:WinnerGetter.getWinners()){
            Label winnerToDisplay = new Label(winnerCounter + "st: " + winner);
            winnerToDisplay.setLayoutY(DISTANCE_FROM_WINNERS * winnerCounter);
            root.getChildren().add(winnerToDisplay);
            winnerCounter++;
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == playAgain)
            playAgain();
        if (event.getSource() == exit)
            exitWindow();
    }
}