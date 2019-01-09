package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

public class Main extends Application {


    public static int idVac;
    public static int idPurchas;
    public static int idRequest;
    public static int idExchange;

    @Override
    public void start(Stage primaryStage) throws Exception{
        View view = new View();
        view.start(primaryStage);
    }

    /**
     * creates the db of the system
     */
    public static void createNewDatabase() {
        String url = "jdbc:sqlite:Vacation4U.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * creates vacation table
     */
    public static void createVacationTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Vacation4U.db";
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS vacation (\n"
                + "id INTEGER PRIMARY KEY, \n"
                + "userName text NOT NULL, \n"
                + "airline text NOT NULL, \n"
                + "fromC text NOT NULL, \n"
                + "destination text NOT NULL, \n"
                + "Depart text NOT NULL, \n"
                + "Return text, \n"
                + "travelersA text NOT NULL, \n"
                + "travelersC text, \n"
                + "travelersB text, \n"
                + "direct text, \n"
                + "price INTEGER NOT NULL, \n"
                + "baggage text, \n"
                + "type text, \n"
                + "hotel text, \n"
                + "hotelRating INTEGER, \n"
                + "status text, \n"
                + "switch text, \n"
                + "FOREIGN KEY(userName) REFERENCES users(userName) \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * create purchases table
     */
    public static void createPurchaseTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Vacation4U.db";
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS purchases (\n"
                + "id INTEGER PRIMARY KEY, \n"
                + "idVac INTEGER NOT NULL, \n"
                + "Date text, \n"
                + "seller text NOT NULL, \n"
                + "buyer text NOT NULL, \n"
                + "price text NOT NULL, \n"
                + "buyerPhone text, \n"
                + "FOREIGN KEY(idVac) REFERENCES vacation(id), \n"
                + "FOREIGN KEY(seller) REFERENCES users(userName), \n"
                + "FOREIGN KEY(buyer) REFERENCES users(userName) \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * creats buy requests table
     */
    public static void requestsTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Vacation4U.db";
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS requests (\n"
                + "id INTEGER PRIMARY KEY, \n"
                + "idVac INTEGER NOT NULL, \n"
                + "seller text NOT NULL, \n"
                + "buyer text NOT NULL, \n"
                + "ExchangeVacID INTEGER, \n"
                + "status text NOT NULL, \n"
                + "isSwitch text, \n"
                + "FOREIGN KEY(idVac) REFERENCES vacation(id), \n"
                + "FOREIGN KEY(ExchangeVacID) REFERENCES vacation(id), \n"
                + "FOREIGN KEY(seller) REFERENCES users(userName), \n"
                + "FOREIGN KEY(buyer) REFERENCES users(userName) \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * create users table
     */
    public static void createUsersTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Vacation4U.db";
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "userName text PRIMARY KEY,\n"
                + "password text NOT NULL,\n"
                + "firstName text, \n"
                + "lastName text, \n"
                + "birthDate text, \n"
                + "city text \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * get and set serial number for purchase,request and vacation
     * the method look for max id in the tables and sets the next id to use
     */
    public static void getID(){
        String url= "jdbc:sqlite:Vacation4U.db";

        String sql = "SELECT MAX(id) FROM vacation ";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt1.executeQuery();
            while (rs.next()) {
                idVac = rs.getInt("MAX(id)");
                idVac++;
            }
        }catch (Exception e){e.printStackTrace();}

        sql="SELECT MAX(id) FROM purchases ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt1.executeQuery();
            while (rs.next()) {
                idPurchas = rs.getInt("MAX(id)");
                idPurchas++;
            }
        }catch (Exception e){e.printStackTrace();}

        sql="SELECT MAX(id) FROM requests ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt1.executeQuery();
            while (rs.next()) {
                idRequest = rs.getInt("MAX(id)");
                idRequest++;
            }
        }catch (Exception e){e.printStackTrace();}
    }

    /**
     * create exchanges table
     */
    public static void createExchangeTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Vacation4U.db";
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS exchanges (\n"
                + "id INTEGER PRIMARY KEY, \n"
                + "idVac1 INTEGER NOT NULL, \n"
                + "idVac2 INTEGER NOT NULL, \n"
                + "Date text, \n"
                + "FOREIGN KEY(idVac) REFERENCES vacation(id), \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        createNewDatabase();
        createUsersTable();
        createVacationTable();
        createPurchaseTable();
        requestsTable();
        getID();
        launch(args);
    }
}
