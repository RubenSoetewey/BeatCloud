package Application.Controllers;

import Application.Controllers.MediaController;
import Application.Controllers.FileController;

import Application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
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
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.event.*;
import javafx.scene.media.MediaPlayer.Status;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.xml.transform.Source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class ApplicationController extends BorderPane {
    @FXML
    Stage primaryStage;

    //Files
    @FXML
    MenuItem newFile;

    @FXML
    public void createNewFile(ActionEvent event) {
        FileChooser fileChoosed = new FileChooser();
        fileChoosed.setTitle("Create new file");
        fileChoosed.setInitialDirectory(new File("./"));
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
    }

    //Media
    @FXML
    MediaView mainView;
    @FXML
    MediaPlayer mainPlayer;
    @FXML
    Media mainMedia;
    @FXML
    Image play;
    @FXML
    Image pause;
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

    public void ApplicationController(/*final MediaPlayer player*/) {
        //this.mainPlayer = player;
        setStyle("-fx-background-color: white;");
        mainView = new MediaView(mainPlayer);
        musicPane = new Pane() {
        };
        musicPane.getChildren().add(mainView);
        musicPane.setStyle("-fx-background-color: black;");
        setCenter(musicPane);
        //mediaBar = new HBox();
        //mediaBar.setAlignment(Pos.CENTER);
        //mediaBar.setPadding(new Insets(5, 10, 5, 10));
        //BorderPane.setAlignment(mediaBar, Pos.CENTER);

        Image pause = new Image(getClass().getResource("Photo/pause.jpg").toExternalForm(), 20, 20, true, true);

        mainPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });

        mainPlayer.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mainPlayer.pause();
                    stopRequested = false;
                } else {
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
        mainPlayer.setOnReady(new Runnable() {
            public void run() {
                duration = mainPlayer.getMedia().getDuration();
                updateValues();
            }
        });

        mainPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mainPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    playButton.setGraphic(new ImageView(play));
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
        });

        // Add time slider
        mediaBar.setHgrow(timeSlider, Priority.ALWAYS);
        //timeSlider.setMinWidth(50);
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
        //volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        //volumeSlider.setMinWidth(30);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    mainPlayer.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });
        setBottom(mediaBar);
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
            //playButton.setGraphic(new ImageView(play));
            mainPlayer.play();
        } else {
            //playButton.setGraphic(new ImageView(pause));
            mainPlayer.pause();
        }
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
    }

 /*
    @FXML
    public void playing() {
        if(stopRequested){
            mainPlayer.pause();
            stopRequested = false;
        }else{
            Image pause = new Image(getClass().getResource("Photo/pause.jpg").toExternalForm(), 20, 20, true, true);
            playButton.setGraphic(new ImageView(pause));
        }
    };


    @FXML
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
                    Duration currentTime = mainPlayer.getCurrentTime();
                    playTime.setText(formatTime(currentTime, duration));
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int)Math.round(mainPlayer.getVolume() * 100));
                    }
                }
            });
        }
    }*/

    //Instruments
    @FXML
    MenuButton pianoButton;
    @FXML
    Button pianoTest;
    @FXML
    HBox dropZone;

    /*Button target = new Button(32, 32, "DROP HERE");
        target.setScaleX(2.0);
        target.setScaleY(2.0);
    */

    public void onDragDetected(MouseEvent onDragDetected) {
        /* drag was detected, start drag-and-drop gesture*/
        System.out.println("onDragDetected");
                /* allow any transfer mode */
        Dragboard db = pianoTest.startDragAndDrop(TransferMode.ANY);
                /* put a string on dragboard */
        ClipboardContent content = new ClipboardContent();
        content.putString(pianoTest.getText());
        db.setContent(content);

        onDragDetected.consume();
    }

    public void onDragOver(DragEvent onDragOver) {
        /* data is dragged over the target */
        System.out.println("onDragOver");
        /* accept it only if it is  not dragged from the same node and if it has a string data */
        if (onDragOver.getGestureSource() != dropZone && onDragOver.getDragboard().hasString()) {
            /* allow for both copying and moving, whatever user chooses */
            onDragOver.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        onDragOver.consume();
    }

    public void onDragEntered(DragEvent onDragEntered) {
        /* the drag-and-drop gesture entered the target */
        System.out.println("onDragEntered");
        /* show to the user that it is an actual gesture target */
        if (onDragEntered.getGestureSource() != dropZone && onDragEntered.getDragboard().hasString()) {
            //dropZone.setFill(Color.GREEN);
            System.out.println("DragEntered success");
        }
        onDragEntered.consume();
    }

    public void onDragExited(DragEvent onDragExited) {
        /* mouse moved away, remove the graphical cues */
        System.out.println("DragExited");
        onDragExited.consume();
    }

    public void onDragDropped(DragEvent onDragDropped) {
        /* data dropped */
        System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
        Dragboard db = onDragDropped.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            System.out.println("Drag Dropped success");
            success = true;
        }
        /* let the source know whether the string was successfully transferred and used */
        onDragDropped.setDropCompleted(success);
        onDragDropped.consume();
    }

    public void onDragDone(DragEvent onDragDone) {
        /* the drag-and-drop gesture ended */
        System.out.println("onDragDone");
        /* if the data was successfully moved, clear it */
        if (onDragDone.getTransferMode() == TransferMode.MOVE) {
            //pianoTest.setText("");
            Button target = new Button(pianoTest.getText());
            dropZone.getChildren().add(target);
        }
        onDragDone.consume();
    }
}

    /*public void addSoundButton(Button b, HBox hbox) {
        hbox.getChildren().add(b);
    }

    @FXML
    private void initialize() {
        buildNodeDragHandlers();
    }

    private EventHandler mContextDragOver;
    private EventHandler mContextDragDropped;

    public void buildNodeDragHandlers() {

        //drag detection for node dragging
        pianoTest.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                getParent().setOnDragOver(null);
                getParent().setOnDragDropped(null);

                getParent().setOnDragOver(mContextDragOver);
                getParent().setOnDragDropped(mContextDragDropped);

                //begin drag ops
                mDragOffset = new Point2D(event.getX(), event.getY());

                relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

                ClipboardContent content = new ClipboardContent();
                DragContainer container = new DragContainer();

                container.addData("type", mType.toString());
                content.put(DragContainer.DragNode, container);

                startDragAndDrop(TransferMode.ANY).setContent(content);

                event.consume();
            }

        });
    }*/

    /*@FXML
    public void onMousePressed(MouseEvent onMousePressed) {
        pianoTest.setMouseTransparent(true);
        onMousePressed.setDragDetect(true);
        System.out.println("onMousePressed");
    }

    @FXML
    public void onMouseReleased(MouseEvent onMouseReleased) {
        pianoTest.setMouseTransparent(false);
        System.out.println("onMouseReleased");
    }*/

    /*@FXML
    public void onDragDetected(MouseEvent onDragDetected) {
        //String text = pianoTest.getText();
        if(text == null || text.trim().equals("")){
            onDragDetected.consume();
            return;
        }
        Dragboard dragboard = pianoTest.startDragAndDrop(TransferMode.COPY_OR_MOVE);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(text);
        dragboard.setContent(clipboardContent);
        System.out.println("onDragDetected");
        // drag was detected, start a drag-and-drop gesture
        // allow any transfer mode
        Dragboard db = pianoTest.startDragAndDrop(TransferMode.ANY);
        // Put a string on a dragboard
        ClipboardContent content = new ClipboardContent();
        content.putString(pianoTest.getText());
        db.setContent(content);
        System.out.println(pianoTest.getText());
        System.out.println("onDragDetected");

        onDragDetected.consume();
    }*/

    /*
    @FXML
    public void onMouseDragEntered(MouseDragEvent onMouseDragEntered) {
        System.out.println("onDragEntered");
    }*/

    /*@FXML
    public void onDragOver(DragEvent onDragOver) {
        // data is dragged over the target
        // accept it only if it is not dragged from the same node
        // and if it has a string data
        if (onDragOver.getGestureSource() != target && onDragOver.getDragboard().hasString()) {
            // allow for both copying and moving, whatever user chooses
            onDragOver.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        System.out.println("onDragOver");
        Dragboard dragboard = onDragOver.getDragboard();
        if (dragboard.hasString()) {
            onDragOver.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            System.out.println("onDragOver");
        }
        onDragOver.consume();
    }*/

    /*@FXML
    public void onMouseDragReleased(MouseDragEvent onMouseDragReleased) {
        Button target = new Button();
        target.setText(pianoTest.getText());
        System.out.println("onMouseDragReleased");
    }*/

    //@FXML
    //private void onDragDropped(DragEvent onDragDropped) {
        /*// Transfer the data to the target
        Dragboard dragboard = onDragDropped.getDragboard();
        if (dragboard.hasString())
        {
            //Button target = new Button();
            target.setVisible(true);
            target.setText(dragboard.getString());
            // Data transfer is successful
            onDragDropped.setDropCompleted(true);
            //System.out.println("onDragDropped");
        }
        else
        {
            // Data transfer is not successful
            onDragDropped.setDropCompleted(false);
        }*/

         /* data dropped */
        /* if there is a string data on dragboard, read it and use it */

        /*Dragboard db = onDragDropped.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            dropZone.getChildren().add(target);
            target.setText(db.getString());
            success = true;
        }
        // let the source know whether the string was successfully
        // transferred and used
        onDragDropped.setDropCompleted(success);
        System.out.println("onDragDropped");

        onDragDropped.consume();*/
    //}

    /*@FXML
    private void onDragDone(DragEvent onDragDone) {
        /// Check how data was transfered to the target. If it was moved, clear the text in the source.
        TransferMode modeUsed = onDragDone.getTransferMode();
        if (modeUsed == TransferMode.MOVE)
        {
            pianoTest.setText("");
        }

        // the drag and drop gesture ended
        // if the data was successfully moved, clear it
        if (onDragDone.getTransferMode() == TransferMode.MOVE) {
            pianoTest.setText("");
        }
        System.out.println("onDragDone");
        onDragDone.consume();
    }*/

    /*@FXML
    public void onMouseDragged(MouseEvent event) {
        pianoTest.setLayoutX(event.getSceneX());
        pianoTest.setLayoutY(event.getSceneY());
    }*/

    /*@FXML
    public void onMouseDragExited(MouseDragEvent onMouseDragExited) {
        Button target = new Button();
        target.setOnMouseDragExited(new EventHandler <MouseDragEvent>()
        {
            public void handle(MouseDragEvent event)
            {
                System.out.println("onMouseDragExited");
            }
    });*/


    /*@FXML
    public void onDragDetected(MouseEvent onDragDetected) {
        System.out.println("onDragDetected");
        Dragboard dragboard = pianoButton.startDragAndDrop(TransferMode.COPY);
        dragboard.setDragView(pianoButton.snapshot(null, null));
        ClipboardContent content = new ClipboardContent();
        //content.putImage()
        dragboard.setContent(content);

        onDragDetected.consume();
    }

    @FXML
    public void onDragOver(DragEvent onDragOver) {
        System.out.println("onDragOver");
        if(onDragOver.getGestureSource() != musicPane && onDragOver.getDragboard().hasImage()) {
            onDragOver.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        onDragOver.consume();
    }

    @FXML
    public void onDragEntered(DragEvent onDragEntered) {
        System.out.println("onDragEntered");
        if(onDragEntered.getGestureSource() != musicPane && onDragEntered.getDragboard().hasImage()) {
            //mvPane.set
        }
    }*/

    /*double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    @FXML
    public void onMousePressed(MouseEvent onMousePressed) {
        //pianoButton.startDragAndDrop(TransferMode.COPY);
        orgSceneX = onMousePressed.getSceneX();
        orgSceneY = onMousePressed.getSceneY();
        orgTranslateX = ((MenuButton) (onMousePressed.getSource())).getTranslateX();
        orgTranslateY = ((MenuButton) (onMousePressed.getSource())).getTranslateY();

        ((MenuButton) (onMousePressed.getSource())).toFront();
    }

    @FXML
    public void onMouseDragged(MouseEvent onMouseDragged) {
        double offsetX = onMouseDragged.getSceneX() - orgSceneX;
        double offsetY = onMouseDragged.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        ((MenuButton) (onMouseDragged.getSource())).setTranslateX(newTranslateX);
        ((MenuButton) (onMouseDragged.getSource())).setTranslateY(newTranslateY);
    }*/

    /*@FXML
    MenuItem doPiano;
    @FXML
    public void handleDoPiano(ActionEvent event){
        System.out.println("doPiano ok");
        String sound = "C:\\Users\\Pauline\\Desktop\\BeatCloud\\Source\\Appli\\src\\Application\\Music\\test.mp3";
        Media piano1 = new Media(new File(sound).toURI().toString());
        MediaPlayer piano1Player = new MediaPlayer(piano1);
        piano1Player.setVolume(40.0);
        Status status = piano1Player.getStatus();
        if(status == Status.UNKNOWN || status == Status.HALTED){
            return;
        }
        if (status == Status.READY)
        {
            piano1Player.play();
        }
    }*/

    /*@FXML
    Button playPiano1;
    @FXML
    Media piano;
    @FXML
    MediaPlayer piano1Player;
    @FXML
    MediaView piano1View;*/

    //@FXML
    //public void listenSound(ActionEvent pressed) {
        /*String sound = "C:\\Users\\Pauline\\Desktop\\BeatCloud\\Source\\Appli\\src\\Application\\Music\\test.mp3";
        Media piano1 = new Media(new File(sound).toURI().toString());
        MediaPlayer piano1Player = new MediaPlayer(piano1);
        piano1Player.setVolume(40.0);
        Status status = piano1Player.getStatus();
        if(status == Status.UNKNOWN || status == Status.HALTED){
            return;
        }
        if (status == Status.READY)
        {
            piano1Player.play();
        }*/

        /*piano = new Media(new File("Music/test.mp3").toURI().toString());//Main.class.getClass().getResourceAsStream("Music/test.mp3"));
        piano1Player = new MediaPlayer(piano);
        piano1Player.setVolume(1);
        piano1View = new MediaView(piano1Player);
        piano1Player.play();

        System.out.println("kikou");*/

    //}
//}

 /*@FXML
    FileController fileController;
    @FXML
    MediaController mediaController;

    @FXML
    public void initialize() {
        System.out.println("Application started");
        fileController.init(this);
        mediaController.init(this);
    }

    public void ApplicationController() {
        //this.mainPlayer = player;
        mediaController = new MediaController(mainPlayer);

        fileController = new FileController();
        newFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                fileController.createNewFile(e);
            }
    });*/
