package sample;

import java.sql.*;
import java.util.Observable;


public class Model extends Observable implements IModel {


    private boolean isLogin;
    private String currentUser;

    public Model(){
        isLogin=false;
    }
    /*public void update(String fieldName, String newValue, String userName){

        String url= "jdbc:sqlite:C://sqlite/db/users.db";


        String sql = "UPDATE users SET "+fieldName+" = ? , "
                + "WHERE userName = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, newValue);
            pstmt.setString(2, userName);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/


    public void login(String userName, String  password){
        isLogin=false;
        String url="jdbc:sqlite:users.db";
        String sql="SELECT password FROM users WHERE userName = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            // create a new table
            pstmt.setString(1,userName);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                if((rs.getString("password")).compareTo(password)==0) {
                    isLogin = true;
                    currentUser=userName;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public String getDetails(String label){
        String url="jdbc:sqlite:users.db";
        String sql="SELECT "+label+" FROM users WHERE userName = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            // create a new table
            pstmt.setString(1,currentUser);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getString(label);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public boolean isLogin() {
        return isLogin;
    }
}
