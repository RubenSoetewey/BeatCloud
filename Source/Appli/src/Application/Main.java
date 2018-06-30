package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.print.attribute.standard.Media;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        URL applicationURL = getClass().getResource("application.fxml");
        FXMLLoader loaderApplication = new FXMLLoader(applicationURL);
        BorderPane rootApplication = (BorderPane)loaderApplication.load();
        ApplicationController applicationController = loaderApplication.getController();
        primaryStage.setTitle("BeatCloud");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/Logo.png")));
        primaryStage.setScene(new Scene(rootApplication, 1200, 750));
        //primaryStage.show();

        URL connectionURL = getClass().getResource("connection.fxml");
        FXMLLoader loaderConnection = new FXMLLoader(connectionURL);
        AnchorPane rootConnection = (AnchorPane) loaderConnection.load();
        ConnectionController connection = loaderConnection.getController();
        Stage connectionStage = new Stage(StageStyle.DECORATED);
        connectionStage.setTitle("Connexion");
        connectionStage.initModality(Modality.APPLICATION_MODAL);
        connectionStage.setScene(new Scene(rootConnection, 500, 450));
        //connectionStage.show();

        URL registerURL = getClass().getResource("register.fxml");
        FXMLLoader loaderRegister = new FXMLLoader(registerURL);
        AnchorPane rootRegister = (AnchorPane) loaderRegister.load();
        RegisterController register = loaderRegister.getController();
        Stage registerStage = new Stage(StageStyle.DECORATED);
        registerStage.setTitle("Inscription");
        registerStage.initModality(Modality.APPLICATION_MODAL);
        registerStage.setScene(new Scene(rootRegister, 500, 710));
        registerStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
