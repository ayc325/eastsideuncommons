import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Property {
    /*
    * Remember to validate who's a Property Manager.
    * 1. Record Visit Data
    * 2. Record Lease Data
    * 3. Record Move-out Data
    *      a. Auto Set Move-out Date (12 months out from move-in date)
    * 4. Add Person to a lease
    *      a. Gives a list of inputs to ask person
    *      b. First Name, Middle Initial, Last Name, Phone Number, Date of Birth
    */
    /***
     * Add Prospective Tenant (randomize Prosp_Tenantid) and add Person and all the info
     * @param conn
     */
    public static void recordVisitData(Connection conn){
        
    }
    public static void recordLeaseData(Connection conn){

    }
    public static void recordMoveOutData(Connection conn){

    }
    public static void addPersonToLease(Connection conn){

    }
    public static int addPerson(Connection conn){//property manager
        Scanner scnr = new Scanner(System.in);
        int input = 0;
        while(input == 0){
            System.out.println("Which data would you like to add?");
            System.out.println("1. Name");
            System.out.println("2. Phone Number");
            System.out.println("3. Date of Birth"); //Make sure it's in DD-MON-YY format
            System.out.println("4. Payment Method");
            System.out.println("5. Visit Status");
            System.out.println("6. Return to Tenant Menu");
            //Make sure number is valid in Enterprise.java
            if(scnr.hasNextInt()){
                input = scnr.nextInt();
                if(input > 0 && input < 7){
                    return input;
                }else{
                    input = 0;
                    break;
                }
            }else{
                input = 0;
                System.out.println("Invalid input. Try Again. Input must be between 1-6.");
            }
        }
        return input;
        }
    }
