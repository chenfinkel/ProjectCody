package View;

import Controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;

public class VacationSearchView {

    private View view;
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private DatePicker departDate;
    @FXML
    private DatePicker returnDate;
    @FXML
    private ComboBox<Integer> travelers;
    @FXML
    private TextField searchText;

    public void setView(View v){
        view=v;
    }

    public VacationSearchView(){
        List<Integer> num=new LinkedList<>();
        for(int i=1; i<11; i++)
            num.add(i);
        travelers=new ComboBox<>();
        travelers.setItems(FXCollections.observableList(num));
    }

    public void searchVac(){
        if(returnDate.getValue().isBefore(departDate.getValue())){
            Alert al=new Alert(Alert.AlertType.INFORMATION);
            al.setContentText("Return date must be later than depart date");
            al.show();
            returnDate.getEditor().clear();
        }
        view.searchVac(from.getText(),to.getText(),departDate.getValue(),returnDate.getValue(),travelers.getEditor().getText());
    }

    public void goToLogin(){
        try {
            view.returnLoginPage();
        }catch(Exception e){}
    }

    public void searchUser(){
        view.search(searchText.getText());
        searchText.clear();
    }
}
