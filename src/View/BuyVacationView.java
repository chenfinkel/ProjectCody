package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

/**
 * controller of buy vacation fxml, responds to button clicks etc. and send to view to pass it on
 */
public class BuyVacationView {

    @FXML
    private TextField phoneNO;
    @FXML
    private TextField discount;
    @FXML
    private Label subTotal;
    @FXML
    private Label discountLabel;
    @FXML
    private Label grandTotal;
    @FXML
    private Button order;
    @FXML
    private ChoiceBox code;


    private String vacation;

    private View view;

    private String currentUser;


    /**
     * set view
     * @param v
     */
    public void setView(View v) {
        view=v;
    }

    /**
     * init combo boxes and details of vacation
     * @param vacation- the vacation to buy
     */
    public void initiate(String vacation){
        String[] split = vacation.split("Price: ");
        String price = split[1];
        subTotal.setText(price+"$");
        grandTotal.setText(price+"$");
        this.vacation = vacation;
        ObservableList codes = FXCollections.observableArrayList();
        codes.addAll("050","052","054","058", "02","03","04","08","09");
        code.setItems(codes);

    }

    /**
     * set current user field by given user name
     * @param userName
     */
    public void setCurrentUser(String userName){
        currentUser=userName;
    }

    /**
     * check if all data is filled correctly, if so,
     * sends to view to pass it on to model and updates the user his payment has baan recieved.
     */
    public void order(){
        String grandTotalText = grandTotal.getText();
        String price = grandTotalText.substring(0, grandTotalText.length()-1);
        String phoneNumber = phoneNO.getText();
        if(phoneNumber.equals("")){
            view.alert("Please enter phone number to proceed!");
        }
        else {
            if(validPhoneNum(phoneNumber)) {
                phoneNumber = (String)code.getSelectionModel().getSelectedItem() + phoneNumber;
                view.order(vacation, currentUser, phoneNumber, price);
                view.alert("The seller will contact you in order to complete the purchase");
                order.setDisable(true);
            }else {
                view.alert("Please enter a valid phone number!");
            }
        }
    }

    private boolean validPhoneNum(String phoneNumber) {
        if(phoneNumber.length() == 7) {
            for (int i = 0; i < phoneNumber.length(); i++){
                if (!Character.isDigit(phoneNumber.charAt(i)))
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * if discount code is valid , takes 10% off the vacation price
     */
    public void applyDiscount(){
        if (discount.getText().equals("10off")){
            String s = subTotal.getText();
            double price = Double.parseDouble(s.substring(0,s.length()-1));
            double priceAfterDisc = price*0.9;
            discountLabel.setText("10%");
            grandTotal.setText(priceAfterDisc+"$");
        }
    }

}
