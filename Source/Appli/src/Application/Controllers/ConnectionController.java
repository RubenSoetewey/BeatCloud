package Application.Controllers;

import Application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class ConnectionController extends AnchorPane{
    @FXML
    Stage connectionStage;
    @FXML
    TextField connectLogin;
    @FXML
    PasswordField connectPassword;
    @FXML
    Button connectValidation;
    @FXML
    public void validationEnter (MouseEvent onMouseEntered) { connectValidation.setStyle("-fx-background-color: #04B431; -fx-text-fill: white"); }
    @FXML
    public void validationExit (MouseEvent onMouseExited) { connectValidation.setStyle("-fx-background-color: #0489B1; -fx-text-fill: white"); }

    @FXML
    public void openApplication(MouseEvent onMouseClicked) throws Exception {
        if (connectLogin.getText().length() != 0 && connectPassword.getText().length() != 0) {
            FXMLLoader loaderApplication = new FXMLLoader();
            loaderApplication.setLocation(Main.class.getResource("Fxml/application.fxml"));
            BorderPane rootApplication = (BorderPane) loaderApplication.load();
            ApplicationController applicationController = loaderApplication.getController();
            Stage applicationStage = new Stage(StageStyle.DECORATED);
            applicationStage.setTitle("BeatCloud");
            applicationStage.setResizable(false);
            applicationStage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/Logo.png")));
            applicationStage.setScene(new Scene(rootApplication, 700, 400));
            applicationStage.show();

            connectionStage = (Stage) connectAnnulation.getScene().getWindow();
            connectionStage.close();
        }
        else {
            Alert connectionAlert = new Alert(Alert.AlertType.ERROR);
            connectionAlert.setTitle("Attention");
            Stage stage = (Stage) connectionAlert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/attention.png")));
            connectionAlert.setContentText("Login ou mot de passe incorrect ! ");
            connectionAlert.showAndWait();
        }
    }

    @FXML
    Button connectAnnulation;
    @FXML
    public void annulationEnter (MouseEvent onMouseEntered) { connectAnnulation.setStyle("-fx-background-color: #DF0101; -fx-text-fill: white"); }
    @FXML
    public void annulationExit (MouseEvent onMouseExited) { connectAnnulation.setStyle("-fx-background-color: #0489B1; -fx-text-fill: white"); }

    @FXML
    public void closeWindow (MouseEvent onMouseClicked){
        connectionStage = (Stage) connectAnnulation.getScene().getWindow();
        connectionStage.close();
    }

    @FXML
    Button register;
    @FXML
    public void openRegisterWindow (ActionEvent onMouseClicked) throws Exception {
        FXMLLoader loaderRegister = new FXMLLoader();
        loaderRegister.setLocation(Main.class.getResource("Fxml/register.fxml"));
        AnchorPane rootRegister = (AnchorPane) loaderRegister.load();
        RegisterController register = loaderRegister.getController();
        Stage registerStage = new Stage(StageStyle.DECORATED);
        registerStage.setTitle("Inscription");
        registerStage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/Logo.png")));
        registerStage.setResizable(false);
        registerStage.initModality(Modality.APPLICATION_MODAL);
        registerStage.setScene(new Scene(rootRegister, 500, 710));
        registerStage.show();

        connectionStage = (Stage) connectAnnulation.getScene().getWindow();
        connectionStage.close();
    }

    @FXML
    Hyperlink forgotPassword;
    @FXML
    public void forgotPassword(ActionEvent onMouseClicked) {
        TextInputDialog forgotPasswordDialog= new TextInputDialog("");
        forgotPasswordDialog.setTitle("Mot de passe oublié");
        Stage stage = (Stage) forgotPasswordDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/attention.png")));
        forgotPasswordDialog.setHeaderText("Nous allons vous renvoyez un mot de passe");
        forgotPasswordDialog.setContentText("Veuillez saisir votre email: ");
        Optional<String> result = forgotPasswordDialog.showAndWait();
        if(result.isPresent()){
            System.out.println("Your email: " + result.get());
        }
    }

    @FXML
    CheckBox rememberMe;
    @FXML
    public void rememberMe(ActionEvent onSelected) {
        if (rememberMe.isSelected()) {
            System.out.println("Vous avez coché: Se souvenir de moi");
        }
    }

}