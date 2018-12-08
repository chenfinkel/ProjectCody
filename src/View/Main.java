package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

public class Main extends Application {

    public static Stage primStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader=new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("resources/VacationSearch.fxml").openStream());
        VacationSearchView viewControl = fxmlLoader.getController();
        viewControl.setView(new View());
        viewControl.setCombos();
        primaryStage.setTitle("Vacation4U");
        Scene scene = new Scene(root, 1000, 650);
        primaryStage.setScene(scene);
        primStage=primaryStage;
        primStage.show();
    }


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

    public static void createVacationTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:Vacation4U.db";
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS vacation (\n"
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
                + "price text NOT NULL, \n"
                + "baggage text, \n"
                + "type text, \n"
                + "hotel text, \n"
                + "hotelRating text \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


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


    public static void main(String[] args) {
        createNewDatabase();
        createUsersTable();
        createVacationTable();
        launch(args);
    }
}
