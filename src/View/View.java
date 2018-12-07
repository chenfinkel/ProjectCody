package View;

import Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.time.LocalDate;

public class View {

    private Controller control;

    public View(){
        control=new Controller();
        control.setView(this);
    }

    public void searchVac(String from, String to, LocalDate depart, LocalDate returnDate, String travelers){

    }

    public boolean login(String userName, String password) {
        return control.login(userName,password);
    }

    public void login() throws Exception{
        /*
        //Stage window=Main.primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        (Main.primStage).setScene(new Scene(fxmlLoader.load(getClass().getResource("resources/UserPage.fxml").openStream()), 1000, 650));
        UserPageView viewControl = fxmlLoader.getController();
        //viewControl.setPrimStage(window);
        viewControl.setView(this);
        viewControl.setUserName();
        (Main.primStage).show();
        */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/UserPage.fxml"));
        try {
            Parent root1 = fxmlLoader.load();
            Stage updateStage = new Stage();
            updateStage.setScene(new Scene(root1));
            UserPageView viewControl = fxmlLoader.getController();
            viewControl.setView(this);
            //viewControl.setUpdateStage(updateStage);
            viewControl.setUserName();
            updateStage.setTitle("Settings");
            updateStage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void goToProfile(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/UpdateFile.fxml"));
        try {
            Parent root1 = fxmlLoader.load();
            Stage updateStage = new Stage();
            updateStage.setScene(new Scene(root1));
            UpdateFileView viewControl = fxmlLoader.getController();
            viewControl.setView(this);
            //viewControl.setUpdateStage(updateStage);
            viewControl.setText();
            updateStage.setTitle("Settings");
            updateStage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnLoginPage() throws Exception{
        Stage window=Main.primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("resources/LoginPage.fxml").openStream()), 1000, 650));
        LoginPageView viewControl = fxmlLoader.getController();
        //viewControl.setPrimStage(window);
        viewControl.setView(this);
        window.show();
    }

    public void showSearch(String user, String fName, String lName, String bDate, String city) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/UserProfile.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        UserProfileView viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        viewControl.setSearchText(user,fName,lName,bDate,city);
        stage.setTitle("Search Result");
        stage.show();
    }



    public void signUp(String userName, String password, String fName, String lName, LocalDate bDate, String city) {
        control.signUp(userName,password,  fName, lName, bDate, city);
    }

    public void delete() {
        control.delete();
        //updateStage.close();
    }

    public String getDetails(String s) {
        return control.getDetails(s);
    }

    public void update(String userName, String password, String fName, String lName, LocalDate bDate, String city) {
        control.update( userName,  password,  fName,  lName,  bDate,  city);
    }

    public void logOff() {
        control.logOff();
    }

    public void search(String text) {
        control.search(text);
    }

    public void alert(String alertMessage) {
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setContentText(alertMessage);
        al.show();
    }

    public void notFound(){
        Alert al=new Alert(Alert.AlertType.INFORMATION);
        al.setContentText("Username not found");
        al.show();
    }

    public String getCurrentUser() {
        return control.getCurrentUser();
    }
}
