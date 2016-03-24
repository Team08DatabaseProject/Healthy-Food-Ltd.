package classpackage;

import java.sql.*;

/**
 * Created by axelkvistad on 3/18/16.
 */
public class DBConnector {

    private String username = "thomjos"; //DataLeser2.lesTekst("Brukernavn: ");  // DataLeser2, se nedenfor
    private String password = "cinPn2AK";
    private String databaseName = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + username + "?user=" + username + "&password=" + password;
    private String databaseDriver = "com.mysql.jdbc.driver";
    protected static Connection con = null;

    public DBConnector() {
        if(con == null) {
            try {
                Class.forName(databaseDriver);
                con = DriverManager.getConnection(databaseName);
            } catch (ClassNotFoundException exc) {
                System.out.println(exc);
            } catch (SQLException exc) {
                System.out.println(exc);
            }
        }
    }

    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }


}
