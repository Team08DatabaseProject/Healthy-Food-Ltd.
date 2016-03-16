package _0;

import classpackage.*;
import java.sql.*;
public class MySQL
{
    public static void _init_() throws Exception
    {
        String dbDriver = "com.mysql.jdbc.Driver";
        Class.forName(dbDriver);
        String databasenavn = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + consts.username + "?user=" + consts.username + "&password=" + consts.passord;
        statics.db = DriverManager.getConnection(databasenavn);
    }
    class Reload
    {
        //This is public and the rest is private because we had agreed to just update everything on a single change (perhaps I would not recommended it as its memory intensive).
        //If we decide later to reload each when necessary, then we can make all public.
        public static void ReloadAll()
        {
            updateEmployeeList()
        }
        private static void updateEmployeeList() throws Exception {
            s_employeeListSavedOnClient.clear();
            Statement statement = statics.db.createStatement();
            ResultSet res = statement.executeQuery(statics.sf_getAllEmployeesQuery);
            while (res.next())
                statics.s_employeeListSavedOnClient.add(new Employee(res.getInt("person_id"), res.getString("username"), res.getInt("pos_id"), res.getDouble("salary"), res.getString("passhash")));
        }
    }
}
