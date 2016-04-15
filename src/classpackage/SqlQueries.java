package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by paul thomas on 17.03.2016.
 */


/*
* Sales:
* Address to customer, order
* Dish to OrderLine
* OrderLine to Order
* Customer to Order
* Order to subscription
* Subscription to Customer
* 
* */

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
            String sql = "INSERT INTO address(address, zipcode, place) VALUES(?, ?, ?);";
            insertQuery = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setString(1, newAddress.getAddress());
            insertQuery.setInt(2, newAddress.getZipCode());
            insertQuery.setString(3, newAddress.getPlace());
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
            String selectSql = "SELECT address, zipcode, place FROM address WHERE address_id = " + addressId;
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            if (!res.next()) return null;
            String address = res.getString(1);
            int zipCode = res.getInt(2);
            String place = res.getString(3);
            return new Address(addressId, address, zipCode, place);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getAddress failed");
        }
        return null;
    }

    public boolean updateAddress(Address address) {
        boolean success = false;
        try {
            String sql = "UPDATE address SET zipcode = ?, address = ?, place = ? WHERE address_ID = ?;";
            updateQuery = con.prepareStatement(sql);
            updateQuery.setInt(1, address.getZipCode());
            updateQuery.setString(2, address.getAddress());
            updateQuery.setString(3, address.getPlace());
            updateQuery.setInt(4, address.getAddressId());
            updateQuery.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method updateAddress failed");
            success = false;
        }
        return success;
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
            if (theCustomer.getIsBusiness()) {
                isBusiness = 1;
            }

            insertQuery = con.prepareStatement(sqlSetning, Statement.RETURN_GENERATED_KEYS);
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


    public boolean updateCustomer(Customer customer) {
        boolean success = false;
        try {
            String sql = "UPDATE customer SET address_id = ?, business_name = ?, first_name = ?, " +
                    "last_name = ?, phone = ?, email = ?, isbusiness = ? WHERE c_id = ?";
            int isBusiness = 0;
            if (customer.getIsBusiness()) {
                isBusiness = 1;
            }
            updateQuery = con.prepareStatement(sql);
            updateQuery.setInt(1, customer.getAddress().getAddressId());
            updateQuery.setString(2, customer.getBusinessName());
            updateQuery.setString(3, customer.getFirstName());
            updateQuery.setString(4, customer.getLastName());
            updateQuery.setInt(5, customer.getPhoneNumber());
            updateQuery.setString(6, customer.getEmail());
            updateQuery.setInt(7, isBusiness);
            updateQuery.setInt(8, customer.getCustomerId());
            updateQuery.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method updateCustomer failed");
            success = false;
        }
        return success;
    }


    public ArrayList<Integer> getOrderIdsBySubscription(Subscription subscription) {
        ArrayList<Integer> orderIds = new ArrayList<Integer>();
        try {
            String selectSql = "SELECT order_id FROM subscription_relation_order WHERE subscription_id = " + subscription.getSubscriptionId();
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            while (res.next()) {
                orderIds.add(res.getInt("order_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getOrderIdsByOrderIds failed");
        }
        return orderIds;
    }

    public ArrayList<Integer> getOrderIdsByCustomer(Customer customer) {
        ArrayList<Integer> orderIds = new ArrayList<Integer>();
        try {
            String selectSql = "SELECT order_id FROM n_order WHERE customer_id = " + customer.getCustomerId();
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            while (res.next()) {
                orderIds.add(res.getInt("order_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getOrderIdsByCustomer failed");
        }
        return orderIds;
    }

    /*    Method for getting all customers with
    its orders, subscriptions (with orders)
*/
    public ObservableList<Customer> getAllCustomers(ObservableList<Order> allOrders) {
        ObservableList<Subscription> allSubscriptions = getAllSubscriptions();
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
                Customer existingCustomer = new Customer(res.getInt("c_id"), isBusiness, res.getString("email"), res.getString("first_name"),
                        res.getString("last_name"), res.getInt("phone"), res.getString("business_name"));
                existingCustomer.setAddress(getAddress(res.getInt("address_id")));

                ObservableList<Order> allOrdersUnderCustomer = FXCollections.observableArrayList();

                for (Subscription subscription : allSubscriptions
                        ) {

                    if (subscription.getCustomerId() == existingCustomer.getCustomerId()) {
                        existingCustomer.setSubscription(subscription);
                    }
                    ObservableList<Order> ordersOnThisSubscription = FXCollections.observableArrayList();
                    ArrayList<Integer> orderIdsForThisSubscription = getOrderIdsBySubscription(subscription);
                    for (Integer orderId :
                            orderIdsForThisSubscription) {
                        for (Order order :
                                allOrders) {
                            if (order.getOrderId() == orderId) {
                                ordersOnThisSubscription.add(order);
                            }
                        }
                    }
                    ordersOnThisSubscription.forEach(order -> allOrdersUnderCustomer.add(order));
                    subscription.setOrdersOnThisSubscription(ordersOnThisSubscription);
                }

                ArrayList<Integer> orderIdsByCustomer = getOrderIdsByCustomer(existingCustomer);

                for (Integer orderId :
                        orderIdsByCustomer) {
                    for (Order order :
                            allOrders) {
                        if (orderId == order.getOrderId()) {
                            allOrdersUnderCustomer.add(order);
                        }
                    }

                    existingCustomer.setOrders(allOrdersUnderCustomer);
                    customers.add(existingCustomer);

                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getAllCustomers failed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("method getAllCustomers failed, not Sql exception!");
        }
        return customers;
    }

    /*public void setOrdersInCustomer(ObservableList<Customer> allCustomers) {
        ObservableList<> customers = FXCollections.observableArrayList();
        try {
            String selectSql = "SELECT * FROM customer";
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            while (res.next()) {
                boolean isBusiness = false;
                if (res.getInt("isBusiness") == 1) {
                    isBusiness = true;

                }
                Customer existingCustomer = new Customer(res.getInt(1), isBusiness, res.getString("email"), res.getString("first_name"),
                        res.getString("last_name"), res.getInt("phone"), res.getString("business_name"));
                existingCustomer.setAddress(getAddress(res.getInt("address_id")));
                customers.add(existingCustomer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getAllCustomers failed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("method getAllCustomers failed, not Sql exception!");
        }
        return customers;
    }*/




    /*Dish methods*/

    // Method for  Registering Dish in database

    public boolean addDish(Dish theDish) {
        boolean success = false;
        try {
            String sqlSetning = "INSERT INTO dish(price, name) VALUES(?,?)";
            insertQuery = con.prepareStatement(sqlSetning, Statement.RETURN_GENERATED_KEYS);
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

    //    Method for updating/changing a Dish
    public boolean updateDish(Dish dish) {
        boolean success = false;
        try {
            String sql = "UPDATE dish SET price = ?, name = ? WHERE dish_id = ?";
            updateQuery = con.prepareStatement(sql);
            updateQuery.setDouble(1, dish.getPrice());
            updateQuery.setString(2, dish.getDishName());
            updateQuery.setInt(7, dish.getDishId());
            updateQuery.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method updateDish failed");
            success = false;
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

    // TODO: 14.04.2016 Change to Rogers update
    /*














6:20PM

*/

    public boolean addEmployee(Employee newEmp) {
        boolean ok = false;

        try {
            con.setAutoCommit(false);
            if (!addAddress(newEmp.getAddress())) {
                SqlCleanup.rullTilbake(con);
                SqlCleanup.settAutoCommit(con);
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

    public Employee getEmployeeByUsername(String username) {
        try {
            String selectSql = "SELECT e.employee_id, e.first_name, e.last_name, e.phone, e.email, e.username, e.salary, e.passhash, e.pos_id, e.address_id" +
                    " FROM employee e WHERE e.username = ?;";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setString(1, username);
            ResultSet res = selectQuery.executeQuery();
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
            System.out.println("method getEmployeeByUsername failed");
        }
        return null;
    }

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
            updateEmpQuery.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method updateEmployee failed");
            SqlCleanup.rullTilbake(con);
            SqlCleanup.settAutoCommit(con);
        }
        return false;
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
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setInt(1, ingredient.getSupplier().getSupplierId());
            insertQuery.setDouble(2, ingredient.getQuantityOwned());
            insertQuery.setString(3, ingredient.getUnit());
            insertQuery.setDouble(4, ingredient.getPrice());
            insertQuery.setString(5, ingredient.getIngredientName());
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

    //    Method for updating an Ingredient
    public boolean updateIngredient(Ingredient ingredient) {
        boolean success = false;
        try {
            String sql = "UPDATE ingredient SET supplier_id = ?, quantity_owned = ?, unit = ?, " +
                    "price = ?, description = ? WHERE ingredient_id = ?";
            updateQuery = con.prepareStatement(sql);
            updateQuery.setInt(1, ingredient.getSupplierId());
            updateQuery.setDouble(2, ingredient.getQuantityOwned());
            updateQuery.setString(3, ingredient.getUnit());
            updateQuery.setDouble(4, ingredient.getPrice());
            updateQuery.setString(5, ingredient.getIngredientName());
            updateQuery.setInt(6, ingredient.getIngredientId());
            updateQuery.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method updateIngredient failed");
            success = false;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in updateIngredient, not Sql Exception");
        }
        return success;
    }


    public ObservableList<Order> getOrders(int posId) {
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
                LocalDate deadline = res.getDate("delivery_date").toLocalDate();
                LocalDate actualDeliveryDate = res.getDate("delivered_date").toLocalDate();
                double price = res.getDouble("price");
                String status = res.getString("status");
                Address address = getAddress(res.getInt("address"));
                Order order = new Order(orderId, customerRequests, deadline, actualDeliveryDate, price, status, null, address);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in method getOrders, most likely to do when calling DRIVER, because results clash (created by Paul)");
        }
        return orders;
    }


    // TODO: 28.03.2016 needs to enable a selector in the windows of the individual employee positions that lets you select elements and send them to methods

    /*Method for registering ingredients in DISH
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in addIngredientsInDish, not Sql Exception");
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
        }
        return success;
    }

    //    Method for removing rows aka dishlines from dishline. Dishlines sent it is removed from the table.
    public boolean deleteIngredientsInDish(ObservableList<DishLine> dishLines) {
        boolean success = false;
        try {
            con.setAutoCommit(false);
            for (DishLine oneLine :
                    dishLines) {

                String sqlSetning = "DELETE FROM dish_line WHERE ingredient_id = ?";
                PreparedStatement deleteQuery = con.prepareStatement(sqlSetning);
                deleteQuery = con.prepareStatement(sqlSetning);
                deleteQuery.setInt(1, oneLine.getIngredient().getIngredientId());
                deleteQuery.execute();
            }
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Method addIngredientInDish failed");
            SqlCleanup.lukkForbindelse(con);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in removeIngredientIndDish, not Sql Exception");
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
                    Ingredient tempIngredient = new Ingredient(ingredientId, name, unit, quantityOwned, price, tempSupplier.get(0));
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
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
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


    public ObservableList<Subscription> getAllSubscriptions() {
        ObservableList<Subscription> subscriptions = FXCollections.observableArrayList();
        try {
            String selectSql = "SELECT * FROM subscription";
            selectQuery = con.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();
            while (res.next()) {
                int supplierId = res.getInt("subscription_id");
                LocalDate startDate = res.getDate("start_date").toLocalDate();
                LocalDate endDate = res.getDate("end_date").toLocalDate();
                int customerId = res.getInt("customer_id");
                subscriptions.add(new Subscription(supplierId, startDate, endDate, null, customerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method getAllSuppliers failed");
        }
        return subscriptions;
    }

    //    Method for updating a subscription
    public boolean updateSubscription(Subscription subscription) {
        boolean success = false;
        try {
            String sql = "UPDATE subscription SET customer_id = ?, start_date = ?, " +
                    "end_date = ? WHERE subscription_id = ?";
            updateQuery = con.prepareStatement(sql);
            updateQuery.setInt(1, subscription.getCustomerId());
            updateQuery.setDate(2, Date.valueOf(subscription.getStartSubscription()));
            updateQuery.setDate(3, Date.valueOf(subscription.getEndSubscription()));
            updateQuery.setInt(4, subscription.getSubscriptionId());
            updateQuery.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method updateSubscription failed");
            success = false;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("method updateSubscription failed, not SQL exeption");
        }
        return success;
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
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
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

//    Method for updating supplyer
public boolean updateSupplier(Supplier supplier) {
    boolean success = false;
    try {
        String sql = "UPDATE supplier SET address_id = ?, business_name = ?, " +
                "phone = ? WHERE supplier_id = ?";
        updateQuery = con.prepareStatement(sql);
        updateQuery.setInt(1, supplier.getSupplierId());
        updateQuery.setString(2, supplier.getBusinessName());
        updateQuery.setInt(3, supplier.getPhoneNumber());
        updateQuery.setInt(4, supplier.getSupplierId());
        updateQuery.executeUpdate();
        success = true;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("method updateSupplier failed");
        success = false;
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("method updateSupplier failed, not SQL exception");
    }
    return success;
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