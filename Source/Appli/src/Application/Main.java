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
        Stage secondStage = new Stage(StageStyle.DECORATED);
        secondStage.setTitle("Connection");
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setScene(new Scene(rootConnection, 500, 450));
        secondStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
