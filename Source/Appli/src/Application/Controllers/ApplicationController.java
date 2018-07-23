package Application.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import Application.Util.BCButton;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import Application.Util.JarLoader;

import static Application.Util.SoundMixer.concatWav;

public class ApplicationController extends BorderPane {

    public String token = "";
    @FXML
    ChoiceBox pluginId;

    @FXML
    Stage primaryStage;
    @FXML
    Pane musicPane;
    @FXML
    HBox dropZone;
    @FXML
    Button validMusic;
    @FXML
    public void validMusicEnter (MouseEvent onMouseEntered) { validMusic.setStyle("-fx-background-color: green"); }
    @FXML
    private void validMusicExit (MouseEvent onMouseExited) { validMusic.setStyle("-fx-background-color: darkturquoise"); }
    JarLoader jarLoader = new JarLoader();
    @FXML
    private void validMusicCliqued (MouseEvent onMouseCliqued){
        List<String> allFiles = new ArrayList<>();
        for (Node n: ( dropZone.getChildren())) {
            try{
                BCButton b = (BCButton)n;
                allFiles.add(b.associatedFileURL);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date date = new Date();
            String nb = dateFormat.format(date);
            String filename = "Output"+nb+".wav";
            concatWav(filename,allFiles);
             File file=new File(filename);
             String source=file.toURI().toString();
            Media media = new Media(source);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            String pluginKey = pluginId.getValue().toString();
            ArrayList<String> params = new ArrayList<String>();

            params.add(this.token);
            params.add(filename);

            if(pluginKey.length() > 0){
                System.out.println(this.jarLoader.execPlugin(pluginKey, params));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importFile(ActionEvent actionEvent) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Open file");
        fileChoosed.setInitialDirectory(new File("./src/Application/Music"));
        fileChoosed.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Audio Files", "*.wav"));
        File selectedFile = fileChoosed.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            BCButton button =  new BCButton(selectedFile.getAbsoluteFile().toURI().toString(),selectedFile.getName(),selectedFile.getAbsoluteFile().toString());
            button.setOnMouseClicked(this::listenSound);
            button.setOnDragDone(this::onDragDone);
            button.setOnDragDetected(this::onDragDetected);
            soundZone.getChildren().add(button);
        }
        actionEvent.consume();
    }

    @FXML
    Button deleteSound;
    @FXML
    HBox soundZone;
    @FXML
    public void listenSound(MouseEvent onClicked) {

        if(onClicked.getButton().equals(MouseButton.PRIMARY)){
            MediaPlayer player = new MediaPlayer(new Media(((BCButton)onClicked.getSource()).associatedFile));
            player.play();
        }
        else{
            BCButton b = new BCButton(((BCButton)onClicked.getSource()).associatedFile,((BCButton)onClicked.getSource()).getText(),((BCButton)onClicked.getSource()).associatedFileURL);
            b.setOnMouseClicked(this::listenSound);
            dropZone.getChildren().add(b);
        }
    }

    @FXML
    public void deleteSound(MouseEvent onClicked) {
        dropZone.getChildren().remove(0);
        System.out.println("kikou");
        dropZone.getChildren().remove(0); //marche uniquement pour le premier pour l'instant
    }

    @FXML
    public void onDragDetected(MouseEvent onDragDetected) {
        /* drag was detected, start drag-and-drop gesture*/
        ClipboardContent content = new ClipboardContent();
        content.putString(((BCButton)onDragDetected.getSource()).getText());
        Dragboard db = ((BCButton)onDragDetected.getSource()).startDragAndDrop(TransferMode.ANY);
        db.setContent(content);
        onDragDetected.consume();
    }

    @FXML
    public void onDragOver(DragEvent onDragOver) {
        /* data is dragged over the target */
        /* accept it only if it isn't dragged from the same node and if it has a string data */
        if (onDragOver.getGestureSource() != dropZone && onDragOver.getDragboard().hasString()) {
            /* allow for both copying and moving, whatever user chooses */
            onDragOver.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        onDragOver.consume();
    }

    @FXML
    public void onDragEntered(DragEvent onDragEntered) {
        /* the drag-and-drop gesture entered the target */
        /* show to the user that it is an actual gesture target */
        if (onDragEntered.getGestureSource() != dropZone && onDragEntered.getDragboard().hasString()) {
            //do nothing
        }
        onDragEntered.consume();
    }

    @FXML
    public void onDragExited(DragEvent onDragExited) {
        // mouse moved away, do nothing
        onDragExited.consume();
    }

    @FXML
    public void onDragDropped(DragEvent onDragDropped) {
        /* data dropped */
        /* if there is a string data on dragboard, read it and use it */
        ObservableList<Node> nodes =  dropZone.getChildren();
        Dragboard db = onDragDropped.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            success = true;
        }
        /* let the source know whether the string was successfully transferred and used */
        onDragDropped.setDropCompleted(success);
        onDragDropped.consume();
    }

    @FXML
    public void onDragDone(DragEvent onDragDone) {
        /* the drag-and-drop gesture ended */
        /* if the data was successfully moved, clear it */
        if (onDragDone.getTransferMode() == TransferMode.MOVE && onDragDone.getDragboard().hasString()) {
            BCButton target = new BCButton(((BCButton)onDragDone.getSource()).associatedFile,((BCButton)onDragDone.getSource()).getText());
            dropZone.getChildren().add(target);
        }
        onDragDone.consume();
    }

    public void initPlugin(){
        this.jarLoader.downloadPlugins();
        this.jarLoader.loadPlugins();
        ObservableList<String> options = FXCollections.observableArrayList();

        for( String key : this.jarLoader.pluginsKeys){
            options.add(key);
        }
        this.pluginId.setItems(options);
    }
}
