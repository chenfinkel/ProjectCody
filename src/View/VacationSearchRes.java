package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * this class is the controller of the search results
 *
 */
public class VacationSearchRes {

    private View view;
    @FXML
    private Label userName;
    @FXML
    private Label Profile;
    @FXML
    private Label Profile2;
    @FXML
    private Label LogOff;
    @FXML
    private ListView listView;
    @FXML
    private Button loginButton;

    /** search results*/
    private ArrayList<String> vacations;

    /** purchase request buttons*/
    private ArrayList<Button> PurchaseReqButtons;

    /** exchange request buttons */
    private ArrayList<Button> ExchangeReqButtons;


     /**
      *
      * sey view
      *
     * @param v view
     */
    public void setView(View v) {
        view = v;
    }

    /**
     *
     * gets list of vacation match to search
     * and display the list
     *
     * @param l list of vacation match to search
     */
    public void setResults(List<String> l) {
        ObservableList lines = FXCollections.observableArrayList();
        if (l.size() == 0) {
            lines.add("No matches found");
        } else {
            vacations = new ArrayList<>();
            PurchaseReqButtons = new ArrayList<>();
            ExchangeReqButtons = new ArrayList<>();
            for (int i = 0; i < l.size(); i++) {
                String line = l.get(i);
                Label label = new Label(line);
                HBox hbox = new HBox();
                vacations.add(line);
                String Switch = line.split("Available for switch: ")[1];
                Button ExchangeReq = new Button("Apply Exchange Request");
                ExchangeReq.setVisible(false);
                Button PurchaseReq = new Button("Apply Purchase Request");
                PurchaseReq.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        sendPurchaseRequest((Button) e.getSource());
                        ExchangeReq.setDisable(true);
                        PurchaseReq.setDisable(true);

                    }
                });
                PurchaseReq.setCursor(Cursor.HAND);
                PurchaseReqButtons.add(PurchaseReq);
                hbox.setSpacing(20);
                hbox.setHgrow(label, Priority.ALWAYS);
                hbox.setHgrow(PurchaseReq, Priority.ALWAYS);
                hbox.getChildren().addAll(label, PurchaseReq);
                if (Switch.equals("yes")) {
                    ExchangeReq.setVisible(true);
                    ExchangeReq.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            sendExchangeRequest((Button) e.getSource());
                            ExchangeReq.setDisable(true);
                            PurchaseReq.setDisable(true);
                        }
                    });
                    ExchangeReq.setCursor(Cursor.HAND);
                    ExchangeReqButtons.add(ExchangeReq);
                    hbox.setHgrow(ExchangeReq, Priority.ALWAYS);
                    hbox.getChildren().addAll(ExchangeReq);
                } else {
                    ExchangeReqButtons.add(new Button());
                }
                lines.add(hbox);
            }
        }
        listView.setItems(lines);
    }

    /**
     *
     * send a request to the publish
     * and set a the button as disable
     *
     * @param b the send request button of the vacation
     */
    private void sendPurchaseRequest(Button b) {
        String vacation = vacations.get(PurchaseReqButtons.indexOf(b));
        if (!userName.getText().equals("guest")) {
            String[] split = vacation.split("Seller: ");
            String seller = split[1].split("\n")[0];
            if (userName.getText().equals(seller)) {
                view.alert("You can't submit a request for a vacation you added");
            }
            else{
                try {
                    view.requestVac(vacation);
                } catch(Exception e){e.printStackTrace();}
            }
        }
        else{
            view.alert("Please login in order to submit a purchase request");
        }
    }

    //send a request for exchange
    private void sendExchangeRequest(Button b){
        if (!userName.getText().equals("guest")) {
            String[] split = vacations.get(ExchangeReqButtons.indexOf(b)).split("Seller: ");
            String seller = split[1].split("\n")[0];
            if (userName.getText().equals(seller)) {
                view.alert("You can't submit a request for a vacation you added");
            }
            else{
                try {
                    view.getUserVacations(vacations.get(ExchangeReqButtons.indexOf(b)));
                } catch(Exception e){e.printStackTrace();}
            }
        }
        else{
            view.alert("Please login in order to submit a purchase request");
        }

    }

    /**
     *
     * set a user as current user
     *
     * @param currentUser user to set as current
     */
    public void setCurrentUser(String currentUser) {
        this.userName.setText(currentUser);
        if (currentUser.equals("guest"))
            loginButton.setVisible(true);
        else {
            loginButton.setVisible(false);
            Profile.setVisible(true);
            Profile2.setVisible(true);
            LogOff.setVisible(true);
        }

    }

    /**
     *
     * this function change the view to the login page
     *
     */
    public void goToLogin() {
        try {
            view.returnLoginPage();
        } catch (Exception e) {
        }
    }

    /**
     *
     * this function change the view to the search page
     *
     */
    public void returnSearch() {
        try {
            view.goToSearch();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     *
     * this function change the view to the user page
     *
     */
    public void goToProfile() {
        view.goToUserPage();
    }

    /**
     *
     * this function log off the user who is login
     *
     */
    public void logOff() {
        view.logOff();
    }
}
