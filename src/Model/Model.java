package Model;

import java.sql.*;
import java.util.Observable;


public class Model extends Observable implements IModel {


    private boolean isLogin;
    private String currentUser;

    public Model(){
        isLogin=false;
    }

    public void logOff(){
        isLogin = false;
        currentUser = "";
    }

    public String getCurrentUser(){
        return currentUser;
    }

    public void update(String user, String password, String fName, String lName, String day, String month, String year, String city){

        String url= "jdbc:sqlite:users.db";

        String sql = "UPDATE users SET userName = ? , "+"password = ? , "+"firstName = ? , "+"lastName = ? , "+"birthDate = ? , "+"city = ?"
                + " WHERE userName = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, user);
            pstmt.setString(2, password);
            pstmt.setString(3, fName);
            pstmt.setString(4, lName);
            pstmt.setString(5, day+"/"+month+"/"+year);
            pstmt.setString(6, city);
            pstmt.setString(7, currentUser);
            currentUser=user;
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

    public String getDetails(String label, String user){
        String ans = "";
        if (user.equals(""))
            user = currentUser;
        String url="jdbc:sqlite:users.db";
        String sql="SELECT "+label+" FROM users WHERE userName = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setString(1,user);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                ans = rs.getString(label);
            }
            return ans;
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
        logOff();
    }

    public void signUp(String user, String password, String fName, String lName, String bDate, String city){
            String sql1 = "INSERT INTO users(userName,password,firstName,lastName,birthDate,city) VALUES(?,?,?,?,?,?)";
            String url = "jdbc:sqlite:users.db";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql1)) {
                pstmt.setString(1, user);
                pstmt.setString(2, password);
                pstmt.setString(3, fName);
                pstmt.setString(4, lName);
                pstmt.setString(5, bDate);
                pstmt.setString(6, city);
                pstmt.executeUpdate();
                currentUser=user;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

    public boolean UsernameExist(String user){

        if(user.equals(currentUser))
            return false;
        String url = "jdbc:sqlite:users.db";

        String oldUser="";
        String checkUser="SELECT userName FROM users WHERE userName = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(checkUser)) {
            pstmt1.setString(1,user);
            ResultSet rs  = pstmt1.executeQuery();
            while (rs.next()) {
                oldUser= rs.getString("userName");
            }
        } catch (Exception e){}
        if (oldUser.equals(""))
            return false;
        return true;
    }
}
