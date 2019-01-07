package Controller;

import Model.Model;
import View.View;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * this class get orders anf parameters from View class and checks if all data valid,
 * if so, it transfers the data to the model and return the results back to View.
 * the controller communicates with view and the model by instances of them- which are its fields.
 */
public class Controller {

    private Model model;
    private View view;

    public Controller(){
        model=new Model();
    }

    /**
     * check which user is connected to the system at the moment
     * @return userName of thi user
     */
    public String getCurrentUser(){
        return model.getCurrentUser();
    }

    /**
     * set view instance
     * @param v
     */
    public void setView(View v){
        view=v;
    }

    /**
     * check userName and password not empty, then send data to model and return true if login succeeded
     * @param userName- userName of user trying to log in
     * @param password- his password
     * @return true if login succeeded
     */
    public boolean login(String userName, String password){
        if (!userName.equals("") && !password.equals(""))
            model.login(userName, password);
        return model.isLogin();
    }

    /**
     * log off current user off the system
     */
    public void logOff(){
        model.logOff();
        try {
            view.goToSearch();
        } catch (Exception e){e.printStackTrace();}
    }

    /**
     * ask model for user details of specific field of data
     * @param label- the data wanted
     * @return- value of the data
     */
    public String getDetails(String label){
        return model.getDetails(label,"");
    }

    /**
     * get new values to change user information, check if valid the transfer to model to update db
     * @param user user name
     * @param password
     * @param fName
     * @param lName
     * @param bDate
     * @param city
     */
    public void update(String user, String password, String fName, String lName, LocalDate bDate, String city){
        if (valid(user, password, fName, lName, bDate, city)) {
            String day = bDate.getDayOfMonth()+"";
            String month = bDate.getMonthValue() + "";
            String year = bDate.getYear() + "";
            model.update(user, password, fName, lName, day, month, year, city);
            view.alert("Your account has been updated");
        }
    }

    /**
     * user request to delete his account
     */
    public void delete(){
        model.delete();
        try {
            view.returnLoginPage();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * sends user name to model and gets user information and transfer to view
     * @param user- user name to search in db
     */
    public void searchUser(String user) {
        if (!user.equals("")) {
            try {
                if (model.getDetails("userName", user).equals("")) {
                    view.notFound();
                } else {
                view.showUserSearch(user, model.getDetails("firstName", user), model.getDetails("lastName", user),
                        model.getDetails("birthDate", user), model.getDetails("city", user));
                }
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    /**
     * gets information of new user, checks if valid, them transfer to model to insert to db
     * @param user
     * @param password
     * @param fName
     * @param lName
     * @param bDate
     * @param city
     */
    public void signUp(String user, String password, String fName, String lName, LocalDate bDate, String city){
        if (valid(user, password, fName, lName, bDate, city)){
                String day = bDate.getDayOfMonth() + "";
                String month = bDate.getMonthValue() + "";
                String year = bDate.getYear() + "";
                model.signUp(user, password, fName, lName, day + "/" + month + "/" + year, city);
                view.alert("You successfully signed up. You can now login");
        }
    }

    /**
     * helper function to check if all information is legit
     * @param user
     * @param password
     * @param fName
     * @param lName
     * @param bDate
     * @param city
     * @return true if all information is okay
     */
    public boolean valid(String user, String password, String fName, String lName, LocalDate bDate, String city) {
        if (user.equals("") || password.equals("") || fName.equals("") || lName.equals("") || (bDate.toString()).equals("") || city.equals("")){
            view.alert("All field are requierd");
            return false;
        }
        if (Period.between(bDate, LocalDate.now()).getYears() < 18){
            view.alert("Registered users must be at least 18 years old");
            return false;
        }
        if (model.UsernameExist(user)) {
            view.alert("Username is taken");
            return false;
        }
        return true;
    }

    /**
     * get some or none criteria to look for vacation
     *
     * @param from
     * @param to
     * @param departDate
     * @param returnDate
     * @param adultTravelers
     * @param childTravelers
     * @param babyTravelers
     * @param airline
     * @param baggage
     * @param isDirect
     * @param priceFrom
     * @param priceTo
     * @param type
     * @param hotel
     * @param rank
     * @return a list of matching vacations found in the db
     */
    public List<String> searchVac(String from, String to, LocalDate departDate, LocalDate returnDate, String adultTravelers, String childTravelers,
                                  String babyTravelers, String airline, String baggage, boolean isDirect, String priceFrom, String priceTo, String type, String hotel, String rank, boolean Switch) {
       return model.searchVac(from, to, departDate, returnDate, adultTravelers, childTravelers, babyTravelers, airline, baggage,isDirect, priceFrom, priceTo, type, hotel, rank, Switch);

    }

    /**
     * add new vacation by given parameters
     *
     * @param userName
     * @param from
     * @param to
     * @param departDate
     * @param returnDate
     * @param travelersA
     * @param travelersC
     * @param travelersB
     * @param airline
     * @param baggage
     * @param isDirect
     * @param price
     * @param type
     * @param hotelName
     * @param hotelRank
     */
    public void addVac( String userName, String from, String to, LocalDate departDate, LocalDate returnDate, String travelersA, String travelersC, String travelersB, String airline, String baggage, boolean isDirect, String price, String type, String hotelName, String hotelRank, boolean Switch) {
        if (Period.between(LocalDate.now(), departDate).isNegative()){
            view.alert("please enter a depart date starting from today");
        }
        else {
            String departday = departDate.getDayOfMonth() + "";
            String departmonth = departDate.getMonthValue() + "";
            String departyear = departDate.getYear() + "";
            String departDateS = departday + "/" + departmonth + "/" + departyear;
            String returnDateS = "";
            if (returnDate != null) {
                String returnDay = returnDate.getDayOfMonth() + "";
                String returnMonth = returnDate.getMonthValue() + "";
                String returnYear = returnDate.getYear() + "";
                returnDateS = returnDay + "/" + returnMonth + "/" + returnYear;
            }
            model.addVacation(userName, from, to, departDateS, returnDateS, travelersA, travelersC, travelersB, airline,
                    baggage, isDirect, price, type, hotelName, hotelRank, Switch);
        }
    }

    /**
     * a user asked to send buying request on given vacation
     * @param vac- the vacation to request
     */
    public void requestVac(String vac) {
        model.requestVac(vac, getCurrentUser());
    }

    /**
     * this method get a user name and check by model in db for the vacations he published
     * @param user- user Name
     * @return list of vacation
     */
    public List<String> getUserVac(String user) {
        return model.userVac(user);
    }

    /**
     * this method get a user name and check by model in db for the requests he sent
     * @param user- user Name
     * @return list of requests
     */
    public List<String> getUserReq(String user) {
        return model.userReq(user);
    }

    /**
     * marks the request as approved so the user can buy it
     * @param request
     */
    public void approveReq(String request) {
        model.approveReq(request);
    }

    /**
     * marks the request as decline so the user can't buy it- other users can now request for this vacation
     * @param request
     */
    public void declineReq(String request) {
        model.declineReq(request);
    }

    /**
     * this method get a user name and check by model in db for the requests he recieved by other users
     * @param user- user Name
     * @return list of requests
     */
    public List<String> getIncomingReq(String user) {
        return model.userIncomingReq(user);
    }

    /**
     * sends to model purchase details
     * @param vacation- the vacation the user wants to buy
     * @param currentUser- user Name that want to buy
     * @param price- vacation price
     */
    public void order(String vacation, String currentUser, String price) {
        String[] split = vacation.split("Request ID: ");
        String vacationID = split[1].split(",")[0];
        int vacationIDint = Integer.parseInt(vacationID);
        model.vacationPurchase(vacationIDint, currentUser, price);
    }

    /**
     * this method get a user name and check by model in db for his past purchases
     * @param currentUser- user Name
     * @return list of purchases
     */
    public List<String> purchaseHistory(String currentUser) {
        return model.userPurch(currentUser);
    }

    public void approveCash(String request) {
        model.approveCash(request);
    }

    public void sendVacToSwitch(String request,String vacToSwitch) {
        model.sendVacToSwitch(request,vacToSwitch);
    }

    public List<String> getExchangableUserVac(String currentUser) {
        return model.userExchangableVac(currentUser);
    }
}
