import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Menu {
    /***
     * Print Main Menu
     * @return void
     */
    public int printMenu(Connection conn){
        Scanner scnr = new Scanner(System.in);
        int menu = 0;
        System.out.println("Who are you?");
        System.out.println("1. Tenant");
        System.out.println("2. Property Manager");
        System.out.println("3. Company Manager");
        System.out.println("4. Financial Manager");
        System.out.println("5. Exit Program");
        System.out.println("Enter a number: ");
        //Make sure number is valid in Enterprise.java
        if(scnr.hasNextInt()){
            menu = scnr.nextInt();
        }
        switch(menu){
            case 1:
                return printTenantMenu(conn);
            case 2:
                printPropertyMenu();
                return 0;
            case 3:
                printCompanyMenu();
                return 0;
            case 4:
                printFinancialMenu();
                return 0;
            case 5:
                return 5;
            default:
                System.out.println("Invalid Input. Only options 1-5. Try Again");
                return -1;
        }


    }
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
    public int printTenantMenu(Connection conn){
        Tenant tenant = new Tenant();
        Validator validator = new Validator();
        Scanner scnr = new Scanner(System.in);
        int input = 0;
        int id = 1;
        int tempId = 0;
        do{
            //Enter Tenant ID
            System.out.println("Please enter the tenant id: ");
            System.out.println("Press 1 for 'Help, I came into this menu by accident!'");
            //for graders
                System.out.println("For Graders: tenantid 267700 should have 1790.23 amount due, so unpaid");
                System.out.println("tenantid 466266 should be paid");
                System.out.println("List of unpaid tenantid: 267700, 751043, 765542, 976396");
                System.out.println("List of paid tenantid: 466266, 134659, 227886, 536494");
            if(scnr.hasNextInt()){
                tempId = scnr.nextInt();
                //exit to previous menu
                if(tempId == 1){ 
                    return -1; 
                }
                if(validator.idInData(tempId, 1, conn)){
                    id = tempId;
                    System.out.println("Login Success!");
                }else if(tempId != 1 && !validator.idInData(tempId, 1, conn)){
                    System.out.println("Invalid Tenant ID.  Try Again.\n");
                }
                
            }else{
                System.out.println("Not a number.  Try Again.");
                System.out.println("If need to add tenant, contact the Property Manager.");
                scnr.nextLine();
            }
        }
        while(id == 1);
        //Method to check for ID in database
        //validator.idInData(id, 1, conn) in while section at the bottom of the do-while loop.
        do{
            System.out.println("Tenant Menu:");
            System.out.println("1. Check payment status");
            System.out.println("2. Make rental payment"); //can pay in full and partially
            System.out.println("3. Update Personal Data"); 
            System.out.println("4. Exit Tenant Menu");
            //Make sure number is valid in Enterprise.java
            if(scnr.hasNextInt()){
                input = scnr.nextInt();
            }
            switch(input){
                case 1:
                    System.out.println(tenant.checkPaymentStatus(id, conn));
                    break;
                case 2:
                    printPropertyMenu();
                    break;
                case 3:
                    printUpdateDataMenu();
                    break;
                case 4:
                    return -1;
                default:
                    System.out.println("Invalid Input. Only options 1-4. Try Again");
                    
            }
        }while(validator.idInData(id, 1, conn));
        return 0; //edit this later
    }
    /***
     * Prints all options for tenants to update their Personal Data
     * First Name, Middle Initial, Last Name, Phone Number, Date of Birth, Payment Method
     * @return void
     */
    public void printUpdateDataMenu(){
        System.out.println("Which data would you like to update?");
        System.out.println("1. First Name");
        System.out.println("2. Middle Initial");
        System.out.println("3. Last Name");
        System.out.println("4. Phone Number");
        System.out.println("5. Date of Birth"); //Make sure it's in DD-MON-YY format
        System.out.println("6. Payment Method");
        System.out.println("7. Return to Tenant Menu");
        //Make sure number is valid in Enterprise.java
    }
    /***
     * Prints Menu for Property Manager
     * @return void
     */
    public void printPropertyMenu(){
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
        System.out.println("Property Manager Menu");
        System.out.println("1. Record Visit Data");
        System.out.println("2. Record Lease Data");
        System.out.println("3. Record Move-out Data");
        System.out.println("4. Add Person to a Lease");
        System.out.println("5. Exit Property Manager Menu");
        //Make sure number is valid in Enterprise.java

    }
    public void printCompanyMenu(){
        /*
         * 1. Add new property
         *      a. Randomly assign an ID (no duplicate)
         *      b. Address, City, Prop_State (Short for Property_State), Postal_Code, Price
         *      c. (Optional) (Ask: Would you like to add an amenity to the property?) Add Public Amenities (Options: PLAYGROUND, POOL, HOT-TUB, GYM)
         *          i. Assign Monthly Charge and Open Hours (Format: ##A/PM - ##A/PM)
         *      d. Randomnly add ~5 apartments to each property 
         *          i. Each apartment should have randomnly set apartment id, monthly_rent, security_deposit, apt_size, bed, bath, and private amenities (optional)
         *              1. Private amenity options: LAUNDRY, PARKING, DISHWASHER and add monthly charge
         */
        System.out.println("Company Manager Menu");
        System.out.println("1. Add new Property");
        System.out.println("3. Exit Company Manager Menu");
        //make sure to utilize Randomize.java to create Property
    }
    /***
     * @return void
     */
    public void printFinancialMenu(){
        /***
         * 1. Reads data about property
         *      a. Select specific property (by ID)
         * 2. Some properties (Split by State/Region)
         * 3. All properties
         *  a. Address, City, Prop_State (Short for Property_State), Postal_Code, Price for each property
         * 4. Produce Financial Report
         *  a. Sum of money earned per property
         *  b. Sum of money earned across all properties
         */
        System.out.println("Financial Manager Menu");
        System.out.println("1. Obtain data about properties");
        System.out.println("2. Produce Financial Report");
        System.out.println("3. Exit Financial Manager Menu");
    }
    public void propertyData(){
        /***
         * 1. Reads data about property
         *      a. Select specific property (by ID)
         * 2. Some properties (Split by State)
         * 3. All properties
         *  a. Address, City, Prop_State (Short for Property_State), Postal_Code, Price for each property
         */
        System.out.println("Select number of properties to view");
        System.out.println("1. Single property: You must know the PropertyID to view this option");
        //Enter specific Property ID
        System.out.println("2. Properties by state");
        //Enter two-letter abbreviation
        System.out.println("3. All Properties");
        //Show all properties' data
        System.out.println("4. Return to Financial Manager Menu");
    }

    /***
     * Lets tenant edit payment method
     * @return void
     */
    public void printPaymentTypeMenu(){
        /*
         * 1. Select Payment Type
         *      1. Credit/Debit
         *          1. Card Type
         *          2. Card Number
         *          3. CVC
         *          4. Exp Date
         *      2. Checking/Savings
         *          1. Account Type
         *          2. Routing Number
         *          3. Account Number
         */
        int menuOption = 0;
        Scanner scnr = new Scanner(System.in);
        System.out.println("Select Payment Type: \nPress q to abort process at any time.");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. Checking Account");
        System.out.println("4. Savings Account");
        System.out.println("5. Exit");
        if(scnr.hasNextInt()){
            menuOption = scnr.nextInt();
        }else{
            if(scnr.nextLine().equalsIgnoreCase("q")){
                System.out.println("Aborting...");
                printUpdateDataMenu();
            }
            System.out.println("Not a number. Options 1-5 only.");
            printPaymentTypeMenu();
        }
        
        switch(menuOption){
            case 1:
            case 2:
                printCardOptionMenu(menuOption);
                break;
            case 3:   
            case 4:
                printAccountOptionMenu(menuOption);
                break;
            case 5:
                printUpdateDataMenu();
                break;
            default:
                printPaymentTypeMenu();
                break;
        }

    }
    /***
     * Method used to enter a card payment type. (No SQL)
     * @param cardType- 1: Credit, 2: Debit
     * @return List<String> 
     */
    public List<String> printCardOptionMenu(int cardType){
        Validator validator = new Validator();
        Scanner scnr = new Scanner(System.in);
        List<String> cardInfo = new ArrayList<>();
        String cardNum = "0", cvcNum = "0", expDate = "0";
        do{
            switch(cardType){
                case 1:
                    cardInfo.add("Credit");
                    break;
                case 2:
                    cardInfo.add("Debit");
                    break;
                default:
                    System.out.println("Invalid Card Type. Credit or Debit only. Try Again.");
                    printPaymentTypeMenu();
                    break;
            }   

        System.out.println("Enter Card Information: \nPress q to abort process at any time.");
            while(cardInfo.size() == 1){
                System.out.println("Enter Card Number");
                cardNum = scnr.nextLine();
                if(validator.isValidCardNumber(cardNum)){
                    cardInfo.add(cardNum);
                }else if(cardNum.equalsIgnoreCase("q")){
                    System.out.println("Aborting...\n");
                    //printPaymentTypeMenu();
                    return null;
                }else{
                    System.out.println("Invalid Card Number. Must be 16 digits, no spaces. Try Again.");
                }
                while(cardInfo.size() == 2){
                    System.out.println("Enter CVC");
                    cvcNum = scnr.nextLine();
                    if(validator.isValidCVCNumber(cvcNum)){
                        cardInfo.add(cvcNum);
                    }else if(cvcNum.equalsIgnoreCase("q")){
                            System.out.println("Aborting...\n");
                            //printPaymentTypeMenu();
                            return null;
                    }else{
                        System.out.println("Invalid CVC. Must be 3 digits. Try Again.");
                    }
                    while(cardInfo.size() == 3){
                        System.out.println("Enter Exp Date in MM/YY format");
                        expDate = scnr.nextLine();
                        if(validator.isValidExpirationDate(expDate)){
                            cardInfo.add(expDate);
                        }else if(expDate.equalsIgnoreCase("q")){
                                System.out.println("Aborting...\n");
                                //printPaymentTypeMenu();
                                return null;
                        }else{
                            System.out.println("Invalid Exp Date. Must be MM/YY format and cannot be year prior to 2024. Try Again.");
                        }
                    }
                }
            }  
    }while(cardInfo.size() < 4);
        return cardInfo;
    }
    /***
     * Method used to add a Checkings or Savings account as a payment method (No SQL)
     * @param accountType- 3: Checkings, 4: Savings
     * @return List<String> accountInfo
     */
    public List<String> printAccountOptionMenu(int accountType){
        Validator validator = new Validator();
        Scanner scnr = new Scanner(System.in);
        List<String> accountInfo = new ArrayList<>();
        String acctNum = "0", routingNum = "0";
        do{
            switch(accountType){
                case 3:
                    accountInfo.add("Checkings");
                    break;
                case 4:
                    accountInfo.add("Savings");
                    break;
                default:
                    System.out.println("Invalid Account Type");
                    printPaymentTypeMenu();
                    break;
            }   

        System.out.println("Enter Account Information: \nPress q to abort process at any time.");
            while(accountInfo.size() == 1){
                System.out.println("Enter Account Number");
                acctNum = scnr.nextLine();
                if(validator.isValidAccountNumber(acctNum)){
                    accountInfo.add(acctNum);
                }else if(acctNum.equalsIgnoreCase("q")){
                        System.out.println("Aborting...\n");
                        return null;
                        //printPaymentTypeMenu(); //call this in the main method, not here...
                }else{
                    System.out.println("Invalid Account Number. Account Number must be between 8-12 digits in length. Try Again.");
                }
                while(accountInfo.size() == 2){
                    System.out.println("Enter Routing Number");
                    routingNum = scnr.nextLine();
                    if(validator.isValidRoutingNumber(routingNum)){
                        accountInfo.add(routingNum);
                    }else if(routingNum.equalsIgnoreCase("q")){
                            System.out.println("Aborting...\n");
                            return null;
                            //printPaymentTypeMenu();
                    }else{
                        System.out.println("Invalid Routing Number. Routing Number must be 9 digits in length. Try Again.");
                    }
                }
            }  
    }while(accountInfo.size() < 3);
        return accountInfo;
    }
}
