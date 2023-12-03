# Angela's Eastside Uncommons

## How to run the code

* Compile with `javac Enterprise.java`
* Setup if any changes with `jar cvfm Enterprise.jar Manifest.txt Enterprise.class`
* Run with `java -jar Enterprise.jar`


## Assumptions

1. Start Date: The date in the program is Jan 1, 2024 while running the program. 
    Basically saying if you add a new tenant while you run the program, their move-in date will automatically be set to Jan 1, 2024
2. Move-out Date: All move-out dates are 12 months out from move-in date
3. No Pets: Many properties in real life do not allow pets due to property damage so my Eastside Uncommons also does not allow pets to keep costs low.
4. Apartment Buildings only: Properties are the Apartment Building itself and Apartments = apartment units so you cannot add apartments unless you add a new property. The company only invests in apartment buildings in cities.