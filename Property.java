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
     * link an apartment_id (that is (null) under tenants) to the prospective tenant, the count of the number of apartment id's the prospective tenant table
     * is the visit_status in persons
     * @param conn
     */
    public static int recordVisitData(Connection conn){
        Scanner scnr = new Scanner(System.in);
        Randomize randomize = new Randomize();
        List<Integer> emptyAptId = new ArrayList<>();
        List<Integer> visitedAptId = new ArrayList<>();
        int tempApt = 1;
        int numVisitedApt = 0;
        int personId = addPerson(conn);
        if(personId == 0){
            return 0;
        }
        //no duplicates to assign a new prosp_tenantid, but there can be duplicates in the table when recording apt_id data
        int prosp_tenantid = randomize.randomizeID(2, conn); 
        //addProspTenant(conn, prosp_tenantid, personId);
        
        //print list of empty apartments
        System.out.println("Which apartment did the Prospective Tenant " + prosp_tenantid + " visit?");
        //get empty apartment list
        System.out.println("Empty Apartments:");
        emptyAptId = getEmptyApartments(conn);
        for(int i = 0; i < emptyAptId.size(); i++){
           System.out.println(emptyAptId.get(i));
        }
        System.out.println("Enter ApartmentID one line at a time, duplicates may exist and press 0 when done adding.");
        do{
            if(scnr.hasNextInt()){
                tempApt = scnr.nextInt();
                if(emptyAptId.contains(tempApt)){
                    visitedAptId.add(tempApt);
                    tempApt = 1;
                }else if(tempApt == 0){
                    break;
                }else{
                    System.out.println("Apartment not empty.");
                    tempApt = 1;
                }
            }
        }while(tempApt == 1);

        //insert visitedapt into sql
        numVisitedApt = addVisitedApartment(conn, prosp_tenantid, personId, visitedAptId);

        //insert numVisitedApt into visit_status in person table
        addVisitStatus(numVisitedApt, personId, conn);
        
        System.out.println("Prospective Tenant: " + prosp_tenantid + " has visited " + selectVisitStatus(personId, conn) + "apartments.");
        System.out.println("Those apartments are: ");
        for(int j = 0; j < visitedAptId.size(); j++){
           System.out.println(visitedAptId.get(j));
        }
        return 0;
    }
    public static void recordLeaseData(Connection conn){

    }
    public static void recordMoveOutData(Connection conn){

    }
    public static void addPersonToLease(Connection conn){

    }
    public static int selectVisitStatus(int personId, Connection conn){
        int visit_status = 0;
        String query = " ";
        query = "SELECT visit_status FROM persons WHERE personid = ?";
        try (
            // Create a PreparedStatement with the SQL query
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            // Execute the query and get the result set
            ResultSet rs = preparedStatement.executeQuery();
        ) {
            // Iterate through the result set and add apartment IDs to the list
            while (rs.next()) {
                visit_status = rs.getInt("visit_status");
                return visit_status;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visit_status;
    }
    public static int addVisitStatus(int num, int personId, Connection conn){
        String query = " ";
        query = "UPDATE persons SET visit_status = ? WHERE personid = ?";
        try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
            prepdstatement.setInt(1, num);
            prepdstatement.setInt(2, personId);
            
            int rowsAffected = prepdstatement.executeUpdate(); 
            
            //return rs.getInt(1);
            if(rowsAffected > 0){
                System.out.println("Visit Status successfully updated");
                return num;
            }

        }
        catch(Exception e){
            e.printStackTrace();

        }

        return num;
    }
    /***
     * 
     * @param conn
     * @return
     */
    public static List<Integer> getEmptyApartments(Connection conn){
        List<Integer> emptyAptId = new ArrayList<>();
        String query = " ";
        query = "SELECT apartmentid FROM apartments WHERE tenantid IS NULL";
        try (
            // Create a PreparedStatement with the SQL query
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            // Execute the query and get the result set
            ResultSet rs = preparedStatement.executeQuery();
        ) {
            // Iterate through the result set and add apartment IDs to the list
            while (rs.next()) {
                int apartmentId = rs.getInt("apartmentid");
                emptyAptId.add(apartmentId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emptyAptId;
    }
    public static int addVisitedApartment(Connection conn, int prosp_tenantid, int personId, List<Integer> visitedApt){
        String query = " ";
        int rowsAffected = 0;
        for(int i = 0; i < visitedApt.size(); i++){
            query = "INSERT INTO prospectivetenants (prosp_tenantid, personid, apartmentid) VALUES (?, ?, ?)";
            try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
                prepdstatement.setInt(1, prosp_tenantid);
                prepdstatement.setInt(2, personId);
                prepdstatement.setInt(3, visitedApt.get(i));
                
                rowsAffected = prepdstatement.executeUpdate(); 
                
                //return rs.getInt(1);
                if(rowsAffected > 0){
                    System.out.println("Prospective tenant and visit information successfully added");
                    return rowsAffected;
                }

            }
            catch(Exception e){
                e.printStackTrace();

            }
        }
        return rowsAffected;
    }

    public static int addPerson(Connection conn){//property manager
        Scanner scnr = new Scanner(System.in);
        Randomize randomize = new Randomize();
        Validator validator = new Validator();
        Tenant tenant = new Tenant();
        Menu menu = new Menu();
        int input = 0;
        List <String> name = new ArrayList<>();
        String phoneNumber = " ";
        String birthdate = " ";
        List <String> payment = new ArrayList<>();
        while(input == 0){
            System.out.println("Add Person Information:");
            System.out.println("Name");
                name = tenant.updateName(conn); 
            System.out.println("Phone Number");
                phoneNumber = tenant.updatePhoneNumber(conn);
            System.out.println("Date of Birth"); //Make sure it's in MM/DD/YYYY format
                birthdate = updateBirthdate(conn);

            int personId = randomize.randomizeID(1, conn);

            input = addPerson(name, phoneNumber, birthdate, personId, conn);
            //Make sure number is valid in Enterprise.java
        }
        return input;
    }


    /***
     * 
     * @param name
     * @param phoneNumber
     * @param conn
     * @return
     */
    public static int addPerson(List<String> name, String phoneNumber, String birthdate, int personId, Connection conn){
        String firstName = name.get(0);
        String middleInitial = name.get(1);
        String lastName = name.get(2);
        
        /*
        * running query to update name
        */
        String query;
        
        query = "INSERT INTO persons (personid, first_name, middle_initial, last_name, phone_number, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
            prepdstatement.setInt(1, personId);
            prepdstatement.setString(2, firstName);
            prepdstatement.setString(3, middleInitial);
            prepdstatement.setString(4, lastName);
            prepdstatement.setString(5, phoneNumber);
            prepdstatement.setString(6, birthdate);
            
            int rowsAffected = prepdstatement.executeUpdate(); 
            
            //return rs.getInt(1);
            if(rowsAffected > 0){
                System.out.println("Person successfully added!");
                if(!"(null)".equals(middleInitial)){
                   System.out.println("Your added person is: " + firstName + " " + middleInitial + " " + lastName + ".\n" +
                            "Phone number is " + phoneNumber + ".\n" +
                            "Birthdate is " + birthdate + ".");
                    return personId;
                }else{
                   System.out.println("Your added person is: " + firstName + " " + lastName + ".\n" + 
                            "Phone number is " + phoneNumber + ".\n" +
                            "Birthdate is " + birthdate + ".");
                    return personId;
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();

        }
        return 0;
    }

    public static String updateBirthdate(Connection conn){
        Scanner scnr = new Scanner(System.in);
        Validator validator = new Validator();
        String birthdate = " ";
        //phoneNumber
        do {
            System.out.println("Enter Birthdate MM/DD/YYY");
            birthdate = scnr.nextLine();
            if (validator.isValidBirthdate(birthdate)) {
                return birthdate;
            }else{
                System.out.println("Invalid format. Make sure it's in MM/DD/YYY format.");
            }
        } while (!validator.isValidName(birthdate));
        return birthdate;
    }
}
