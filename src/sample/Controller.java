package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.concurrent.Executor;

public class Controller {

    private Model model;
    private Stage primStage;

    public Controller(){
        model=new Model();
    }

    public void setModel(Model m){
        model=m;
    }

    public void setPrimStage(Stage prim){
        primStage=prim;
    }

    @FXML
    private javafx.scene.control.TextField userName;
    @FXML
    private javafx.scene.control.PasswordField password;
    @FXML
    private javafx.scene.control.TextField user;
    @FXML
    private javafx.scene.control.TextField pass;
    @FXML
    private javafx.scene.control.TextField fName;
    @FXML
    private javafx.scene.control.TextField lName;
    @FXML
    private javafx.scene.control.TextField bDate;
    @FXML
    private javafx.scene.control.TextField city;


    public void signIn() throws Exception{
        model.login(userName.getText(),password.getText());
        if(model.isLogin()){
            Stage window=primStage;
            FXMLLoader fxmlLoader=new FXMLLoader();
            window.setScene(new Scene(fxmlLoader.load(getClass().getResource("UserPage.fxml").openStream()), 400, 300));
            Controller viewControl = fxmlLoader.getController();
            viewControl.setPrimStage(primStage);
            viewControl.setModel(model);
        }
        else {
            Alert al=new Alert(Alert.AlertType.INFORMATION);
            al.setContentText("user name or password are incorrect");
            al.show();
        }

    }

    public void settings()throws Exception{
        Stage window=primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("UpdateFile.fxml").openStream()), 400, 300));
        Controller viewControl = fxmlLoader.getController();
        viewControl.setPrimStage(primStage);
        viewControl.setModel(model);
        viewControl.setText();
    }

    public void setText(){
        user.setText(model.getDetails("userName"));
        pass.setText(model.getDetails("password"));
        fName.setText(model.getDetails("firstName"));
        lName.setText(model.getDetails("lastName"));
        bDate.setText(model.getDetails("birthDate"));
        city.setText(model.getDetails("city"));
    }

    public void update(){
        model.update(pass.getText(),fName.getText(),lName.getText(),bDate.getText(),city.getText());
        setText();
        Alert al=new Alert(Alert.AlertType.INFORMATION);
        al.setContentText("Your account has been updated");
        al.show();
    }
}
