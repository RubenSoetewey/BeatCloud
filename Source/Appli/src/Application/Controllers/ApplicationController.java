package Application.Controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import Application.Util.BCButton;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;
import static Application.Util.SoundMixer.concatWav;
public class ApplicationController extends BorderPane {
    @FXML
    Stage primaryStage;

    /////////////////////////////////////////////////Files///////////////////////////////////////
    @FXML
    MenuItem newFile;

    @FXML
    public void createNewFile(ActionEvent event) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Create new file");
        fileChoosed.setInitialDirectory(new File("./src/Application/Music"));
        fileChoosed.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("WAV Files", "*.wav"));
        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        File selectedFile = fileChoosed.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            System.out.println(selectedFile);
        }
        event.consume();
    }

    @FXML
    MenuItem openFile;

    @FXML
    public void openFile(ActionEvent event) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Open file");
        fileChoosed.setInitialDirectory(new File("./src/Application/Music"));
        fileChoosed.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        File selectedFile = fileChoosed.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            System.out.println(selectedFile);
        }
        event.consume();
    }

    @FXML
    MenuItem saveFile;

    @FXML
    public void saveFile(ActionEvent event) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Save File");
        fileChoosed.setInitialDirectory(new File("./src/Application/Music"));
        fileChoosed.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
        saveFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN)); //Crée une erreur NullPointeur
        File selectedFile = fileChoosed.showSaveDialog(primaryStage);
        if (selectedFile != null) {
            System.out.println(selectedFile);
        }
        event.consume();
    }
    @FXML
    MediaView mainView;
    @FXML
    MediaPlayer mainPlayer;
    @FXML
    Image play;
    @FXML
    Duration duration;
    @FXML
    Slider timeSlider;
    @FXML
    Label playTime;
    @FXML
    Slider volumeSlider;
    @FXML
    Pane mvPane;
    @FXML
    Pane musicPane;
    @FXML
    HBox mediaBar;
    @FXML
    Button playButton;
    @FXML
    Button stopButton;
    final boolean repeat = false;
    boolean stopRequested = false;
    boolean atEndOfMedia = false;
    private FadeTransition ft;
    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds, durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes, durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
            }
        }
    }

    protected void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = mainPlayer.getCurrentTime();
                    playTime.setText(formatTime(currentTime, duration));
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int) Math.round(mainPlayer.getVolume() * 100));
                    }
                }
            });
        }
    }

    @FXML
    public void handlePlay(ActionEvent onMouseClicked) {
        Status status = mainPlayer.getStatus();
        if (status == Status.UNKNOWN || status == Status.HALTED) {
            return;
        }
        if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
            // rewind the movie if we're sitting at the end
            if (atEndOfMedia) {
                mainPlayer.seek(mainPlayer.getStartTime());
                atEndOfMedia = false;
            }
            mainPlayer.play();

        } else {
            mainPlayer.pause();
        }
        onMouseClicked.consume();
    }

    @FXML
    public void handleStop(ActionEvent onMouseClicked) {
        Status status = mainPlayer.getStatus();
        if (status == Status.UNKNOWN || status == Status.HALTED) {
            // don't do anything in these states
            mainPlayer.play();
            return;
        }
        if (status == Status.STOPPED) {
            // rewind the movie if we're sitting at the end
            if (atEndOfMedia) {
                mainPlayer.seek(mainPlayer.getStartTime());
                atEndOfMedia = false;
            }
            mainPlayer.play();
        } else {
            mainPlayer.stop();
            if (playButton.isPressed()) {
                mainPlayer.play();
            }
        }
        onMouseClicked.consume();
    }

    @FXML
    HBox dropZone;
    @FXML
    Button validMusic;
    @FXML
    public void validMusicEnter (MouseEvent onMouseEntered) { validMusic.setStyle("-fx-background-color: green"); }
    @FXML
    private void validMusicExit (MouseEvent onMouseExited) { validMusic.setStyle("-fx-background-color: darkturquoise"); }

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    Button deleteSound;

    @FXML
    HBox soundZone;

    @FXML
    Media guitarMedia;

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

    public void importFile(ActionEvent actionEvent) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Open file");
        fileChoosed.setInitialDirectory(new File("./src/Application/Music"));
        fileChoosed.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Audio Files", "*.wav"));
        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
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
}
