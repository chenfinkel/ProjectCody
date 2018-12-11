package View;

import Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class View {

    private Controller control;

    private Stage updateStage;

    public View(){
        control=new Controller();
        control.setView(this);
        updateStage=new Stage();
    }

    public void searchVac(String from, String to, LocalDate departDate, LocalDate returnDate, String adultTravelers, String childTravelers,
                                  String babyTravelers, String airline, String baggage, boolean isDirect, String priceFrom, String priceTo, String type, String hotelName, String hotelRank){
        List<String> vacs=control.searchVac(from, to, departDate, returnDate, adultTravelers, childTravelers, babyTravelers, airline, baggage,isDirect, priceFrom, priceTo, type, hotelName, hotelRank);
        Stage window=Main.primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        Scene s=null;
        Parent root=null;
        try {
            root=fxmlLoader.load(getClass().getResource("resources/VacationSearchRes.fxml").openStream());
        }catch(IOException e){}
        VacationSearchRes viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        viewControl.setGrid(vacs);
        viewControl.setCurrentUser(getCurrentUser());
        s= new Scene(root, 1300, 650);
        window.setScene(s);
        window.show();
    }

    public boolean login(String userName, String password) {
        return control.login(userName,password);
    }

    public void goToUserPage(){
        try {
            Stage window = Main.primStage;
            FXMLLoader fxmlLoader = new FXMLLoader();
            window.setScene(new Scene(fxmlLoader.load(getClass().getResource("resources/UserPage.fxml").openStream()), 1000, 650));
            UserPageView viewControl = fxmlLoader.getController();
            viewControl.setView(this);
            viewControl.setUserName();
            //viewControl.setVacations();
            //viewControl.setRequests();
            window.show();
        }catch (Exception e){ e.printStackTrace(); }
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
        if(control.getCurrentUser()==null)
            return "guest";
        return control.getCurrentUser();
    }

    public void goToSearch() throws Exception{
        Stage window=Main.primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("resources/VacationSearch.fxml").openStream()), 1000, 650));
        VacationSearchView viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        String s=getCurrentUser();
        viewControl.setCurrentUser(s);
        if(!s.equals("guest")) {
            viewControl.setLoginButton();
            viewControl.setProfilLogOff();
        }
        viewControl.setCombos();
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

    public void buy() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/buyVacation.fxml"));
        Parent root1 = fxmlLoader.load();
        AddVacationView viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        viewControl.setCombos();
        viewControl.setCurrentUser(getCurrentUser());
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("Buy Vacation");
        stage.show();
    }

    public void addVac(String userName, String from, String to, LocalDate departDate, LocalDate returnDate, String travelersA, String travelersC, String travelersB, String airline,
                       String baggage, boolean isDirect, String price, String type, String hotelName, String hotelRank) {
        control.addVac(userName, from, to, departDate, returnDate, travelersA, travelersC, travelersB, airline,
                baggage, isDirect, price, type, hotelName, hotelRank);

    }

    public void requestVac(String vac) {
        String currentUser = getCurrentUser();
        if(!currentUser.equals("guest")){
            String[] split = vac.split("Seller: ");
            String seller = split[1].split("  From")[0];
            if (currentUser.equals(seller))
                alert("You can't submit a request for a vacation you added");
            else {
                control.requestVac(vac);
                alert("Your request has been sent");
            }
        }
        else{
            alert("Please login in order to submit a purchase request");
        }
    }

    public List<String> getUserVac(String user){
        return control.getUserVac(user);
    }

    public List<String> getUserReq(String user) {
        return control.getUserReq(user);
    }

    public List<String> getIncomingReq(String user){
        return control.getIncomingReq(user);
    }

    public void buyVac(String text) {

    }

    public void approveReq(String request) {
        control.approveReq(request);
    }

    public void declineReq(String request) {
        control.declineReq(request);
    }
}
