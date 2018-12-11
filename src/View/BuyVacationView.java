package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;


public class BuyVacationView {


    @FXML
    private TextField cardOwner;
    @FXML
    private TextField cardNumber;
    @FXML
    private TextField cvv;
    @FXML
    private TextField PayPalUser;
    @FXML
    private PasswordField PayPalPass;
    @FXML
    private ChoiceBox month;
    @FXML
    private ChoiceBox year;
    @FXML
    private Pane PayPalPane;
    @FXML
    private Pane VisaPane;


    private String vacation;

    private View view;

    private String currentUser;

    private String paymethod;

    public void initiate(String vacation){
        ObservableList<String> months = FXCollections.observableArrayList();
        months.addAll("01", "02", "03","04", "05", "06","07", "08", "09", "10", "11", "12");
        month.setItems(months);
        ObservableList<String> years = FXCollections.observableArrayList();
        years.addAll("2022", "2021", "2020", "2019", "2018");
        year.setItems(years);
        this.vacation = vacation;
    }

    public void setView(View v) {
        view=v;
    }

    public void setCurrentUser(String userName){
        currentUser=userName;
    }

    public void visa(){
        PayPalPane.setVisible(false);
        VisaPane.setVisible(true);
        paymethod = "visa";
    }

    public void payPal(){
        VisaPane.setVisible(false);
        PayPalPane.setVisible(true);
        paymethod = "paypal";
    }

    public void order(){
        if (paymethod.equals("visa")){
            if (cardOwner.getText().equals("") || cardNumber.getText().equals("") ||
            cvv.getText().equals("") || month.getValue() == null || year.getValue() == null) {
                view.alert("All fields are required!");
                return;
            }
        } else {
            if (PayPalUser.getText().equals("") || PayPalPass.getText().equals("")) {
                view.alert("All fields are required!");
                return;
            }
        }
        view.order(vacation, currentUser);
        view.alert("Payment approved, Tickets were sent to your email.\nHave fun in your vacation!!!");
    }


}
