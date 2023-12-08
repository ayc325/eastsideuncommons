import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Property {
    /*
     * Remember to validate who's a Property Manager.
     * 1. Record Visit Data
     * 2. Record Lease Data
     * 3. Record Move-out Data
     * a. Auto Set Move-out Date (12 months out from move-in date)
     * 4. Add Person to a lease
     * a. Gives a list of inputs to ask person
     * b. First Name, Middle Initial, Last Name, Phone Number, Date of Birth
     */
    /***
     * Add Prospective Tenant (randomize Prosp_Tenantid) and add Person and all the
     * info
     * link an apartment_id (that is (null) under tenants) to the prospective
     * tenant, the count of the number of apartment id's the prospective tenant
     * table
     * is the visit_status in persons
     * 
     * @param conn
     */
    public static int recordVisitData(Connection conn) {
        Scanner scnr = new Scanner(System.in);
        Randomize randomize = new Randomize();
        Validator validator = new Validator();
        List<Integer> emptyAptId = new ArrayList<>();
        List<Integer> visitedAptId = new ArrayList<>();
        List<Integer> prospectiveTenantId = new ArrayList<>();
        int tempApt = 1;
        int numVisitedApt = 0;
        int tempId = 0;
        int prosp_tenantid = 0;
        int personId = 0;

        // no duplicates to assign a new prosp_tenantid, but there can be duplicates in
        // the table when recording apt_id data
        // print all prospective tenants
        prospectiveTenantId = getProspectiveTenantId(conn);
        System.out.println("Current Propsective IDs: ");
        for (int k = 0; k < prospectiveTenantId.size(); k++) {
            System.out.println(prospectiveTenantId.get(k));
        }
        System.out.println("Enter a prospective tenant id");
        if (scnr.hasNextInt()) {
            tempId = scnr.nextInt();
            if (validator.idInData(tempId, 5, conn)) {
                prosp_tenantid = tempId;
            } else {
                System.out.println("Prospective tenant doesn't exist");
                personId = addPerson(conn);
                if (personId == 0) {
                    return 0;
                }
                prosp_tenantid = randomize.randomizeID(2, conn);
                System.out.println("New Prospective Tenant ID: " + prosp_tenantid);
            }
        }

        // addProspTenant(conn, prosp_tenantid, personId);

        // print list of empty apartments
        System.out.println("Which apartment did the Prospective Tenant " + prosp_tenantid + " visit?");
        // get empty apartment list
        System.out.println("Empty Apartments:");
        emptyAptId = getEmptyApartments(conn);
        for (int i = 0; i < emptyAptId.size(); i++) {
            System.out.println(emptyAptId.get(i));
        }
        System.out.println("Enter ApartmentID one line at a time, duplicates may exist and press 0 when done adding.");
        do {
            if (scnr.hasNextInt()) {
                tempApt = scnr.nextInt();
                if (emptyAptId.contains(tempApt)) {
                    visitedAptId.add(tempApt);
                    tempApt = 1;
                } else if (tempApt == 0) {
                    break;
                } else {
                    System.out.println("Apartment not empty.");
                    tempApt = 1;
                }
            }
        } while (tempApt == 1);

        // insert visitedapt into sql
        numVisitedApt = addVisitedApartment(conn, prosp_tenantid, personId, visitedAptId);

        // insert numVisitedApt into visit_status in person table
        // System.out.println(numVisitedApt);
        // System.out.println(personId);
        addVisitStatus(numVisitedApt, personId, conn);

        System.out.println("Prospective Tenant: " + prosp_tenantid + " has visited " + selectVisitStatus(personId, conn)
                + " apartments.");
        System.out.println("Those apartments are: ");
        for (int j = 0; j < visitedAptId.size(); j++) {
            System.out.println(visitedAptId.get(j));
        }
        return 0;
    }

    /***
     * record lease data: records tenant term,
     * 
     * @param conn
     */
    public static void recordLeaseData(Connection conn) {
        //
    }

    /***
     * if current date is past move out date, remove tenant row
     * set tenant id in apartment id to null
     * return # of tenants that moved out
     * 
     * @param conn
     */
    public static int recordMoveOut(Connection conn) {
        //

        // get moveoutdate
        // save move out dates
        List<String> dates = getMoveOutDate(conn);
        List<String> moveOutList = new ArrayList<>();
        List<Integer> moveOutTenants = new ArrayList<>();

        // compare dates
        // save move out dates that return false
        for (int i = 0; i < dates.size(); i++) {
            if (isDateAfterCurrent(dates.get(i)) == false) {
                moveOutList.add(dates.get(i));
            }
        }
        // get tenantid on the moveoutlist
        if (!moveOutList.isEmpty()) {
            moveOutTenants = moveOutList(moveOutList, conn);
        } else {
            System.out.println("No one needs to move out");
        }

        if (!moveOutTenants.isEmpty()) {
            System.out.println("Tenants that need to move out: ");
            for (int j = 0; j < moveOutTenants.size(); j++) {
                System.out.println(moveOutTenants.get(j));
            }
            removeTenantApartment(moveOutTenants, conn);
            // remove tenants from tenant row, person information is still saved.
        } else {
            System.out.println("No one needs to move out");
        }

        return moveOutTenants.size();

    }

    public static void removeTenant(List<Integer> tenantList, Connection conn) {
        System.out.println("hello");
    }

    public static List<Integer> getMoveOutApartments(List<Integer> tenantList, Connection conn) {
        List<Integer> moveOutTenants = new ArrayList<>();
        String query = " ";
        return moveOutTenants;
    }

    public static void removeTenantApartment(List<Integer> tenantList, Connection conn) {
        String query = " ";
        query = "UPDATE apartments SET tenantid = NULL WHERE tenantid = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < tenantList.size(); i++) {
                // Set the parameter before executing the query
                preparedStatement.setInt(1, tenantList.get(i));

                // Execute the query and get the result set
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    // Iterate through the result set and add tenant IDs to the list
                    while (rs.next()) {
                        int tenantid = rs.getInt("tenantid");
                        tenantList.add(tenantid);
                        System.out.println("Tenant: " + tenantid + "moved out.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> moveOutList(List<String> moveOutDate, Connection conn) {
        List<Integer> moveOutTenants = new ArrayList<>();
        String query = " ";
        query = "SELECT tenantid FROM tenants WHERE moveoutdate = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            for (int i = 0; i < moveOutDate.size(); i++) {
                // Set the parameter before executing the query
                preparedStatement.setString(1, moveOutDate.get(i));

                // Execute the query and get the result set
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    // Iterate through the result set and add tenant IDs to the list
                    while (rs.next()) {
                        int tenantid = rs.getInt("tenantid");
                        moveOutTenants.add(tenantid);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moveOutTenants;
    }

    public static boolean isDateAfterCurrent(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // Parse the provided date
        LocalDate providedDate = LocalDate.parse(dateString, formatter);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Compare the dates
        return providedDate.isAfter(currentDate);
    }

    /***
     * 
     * @param type 1. date
     * @param conn
     * @return
     */
    public static List<String> getMoveOutDate(Connection conn) {
        List<String> moveOutDates = new ArrayList<>();
        String query = " ";
        query = "SELECT moveoutdate FROM tenants";
        try (
                // Create a PreparedStatement with the SQL query
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                // Execute the query and get the result set
                ResultSet rs = preparedStatement.executeQuery();) {
            // Iterate through the result set and add apartment IDs to the list
            while (rs.next()) {
                String moveOutDate = rs.getString("moveoutdate");
                moveOutDates.add(moveOutDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return moveOutDates;
    }

    /***
     * generates a random unique tenant id
     * if prosp_tenant id exists, then use person data, if it doesn't exist, add a
     * person
     * set a move out date (12 months out from current date)
     * assign tenantid to apartments table
     * take rent from apartments table and sync it with rent in tenant table
     * set payment status to unpaid
     * set amount due to security deposit
     * 
     * @param conn
     */
    public static int addPersonToLease(Connection conn) {
        // initialize all classes
        Scanner scnr = new Scanner(System.in);
        Randomize randomize = new Randomize();
        Validator validator = new Validator();
        // initialize all int's
        int tenantId = 0;
        int tempTenantId = 0;
        int prosp_tenantid = 0;
        int personid = 0;
        int selectedApt = 0;
        int tempId = 0;
        int success = 0;
        // initialize all lists
        List<Integer> prospectiveTenantId = new ArrayList<>();
        List<Integer> emptyApt = new ArrayList<>();
        // initialize all doubles and booleans and Strings
        String moveOutDate = " ";
        boolean hasPerson = false;
        double rent = 0.0;
        double security_deposit = 0.0;
        double amountDue = 0.0;

        do {
            // if prosp_tenant id exists (if person id exists)
            prospectiveTenantId = getProspectiveTenantId(conn);
            System.out.println("Current Propsective IDs: ");
            for (int k = 0; k < prospectiveTenantId.size(); k++) {
                System.out.println(prospectiveTenantId.get(k));
            }
            System.out.println("Enter a prospective tenant to add to the lease: ");
            if (scnr.hasNextInt()) {
                tempId = scnr.nextInt();
                if (validator.idInData(tempId, 5, conn)) {
                    prosp_tenantid = tempId;
                    // get personid from prospectivetenant id
                    personid = getPersonId(prosp_tenantid, conn);
                    hasPerson = true;
                } else {
                    // produce a random propsective id
                    System.out.println("Prospective tenant does not exist, creating person...");
                    personid = addPerson(conn);
                    hasPerson = true;
                }
            } else {
                System.out.println("Not a number.");
                scnr.nextLine();
            }
        } while (hasPerson == false);

        do {
            emptyApt = getEmptyApartments(conn);
            if (emptyApt.isEmpty()) {
                System.out.println("Sorry, there are no available apartments to move into at the moment.");
                selectedApt = -1;
                return selectedApt;
            } else {
                for (int i = 0; i < emptyApt.size(); i++) {
                    System.out.println(emptyApt.get(i));
                }
            }
            System.out.println("Select the apartment id to move into");
            if (scnr.hasNextInt()) {
                tempId = scnr.nextInt();
                if (emptyApt.contains(tempId)) {
                    selectedApt = tempId;
                    // add tenant id to the row

                } else {
                    System.out.println("Sorry, that apartment is unavailable");
                }
            } else {
                System.out.println("Not a number.");
                scnr.nextLine();
            }
        } while (selectedApt == 0);

        do {
            tempTenantId = randomize.randomizeID(5, conn);
            tenantId = tempTenantId;
            // add tenantid into apartment table
            updateApartmentTenant(tenantId, selectedApt, conn);
            // get rent
            rent = getRent(selectedApt, tenantId, conn);
            // get amount due = security deposit
            security_deposit = getSecurityDeposit(selectedApt, tenantId, conn);
            amountDue = rent + security_deposit;
            // moveout date
            moveOutDate = generateMoveOutDate();
            // test print
            System.out.println("New tenant id is " + tenantId);
            System.out.println("security deposit" + security_deposit);
            System.out.println("amountDue" + amountDue);
            // System.out.println("Prospective tenant id" + prosp_tenantid);
            // add tenantid into tenant table
            tenantId = addTenant(tenantId, personid, moveOutDate, rent, amountDue, conn);
            if (tenantId != 0) {
                // produce a random tenant id
                System.out.println("New tenant id is " + tenantId);
                success = tenantId;
            }
        } while (success == 0);

        return 0;

    }

    public static int addTenant(int tenantId, int personId, String moveOutDate, double rent, double amountDue,
            Connection conn) {
        String query = "INSERT INTO tenants (tenantid, personid, moveoutdate, rent, payment_status, amount_due) VALUES (?, ?, ?, ?, 'UNPAID', ?)";
        try (
                // Create a PreparedStatement with the SQL query and the option to retrieve
                // generated keys
                PreparedStatement preparedStatement = conn.prepareStatement(query);) {
            // Set the parameters
            preparedStatement.setInt(1, tenantId);
            preparedStatement.setInt(2, personId);
            preparedStatement.setString(3, moveOutDate);
            preparedStatement.setDouble(4, rent);
            preparedStatement.setDouble(5, amountDue);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            // Retrieve the generated keys
            // Execute the query and get the result set
            if (rowsAffected > 0) {
                System.out.println("Tenant " + tenantId + " added.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return 0 if tenantid retrieval fails or the query doesn't affect any rows
        return 0;
    }

    public static int getPersonId(int prosp_tenantid, Connection conn) {
        String query = " ";
        query = "SELECT personid FROM prospectivetenants WHERE prosp_tenantid = ?";
        try (
                // Create a PreparedStatement with the SQL query
                PreparedStatement preparedStatement = conn.prepareStatement(query);) {
            // Set the parameter
            preparedStatement.setInt(1, prosp_tenantid);

            // Execute the query and get the result set
            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Check if a result is available
                if (rs.next()) {
                    // Return the personid
                    return rs.getInt("personid");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return 0 if there is an error or if no personid is found
        return 0;

    }

    public static List<Integer> getProspectiveTenantId(Connection conn) {
        List<Integer> listOfId = new ArrayList<>();
        String query = " ";
        query = "SELECT distinct(prosp_tenantid) FROM prospectivetenants";
        try (
                // Create a PreparedStatement with the SQL query
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                // Execute the query and get the result set
                ResultSet rs = preparedStatement.executeQuery();) {
            // Iterate through the result set and add apartment IDs to the list
            while (rs.next()) {
                int prosp_tenantid = rs.getInt(1);
                listOfId.add(prosp_tenantid);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfId;
    }

    /***
     * 
     * @param selectedApt
     * @param tenantId
     * @param conn
     * @return
     */
    public static double getSecurityDeposit(int selectedApt, int tenantId, Connection conn) {
        double security_deposit = 0.0;
        String query = " ";
        query = "SELECT security_deposit FROM apartments WHERE apartmentid = ? AND tenantid = ?";
        try (PreparedStatement prepdstatement = conn.prepareStatement(query)) {
            prepdstatement.setInt(1, selectedApt);
            prepdstatement.setInt(2, tenantId);
            // Execute the query and get the result set
            ResultSet rs = prepdstatement.executeQuery();

            // Iterate through the result set and add apartment IDs to the list
            while (rs.next()) {
                security_deposit = rs.getInt(1);
                return security_deposit;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return security_deposit;
    }

    /***
     * 
     * @param selectedApt
     * @param tenantId
     * @param conn
     * @return
     */
    public static double getRent(int selectedApt, int tenantId, Connection conn) {
        double rent = 0.0;
        String query = " ";
        query = "SELECT monthly_rent FROM apartments WHERE apartmentid = ? AND tenantid = ?";
        try (
                // Create a PreparedStatement with the SQL query
                PreparedStatement preparedStatement = conn.prepareStatement(query);) {
            // Set the parameters
            preparedStatement.setInt(1, selectedApt);
            preparedStatement.setInt(2, tenantId);

            // Execute the query and get the result set
            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Iterate through the result set and get the monthly rent
                if (rs.next()) {
                    rent = rs.getDouble("monthly_rent");
                    return rent;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rent;

    }

    /***
     * 
     * @param tenantId
     * @param selectedApt
     * @param conn
     */
    public static void updateApartmentTenant(int tenantId, int selectedApt, Connection conn) {
        String query = " ";
        query = "UPDATE apartments SET tenantid = ? WHERE apartmentid = ?";
        try (PreparedStatement prepdstatement = conn.prepareStatement(query)) {
            prepdstatement.setInt(1, tenantId);
            prepdstatement.setInt(2, selectedApt);

            int rowsAffected = prepdstatement.executeUpdate();

            // return rs.getInt(1);
            if (rowsAffected > 0) {
                System.out.println("Tenant officially moved into apartment " + selectedApt);
            } else {
                System.out.println("Tenant did not move into apartment");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /***
     * 
     * @return
     */
    public static String generateMoveOutDate() {
        // Get today's date
        Date currentDate = new Date();

        // Use Calendar to add 12 months to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, 12);
        Date moveOutDate = calendar.getTime();

        // Format the moveOutDate as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String moveOutDateString = dateFormat.format(moveOutDate);

        return moveOutDateString;
    }

    /***
     * 
     * @param personId
     * @param conn
     * @return
     */
    public static int selectVisitStatus(int personId, Connection conn) {
        int visit_status = 0;
        String query = " ";
        query = "SELECT visit_status FROM persons WHERE personid = ?";
        try (
                // Create a PreparedStatement with the SQL query
                PreparedStatement preparedStatement = conn.prepareStatement(query);) {
            // Set the personId parameter
            preparedStatement.setInt(1, personId);

            // Execute the query and get the result set
            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Iterate through the result set and add apartment IDs to the list
                while (rs.next()) {
                    visit_status = rs.getInt("visit_status");
                    return visit_status;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visit_status;
    }

    public static int addVisitStatus(int num, int personId, Connection conn) {
        String query = " ";
        query = "UPDATE persons SET visit_status = ? WHERE personid = ?";
        try (PreparedStatement prepdstatement = conn.prepareStatement(query)) {
            prepdstatement.setInt(1, num);
            prepdstatement.setInt(2, personId);

            int rowsAffected = prepdstatement.executeUpdate();

            // return rs.getInt(1);
            if (rowsAffected > 0) {
                System.out.println("Visit Status successfully updated");
                return num;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return num;
    }

    /***
     * 
     * @param conn
     * @return
     */
    public static List<Integer> getEmptyApartments(Connection conn) {
        List<Integer> emptyAptId = new ArrayList<>();
        String query = " ";
        query = "SELECT apartmentid FROM apartments WHERE tenantid IS NULL";
        try (
                // Create a PreparedStatement with the SQL query
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                // Execute the query and get the result set
                ResultSet rs = preparedStatement.executeQuery();) {
            // Iterate through the result set and add apartment IDs to the list
            while (rs.next()) {
                int apartmentId = rs.getInt("apartmentid");
                emptyAptId.add(apartmentId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emptyAptId;
    }

    public static int addVisitedApartment(Connection conn, int prosp_tenantid, int personId, List<Integer> visitedApt) {
        String query = " ";
        int rowsAffected = 0;
        if (visitedApt != null) {
            for (int i = 0; i < visitedApt.size(); i++) {
                query = "INSERT INTO prospectivetenants (prosp_tenantid, personid, apartmentid) VALUES (?, ?, ?)";
                try (PreparedStatement prepdstatement = conn.prepareStatement(query)) {
                    prepdstatement.setInt(1, prosp_tenantid);
                    prepdstatement.setInt(2, personId);
                    prepdstatement.setInt(3, visitedApt.get(i));

                    rowsAffected = prepdstatement.executeUpdate();

                    // return rs.getInt(1);
                    if (rowsAffected > 0) {
                        System.out.println("Prospective tenant and visit information successfully added");
                        return rowsAffected;
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        } else {
            System.out.println("Prospective tenant not added because they did not visit");
        }
        return rowsAffected;
    }

    public static int addPerson(Connection conn) {// property manager
        Scanner scnr = new Scanner(System.in);
        Randomize randomize = new Randomize();
        Validator validator = new Validator();
        Tenant tenant = new Tenant();
        Menu menu = new Menu();
        int input = 0;
        List<String> name = new ArrayList<>();
        String phoneNumber = " ";
        String birthdate = " ";
        List<String> payment = new ArrayList<>();
        while (input == 0) {
            System.out.println("Add Person Information:");
            System.out.println("Name");
            name = tenant.updateName(conn);
            System.out.println("Phone Number");
            phoneNumber = tenant.updatePhoneNumber(conn);
            System.out.println("Date of Birth"); // Make sure it's in MM/DD/YYYY format
            birthdate = updateBirthdate(conn);

            int personId = randomize.randomizeID(1, conn);

            input = addPerson(name, phoneNumber, birthdate, personId, conn);
            // Make sure number is valid in Enterprise.java
        }
        return input;
    }

    /***
     * 
     * @param name
     * @param phoneNumber
     * @param conn
     * @return
     */
    public static int addPerson(List<String> name, String phoneNumber, String birthdate, int personId,
            Connection conn) {
        String firstName = name.get(0);
        String middleInitial = name.get(1);
        String lastName = name.get(2);

        /*
         * running query to update name
         */
        String query;

        query = "INSERT INTO persons (personid, first_name, middle_initial, last_name, phone_number, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement prepdstatement = conn.prepareStatement(query)) {
            prepdstatement.setInt(1, personId);
            prepdstatement.setString(2, firstName);
            prepdstatement.setString(3, middleInitial);
            prepdstatement.setString(4, lastName);
            prepdstatement.setString(5, phoneNumber);
            prepdstatement.setString(6, birthdate);

            int rowsAffected = prepdstatement.executeUpdate();

            // return rs.getInt(1);
            if (rowsAffected > 0) {
                System.out.println("Person successfully added!");
                if (!"(null)".equals(middleInitial)) {
                    System.out.println(
                            "Your added person is: " + firstName + " " + middleInitial + " " + lastName + ".\n" +
                                    "Phone number is " + phoneNumber + ".\n" +
                                    "Birthdate is " + birthdate + ".");
                    return personId;
                } else {
                    System.out.println("Your added person is: " + firstName + " " + lastName + ".\n" +
                            "Phone number is " + phoneNumber + ".\n" +
                            "Birthdate is " + birthdate + ".");
                    return personId;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }

    public static String updateBirthdate(Connection conn) {
        Scanner scnr = new Scanner(System.in);
        Validator validator = new Validator();
        String birthdate = " ";
        // phoneNumber
        do {
            System.out.println("Enter Birthdate MM/DD/YYYY");
            birthdate = scnr.nextLine();
            if (validator.isValidBirthdate(birthdate)) {
                return birthdate;
            } else {
                System.out.println("Invalid format. Make sure it's in MM/DD/YYY format.");
            }
        } while (!validator.isValidName(birthdate));
        return birthdate;
    }
}
