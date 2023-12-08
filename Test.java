import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
public class Test {
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
            System.out.println(" ... successfully connected");

        } catch (SQLException sqle) {
            System.out.println("SQLException : " + sqle);
        }
        
        Validator valid = new Validator();
        int prosp_tenantid = 2511;
        System.out.println(valid.idInData(prosp_tenantid, 2, conn));
    }

    
    
}
