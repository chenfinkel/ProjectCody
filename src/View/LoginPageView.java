package View;

import Controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LoginPageView {

    private View view;
    private Stage primStage;
    private Stage updateStage;

    public LoginPageView(){
        view=new View();
    }

    public void setView(View v){
        view=v;
    }

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private TextField signUpUser;
    @FXML
    private TextField signUpPass;
    @FXML
    private TextField signUpFname;
    @FXML
    private TextField signUpLname;
    @FXML
    private DatePicker signUpBdate;
    @FXML
    private TextField signUpCity;


    public void signIn(){
        boolean isLogin=view.login(userName.getText(),password.getText());
        if(!isLogin)
            incorrectLogin();
        else{
            try{
                view.login();
            }catch (Exception e){}
        }
    }

    public void incorrectLogin(){
        Alert al=new Alert(Alert.AlertType.INFORMATION);
        al.setContentText("Username or password are incorrect");
        al.show();
    }

    public void SignUp(){
        view.signUp(signUpUser.getText(),signUpPass.getText(),
                signUpFname.getText(),signUpLname.getText(),signUpBdate.getValue(),signUpCity.getText());
    }

    public void clear(){
        signUpUser.clear();
        signUpPass.clear();
        signUpFname.clear();
        signUpLname.clear();
        signUpBdate.getEditor().clear();
        signUpCity.clear();
    }



}
