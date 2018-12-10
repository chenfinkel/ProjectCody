package View;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class VacationSearchRes {

    private View view;
    @FXML
    private Label lbl0;
    @FXML
    private Label lbl1;
    @FXML
    private Label lbl2;
    @FXML
    private Label lbl3;
    @FXML
    private Label lbl4;
    @FXML
    private Label lbl5;
    @FXML
    private Label lbl6;
    @FXML
    private Label lbl7;
    @FXML
    private Label lbl8;
    @FXML
    private Label lbl9;
    @FXML
    private Label userName;
    @FXML
    private Button Buy0;
    @FXML
    private Button Buy1;
    @FXML
    private Button Buy2;
    @FXML
    private Button Buy3;
    @FXML
    private Button Buy4;
    @FXML
    private Button Buy5;
    @FXML
    private Button Buy6;
    @FXML
    private Button Buy7;
    @FXML
    private Button Buy8;
    @FXML
    private Button Buy9;


    public void setView(View v){
        view=v;
    }

    public void setGrid( List<String> l){
        if(l.size()==0){
            lbl0.setText("No matches found");
        }
        else {
            int i;
            for (i = 0; i < 10 && i < l.size(); i++) {
                String[] temp = l.get(i).split(",");
                String lab = "Vacation id:"+temp[15]+"  Seller: " + temp[0] + "  From:" + temp[2] + "  To:" + temp[3] + "  Depart:" + temp[4];
                if(!temp[5].equals(" "))
                    lab+=("  Return:" + temp[5]);
                lab+=("  Airline:" + temp[1] + "  Adult Travelers:" + temp[6]);
                if(!temp[7].equals(" "))
                    lab+=("  Child Travelers:" + temp[7]);
                if(!temp[8].equals(" "))
                    lab+=("  Baby Travelers:" + temp[8]);
                lab+="  Direct: ";
                if (temp[9].equals("true"))
                    temp[9] = "Yes";
                else
                    temp[9] = "No";
                lab += temp[9] + "  Price:" + temp[10] + "  Baggage:" + temp[11];
                if (!temp[12].equals(" "))
                    lab += "  Vacation Type:" + temp[12];
                if (!temp[13].equals(" "))
                    lab += "  Hotel Name:" + temp[13];
                if (!temp[14].equals(" "))
                    lab += "  Hotel Rank:" + temp[14];
                if (i == 0) {
                    lbl0.setText(lab);
                    Buy0.setVisible(true);
                }
                if (i == 1) {
                    lbl1.setText(lab);
                    Buy1.setVisible(true);
                }
                if (i == 2) {
                    lbl2.setText(lab);
                    Buy2.setVisible(true);
                }
                if (i == 3) {
                    lbl3.setText(lab);
                    Buy3.setVisible(true);
                }
                if (i == 4) {
                    lbl4.setText(lab);
                    Buy4.setVisible(true);
                }
                if (i == 5) {
                    lbl5.setText(lab);
                    Buy5.setVisible(true);
                }
                if (i == 6) {
                    lbl6.setText(lab);
                    Buy6.setVisible(true);
                }
                if (i == 7) {
                    lbl7.setText(lab);
                    Buy7.setVisible(true);
                }
                if (i == 8) {
                    lbl8.setText(lab);
                    Buy8.setVisible(true);
                }
                if (i == 9) {
                    lbl9.setText(lab);
                    Buy9.setVisible(true);
                }
            }
        }
    }

    public void login(){
        try {
            view.returnLoginPage();
        }catch(Exception e){System.out.println(e.getMessage());}
    }

    public void setCurrentUser(String currentUser) {
        this.userName.setText(currentUser);
    }

    public void returnSearch(){
        try{
            view.goToSearch();
        }catch (Exception e){System.out.println(e.getMessage());}

    }

    public void request0(){
        view.requestVac(lbl0.getText());
    }

    public void request1(){
        view.requestVac(lbl1.getText());
    }

    public void request2(){
        view.requestVac(lbl2.getText());
    }

    public void request3(){
        view.requestVac(lbl3.getText());
    }

    public void request4(){
        view.requestVac(lbl4.getText());
    }

    public void request5(){
        view.requestVac(lbl5.getText());
    }

    public void request6(){
        view.requestVac(lbl6.getText());
    }

    public void request7(){
        view.requestVac(lbl7.getText());
    }

    public void request8(){
        view.requestVac(lbl8.getText());
    }

    public void request9(){
        view.requestVac(lbl9.getText());
    }
}
