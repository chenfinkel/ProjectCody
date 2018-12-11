package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * this class is the controller of the search
 *
 */
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
    @FXML
    private ComboBox<String> type;
    @FXML
    private ComboBox<String> hotelRank;
    @FXML
    private TextField hotelName;
    @FXML
    private Label Profile;
    @FXML
    private Label Profile2;
    @FXML
    private Label LogOff;
    @FXML
    private Button advanced;

    /**
     *
     * set the combo box with values
     *
     */
    public void setCombos(){
        Set<String> items=new HashSet<>();
        for(int i=1; i<11; i++)
            items.add(i+"");
        ObservableList<String> list= FXCollections.observableArrayList(items);
        adultTravelers.setItems(list);
        childTravelers.setItems(list);
        babyTravelers.setItems(list);
        baggage.getItems().addAll("None","Trolly","10kg suitcase","20kg suitcase");
        type.getItems().addAll("Exotic","Urban");
        hotelRank.getItems().addAll("1","2","3","4","5");
    }

    /**
     *
     * set view
     *
     * @param v view
     */
    public void setView(View v){
        view=v;
    }

    /**
     *
     * this function log off the user who is login
     *
     */
    public void logOff(){
        view.logOff();
    }

    /**
     *
     * this function send the search values and change the view to search results
     *
     */
    public void searchVac(){
        if(!returnDate.getEditor().getText().equals("")) {
            if (returnDate.getValue().isBefore(departDate.getValue())) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setContentText("Return date must be later than depart date");
                al.show();
                returnDate.getEditor().clear();
            }
        }
        if(!returnDate.getEditor().getText().equals("")) {
            if (returnDate.getValue().isBefore(departDate.getValue())) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setContentText("Return date must be later than depart date");
                al.show();
                returnDate.getEditor().clear();
            }
        }
        String travelersC="", travelersB="" , travelersA="", baggageS="", types="", rank="";
        if(type.getValue()!=null)
            types=type.getValue();
        if(hotelRank.getValue()!=null)
            rank=hotelRank.getValue();
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
                isDirect.isSelected(), priceFrom.getText(), priceTo.getText(),types,hotelName.getText(),rank);
    }

    /**
     *
     * this function change the view to the login page
     *
     */
    public void goToLogin(){
        try {
            view.returnLoginPage();
        }catch(Exception e){}
    }


    /**
     *
     * this function search another user
     *
     */
    public void searchUser(){
        view.searchUser(searchUserName.getText());
        searchUserName.clear();
    }

    /**
     *
     * set a user as current user
     *
     * @param currentUser the user name
     */
    public void setCurrentUser(String currentUser) {
        this.currentUser.setText(currentUser);
    }

    /**
     *
     * set the login button as not visible
     *
     */
    public void setLoginButton() {
        loginButton.setVisible(false);
    }

    /**
     *
     * show another fields of search
     *
     */
    public void showAdv(){
        if (advanced.getText().equals("Advanced Search")) {
            type.setVisible(true);
            hotelName.setVisible(true);
            hotelRank.setVisible(true);
            advanced.setText("Simple Search");
        } else {
            type.setVisible(false);
            hotelName.setVisible(false);
            hotelRank.setVisible(false);
            advanced.setText("Advanced Search");
        }
    }

    /**
     *
     * log off the user
     *
     */
    public void setProfilLogOff() {
        Profile.setVisible(true);
        Profile2.setVisible(true);
        LogOff.setVisible(true);
    }

    /**
     *
     * change the view to user page
     *
     */
    public void goToProfile(){
        view.goToUserPage();
    }
}
