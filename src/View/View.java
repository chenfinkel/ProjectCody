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

    private Stage updateStage;

    public View(){
        control=new Controller();
        control.setView(this);
        updateStage=new Stage();
    }

    public void searchVac(String from,String to, LocalDate departDate, LocalDate returnDate, String adultTravelers, String childTravelers,
                String babyTravelers, String airline, String baggage, boolean isDirect, String priceFrom, String priceTo){
        control.searchVac(from, to, departDate, returnDate, adultTravelers, childTravelers, babyTravelers, airline, baggage,isDirect, priceFrom, priceTo);
    }

    public boolean login(String userName, String password) {
        return control.login(userName,password);
    }

    public void login() throws Exception{
        Stage window=Main.primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("resources/UserPage.fxml").openStream()), 1000, 650));
        UserPageView viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        viewControl.setUserName();
        window.show();
    }

    public void goToProfile(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/UpdateFile.fxml"));
        try {
            Parent root1 = fxmlLoader.load();
            updateStage.setScene(new Scene(root1));
            UpdateFileView viewControl = fxmlLoader.getController();
            viewControl.setView(this);
            viewControl.setText();
            updateStage.setTitle("Settings");
            updateStage.show();
        }catch (Exception e) {e.printStackTrace();}
    }

    public void returnLoginPage() throws Exception{
        Stage window=Main.primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("resources/LoginPage.fxml").openStream()), 1000, 650));
        LoginPageView viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        window.show();
    }

    public void showUserSearch(String user, String fName, String lName, String bDate, String city) throws Exception{
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
        updateStage.close();
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

    public void searchUser(String text) {
        control.searchUser(text);
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

    public void goToSearch() throws Exception{
        Stage window=Main.primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("resources/VacationSearch.fxml").openStream()), 1000, 650));
        VacationSearchView viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        viewControl.setCurrentUser(getCurrentUser());
        viewControl.setLoginButton();
        window.show();
    }

    public void addVacation() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/AddVacation.fxml"));
        Parent root1 = fxmlLoader.load();
        AddVacationView viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        viewControl.setCombos();
        viewControl.setCurrentUser(getCurrentUser());
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("Add Vacation");
        stage.show();
    }

    public void addVac(String userName, String from, String to, LocalDate departDate, LocalDate returnDate, String travelersA, String travelersC, String travelersB, String airline,
                       String baggage, boolean isDirect, String price, String type, String hotelName, String hotelRank) {
        control.addVac(userName, from, to, departDate, returnDate, travelersA, travelersC, travelersB, airline,
                baggage, isDirect, price, type, hotelName, hotelRank);

    }
}
