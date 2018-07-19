package Application.Controllers;

import Application.Controllers.ApplicationController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileController {
    @FXML
    Stage primaryStage;
    @FXML
    ApplicationController applicationController;
    @FXML
    MenuItem newFile;

    /*public void init(ApplicationController mainApplication) {
        applicationController = mainApplication;
    }*/

    @FXML
    public void createNewFile(ActionEvent event){
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Create new file");
        fileChoosed.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        File selectedFile = fileChoosed.showSaveDialog(primaryStage);
        if (selectedFile != null) {
            System.out.println(selectedFile);
            //openFile(selectedFile);
            //System.out.println("You chose to open this file: " + fileChoosed.getTitle());//.getSelectedFile().getName());
            //mainStage.display(selectedFile);
        }
    }

    @FXML
    MenuItem openFile;
    @FXML
    public void openFile(final ActionEvent event) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Open file");
        fileChoosed.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        File selectedFile = fileChoosed.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            System.out.println(selectedFile);
            //openFile(selectedFile);
            //System.out.println("You chose to open this file: " + fileChoosed.getTitle());//.getSelectedFile().getName());
            //mainStage.display(selectedFile);
        }
    }

    @FXML
    MenuItem saveFile;
    @FXML
    public void saveFile(ActionEvent event) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Save File");
        fileChoosed.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
        saveFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        File selectedFile = fileChoosed.showSaveDialog(primaryStage);
        if (selectedFile != null) {
            System.out.println(selectedFile);
            //openFile(selectedFile);
            //System.out.println("You chose to open this file: " + fileChoosed.getTitle());//.getSelectedFile().getName());
            //mainStage.display(selectedFile);
        }
    }
}
