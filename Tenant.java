import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
    * Prints Menu for Tenants
    * 
    * 1. Check payment status
    *      a. Amount due if any
    *      b. Provide like an invoice to view what amount due consists of (monthly charge, rent)
    * 2. Make rental payment
    *      a. Update data to subtract from amount due
    *      b. If amount due is 0, print a message to let tenant know that there's nothing to pay
    * 3. Update personal data 
    *      a. First Name, Middle Initial, Last Name, Phone Number, Date of Birth
    * @return void
     */
public class Tenant {
    /***
     * checksPaymentStatus based on amountDue.
     * @param id
     * @param conn
     * @return String
     */
    public String checkPaymentStatus(int id, Connection conn){
        double initialAmount, newAmount = 0.0;
        initialAmount = amountDue(id, conn);
        if(initialAmount > 0){
            newAmount = updateAmountDue(1, id, conn);
            return "Unpaid. Amount Due: $" + newAmount;
        }else{
            return "Paid";
        }
    }
    /***
     * returns amountDue
     * @param id
     * @param conn
     * @return double
     */
    public static double amountDue(int id, Connection conn){
            String query = "SELECT amount_due FROM tenants WHERE tenantid = ?";
            try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
                prepdstatement.setInt(1, id);
    
                ResultSet rs = prepdstatement.executeQuery();
                
                //return rs.getInt(1);
                if(rs.next()){
                    double amount = rs.getDouble(1);
                    return amount;
                }
    
            }
            catch(Exception e){
                e.printStackTrace();
    
            }
            return 0.0;
    }
    /***
     * @param type 1. check payment status, 2. make payment
     * @param id
     * @param conn
     * @return
     */
    public static double updateAmountDue(int type, int id, Connection conn){
        //calculates amount due and sets that part of the column to this new number
        double total = monthlyRent(id, conn) + monthlyCharge(id, conn);
        String query = "";
        if(type == 1){
            query = "UPDATE tenants SET amount_due = ? WHERE tenantid = ? AND payment_status = 'UNPAID'";
            try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
                prepdstatement.setDouble(1, total);
                prepdstatement.setInt(2, id);
                
                int rowsAffected = prepdstatement.executeUpdate();
                
                //return rs.getInt(1);
                if(rowsAffected > 0){ 
                    return total;
                }
    
            }
            catch(Exception e){
                e.printStackTrace();
    
            }
        }
        //make rent payment
        else if(type == 2){
            query = "UPDATE tenants SET amount_due = 0.0, payment_status = 'PAID' WHERE tenantid = ?";
            try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
                prepdstatement.setInt(1, id);
                
                int rowsAffected = prepdstatement.executeUpdate();
                
                //return rs.getInt(1);
                if(rowsAffected > 0){
                    total = 0.0;
                    System.out.println("Successfully paid! Amount Due: $" + total);
                    return total;
                }
    
            }
            catch(Exception e){
                e.printStackTrace();
    
            }
        }
        
            

        return 0.0;
    }
    public static double monthlyRent(int id, Connection conn){
        String query = "SELECT rent FROM tenants WHERE tenantid = ?";
            try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
                prepdstatement.setInt(1, id);
    
                ResultSet rs = prepdstatement.executeQuery();
                
                //return rs.getInt(1);
                if(rs.next()){
                    double rent = rs.getDouble(1);
                    return rent;
                }
    
            }
            catch(Exception e){
                e.printStackTrace();
    
            }
            return 0.0;
    }
    public static double monthlyCharge(int id, Connection conn){
        String query = "SELECT SUM(monthly_charge) AS total_charge FROM (" +
                "   SELECT A2.monthly_charge" +
                "   FROM AYC325.APARTMENTS A1" +
                "   JOIN AYC325.PRIVATEAMENITIES A2 ON A1.APARTMENTID = A2.APARTMENTID" +
                "   WHERE A1.TENANTID = ?" +
                "   UNION ALL" +
                "   SELECT monthly_charge" +
                "   FROM AYC325.APARTMENTS A1" +
                "   JOIN AYC325.PUBLICAMENITIES A3 ON A1.PROPERTYID = A3.PROPERTYID" +
                "   WHERE A1.TENANTID = ?" +
                ") combined_charges" +
                " WHERE EXISTS (" +
                "   SELECT 1" +
                "   FROM AYC325.APARTMENTS A1" +
                "   JOIN AYC325.PRIVATEAMENITIES A2 ON A1.APARTMENTID = A2.APARTMENTID" +
                "   JOIN AYC325.PUBLICAMENITIES A3 ON A1.PROPERTYID = A3.PROPERTYID" +
                "   WHERE A1.TENANTID = ?" +
                ")";
            try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
                // Set the parameter values
                prepdstatement.setInt(1, id);
                prepdstatement.setInt(2, id);
                prepdstatement.setInt(3, id);
    
                ResultSet rs = prepdstatement.executeQuery();
                
                //return rs.getInt(1);
                if(rs.next()){
                    double totalMonthlyCharge = rs.getDouble(1);
                    return totalMonthlyCharge;
                }
    
            }
            catch(Exception e){
                e.printStackTrace();
    
            }
            return 0.0;
    } 
    public static int getPersonID(int id, Connection conn){
        String query = "SELECT personid FROM tenants WHERE tenantid = ?";
        try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
                // Set the parameter values
                prepdstatement.setInt(1, id);
    
                ResultSet rs = prepdstatement.executeQuery();
                
                //return rs.getInt(1);
                if(rs.next()){
                    int personId = rs.getInt(1);
                    return personId;
                }
    
            }
            catch(Exception e){
                e.printStackTrace();
    
            }
            return 0;
    }
    /***
     * 
     * @param type
     * @param id
     * @param conn
     */
    public static void updateTenantData(int type, int id, Connection conn){
        Scanner scnr = new Scanner(System.in);
        Validator validator = new Validator();
        Menu menu = new Menu();
        int personId = getPersonID(id, conn);
        
        switch(type){
            case 1:
                //Name
                System.out.println(updateName(updateName(conn), personId, conn));
                break;
            case 2:
                //Phone Number
                System.out.println(updatePhoneNumber(updatePhoneNumber(conn), personId, conn));
                break;
            case 3:
                //Payment Method
                List<String> options = new ArrayList<>();
                options = menu.printPaymentTypeMenu();
                if(options != null){
                    System.out.println(updatePaymentMethod(options, id, conn));
                }else{
                    break;
                }
                break;
            case 4:
            default:
                menu.printUpdateDataMenu(1);
                break;
        }
    }
    public static String updatePaymentMethod(List<String> payment, int id, Connection conn){
        String query = " ";
        String result = " ";
        if(payment.get(0).equals("Credit") || payment.get(0).equals("Debit")){
            query = "UPDATE payments SET account_type = ?, card_number = ?, cvc = ?, exp_date = ? WHERE tenantid = ?";
            try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
                prepdstatement.setString(1, payment.get(0));
                prepdstatement.setString(2, payment.get(1));
                prepdstatement.setString(3, payment.get(2));
                prepdstatement.setString(4, payment.get(3));
                prepdstatement.setInt(5, id);


                int rowsAffected = prepdstatement.executeUpdate(); 
                
                //return rs.getInt(1);
                if(rowsAffected > 0){
                    result = "Card info succesfully changed!";
                    return result;
                }
            }
            catch(Exception e){
                e.printStackTrace();

            }
        }else if(payment.get(0).equals("Checkings") || payment.get(0).equals("Savings")){
            query = "UPDATE payments SET account_type = ?, routing_number = ?, account_number = ? WHERE tenantid = ?";
            try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
                prepdstatement.setString(1, payment.get(0));
                prepdstatement.setString(2, payment.get(1));
                prepdstatement.setString(3, payment.get(2));
                prepdstatement.setInt(4, id);

                int rowsAffected = prepdstatement.executeUpdate(); 
                
                //return rs.getInt(1);
                if(rowsAffected > 0){
                    result = "ACH info succesfully changed!";
                    return result;
                }

            }
            catch(Exception e){
                e.printStackTrace();

            }
        }
        return result;
       
    }
    public static String updatePhoneNumber(Connection conn){
        Scanner scnr = new Scanner(System.in);
        Validator validator = new Validator();
        String phoneNumber = " ";
        //phoneNumber
        do {
            System.out.println("Enter Phone Number (no dashes, ########## format)");
            phoneNumber = scnr.nextLine();
            if (validator.isValidPhoneNumber(phoneNumber)) {
                return phoneNumber;
            }else{
                System.out.println("Invalid first name. Make sure the first letter is capitalized.");
            }
        } while (!validator.isValidName(phoneNumber));
        return phoneNumber;
    }
    public static String updatePhoneNumber(String phoneNumber, int personId, Connection conn){
        String query = "UPDATE persons SET phone_number = ? WHERE personid = ?";
        try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
            prepdstatement.setString(1, phoneNumber);
            prepdstatement.setInt(2, personId);
            
            int rowsAffected = prepdstatement.executeUpdate(); 
            
            //return rs.getInt(1);
            if(rowsAffected > 0){
                System.out.println("Phone number succesfully changed!");
                return phoneNumber;
            }

        }
        catch(Exception e){
            e.printStackTrace();

        }
        return phoneNumber;
    }
    /***
     * 
     * @param conn
     * @return
     */
    public static List<String> updateName(Connection conn){
        Scanner scnr = new Scanner(System.in);
        Validator validator = new Validator();

        List<String> name = new ArrayList<>();
        String firstName, middleInitial, lastName;

        // First Name
        do {
            System.out.println("Enter First Name");
            firstName = scnr.nextLine();
            if (!validator.isValidName(firstName)) {
                System.out.println("Invalid first name. Make sure the first letter is capitalized.");
            }
        } while (!validator.isValidName(firstName));

        // Last Name
        do {
            System.out.println("Enter Last Name");
            lastName = scnr.nextLine();
            if (!validator.isValidName(lastName)) {
                System.out.println("Invalid last name. Make sure the first letter is capitalized.");
            }
        } while (!validator.isValidName(lastName));

        // Middle Initial
        do {
            System.out.println("Enter Middle Initial (optional: press 0 if you don't have one)");
            middleInitial = scnr.nextLine().trim();

            if ("0".equals(middleInitial)) {
                middleInitial = "(null)";
                break;
            } else if (!validator.isValidMiddleInitial(middleInitial)) {
                System.out.println("Invalid middle initial. Make sure it's one letter long and capitalized.");
            }
        } while (!validator.isValidMiddleInitial(middleInitial));

        // Populate the name list
        name.add(firstName);
        name.add(middleInitial);
        name.add(lastName);

        // Return the final name
        return name;
    }


    public static String updateName(List<String> name, int personId, Connection conn){
        String firstName = name.get(0);
        String middleInitial = name.get(1);
        String lastName = name.get(2);
        /*
        * running query to update name
        */
        String query;
        String result = " ";
        query = "UPDATE persons SET first_name = ?, middle_initial = ?, last_name = ? WHERE personid = ?";
        try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
            prepdstatement.setString(1, firstName);
            prepdstatement.setString(2, middleInitial);
            prepdstatement.setString(3, lastName);
            prepdstatement.setInt(4, personId);
            
            int rowsAffected = prepdstatement.executeUpdate(); 
            
            //return rs.getInt(1);
            if(rowsAffected > 0){
                System.out.println("Name successfully changed!");
                if(!"(null)".equals(middleInitial)){
                    result = "Your updated name is: " + name.get(0) + " " + name.get(1) + " " + name.get(2) + ".";
                }else{
                    result = "Your updated name is: " + name.get(0) + " " + name.get(2) + ".";
                }
                return result;
            }

        }
        catch(Exception e){
            e.printStackTrace();

        }
        return result;
    }
}

