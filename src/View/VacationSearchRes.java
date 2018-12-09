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
    private Button buy0;
    @FXML
    private Button buy1=new Button("Buy");
    @FXML
    private Button buy2=new Button("Buy");
    @FXML
    private Button buy3=new Button("Buy");
    @FXML
    private Button buy4=new Button("Buy");
    @FXML
    private Button buy5=new Button("Buy");
    @FXML
    private Button buy6=new Button("Buy");
    @FXML
    private Button buy7=new Button("Buy");
    @FXML
    private Button buy8=new Button("Buy");
    @FXML
    private Button buy9=new Button("Buy");


    public void setView(View v){
        view=v;
    }

    public void setGrid( List<String> l){
        int i;
        for(i=0; i<10 && i<l.size(); i++){
            String[]temp=l.get(i).split(",");
            String lab="Seller: "+temp[0]+"  Airline: "+temp[1]+"  From: "+temp[2]+"  To: "+temp[3]+"  Depart: "+temp[4]+"  Return: "+temp[5]+"  Adult Travelers: "
                    +temp[6]+"  Child Travelers: "+temp[7]+"  Baby Travelers: "+temp[8]+"  Direct: ";
            if(temp[9].equals("true"))
                temp[9]="Yes";
            else
                temp[9]="No";
            lab+=temp[9]+"  Price: "+temp[10]+"  Baggage: "+temp[11];
            if(!temp[12].equals(""))
                lab+="  Vacation Type: "+temp[12];
            if(!temp[13].equals(""))
                lab+="  Hotel Name: "+temp[13];
            if(!temp[14].equals(""))
                lab+="  Hotel Rank: "+temp[14];
            if(i==0) {
                lbl0.setText(lab);
                buy0.setDisable(false);
            }
            if(i==1) {
                lbl1.setText(lab);
                buy1.setVisible(true);
            }
            if(i==2) {
                lbl2.setText(lab);
                buy2.setVisible(true);
            }
            if(i==3) {
                lbl3.setText(lab);
                buy3.setVisible(true);
            }
            if(i==4) {
                lbl4.setText(lab);
                buy4.setVisible(true);
            }
            if(i==5) {
                buy5.setVisible(true);
                lbl5.setText(lab);
            }
            if(i==6) {
                lbl6.setText(lab);
                buy6.setVisible(true);
            }
            if(i==7) {
                lbl7.setText(lab);
                buy7.setVisible(true);
            }
            if(i==8) {
                lbl8.setText(lab);
                buy8.setVisible(true);
            }
            if(i==9) {
                lbl9.setText(lab);
                buy9.setVisible(true);
            }
        }
    }


    public void returnSearch(){
        try{
            view.goToSearch();
        }catch (Exception e){System.out.println(e.getMessage());}

    }
}
