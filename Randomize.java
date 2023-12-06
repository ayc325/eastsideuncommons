import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Randomize {
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
    public int randomizeID(int type, Connection conn){
        Validator validator = new Validator();
        int min, max;
        switch (type) {
        //1. randomize prosp_tenant id and people id (4 digits) (999 < x && x < 10000)
            case 1: // personid
            case 2: // prosp_tenant
                min = 1000;
                max = 9999;
                break;
        //2. randomize property/apartment id (5 digits) (9999 < x && x < 100000)
            case 3: // Property
            case 4: // Apartment
                min = 10000;
                max = 99999;
                break;
        //3. randomize tenant id (6 digits) (99999 < x && x < 1000000)
            case 5: // Tenant ID (6 digits)
                min = 100000;
                max = 999999;
                break;
        default:
            throw new IllegalArgumentException("Invalid ID type");
    }
        // Create a random number generator
        Random random = new Random();

        int generatedID;
        boolean isNotUnique = true;

        do {
            // Generate a random ID within the specified range
            generatedID = random.nextInt((max - min) + 1) + min;

            // Check if the generated ID is unique in the database
            switch(type){
                case 1://personid
                    isNotUnique = validator.idInData(generatedID, 6, conn);
                    break;
                case 2://prosp_tenant
                    isNotUnique = validator.idInData(generatedID, 5, conn);
                    break;
                case 3: //property id
                    isNotUnique = validator.idInData(generatedID, 7, conn);
                    break;
                case 4: //apartment id
                    isNotUnique = validator.idInData(generatedID, 8, conn);
                    break;
                case 5: //tenant id
                    isNotUnique = validator.idInData(generatedID, 1, conn);
                    break;
            }
        } while(isNotUnique == true);

        // Return the unique generated ID
        return generatedID;
    
    }
    
    // public String randomizeAddress(){
    //     return " ";
    // }
    // public String randomizeCity(){
    //     return " ";
    // }
    // public String randomizeState(){
    //     return " ";
    // }
    public double randomizeDoubles(int type){
        //use this to randomize prices
        //1. property price (minimum 100k)
        //2. monthly rent (499 < x && x < 10000)
        //3. security deposit (2*monthly rent)
        //4. apt_size (100 < x && x < 10000)
        //5. bathroom make an array of options and pick in 0.5 increments from 0-4
        return 1.0;
    }
    public int randomizeBed(){
        return 1;
    }
    public String randomizeAmenities(int type){
        //1. Private amenities
            //Choose randomnly out of an array consisting Laundry, Dishwasher, and Parking
        //2. Public amenities
            //Choose randomnly out of an array consisting Hot-Tub, Pool, Playground, and Gym
        return " ";
    }
    
}
