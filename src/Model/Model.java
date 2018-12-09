package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;


public class Model extends Observable {


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

        String url= "jdbc:sqlite:Vacation4U.db";

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
        String url="jdbc:sqlite:Vacation4U.db";
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
        String url="jdbc:sqlite:Vacation4U.db";
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
        String url="jdbc:sqlite:Vacation4U.db";
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
            String url = "jdbc:sqlite:Vacation4U.db";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql1)) {
                pstmt.setString(1, user);
                pstmt.setString(2, password);
                pstmt.setString(3, fName);
                pstmt.setString(4, lName);
                pstmt.setString(5, bDate);
                pstmt.setString(6, city);
                pstmt.executeUpdate();
                //currentUser=user;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

    public boolean UsernameExist(String user){

        if(user.equals(currentUser))
            return false;
        String url = "jdbc:sqlite:Vacation4U.db";

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

    public List<String> searchVac(String from, String to, LocalDate depart, LocalDate returnDate, String travelersA, String travelersC, String travelersB, String airline,String baggage, boolean direct, String priceFrom, String priceTo) {
        List<String> list=new LinkedList<>();
        String url = "jdbc:sqlite:Vacation4U.db";
        String vac="SELECT userName, airline, fromC, destination, Depart, Return, travelersA, travelersC, travelersB, direct, price, baggage, type, hotel, hotelRating FROM vacation WHERE airline = ? AND fromC = ? AND destination = ? AND Depart = ? AND Return = ? AND travelersA = ? AND " +
                "travelersC = ? AND travelersB = ? AND direct = ? AND baggage = ? AND price >= ? AND price <= ? ";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(vac)) {
            pstmt1.setString(1,airline);
            pstmt1.setString(2,from);
            pstmt1.setString(3,to);
            pstmt1.setString(4,(depart.getDayOfMonth()+"/"+depart.getMonthValue()+"/"+depart.getYear()));
            pstmt1.setString(5,(returnDate.getDayOfMonth()+"/"+returnDate.getMonthValue()+"/"+returnDate.getYear()));
            pstmt1.setString(6,travelersA);
            pstmt1.setString(7,travelersC);
            pstmt1.setString(8,travelersB);
            pstmt1.setString(9,direct+"");
            pstmt1.setString(10,baggage);
            pstmt1.setString(11,priceFrom);
            pstmt1.setString(12,priceTo);

            ResultSet rs  = pstmt1.executeQuery();
            while (rs.next()) {
                String s= rs.getString("userName");
                s+=("," +rs.getString("airline"));
                s+=("," +rs.getString("fromC"));
                s+=("," +rs.getString("destination"));
                s+=("," +rs.getString("Depart"));
                s+=("," +rs.getString("Return"));
                s+=("," +rs.getString("travelersA"));
                s+=("," +rs.getString("travelersC"));
                s+=("," +rs.getString("travelersB"));
                s+=("," +rs.getString("direct"));
                s+=("," +rs.getString("price"));
                s+=("," +rs.getString("baggage"));
                s+=("," +rs.getString("type"));
                s+=("," +rs.getString("hotel"));
                s+=("," +rs.getString("hotelRating"));
                list.add(s);
            }
        } catch (Exception e){System.out.println(e.getMessage());}
        return list;
    }

    public List<String> searchVacAdv(String airline, LocalDate depart, LocalDate returnDate, boolean direct, String baggage, String travelersA, String travelersC, String travelersB, String to, String from, String type, String hotel, String hotelRating, String priceFrom, String priceTo) {
        List<String> list=new LinkedList<>();
        String url = "jdbc:sqlite:Vacation4U.db";

        String oldUser="";
        String vac="SELECT * FROM vacation WHERE airline = ? AND fromC = ? AND destination = ? AND Depart = ? AND Return = ? AND travelersA = ?" +
                "travelersC = ? AND travelersB = ? AND direct = ? AND baggage = ? AND price >= ? AND price <= ? AND type = ? AND hotel = ? AND hotelRating = ? ";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(vac)) {
            pstmt1.setString(1,airline);
            pstmt1.setString(2,from);
            pstmt1.setString(3,to);
            pstmt1.setString(4,(depart.getDayOfMonth()+"/"+depart.getMonthValue()+"/"+depart.getYear()));
            pstmt1.setString(5,(returnDate.getDayOfMonth()+"/"+returnDate.getMonthValue()+"/"+returnDate.getYear()));
            pstmt1.setString(6,travelersA);
            pstmt1.setString(7,travelersC);
            pstmt1.setString(8,travelersB);
            pstmt1.setString(9,direct+"");
            pstmt1.setString(10,baggage);
            pstmt1.setInt(11,Integer.parseInt(priceFrom));
            pstmt1.setInt(12,Integer.parseInt(priceTo));
            pstmt1.setString(13,type);
            pstmt1.setString(14,hotel);
            pstmt1.setString(15,hotelRating);
            ResultSet rs  = pstmt1.executeQuery();
            while (rs.next()) {
                String s= rs.getString("userName");
                s+=("," +rs.getString("airline"));
                s+=("," +rs.getString("fromC"));
                s+=("," +rs.getString("destination"));
                s+=("," +rs.getString("Depart"));
                s+=("," +rs.getString("Return"));
                s+=("," +rs.getString("travelersA"));
                s+=("," +rs.getString("travelersC"));
                s+=("," +rs.getString("travelersB"));
                s+=("," +rs.getString("direct"));
                s+=("," +rs.getString("price"));
                s+=("," +rs.getString("baggage"));
                s+=("," +rs.getString("type"));
                s+=("," +rs.getString("hotel"));
                s+=("," +rs.getString("hotelRating"));
                list.add(s);
            }
        } catch (Exception e){}
        return list;
    }

    public void addVacation( String userName, String from, String to, String departDate, String returnDate, String travelersA, String travelersC, String travelersB, String airline, String baggage, boolean isDirect, String price, String type, String hotelName, String hotelRank) {
        String sql1 = "INSERT INTO vacation(userName,airline,fromC,destination,Depart,Return,travelersA,travelersC,travelersB,direct,price,baggage,type,hotel,hotelRating) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String url = "jdbc:sqlite:Vacation4U.db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql1)) {
            //pstmt.setString(1, vacID+"");
            pstmt.setString(1, userName);
            pstmt.setString(2, airline);
            pstmt.setString(3, from);
            pstmt.setString(4, to);
            pstmt.setString(5, departDate);
            pstmt.setString(6, returnDate);
            pstmt.setString(7, travelersA);
            pstmt.setString(8, travelersC);
            pstmt.setString(9, travelersB);
            pstmt.setString(10, isDirect+"");
            pstmt.setInt(11, Integer.parseInt(price));
            pstmt.setString(12, baggage);
            pstmt.setString(13, type);
            pstmt.setString(14, hotelName);
            pstmt.setString(15, hotelRank);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> userVac(String userName) {
        List<String> list=new LinkedList<>();
        String url = "jdbc:sqlite:Vacation4U.db";
        String vac="SELECT userName, airline, fromC, destination, Depart, Return, travelersA, travelersC, travelersB, direct, price, baggage, type, hotel, hotelRating FROM vacation WHERE userName = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(vac)) {
            pstmt1.setString(1,userName);
            ResultSet rs  = pstmt1.executeQuery();
            while (rs.next()) {
                String s= rs.getString("userName");
                s+=("," +rs.getString("airline"));
                s+=("," +rs.getString("fromC"));
                s+=("," +rs.getString("destination"));
                s+=("," +rs.getString("Depart"));
                s+=("," +rs.getString("Return"));
                s+=("," +rs.getString("travelersA"));
                s+=("," +rs.getString("travelersC"));
                s+=("," +rs.getString("travelersB"));
                s+=("," +rs.getString("direct"));
                s+=("," +rs.getString("price"));
                s+=("," +rs.getString("baggage"));
                s+=("," +rs.getString("type"));
                s+=("," +rs.getString("hotel"));
                s+=("," +rs.getString("hotelRating"));
                list.add(s);
            }
        } catch (Exception e){System.out.println(e.getMessage());}
        return list;
    }
}
