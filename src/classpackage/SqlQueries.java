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


    // TODO: 22.04.2016 In Delete methods that include fex adress remember to delete the address as well
    // TODO: 19.04.2016 Refine methods to give confirmations on both execute() and executeUpdate()
    // TODO: 04.04.2016 the strings for Gui that deals with Orders should use just so we have this standardized
   /* public final String CREATED = "Created";
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
*/
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
            insertQuery.execute();
            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
            newAddress.setAddressId(res.getInt(1));
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

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

    public boolean updateAddress(Address address) {
        try {
            String sql = "UPDATE address SET zipcode = ?, address = ?, place = ? WHERE address_ID = ?;";
            updateQuery = con.prepareStatement(sql);
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


    /*Customer methods:*/
    //Method for registering Customer
    public boolean addCustomer(Customer theCustomer) {
        ResultSet res = null;
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


    public boolean updateCustomer(Customer customer) {
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


    public ArrayList<Integer> getOrderIdsByCustomer(Customer customer) {
        ArrayList<Integer> orderIds = new ArrayList<Integer>();
        ResultSet res = null;
        try {
            String selectSql = "SELECT order_id FROM `order` WHERE customer_id = ?";
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

    /*    Method for getting all customers with
    its orders, subscriptions (with orders)
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
        } finally {
            closeEverything(res, selectQuery, con);
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

    //    Method for updating/changing a Dish
    public boolean updateDish(Dish dish) {
        try {
            String sql = "UPDATE dish SET price = ?, name = ? WHERE dish_id = ?";
            updateQuery = con.prepareStatement(sql);
            updateQuery.setDouble(1, dish.getPrice());
            updateQuery.setString(2, dish.getDishName());
            updateQuery.setInt(7, dish.getDishId());
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

    //    Method for deleting a dish
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


    //    Method for adding Dish in Menu
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
            e.printStackTrace();
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }


    //    Method for removing dishes in a menu
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(null, deleteQuery, con);
        }
        return false;
    }


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

    public boolean addEmployee(Employee newEmp) {
        ResultSet res = null;

        try {
            con.setAutoCommit(false);
            if (!addAddress(newEmp.getAddress())) {
                SqlCleanup.rullTilbake(con);
                SqlCleanup.settAutoCommit(con);
                return false;
            }
            String insertSql = "INSERT INTO employee (first_name, last_name, phone, email, address_id, username, pos_id, salary, passhash) " +
                    "VALUES(?,?,?,?,?,?,?,?,?)";
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setString(1, newEmp.getFirstName());
            insertQuery.setString(2, newEmp.getLastName());
            insertQuery.setInt(3, newEmp.getPhoneNo());
            insertQuery.setString(4, newEmp.geteMail());
            insertQuery.setInt(5, newEmp.getAddress().getAddressId());
            insertQuery.setString(6, newEmp.getUsername());
            insertQuery.setInt(7, newEmp.getPosition().getId());
            insertQuery.setDouble(8, newEmp.getSalary());
            insertQuery.setString(9, newEmp.getPassHash());
            insertQuery.execute();

            res = insertQuery.getGeneratedKeys();
            res.next();
            newEmp.setEmployeeId(res.getInt(1));
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

    /*
        public Employee getUser(String username, String passwordHash) {
            int personId = -1;
            int posId = -1;
            double salary = -1;
            int attempts = 0;
            boolean success = false;
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
                    success = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    SqlCleanup.lukkResSet(res);
                    SqlCleanup.settAutoCommit(con);
                }
            } while (!success);
            if (personId != -1) {
                return new Employee(personId, username, posId, salary);
            }
            return null;
        }
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

    public EmployeePosition getEmployeePosition(int posId) {
        ResultSet res = null;
        try {
            String selectSql = "SELECT description, default_salary FROM employee_position WHERE pos_id = ?";
            selectQuery = con.prepareStatement(selectSql);
            selectQuery.setInt(1, posId);
            res = selectQuery.executeQuery();
            if (!res.next()) return null;

            String description = res.getString(1);
            double salary = res.getDouble(2);
            return new EmployeePosition(posId, description, salary);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return null;
    }

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



    /*Ingredient methods*/

    // Method for adding Ingredients

    //    Method is usable for adding ingredient with or without new supplier
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

    //    Method for updating an Ingredient
    public boolean updateIngredient(Ingredient ingredient) {
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

    // TODO: 28.03.2016 needs to enable a selector in the windows of the individual employee positions that lets you select elements and send them to methods

    /*Method for registering ingredients in DISH
    * */
    public boolean addIngredientInDish(Dish dish, ObservableList<DishLine> dishLine) {
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

    //    Method for removing rows aka dishlines from dishline. Dishlines sent it is removed from the table.
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
            e.printStackTrace();
        } finally {
            closeEverything(null, deleteQuery, con);
        }
        return false;
    }


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


                ObservableList<Supplier> tempSupplier = FXCollections.observableArrayList();
                allSuppliers.filtered(supplier -> supplier.getSupplierId() == supplierId).forEach(supplier1 -> tempSupplier.add(supplier1));

                try {
                    Ingredient tempIngredient = new Ingredient(ingredientId, name, unit, quantityOwned, price, tempSupplier.get(0));
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

    /*Menu methods*/

    //    method for adding menu
    public boolean addMenu(Menu menu) {
        ResultSet res = null;
        try {

            String insertSql = "INSERT INTO menu(description, type_meal) VALUES(?,?)";
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setString(1, menu.getName());
            insertQuery.setString(2, menu.getMealType());
            insertQuery.executeUpdate();

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

    //    Method for deleting a menu
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

    public ObservableList<Menu> getAllMenus(ObservableList<Dish> allDishes) {
        ObservableList<Menu> menus = FXCollections.observableArrayList();
        ResultSet res = null;
        try {
            String selectSql = "SELECT * FROM menu";
            selectQuery = con.prepareStatement(selectSql);
            res = selectQuery.executeQuery();
            while (res.next()) {
                int menuId = res.getInt("menu_id");
                String descriptin = res.getString("description");
                String typeMeal = res.getString("type_meal");
                Menu existingMenu = new Menu(menuId, descriptin, typeMeal, null);
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


    /*MenuLine methods*/

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
                for (Dish dish :
                        allDishes) {
                    if (dish.getDishId() == dishId) {
                        menuLines.add(new MenuLine(dish, amount, priceFactor));
                        break;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return menuLines;
    }


    /*Order Methods*/
//    Method for getting orders based on position id
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
                selectSql = "SELECT * FROM `order` WHERE status_id = ?;" +
                        "SELECT * FROM `order` WHERE status_id = ? AND delivery_date = DATE(now())";
                selectQuery = con.prepareStatement(selectSql);
                selectQuery.setInt(1, READYFORDELIVERY);
                selectQuery.setInt(2, DELIVERED);
            } else if (posId == 4) {
                selectSql = "SELECT * FROM `order` WHERE status_id = ? OR ? OR ?";
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
                int customerId = res.getInt("customer_id");
                int subscriptionId = res.getInt("subscription_id");
                String customerRequests = res.getString("customer_requests");
                LocalDate deadline = res.getDate("delivery_date").toLocalDate();
                double price = res.getDouble("price");
                OrderStatus status = getOrderStatus(res.getInt("status_id"));
                Address address = getAddress(res.getInt("address_id"));
                LocalDate actualDeliveryDate = null;
                LocalDate actualDeliveryDateUnstable = res.getDate("delivered_date").toLocalDate();
                if (actualDeliveryDateUnstable != null) {
                    actualDeliveryDate = actualDeliveryDateUnstable;
                }
                Order order = new Order(orderId, customerRequests, deadline, actualDeliveryDate, price, status, null, address);
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

/*Method for adding order, takes in either a customer or a subscription to put the order under,
     customer_id will be set to null if customer is null. the order will be added to subscription_relation order
    if subscription is NOT null*/

    public boolean addOrder(Subscription subscription, Order order, Customer customer) {
        ResultSet res = null;
        try {
            con.setAutoCommit(false);
            if (order.getAddress().getAddressId() == 0) {
                if (!addAddress(order.getAddress())) {
                    SqlCleanup.rullTilbake(con);
                    return false;
                }
            }
            String insertSql = "INSERT INTO `order`(customer_id, subscription_id, customer_requests, delivery_date, delivered_date, " +
                    "price, address_id, status_id) VALUES(?,?,?,?,?,?,?,?)";
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setInt(1, customer.getCustomerId());
            insertQuery.setString(3, order.getCustomerRequests());
            insertQuery.setDate(4, Date.valueOf(order.getDeadline()));
            insertQuery.setDate(5, Date.valueOf(order.getActualDeliveryDate()));
            insertQuery.setDouble(6, order.getPrice());
            insertQuery.setInt(7, order.getAddress().getAddressId());
            insertQuery.setInt(8, order.getStatus().getStatusId());
            if (subscription != null) {
                insertQuery.setInt(2, subscription.getSubscriptionId());
            } else {
                insertQuery.setNull(2, Types.INTEGER);
            }
            if (insertQuery.executeUpdate() == 1) {
                con.commit();
                res = insertQuery.getGeneratedKeys();
                res.next();
                order.setOrderId(res.getInt(1));
                return true;
            } else {
                SqlCleanup.rullTilbake(con);
                return false;
            }
        } catch (SQLException e) {
            SqlCleanup.rullTilbake(con);
        } finally {
            SqlCleanup.settAutoCommit(con);
            closeEverything(res, insertQuery, con);
        }
        return false;
    }

    public boolean updateOrder(Order order, Customer customer) {
        try {
            String sql = "UPDATE `order` SET ";

            sql += "customer_requests = ?, delivery_date = ?, " +
                    "delivered_date = ?, price = ?, address = ?, status = ?";
            if (!(customer == null)) {
                sql += ", customer_id = ?";
            }
            sql += " WHERE order_id = ?";
            updateQuery = con.prepareStatement(sql);
            if (!(customer == null)) {
                updateQuery.setInt(7, customer.getCustomerId());
            }
            updateQuery.setString(1, order.getCustomerRequests());
            updateQuery.setDate(2, Date.valueOf(order.getDeadline()));
            updateQuery.setDate(3, Date.valueOf(order.getActualDeliveryDate()));
            updateQuery.setDouble(4, order.getPrice());
            updateQuery.setInt(5, order.getAddress().getAddressId());
            int rowsAffected = updateQuery.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("method updateOrder failed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in updateOrder, not Sql Exception");
        } finally {
            closeEverything(null, updateQuery, con);
        }
        return false;
    }

    //    Method for adding a order in the junction table between order and subscription
    public boolean addOrderInSubscription(Subscription subscription, Order order) {
        try {
            String insertSql = "INSERT INTO subscription_relation_order(order_id, subscription_id) VALUES(?,?)";
            insertQuery = con.prepareStatement(insertSql);
            insertQuery.setInt(1, order.getOrderId());
            insertQuery.setInt(2, subscription.getSubscriptionId());
            insertQuery.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println("Something went wrong: user registration failed in method addSubscription");
        } finally {
            closeEverything(null, insertQuery, con);
        }
        return false;
    }

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
                        break;
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

    //    Method for getting an Order status
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

    //    Method for deleting an order
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







    /*Subscription methods:*/

    public boolean addSubscription(Subscription subscription) {
        boolean success = false;
        ResultSet res = null;
        try {
            String insertSql = "INSERT INTO subscription(start_date, end_date) VALUES(?,?)";
            insertQuery = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setDate(1, java.sql.Date.valueOf(subscription.getStartSubscription()));
            insertQuery.setDate(2, java.sql.Date.valueOf(subscription.getEndSubscription()));
            insertQuery.executeQuery();

            res = insertQuery.getGeneratedKeys();
            res.next();
            subscription.setSubscriptionId(res.getInt(1));
            success = true;
        } catch (SQLException e) {
            System.out.println("Something went wrong: user registration failed in method addSubscription");
        } finally {
            closeEverything(res, insertQuery, con);
        }
        return success;
    }


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
            System.out.println("method getAllSuppliers failed");
        } finally {
            closeEverything(res, selectQuery, con);
        }
        return subscriptions;
    }

    //    Method for updating a subscription
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



    /*Supplier methods:*/

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

    //    Method for deleting The supplier in the parameters from db
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

    //    Method for updating supplyer
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

    //    Method for getting all OrderStatus types
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