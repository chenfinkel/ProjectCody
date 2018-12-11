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

    private ArrayList<String> vacations;

    private ArrayList<Button> requestButtons;


    public void setView(View v) {
        view = v;
    }

    public void setResults(List<String> l) {
        ObservableList lines = FXCollections.observableArrayList();
        if (l.size() == 0) {
            lines.add("No matches found");
        } else {
            vacations = new ArrayList<>();
            requestButtons = new ArrayList<>();
            for (int i = 0; i < l.size(); i++) {
                String line = l.get(i);
                Label label = new Label(line);
                HBox hbox = new HBox();
                vacations.add(line);
                Button request = new Button("Send Request");
                request.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        sendRequest((Button) e.getSource());
                    }
                });
                request.setCursor(Cursor.HAND);
                requestButtons.add(request);
                hbox.setSpacing(20);
                hbox.setHgrow(label, Priority.ALWAYS);
                hbox.setHgrow(request, Priority.ALWAYS);
                hbox.getChildren().addAll(label, request);
                lines.add(hbox);
            }
        }
        listView.setItems(lines);
    }

    private void sendRequest(Button b) {
        String vacation = vacations.get(requestButtons.indexOf(b));
        boolean bool = view.requestVac(vacation);
        if (bool)
            b.setDisable(true);
        else
            b.setVisible(false);
    }


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

    public void goToLogin() {
        try {
            view.returnLoginPage();
        } catch (Exception e) {
        }
    }

    public void returnSearch() {
        try {
            view.goToSearch();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void goToProfile() {
        view.goToUserPage();
    }

    public void logOff() {
        view.logOff();
    }
}
