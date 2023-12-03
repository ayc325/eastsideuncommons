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
    public String checkPaymentStatus(int id, Connection conn){
        double amount = 0.0;
        amount = amountDue(id, conn);
        if(amount > 0){
            return "Unpaid. Remaining Balance: $" + amount;
        }else{
            return "Paid";
        }
    }

    public static double amountDue(int id, Connection conn){
            String query = "SELECT amount_due FROM tenants WHERE tenantid = ?";
            try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
                prepdstatement.setInt(1, id);
    
                ResultSet rs = prepdstatement.executeQuery();
                
                //return rs.getInt(1);
                if(rs.next()){
                    double amount = rs.getInt(1);
                    return amount;
                }
    
            }
            catch(Exception e){
                e.printStackTrace();
    
            }
            return 0.0;
        }
}

