package Application.Controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Application.Util.BCButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.beans.InvalidationListener;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import Application.Util.SoundMixer;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import static Application.Util.SoundMixer.concatWav;

public class ApplicationController extends BorderPane implements Initializable {
    @FXML
    Stage primaryStage;

    /////////////////////////////////////////////////Files///////////////////////////////////////
    @FXML
    MenuItem newFile;

    @FXML
    public void createNewFile(ActionEvent event) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Create new file");
        fileChoosed.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop")); //position on desktop
        fileChoosed.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("WAV Files", "*.wav"));
        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        File selectedFile = fileChoosed.showSaveDialog(primaryStage);
        if (selectedFile != null) {
            System.out.println(selectedFile);
            //List<File> files = Arrays.asList(selectedFile);
            //mainView.setMediaPlayer(new Media(files.add(selectedFile)));
            //openFile(selectedFile);
            //System.out.println("You chose to open this file: " + fileChoosed.getTitle());//.getSelectedFile().getName());
            //mainView.setMediaPlayer(new Media(selectedFile.toURI().toString()));
        }
        event.consume();
    }

    @FXML
    MenuItem openFile;

    @FXML
    public void openFile(final ActionEvent event) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Open file");
        fileChoosed.setInitialDirectory(new File("./"));
        fileChoosed.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        File selectedFile = fileChoosed.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            System.out.println(selectedFile);
            //openFile(selectedFile);
            //System.out.println("You chose to open this file: " + fileChoosed.getTitle());//.getSelectedFile().getName());
            //mainStage.display(selectedFile);
        }
        event.consume();
    }

    @FXML
    MenuItem saveFile;

    @FXML
    public void saveFile(ActionEvent event) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Save File");
        fileChoosed.setInitialDirectory(new File("./"));
        fileChoosed.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
        //saveFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN)); //Crée une erreur NullPointeur
        File selectedFile = fileChoosed.showSaveDialog(primaryStage);
        if (selectedFile != null) {
            System.out.println(selectedFile);
            //openFile(selectedFile);
            //System.out.println("You chose to open this file: " + fileChoosed.getTitle());//.getSelectedFile().getName());
            //mainStage.display(selectedFile);
        }
        event.consume();
    }

    /////////////////////////////////////////////Media////////////////////////////////////////
    @FXML
    MediaView mainView;
    @FXML
    MediaPlayer mainPlayer;
    @FXML
    Media mainMedia;
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

    public void setMainPlayer(MediaPlayer mediaPlayer) {
        this.mainPlayer = mediaPlayer;

        mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);

        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateValues();
            }
        });

        mediaPlayer.setOnPlaying(new Runnable() {
            @Override
            public void run() {
                if (stopRequested) {
                    mainPlayer.pause();
                    stopRequested = false;
                } else {
                    playButton.setGraphic(new ImageView("@../Photo/pause.jpg"));
                }
            }
        });

        mediaPlayer.setOnPaused(new Runnable() {
            @Override
            public void run() {
                System.out.println("onPaused");
                playButton.setGraphic(new ImageView(play));
            }
        });

        mediaPlayer.setOnReady(new Runnable() {
            public void run() {
                duration = mainPlayer.getMedia().getDuration();
                updateValues();
            }
        });

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (!repeat) {
                    playButton.setGraphic(new ImageView(play));
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
        });

        // Add time slider
//        mediaBar.setHgrow(timeSlider, Priority.ALWAYS);
  //      timeSlider.setMaxWidth(Double.MAX_VALUE);
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    mainPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            }
        });

        // Add Volume slider
    //    volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    mainPlayer.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });
    }

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

    /*public void ApplicationController(MediaPlayer player) {
        this.mainPlayer = player;
        mainView = new MediaView(mainPlayer);
        musicPane = new Pane() {        };
        musicPane.getChildren().add(mainView);*/

    //Image pause = new Image(getClass().getResource("Photo/pause.jpg").toExternalForm(), 20, 20, true, true);

        /*player.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });*/

        /*player.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mainPlayer.pause();
                    stopRequested = false;
                } else {
                    playButton.setGraphic(new ImageView("@../Photo/pause.jpg"));
                }
            }
        });
        player.setOnPaused(new Runnable() {
            public void run() {
                System.out.println("onPaused");
                playButton.setGraphic(new ImageView(play));
            }
        });
        player.setOnReady(new Runnable() {
            public void run() {
                duration = mainPlayer.getMedia().getDuration();
                updateValues();
            }
        });*/

    //player.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);

        /*player.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    playButton.setGraphic(new ImageView(play));
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
        });*/

    // Add time slider
    //mediaBar.setHgrow(timeSlider, Priority.ALWAYS);
    //timeSlider.setMinWidth(50);
    //timeSlider.setMaxWidth(Double.MAX_VALUE);
        /*timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    mainPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            }
        });*/

    // Add Volume slider
    //volumeSlider.setPrefWidth(70);
        /*volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        //volumeSlider.setMinWidth(30);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    mainPlayer.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });
        setBottom(mediaBar);
    }*/

    /*private static String formatTime(Duration elapsed, Duration duration) {
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
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ft = new FadeTransition(Duration.millis(2000), mediaBar);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);

        setMainPlayer(mainPlayer);

        /*selectedMedia.addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                playVideo(newValue.toString());
            }
        });

        deletedMedia.addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                stopAction(null);
            }
        });*/
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
            //playButton.setGraphic(new ImageView("@../Photo/play.jpg"));
            mainPlayer.play();

        } else {
            //playButton.setGraphic(new ImageView("@../Photo/pause.jpg"));
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

    /*public void faster(ActionEvent onMouseClicked) {
        mainPlayer.setRate(2);
    }

    public void slower(ActionEvent onMouseClicked) {
        mainPlayer.setRate(0.5);
    }

    public void reload(ActionEvent onMouseClicked) {
        mainPlayer.seek(mainPlayer.getStartTime());
        mainPlayer.play();
    }

    public void startDuration(ActionEvent event) {
        mainPlayer.seek(mainPlayer.getStartTime());
        mainPlayer.stop();
    }

    public void endOfMainPlayer(ActionEvent event) {
        mainPlayer.seek(mainPlayer.getTotalDuration());
        mainPlayer.stop();
    }*/

    /*@FXML
    public void onPlaying(Runnable run) {
        if(stopRequested){
            mainPlayer.pause();
            stopRequested = false;
        }else{
            //Image pause = new Image(getClass().getResource("Photo/pause.jpg").toExternalForm(), 20, 20, true, true);
            playButton.setGraphic(new ImageView("@../Photo/pause.jpg"));
        }
    };*/


/*    @FXML
    public void paused(MediaPlayer mainPlayer) {
        System.out.println("onPaused");
        playButton.setGraphic(new ImageView(play));
    };

    @FXML
    public void ready(MediaPlayer mainPlayer) {
        duration = mainPlayer.getMedia().getDuration();
        updateValues();
    };
*/

    /*public void ApplicationController(final MediaPlayer player) {
        this.mainPlayer = player;
        setStyle("-fx-background-color: white;");
        mainView = new MediaView(player);
        musicPane = new Pane(){                };
        musicPane.getChildren().add(mainView);
        setCenter(musicPane);

        Image pause = new Image(getClass().getResource("Photo/pause.jpg").toExternalForm(), 20, 20, true, true);

        mainPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {updateValues();}});

        mainPlayer.setOnPlaying(new Runnable() {
            @Override
            public void run() {
                if(stopRequested){
                    mainPlayer.pause();
                    stopRequested = false;
                }else{
                    playButton.setGraphic(new ImageView(pause));
                }
            }
        });

        mainPlayer.setOnPaused(new Runnable() {
            public void run() {
                System.out.println("onPaused");
                playButton.setGraphic(new ImageView(play));
            }
        });

        player.setOnReady(new Runnable() {
            public void run() {
                duration = player.getMedia().getDuration();
                updateValues();
            }
        });

        player.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        player.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    playButton.setGraphic(new ImageView(play));
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
        });

        // Add time slider
        timeSlider.setMaxWidth(Double.MAX_VALUE);
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    mainPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            }
        });

        // Add Volume slider
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    mainPlayer.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });
    }
    */

    @FXML
    HBox dropZone;
    @FXML
    Button validMusic;
    @FXML
<<<<<<< HEAD
=======
    public void validMusicEnter (MouseEvent onMouseEntered) { validMusic.setStyle("-fx-background-color: green"); }
    @FXML
    private void validMusicExit (MouseEvent onMouseExited) { validMusic.setStyle("-fx-background-color: darkturquoise"); }

    @FXML
    private void validMusicCliqued (MouseEvent onMouseCliqued){
        Vector<String> allFiles = new Vector<>();
        for (Node n: ( dropZone.getChildren())) {
            try{
                BCButton b = (BCButton)n;
                allFiles.add(b.associatedFile);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        try {
            concatWav("Output.wav",allFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    Button deleteSound;

    @FXML
    HBox soundZone;

    @FXML
    Button pianoTest;
    @FXML
    MediaPlayer pianoPlayer;
    @FXML
    Media pianoMedia;

    //@FXML
    //BCButton guitarTest;
    //@FXML
    //MediaPlayer guitarPlayer;
    @FXML
    Media guitarMedia;

    @FXML
    public void listenSound(MouseEvent onClicked) {
        /*if (pianoTest.isFocused() == true) {
        if (pianoTest.isFocused() == true) {
            pianoPlayer = new MediaPlayer(pianoMedia);
            pianoPlayer.play();
        }
        else if(guitarTest.isFocused() == true) {
            guitarPlayer = new MediaPlayer(guitarMedia);
            guitarPlayer.play();
        }*/

        MediaPlayer player = new MediaPlayer(new Media(((BCButton)onClicked.getSource()).associatedFile));
        player.play();
    }

    @FXML
    public void deleteSound(MouseEvent onClicked) {
        //dropZone.getChildren().remove(0)
        System.out.println("kikou");
        dropZone.getChildren().remove(0); //marche uniquement pour le premier pour l'instant
    }

    @FXML
    public void onDragDetected(MouseEvent onDragDetected) {
        /* drag was detected, start drag-and-drop gesture*/
        ClipboardContent content = new ClipboardContent();
       /* content.putString(pianoTest.getText());
        if (pianoTest.isFocused() == true) {
            Dragboard db = pianoTest.startDragAndDrop(TransferMode.ANY); //allow any transfer mode
            db.setContent(content);
        }
        else if (guitarTest.isFocused() == true) {
            Dragboard db = guitarTest.startDragAndDrop(TransferMode.ANY);
            db.setContent(content);
        }*/

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
           /* if (pianoTest.isFocused() == true) {
                Button target = new Button(pianoTest.getText());
                dropZone.getChildren().add(target);
            }

            else if (guitarTest.isFocused() == true) {
                Button target = new Button(guitarTest.getText());
                dropZone.getChildren().add(target);
            }*/
            BCButton target = new BCButton(((BCButton)onDragDone.getSource()).associatedFile,((BCButton)onDragDone.getSource()).getText());
            dropZone.getChildren().add(target);
        }
        onDragDone.consume();
    }

    public void importFile(ActionEvent actionEvent) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Open file");
        fileChoosed.setInitialDirectory(new File("./"));
        fileChoosed.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Audio Files", "*.wav"));
        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
        File selectedFile = fileChoosed.showOpenDialog(primaryStage);
        if (selectedFile != null) {
           BCButton button =  new BCButton(selectedFile.getAbsoluteFile().toURI().toString(),selectedFile.getName());
           button.setOnMouseClicked(this::listenSound);
           button.setOnDragDone(this::onDragDone);
           button.setOnDragDetected(this::onDragDetected);
           soundZone.getChildren().add(button);
        }
        actionEvent.consume();
    }
}
