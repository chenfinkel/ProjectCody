package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class View {

    private Controller control;
    private Stage primStage;

    public View(){
        control=new Controller();
        control.setView(this);
    }

    public void setController(Controller c){
        control=c;
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


    public void signIn(){
        control.login(userName.getText(),password.getText());


    }

    public void login() throws Exception{
        Stage window=primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("UserPage.fxml").openStream()), 400, 300));
        View viewControl = fxmlLoader.getController();
        viewControl.setPrimStage(primStage);
        viewControl.setController(control);
    }

    public void incorrect(){
        Alert al=new Alert(Alert.AlertType.INFORMATION);
        al.setContentText("user name or password are incorrect");
        al.show();
    }

    public void settings()throws Exception{
        Stage window=primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("UpdateFile.fxml").openStream()), 400, 300));
        View viewControl = fxmlLoader.getController();
        viewControl.setPrimStage(primStage);
        viewControl.setController(control);
        viewControl.setText();
    }

    public void setText(){
        user.setText(control.getDetails("userName"));
        pass.setText(control.getDetails("password"));
        fName.setText(control.getDetails("firstName"));
        lName.setText(control.getDetails("lastName"));
        bDate.setText(control.getDetails("birthDate"));
        city.setText(control.getDetails("city"));
    }

    public void update(){
        control.update(pass.getText(),fName.getText(),lName.getText(),bDate.getText(),city.getText());
        setText();
        Alert al=new Alert(Alert.AlertType.INFORMATION);
        al.setContentText("Your account has been updated");
        al.show();
    }

    public void delete(){
        Alert al=new Alert(Alert.AlertType.CONFIRMATION);
        al.setContentText("Are you sure you want to delete your account?");
        Optional<ButtonType> result = al.showAndWait();

        if (result.get() == ButtonType.OK){
            control.delete();

        }
        else {
            al.close();
        }
    }

    public void returnMain() throws Exception{
        Stage window=primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("View.fxml").openStream()), 400, 300));
        View viewControl = fxmlLoader.getController();
        viewControl.setPrimStage(primStage);
        viewControl.setController(control);
    }
}
