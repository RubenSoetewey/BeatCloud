package sample;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.media.*;
import java.io.File;

import static javafx.scene.text.Font.font;

public class Main extends Application {

  private static final String MEDIA_URL = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("BeatCloud!");
    Group objects = new Group();
    Scene mainWindow = new Scene(objects, 1200, 750, Color.rgb(0, 51, 102));

    //addStackPane(hBoxOptions);

    /*Label lastCreated = new Label("Last created");
    lastCreated.setTextFill(Color.rgb(0, 204, 204));
    lastCreated.setFont(Font.font("Verdana", 20));*/

    //Create media player
    Media media = new Media(MEDIA_URL);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setAutoPlay(true);
    MediaControl mediaControl = new MediaControl(mediaPlayer);
    mediaControl.setLayoutX(325);
    mediaControl.setLayoutY(40);

    //Instruments
    Image imagePiano = new Image(getClass().getResource("Photo/piano.png").toExternalForm(), 100, 100, true, true);
    Button piano = new Button();
    piano.setLayoutX(300);
    piano.setLayoutY(400);
    piano.setGraphic(new ImageView(imagePiano));
    piano.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        System.out.println("Piano OK");
      }
    });

    Image imageGuitar = new Image(getClass().getResource("Photo/guitar.jpg").toExternalForm(), 100, 100, true, true);
    Button guitar = new Button();
    guitar.setLayoutX(450);
    guitar.setLayoutY(400);
    guitar.setGraphic(new ImageView(imageGuitar));
    guitar.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        System.out.println("Guitare OK");
      }
    });

    Image imageDrums = new Image(getClass().getResource("Photo/drums.jpg").toExternalForm(), 100, 100, true, true);
    Button drums = new Button();
    drums.setLayoutX(545);
    drums.setLayoutY(400);
    drums.setGraphic(new ImageView(imageDrums));
    drums.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        System.out.println("Batterie OK");
      }
    });

    Image imageViolin = new Image(getClass().getResource("Photo/violin.jpg").toExternalForm(), 100, 100, true, true);
    Button violin = new Button();
    violin.setLayoutX(695);
    violin.setLayoutY(400);
    violin.setGraphic(new ImageView(imageViolin));
    violin.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        System.out.println("Violon OK");
      }
    });

    //Options HBOX
    HBox optionsHbox = new HBox(10);
    optionsHbox.setPadding(new Insets(5, 5, 5, 5));

      //Files buttons
      FileChooser fileChoosed = new FileChooser();
      FileControl fileControl = new FileControl(fileChoosed);
      Button openFile = new Button();
      openFile.setText("Open a File");
      openFile.setLayoutY(5);
      openFile.setLayoutX(5);
      openFile.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          File selectedFile = fileChoosed.showOpenDialog(primaryStage);
          /*if (selectedFile != null) {
            primaryStage.setScene(mainWindow);
          }*/
        }
      });

      Button saveFile = new Button();
      saveFile.setText("Save");
      saveFile.setLayoutX(90);
      saveFile.setLayoutY(5);
      saveFile.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          File savedFile = fileChoosed.showSaveDialog(primaryStage);
          System.out.println("Saved");
          /*if(fileChooser != null){
            SaveFile(fileChooser);
          }*/
        }
      });

      //Instruments buttons
      Button addInstrument = new Button();
      addInstrument.setText("Add instrument");
      addInstrument.setLayoutX(140);
      addInstrument.setLayoutY(5);
      addInstrument.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          System.out.println("Added");
        }
      });

      Button changeInstrument = new Button();
      changeInstrument.setText("Change instrument");
      changeInstrument.setLayoutX(250);
      changeInstrument.setLayoutY(5);
      changeInstrument.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          System.out.println("Changed");
        }
      });

      Button deleteInstrument = new Button();
      deleteInstrument.setText("Delete instrument");
      deleteInstrument.setLayoutX(380);
      deleteInstrument.setLayoutY(5);
      deleteInstrument.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          System.out.println("Deleted");
        }
      });

    HBox.setHgrow(openFile, Priority.ALWAYS);
    HBox.setHgrow(saveFile, Priority.ALWAYS);
    HBox.setHgrow(addInstrument, Priority.ALWAYS);
    HBox.setHgrow(changeInstrument, Priority.ALWAYS);
    HBox.setHgrow(deleteInstrument, Priority.ALWAYS);
    openFile.setMaxWidth(Double.MAX_VALUE);
    saveFile.setMaxWidth(Double.MAX_VALUE);
    addInstrument.setMaxWidth(Double.MAX_VALUE);
    changeInstrument.setMaxWidth(Double.MAX_VALUE);
    deleteInstrument.setMaxWidth(Double.MAX_VALUE);
    optionsHbox.getChildren().addAll(openFile, saveFile, addInstrument, changeInstrument, deleteInstrument);
    optionsHbox.setPrefWidth(1200);

    objects.getChildren().addAll(/*lastCreated,*/ mediaControl, optionsHbox, piano, violin, guitar, drums);

    primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/Logo.png")));
    primaryStage.setScene(mainWindow);
    primaryStage.show();
    }

  /*private void addStackPane(HBox hBoxOptions) {
    StackPane stack = new StackPane();
    Rectangle helpIcon = new Rectangle(30.0, 25.0);
    helpIcon.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
      new Stop[]{
        new Stop(0, Color.web("#4977A3")),
        new Stop(0.5, Color.web("#B0C6DA")),
        new Stop(1, Color.web("#9CB6CF")),}));
    helpIcon.setStroke(Color.web("#D0E6FA"));
    helpIcon.setArcHeight(3.5);
    helpIcon.setArcWidth(3.5);

    Text helpText = new Text("?");
    helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
    helpText.setFill(Color.WHITE);
    helpText.setStroke(Color.web("#7080A0"));

    stack.getChildren().addAll(helpIcon, helpText);
    stack.setAlignment(Pos.CENTER_RIGHT);     // Right-justify nodes in stack
    StackPane.setMargin(helpText, new Insets(0, 10, 0, 0)); // Center "?"

    hBoxOptions.getChildren().add(stack);            // Add to HBox
    HBox.setHgrow(stack, Priority.ALWAYS);    // Give stack any extra space
  }*/

    public static void main(String[] args) {
        launch(args);
    }
}
