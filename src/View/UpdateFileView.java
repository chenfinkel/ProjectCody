package View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class UpdateFileView {

    private View view;
    @FXML
    private TextField user;
    @FXML
    private PasswordField UpdatePass;
    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private TextField city;
    @FXML
    private javafx.scene.control.DatePicker bDate;

    public void setView(View v){
        view=v;
    }

    public void setText(){
        user.setText(view.getDetails("userName"));
        UpdatePass.setText(view.getDetails("password"));
        fName.setText(view.getDetails("firstName"));
        lName.setText(view.getDetails("lastName"));
        String date=view.getDetails("birthDate");
        DateTimeFormatter form= DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate ldate = LocalDate.parse(date,form);
        bDate.setValue(ldate);
        city.setText(view.getDetails("city"));
    }

    public void update(){
        view.update(user.getText(), UpdatePass.getText(),fName.getText(),lName.getText(),bDate.getValue(),city.getText());
        setText();
    }

    public void delete(){
        Alert al=new Alert(Alert.AlertType.CONFIRMATION);
        al.setContentText("Are you sure you want to delete your account?");
        Optional<ButtonType> result = al.showAndWait();
        if (result.get() == ButtonType.OK){
            view.delete();
        }
        else {
            al.close();
        }
    }
}