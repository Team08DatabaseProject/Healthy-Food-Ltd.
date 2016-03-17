package classpackage;

import java.sql.*;

/**
 * Created by paul thomas on 17.03.2016.
 */
public class SqlQueries {

    /*
    Class where all methods that sql is required, shall be written.
     For example every method that requires getting data from database.
     */


    String brukernavn = "thomjos"; //DataLeser2.lesTekst("Brukernavn: ");  // DataLeser2, se nedenfor
    String passord = "cinPn2AK";
    String databasenavn = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + brukernavn + "?user=" + brukernavn + "&password=" + passord;
    String databasedriver = "com.mysql.jdbc.Driver";
    Connection connection = null;
    PreparedStatement selectQuery;
    ResultSet res = null;


    /*
    needs revision based on GUI


    */
    public SqlQueries() {
        try {
            Class.forName(databasedriver);  // laster inn driverklassen
            connection = DriverManager.getConnection(databasenavn);
        } catch (ClassNotFoundException e) {
            System.out.println("Databasedriver ikke funnet.");
        } catch (SQLException e) {
            System.out.println("Forbindelse feilet.");
        }
    }

    public void databaseConnection() {
        try {
            Class.forName(databasedriver);  // laster inn driverklassen
            connection = DriverManager.getConnection(databasenavn);
        } catch (ClassNotFoundException e) {
            System.out.println("Databasedriver ikke funnet.");
        } catch (SQLException e) {
            System.out.println("Forbindelse feilet.");
        }

    }

    public void lukkForbindelse() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Forbindelse kunne ikke lukkes.");
        }
    }

    public Employee getUser(String username, String passwordHash) {
        int personId = -1;
        int posId = -1;
        double salary = -1;
        int attempts = 0;

        boolean ok = false;

        do {
            try {
//                connection.setAutoCommit(false);
                String selectSql = "SELECT person_id, username, pos_id, salary, passhash FROM employee WHERE username = ? AND passHash = ?";
                selectQuery = connection.prepareStatement(selectSql);
                selectQuery.setString(1, username);
                selectQuery.setString(2, passwordHash);
                res = selectQuery.executeQuery();
                res.next();

                personId = res.getInt("person_id");
                posId = res.getInt("pos_id");
                salary = res.getDouble("salary");
         //       connection.commit();
                ok = true;

            } catch (SQLException e) {
                if (attempts < 4) {
                    attempts++;
                } else {
                    return null;
                }
                SqlCleanup.rullTilbake(connection);
            } finally {
                SqlCleanup.lukkResSet(res);
                SqlCleanup.settAutoCommit(connection);
            }
        } while(!ok);
        if (personId != -1) {
            return new Employee(personId, username, posId, salary, passwordHash);
        }
        return null;
    }

    public Employee getUser() {
        return null;

    }

}
