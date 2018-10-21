package sample;
import com.sun.org.apache.xpath.internal.operations.String;
import javafx.scene.input.KeyCode;

import java.sql.*;

import java.util.Observable;


public class Model extends Observable implements IModel {

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

}
