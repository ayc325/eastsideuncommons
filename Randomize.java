

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
    public int randomizeID(int type){
        //1. randomize people/tenant id (4 digits) (999 < x && x < 10000)
        //2. randomize property/apartment id (5 digits) (9999 < x && x < 100000)
        //3. randomize tenant id (6 digits) (99999 < x && x < 1000000)
        return 0;
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
