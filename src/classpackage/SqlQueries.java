package classpackage;

import classpackage.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by paul thomas on 17.03.2016.
 */

public class SqlQueries extends DBConnector {

    /*
    Class where all methods that sql is required, shall be written.
     For example every method that requires getting data from database.
     */
    
    PreparedStatement selectQuery;
    PreparedStatement insertQuery;
    ResultSet res = null;

    public SqlQueries() {

    }

    public ObservableList<Order> getOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        boolean done = false;

        do {
            try {
                String selectSql = "SELECT * FROM n_order";
                selectQuery = con.prepareStatement(selectSql);
                res = selectQuery.executeQuery();

                while (res.next()) {
                    int orderId = res.getInt("order_id");
                    int customerId = res.getInt("customer_id");
                    int subscriptionId = res.getInt("subscription_id");
                    String  customerReq = res.getString("customer_requests");
                    java.util.Date deliveryDate = res.getDate("delivery_date");
                    double price = res.getDouble("price");
                    String address = res.getString("address");

                    Order order = new Order(orderId, customerReq, deliveryDate, price, address);
                    orders.add(order);
                }
                done = true;
            } catch (SQLException e) {
                System.out.println(e);
            }
        } while(!done);
        return orders;
    }


    public void addEmployee(Employee newEmp, String fName, String lName, int phone,
                            String email, int addressId) {
        int attempts = 0;
        boolean ok = false;

        do {
            try {
                con.setAutoCommit(false);
                String insertSql = "INSERT INTO employee VALUES(?,?,?,?,?,?,?,?,?,?)";
                insertQuery = con.prepareStatement(insertSql);
                insertQuery.setInt(1, newEmp.getPersonId());
                insertQuery.setString(2, fName);
                insertQuery.setString(3, lName);
                insertQuery.setInt(4, phone);
                insertQuery.setString(5, email);
                insertQuery.setInt(6, addressId);
                insertQuery.setString(7, newEmp.getUsername());
                insertQuery.setInt(8, newEmp.getPosId());
                insertQuery.setDouble(9, newEmp.getSalary());
                insertQuery.setString(10, newEmp.getPassHash());
                insertQuery.execute();

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
        } while(!ok);
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
                if (attempts < 4) {
                    attempts++;
                } else {
                    return null;
                }
            } finally {
                SqlCleanup.lukkResSet(res);
                SqlCleanup.settAutoCommit(con);
            }
        } while(!ok);
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

}
