package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by paul thomas on 17.03.2016.
 * <br>
 * SQLQueries is the class containing all the methods connecting to the database and doing operations towards it.
 * <br>
 * This class inherits one connection from DBConnector and keeps the connection open for the methods to use
 * <br>
 * Almost all methods inserting data into the database, and wherever the objects have an id, the method sets the id from the generated keys in the database
 */


public class SqlQueries extends DBConnector {

    // TODO: 26.04.2016 add mealtypes in gui
    // TODO: 26.04.2016 delete orderswrong
    // TODO: 26.04.2016 User manual for changing database
    // TODO: 26.04.2016 timetable report
    // TODO: 25.04.2016 presentation
    // TODO: 25.04.2016 fix getEmployees to work faster
    // TODO: 24.04.2016 JavaDoc
    // TODO: 23.04.2016 Log out, (statistikk), sette alt inne i samme vindu, styling, validate felt
    // TODO: 22.04.2016 In Delete methods that include fex adress remember to delete the address as well
    // TODO: 19.04.2016 Refine methods to give confirmations on both execute() and executeUpdate()


    public final int CREATED = 1;
    public final int INPREPARATION = 2;
    public final int READYFORDELIVERY = 3;
    public final int UNDERDELIVERY = 4;
    public final int DELIVERED = 5;


    /*
    Class where all methods that sql is required, shall be written.
     For example every method that requires getting data from database.
     */
    PreparedStatement selectQuery;

    PreparedStatement insertQuery;
    PreparedStatement updateQuery;

    public SqlQueries() {

    }

    /**
     * Adds a new address to the database and sets addressId to primary key.
     *
     * @param newAddress the <code>Address</code> to be added
     * @return false if any <code>SQL Exceptions </code>occurs
     */
    public boolean addAddress(Address newAddress) {
        try {
            String sql = "INSERT INTO address(address, zipcode, place) VALUES(?, ?, ?);";
            insertQuery = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setString(1, newAddress.getAddress());
            insertQuery.setInt(2, newAddress.getZipCode());
            insertQuery.setString(3, newAddress.getPlace());
            insertQuery.execute();
            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
            newAddress.setAddressId(res.getInt(1));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Fetches an Address from the database, matching addressId with
     * Address table.
     *
     * @param addressId
     * @return Address
     */
    public Address getAddress(int addressId) {
        try {
            String selectSql = "SELECT address, zipcode, place FROM address WHERE address_id = ?";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, addressId);
            ResultSet res = selectQuery.executeQuery();
            if (!res.next()) return null;
            String address = res.getString(1);
            int zipCode = res.getInt(2);
            String place = res.getString(3);
            return new Address(addressId, address, zipCode, place);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an address in the database, matching the address id
     * Returns true if address table in db is updated
     * Returns false if address is null or any <code>SQLException</code> occurs.*
     *
     * @param address
     * @return boolean
     */
    public boolean updateAddress(Address address) {
        try {
            String sql = "UPDATE address SET zipcode = ?, address = ?, place = ? WHERE address_ID = ?";
            PreparedStatement updateQuery = con.prepareStatement(sql);
            updateQuery.setInt(1, address.getZipCode());
            updateQuery.setString(2, address.getAddress());
            updateQuery.setString(3, address.getPlace());
            updateQuery.setInt(4, address.getAddressId());
            if (updateQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAddress(Address address) {
        try {
            String sql = "DELETE FROM address WHERE address_id = ?";
            PreparedStatement deleteQuery = con.prepareStatement(sql);
            deleteQuery.setInt(1, address.getAddressId());
            if (deleteQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Adds a customer to the database while also adding the corresponding address of the object.*
     *
     * @param theCustomer
     * @return false if address is null or an SQLException occurs.
     */
    public boolean addCustomer(Customer theCustomer) {
        ResultSet res = null;
        try {
            con.setAutoCommit(false);
            if (!addAddress(theCustomer.getAddress())) {
                return false;
            }
            String sqlSetning = "INSERT INTO customer(address_id, business_name, first_name, last_name, phone, email, isbusiness) VALUES(?,?,?,?,?,?,?)";

            insertQuery = con.prepareStatement(sqlSetning, Statement.RETURN_GENERATED_KEYS);
            int isBusiness = 0;
            if (theCustomer.getIsBusiness()) {
                isBusiness = 1;
                insertQuery.setString(2, theCustomer.getBusinessName());
            } else {
                insertQuery.setNull(2, Types.INTEGER);
            }
            insertQuery.setInt(1, theCustomer.getAddress().getAddressId());
            insertQuery.setString(3, theCustomer.getFirstName());
            insertQuery.setString(4, theCustomer.getLastName());
            insertQuery.setInt(5, theCustomer.getPhoneNumber());
            insertQuery.setString(6, theCustomer.getEmail());
            insertQuery.setInt(7, isBusiness); // 0 is not business, 1 is!
            insertQuery.execute();

            res = insertQuery.getGeneratedKeys();
            res.next();
            theCustomer.setCustomerId(res.getInt(1));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return false;
    }

    /**
     * Updates the data of a customer in the db
     *
     * @param customer customer to be updated
     * @return true if customer exists in db and no SQLExceptions occurs
     */
    public boolean updateCustomer(Customer customer) {
        try {
            String sql = "UPDATE customer SET address_id = ?, business_name = ?, first_name = ?, " +
                    "last_name = ?, phone = ?, email = ?, isbusiness = ? WHERE c_id = ?";
            int isBusiness = 0;
            if (customer.getIsBusiness()) {
                isBusiness = 1;
            }
            PreparedStatement updateQuery = con.prepareStatement(sql);
            updateQuery.setInt(1, customer.getAddress().getAddressId());
            updateQuery.setString(2, customer.getBusinessName());
            updateQuery.setString(3, customer.getFirstName());
            updateQuery.setString(4, customer.getLastName());
            updateQuery.setInt(5, customer.getPhoneNumber());
            updateQuery.setString(6, customer.getEmail());
            updateQuery.setInt(7, isBusiness);
            updateQuery.setInt(8, customer.getCustomerId());
            if (updateQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, updateQuery, con);
        }
        return false;
    }


    /**
     * Retrieves the orderId's of orders in the db containing a subscriptionId
     *
     * @param subscription the subscription to match subscription_id in the order table in the db
     * @return ArrayList containing the order id's
     */
    public ArrayList<Integer> getOrderIdsBySubscription(Subscription subscription) {
        ArrayList<Integer> orderIds = new ArrayList<Integer>();
        ResultSet res = null;
        try {
            String selectSql = "SELECT order_id FROM `order` WHERE subscription_id = ?";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, subscription.getSubscriptionId());
            res = selectQuery.executeQuery();
            while (res.next()) {
                orderIds.add(res.getInt("order_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return orderIds;
    }

    /**
     * Retrieves the orderId's of orders in the db containing a customer_id but not a subscription
     *
     * @param customer the customer to match the customer_id in the order table in the db
     * @return ArrayList containing the order id's
     */
    public ArrayList<Integer> getOrderIdsByCustomer(Customer customer) {
        ArrayList<Integer> orderIds = new ArrayList<>();
        ResultSet res = null;
        try {
            String selectSql = "SELECT order_id FROM `order` WHERE customer_id = ? AND subscription_id IS NULL ";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, customer.getCustomerId());
            res = selectQuery.executeQuery();
            while (res.next()) {
                orderIds.add(res.getInt("order_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return orderIds;
    }

    /**
     * Fetches all the customers in the db, setting the customer's respective Subscription,
     * setting the all the orders of the customer in the customer's orders list as well as
     * setting the orders of the customer's subscription, in the subscription's respective orders list.
     *
     * @param allOrders an ObservableList containing all the Orders in the db, the list returned by method getOrders
     *                  is to be used as parameter
     * @return ObservableList containing all the customers, if any errors occur , the list will be empty
     */
    public ObservableList<Customer> getAllCustomers(ObservableList<Order> allOrders) {

        ObservableList<Subscription> allSubscriptions = getAllSubscriptions();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        ResultSet res = null;
        try {
            String selectSql = "SELECT * FROM customer";
            selectQuery = con.prepareStatement(selectSql);
            res = selectQuery.executeQuery();
            while (res.next()) {
                boolean isBusiness = false;
                if (res.getInt("isBusiness") == 1) {
                    isBusiness = true;
                }
                Customer existingCustomer = new Customer(res.getInt("c_id"), isBusiness, res.getString("email"), res.getString("first_name"),
                        res.getString("last_name"), res.getInt("phone"), res.getString("business_name"));
                existingCustomer.setAddress(getAddress(res.getInt("address_id")));

                ObservableList<Order> allOrdersUnderCustomer = FXCollections.observableArrayList();


                allSubscriptions.stream().filter(subscription -> subscription.getCustomerId() == existingCustomer.getCustomerId()).forEach(subscription -> {
                    existingCustomer.setSubscription(subscription);
                    ObservableList<Order> ordersOnThisSubscription = FXCollections.observableArrayList();
                    ArrayList<Integer> orderIdsForThisSubscription = getOrderIdsBySubscription(subscription);
                    for (Integer orderId :
                            orderIdsForThisSubscription) {
                        ordersOnThisSubscription.addAll(allOrders.stream().filter(order -> order.getOrderId() == orderId).collect(Collectors.toList()));
                    }
                    ordersOnThisSubscription.forEach(allOrdersUnderCustomer::add);
                    subscription.setOrdersOnThisSubscription(ordersOnThisSubscription);
                });

                ArrayList<Integer> orderIdsByCustomer = getOrderIdsByCustomer(existingCustomer);

                for (Integer orderId :
                        orderIdsByCustomer) {
                    allOrdersUnderCustomer.addAll(allOrders.stream().filter(order -> orderId == order.getOrderId()).collect(Collectors.toList()));

                    existingCustomer.setOrders(allOrdersUnderCustomer);
                }
                customers.add(existingCustomer);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return customers;
    }



    /*Dish methods*/

    /**
     * Adds dishes in the db
     *
     * @param theDish to be added
     * @return true if insert went ok
     */
    public boolean addDish(Dish theDish) {
        ResultSet res = null;
        try {
            String sqlSetning = "INSERT INTO dish(price, name) VALUES(?,?)";
            insertQuery = con.prepareStatement(sqlSetning, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setDouble(1, theDish.getPrice());
            insertQuery.setString(2, theDish.getDishName());
            insertQuery.execute();
            res = insertQuery.getGeneratedKeys();
            res.next();
            theDish.setDishId(res.getInt(1));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return false;
    }

    /**
     * Updates a dish in the db
     *
     * @param dish to be updated
     * @return true if update was successful
     */
    public boolean updateDish(Dish dish) {
        try {
            String sql = "UPDATE dish SET price = ?, name = ? WHERE dish_id = ?";
            updateQuery = con.prepareStatement(sql);
            updateQuery.setDouble(1, dish.getPrice());
            updateQuery.setString(2, dish.getDishName());
            updateQuery.setInt(3, dish.getDishId());
            if (updateQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, updateQuery, con);
        }
        return false;
    }

    /**
     * Deletes a Dish from the database
     *
     * @param dish to be deleted from the db
     * @return true if deletion was successful
     */
    public boolean deleteDish(Dish dish) {
        try {
            String sqlSetning = "DELETE FROM dish WHERE dish_id = ?";
            PreparedStatement deleteQuery = con.prepareStatement(sqlSetning);
            deleteQuery.setInt(1, dish.getDishId());
            if (deleteQuery.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

    /**
     * Fetches all the dishes in the database.
     *
     * @param allIngredients in the db, the list returned by method getAllIngredient should be used as parameter.
     * @return ObservableList of all the dishes in the db, if an error occurs the list will be empty or incomplete
     */
    public ObservableList<Dish> getAllDishes(ObservableList<Ingredient> allIngredients) {
        ObservableList<Dish> allDishes = FXCollections.observableArrayList();
        ResultSet res = null;
        try {
            String selectSql = "SELECT * FROM dish";
            selectQuery = con.prepareStatement(selectSql);
            res = selectQuery.executeQuery();
            while (res.next()) {
                int dishId = res.getInt("dish_id");
                double price = res.getDouble("price");
                String name = res.getString("name");

                Dish dish = new Dish(dishId, price, name, null);
                dish.setAllDishLinesForThisDish(getDishLinesByDish(dish, allIngredients));
                allDishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return allDishes;
    }

    /**
     * Adds MenuLines to a menu in the db
     *
     * @param menu     the MenuLines should be added under
     * @param menuLine the MenuLines to be added under the menu
     * @return true if addition to db was successful, performs a rollback if an error occurs and returns false
     */
    public boolean addDishesInMenu(Menu menu, ObservableList<MenuLine> menuLine) {
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
            return true;
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
            e.printStackTrace();
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }


    /**
     * Deletes MenuLines in a Menu in the db
     *
     * @param menu      the MenuLines should be added under
     * @param menuLines to be added to the menu
     * @return true if deletion was succesful, false and performs rollback if any errors occurs
     */
    public boolean deleteDishesInMenu(Menu menu, ObservableList<MenuLine> menuLines) {
        PreparedStatement deleteQuery = null;
        try {
            con.setAutoCommit(false);
            String sqlSetning = "DELETE FROM menu_line WHERE dish_id = ? AND menu_id = ?";
            deleteQuery = con.prepareStatement(sqlSetning);
            for (MenuLine oneLine :
                    menuLines) {
                deleteQuery.setInt(1, oneLine.getDish().getDishId());
                deleteQuery.setInt(2, menu.getMenuId());
                deleteQuery.execute();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
            e.printStackTrace();
        } finally {
            closeEverything(null, deleteQuery, con);
        }
        return false;
    }

    /**
     * Fetches the Dishlines to a Dish from the db
     *
     * @param dish           dish to fetch Dishlines for
     * @param allIngredients all ingredients in the db, the list returned by getAllIngredients is to be used
     * @return an ObservableList containing the DishLines to the dish
     */
    public ObservableList<DishLine> getDishLinesByDish(Dish dish, ObservableList<Ingredient> allIngredients) {
        ResultSet res = null;
        ObservableList<DishLine> dishLines = FXCollections.observableArrayList();
        Map<Integer, Double> collection = new HashMap<>();
        try {
            String selectSql = "SELECT ingredient_id, quantity FROM dish_line WHERE dish_id = " + dish.getDishId();
            selectQuery = con.prepareStatement(selectSql);
            res = selectQuery.executeQuery();
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
        } finally {
            closeEverything(res, selectQuery, con);
        }

        return dishLines;
    }

    /**
     * Adds an employee and its corresponding Address to the db
     *
     * @param newEmployee the employee to be added
     * @return true if addition was successful
     */
    public boolean addEmployee(Employee newEmployee) {
        ResultSet res = null;

        try {
            con.setAutoCommit(false);
            if (!addAddress(newEmployee.getAddress())) {
                SqlCleanup.rullTilbake(con);
                SqlCleanup.settAutoCommit(con);
                return false;
            }
            String insertSql = "INSERT INTO employee (first_name, last_name, phone, email, address_id, username, pos_id, salary, passhash) " +
                    "VALUES(?,?,?,?,?,?,?,?,?)";
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setString(1, newEmployee.getFirstName());
            insertQuery.setString(2, newEmployee.getLastName());
            insertQuery.setInt(3, newEmployee.getPhoneNo());
            insertQuery.setString(4, newEmployee.geteMail());
            insertQuery.setInt(5, newEmployee.getAddress().getAddressId());
            insertQuery.setString(6, newEmployee.getUsername());
            insertQuery.setInt(7, newEmployee.getPosition().getId());
            insertQuery.setDouble(8, newEmployee.getSalary());
            insertQuery.setString(9, newEmployee.getPassHash());
            insertQuery.execute();

            res = insertQuery.getGeneratedKeys();
            res.next();
            newEmployee.setEmployeeId(res.getInt(1));
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return false;
    }

    /**
     * Fetches all employees in the db and sets its respective Address and EmployeePosition.
     *
     * @return an ObservableList containing all the employees
     */
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
        }
        return employees;
    }

    /**
     * Fetches the EmployeePositions in the db
     *
     * @return an ObservableList containing the EmployeePositions
     */
    public ObservableList<EmployeePosition> getEmployeePositions() {
        ResultSet res = null;
        ObservableList<EmployeePosition> employeePositions = FXCollections.observableArrayList();
        try {
            String selectSql = "SELECT pos_id, description, default_salary FROM employee_position";
            Statement s = con.createStatement();
            res = s.executeQuery(selectSql);
            while (res.next()) {
                EmployeePosition employeePosition = new EmployeePosition(res.getInt(1), res.getString(2), res.getDouble(3));
                employeePositions.add(employeePosition);
            }
            return employeePositions;
        } catch (SQLException e) {
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return null;
    }

    /**
     * Fetches an EmployeePosition from an int
     *
     * @param positionId positionId for the corresponding EmployeePosition
     * @return returns the corresponding EmployeePosition if positionId matches in db, returns null otherwise
     */
    public EmployeePosition getEmployeePosition(int positionId) {
        ResultSet res = null;
        try {
            String selectSql = "SELECT description, default_salary FROM employee_position WHERE pos_id = ?";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, positionId);
            res = selectQuery.executeQuery();
            if (!res.next()) return null;

            String description = res.getString(1);
            double salary = res.getDouble(2);
            return new EmployeePosition(positionId, description, salary);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return null;
    }

    /**
     * Fetches an Employee from the db matching the username
     *
     * @param username username of the Employee in the db
     * @return the Employee that matched the username, returns null if username doesnt exist
     */
    public Employee getEmployeeByUsername(String username) {
        ResultSet res = null;
        try {
            String selectSql = "SELECT e.employee_id, e.first_name, e.last_name, e.phone, e.email, e.username, e.salary, e.passhash, e.pos_id, e.address_id" +
                    " FROM employee e WHERE e.username = ?";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setString(1, username);
            res = selectQuery.executeQuery();
            if (res.next()) {
                int employeeId = res.getInt(1);
                String firstName = res.getString(2);
                String lastName = res.getString(3);
                int phoneNo = res.getInt(4);
                String eMail = res.getString(5);
                double salary = res.getDouble(7);
                String passHash = res.getString(8);
                int posId = res.getInt(9);
                int addressId = res.getInt(10);
                Address address = getAddress(addressId);
                EmployeePosition position = getEmployeePosition(posId);
                return new Employee(employeeId, username, firstName, lastName, phoneNo, eMail, salary, passHash, address, position);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return null;
    }

    /**
     * Updates an employee in the db
     *
     * @param employee the Employee to be updated
     * @return true if update was successful, false and performs rollback otherwise
     */
    public boolean updateEmployee(Employee employee) {
        try {
            String updateSql = "UPDATE employee SET first_name = ?, last_name = ?, phone = ?, email = ?," +
                    " username = ?, pos_id = ?, salary = ?, passhash = ? WHERE employee_id = ?;";
            PreparedStatement updateEmpQuery = con.prepareStatement(updateSql);
            con.setAutoCommit(false);
            if (!updateAddress(employee.getAddress())) {
                SqlCleanup.rullTilbake(con);
                SqlCleanup.settAutoCommit(con);
                return false;
            }
            updateEmpQuery.setString(1, employee.getFirstName());
            updateEmpQuery.setString(2, employee.getLastName());
            updateEmpQuery.setInt(3, employee.getPhoneNo());
            updateEmpQuery.setString(4, employee.geteMail());
            updateEmpQuery.setString(5, employee.getUsername());
            updateEmpQuery.setInt(6, employee.getPosition().getId());
            updateEmpQuery.setDouble(7, employee.getSalary());
            updateEmpQuery.setString(8, employee.getPassHash());
            updateEmpQuery.setInt(9, employee.getEmployeeId());
            if (updateEmpQuery.executeUpdate() == 1) {
                con.commit();
                return true;
            } else {
                SqlCleanup.rullTilbake(con);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            SqlCleanup.rullTilbake(con);
        } finally {
            SqlCleanup.settAutoCommit(con);
        }
        return false;
    }

    /**
     * Deletes an Employee from the db
     *
     * @param employee the Employee to be deleted
     * @return true if deletion was successful, false and performs rollback otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        try {
            con.setAutoCommit(false);
            String sql = "DELETE FROM employee WHERE employee_id = ?";
            PreparedStatement deleteQuery = con.prepareStatement(sql);
            deleteQuery.setInt(1, employee.getEmployeeId());
            if (deleteQuery.executeUpdate() == 1) {
                if (deleteAddress(employee.getAddress())) {
                    con.commit();
                    return true;
                } else {
                    SqlCleanup.rullTilbake(con);
                }
            } else {
                SqlCleanup.rullTilbake(con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            SqlCleanup.rullTilbake(con);
        } finally {
            SqlCleanup.settAutoCommit(con);
        }
        return false;
    }

    /**
     * Adds an Ingredient to the db
     *
     * @param ingredient Ingredient to be added
     * @return true if addition was successful, false otherwise
     */
    public boolean addIngredient(Ingredient ingredient) {
        ResultSet res = null;
        try {


            if (ingredient.getSupplier().getSupplierId() == 0) {
                con.setAutoCommit(false);
                if (!addSupplier(ingredient.getSupplier())) {
                    con.setAutoCommit(true);
                    return false;
                }
            }

            String insertSql = "INSERT INTO ingredient(supplier_id, quantity_owned, unit, price, description) VALUES(?,?,?,?,?)";
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setInt(1, ingredient.getSupplier().getSupplierId());
            insertQuery.setDouble(2, ingredient.getQuantityOwned());
            insertQuery.setString(3, ingredient.getUnit());
            insertQuery.setDouble(4, ingredient.getPrice());
            insertQuery.setString(5, ingredient.getIngredientName());
            insertQuery.execute();

            res = insertQuery.getGeneratedKeys();
            res.next();
            ingredient.setIngredientId(res.getInt(1));
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            SqlCleanup.rullTilbake(con);
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return false;
    }

    /**
     * Fetches an Ingredient from the db.
     *
     * @param ingredientId the ingredientId for the corresponding ingredient in the db
     * @return the Ingredient from the db with matching ingredientId, returns null if it does not find a match
     */
    public Ingredient getIngredient(int ingredientId) {
        try {
            String selectSql = "SELECT quantity_owned, unit, price, description FROM ingredient WHERE ingredient_id = ?";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, ingredientId);
            ResultSet res = selectQuery.executeQuery();
            if (!res.next()) return null;
            double quantityOwned = res.getDouble(1);
            String unit = res.getString(2);
            double price = res.getDouble(3);
            String description = res.getString(4);
            return new Ingredient(ingredientId, description, unit, quantityOwned, price);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates Ingredients in the db.
     *
     * @param ingredients an ObservableList containing the Ingredients to be updated
     * @return true if update was succesful, false otherwise
     */
    public boolean updateIngredient(ObservableList<Ingredient> ingredients) {
        try {
            con.setAutoCommit(false);
            for (Ingredient ing : ingredients) {
                String sql = "UPDATE ingredient SET quantity_owned = ?, unit = ?, price = ?, description = ? WHERE ingredient_id = ?";
                updateQuery = con.prepareStatement(sql);
                updateQuery.setDouble(1, ing.getQuantityOwned());
                updateQuery.setString(2, ing.getUnit());
                updateQuery.setDouble(3, ing.getPrice());
                updateQuery.setString(4, ing.getIngredientName());
                updateQuery.setInt(5, ing.getIngredientId());
                updateQuery.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, updateQuery, con);
        }
        return false;
    }

    /**
     * Deletes an Ingredient in the db.
     *
     * @param ingredient the Ingredient to be deleted
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteIngredient(Ingredient ingredient) {
        try {
            String sqlSetning = "DELETE FROM ingredient WHERE ingredient_id = ?";
            PreparedStatement deleteQuery = con.prepareStatement(sqlSetning);
            deleteQuery.setInt(1, ingredient.getIngredientId());
            deleteQuery.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

    /**
     * Adds a DishLine in a Dish
     *
     * @param dish      the Dish, the DishLines should be added under
     * @param dishLines the DishLines to be added
     * @return
     */
    public boolean addIngredientInDish(Dish dish, ObservableList<DishLine> dishLines) {
        try {
            con.setAutoCommit(false);
            for (DishLine oneLine :
                    dishLines) {

                String sqlSetning = "INSERT INTO dish_line(ingredient_id, dish_id, quantity) VALUES(?,?,?)";
                insertQuery = con.prepareStatement(sqlSetning);
                insertQuery.setInt(1, oneLine.getIngredient().getIngredientId());
                insertQuery.setInt(2, dish.getDishId());
                insertQuery.setDouble(3, oneLine.getAmount());
                insertQuery.execute();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            SqlCleanup.rullTilbake(con);
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

    /**
     * Fetches the Ingredients that have the supplier in the parameters
     *
     * @param supplierId supplierId to match in the db
     * @return ObservableList containing the Ingredients that matched supplierId in the db
     */
    public ObservableList<Ingredient> getIngredientsBySupplierId(int supplierId) {
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        try {
            String selectSql = "SELECT ingredient_id, quantity_owned, unit, price, description FROM ingredient WHERE supplier_id = ?;";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, supplierId);
            ResultSet res = selectQuery.executeQuery();
            while (res.next()) {
                int ingredientId = res.getInt(1);
                double quantity = res.getDouble(2);
                String unit = res.getString(3);
                double price = res.getDouble(4);
                String description = res.getString(5);
                Ingredient ingredient = new Ingredient(ingredientId, description, unit, quantity, price);
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    /**
     * Deletes DishLines to a dish in the db
     *
     * @param dish      the dish to delete Ingredients from
     * @param dishLines the DishLines to be deleted
     * @return true if deletion was successful, false and performes rollback otherwise
     */
    public boolean deleteIngredientsInDish(Dish dish, ObservableList<DishLine> dishLines) {
        PreparedStatement deleteQuery = null;
        try {
            con.setAutoCommit(false);
            String sqlSetning = "DELETE FROM dish_line WHERE ingredient_id = ? AND dish_id = ?";
            deleteQuery = con.prepareStatement(sqlSetning);
            for (DishLine oneLine :
                    dishLines) {
                deleteQuery.setInt(1, oneLine.getIngredient().getIngredientId());
                deleteQuery.setInt(2, dish.getDishId());
                deleteQuery.execute();
            }
            con.commit();
            con.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
            e.printStackTrace();
        } finally {
            closeEverything(null, deleteQuery, con);
        }
        return false;
    }


    /**
     * Fetches all the Ingredients from the db
     *
     * @param allSuppliers all Suppliers in the db, the list returned by method getAllSuppliers is to be used her
     * @return an ObservableList containing all the Ingredients
     */
    public ObservableList<Ingredient> getAllIngredients(ObservableList<Supplier> allSuppliers) {
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        ResultSet res = null;
        try {
            String selectSql = "SELECT * FROM ingredient";
            selectQuery = con.prepareStatement(selectSql);
            res = selectQuery.executeQuery();
            while (res.next()) {
                int ingredientId = res.getInt("ingredient_id");
                int supplierId = res.getInt("supplier_id");
                Double quantityOwned = res.getDouble("quantity_owned");
                String unit = res.getString("unit");
                Double price = res.getDouble("price");
                String name = res.getString("description");


                Supplier temp = null;
                for (Supplier supplier :
                        allSuppliers) {
                    if (supplier.getSupplierId() == supplierId) {
                        temp = supplier;
                        break;
                    }
                }
//                allSuppliers.filtered(supplier -> supplier.getSupplierId() == supplierId).forEach(supplier1 -> tempSupplier.add(supplier1));

                try {
                    Ingredient tempIngredient = new Ingredient(ingredientId, name, unit, quantityOwned, price, temp);
                    ingredients.add(tempIngredient);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("method getAllIngredients failed, when trying to get index of temporary arraylist");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return ingredients;
    }

    /**
     * Adds a Menu to the db.
     *
     * @param menu the Menu to be added
     * @return true if addition was successful, false otherwise
     */
    public boolean addMenu(Menu menu) {
        ResultSet res = null;
        try {

            String insertSql = "INSERT INTO menu(description, meal_type_id) VALUES(?,?)";
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setString(1, menu.getName());
            insertQuery.setInt(2, menu.getMealType().getMealTypeId());
            insertQuery.execute();

            res = insertQuery.getGeneratedKeys();
            res.next();
            menu.setMenuId(res.getInt(1));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
            SqlCleanup.settAutoCommit(con);
        }
        return false;
    }

    /**
     * Updates a Menu in the db
     *
     * @param menu the Menu to be updated
     * @return true if update was successful, false otherwise
     */
    public boolean updateMenu(Menu menu) {
        try {
            String sql = "UPDATE menu SET meal_type_id = ?, description = ?" +
                    " WHERE menu_id = " + menu.getMenuId();
            updateQuery = con.prepareStatement(sql);
            updateQuery.setInt(1, menu.getMealType().getMealTypeId());
            updateQuery.setString(2, menu.getName());

            if (updateQuery.executeUpdate() == 1) return true;
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
            e.printStackTrace();
        } finally {
            closeEverything(null, updateQuery, con);
        }

        return false;
    }

    /**
     * Deletes a Menu from the db.
     *
     * @param menu the Menu to be deleted
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteMenu(Menu menu) {
        try {
            String sqlSetning = "DELETE FROM menu WHERE menu_id = ?";
            PreparedStatement deleteQuery = con.prepareStatement(sqlSetning);
            deleteQuery.setInt(1, menu.getMenuId());
            if (deleteQuery.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

    /**
     * Fetches all Menus from the db
     *
     * @param allDishes an ObservableList containing all the dishes in the db, the list returned by method getAllDishes is to be used here.
     * @return an ObservableList containing all the menus
     */
    public ObservableList<Menu> getAllMenus(ObservableList<Dish> allDishes) {
        ObservableList<Menu> menus = FXCollections.observableArrayList();
        ResultSet res = null;
        try {
            String selectSql = "SELECT * FROM menu";
            selectQuery = con.prepareStatement(selectSql);
            res = selectQuery.executeQuery();
            while (res.next()) {
                int menuId = res.getInt("menu_id");
                String description = res.getString("description");
                int meal_type_id = res.getInt("meal_type_id");
                Menu existingMenu = new Menu(menuId, description, getMealType(meal_type_id), null);
                existingMenu.setMenuLines(getMenuLinesByMenu(existingMenu, allDishes));
                menus.add(existingMenu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return menus;
    }

    /**
     * Adds a MealType to the db.
     *
     * @param mealType the MealType to be added
     * @return true if addition was successful, false otherwise
     */
    public boolean addMealType(MealType mealType) {
        ResultSet res = null;
        try {
            String insertSql = "INSERT INTO meal_type(meal_type_name) VALUES(?)";
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setString(1, mealType.getName());
            insertQuery.executeUpdate();

            res = insertQuery.getGeneratedKeys();
            res.next();
            mealType.setMealTypeId(res.getInt(1));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return false;
    }

    /**
     * Fetches a MealType matching the id in the db
     *
     * @param id the id to match
     * @return the matching MealType, null if it finds no match
     */
    public MealType getMealType(int id) {
        ResultSet res = null;
        try {
            String selectSql = "SELECT meal_type_id, meal_type_name FROM meal_type WHERE meal_type_id = " + id;
            selectQuery = con.prepareStatement(selectSql);
            res = selectQuery.executeQuery();
            if (!res.next()) return null;
            int mealTypeId = res.getInt(1);
            String name = res.getString(2);
            return new MealType(mealTypeId, name);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return null;
    }

    /**
     * Fetches all the MealTypes from the db
     *
     * @return an ObservableList containg all the MealTypes
     */
    public ObservableList<MealType> getAllMealTypes() {
        ResultSet res = null;
        ObservableList<MealType> mealTypes = FXCollections.observableArrayList();
        try {
            String selectsql = "SELECT * FROM meal_type";
            selectQuery = con.prepareStatement(selectsql);
            res = selectQuery.executeQuery();
            while (res.next()) {
                mealTypes.add(new MealType(res.getInt("meal_type_id"), res.getString("meal_type_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return mealTypes;
    }

    /**
     * Adds MenuLines to the db.
     *
     * @param menu      the Menu the MenuLines gets added to
     * @param menuLines the MenuLines to add
     * @return
     */
    public boolean addMenuLine(Menu menu, ObservableList<MenuLine> menuLines) {
        try {
            String insertSql = "INSERT INTO menu_line(dish_id, menu_id, quantity, price_factor) VALUES(?,?,?,?)";
            insertQuery = con.prepareStatement(insertSql);
            for (MenuLine menuLine :
                    menuLines) {
                insertQuery.setInt(1, menuLine.getDish().getDishId());
                insertQuery.setInt(2, menu.getMenuId());
                insertQuery.setDouble(3, menuLine.getAmount());
                insertQuery.setDouble(4, menuLine.getPriceFactor());
                insertQuery.execute();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

    /**
     * Fetches the MenuLines to a Menu in the db
     *
     * @param menu      the Menu to get MenuLines from
     * @param allDishes all dishes in the db, the list returned by getAllDishes is to be used here
     * @return
     */
    public ObservableList<MenuLine> getMenuLinesByMenu(Menu menu, ObservableList<Dish> allDishes) {
        ObservableList<MenuLine> menuLines = FXCollections.observableArrayList();
        ResultSet res = null;
        try {
            String selectSql = "SELECT * FROM menu_line WHERE menu_id = ?";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, menu.getMenuId());
            res = selectQuery.executeQuery();
            while (res.next()) {
                int dishId = res.getInt("dish_id");
                int amount = res.getInt("quantity");
                double priceFactor = res.getDouble("price_factor");
                menuLines.addAll(allDishes.stream().filter(dish -> dish.getDishId() == dishId).map(dish -> new MenuLine(dish, amount, priceFactor)).collect(Collectors.toList()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return menuLines;
    }

    /**
     * Deletes MenuLines to a Menu in the db
     *
     * @param menu      the Menu to delete from
     * @param menuLines the MenuLines to be deleted
     * @return true, if deletion was successful, false otherwise
     */
    public boolean deleteMenuLines(Menu menu, ObservableList<MenuLine> menuLines) {
        try {
            con.setAutoCommit(false);
            String sqlSetning = "DELETE FROM menu_line WHERE menu_id = " + menu.getMenuId() + " AND dish_id = ?";
            PreparedStatement deleteQuery = con.prepareStatement(sqlSetning);
            for (MenuLine menuline :
                    menuLines) {
                deleteQuery.setInt(1, menuline.getDish().getDishId());
                deleteQuery.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
            e.printStackTrace();
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

    /**
     * Updates the MenuLines to a Menu in the db.
     *
     * @param menu      the Menu to update for
     * @param menuLines the MenuLines to be updated
     * @return true if update was successful, false otherwise
     */
    public boolean updateMenuLine(Menu menu, ObservableList<MenuLine> menuLines) {
        try {
            con.setAutoCommit(false);
            String sql = "UPDATE menu_line SET quantity = ? WHERE menu_id = " + menu.getMenuId() + " AND dish_id =?";
            updateQuery = con.prepareStatement(sql);
            for (MenuLine menuLine :
                    menuLines) {
                updateQuery.setInt(1, menuLine.getAmount());
                updateQuery.setInt(2, menuLine.getDish().getDishId());
                updateQuery.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
            e.printStackTrace();
        } finally {
            closeEverything(null, updateQuery, con);
        }
        return false;
    }

    /**
     * Fetches all the Orders based on an EmployeePosition
     *
     * @param posId     the positionId for the EmployeePosition
     * @param allDishes all the Dishes in the db, the list returned by method getAllDishes is to be used here
     * @return an ObservableList containing the orders
     */
    public ObservableList<Order> getOrders(int posId, ObservableList<Dish> allDishes) {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        ResultSet res = null;
        try {
            String selectSql = "";
            //ceo and sales
            if (posId == 1) {
                selectSql = "SELECT * FROM `order`";
                selectQuery = con.prepareStatement(selectSql);
                //CHEF
            } else if (posId == 2) {
                selectSql = "SELECT * FROM `order` WHERE status_id = ? OR ? OR ?";
                selectQuery = con.prepareStatement(selectSql);
                selectQuery.setInt(1, CREATED);
                selectQuery.setInt(2, INPREPARATION);
                selectQuery.setInt(3, READYFORDELIVERY);
                //DRIVER
            } else if (posId == 3) {

                selectSql = "SELECT * FROM `order` WHERE (status_id = ? OR status_id = ? OR `order`.status_id = ?) AND (DATE(delivery_date) = CURDATE() AND DATE(delivered_date) = curdate())";
                selectQuery = con.prepareStatement(selectSql);
                selectQuery.setInt(1, READYFORDELIVERY);
                selectQuery.setInt(2, DELIVERED);
                selectQuery.setInt(3, UNDERDELIVERY);
            } else if (posId == 4) {
                selectSql = "SELECT * FROM `order` WHERE status_id = ? OR status_id = ? OR status_id = ? OR status_id = ?";
                selectQuery = con.prepareStatement(selectSql);
                selectQuery.setInt(1, CREATED);
                selectQuery.setInt(2, INPREPARATION);
                selectQuery.setInt(3, READYFORDELIVERY);
                selectQuery.setInt(4, DELIVERED);
                //SALES
            }
            res = selectQuery.executeQuery();
            while (res.next()) {
                int orderId = res.getInt("order_id");
                String customerRequests = res.getString("customer_requests");
                LocalDateTime deadline = res.getTimestamp("delivery_date").toLocalDateTime();
                double price = res.getDouble("price");
                OrderStatus status = getOrderStatus(res.getInt("status_id"));
                Address address = getAddress(res.getInt("address_id"));
                Order order;
                if (res.getTimestamp("delivered_date") != null) {
                    LocalDateTime actualDeliveryDate = res.getTimestamp("delivered_date").toLocalDateTime();

                    order = new Order(orderId, customerRequests, deadline, actualDeliveryDate, price, status, null, address);
                } else {
                    order = new Order(orderId, customerRequests, deadline, null, price, status, null, address);
                }
                setOrderLinesInOrder(order, allDishes);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return orders;
    }

    /**
     * Adds an Order to the db
     *
     * @param subscription the Subscription the Order should be put under, if it does not belong under a Supscription, set as null
     * @param order        the Order to be added
     * @param customer     the Customer the Order should be added under
     * @return true if addition was successful, false otherwise
     */
    public boolean addOrder(Subscription subscription, Order order, Customer customer) {
        PreparedStatement updateQuery = null;
        ResultSet res = null;
        try {
            if (order.getAddress().getAddressId() == 0) {
                if (!addAddress(order.getAddress())) {
                    SqlCleanup.rullTilbake(con);
                    return false;
                }
            }
            String insertSql = "INSERT INTO `order`(customer_id, subscription_id, customer_requests, delivery_date, delivered_date, " +
                    "price, address_id, status_id) VALUES(?,?,?,?,?,?,?,?)";
            updateQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            if (!addAddress(order.getAddress())) {
                return false;
            }

            if (customer == null) {
                updateQuery.setNull(1, Types.INTEGER);
            } else {
                updateQuery.setInt(1, customer.getCustomerId());
            }
            if (subscription == null) {
                updateQuery.setNull(2, Types.INTEGER);
            } else {
                updateQuery.setInt(2, subscription.getSubscriptionId());
            }
            updateQuery.setString(3, order.getCustomerRequests());
            System.out.println(order.getDeadlineTime().toString());
            updateQuery.setTimestamp(4, Timestamp.valueOf(order.getDeadlineTime()));
            if (order.getActualDeliveryDateTime() == null) {
                updateQuery.setNull(5, Types.DATE);
            } else {
                updateQuery.setTimestamp(5, Timestamp.valueOf(order.getActualDeliveryDateTime()));
            }
            updateQuery.setDouble(6, order.getPrice());
            updateQuery.setInt(7, order.getAddress().getAddressId());
            updateQuery.setInt(8, order.getStatus().getStatusId());
            if (subscription != null) {
                updateQuery.setInt(2, subscription.getSubscriptionId());
            } else {
                updateQuery.setNull(2, Types.INTEGER);
            }
            if (updateQuery.executeUpdate() == 1) {
                res = updateQuery.getGeneratedKeys();
                res.next();
                order.setOrderId(res.getInt(1));
                return true;
            } else {
                SqlCleanup.rullTilbake(con);
                return false;
            }
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
            e.printStackTrace();
        } finally {
            closeEverything(res, updateQuery, null);
        }
        return false;
    }

    /**
     * Same as addOrder but with multiple Orders in a List
     *
     * @param subscription the Subscription the Order should be put under, if it does not belong under a Supscription, set as null
     * @param orders       the Orders to be added
     * @param customer     the Customer the Order should be added under
     * @return true if addition was successful, false otherwise
     */
    public boolean addOrders(Subscription subscription, ObservableList<Order> orders, Customer customer) {
        try {
            con.setAutoCommit(false);
            for (Order order :
                    orders) {
                addOrder(subscription, order, customer);
            }
            return true;
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

    /**
     * Updates an Order in the db.
     *
     * @param order the Order to be updated
     * @return true if update was successful, false otherwise
     */
    public boolean updateOrder(Order order) {
        try {
            con.setAutoCommit(false);
            String sql = "UPDATE `order` SET customer_requests = ?, delivery_date = ?, " +
                    "delivered_date = ?, price = ?, address_id = ?, status_id  = ? WHERE order_id = ?";
            PreparedStatement updateQuery = con.prepareStatement(sql);
            if (!updateAddress(order.getAddress())){
                System.out.println("address fail");
                return false;
            }
            updateQuery.setString(1, order.getCustomerRequests());
            updateQuery.setTimestamp(2, Timestamp.valueOf(order.getDeadlineTime()));
            if (order.getActualDeliveryDateTime() == null) {
                updateQuery.setNull(3, Types.DATE);
            } else {
                updateQuery.setTimestamp(3, Timestamp.valueOf(order.getActualDeliveryDateTime()));
            }
            updateQuery.setDouble(4, order.getPrice());
            updateQuery.setInt(5, order.getAddress().getAddressId());
            updateQuery.setInt(6, order.getStatus().getStatusId());
            updateQuery.setInt(7, order.getOrderId());

            if (updateQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, updateQuery, con);
        }
        return false;
    }

    /**
     * Adds OrderLines in an Order
     *
     * @param order      the Order to add under
     * @param orderLines the OrderLines to be added
     * @return true if addition was successful, false and performs rollback otherwise
     */
    public boolean addOrderLines(Order order, ObservableList<OrderLine> orderLines) {
        try {
            con.setAutoCommit(false);
            String sqlSetning = "INSERT INTO order_line (dish_id, quantity, order_id) VALUES (?,?, " + order.getOrderId() + ")";
            insertQuery = con.prepareStatement(sqlSetning);
            for (OrderLine orderline :
                    orderLines) {

                insertQuery.setInt(1, orderline.getDish().getDishId());
                insertQuery.setInt(2, orderline.getAmount());
                insertQuery.execute();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
            e.printStackTrace();
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

    /**
     * Fetches and sets the OrderLines in an Order
     *
     * @param order     the order to be processed
     * @param allDishes all the dishes in the db, the list returned by method getAllDishes is to be used here
     * @return
     */
    public boolean setOrderLinesInOrder(Order order, ObservableList<Dish> allDishes) {
        ResultSet res = null;
        ObservableList<OrderLine> dishLinesInThisOrder = FXCollections.observableArrayList();
        try {
            String selectSql = "SELECT dish_id, quantity FROM order_line WHERE order_id = ?";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, order.getOrderId());
            res = selectQuery.executeQuery();
            while (res.next()) {
                int dishId = res.getInt(1);
                int quantity = res.getInt(2);
                for (Dish dish :
                        allDishes) {
                    if (dish.getDishId() == dishId) {
                        dishLinesInThisOrder.add(new OrderLine(dish, quantity));
                        // break;
                    }
                }
            }
            order.setDishesInThisOrder(dishLinesInThisOrder);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return false;
    }

    /**
     * Deletes OrderLines in an Order
     *
     * @param order      the order to be deleted from
     * @param orderLines the OrderLines to be deleted
     * @return true if deletion was successful, false and performs rollback otherwise
     */
    public boolean deleteOrderLine(Order order, ObservableList<OrderLine> orderLines) {
        try {
            con.setAutoCommit(false);
            String sqlSetning = "DELETE FROM order_line WHERE order_id = ? AND dish_id = ?";
            PreparedStatement deleteQuery = con.prepareStatement(sqlSetning);
            for (OrderLine orderline :
                    orderLines) {
                deleteQuery.setInt(1, order.getOrderId());
                deleteQuery.setInt(2, orderline.getDish().getDishId());
                System.out.println("Removing dish: " + orderline.getDish().getDishName());
                deleteQuery.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
            e.printStackTrace();
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

    /**
     * Fetches an OrderStatus from the db.
     *
     * @param id the id matching the OrderStatus in the db
     * @return the OrderStatues if found, null otherwise
     */
    public OrderStatus getOrderStatus(int id) {
        try {
            String selectSql = "SELECT status_id, name FROM orderstatus WHERE status_id = ?";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, id);
            ResultSet res = selectQuery.executeQuery();
            if (!res.next()) return null;
            int statusId = res.getInt(1);
            String name = res.getString(2);
            return new OrderStatus(statusId, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes an Order in the db.
     *
     * @param order the Order to be deleted
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteOrder(Order order) {
        try {
            String sqlSetning = "DELETE FROM `order` WHERE order_id = ?";
            PreparedStatement deleteQuery = con.prepareStatement(sqlSetning);
            deleteQuery.setInt(1, order.getOrderId());
            if (deleteQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

    /**
     * Adds a Subscription in the db and also adds the Orders under the Subscription
     *
     * @param subscription The Subscription to be added
     * @param customer     the Customer that has the Subscription
     * @param orders       the Orders to be added
     * @return true if addition was successful, false and perform rollback otherwise
     */
    public boolean addSubscription(Subscription subscription, Customer customer, ObservableList<Order> orders) {
        ResultSet res = null;
        try {
            con.setAutoCommit(false);
            String insertSql = "INSERT INTO subscription(start_date, end_date, customer_id) VALUES(?,?,?)";
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setDate(1, java.sql.Date.valueOf(subscription.getStartSubscription()));
            insertQuery.setDate(2, java.sql.Date.valueOf(subscription.getEndSubscription()));
            insertQuery.setInt(3, customer.getCustomerId());
            insertQuery.execute();

            res = insertQuery.getGeneratedKeys();
            res.next();
            subscription.setSubscriptionId(res.getInt(1));
            if (orders != null) {
                for (Order order :
                        orders) {
                    addOrder(subscription, order, customer);
                }
            }
            return true;
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
            e.printStackTrace();
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return false;
    }

    /**
     * Fetches all the Subscriptions from the db.
     *
     * @return list of all the Subscriptions
     */
    public ObservableList<Subscription> getAllSubscriptions() {
        ObservableList<Subscription> subscriptions = FXCollections.observableArrayList();
        ResultSet res = null;
        try {
            String selectSql = "SELECT * FROM subscription";
            selectQuery = con.prepareStatement(selectSql);
            res = selectQuery.executeQuery();
            while (res.next()) {
                int subscription_id = res.getInt("subscription_id");
                LocalDate startDate = res.getDate("start_date").toLocalDate();
                LocalDate endDate = res.getDate("end_date").toLocalDate();
                int customerId = res.getInt("customer_id");
                subscriptions.add(new Subscription(subscription_id, startDate, endDate, null, customerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getAllSubscriptions failed");
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return subscriptions;
    }


    public boolean updateSubscription(Subscription subscription) {
        try {
            String sql = "UPDATE subscription SET customer_id = ?, start_date = ?, " +
                    "end_date = ? WHERE subscription_id = ?";
            updateQuery = con.prepareStatement(sql);
            updateQuery.setInt(1, subscription.getCustomerId());
            updateQuery.setDate(2, Date.valueOf(subscription.getStartSubscription()));
            updateQuery.setDate(3, Date.valueOf(subscription.getEndSubscription()));
            updateQuery.setInt(4, subscription.getSubscriptionId());
            int rowsAffected = updateQuery.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method updateSubscription failed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("method updateSubscription failed, not SQL exeption");
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

    /**
     * Adds a Supplier to the db.
     *
     * @param supplier the Supplier to be added
     * @return true if addition was successful, false otherwise
     */
    public boolean addSupplier(Supplier supplier) {
        ResultSet res = null;
        try {
            con.setAutoCommit(false);
            if (!addAddress(supplier.getThisAddress())) {
                return false;
            }
            String insertSql = "INSERT INTO supplier(address_id, business_name, phone) VALUES(?,?,?)";
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setInt(1, supplier.getThisAddress().getAddressId());
            insertQuery.setString(2, supplier.getBusinessName());
            insertQuery.setInt(3, supplier.getPhoneNumber());
            insertQuery.execute();

            res = insertQuery.getGeneratedKeys();
            res.next();
            supplier.setSupplierId(res.getInt(1));
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            System.out.println("Restraint violation in addSupplier");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong: user registration failed in method addSupplier");
            SqlCleanup.rullTilbake(con);
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return false;
    }


    public boolean deleteSupplier(Supplier supplier) {
        try {
            String sqlSetning = "DELETE FROM supplier WHERE supplier_id = " + supplier.getSupplierId();
            PreparedStatement deleteQuery = con.prepareStatement(sqlSetning);
            if (deleteQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

    /**
     * Fetches all the Suppliers from the db.
     *
     * @return a List containing all the Suppliers
     */
    public ObservableList<Supplier> getAllSuppliers() {
        ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        ResultSet res = null;
        try {
            String selectSql = "SELECT * FROM supplier";
            selectQuery = con.prepareStatement(selectSql);
            res = selectQuery.executeQuery();
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
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return suppliers;
    }

    /**
     * Updates a Supplier in the db.
     *
     * @param supplier the Supplier
     * @return true if update was successful, false otherwise
     */
    public boolean updateSupplier(Supplier supplier) {
        try {
            String sql = "UPDATE supplier SET address_id = ?, business_name = ?, " +
                    "phone = ? WHERE supplier_id = ?";
            updateQuery = con.prepareStatement(sql);
            updateQuery.setInt(1, supplier.getThisAddress().getAddressId());
            updateQuery.setString(2, supplier.getBusinessName());
            updateQuery.setInt(3, supplier.getPhoneNumber());
            updateQuery.setInt(4, supplier.getSupplierId());
            if (updateQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, updateQuery, con);
        }
        return false;
    }

    /**
     * Fetches the all the OrderStatuses from the db
     *
     * @return all the OrderStatuses
     */
    public ObservableList<OrderStatus> getStatusTypes() {
        ObservableList<OrderStatus> statusTypes = FXCollections.observableArrayList();
        try {
            String selectSql = "SELECT status_id, name FROM orderstatus;";
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();

            while (res.next()) {
                int statusId = res.getInt(1);
                String name = res.getString(2);
                statusTypes.add(new OrderStatus(statusId, name));
            }
            return statusTypes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fetches a ZipCode from the db
     *
     * @param zipcode to match in the db
     * @return the ZipCode matching
     */
    public ZipCode getZipcodeByZipInt(int zipcode) {
        ResultSet res = null;
        try {
            String selectSql = "SELECT * FROM zipcode WHERE zipcode = ?";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, zipcode);
            res = selectQuery.executeQuery();
            if (!res.next()) {
                return null;
            } else {
                return new ZipCode(res.getInt(2), res.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return null;
    }

    /**
     * Helper method to close things properly after a method has been called
     *
     * @param res   the ResultSet to be closed, can be null
     * @param query the Prepared Statement to be closed
     * @param con   the connection to set to autocommit(true)
     */
    public void closeEverything(ResultSet res, PreparedStatement query, Connection con) {
        if (!(res == null)) {
            SqlCleanup.lukkResSet(res);
        }
        if (!(query == null)) {
            SqlCleanup.lukkSetning(query);
        }
        if (!(con == null)) {
            SqlCleanup.settAutoCommit(con);
        }
    }

// purchase methods

    /*rogers recent methods 25.04.16*/
    public ObservableList<POrder> getPOrders(Boolean status) {
        ObservableList<POrder> pOrders = FXCollections.observableArrayList();
        try {
            String filter = "";
            if (status != null) {
                filter += "WHERE received = ";
                filter += (status) ? "1" : "0";
            }
            String selectPOrders = "SELECT porder_id, supplier_id, placed_date, received FROM porder " + filter + " ORDER BY placed_date;";
            String selectPOrderLines = "SELECT ingredient_id, quantity FROM porder_line WHERE porder_id = ?;";
            PreparedStatement selectPOrdersStatement = con.prepareStatement(selectPOrders);
            PreparedStatement selectPOrderLinesStatement = con.prepareStatement(selectPOrderLines);
            ResultSet res = selectPOrdersStatement.executeQuery();
            while (res.next()) {
                int pOrderId = res.getInt(1);
                Supplier supplier = getSupplier(res.getInt(2));
                selectPOrderLinesStatement.setInt(1, pOrderId);
                ResultSet res2 = selectPOrderLinesStatement.executeQuery();
                ObservableList<POrderLine> pOrderLines = FXCollections.observableArrayList();
                while (res2.next()) {
                    pOrderLines.add(new POrderLine(pOrderId, getIngredient(res2.getInt(1)), res2.getDouble(2)));
                }
                LocalDate placedDate = res.getDate(3).toLocalDate();
                boolean received = res.getBoolean(4);
                POrder pOrder = new POrder(pOrderId, supplier, placedDate, pOrderLines, received);
                pOrders.add(pOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pOrders;
    }

    public boolean updatePOrder(POrder pOrder) {
        try {
            String updateSql = "UPDATE porder SET received = ? WHERE porder_id = ?;";
            PreparedStatement updateQuery = con.prepareStatement(updateSql);
            updateQuery.setBoolean(1, pOrder.isReceived());
            updateQuery.setInt(2, pOrder.getpOrderId());
            if (updateQuery.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, updateQuery, con);
        }
        return false;
    }

    public boolean addPOrder(POrder pOrder) {
        boolean success = false;
        try {
            con.setAutoCommit(false);
            String insertSql = "INSERT INTO porder(supplier_id, placed_date, received) VALUES(?, NOW(), 0);";
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setInt(1, pOrder.getSupplier().getSupplierId());
            if (insertQuery.executeUpdate() == 1) {
                ResultSet res = insertQuery.getGeneratedKeys();
                res.next();
                pOrder.setpOrderId(res.getInt(1));
                insertSql = "INSERT INTO porder_line(porder_id, ingredient_id, quantity) VALUES(?, ?, ?);";
                insertQuery = con.prepareStatement(insertSql);
                insertQuery.setInt(1, pOrder.getpOrderId());
                for (POrderLine pOrderLine : pOrder.getpOrderLines()) {
                    insertQuery.setInt(2, pOrderLine.getIngredient().getIngredientId());
                    insertQuery.setDouble(3, pOrderLine.getQuantity());
                    if (insertQuery.executeUpdate() != 1) {
                        SqlCleanup.rullTilbake(con);
                        return success;
                    }
                }
                success = true;
            } else {
                SqlCleanup.rullTilbake(con);
                return success;
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong: addPOrder failed. " + e);
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return success;
    }

    public Supplier getSupplier(int supplierId) {
        try {
            String selectSql = "SELECT business_name, phone, address_id FROM supplier WHERE supplier_id = ?;";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, supplierId);
            ResultSet res = selectQuery.executeQuery();
            if (!res.next()) return null;
            String businessName = res.getString(1);
            int phone = res.getInt(2);
            Address address = getAddress(res.getInt(3));
            return new Supplier(supplierId, phone, address, businessName);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, selectQuery, con);
        }
        return null;
    }
}
