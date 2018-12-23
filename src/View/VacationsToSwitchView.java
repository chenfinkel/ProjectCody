package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class VacationsToSwitchView {

    private View view;
    @FXML
    private ListView viewList;


    public void setView(View v){
        view=v;
    }

    public void setVacations(String currentUser){
        viewList.setVisible(false);
        List<String> l=view.getUserVac(currentUser);
        viewList.setVisible(true);
        ObservableList vacations = FXCollections.observableArrayList();
        if(l.size()==0){
            vacations.add("No vacations added");
        }
        else {
            for (int i = 0; i < l.size(); i++) {
                vacations.add(l.get(i));
            }
        }
        viewList.setItems(vacations);
    }
}
