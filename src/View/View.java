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

/**
 *
 * this class connects all the stages to the controller
 *
 */
public class View {

    private Controller control;

    private Stage updateStage;

    /**
     *
     * simple constructor
     *
     */
    public View() {
        control = new Controller();
        control.setView(this);
        updateStage = new Stage();
    }

    /**
     *
     *  search a vacation by according to all or part of the data
     *
     * @param from where the flight leave
     * @param to the destination of the flight
     * @param departDate the date of the depart
     * @param returnDate the return date
     * @param adultTravelers number of adult travelers
     * @param childTravelers number of child travelers
     * @param babyTravelers number of baby travelers
     * @param airline the airline of the fligh
     * @param baggage what luggage included in the price
     * @param isDirect if the flight is direct or not
     * @param priceFrom the start of the price range
     * @param priceTo the end of the price range
     * @param type the type of the vacation
     * @param hotelName what hotel is included in the price
     * @param hotelRank the rank of the hotel
     */
    public void searchVac(String from, String to, LocalDate departDate, LocalDate returnDate, String adultTravelers, String childTravelers,
                          String babyTravelers, String airline, String baggage, boolean isDirect, String priceFrom, String priceTo, String type, String hotelName, String hotelRank) {
        List<String> vacs = control.searchVac(from, to, departDate, returnDate, adultTravelers, childTravelers, babyTravelers, airline, baggage, isDirect, priceFrom, priceTo, type, hotelName, hotelRank);
        if (vacs.size() == 0)
            alert("No matches found!");
        else {
            Stage window = Main.primStage;
            FXMLLoader fxmlLoader = new FXMLLoader();
            Scene s = null;
            Parent root = null;
            try {
                root = fxmlLoader.load(getClass().getResource("resources/VacationSearchRes.fxml").openStream());
            } catch (IOException e) {
            }
            VacationSearchRes viewControl = fxmlLoader.getController();
            viewControl.setView(this);
            viewControl.setResults(vacs);
            viewControl.setCurrentUser(getCurrentUser());
            s = new Scene(root, 1000, 650);
            window.setScene(s);
            window.show();
        }
    }

    /**
     *
     * logs on a user
     *
     * @param userName the user name
     * @param password the user password
     * @return true if login is successful false otherwise
     */
    public boolean login(String userName, String password) {
        return control.login(userName, password);
    }

    /**
     *
     * change the view to user page
     *
     */
    public void goToUserPage() {
        try {
            Stage window = Main.primStage;
            FXMLLoader fxmlLoader = new FXMLLoader();
            window.setScene(new Scene(fxmlLoader.load(getClass().getResource("resources/UserPage.fxml").openStream()), 1000, 650));
            UserPageView viewControl = fxmlLoader.getController();
            viewControl.setView(this);
            viewControl.setUserName();
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * change the view to user profile
     *
     */
    public void goToProfile() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/UpdateFile.fxml"));
        try {
            Parent root1 = fxmlLoader.load();
            updateStage.setScene(new Scene(root1));
            UpdateFileView viewControl = fxmlLoader.getController();
            viewControl.setView(this);
            viewControl.setText();
            updateStage.setTitle("Settings");
            updateStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * change the view to login page
     *
     * @throws Exception if change was not successful
     */
    public void returnLoginPage() throws Exception {
        Stage window = Main.primStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("resources/LoginPage.fxml").openStream()), 1000, 650));
        LoginPageView viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        window.show();
    }

    /**
     *
     * open a popup of user details
     *
     * @param user user name
     * @param fName first name
     * @param lName last name
     * @param bDate birth date
     * @param city the city where the user lives
     * @throws Exception if popup was not successful
     */
    public void showUserSearch(String user, String fName, String lName, String bDate, String city) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/UserProfile.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        UserProfileView viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        viewControl.setSearchText(user, fName, lName, bDate, city);
        stage.setTitle("Search Result");
        stage.show();
    }

    /**
     *
     * sign up a new user
     *
     * @param userName user name
     * @param password user password
     * @param fName user first name
     * @param lName user last name
     * @param bDate user birth date
     * @param city the city where the user lives
     */
    public void signUp(String userName, String password, String fName, String lName, LocalDate bDate, String city) {
        control.signUp(userName, password, fName, lName, bDate, city);
    }

    /**
     *
     * delete a user
     *
     */
    public void delete() {
        control.delete();
        updateStage.close();
    }

    /**
     *
     * get details of current user
     *
     * @param s the detail we want to get
     * @return detail of the user
     */
    public String getDetails(String s) {
        return control.getDetails(s);
    }

    /**
     *
     * update the user information
     *
     * @param userName user name
     * @param password user password
     * @param fName user first name
     * @param lName user last name
     * @param bDate user birth date
     * @param city the city where the user lives
     */
    public void update(String userName, String password, String fName, String lName, LocalDate bDate, String city) {
        control.update(userName, password, fName, lName, bDate, city);
    }

    /**
     *
     * log off the current user
     *
     */
    public void logOff() {
        control.logOff();
    }

    /**
     *
     * search another user
     *
     * @param text the user name to search
     */
    public void searchUser(String text) {
        control.searchUser(text);
    }

    /**
     *
     * popup an alert
     *
     * @param alertMessage the massage we want to show
     */
    public void alert(String alertMessage) {
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setContentText(alertMessage);
        al.show();
    }

    /**
     *
     * popup a massage user not found
     *
     */
    public void notFound() {
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setContentText("Username not found");
        al.show();
    }

    /**
     *
     * get the current user name
     *
     * @return current user name
     */
    public String getCurrentUser() {
        if (control.getCurrentUser() == null)
            return "guest";
        return control.getCurrentUser();
    }

    /**
     *
     * change the view to search page
     *
     * @throws Exception if change was not successful
     */
    public void goToSearch() throws Exception {
        Stage window = Main.primStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("resources/VacationSearch.fxml").openStream()), 1000, 650));
        VacationSearchView viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        String s = getCurrentUser();
        viewControl.setCurrentUser(s);
        if (!s.equals("guest")) {
            viewControl.setLoginButton();
            viewControl.setProfilLogOff();
        }
        viewControl.setCombos();
        window.show();
    }

    /**
     *
     * open a popup to add a new vacation
     *
     * @throws Exception if popup was not successful
     */
    public void addVacation() throws Exception {
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

    /**
     *
     * open a popup to buy a vacation
     *
     * @param vacation vacation to buy
     * @throws Exception if popup was not successful
     */
    public void buy(String vacation) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/buyVacation.fxml"));
        Parent root1 = fxmlLoader.load();
        BuyVacationView viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        viewControl.initiate(vacation);
        viewControl.setCurrentUser(getCurrentUser());
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("Buy Vacation");
        stage.show();
    }

    /**
     *
     * add a new vacation
     *
     * @param from where the flight leave
     * @param to the destination of the flight
     * @param departDate the date of the depart
     * @param returnDate the return date
     * @param travelersA number of adult travelers
     * @param travelersC number of child travelers
     * @param travelersB number of baby travelers
     * @param airline the airline of the flight
     * @param baggage what luggage included in the price
     * @param isDirect if the flight is direct or not
     * @param price the price of the vacation
     * @param type the type of the vacation
     * @param hotelName what hotel is included in the price
     * @param hotelRank the rank of the hotel
     */
    public void addVac(String userName, String from, String to, LocalDate departDate, LocalDate returnDate, String travelersA, String travelersC, String travelersB, String airline,
                       String baggage, boolean isDirect, String price, String type, String hotelName, String hotelRank) {
        control.addVac(userName, from, to, departDate, returnDate, travelersA, travelersC, travelersB, airline,
                baggage, isDirect, price, type, hotelName, hotelRank);

    }

    /**
     *
     * request to buy a vacation
     *
     * @param vac the vacation to buy
     * @return true if request was successful false otherwise
     */
    public boolean requestVac(String vac) {
        String currentUser = getCurrentUser();
        if (!currentUser.equals("guest")) {
            String[] split = vac.split("Seller: ");
            String seller = split[1].split("  From")[0];
            if (currentUser.equals(seller)) {
                alert("You can't submit a request for a vacation you added");
                return false;
            }else {
                control.requestVac(vac);
                alert("Your request has been sent");
                return true;
            }
        } else {
            alert("Please login in order to submit a purchase request");
            return true;
        }
    }

    /**
     *
     * search vacations the user published
     *
     * @param user the user name
     * @return list of all vacations the user published
     */
    public List<String> getUserVac(String user) {
        return control.getUserVac(user);
    }

    /**
     *
     * search vacations the user request
     *
     * @param user the user name
     * @return list of all vacations the user request
     */
    public List<String> getUserReq(String user) {
        return control.getUserReq(user);
    }

    /**
     *
     * search vacations the user got a request for
     *
     * @param user the user name
     * @return list of all vacations the user got a request for
     */
    public List<String> getIncomingReq(String user) {
        return control.getIncomingReq(user);
    }

    /**
     *
     * buy a vacation
     *
     * @param text vacation to buy
     */
    public void buyVac(String text) {

    }

    /**
     *
     * approve a request
     *
     * @param request to approve
     */
    public void approveReq(String request) {
        control.approveReq(request);
    }

    /**
     *
     * decline a request
     *
     * @param request to decline
     */
    public void declineReq(String request) {
        control.declineReq(request);
    }

    /**
     *
     * order a vacation
     *
     * @param vacation to order
     * @param currentUser user name
     * @param price of the vacation
     */
    public void order(String vacation, String currentUser, String price) {
        control.order(vacation, currentUser, price);
    }

    /**
     *
     * get all vacations purchase
     *
     * @param currentUser current user name
     * @return list of all vacations purchase by user  
     */
    public List<String> purchaseHistory(String currentUser) {
        return control.purchaseHistory(currentUser);
    }

    public void getUserVacations(String vacToSwitch) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/VacationsToSwitch.fxml"));
        Parent root1 = fxmlLoader.load();
        VacationsToSwitchView viewControl = fxmlLoader.getController();
        viewControl.setView(this);
        viewControl.setVacations(getCurrentUser(),vacToSwitch);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("Your Vacations");
        stage.show();
    }

    public void approveCash(String request) {
        control.approveCash(request);
    }

    public void sendVacToSwitch(String request,String vacToSwitch) {
        control.sendVacToSwitch(request,vacToSwitch);
    }

    public List<String> getExchangableUserVac(String currentUser) {
        return control.getExchangableUserVac(currentUser);
    }
}
