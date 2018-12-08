package View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Optional;

public class UserPageView {

    private View view;
    @FXML
    private Label currentUser;


    public void setView(View v){
        view=v;
    }

    public void LogOff(){
        view.logOff();
    }

    public void goToProfile(){
        view.goToProfile();
    }


    public void setUserName(){
        currentUser.setText(view.getCurrentUser());
    }

    public void goToSearch(){
        try {
            view.goToSearch();
        }catch(Exception e){}
    }

    public void addVacation(){
        try {
            view.addVacation();
        }catch(Exception e){System.out.println(e.getMessage());}
    }
}
