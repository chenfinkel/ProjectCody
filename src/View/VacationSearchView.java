package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.HashSet;
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
        if(!returnDate.getEditor().getText().equals("")) {
            if (returnDate.getValue().isBefore(departDate.getValue())) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setContentText("Return date must be later than depart date");
                al.show();
                returnDate.getEditor().clear();
            }
        }
        String travelersC="", travelersB="" , travelersA="", baggageS="";
        if(baggage.getValue()!=null)
            baggageS=(String)baggage.getValue();
        if(adultTravelers.getValue()!=null)
            travelersA=adultTravelers.getValue();
        if(childTravelers.getValue()!=null)
            travelersC=childTravelers.getValue();
        if(babyTravelers.getValue()!=null)
            travelersB=babyTravelers.getValue();
        view.searchVac(from.getText(),to.getText(),departDate.getValue(),returnDate.getValue(),travelersA,
                travelersC, travelersB, airline.getText(),baggageS,
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
