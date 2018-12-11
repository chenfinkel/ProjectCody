package View;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * controller of login page fxml, responds to button clicks etc. and send to view to pass it on
 */
public class LoginPageView {

    private View view;

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private TextField signUpUser;
    @FXML
    private TextField signUpPass;
    @FXML
    private TextField signUpFname;
    @FXML
    private TextField signUpLname;
    @FXML
    private DatePicker signUpBdate;
    @FXML
    private TextField signUpCity;


    /**
     * set view
     * @param v
     */
    public void setView(View v){
        view=v;
    }

    /**
     * send to view which returns if log in succeeded
     * if succeeded, returns the user to main screen
     * if not succeeded, notify user and clear text fields
     */
    public void signIn(){
        boolean isLogin=view.login(userName.getText(),password.getText());
        if(!isLogin) {
            incorrectLogin();
            userName.clear();
            password.clear();
        }
        else{
            try{
                view.goToSearch();
            }catch (Exception e){e.printStackTrace();}
        }
    }

    /**
     * notify user login failed
     */
    public void incorrectLogin(){
        Alert al=new Alert(Alert.AlertType.INFORMATION);
        al.setContentText("Username or password are incorrect");
        al.show();
    }

    /**
     * sends data of new user to view to pass it on to model and insert to db
     */
    public void SignUp(){
        view.signUp(signUpUser.getText(),signUpPass.getText(),
                signUpFname.getText(),signUpLname.getText(),signUpBdate.getValue(),signUpCity.getText());
        clear();
    }

    /**
     * clear field of sign up
     */
    public void clear(){
        signUpUser.clear();
        signUpPass.clear();
        signUpFname.clear();
        signUpLname.clear();
        signUpBdate.getEditor().clear();
        signUpCity.clear();
    }

    /**
     * return to main screen
     */
    public void goToSearch(){
        try {
            view.goToSearch();
        }catch(Exception e){e.printStackTrace();}
    }

}
