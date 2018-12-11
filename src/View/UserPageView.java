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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            viewList.setVisible(false);
            view.addVacation();
        }catch(Exception e){System.out.println(e.getMessage());}
    }

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

    public void setRequests() {
        viewList.setVisible(false);
        List<String> l=view.getUserReq(currentUser.getText());
        viewList.setVisible(true);
        ObservableList lines = FXCollections.observableArrayList();
        if(l.size()==0){
            lines.add("No requests submitted");
        }
        else {
            for (int i = 0; i < l.size(); i++) {
                String line = l.get(i);
                if (line.contains("approved")) {
                    userRequests = new ArrayList<>();
                    buyButtons = new ArrayList<>();
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

    private void buy(Button b) {
        try {
            String request = userRequests.get(buyButtons.indexOf(b));
            view.buy(request);
        }catch (Exception e) {e.printStackTrace();}
    }

    private void declineRequest(Button b) {
        String request = incomingRequests.get(declines.indexOf(b));
        view.approveReq(request);
        b.setDisable(true);
        Button approve = approves.get(declines.indexOf(b));
        approve.setDisable(true);
    }

    private void approveRequest(Button b) {
        String request = incomingRequests.get(approves.indexOf(b));
        view.approveReq(request);
        b.setDisable(true);
        Button decline = declines.get(approves.indexOf(b));
        decline.setDisable(true);
    }
}
