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
        if(phoneNO.getText().equals("")){
            view.alert("Please enter phone number to proceed!");
        }
        else {
            view.order(vacation, currentUser, price);
            view.alert("The seller will contact you in order to complete the purchase");
            order.setDisable(true);
        }
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
