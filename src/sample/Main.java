package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage=null;
    public static Scene scene = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //sets the background color to white
//        root.setStyle("-fx-background-color: #FFFFFF;");

        primaryStage.setTitle("Paint");
        scene = new Scene(root);
        primaryStage.setScene(scene);
        stage=primaryStage;
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}