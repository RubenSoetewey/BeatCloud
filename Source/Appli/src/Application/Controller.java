package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller{
    @FXML
    Stage primaryStage;

    //Instruments
    @FXML
    Button pianoButton;
    @FXML
    public void handlePiano(ActionEvent event){
        System.out.println("ok piano");
    }

    @FXML
    Button drumsButton;
    @FXML
    public void handleDrums(ActionEvent event){
        System.out.println("ok drums");
    }

    @FXML
    Button guitarButton;
    @FXML
    public void handleGuitar(ActionEvent event){
        System.out.println("ok guitar");
    }

    @FXML
    Button violinButton;
    @FXML
    public void handleViolin(ActionEvent event){
        System.out.println("ok violin");
    }

    //Files
    @FXML
    MenuItem newFile;
    @FXML
    public void createNewFile(ActionEvent event){
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        File selectedFile = fileChoosed.showSaveDialog(primaryStage);
    }

    @FXML
    MenuItem openFile;
    @FXML
    public void openFile(ActionEvent event) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        File selectedFile = fileChoosed.showOpenDialog(primaryStage);
    }

    @FXML
    MenuItem saveFile;
    @FXML
    public void saveFile(ActionEvent event) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
        saveFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        File selectedFile = fileChoosed.showSaveDialog(primaryStage);
    }


}
