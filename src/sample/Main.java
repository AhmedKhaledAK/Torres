package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage=null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //sets the background color to white
        root.setStyle("-fx-background-color: #FFFFFF;");
        primaryStage.setTitle("Paint");
        primaryStage.setScene(new Scene(root, 800, 800));
        stage=primaryStage;
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}