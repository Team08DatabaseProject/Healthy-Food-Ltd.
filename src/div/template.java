/*package classpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import someFolderThatNeedsABetterName.consts;

public class template extends consts {

    // This 'should' be either saved in a common class for just static final queries or we just saved it as final in the methods they are being used. In this case i am saving outside to be simpler to find.
    static final String sf_getAllEmployeesQuery = "SELECT * FROM `employee`";

    //Either use the name "db", or "connection", i personally use db and rarely connection, but any other name could end up being misleading. This should also be in a class thats easily rememebred and accessed by every class so that we all use the same connection and keep it alive.
    public static Connection db;


    // This variable should be saved in a common class with all the other variables containing a collection of the object in question, like "s_orderListSavedOnClient".. and probably use a different name, though for exaplaining purposes, i am naming it like this:
    static List<Employee> s_employeeListSavedOnClient;

    // Named _main to not conflict with a main if there is already in our program. Though our main main should have these functions: and _init_() should be on the top of everything in our main.
    public static void main(String[] args) throws Exception {
        _init_();
        updateEmployeeList();
    }

    // This only has to be called once and at the startup of our application.. probaly the first function in our main(). Also, we should set our username and password. I actually don't mind if we use my username and password.
    private static void _init_() throws Exception
    {
        String dbDriver = "com.mysql.jdbc.Driver";
        Class.forName(dbDriver);
        String databasenavn = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + consts.username + "?user=" + consts.username + "&password=" + consts.passord;
        db = DriverManager.getConnection(databasenavn);
    }



    // Call this function whenever another client updates or when we init to get the list. NOTE: we should actually change the class Employee to have "passhash" instead of "startPassword", as we should not have the password saved in memory. (we shouldnt have the hash either.. but this is a school thing, so we can keep it. The hash is generated when we enter the password and the salt.
    private static void updateEmployeeList() throws Exception {
        s_employeeListSavedOnClient.clear();
        Statement setning = db.createStatement();
        ResultSet res = setning.executeQuery(sf_getAllEmployeesQuery);
        while (res.next())
            s_employeeListSavedOnClient.add(new Employee(res.getInt("person_id"), res.getString("username"), res.getInt("pos_id"), res.getDouble("salary"), res.getString("passhash")));
    }
}
*/