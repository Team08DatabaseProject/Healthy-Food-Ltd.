package classpackage;

/**
 * Created by axelkvistad on 3/17/16.
 * Test class for SqlQueries.java
 */

public class QueryTest2 {
    public static void main(String[] args) {
        SqlQueries sq = new SqlQueries();
        // sq.databaseConnection();
        Employee employee = sq.getUser("Axel", "abc");
        System.out.println("Position id: " + employee.getPosId() + "\nUsername: " + employee.getUsername() +
        "\nSalary: " + employee.getSalary() + "\nPassword hash: " + employee.getPassHash());
    }
}
