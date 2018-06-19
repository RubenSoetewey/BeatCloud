package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.beans.InvalidationListener;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.event.*;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer.Status;

import java.io.File;

public class Controller extends BorderPane{
    @FXML
    Stage primaryStage;

    //Instruments
    @FXML
    MenuButton pianoButton;
    @FXML
    public void handlePiano(ActionEvent event){System.out.println("ok piano"); }

    @FXML
    MenuItem doPiano;
    @FXML
    public void handleDoPiano(ActionEvent event){
        System.out.println("doPiano ok");
    }

    @FXML
    MenuItem rePiano;
    @FXML
    public void handleRePiano(ActionEvent event){
        System.out.println("rePiano ok");
    }

    @FXML
    MenuItem miPiano;
    @FXML
    public void handleMiPiano(ActionEvent event){
        System.out.println("miPiano ok");
    }

    @FXML
    MenuItem faPiano;
    @FXML
    public void handleFaPiano(ActionEvent event){
        System.out.println("faPiano ok");
    }

    @FXML
    MenuItem solPiano;
    @FXML
    public void handleSolPiano(ActionEvent event){
        System.out.println("solPiano ok");
    }
    @FXML
    MenuItem laPiano;
    @FXML
    public void handleLaPiano(ActionEvent event){
        System.out.println("laPiano ok");
    }

    @FXML
    MenuItem siPiano;
    @FXML
    public void handleSiPiano(ActionEvent event){
        System.out.println("siPiano ok");
    }

    @FXML
    MenuButton drumsButton;
    @FXML
    public void handleDrums(ActionEvent event){
        System.out.println("ok drums");
    }

    @FXML
    MenuItem doDrums;
    @FXML
    public void handleDoDrums(ActionEvent event){
        System.out.println("doDrums ok");
    }

    @FXML
    MenuItem reDrums;
    @FXML
    public void handleReDrums(ActionEvent event){
        System.out.println("reDrums ok");
    }

    @FXML
    MenuItem miDrums;
    @FXML
    public void handleMiDrums(ActionEvent event){
        System.out.println("miDrums ok");
    }

    @FXML
    MenuItem faDrums;
    @FXML
    public void handleFaDrums(ActionEvent event){
        System.out.println("faDrums ok");
    }

    @FXML
    MenuItem solDrums;
    @FXML
    public void handleSolDrums(ActionEvent event){
        System.out.println("solDrums ok");
    }
    @FXML
    MenuItem laDrums;
    @FXML
    public void handleLaDrums(ActionEvent event){
        System.out.println("laDrums ok");
    }

    @FXML
    MenuItem siDrums;
    @FXML
    public void handleSiDrums(ActionEvent event){
        System.out.println("siDrums ok");
    }

    @FXML
    MenuButton guitarButton;
    @FXML
    public void handleGuitar(ActionEvent event){
        System.out.println("ok guitar");
    }

    @FXML
    MenuItem doGuitar;
    @FXML
    public void handleDoGuitar(ActionEvent event){
        System.out.println("doGuitar ok");
    }

    @FXML
    MenuItem reGuitar;
    @FXML
    public void handleReGuitar(ActionEvent event){
        System.out.println("reGuitar ok");
    }

    @FXML
    MenuItem miGuitar;
    @FXML
    public void handleMiGuitar(ActionEvent event){
        System.out.println("miGuitar ok");
    }

    @FXML
    MenuItem faGuitar;
    @FXML
    public void handleFaGuitar(ActionEvent event){
        System.out.println("faGuitar ok");
    }

    @FXML
    MenuItem solGuitar;
    @FXML
    public void handleSolGuitar(ActionEvent event){
        System.out.println("solGuitar ok");
    }

    @FXML
    MenuItem laGuitar;
    @FXML
    public void handleLaGuitar(ActionEvent event){
        System.out.println("laGuitar ok");
    }

    @FXML
    MenuItem siGuitar;
    @FXML
    public void handleSiGuitar(ActionEvent event){
        System.out.println("siGuitar ok");
    }

    @FXML
    MenuButton violinButton;
    @FXML
    public void handleViolin(ActionEvent event){
        System.out.println("ok violin");
    }

    @FXML
    MenuItem doViolin;
    @FXML
    public void handleDoViolin(ActionEvent event){
        System.out.println("doViolin ok");
    }

    @FXML
    MenuItem reViolin;
    @FXML
    public void handleReViolin(ActionEvent event){
        System.out.println("reViolin ok");
    }

    @FXML
    MenuItem miViolin;
    @FXML
    public void handleMiViolin(ActionEvent event){
        System.out.println("miViolin ok");
    }

    @FXML
    MenuItem faViolin;
    @FXML
    public void handleFaViolin(ActionEvent event){
        System.out.println("faViolin ok");
    }

    @FXML
    MenuItem solViolin;
    @FXML
    public void handleSolViolin(ActionEvent event){
        System.out.println("solViolin ok");
    }
    @FXML
    MenuItem laViolin;
    @FXML
    public void handleLaViolin(ActionEvent event){
        System.out.println("laViolin ok");
    }

    @FXML
    MenuItem siViolin;
    @FXML
    public void handleSiViolin(ActionEvent event){
        System.out.println("siViolin ok");
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

    //Media
    @FXML
    MediaPlayer player;
    @FXML
    MediaView mediaView;
    @FXML
    Label spacer;
    @FXML
    Image play;
    @FXML
    Image pause;
    final boolean repeat = false;
    boolean stopRequested = false;
    boolean atEndOfMedia = false;
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
    HBox mediaBar;
    @FXML
    Button playButton;
    @FXML
    public void handlePlay(ActionEvent event){
        Status status = player.getStatus();
        if(status == Status.UNKNOWN || status == Status.HALTED){
            return;
        }
        if (status == Status.PAUSED
                || status == Status.READY)
        {
            // rewind the movie if we're sitting at the end
            if (atEndOfMedia) {
                player.seek(player.getStartTime());
                atEndOfMedia = false;
            }
            player.play();
        } else {
            player.pause();
        }
    }

    @FXML
    Button stopButton;
    @FXML
    public void handleStop(ActionEvent event){
        Status status = player.getStatus();
        if (status == Status.UNKNOWN || status == Status.HALTED) {
            // don't do anything in these states
            player.play();
            return;
        }
        if (status == Status.STOPPED)
        {
            // rewind the movie if we're sitting at the end
            if (atEndOfMedia) {
                player.seek(player.getStartTime());
                atEndOfMedia = false;
            }
            player.play();
        } else {
            player.stop();
            playButton.setGraphic(new ImageView(play));
            player.pause();
            if(playButton.isPressed()){
                player.play();
            }
        }
    }

    public void Controller(final MediaPlayer player) {
        this.player = player;
        setStyle("-fx-background-color: white;");
        mediaView = new MediaView(player);
        mvPane = new Pane(){                };
        mvPane.getChildren().add(mediaView);
        setCenter(mvPane);

        Image pause = new Image(getClass().getResource("Photo/pause.jpg").toExternalForm(), 20, 20, true, true);

        player.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {updateValues();}});

        player.setOnPlaying(new Runnable() {
            @Override
            public void run() {
                if(stopRequested){
                    player.pause();
                    stopRequested = false;
                }else{
                    playButton.setGraphic(new ImageView(pause));
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
    HBox.setHgrow(timeSlider, Priority.ALWAYS);
    timeSlider.setMinWidth(50);
    timeSlider.setMaxWidth(Double.MAX_VALUE);
    timeSlider.valueProperty().addListener(new InvalidationListener() {
        public void invalidated(Observable ov) {
            if (timeSlider.isValueChanging()) {
                // multiply duration by percentage calculated by slider position
                player.seek(duration.multiply(timeSlider.getValue() / 100.0));
            }
        }
    });

    // Add Volume slider
    volumeSlider.setPrefWidth(70);
    volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
    volumeSlider.setMinWidth(30);
    volumeSlider.valueProperty().addListener(new InvalidationListener() {
        public void invalidated(Observable ov) {
            if (volumeSlider.isValueChanging()) {
                player.setVolume(volumeSlider.getValue() / 100.0);
            }
        }
    });
}

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int)Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int)Math.floor(duration.toSeconds());
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
                    Duration currentTime = player.getCurrentTime();
                    playTime.setText(formatTime(currentTime, duration));
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int)Math.round(player.getVolume() * 100));
                    }
                }
            });
        }
    }
}
