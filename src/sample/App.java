/**
 * Created by Sivasubramanian on 8/17/2017.
 */

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class App extends Application {
    public Connection conn;
    PreparedStatement pst = null;
    //Button btn = new Button("=");

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Howell_Calci");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 300, 350));
        primaryStage.show();
//
    }


    public static void main(String[] args) {
        launch(args);
    }
}
