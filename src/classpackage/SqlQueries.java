package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;

import java.util.Date;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by paul thomas on 17.03.2016.
 */

// TODO: 07.04.2016 menu_header, menu_line

public class SqlQueries extends DBConnector {

    // TODO: 10.04.2016 Need to reevaluate how F.ex addEmployee and addSupplier creates new address when run, what to do with collisions?
    // TODO: 11.04.2016 Agree on wether customers should have several subscriptions

    // TODO: 04.04.2016 the strings for Gui that deals with Orders should use just so we have this standardized
    public final String CREATED = "Created";
    public final String INPREPARATION = "In preparation";
    public final String READYFORDELIVERY = "Ready for delivery";
    public final String UNDERDELIVERY = "Under delivery";
    public final String DELIVERED = "In preparation";

    public String getINPREPARATION() {
        return INPREPARATION;
    }

    public String getCREATED() {
        return CREATED;
    }

    public String getREADYFORDELIVERY() {
        return READYFORDELIVERY;
    }

    public String getUNDERDELIVERY() {
        return UNDERDELIVERY;
    }

    public String getDELIVERED() {
        return DELIVERED;
    }

    /*
    Class where all methods that sql is required, shall be written.
     For example every method that requires getting data from database.
     */
    PreparedStatement selectQuery;

    PreparedStatement insertQuery;
    PreparedStatement updateQuery;

    public SqlQueries() {

    }


    // method for registering new Ingredient
    // Method for registering new customer
    /*MultipleSelectionModel selector = new MultipleSelectionModel() {
    }*/
    // TODO: 31.03.2016 is this method finished or should there sqlcleanup be called? As well as a more specific Exception handled?
    public boolean addAddress(Address newAddress) {
        boolean success = false;
        try {
            String sql = "INSERT INTO address(address, zipcode) VALUES(?, ?);";
            insertQuery = con.prepareStatement(sql);
            insertQuery.setString(1, newAddress.getAddress());
            insertQuery.setInt(2, newAddress.getZipCode().getZipCode());
            insertQuery.executeUpdate();
            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
            newAddress.setAddressId(res.getInt(1));
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method addAddress failed");
        }
        return success;
    }

    public Address getAddress(int addressId) {
        try {
            String selectSql = "SELECT address, zipcode FROM address WHERE address_id = " + addressId;
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            if (!res.next()) return null;
            String address = res.getString(1);
            int zipCode = res.getInt(2);
            ZipCode zipCodeObject = getZipcodeByZipInt(zipCode);
            return new Address(addressId, address, zipCodeObject);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getAddress failed");
        }
        return null;
    }


    /*Customer methods:*/
    //Method for registering Customer
    public boolean addCustomer(Customer theCustomer) {
        boolean success = false;

        try {
            con.setAutoCommit(false);
            if (addAddress(theCustomer.getAddress())) {
                return false;
            }
            String sqlSetning = "INSERT INTO customer(address_id, business_name, first_name, last_name, phone, email, isbusiness) VALUES(?,?,?,?,?,?,?)";
            int isBusiness = 0;
            if (theCustomer.isBusiness()) {
                isBusiness = 1;
            }

            insertQuery = con.prepareStatement(sqlSetning);
            insertQuery.setInt(1, theCustomer.getAddress().getAddressId());
            insertQuery.setString(2, theCustomer.getBusinessName());
            insertQuery.setString(3, theCustomer.getFirstName());
            insertQuery.setString(4, theCustomer.getLastName());
            insertQuery.setInt(5, theCustomer.getPhoneNumber());
            insertQuery.setString(6, theCustomer.getEmail());
            insertQuery.setInt(7, isBusiness); // 0 is not business, 1 is!
            insertQuery.execute();

            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
            theCustomer.setCustomerId(res.getInt(1));
            // TODO: 31.03.2016 compare registerAddress with how this method should be done!
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Method addCustomer failed");
            SqlCleanup.lukkForbindelse(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
        }
        return success;
    }

    //    Method for getting all customers
    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            String selectSql = "SELECT * FROM customer";
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            while (res.next()) {
                boolean isBusiness = false;
                if (res.getInt("isBusiness") == 1) {
                    isBusiness = true;
                }
                customers.add(new Customer(res.getInt(1), isBusiness, res.getString("email"), res.getString("first_name"),
                        res.getString("last_name"), res.getInt("phone"), res.getString("business_name"), (getAddress(res.getInt("address_id")))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getAllIngredients failed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("method getAllIngredients failed, not Sql exception!");
        }
        return customers;
    }




    /*Dish methods*/

    // Method for  Registering Dish in database
    public boolean addDish(Dish theDish) {
        boolean success = false;
        try {
            String sqlSetning = "INSERT INTO dish(price, name) VALUES(?,?)";
            insertQuery = con.prepareStatement(sqlSetning);
            insertQuery.setDouble(1, theDish.getPrice());
            insertQuery.setString(2, theDish.getDishName());
            insertQuery.execute();
            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
            theDish.setDishId(res.getInt(1));
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Method addDish failed");
            SqlCleanup.lukkForbindelse(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
        }
        return success;
    }

    //    Method for adding Dish in Menu
    public boolean addDishInMenu(Menu menu, ObservableList<MenuLine> menuLine) {
        boolean success = false;
        try {
            con.setAutoCommit(false);
            for (MenuLine oneLine :
                    menuLine) {

                String sqlSetning = "INSERT INTO menu_line VALUES(?,?,?,?)";
                insertQuery = con.prepareStatement(sqlSetning);
                insertQuery.setInt(1, oneLine.getDish().getDishId());
                insertQuery.setInt(2, menu.getMenuId());
                insertQuery.setInt(3, oneLine.getAmount());
                insertQuery.setDouble(4, oneLine.getPriceFactor());
                insertQuery.execute();
            }
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Method addDishInMenu failed");
            SqlCleanup.lukkForbindelse(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
        }
        return success;
    }

    public ObservableList<DishLine> getDishLinesByDish(Dish dish, ObservableList<Ingredient> allIngredients) {
        ObservableList<DishLine> dishLines = FXCollections.observableArrayList();
        Map<Integer, Double> collection = new HashMap<>();
        try {
            String selectSql = "SELECT ingredient_id, quantity FROM dish_line WHERE dish_id = " + dish.getDishId();
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            while (res.next()) {
                collection.put(res.getInt(1), res.getDouble("quantity"));
            }

            for (Map.Entry<Integer, Double> entry : collection.entrySet()) {
                Integer key = entry.getKey();
                Double value = entry.getValue();
                for (Ingredient ingredient : allIngredients) {
                    if (ingredient.getIngredientId() == key) {
                        dishLines.add(new DishLine(ingredient, value));
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getDishLinesByDish failed");
        }

        allIngredients.forEach(ingredient -> System.out.println("All ingredients:\t" + ingredient.toString()));
        dishLines.forEach(dishLine -> System.out.println("Final:\t" + dishLine.toString()));
        return dishLines;
    }










    /*Employee methods*/

    public boolean addEmployee(Employee newEmp) {
        boolean ok = false;

        try {
            con.setAutoCommit(false);
            if (!addAddress(newEmp.getAddress())) {
                return false;
            }
            String insertSql = "INSERT INTO employee VALUES(?,?,?,?,?,?,?,?,?,?)";
            insertQuery = con.prepareStatement(insertSql);
            insertQuery.setInt(1, newEmp.getEmployeeId());
            insertQuery.setString(2, newEmp.getFirstName());
            insertQuery.setString(3, newEmp.getLastName());
            insertQuery.setInt(4, newEmp.getPhoneNo());
            insertQuery.setString(5, newEmp.geteMail());
            insertQuery.setInt(6, newEmp.getAddress().getAddressId());
            insertQuery.setString(7, newEmp.getUsername());
            insertQuery.setInt(8, newEmp.getPosition().getId());
            insertQuery.setDouble(9, newEmp.getSalary());
            insertQuery.setString(10, newEmp.getPassHash());
            insertQuery.executeUpdate();

            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
            newEmp.setEmployeeId(res.getInt(1));
            ok = true;
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            System.out.println("Restraint violation in addEmployee");
        } catch (SQLException e) {
            System.out.println("Something went wrong: user registration failed in method addEmployee");
            SqlCleanup.rullTilbake(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
            SqlCleanup.settAutoCommit(con);
        }
        return ok;
    }


    public ObservableList<Employee> getEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try {
            String selectSql = "SELECT e.employee_id, e.first_name, e.last_name, e.phone, e.email, e.username, e.salary, e.passhash, e.pos_id, e.address_id" +
                    " FROM employee e ORDER BY e.employee_id";
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            while (res.next()) {
                int employeeId = res.getInt(1);
                String firstName = res.getString(2);
                String lastName = res.getString(3);
                int phoneNo = res.getInt(4);
                String eMail = res.getString(5);
                String username = res.getString(6);
                double salary = res.getDouble(7);
                String passHash = res.getString(8);
                int posId = res.getInt(9);
                int addressId = res.getInt(10);
                Address address = getAddress(addressId);
                EmployeePosition position = getEmployeePosition(posId);
                Employee employee = new Employee(employeeId, username, firstName, lastName, phoneNo, eMail, salary, passHash, address, position);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getEmployees failed");
        }
        return employees;
    }

    /*
        public Employee getUser(String username, String passwordHash) {
            int personId = -1;
            int posId = -1;
            double salary = -1;
            int attempts = 0;
            boolean ok = false;
            do {
                try {
    //                con.setAutoCommit(false);
                    String selectSql = "SELECT employee_id, username, pos_id, salary, passhash FROM employee WHERE username = ? AND passHash = ?";
                    selectQuery = con.prepareStatement(selectSql);
                    selectQuery.setString(1, username);
                    selectQuery.setString(2, passwordHash);
                    res = selectQuery.executeQuery();
                    res.next();
                    personId = res.getInt("employee_id");
                    posId = res.getInt("pos_id");
                    salary = res.getDouble("salary");
                    //       con.commit();
                    ok = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    SqlCleanup.lukkResSet(res);
                    SqlCleanup.settAutoCommit(con);
                }
            } while (!ok);
            if (personId != -1) {
                return new Employee(personId, username, posId, salary);
            }
            return null;
        }
    */
    public ObservableList<EmployeePosition> getEmployeePositions() {
        ObservableList<EmployeePosition> employeePositions = FXCollections.observableArrayList();
        try {
            String selectSql = "SELECT pos_id, description, default_salary FROM employee_position";
            Statement s = con.createStatement();
            ResultSet res = s.executeQuery(selectSql);
            while (res.next()) {
                EmployeePosition employeePosition = new EmployeePosition(res.getInt(1), res.getString(2), res.getDouble(3));
                employeePositions.add(employeePosition);
            }
            return employeePositions;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public EmployeePosition getEmployeePosition(int posId) {
        try {
            String selectSql = "SELECT description, default_salary FROM employee_position WHERE pos_id = " + posId;
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            if (!res.next()) return null;

            String description = res.getString(1);
            double salary = res.getDouble(2);
            return new EmployeePosition(posId, description, salary);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getAddress failed");
        }
        return null;
    }

    /*Ingredient methods*/

    // Method for adding Ingredients

    //    Method is usable for adding ingredient with or without new supplier
    public boolean addIngredient(Ingredient ingredient) {
        boolean ok = false;


        try {


            if (ingredient.getSupplier().getSupplierId() == 0) {
                con.setAutoCommit(false);
                if (!addSupplier(ingredient.getSupplier())) {
                    con.setAutoCommit(true);
                    return false;
                }
            }

            String insertSql = "INSERT INTO ingredient(supplier_id, quantity_owned, unit, price, description) VALUES(?,?,?,?,?)";
            insertQuery = con.prepareStatement(insertSql);
            insertQuery.setInt(1, ingredient.getSupplier().getSupplierId());
            insertQuery.setDouble(2, ingredient.getQuantityOwned());
            insertQuery.setString(3, ingredient.getUnit());
            insertQuery.setDouble(4, ingredient.getPrice());
            insertQuery.setString(5, ingredient.getDescription());
            insertQuery.executeUpdate();

            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
            ingredient.setIngredientId(res.getInt(1));
            ok = true;
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            System.out.println("Restraint violation in addIngredient");
        } catch (SQLException e) {
            System.out.println("Something went wrong: user registration failed in method addIngredient");
            SqlCleanup.rullTilbake(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
            SqlCleanup.settAutoCommit(con);
        }
        return ok;
    }
/*
//    Method for adding Ingredient in Dish
public boolean addIngredinetInDish()


    */




    /*public boolean addIngredient(int supplierId, double quantityOwned, double quantityReserved, String unit, double price, String description) {
        boolean success = false;

        try {
            con.setAutoCommit(false);
            String insertSql = "INSERT INTO ingredient(supplier_id, quantity_owned, quantity_reserved, unit, price, description) VALUES(?,?,?,?,?,?)";
            insertQuery = con.prepareStatement(insertSql);
            insertQuery.setInt(1, supplierId);
            insertQuery.setDouble(2, quantityOwned);
            insertQuery.setDouble(3, quantityReserved);
            insertQuery.setString(4, unit);
            insertQuery.setDouble(5, price);
            insertQuery.setString(6, description);
            insertQuery.execute();

            con.commit();
            success = true;
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Something went wrong: user registration failed.");
            SqlCleanup.lukkForbindelse(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
            SqlCleanup.settAutoCommit(con);
        }
        return success;
    }*/







    /*public boolean registerEmployee(String firstName, String lastName, int phoneNumber, String email, int addressId, String userName, int positionId,
                                    int salary, String passHash) {
        boolean success = false;
        try {
            con.setAutoCommit(false);
            String sqlSetning = "INSERT INTO employee(first_name, last_name, phone, email, address_id, username, pos_id, salary, passhash) VALUES(?,?,?,?,?,?,?,?,?)";
            insertQuery = con.prepareStatement(sqlSetning);
            insertQuery.setString(1, firstName);
            insertQuery.setString(2, lastName);
            insertQuery.setInt(3, phoneNumber);
            insertQuery.setString(4, email);
            insertQuery.setInt(5, addressId);
            insertQuery.setString(6, userName);
            insertQuery.setInt(7, positionId);
            insertQuery.setInt(8, salary);
            insertQuery.setString(9, passHash);
            insertQuery.execute();
            con.commit();
            success = true;
        } catch (SQLIntegrityConstraintViolationException e) {
//            this has to match gui and have to be handled in a better way!! // TODO: 27.03.2016
            System.out.println("Unique value restraint!!!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Method registerEmployee failed");
            SqlCleanup.lukkForbindelse(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
            SqlCleanup.settAutoCommit(con);
        }
        return success;
    }*/

    /*Order methods:*/


    /*public ObservableList<Order> getOrders(int posId) {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        try {
            String selectSql = "";
            //ceo and sales
            if (posId == 1) {
                selectSql = "SELECT * FROM n_order";
                //CHEF
            } else if (posId == 2) {
                selectSql = "SELECT * FROM n_order WHERE status = ? OR ? OR ?";
                selectQuery.setString(1, CREATED);
                selectQuery.setString(2, INPREPARATION);
                selectQuery.setString(3, READYFORDELIVERY);
                //DRIVER
            } else if (posId == 3) {
                selectSql = "SELECT * FROM n_order WHERE status = ?;" +
                        "SELECT * FROM n_order WHERE STATUS = ? AND delivery_date = DATE(now())";
                selectQuery.setString(1, READYFORDELIVERY);
                selectQuery.setString(2, DELIVERED);
            } else if (posId == 4) {
                selectSql = "SELECT * FROM n_order WHERE status = ? OR ? OR ?";
                selectQuery.setString(1, CREATED);
                selectQuery.setString(2, INPREPARATION);
                selectQuery.setString(3, READYFORDELIVERY);
                selectQuery.setString(4, DELIVERED);
                //SALES
            }
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            while (res.next()) {
                int orderId = res.getInt("order_id");
                int customerId = res.getInt("customer_id");
                int subscriptionId = res.getInt("subscription_id");
                String customerRequests = res.getString("customer_requests");
                Date deadline = res.getTimestamp("delivery_date");
                double price = res.getDouble("price");
                String address = res.getString("address");
                Order order = new Order(orderId, customerRequests, deadline, price, address);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in method getOrders, most likely to do when calling DRIVER, because results clash (created by Paul)");
        }
        return orders;
    }*/


    // TODO: 28.03.2016 needs to enable a selector in the windows of the individual employee positions that lets you select elements and send them to methods

    /*Method for registering ingredients in DISH
    * takes in a Hashmap where key = ingredientId, value = quantity
    * */
    public boolean addIngredientInDish(Dish dish, ObservableList<DishLine> dishLine) {
        boolean success = false;
        try {
            con.setAutoCommit(false);
            for (DishLine oneLine :
                    dishLine) {

                String sqlSetning = "INSERT INTO dish_line VALUES(?,?,?)";
                insertQuery = con.prepareStatement(sqlSetning);
                insertQuery.setInt(1, oneLine.getIngredient().getIngredientId());
                insertQuery.setInt(2, dish.getDishId());
                insertQuery.setDouble(3, oneLine.getAmount());
                insertQuery.execute();
            }
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Method addIngredientInDish failed");
            SqlCleanup.lukkForbindelse(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
        }
        return success;
    }


    public ObservableList<Ingredient> getAllIngredients(ObservableList<Supplier> allSuppliers) {
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        try {
            String selectSql = "SELECT * FROM ingredient";
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            while (res.next()) {
                int ingredientId = res.getInt("ingredient_id");
                int supplierId = res.getInt("supplier_id");
                Double quantityOwned = res.getDouble("quantity_owned");
                String unit = res.getString("unit");
                Double price = res.getDouble("price");
                String name = res.getString("description");



                ObservableList<Supplier> tempSupplier = FXCollections.observableArrayList();
                allSuppliers.filtered(supplier -> supplier.getSupplierId() == supplierId).forEach(supplier1 -> tempSupplier.add(supplier1));

                try {
                    Ingredient tempIngredient = new Ingredient( ingredientId, name, unit, quantityOwned, price, tempSupplier.get(0));
                    ingredients.add(tempIngredient);
                } catch (Exception e) {
                    System.out.println("method getAllIngredients failed, when trying to get index of temporary arraylist");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getAllIngredients failed");
        }
        return ingredients;
    }



    /*Subscription methods:*/

    public boolean addSubscription(Subscription subscription) {
        boolean ok = false;

        try {
            String insertSql = "INSERT INTO subscription(start_date, end_date) VALUES(?,?)";
            insertQuery = con.prepareStatement(insertSql);
            insertQuery.setDate(1, java.sql.Date.valueOf(subscription.getStartSubscription()));
            insertQuery.setDate(2, java.sql.Date.valueOf(subscription.getEndSubscription()));
            insertQuery.executeQuery();

            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
            subscription.setSubscriptionId(res.getInt(1));
            ok = true;
        } catch (SQLException e) {
            System.out.println("Something went wrong: user registration failed in method addSubscription");
            SqlCleanup.rullTilbake(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
            SqlCleanup.settAutoCommit(con);
        }
        return ok;
    }


    /*Supplier methods:*/

    public boolean addSupplier(Supplier supplier) {
        boolean ok = false;

        try {
            con.setAutoCommit(false);
            if (!addAddress(supplier.getThisAddress())) {
                con.setAutoCommit(true);
                return false;
            }
            String insertSql = "INSERT INTO supplier(address_id, business_name, phone) VALUES(?,?,?)";
            insertQuery = con.prepareStatement(insertSql);
            insertQuery.setInt(1, supplier.getThisAddress().getAddressId());
            insertQuery.setString(2, supplier.getBusinessName());
            insertQuery.setInt(3, supplier.getPhoneNumber());
            insertQuery.executeQuery();

            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
            supplier.setSupplierId(res.getInt(1));
            ok = true;
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            System.out.println("Restraint violation in addSupplier");
        } catch (SQLException e) {
            System.out.println("Something went wrong: user registration failed in method addSupplier");
            SqlCleanup.rullTilbake(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
            SqlCleanup.settAutoCommit(con);
        }
        return ok;
    }

    public ObservableList<Supplier> getAllSuppliers() {
        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        try {
            String selectSql = "SELECT * FROM supplier";
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            while (res.next()) {
                int supplierId = res.getInt(1);
                String businessName = res.getString("business_name");
                int phoneNumber = res.getInt("phone");
                Address address = getAddress(res.getInt("address_id"));
                Supplier supplier = new Supplier(supplierId, phoneNumber, address, businessName);
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getAllSuppliers failed");
        }
        return suppliers;
    }


    public ZipCode getZipcodeByZipInt(int zipcode) {

        try {
            String selectSql = "SELECT * FROM zipcode WHERE zipcode = ?";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, zipcode);
            ResultSet res = selectQuery.executeQuery();
            if (!res.next()) {
                return null;
            } else {
                return new ZipCode(res.getInt(2), res.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in method getZipcode");
        }
        return null;
    }























    /* Miscelleanous Methods:*/


    // Method for reseting password, only to be used by ceo or admin!!!!!!!!!!!!!!!!
    public boolean resetPasswordForUser(Employee theEmployee, String newPasshash) {
        boolean success = false;
        /*
        String query = "update users set num_points = ? where first_name = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt   (1, 6000);
      preparedStmt.setString(2, "Fred");

      // execute the java preparedstatement
      preparedStmt.executeUpdate();
         */
        try {
            con.setAutoCommit(false);
            String query = "UPDATE employee SET passhash = ? WHERE employee_id = ?";
            updateQuery = con.prepareStatement(query);
            updateQuery.setString(1, newPasshash);
            updateQuery.setInt(2, theEmployee.getEmployeeId());
            updateQuery.executeUpdate();
            con.commit();
            success = true;

        } catch (SQLException e) {
            System.out.println("update of password failed in method resetPasswordForUser");
        }
        return success;
    }


}


/* UNUSED OR TEMPLATE METHODS*/
/*public boolean registerEmployee(String firstName, String lastName, int phoneNumber, String email, int addressId, String userName, int positionId,
                                    int salary, String passHash) {
        boolean success = false;
        try {
            con.setAutoCommit(false);
            String sqlSetning = "INSERT INTO employee(first_name, last_name, phone, email, address_id, username, pos_id, salary, passhash) VALUES(?,?,?,?,?,?,?,?,?)";
            insertQuery = con.prepareStatement(sqlSetning);
            insertQuery.setString(1, firstName);
            insertQuery.setString(2, lastName);
            insertQuery.setInt(3, phoneNumber);
            insertQuery.setString(4, email);
            insertQuery.setInt(5, addressId);
            insertQuery.setString(6, userName);
            insertQuery.setInt(7, positionId);
            insertQuery.setInt(8, salary);
            insertQuery.setString(9, passHash);
            insertQuery.execute();
            con.commit();
            success = true;
        } catch (SQLIntegrityConstraintViolationException e) {
//            this has to match gui and have to be handled in a better way!! // TODO: 27.03.2016
            System.out.println("Unique value restraint!!!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Method registerEmployee failed");
            SqlCleanup.lukkForbindelse(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
            SqlCleanup.settAutoCommit(con);
        }
        return success;
    }*/




/*

/*
created
in preparation
ready for delivery
under delivery
delivered

users.sales {
*
}
users.chef{
created
in preparation
ready for delivery
}
users.driver{
ready for delivery
delivered (timestamp)
}
users.ceo
{
*
}
admin{
*
}

 */

/*
public ResultSet getContent(String queryStr) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet resultSet = null;
    CachedRowSetImpl crs = null;
    try {
        Connection conn = dataSource.getConnection();
        stmt = conn.createStatement();
        resultSet = stmt.executeQuery(queryStr);

        crs = new CachedRowSetImpl();
        crs.populate(resultSet);
    } catch (SQLException e) {
        throw new IllegalStateException("Unable to execute query: " + queryStr, e);
    }finally {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Ignored", e);
        }
    }

    return crs;
}
Here is the snippet for creating data source using c3p0:

 ComboPooledDataSource cpds = new ComboPooledDataSource();
            try {
                cpds.setDriverClass("<users.driver class>"); //loads the jdbc users.driver
            } catch (PropertyVetoException e) {
                e.printStackTrace();
                return;
            }
            cpds.setJdbcUrl("jdbc:<url>");
            cpds.setMinPoolSize(5);
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(20);


 javax.sql.DataSource dataSource = cpds;
 */