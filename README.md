# Angela's Eastside Uncommons

## Note: Oracle SQL account is deprecated

## How to run the code

* Compile with `javac *.java`
* Setup if any changes with `jar cvfm Enterprise.jar Manifest.txt *.class`
* Run with `java -jar Enterprise.jar`


## Assumptions

1. No Pets: Many properties in real life do not allow pets due to property damage so my Eastside Uncommons also does not allow pets to keep costs low.
2. Move-out Date: All move-out dates are 12 months out from move-in date. Move-in date is the current date and that actively changes depending on when you run the code. For this reason, there is no manual set-move out date.
3. No Inflation: Usually, property managers will increase rent and security deposit after a tenant moves out to account for inflation but for the simplicity of the project, I will not be considering inflation.
4. Apartment Buildings only: Properties are the Apartment Building itself and Apartments = apartment units so you cannot add apartments unless you add a new property. The company only invests in apartment buildings in cities.
5. All monthly charges from amenities are always included in full so tenants do not have the option to opt-out of amenity fees
6. We assume that tenants have enough money for rent since in real life, property managers cannot check tenants' bank accounts.
7. Payment Status not checked upon move-out. I am assuming that all tenants move out after paying even though that may not be accurate and synched up with the database for simplicity.
8. Person not removed after move out. Although the tenant information itself is removed, the person's information is still kept in the database in case they want to move into another apartment.
9. Each apartment unit cannot hold more than the number of bedrooms it has. Rent is split by bedroom for simplicity. Apartment ID is based on the bedrooms itself. You can tell if it's in the same unit by the apt_num.
10. Move-in date is the date the property manager adds the person to the lease for simplicity.
11. All employee id's were hard-coded since in this program, you shouldn't be able to edit the employee table.

## Running Tenant Interface

## Running Property Manager Interface

1. Record Visit Data
    * Current Prospective Tenant ID's will be listed, 



## Classes

### Enterprise.java //make sure to change to username (note to self)

* Main Method here!

### Menu.java

* 

### Tenant.java

### Randomize.java

### Validator.java

### Test.java
