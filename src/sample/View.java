package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.PasswordField;

import java.util.Observable;


public class View extends Observable implements IView {

    private javafx.scene.control.TextField userName;
    private javafx.scene.control.PasswordField password;
    public StringProperty userNameString=new SimpleStringProperty();
    public StringProperty passwordString=new SimpleStringProperty();



    public void bindProperties(){
        userName.textProperty().bind(userNameString);
        password.textProperty().bind(passwordString);
    }



    public void signIn(){

        System.out.println(userNameString.toString());
        //System.out.println(passwordString.toString());
    }
}
