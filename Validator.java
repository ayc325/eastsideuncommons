import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.*;

public class Validator {
    /**
     * Things that need to be validated
     * ID's: Property, Apartment, Employee, Person, Tenant
     * Address: Separate house number and street name. House number must be number and street name
     * City: Must be string
     * State Abbreviation: Must be two-letters and both capital
     * Postal Code: Must be five digits
     * Payment stuff
     */
    /***
     * Verifies 16-digit card number length
     * @param cardNumber
     * @return true/false
     */
    public static boolean isValidCardNumber(String cardNumber) {
        //System.out.println("Enters isValidCardNumber");
        // Define the regex pattern for a 16-digit numeric string
        String regex = "^[0-9]{16}$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher object
        Matcher matcher = pattern.matcher(cardNumber);

        // Return true if the card number matches the pattern
        return matcher.matches();
    }
    /***
     * Verifies 3-digit CVC length
     * @param cvcNumber
     * @return true/false
     */
    public static boolean isValidCVCNumber(String cvcNumber){
        //System.out.println("Enters isValidCVCNumber");
        // Define the regex pattern for a 3-digit numeric string
        String regex = "^[0-9]{3}$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher object
        Matcher matcher = pattern.matcher(cvcNumber);

        // Return true if the card number matches the pattern
        return matcher.matches();
    }
    /***
     * Verifies MM/YY expiration date format and makes sure it isn't before the year 2024
     * since the program is set to Jan. 1, 2024
     * @param expirationDate
     * @return true/false
     */
    public static boolean isValidExpirationDate(String expirationDate) {
        //System.out.println("Enters isValidExpirationDate");
        // Define the regex pattern for MM/YY format
        String regex = "^(0[1-9]|1[0-2])/(\\d{2})$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher object
        Matcher matcher = pattern.matcher(expirationDate);

        // Check if the expiration date matches the pattern
        if (matcher.matches()) {
            // Extract the month and year
            int month = Integer.parseInt(matcher.group(1));
            int year = Integer.parseInt(matcher.group(2));

            // Perform additional checks
            if (year < 24) {
                return false; // Expiration year cannot be before 24
            }

            // Check if the date is not in the past
            YearMonth currentYearMonth = YearMonth.now();
            YearMonth inputYearMonth = YearMonth.of(2000 + year, month);

            return !inputYearMonth.isBefore(currentYearMonth);
        }
        return false;
    }
    /***
     * Verifies accountNumber length to be between 8-12 digits which is typical
     * @param accountNumber
     * @return true/false
     */
    public static boolean isValidAccountNumber(String accountNumber) {
        // Customize the regex based on the length requirements (8 to 12 digits)
        String regex = "^[0-9]{8,12}$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher object
        Matcher matcher = pattern.matcher(accountNumber);

        // Return true if the account number matches the pattern
        return matcher.matches();
    }
    /***
     * Verifies routingNumber length to be between 9 digits which is typical
     * @param routingNumber
     * @return true/false
     */
    public static boolean isValidRoutingNumber(String routingNumber) {
        // Customize the regex based on the format requirements
        String regex = "^[0-9]{9}$";  // Assumes a 9-digit routing number format

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher object
        Matcher matcher = pattern.matcher(routingNumber);

        // Return true if the routing number matches the pattern
        return matcher.matches();
    }

    /***
     * 
     * @param id
     * @param personType- 1. Tenant, 2. Property Manager, 3. Company Manager, 4. Financial Manager
     * @param conn
     * @return
     */
    public static boolean idInData(int id, int personType, Connection conn){
        String query = " ";
        switch(personType){
            case 1: //tenant
                query = "SELECT COUNT(*) FROM tenants WHERE tenantid = ?";
                break;
            case 2: //property
                query = "SELECT COUNT(*) FROM employees WHERE permissions = 'Property' AND employeeid = ?";
                break;
            case 3: //company
                query = "SELECT COUNT(*) FROM employees WHERE permissions = 'Full' AND employeeid = ?";
                break;
            case 4: //financial
                query = "SELECT COUNT(*) FROM employees WHERE permissions = 'Finance' AND employeeid = ?";
                break;
            case 5: //prosp_tenant
                query = "SELECT COUNT(*) FROM prospectivetenants WHERE prosp_tenantid = ?";
                break;
            case 6: //personid
                query = "SELECT COUNT(*) FROM persons WHERE personid = ?";
                break;
            case 7: //property id
                query = "SELECT COUNT(*) FROM properties WHERE propertyid = ?";
                break;
            case 8: //apartment id
                query = "SELECT COUNT(*) FROM apartments WHERE apartmentid = ?";
                break;
        }
        
        try(PreparedStatement prepdstatement = conn.prepareStatement(query)){
            prepdstatement.setInt(1, id);
            //prepdstatement.setString(2, semester);
            ResultSet rs = prepdstatement.executeQuery();
            if(rs.next()){
                int count = rs.getInt(1);
                if(count > 0){
                    System.out.println("ID exists in database");
                    return true; //semester exists
                }else{
                    System.out.println("ID does not exist in database");
                    return false;
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();

        }
        return false;
    }

    

    public static boolean isValidName(String firstName) {
        // Regex pattern: starts with a capital letter, followed by lowercase letters
        String regex = "^[A-Z][a-z]*$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Check if the firstName matches the pattern
        return pattern.matcher(firstName).matches();
    }

    public static boolean isValidMiddleInitial(String middleInitial) {
        // Regex pattern: a single capital letter
        String regex = "^[A-Z]$";
    
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);
    
        // Check if the middleInitial matches the pattern
        return pattern.matcher(middleInitial).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Define the regex pattern for a phone number with the desired length
        String regex = "\\d{10}";

        // Check if the phone number matches the pattern
        return Pattern.matches(regex, phoneNumber);
    }

    public static boolean isValidBirthdate(String birthdate) {
        // Define the regex pattern for MM/DD/YYYY
        String regex = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)$";
        Pattern pattern = Pattern.compile(regex);

        // Check if the birthdate matches the regex pattern
        Matcher matcher = pattern.matcher(birthdate);
        if (!matcher.matches()) {
            System.out.println("Invalid date format. Please use MM/DD/YYYY.");
            return false;
        }

        // Parse the birthdate string to a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);

        try {
            Date birthDate = dateFormat.parse(birthdate);

            // Get the current date
            Date currentDate = new Date();

            // Create Calendar instances for birthdate and current date
            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.setTime(birthDate);

            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentDate);

            // Verify that the birthdate is not in the future
            if (birthCalendar.after(currentCalendar)) {
                System.out.println("Birthdate cannot be in the future.");
                return false;
            }

            // Verify that the person is not over 120 years old
            birthCalendar.add(Calendar.YEAR, 120);
            if (birthCalendar.before(currentCalendar)) {
                System.out.println("Person is over 120 years old.");
                return false;
            }

            // If both conditions pass, the birthdate is valid
            return true;

        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use MM/DD/YYYY.");
            return false;
        }
    }


}
