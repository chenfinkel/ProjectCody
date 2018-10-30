package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class View {

    private Controller control;
    private Stage primStage;
    private Stage updateStage;

    public View(){
        control=new Controller();
        control.setView(this);
    }

    public void setController(Controller c){
        control=c;
    }

    public void setPrimStage(Stage prim){
        primStage=prim;
    }

    @FXML
    private javafx.scene.control.TextField userName;
    @FXML
    private javafx.scene.control.PasswordField password;
    @FXML
    private javafx.scene.control.TextField user;
    @FXML
    private javafx.scene.control.PasswordField UpdatePass;
    @FXML
    private javafx.scene.control.TextField fName;
    @FXML
    private javafx.scene.control.TextField lName;
    @FXML
    private javafx.scene.control.TextField city;
    @FXML
    private javafx.scene.control.TextField searchText;
    @FXML
    private javafx.scene.control.TextField signUpUser;
    @FXML
    private javafx.scene.control.TextField signUpPass;
    @FXML
    private javafx.scene.control.TextField signUpFname;
    @FXML
    private javafx.scene.control.TextField signUpLname;
    @FXML
    private javafx.scene.control.DatePicker signUpBdate;
    @FXML
    private javafx.scene.control.TextField signUpCity;
    @FXML
    private javafx.scene.control.Label SearchUsername;
    @FXML
    private javafx.scene.control.Label SearchFullName;
    @FXML
    private javafx.scene.control.Label SearchBdate;
    @FXML
    private javafx.scene.control.Label SearchCity;
    @FXML
    private javafx.scene.control.Label currentUser;
    @FXML
    private javafx.scene.control.DatePicker bDate;


    public void signIn(){
        control.login(userName.getText(),password.getText());
        userName.clear();
        password.clear();
    }

    public void setUpdateStage(Stage s) {
        updateStage = s;
    }

    public void goToProfile(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/UpdateFile.fxml"));
        try {
            Parent root1 = fxmlLoader.load();
            Stage updateStage = new Stage();
            updateStage.setScene(new Scene(root1));
            View viewControl = fxmlLoader.getController();
            viewControl.setController(control);
            viewControl.setUpdateStage(updateStage);
            viewControl.setText();
            updateStage.setTitle("Settings");
            updateStage.show();
        }catch (Exception e) { }
    }

    public void login() throws Exception{
        Stage window=primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("resources/UserPage.fxml").openStream()), 1000, 650));
        View viewControl = fxmlLoader.getController();
        viewControl.setPrimStage(window);
        viewControl.setController(control);
        control.setView(viewControl);
        viewControl.currentUser.setText(control.getCurrentUser());
    }


    public void incorrectLogin(){
        Alert al=new Alert(Alert.AlertType.INFORMATION);
        al.setContentText("Username or password are incorrect");
        al.show();
    }

    public void notFound(){
        Alert al=new Alert(Alert.AlertType.INFORMATION);
        al.setContentText("Username not found");
        al.show();
    }

    public void getMeIn(){
        control.signUp(signUpUser.getText(),signUpPass.getText(),
                signUpFname.getText(),signUpLname.getText(),signUpBdate.getValue(),signUpCity.getText());
    }

    public void clear(){
        signUpUser.clear();
        signUpPass.clear();
        signUpFname.clear();
        signUpLname.clear();
        signUpBdate.getEditor().clear();
        signUpCity.clear();
    }

    public void alert(String alertMessage) {
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setContentText(alertMessage);
        al.show();
    }


    public void setText(){
        user.setText(control.getDetails("userName"));
        UpdatePass.setText(control.getDetails("password"));
        fName.setText(control.getDetails("firstName"));
        lName.setText(control.getDetails("lastName"));
        String date=control.getDetails("birthDate");
        DateTimeFormatter form= DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate ldate = LocalDate.parse(date,form);
        bDate.setValue(ldate);
        city.setText(control.getDetails("city"));
    }

    public void update(){
        control.update(user.getText(), UpdatePass.getText(),fName.getText(),lName.getText(),bDate.getValue(),city.getText());
        setText();
    }

    public void delete(){
        Alert al=new Alert(Alert.AlertType.CONFIRMATION);
        al.setContentText("Are you sure you want to delete your account?");
        Optional<ButtonType> result = al.showAndWait();

        if (result.get() == ButtonType.OK){

            control.delete();
            updateStage.close();
        }
        else {
            al.close();
        }
    }

    public void returnMain() throws Exception{
        Stage window=primStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        window.setScene(new Scene(fxmlLoader.load(getClass().getResource("resources/View.fxml").openStream()), 1000, 650));
        View viewControl = fxmlLoader.getController();
        viewControl.setPrimStage(window);
        viewControl.setController(control);
        control.setView(viewControl);
    }


    public void search(){
        control.search(searchText.getText());
        searchText.clear();
    }

    public void showSearch(String user, String fName, String lName, String bDate, String city) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/Search.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        View viewControl = fxmlLoader.getController();
        viewControl.setController(control);
        viewControl.setSearchText(user,fName,lName,bDate,city);
        stage.setTitle("Search Result");
        stage.show();
    }

    public void setSearchText(String user, String fName, String lName, String bDate, String city){
        SearchUsername.setText(user);
        SearchFullName.setText(fName + " " + lName);
        SearchBdate.setText(bDate);
        SearchCity.setText(city);
    }

    public void LogOff(){
        control.logOff();
    }
}
