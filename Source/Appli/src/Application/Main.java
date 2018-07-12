package Application;

import Application.Controllers.ConnectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Locale;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL connectionURL = getClass().getResource("Fxml/connection.fxml");
        FXMLLoader loaderConnection = new FXMLLoader(connectionURL);
        AnchorPane rootConnection = (AnchorPane) loaderConnection.load();
        ConnectionController connection = loaderConnection.getController();
        Stage connectionStage = new Stage(StageStyle.DECORATED);
        connectionStage.setTitle("Connexion");
        connectionStage.setResizable(false);
        connectionStage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/Logo.png")));
        connectionStage.initModality(Modality.APPLICATION_MODAL);
        connectionStage.setScene(new Scene(rootConnection, 500, 450));

        connectionStage.show();
    }

    public static void main(String[] args) {
        //Locale.setDefault(Locale.FRANCE);
        launch(args);
    }
}
