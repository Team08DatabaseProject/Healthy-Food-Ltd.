package classpackage;

import classpackage.Employee;

import java.sql.*;
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
    ResultSet res = null;

    public SqlQueries() {

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
                SqlCleanup.rullTilbake(con);
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
