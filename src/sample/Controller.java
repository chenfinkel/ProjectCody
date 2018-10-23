package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class Controller {

    private Model model;

    public Controller(){
        model=new Model();
    }

    @FXML
    private javafx.scene.control.TextField userName;
    @FXML
    private javafx.scene.control.PasswordField password;


    public void signIn(){
        model.login(userName.getText(),password.getText());
        if(model.isLogin()){
            Alert al=new Alert(Alert.AlertType.INFORMATION);
            al.setContentText("success");
            al.show();
        }

    }



/*
    public void updateFile(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Update");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("UpdateFile.fxml").openStream());
            Scene scene = new Scene(root, 400, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }*/
}
