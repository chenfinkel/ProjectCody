package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

public class AddVacationView {

    private View view;
    private String currentUser;
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private TextField airline;
    @FXML
    private ComboBox<String> travelersA;
    @FXML
    private ComboBox<String> travelersB;
    @FXML
    private ComboBox<String> travelersC;
    @FXML
    private DatePicker departDate;
    @FXML
    private DatePicker returnDate;
    @FXML
    private ComboBox<String> baggage;
    @FXML
    private TextField price;
    @FXML
    private CheckBox isDirect;
    @FXML
    private ComboBox<String> type;
    @FXML
    private TextField hotelName;
    @FXML
    private ComboBox<String> hotelRank;


    public void setCombos(){
        Set<String> items=new HashSet<>();
        for(int i=1; i<11; i++)
            items.add(i+"");
        ObservableList<String> list= FXCollections.observableArrayList(items);
        travelersA.setItems(list);
        travelersC.setItems(list);
        travelersB.setItems(list);
        baggage.getItems().addAll("None","Trolly","10kg suitcase","20kg suitcase");
        type.getItems().addAll("Exotic","Urban");
        hotelRank.getItems().addAll("1","2","3","4","5");
    }

    public void setCurrentUser(String userName){
        currentUser=userName;
    }

    private void clear(){
        from.clear();
        to.clear();
        airline.clear();
        travelersA.getSelectionModel().clearSelection();
        travelersB.getSelectionModel().clearSelection();
        travelersC.getSelectionModel().clearSelection();
        departDate.getEditor().clear();
        returnDate.getEditor().clear();
        baggage.getSelectionModel().clearSelection();
        price.clear();
        isDirect.setSelected(false);
        type.getSelectionModel().clearSelection();
        hotelName.clear();
        hotelRank.getSelectionModel().clearSelection();
    }


    public void setView(View v) {
        view=v;
    }

    public void add(){
        if(from.getText().equals("") || to.getText().equals("") || departDate.toString().equals("") || travelersA.getValue()==null
                || airline.getText().equals("") || baggage.getValue()==null || price.getText().equals("")){
            view.alert("All fields are required!");
        }
        else if (Period.between(LocalDate.now(), departDate.getValue()).isNegative()){
            view.alert("please enter a depart date starting from today");
        }
        else if(!isNumber( price.getText())){
            view.alert("price must be a valid number!");
        }
        else {
            if (!returnDate.getEditor().getText().equals("")) {
                if (returnDate.getValue().isBefore(departDate.getValue())) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setContentText("Return date must be later than depart date");
                    al.show();
                    returnDate.getEditor().clear();
                    return;
                }
            }
            String StravelersC="", StravelersB="";
            if(travelersC.getValue()!=null)
                StravelersC=travelersC.getValue();
            if(travelersB.getValue()!=null)
                StravelersB=travelersB.getValue();
            view.addVac(currentUser, from.getText(), to.getText(), departDate.getValue(), returnDate.getValue(), travelersA.getValue(),
                    StravelersC, StravelersB, airline.getText(), baggage.getValue(),
                    isDirect.isSelected(), price.getText(), type.getValue(), hotelName.getText(), hotelRank.getValue());
            view.alert("vacation added");
            clear();
        }
    }

    private boolean isNumber(String text) {
        for(int i=0; i<text.length(); i++){
            if(text.charAt(i)<'0' || text.charAt(i)>'9')
                return false;
        }
       return true;
    }
}
