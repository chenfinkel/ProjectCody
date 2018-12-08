package View;

import Controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class VacationSearchView {

    private View view;
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private DatePicker departDate;
    @FXML
    private DatePicker returnDate;
    @FXML
    private ComboBox<String> adultTravelers;
    @FXML
    private ComboBox<String> childTravelers;
    @FXML
    private ComboBox<String> babyTravelers;
    @FXML
    private TextField airline;
    @FXML
    private ComboBox baggage;
    @FXML
    private CheckBox isDirect;
    @FXML
    private TextField priceFrom;
    @FXML
    private TextField priceTo;
    @FXML
    private TextField searchUserName;
    @FXML
    private Label currentUser;
    @FXML
    private Button loginButton;


    public void setCombos(){
        Set<String> items=new HashSet<>();
        for(int i=1; i<11; i++)
            items.add(i+"");
        ObservableList<String> list= FXCollections.observableArrayList(items);
        adultTravelers.setItems(list);
        childTravelers.setItems(list);
        babyTravelers.setItems(list);
        baggage.getItems().addAll("None","Trolly","10kg suitcase","20kg suitcase");
    }

    public void setView(View v){
        view=v;
    }

    public void searchVac(){
        if(from.getText().equals("") || to.getText().equals("") || departDate.toString().equals("") || adultTravelers.toString().equals("") || childTravelers.toString().equals("")
                || babyTravelers.toString().equals("") || airline.getText().equals("") || baggage.toString().equals("") || priceFrom.getText().equals("") || priceTo.getText().equals("")){
            view.alert("All fields are required!");
        }
        if(!returnDate.getEditor().getText().equals("")) {
            if (returnDate.getValue().isBefore(departDate.getValue())) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setContentText("Return date must be later than depart date");
                al.show();
                returnDate.getEditor().clear();
            }
        }
        view.searchVac(from.getText(),to.getText(),departDate.getValue(),returnDate.getValue(),adultTravelers.getEditor().getText(),
                childTravelers.getEditor().getText(), babyTravelers.getEditor().getText(), airline.getText(), baggage.getEditor().getText(),
                isDirect.isSelected(), priceFrom.getText(), priceTo.getText());

    }

    public void goToLogin(){
        try {
            view.returnLoginPage();
        }catch(Exception e){}
    }

    public void searchUser(){
        view.searchUser(searchUserName.getText());
        searchUserName.clear();
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser.setText(currentUser);
    }

    public void setLoginButton() {
        loginButton.setDisable(true);
    }
}
