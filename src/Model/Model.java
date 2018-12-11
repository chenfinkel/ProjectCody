package Model;

import View.Main;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Date;


public class Model extends Observable {


    private boolean isLogin;
    private String currentUser;

    public Model(){
        isLogin=false;
    }

    public void logOff(){
        isLogin = false;
        currentUser = null;
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
                if(rs.getString("status").equals("open")) {
                    String s = "Vacation id: " + rs.getString("id") + "\n";
                    s += ("From: " + rs.getString("fromC"));
                    s += (", To: " + rs.getString("destination"));
                    s += (", Depart: " + rs.getString("Depart"));
                    String ret = rs.getString("Return");
                    if (!ret.equals(""))
                        s += (", Return: " + ret + "\n");
                    else
                        s+="\n";
                    s += ("Price: " + rs.getString("price") + "\n\n");
                    s += ("Airline: " + rs.getString("airline"));
                    s += (", Baggage: " + rs.getString("baggage"));
                    String isDirect = rs.getString("direct");
                    if (isDirect.equals("false"))
                        s += "Direct: no\n";
                    else
                        s += "Direct: yes\n";
                    s += ("Adults: " + rs.getString("travelersA"));
                    String child = rs.getString("travelersC");
                    if (!child.equals(""))
                        s += ", Children: "+child;
                    String baby = rs.getString("travelersB");
                    if (!baby.equals(""))
                        s += ", Babies: "+baby;
                    s+="\n";
                    String Type = rs.getString("type");
                    if (!Type.equals(""))
                        s += "Type: " + Type;
                    String Hotel = rs.getString("hotel");
                    if (!Hotel.equals("")){
                        if (!Type.equals(""))
                            s += ", ";
                        s += "Hotel: " + Hotel;
                    }
                    String Rating = rs.getString("hotelRating");
                    if (!Rating.equals("")) {
                        if (!Type.equals("") || !Hotel.equals(""))
                            s += ", ";
                        s += "Rating: " + Rating;
                    }
                    s+="\n";
                    s += "Seller: " + rs.getString("userName");
                    list.add(s);
                }
            }
        } catch (Exception e){e.printStackTrace();}
        return list;
    }

    public void addVacation( String userName, String from, String to, String departDate, String returnDate, String travelersA, String travelersC, String travelersB, String airline, String baggage, boolean isDirect, String price, String type, String hotelName, String hotelRank) {
        String sql1 = "INSERT INTO vacation(id,userName,airline,fromC,destination,Depart,Return,travelersA,travelersC,travelersB,direct,price,baggage,type,hotel,hotelRating,status) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            pstmt.setString(17, "open");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> userVac(String userName) {
        List<String> list=new LinkedList<>();
        String url = "jdbc:sqlite:Vacation4U.db";
        String vac="SELECT * FROM vacation WHERE userName = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(vac);
             ) {

            pstmt1.setString(1,userName);
            ResultSet rs  = pstmt1.executeQuery();
            while (rs.next()) {
                String s="Vacation ID: "+ rs.getString("id") +
                        ", From: " + rs.getString("fromC")+
                        ", To: " +rs.getString("destination") + "\n" +
                        "Depart: " + rs.getString("Depart")+
                        ", Return: " + rs.getString("Return")+ "\n" +
                        "Price: " + rs.getString("price")+ "\n" +
                        "Status: " + rs.getString("status");
                list.add(s);
            }
        } catch (Exception e){System.out.println(e.getMessage());}
        return list;
    }

    public List<String> userPurch(String userName) {
        List<String> list=new LinkedList<>();
        String url = "jdbc:sqlite:Vacation4U.db";
        String vacPur="SELECT * FROM purchases WHERE buyer = ?";
        String vac="SELECT * FROM vacation WHERE id = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(vacPur);
             PreparedStatement pstmt2 = conn.prepareStatement(vac)) {

            pstmt1.setString(1,userName);
            ResultSet rs  = pstmt1.executeQuery();
            while (rs.next()) {
                pstmt2.setString(1,rs.getString("idVac"));
                ResultSet rs2  = pstmt2.executeQuery();
                String s = "Purchase ID: " + rs.getString("id");
                while(rs2.next()) {
                    s+=", From: " + rs2.getString("fromC") +
                            ", To: " + rs2.getString("destination") + "\n" +
                            "Depart: " + rs2.getString("Depart") +
                            ", Return: " + rs2.getString("Return") + "\n";

                }
                s+="Price: " + rs.getString("price") + ", Date: "+ rs.getString("Date");
                list.add(s);
            }
        } catch (Exception e){e.printStackTrace();}
        return list;
    }

    public void requestVac(String vac, String currentUser) {
        String url = "jdbc:sqlite:Vacation4U.db";
        String[] temp=vac.split("Vacation id: ");
        String[] temp2=temp[1].split("\n");
        int id= Integer.parseInt(temp2[0]);
        String[] temp3=temp[1].split("Seller: ");
        String seller=temp3[temp3.length-1];

        String sql="UPDATE vacation SET status = \"requested\" WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
            pstmt1.setInt(1,id);
            pstmt1.executeUpdate();
        } catch (Exception e){}

        String sql1 = "INSERT INTO requests(id,idVac,seller,buyer,status) VALUES(?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
            pstmt1.setInt(1,Main.idRequest++);
            pstmt1.setInt(2,id);
            pstmt1.setString(3,seller);
            pstmt1.setString(4,currentUser);
            pstmt1.setString(5,"waiting");
            pstmt1.executeUpdate();
        } catch (Exception e){e.printStackTrace();}
    }

    public List<String> userReq(String user) {
        List<String> list=new LinkedList<>();
        String url = "jdbc:sqlite:Vacation4U.db";
        String vacReq="SELECT * FROM requests WHERE buyer = ? AND status != \"closed\"";
        String vac="SELECT * FROM vacation WHERE id = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(vacReq);
             PreparedStatement pstmt2 = conn.prepareStatement(vac)) {

            pstmt1.setString(1,user);
            ResultSet rs  = pstmt1.executeQuery();
            while (rs.next()) {
                pstmt2.setString(1,rs.getString("idVac"));
                ResultSet rs2  = pstmt2.executeQuery();
                String s = "Request ID: " + rs.getString("id")+
                            ", Status: " + rs.getString("status")+"\n";
                while(rs2.next()) {
                            s+="From: " + rs2.getString("fromC") +
                            ", To: " + rs2.getString("destination") + "\n" +
                            "Depart: " + rs2.getString("Depart") +
                            ", Return: " + rs2.getString("Return") + "\n" +
                            "Price: " + rs2.getString("price");
                    list.add(s);
                }
            }
        } catch (Exception e){e.printStackTrace();}
        return list;
    }

    public List<String> userIncomingReq(String user) {
        List<String> list=new LinkedList<>();
        String url = "jdbc:sqlite:Vacation4U.db";
        String vacReq="SELECT * FROM requests WHERE seller = ? AND status=\"waiting\"";
        String vac="SELECT * FROM vacation WHERE id = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(vacReq);
             PreparedStatement pstmt2 = conn.prepareStatement(vac)) {

            pstmt1.setString(1,user);
            ResultSet rs  = pstmt1.executeQuery();
            while (rs.next()) {
                pstmt2.setString(1,rs.getString("idVac"));
                ResultSet rs2  = pstmt2.executeQuery();
                String s = "Request ID: " + rs.getString("id")+
                        ", Status: " + rs.getString("status")+"\n";
                while(rs2.next()) {
                    s+="From: " + rs2.getString("fromC") +
                            ", To: " + rs2.getString("destination") + "\n" +
                            "Depart: " + rs2.getString("Depart") +
                            ", Return: " + rs2.getString("Return") + "\n" +
                            "Price: " + rs2.getString("price");
                    list.add(s);
                }
            }
        } catch (Exception e){e.printStackTrace();}
        return list;
    }

    public void approveReq(String vacation) {
        String url = "jdbc:sqlite:Vacation4U.db";
        String[] temp=vacation.split("Request ID: ");
        int vacID=Integer.parseInt(temp[1].split(",")[0]);

        String sql="UPDATE requests SET status = \"approved\" WHERE id = ? ";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
            pstmt1.setInt(1,vacID);
            pstmt1.executeUpdate();
        } catch (Exception e){e.printStackTrace();}
    }

    public void declineReq(String vacation) {
        String url = "jdbc:sqlite:Vacation4U.db";
        String[] temp=vacation.split("Request ID: ");
        int vacID=Integer.parseInt(temp[1]);

        String sql="UPDATE requests SET status = \"declined\" WHERE id = ? ";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
            pstmt1.setInt(1,vacID);
            pstmt1.executeUpdate();
        } catch (Exception e){e.printStackTrace();}
    }

    public void vacationPurchase(int requestID, String currentUser, String price) {
        String url = "jdbc:sqlite:Vacation4U.db";
        String vac="SELECT * FROM requests WHERE id = ? ";
        String seller = "";
        int vacID = 0;
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(vac)) {
            pstmt1.setInt(1,requestID);
            ResultSet rs  = pstmt1.executeQuery();
            while (rs.next()) {
                seller = rs.getString("seller");
                vacID = rs.getInt("idVac");
            }
        } catch (Exception e){e.printStackTrace();}

        String sql="UPDATE vacation SET status = \"sold\" WHERE id = ? ";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
            pstmt1.setInt(1,vacID);
            pstmt1.executeUpdate();
        } catch (Exception e){e.printStackTrace();}

        String sql1 = "INSERT INTO purchases(id,idVac,Date,seller,buyer,price) VALUES(?,?,?,?,?,?)";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(sql1)) {
            pstmt1.setInt(1,Main.idPurchas++);
            pstmt1.setInt(2,vacID);
            pstmt1.setString(3,date);
            pstmt1.setString(4,seller);
            pstmt1.setString(5,currentUser);
            pstmt1.setString(6,price);
            pstmt1.executeUpdate();
        } catch (Exception e){e.printStackTrace();}

        String sql2="UPDATE requests SET status = \"closed\" WHERE id = ? ";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(sql2)) {
            pstmt1.setInt(1,requestID);
            pstmt1.executeUpdate();
        } catch (Exception e){e.printStackTrace();}

    }
}
