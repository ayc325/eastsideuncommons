import java.util.*;

public class Menu {
    /***
     * Print Main Menu
     * @return void
     */
    public void printMenu(){
        System.out.println("Who are you?");
        System.out.println("1. Tenant");
        System.out.println("2. Property Manager");
        System.out.println("3. Company Manager");
        System.out.println("4. Financial Manager");
        System.out.println("5. Exit Program");
        System.out.println("Enter a number: ");
        //Make sure number is valid in Enterprise.java

    }
    /***
     * Prints Menu for Tenants
     * @return void
     */
    public void printTenantMenu(){
        /*
         * 1. Check payment status
         *      a. Amount due if any
         *      b. Provide like an invoice to view what amount due consists of (monthly charge, rent)
         * 2. Make rental payment
         *      a. Update data to subtract from amount due
         *      b. If amount due is 0, print a message to let tenant know that there's nothing to pay
         * 3. Update personal data 
         *      a. First Name, Middle Initial, Last Name, Phone Number, Date of Birth
         */
        System.out.println("Tenant Menu:");
        System.out.println("1. Check payment status");
        System.out.println("2. Make rental payment");
        System.out.println("3. Update Personal Data");
        System.out.println("4. Exit Tenant Menu");
        //Make sure number is valid in Enterprise.java
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
        System.out.println("Select Payment Type");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. Checking Account");
        System.out.println("4. Savings Account");
        System.out.println("5. Exit");
        menuOption = scnr.nextInt();

        switch(menuOption){
            case 1:
                printCardOptionMenu(menuOption);
                break;
            case 2:
                printCardOptionMenu(menuOption);
                break;
            case 3:

            case 4:
            case 5:
            default:
                printPaymentTypeMenu();
                break;
        }

    }
    /***
     * 
     * @param cardType- 1: Credit, 2: Debit
     * @return List<String> 
     */
    public List<String> printCardOptionMenu(int cardType){
        Validator validator = new Validator();
        Scanner scnr = new Scanner(System.in);
        List<String> cardInfo = new ArrayList<>();
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

        System.out.println("Enter Card Information:");
            while(cardInfo.size() == 1){
                System.out.println("Enter Card Number");
                String cardNum = scnr.nextLine();
                if(validator.isValidCardNumber(cardNum)){
                    cardInfo.add(cardNum);
                }else{
                    System.out.println("Invalid Card Number. Must be 16 digits, no spaces. Try Again.");
                }
                while(cardInfo.size() == 2){
                    System.out.println("Enter CVC");
                    String cvcNum = scnr.nextLine();
                    if(validator.isValidCVCNumber(cvcNum)){
                        cardInfo.add(cvcNum);
                    }else{
                        System.out.println("Invalid CVC. Must be 3 digits. Try Again.");
                    }
                    while(cardInfo.size() == 3){
                        System.out.println("Enter Exp Date in MM/YY format");
                        String expDate = scnr.nextLine();
                        if(validator.isValidExpirationDate(expDate)){
                            cardInfo.add(expDate);
                        }else{
                            System.out.println("Invalid Exp Date. Must be MM/YY format and cannot be year prior to 2024. Try Again.");
                        }
                    }
                }
            }  
    }while(cardInfo.size() < 4);
        return cardInfo;
    }

    public List<String> printAccountOptionMenu(int cardType){
        Validator validator = new Validator();
        Scanner scnr = new Scanner(System.in);
        List<String> accountInfo = new ArrayList<>();
        do{
            switch(cardType){
                case 1:
                    accountInfo.add("Credit");
                    break;
                case 2:
                    accountInfo.add("Debit");
                    break;
                default:
                    System.out.println("Invalid Card Type");
                    printPaymentTypeMenu();
                    break;
            }   

        System.out.println("Enter Card Information:");
            while(accountInfo.size() == 1){
                System.out.println("Enter Card Number");
                String cardNum = scnr.nextLine();
                if(validator.isValidCardNumber(cardNum)){
                    accountInfo.add(cardNum);
                }else{
                    System.out.println("Invalid Card Number. Current cardInfo size is: " + cardInfo.size());
                }
                while(accountInfo.size() == 2){
                    System.out.println("Enter CVC");
                    String cvcNum = scnr.nextLine();
                    if(validator.isValidCVCNumber(cvcNum)){
                        accountInfo.add(cvcNum);
                    }else{
                        System.out.println("Invalid CVC. Current cardInfo size is: " + accountInfo.size());
                    }
                    while(accountInfo.size() == 3){
                        System.out.println("Enter Exp Date in MM/YY format");
                        String expDate = scnr.nextLine();
                        if(validator.isValidExpirationDate(expDate)){
                            accountInfo.add(expDate);
                        }else{
                            System.out.println("Invalid Exp Date. Current cardInfo size is: " + accountInfo.size());
                        }
                    }
                }
            }  
    }while(cardInfo.size() < 4);
        return cardInfo;
    }
}
