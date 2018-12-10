package Model;

import View.Main;

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

    public List<String> searchVac(String from, String to, LocalDate depart, LocalDate returnDate, String travelersA, String travelersC, String travelersB, String airline, String baggage, boolean direct, String priceFrom, String priceTo, String type, String hotel, String rank) {
        List<String> list=new LinkedList<>();
        String url = "jdbc:sqlite:Vacation4U.db";
        String vac="SELECT * FROM vacation";
        if(!from.equals("")){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE fromC = \""+from+"\"");
        }
        if(!to.equals("")){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE destination = \""+to+"\"");
        }
        if(depart != null){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE Depart = "+depart.getDayOfMonth()+"/"+depart.getMonthValue()+"/"+depart.getYear());
        }
        if(returnDate != null){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE Return = "+returnDate.getDayOfMonth()+"/"+returnDate.getMonthValue()+"/"+returnDate.getYear());
        }
        if(!travelersA.equals("")){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE travelersA = "+travelersA);
        }
        if(!travelersB.equals("")){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE travelersB = "+travelersB);
        }
        if(!travelersC.equals("")){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE travelersC = "+travelersC);
        }
        if(direct){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE direct = \"true\"");
        }
        if(!airline.equals("")){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE airline = \""+airline+"\"");
        }
        if(!baggage.equals("")){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE baggage = \""+baggage+"\"");
        }
        if(!priceFrom.equals("")){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE price >= "+Integer.parseInt(priceFrom));
        }
        if(!priceTo.equals("")){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE price <= "+Integer.parseInt(priceTo));
        }
        if(!hotel.equals("")){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE hotel = \""+hotel+"\"");
        }
        if(!type.equals("")){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE type = \""+type+"\"");
        }
        if(!rank.equals("")){
            vac+=(" INTERSECT SELECT * FROM vacation WHERE hotelRating >= "+Integer.parseInt(rank));
        }

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(vac)) {
            ResultSet rs  = pstmt1.executeQuery();
            while (rs.next()) {
                String s= rs.getString("userName");
                s+=(", " +rs.getString("airline"));
                s+=(", " +rs.getString("fromC"));
                s+=(", " +rs.getString("destination"));
                s+=(", " +rs.getString("Depart"));
                s+=(", " +rs.getString("Return"));
                s+=(", " +rs.getString("travelersA"));
                s+=(", " +rs.getString("travelersC"));
                s+=(", " +rs.getString("travelersB"));
                s+=(", " +rs.getString("direct"));
                s+=(", " +rs.getString("price"));
                s+=(", " +rs.getString("baggage"));
                s+=(", " +rs.getString("type"));
                s+=(", " +rs.getString("hotel"));
                s+=(", " +rs.getString("hotelRating"));
                s+=(", " +rs.getString("id"));
                list.add(s);
            }
        } catch (Exception e){System.out.println(e.getMessage());}
        return list;
    }

    public void addVacation( String userName, String from, String to, String departDate, String returnDate, String travelersA, String travelersC, String travelersB, String airline, String baggage, boolean isDirect, String price, String type, String hotelName, String hotelRank) {
        String sql1 = "INSERT INTO vacation(id,userName,airline,fromC,destination,Depart,Return,travelersA,travelersC,travelersB,direct,price,baggage,type,hotel,hotelRating) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String url = "jdbc:sqlite:Vacation4U.db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql1)) {
            pstmt.setInt(1, Main.idVac++);
            pstmt.setString(2, userName);
            pstmt.setString(3, airline);
            pstmt.setString(4, from);
            pstmt.setString(5, to);
            pstmt.setString(6, departDate);
            pstmt.setString(7, returnDate);
            pstmt.setString(8, travelersA);
            pstmt.setString(9, travelersC);
            pstmt.setString(10, travelersB);
            pstmt.setString(11, isDirect+"");
            pstmt.setInt(12, Integer.parseInt(price));
            pstmt.setString(13, baggage);
            pstmt.setString(14, type);
            pstmt.setString(15, hotelName);
            pstmt.setString(16, hotelRank);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> userVac(String userName) {
        List<String> list=new LinkedList<>();
        String url = "jdbc:sqlite:Vacation4U.db";
        String vac="SELECT * FROM vacation WHERE userName = ? ";
        String vacReq="SELECT buyer FROM requests WHERE idVac = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(vac);
             PreparedStatement pstmt2 = conn.prepareStatement(vacReq)) {

            pstmt1.setString(1,userName);
            ResultSet rs  = pstmt1.executeQuery();
            while (rs.next()) {
                pstmt2.setString(1,rs.getString("id"));
                ResultSet rs2  = pstmt2.executeQuery();
                String s="Id: "+ rs.getString("id") +
                        ", From: " + rs.getString("fromC")+
                        ", To: " +rs.getString("destination") + "\n" +
                        "Depart: " + rs.getString("Depart")+
                        ", Return: " + rs.getString("Return")+ "\n" +
                        "Price: " + rs.getString("price");
                while (rs2.next()){
                    s+= ", Requested by: " + rs2.getString("buyer");
                }
                list.add(s);
            }
        } catch (Exception e){System.out.println(e.getMessage());}
        return list;
    }

    public void requestVac(String vac, String currentUser) {
        String url = "jdbc:sqlite:Vacation4U.db";
        String[] temp=vac.split("  Seller: ");
        String[] temp2=temp[0].split(": ");
        int id= Integer.parseInt(temp2[1]);
        String[] temp3=temp[1].split("  ");
        String seller=temp3[0];

        String sql="UPDATE status FROM vacation WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
            pstmt1.setInt(1,id);
            pstmt1.executeUpdate();
        } catch (Exception e){}

        String sql1 = "INSERT INTO requests(idVac,seller,buyer) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
            pstmt1.setInt(1,id);
            pstmt1.setString(2,seller);
            pstmt1.setString(3,currentUser);
            pstmt1.executeUpdate();
        } catch (Exception e){e.printStackTrace();}
    }
}
