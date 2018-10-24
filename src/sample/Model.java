package sample;

import java.sql.*;
import java.util.Observable;


public class Model extends Observable implements IModel {


    private boolean isLogin;
    private String currentUser;

    public Model(){
        isLogin=false;
    }


    public void update(String password, String fName, String lName, String bDate, String city){

        String url= "jdbc:sqlite:users.db";

        String sql = "UPDATE users SET password = ? , "+"firstName = ? , "+"lastName = ? , "+"birthDate = ? , "+"city = ?"
                + " WHERE userName = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, password);
            pstmt.setString(2, fName);
            pstmt.setString(3, lName);
            pstmt.setString(4, bDate);
            pstmt.setString(5, city);
            pstmt.setString(6, currentUser);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


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

    public void delete(){
        String url="jdbc:sqlite:users.db";
        String sql="DELETE FROM users WHERE userName = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setString(1,currentUser);
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
