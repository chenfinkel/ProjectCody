package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private Model model;
    private View view;

    public  Controller(Model m, View v){
        this.model = m;
        this.view = v;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    /*public void update(){}

    public void updateFile(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Update");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("UpdateFile.fxml").openStream());
            Scene scene = new Scene(root, 400, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }*/
}
