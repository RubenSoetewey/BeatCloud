package Application.Controllers;

import Application.Main;
import Application.Util.ApiRequest;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class RegisterController extends AnchorPane{
    @FXML
    Stage registerStage;

    @FXML
    TextField surname;
    @FXML
    TextField firstname;
    @FXML
    TextField registerLogin;
    @FXML
    PasswordField registerPassword;
    @FXML
    PasswordField confirmPassword;
    @FXML
    TextField email;
    @FXML
    TextField phone;
    @FXML
    DatePicker birthDate;

    public boolean verifyLogin(String login) {
        String loginRegex = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{0,15}$";
        Pattern pattern = Pattern.compile(loginRegex);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public boolean verifyPassword(String password) {
        String passwordRegex = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{0,15}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean verifyEmail(String email){
        String emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]{2,}\\.[a-z]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean verifyPhone(String phone) {
        String phoneRegex = "^[0-9]{10}";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    @FXML
    Button registerValidation;
    @FXML
    public void validationEnter (MouseEvent onMouseEntered) { registerValidation.setStyle("-fx-background-color: #04B431"); }
    @FXML
    public void validationExit (MouseEvent onMouseExited) { registerValidation.setStyle("-fx-background-color: #0489B1"); }

    @FXML
    public void openApplication(MouseEvent onMouseClicked) throws Exception {
        if (surname.getText().length() != 0 && firstname.getText().length() != 0
            && registerLogin.getText().length() != 0 && verifyLogin(registerLogin.getText()) == true
            && confirmPassword.getText().equals(registerPassword.getText())
            && confirmPassword.getText().length() != 0 && registerPassword.getText().length() != 0
            && email.getText().length() != 0 && verifyEmail(email.getText()) == true
            && phone.getText().length() != 0 && verifyPhone(phone.getText()) == true
            && birthDate.getValue() != null && birthDate.getValue() != LocalDate.now()) {

            FXMLLoader loaderApplication = new FXMLLoader();
            loaderApplication.setLocation(Main.class.getResource("Fxml/application.fxml"));
            BorderPane rootApplication = (BorderPane) loaderApplication.load();

            // register(String username, String password,String confirmPassword,
            //          String email, String phone, String firstName, String lastName)
            JSONObject res = new JSONObject(ApiRequest.register(registerLogin.getText(), registerPassword.getText(), confirmPassword.getText(),
                    email.getText(), phone.getText(), firstname.getText(),surname.getText()));

            if(res.has("token")){
                ApplicationController applicationController = loaderApplication.getController();
                applicationController.token = res.get("token").toString();
                applicationController.initPlugin();

                Stage applicationStage = new Stage(StageStyle.DECORATED);
                applicationStage.setTitle("BeatCloud");
                applicationStage.setResizable(false);
                applicationStage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/Logo.png")));
                applicationStage.setScene(new Scene(rootApplication, 700, 400));
                applicationStage.show();

                registerStage = (Stage) registerAnnulation.getScene().getWindow();
                registerStage.close();
            }else{
                Alert surnameAlert = new Alert(Alert.AlertType.ERROR);
                surnameAlert.setTitle("Attention");
                Stage stage = (Stage) surnameAlert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/attention.png")));
                if(res.has("msg")){
                    surnameAlert.setContentText(res.get("msg").toString());
                }else{
                    surnameAlert.setContentText("Merci de vérifier que tous les champs sont corrects et que vous n'avez pas déja un compte.");
                }
                surnameAlert.showAndWait();
            }
        }
        else {
            if (verifyPassword(registerPassword.getText()) == false && verifyPassword(confirmPassword.getText()) == false
                && registerPassword.getText().length() != 0 && confirmPassword.getText().length() != 0
                && !confirmPassword.getText().equals(registerPassword.getText())) {
                Alert passwordAlert = new Alert(Alert.AlertType.ERROR);
                passwordAlert.setTitle("Attention");
                Stage stage = (Stage) passwordAlert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/attention.png")));
                passwordAlert.setContentText("Votre mot de passe est incorrect ! ");
                passwordAlert.showAndWait();
            }
            if (verifyEmail(email.getText()) == false && phone.getText().length() != 0) {
                Alert surnameAlert = new Alert(Alert.AlertType.ERROR);
                surnameAlert.setTitle("Attention");
                Stage stage = (Stage) surnameAlert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/attention.png")));
                surnameAlert.setContentText("Votre email est incorrect ! ");
                surnameAlert.showAndWait();
            }
            if (verifyLogin(registerLogin.getText()) == false && phone.getText().length() != 0) {
                Alert loginAlert = new Alert(Alert.AlertType.ERROR);
                loginAlert.setTitle("Attention");
                Stage stage = (Stage) loginAlert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/attention.png")));
                loginAlert.setContentText("Votre login est trop long ! ");
                loginAlert.showAndWait();
            }
            if (verifyPhone(phone.getText()) == false && phone.getText().length() != 0) {
                Alert phoneAlert = new Alert(Alert.AlertType.ERROR);
                phoneAlert.setTitle("Attention");
                Stage stage = (Stage) phoneAlert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/attention.png")));
                phoneAlert.setContentText("Votre téléphone est incorrect ! ");
                phoneAlert.showAndWait();
            }
            if(surname.getText().length() == 0 && firstname.getText().length() == 0 && registerLogin.getText().length() == 0
               && confirmPassword.getText().length() == 0 && registerPassword.getText().length() == 0 && email.getText().length() == 0
               && phone.getText().length() == 0 && birthDate.getValue() == null) {
                    Alert connectionAlert = new Alert(Alert.AlertType.ERROR);
                    connectionAlert.setTitle("Attention");
                    Stage stage = (Stage) connectionAlert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/attention.png")));
                    connectionAlert.setContentText("Il manque certaines informations ! ");
                    connectionAlert.showAndWait();
            }
        }
    }

    @FXML
    Button registerAnnulation;
    @FXML
    public void closeWindow (javafx.event.ActionEvent onMouseClicked){
        registerStage = (Stage) registerAnnulation.getScene().getWindow();
        registerStage.close();
    }

    @FXML
    public void annulationEnter (MouseEvent onMouseEntered) { registerAnnulation.setStyle("-fx-background-color: #DF0101"); }
    @FXML
    public void annulationExit (MouseEvent onMouseExited) { registerAnnulation.setStyle("-fx-background-color: #0489B1"); }

    @FXML
    Button connection;
    @FXML
    public void openConnectionWindow (ActionEvent onMouseClicked) throws Exception {
        FXMLLoader loaderConnection = new FXMLLoader();
        loaderConnection.setLocation(Main.class.getResource("Fxml/connection.fxml"));
        AnchorPane rootConnection = (AnchorPane) loaderConnection.load();
        ConnectionController connection = loaderConnection.getController();
        Stage connectionStage = new Stage(StageStyle.DECORATED);
        connectionStage.setTitle("Inscription");
        connectionStage.getIcons().add(new Image(Main.class.getResourceAsStream("Photo/Logo.png")));
        connectionStage.setResizable(false);
        connectionStage.initModality(Modality.APPLICATION_MODAL);
        connectionStage.setScene(new Scene(rootConnection, 500, 450));
        connectionStage.show();

        connectionStage = (Stage) registerAnnulation.getScene().getWindow();
        connectionStage.close();
    }
}
