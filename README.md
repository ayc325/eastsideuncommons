# Angela's Eastside Uncommons

## How to run the code

* Compile with `javac *.java`
* Setup if any changes with `jar cvfm Enterprise.jar Manifest.txt *.class`
* Run with `java -jar Enterprise.jar`


## Assumptions

1. Start Date: The date in the program is Jan 1, 2024 (might change this date) while running the program. 
    Basically saying if you add a new tenant while you run the program, their move-in date will automatically be set to Jan 1, 2024
2. Move-out Date: All move-out dates are 12 months out from move-in date
3. No Pets: Many properties in real life do not allow pets due to property damage so my Eastside Uncommons also does not allow pets to keep costs low.
4. Apartment Buildings only: Properties are the Apartment Building itself and Apartments = apartment units so you cannot add apartments unless you add a new property. The company only invests in apartment buildings in cities.
5. All monthly charges from amenities are always included in full so tenants do not have the option to opt-out of amenity fees
6. We assume that tenants have enough money for rent since in real life, property managers cannot check tenants' bank accounts.

## Classes

### Enterprise.java

* Main Method here!

### Menu.java

* 

### Tenant.java

### Randomize.java

### Validator.java

### Test.java