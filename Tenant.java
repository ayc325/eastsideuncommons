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

    public static void updateTenantData(int type, int id, Connection conn){
        switch(type){
            case 1:
                //Name
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break; 
        }
    }
}

