package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.List;

public class VacationsToSwitchView {

    private View view;
    @FXML
    private ListView viewList;

    private String vacToSwitch;

    private ArrayList<Button> approves;

    private ArrayList<String> vacation;


    public void setView(View v){
        view=v;
    }

    public void setVacations(String currentUser, String vacToSwitch){
        this.vacToSwitch=vacToSwitch;
        viewList.setVisible(false);
        List<String> l=view.getExchangableUserVac(currentUser);
        vacation=new ArrayList<>();
        approves = new ArrayList<>();
        viewList.setVisible(true);
        ObservableList vacations = FXCollections.observableArrayList();
        if(l.size()==0){
            vacations.add("No vacations added");
        }
        else {
            for (int i = 0; i < l.size(); i++) {
                HBox hbox = new HBox();
                vacation.add(l.get(i));
                Label label = new Label(l.get(i));
                Button app = new Button("exchange");
                app.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        sendVacToSwitch((Button) e.getSource());
                    }
                });
                approves.add(app);
                app.setLayoutY(label.getHeight());
                hbox.setSpacing(20);
                hbox.setHgrow(label, Priority.ALWAYS);
                hbox.setHgrow(app, Priority.ALWAYS);
                hbox.getChildren().addAll(label, app);
                vacations.add(hbox);
                ;
            }
        }
        viewList.setItems(vacations);
    }

    private void sendVacToSwitch(Button b) {
        String request = vacation.get(approves.indexOf(b));
        view.sendVacToSwitch(request,vacToSwitch);
        b.setDisable(true);
        view.alert("waiting for seller approval");
    }
}
