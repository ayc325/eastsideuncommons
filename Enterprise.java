
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

        mainMenu(conn, 1);

    }

    /***
     * 
     * @param conn
     * @param menuType- 0. Main Menu, 1. Tenant Menu
     */
    public static void mainMenu(Connection conn, int menuType){
        Menu menu = new Menu();
        int temp = 0;
        int num = 0;
        switch(menuType){
            case 0:
                num = menu.printMenu(conn);
                break;
            case 1:
                num = menu.printTenantMenu(conn);
                break;
        }    
        while(num == -1){
            mainMenu(conn, 0);
        }
    
        if(num == 5){
            try {
                if (conn != null && !conn.isClosed()) {
                    // Close the connection
                    System.out.println("Closing connection...");
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
}
