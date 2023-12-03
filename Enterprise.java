
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Enterprise {
    public static void main(String[] args) {
        //Connecting to database
        Scanner scnr = new Scanner(System.in);
        System.out.println("enter Oracle user id:");
        String userId = scnr.nextLine();
        System.out.println("enter Oracle password for " + userId + ":");
        String password = scnr.nextLine();

        Connection conn = null;
        try {
            System.out.print("Connecting to " + userId + ":");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", userId, password);
            // Statement stmt = conn.createStatement();
            // //… Do Actual Work ….
            // stmt.close();
            // conn.close();

        } catch (SQLException sqle) {
            System.out.println("SQLException : " + sqle);
        }
        System.out.println(" ... successfully connected");

    }
}
