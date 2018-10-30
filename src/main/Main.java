package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader=new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View.fxml").openStream());
        View viewControl = fxmlLoader.getController();
        viewControl.setPrimStage(primaryStage);
        primaryStage.setTitle("Vacation4U");
        Scene scene = new Scene(root, 1000, 650);
        //scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Connect to a sample database
     *
     */
    public static void createNewDatabase() {

        String url = "jdbc:sqlite:users.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:users.db";

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
            System.out.println("table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {

        createNewDatabase();
        createNewTable();
        launch(args);
    }
}
