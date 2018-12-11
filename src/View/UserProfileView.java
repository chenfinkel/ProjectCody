package View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * this class is the controller of the profile page
 *
 */
public class UserProfileView {

    private View view;
    @FXML
    private Label SearchUsername;
    @FXML
    private Label SearchFullName;
    @FXML
    private Label SearchBdate;
    @FXML
    private Label SearchCity;


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
     * sets the user information
     *
     * @param user the user name
     * @param fName user first name
     * @param lName user last name
     * @param bDate user date of birth
     * @param city the city where the user live
     */
    public void setSearchText(String user, String fName, String lName, String bDate, String city){
        SearchUsername.setText(user);
        SearchFullName.setText(fName + " " + lName);
        SearchBdate.setText(bDate);
        SearchCity.setText(city);
    }
}
