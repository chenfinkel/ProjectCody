package Controller;

import Model.Model;
import View.View;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Controller {

    private Model model;
    private View view;

    public Controller(){
        model=new Model();
    }

    public String getCurrentUser(){
        return model.getCurrentUser();
    }

    public void setView(View v){
        view=v;
    }

    public boolean login(String userName, String password){
        if (!userName.equals("") && !password.equals(""))
            model.login(userName, password);
        return model.isLogin();
    }

    public void logOff(){
        model.logOff();
        try {
            view.goToSearch();
        } catch (Exception e){

        }
    }

    public String getDetails(String label){
        return model.getDetails(label,"");
    }

    public void update(String user, String password, String fName, String lName, LocalDate bDate, String city){
        if (valid(user, password, fName, lName, bDate, city)) {
            String day = bDate.getDayOfMonth()+"";
            String month = bDate.getMonthValue() + "";
            String year = bDate.getYear() + "";
            model.update(user, password, fName, lName, day, month, year, city);
            view.alert("Your account has been updated");
        }
    }

    public void delete(){
        model.delete();
        try {
            view.returnLoginPage();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void searchUser(String user) {
        if (!user.equals("")) {
            try {
                if (model.getDetails("userName", user).equals("")) {
                    view.notFound();
                } else {
                view.showUserSearch(user, model.getDetails("firstName", user), model.getDetails("lastName", user),
                        model.getDetails("birthDate", user), model.getDetails("city", user));
                }
            } catch (Exception e) {
            }
        }
    }

    public void signUp(String user, String password, String fName, String lName, LocalDate bDate, String city){
        if (valid(user, password, fName, lName, bDate, city)){
                String day = bDate.getDayOfMonth() + "";
                String month = bDate.getMonthValue() + "";
                String year = bDate.getYear() + "";
                model.signUp(user, password, fName, lName, day + "/" + month + "/" + year, city);
                view.alert("You successfully signed up. You can now login");
        }
    }

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

    public List<String> searchVac(String from, String to, LocalDate departDate, LocalDate returnDate, String adultTravelers, String childTravelers,
                                  String babyTravelers, String airline, String baggage, boolean isDirect, String priceFrom, String priceTo, String type, String hotel, String rank) {
       return model.searchVac(from, to, departDate, returnDate, adultTravelers, childTravelers, babyTravelers, airline, baggage,isDirect, priceFrom, priceTo, type, hotel, rank);

    }

    public void addVac( String userName, String from, String to, LocalDate departDate, LocalDate returnDate, String travelersA, String travelersC, String travelersB, String airline, String baggage, boolean isDirect, String price, String type, String hotelName, String hotelRank) {
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
                    baggage, isDirect, price, type, hotelName, hotelRank);
        }
    }

    public void requestVac(String vac) {
        model.requestVac(vac, getCurrentUser());
    }

    public List<String> getUserVac(String user) {
        return model.userVac(user);
    }

    public List<String> getUserReq(String user) {
        return model.userReq(user);
    }

    public void approveReq(String request) {
        model.approveReq(request);
    }

    public void declineReq(String request) {
        model.declineReq(request);
    }

    public List<String> getIncomingReq(String user) {
        return model.userIncomingReq(user);
    }

    public void order(String vacation, String currentUser, String price) {
        String[] split = vacation.split("Request ID: ");
        String vacationID = split[1].split(",")[0];
        int vacationIDint = Integer.parseInt(vacationID);
        model.vacationPurchase(vacationIDint, currentUser, price);
    }

    public List<String> purchaseHistory(String currentUser) {
        return model.userPurch(currentUser);
    }
}
