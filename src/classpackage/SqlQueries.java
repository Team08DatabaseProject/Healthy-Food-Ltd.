package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

import java.sql.*;
import java.util.*;

/**
 * Created by paul thomas on 17.03.2016.
 */


public class SqlQueries extends DBConnector {

    public final static String INPREPARATION = "In preparation";
    public final static String CREATED = "Create";
    public final static String READYFORDELIVERY = "Ready for delivery";
    public final static String UNDERDELIVERY = "Under delivery";
    public final static String DELIVERED = "In preparation";

    /*
    Class where all methods that sql is required, shall be written.
     For example every method that requires getting data from database.
     */

    PreparedStatement selectQuery;
    PreparedStatement insertQuery;
    PreparedStatement updateQuery;
    ResultSet res = null;

    public SqlQueries() {

    }


    public void addEmployee(Employee newEmp) {
        int attempts = 0;
        boolean ok = false;

        do {
            try {
                con.setAutoCommit(false);
								registerAddress(newEmp.getAddress());
                String insertSql = "INSERT INTO employee VALUES(?,?,?,?,?,?,?,?,?,?)";
                insertQuery = con.prepareStatement(insertSql);
                insertQuery.setInt(1, newEmp.getEmployeeId());
                insertQuery.setString(2, newEmp.getFirstName());
                insertQuery.setString(3, newEmp.getLastName());
                insertQuery.setInt(4, newEmp.getPhoneNo());
                insertQuery.setString(5, newEmp.geteMail());
                insertQuery.setInt(6, newEmp.getAddress().getAddressId());
                insertQuery.setString(7, newEmp.getUsername());
                insertQuery.setInt(8, newEmp.getPosId());
                insertQuery.setDouble(9, newEmp.getSalary());
                insertQuery.setString(10, newEmp.getPassHash());
                insertQuery.executeUpdate();
                con.commit();
                ok = true;
            } catch (SQLException e) {
                System.out.println(e);
                if (attempts < 4) {
                    attempts++;
                } else {
                    System.out.println("Something went wrong: user registration failed.");
                    SqlCleanup.lukkForbindelse(con);
                }
            } finally {
                SqlCleanup.lukkSetning(insertQuery);
                SqlCleanup.settAutoCommit(con);
            }
        } while (!ok);
    }

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
                System.out.println(e);
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

    public Map<Integer, String> getEmployeePositions() {
        Map<Integer, String> employeePositions = new HashMap<>();

        try {
            String selectSql = "SELECT pos_id, description FROM employee_position";
            Statement s = con.createStatement();
            res = s.executeQuery(selectSql);
            while (res.next()) {
                employeePositions.put(res.getInt(1), res.getString(2));
            }
            return employeePositions;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    // method for registering new Ingredient
    public boolean registerNewIngredient(int supplierId, double quantityOwned, double quantityReserved, String unit, double price, String description) {
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
    }

    // Method for registering new customer
  /*  public boolean registerNewAddress(int zipCode, String address) {
        boolean success = false;
        try {
            con.setAutoCommit(false);
            String sqlSetning = "INSERT INTO address(zipcode, address) VALUES(?,?)";
            insertQuery = con.prepareStatement(sqlSetning);
            insertQuery.setInt(1, zipCode);
            insertQuery.setString(2, address);
            insertQuery.execute();
            con.commit();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Method registerNewAddress failed");
            SqlCleanup.lukkForbindelse(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
            SqlCleanup.settAutoCommit(con);
        }
        return success;
    }*/

    //Method for registering Customer
    public boolean registerCustomer(Customer theCustomer) {
        boolean success = false;

        try {
            con.setAutoCommit(false);
            String sqlSetning = "INSERT INTO customer(address_id, business_name, first_name, last_name, phone, email, isbusiness) VALUES(?,?,?,?,?,?,?)";
            int isBusiness = 0;
            if (theCustomer.isBusiness()){
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
            // TODO: 31.03.2016 compare registerAddress with how this method should be done!
            con.commit();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Method registerCustomer failed");
            SqlCleanup.lukkForbindelse(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
            SqlCleanup.settAutoCommit(con);
        }
        return success;
    }

    // Method for  Registering Dish in database
    public boolean registerDish(Dish theDish) {
        boolean success = false;
        try {
            con.setAutoCommit(false);
            String sqlSetning = "INSERT INTO dish(price, name) VALUES(?,?)";
            insertQuery = con.prepareStatement(sqlSetning);
            insertQuery.setDouble(1, theDish.getPrice());
            insertQuery.setString(2, theDish.getDishName());
            insertQuery.execute();
            ResultSet res = insertQuery.getGeneratedKeys();
            while (res.next()){
                theDish.setDishId(res.getInt(1));
            }
            con.commit();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Method registerDish failed");
            SqlCleanup.lukkForbindelse(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
            SqlCleanup.settAutoCommit(con);
        }
        return success;
    }

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
            }
            selectQuery = con.prepareStatement(selectSql);
            res = selectQuery.executeQuery();
            while (res.next()) {
                int orderId = res.getInt("order_id");
                int customerId = res.getInt("customer_id");
                int subscriptionId = res.getInt("subscription_id");
                String customerRequests = res.getString("customer_requests");
                java.util.Date deadline = res.getTimestamp("delivery_date");
                double price = res.getDouble("price");
                String address = res.getString("address");
                Order order = new Order(orderId, customerId, subscriptionId, customerRequests, deadline, price, address);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in method getOrders, most likely to do when calling DRIVER, because results clash (created by Paul)");
        }
        return orders;
    }
    /*MultipleSelectionModel selector = new MultipleSelectionModel() {
    }*/
    // TODO: 28.03.2016 needs to enable a selector in the windows of the individual employee positions that lets you select elements and send them to methods



    /*Method for registering ingredients in DISH
    * takes in a Hashmap where key = ingredientId, value = quantity
    * */
    public boolean registerIngredintsInDish(Dish theDish, HashMap<Integer, Double> ingredientsAndQuantity) {
        boolean success = false;
        try {
            int ingrId;
            con.setAutoCommit(false);
            String sqlSetning = "INSERT INTO ingredient_in_dish(ingredient_id, dish_id, quantity) VALUES(?,?, ?)";
            insertQuery = con.prepareStatement(sqlSetning);


            /*
            System.out.println("\nExample 2...");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		map.forEach((k,v)->System.out.println("Key : " + k + " Value : " + v))
		}

             */

            for (Map.Entry<Integer, Double> entry : ingredientsAndQuantity.entrySet()){
                insertQuery.setInt(1, entry.getKey().intValue());
                insertQuery.setInt(2, theDish.getDishId());
                insertQuery.setDouble(3, entry.getValue().doubleValue());
                insertQuery.execute();
            }
            con.commit();
            success = true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Unique value restraint!!!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Method registerIngredientsInDish failed");
            SqlCleanup.lukkForbindelse(con);
        } finally {
            SqlCleanup.lukkSetning(insertQuery);
            SqlCleanup.settAutoCommit(con);
        }
        return success;
    }

    // TODO: 31.03.2016 is this method finished or should there sqlcleanup be called? As well as a more specific Exception handled?
    public boolean registerAddress(Address newAddress) {
		try {
			String sql = "INSERT INTO address(address, zipcode) VALUES(?, ?);";
			insertQuery = con.prepareStatement(sql);
			insertQuery.setString(1, newAddress.getAddress());
			insertQuery.setInt(2, newAddress.getZipCode());
			insertQuery.executeUpdate();
			ResultSet res = insertQuery.getGeneratedKeys();
			res.next();
			newAddress.setAddressId(res.getInt(1));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}



/*
created
in preparation
ready for delivery
under delivery
delivered

sales {
*
}
chef{
created
in preparation
ready for delivery
}
driver{
ready for delivery
delivered (timestamp)
}
ceo
{
*
}
admin{
*
}

 */