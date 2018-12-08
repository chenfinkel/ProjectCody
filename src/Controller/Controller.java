package Controller;

import Model.Model;
import View.View;

import java.time.LocalDate;
import java.time.Period;

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
            view.returnLoginPage();
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

    public void search(String user) {
        if (!user.equals("")) {
            try {
                if (model.getDetails("userName", user).equals("")) {
                    view.notFound();
                } else {
                view.showSearch(user, model.getDetails("firstName", user), model.getDetails("lastName", user),
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

    public void searchVac(String from, String to, LocalDate depart, LocalDate returnDate, String travelers) {
        model.searchVac(from, to, depart, returnDate, travelers);
    }
}
