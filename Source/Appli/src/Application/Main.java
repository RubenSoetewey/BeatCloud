package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = getClass().getResource("application.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        BorderPane root = (BorderPane)loader.load();
        Controller controller = loader.getController();
        primaryStage.setTitle("BeatCloud");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/Logo.png")));
        primaryStage.setScene(new Scene(root, 1200, 750));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
