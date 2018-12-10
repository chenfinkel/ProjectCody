package View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class UserPageView {

    private View view;
    @FXML
    private Label currentUser;
    @FXML
    private Label labVac0;
    @FXML
    private Label labVac1;
    @FXML
    private Label labVac2;
    @FXML
    private Label labReq0;
    @FXML
    private Label labReq1;
    @FXML
    private Label labReq2;
    @FXML
    private Button apr0;
    @FXML
    private Button apr1;
    @FXML
    private Button apr2;
    @FXML
    private Button dcl0;
    @FXML
    private Button dcl1;
    @FXML
    private Button dcl2;
    @FXML
    private Button buy0;
    @FXML
    private Button buy1;
    @FXML
    private Button buy2;



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

    public void setVacations() {
        List<String> l=view.getUserVac(currentUser.getText());
        if(l.size()==0){
            labVac0.setText("No vacations added");
        }
        else {
            int i;
            for (i = 0; i < 3 && i < l.size(); i++) {
                String vacation = l.get(i);
                if (i == 0) {
                    labVac0.setText(vacation);
                    if (vacation.contains("Requested")) {
                        apr0.setVisible(true);
                        dcl0.setVisible(true);
                    }
                }
                if (i == 1) {
                    labVac1.setText(vacation);
                    if (vacation.contains("Requested")) {
                        apr1.setVisible(true);
                        dcl1.setVisible(true);
                    }
                }
                if (i == 2) {
                    labVac2.setText(vacation);
                    if (vacation.contains("Requested")) {
                        apr2.setVisible(true);
                        dcl2.setVisible(true);
                    }
                }
            }
        }
    }

    public void setRequests() {
        List<String> l=view.getUserReq(currentUser.getText());
        if(l.size()==0){
            labReq0.setText("No requests submitted");
        }
        else {
            int i;
            for (i = 0; i < 3 && i < l.size(); i++) {
                String request = l.get(i);
                if (i == 0) {
                    labReq0.setText(request);
                    if(request.contains("approved"))
                        buy0.setVisible(true);
                }
                if (i == 1) {
                    labReq1.setText(request);
                    if(request.contains("approved"))
                        buy1.setVisible(true);
                }
                if (i == 2) {
                    labReq2.setText(request);
                    if(request.contains("approved"))
                        buy2.setVisible(true);
                }
            }
        }
    }

    public void apr0(){
        view.approveReq(labVac0.getText());
        apr0.setDisable(true);
        dcl0.setDisable(true);
    }

    public void apr1(){
        view.approveReq(labVac1.getText());
        apr1.setDisable(true);
        dcl1.setDisable(true);
    }

    public void apr2(){
        view.approveReq(labVac2.getText());
        apr2.setDisable(true);
        dcl2.setDisable(true);
    }

    public void dcl0(){
        view.declineReq(labVac0.getText());
        apr0.setDisable(true);
        dcl0.setDisable(true);
    }

    public void dcl1(){
        view.declineReq(labVac1.getText());
        apr1.setDisable(true);
        dcl1.setDisable(true);
    }

    public void dcl2(){
        view.declineReq(labVac2.getText());
        apr2.setDisable(true);
        dcl2.setDisable(true);
    }

    public void buy0(){
        view.buyVac(labReq0.getText());
        buy0.setDisable(true);
    }

    public void buy1(){
        view.buyVac(labReq1.getText());
        buy1.setDisable(true);
    }

    public void buy2(){
        view.buyVac(labReq2.getText());
        buy2.setDisable(true);
    }
}
