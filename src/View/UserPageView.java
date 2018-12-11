package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * controller of user page fxml, responds to button clicks etc. and send to view to pass it on
 */
public class UserPageView {


    private View view;
    @FXML
    private Label currentUser;
    @FXML
    private ListView viewList;
    @FXML
    private ArrayList<Button> approves;

    private ArrayList<Button> declines;

    private ArrayList<String> incomingRequests;

    private ArrayList<String> userRequests;

    private ArrayList<Button> buyButtons;

    /**
     * set view
     * @param v
     */
    public void setView(View v){
        view=v;
    }

    /**
     * pass user request to log off
     */
    public void LogOff(){
        view.logOff();
    }

    /**
     * open update file so the user can change its info
     */
    public void goToProfile(){
        view.goToProfile();
    }

    /**
     * set current user name label
     */
    public void setUserName(){
        currentUser.setText(view.getCurrentUser());
    }

    /**
     * return to main screen
     */
    public void goToSearch(){
        try {
            view.goToSearch();
        }catch(Exception e){e.printStackTrace();}
    }

    /**
     * open add vacation screen so the user can add new vacation to sell
     */
    public void addVacation(){
        try {
            viewList.setVisible(false);
            view.addVacation();
        }catch(Exception e){e.printStackTrace();}
    }

    /**
     * gets from view a list of current user vacations he added and shows them
     */
    public void setVacations() {
        viewList.setVisible(false);
        List<String> l=view.getUserVac(currentUser.getText());
        viewList.setVisible(true);
        ObservableList vacations = FXCollections.observableArrayList();
        if(l.size()==0){
            vacations.add("No vacations added");
        }
        else {
            for (int i = 0; i < l.size(); i++) {
                vacations.add(l.get(i));
            }
        }
        viewList.setItems(vacations);
    }

    /**
     * gets from view a list of current user requests he need to approve or decline and shows them
     */
    public void setIncomingRequests() {
        viewList.setVisible(false);
        approves = new ArrayList<>();
        declines = new ArrayList<>();
        incomingRequests = new ArrayList<>();
        List<String> l=view.getIncomingReq(currentUser.getText());
        viewList.setVisible(true);
        ObservableList lines = FXCollections.observableArrayList();
        if(l.size()==0){
            lines.add("No requests submitted");
        }
        else {
            for (int i = 0; i < l.size(); i++) {
                HBox hbox = new HBox();
                Label label = new Label(l.get(i));
                incomingRequests.add(label.getText());
                Button app = new Button("Approve");
                app.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        approveRequest((Button)e.getSource());
                    }
                });
                approves.add(app);
                app.setLayoutY(label.getHeight());
                Button dec = new Button("Decline");
                dec.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        declineRequest((Button)e.getSource());
                    }
                });
                declines.add(dec);
                hbox.setSpacing(20);
                hbox.setHgrow(label, Priority.ALWAYS);
                hbox.setHgrow(app, Priority.ALWAYS);
                hbox.setHgrow(dec, Priority.ALWAYS);
                hbox.getChildren().addAll(label, app, dec);
                lines.add(hbox);
            }
        }
        viewList.setItems(lines);
    }

    /**
    * gets from view a list of current user requests he applied and shows them
     */
    public void setRequests() {
        viewList.setVisible(false);
        List<String> l=view.getUserReq(currentUser.getText());
        viewList.setVisible(true);
        ObservableList lines = FXCollections.observableArrayList();
        if(l.size()==0){
            lines.add("No requests submitted");
        }
        else {
            userRequests = new ArrayList<>();
            buyButtons = new ArrayList<>();
            for (int i = 0; i < l.size(); i++) {
                String line = l.get(i);
                if (line.contains("approved")) {
                    HBox hbox = new HBox();
                    Label label = new Label(line);
                    userRequests.add(label.getText());
                    Button buy = new Button("Buy");
                    buy.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent e) {
                            buy((Button)e.getSource());
                        }
                    });
                    buyButtons.add(buy);
                    hbox.setSpacing(20);
                    hbox.setHgrow(label, Priority.ALWAYS);
                    hbox.setHgrow(buy, Priority.ALWAYS);
                    hbox.getChildren().addAll(label, buy);
                    lines.add(hbox);
                } else
                    lines.add(line);
            }
        }
        viewList.setItems(lines);
    }

    /**
     * gets from view a list of current user past purchases and shows them
     */
    public void PurchaseHistory(){
        viewList.setVisible(false);
        List<String> l = view.purchaseHistory(currentUser.getText());
        viewList.setVisible(true);
        ObservableList vacations = FXCollections.observableArrayList();
        if(l.size()==0){
            vacations.add("No purchases were made yet");
        }
        else {
            for (int i = 0; i < l.size(); i++) {
                vacations.add(l.get(i));
            }
        }
        viewList.setItems(vacations);
    }

    /**
     * send to view the request of vacation the user wants to buy
     * to verify and sends the user to payment screen
     * @param b
     */
    private void buy(Button b) {
        try {
            String request = userRequests.get(buyButtons.indexOf(b));
            view.buy(request);
            b.setDisable(true);
        }catch (Exception e) {e.printStackTrace();}
    }

    /**
     * current user wants to decline incoming request
     * @param b
     */
    private void declineRequest(Button b) {
        String request = incomingRequests.get(declines.indexOf(b));
        view.declineReq(request);
        b.setDisable(true);
        Button approve = approves.get(declines.indexOf(b));
        approve.setDisable(true);
    }

    /**
     * current user wants to approve incoming request
     * @param b
     */
    private void approveRequest(Button b) {
        String request = incomingRequests.get(approves.indexOf(b));
        view.approveReq(request);
        b.setDisable(true);
        Button decline = declines.get(approves.indexOf(b));
        decline.setDisable(true);
    }

    /**
     * return to main screen
     */
    public void returnSearch(){
        try {
            view.goToSearch();
        }catch (Exception e){e.printStackTrace();}
    }
}
