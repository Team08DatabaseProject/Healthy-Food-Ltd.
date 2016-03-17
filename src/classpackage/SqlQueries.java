package classpackage;

import java.sql.*;

/**
 * Created by paul thomas on 17.03.2016.
 */
class SqlQueries {

    /*
    Class where all methods that sql is required, shall be written.
     For example every method that requires getting data from database.
     */


    private String brukernavn = "axelbkv"; //DataLeser2.lesTekst("Brukernavn: ");  // DataLeser2, se nedenfor
    private String passord = "btrWX2Fy";
    private String databasenavn = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + brukernavn + "?user=" + brukernavn + "&password=" + passord;
    private String databasedriver = "com.mysql.jdbc.Driver";
    private Connection connection = null;


    /*
    needs revision based on GUI


    */
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




    }

}
