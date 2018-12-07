package View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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


    public void setView(View v){
        view=v;
    }

    public void setSearchText(String user, String fName, String lName, String bDate, String city){
        SearchUsername.setText(user);
        SearchFullName.setText(fName + " " + lName);
        SearchBdate.setText(bDate);
        SearchCity.setText(city);
    }
}
