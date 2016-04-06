package classpackage;


import java.util.ArrayList;

/**
 * Created by axelkvistad on 3/16/16.
 */
public class Employee {
    private int employeeId;
    private String username;
    private String firstName;
    private String lastName;
    private int phoneNo;
    private String eMail;
    private double salary;
    private String passHash;
    private Address address;
    private EmployeePosition position;
    private static final int ADMIN = 0;
    private static final int CEO = 1;
    private static final int CHEF = 2;
    private static final int DRIVER = 3;
    private static final int SALES = 4;
    private static final int NUTRITION = 5;

    // Constructor for getting an employee from the employee table

    public Employee(int employeeId, String username, String firstName, String lastName, int phoneNo, String eMail, double salary, String passHash, Address address, EmployeePosition position) {
        this.employeeId = employeeId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.eMail = eMail;
        this.salary = salary;
        this.passHash = passHash;
        this.address = address;
        this.position = position;
        /* Kall til EncryptionService her for å generere passHash (og kanskje salt) */
        // Thomas: If a new employee is being created (i dont meant he object.. i mean a real new one to be added to the DB), the method to encrypt the password should called before and only the hash should be passed to the object.
        //passHash = "encryption-ting her";
    }

    // Constructor for creating a new employee object from GUI, missing employeeId

    public Employee(String username, String firstName, String lastName, int phoneNo, String eMail, double salary, String passHash, Address address, EmployeePosition position) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.eMail = eMail;
        this.salary = salary;
        this.passHash = passHash;
        this.address = address;
        this.position = position;
        /* Kall til EncryptionService her for å generere passHash (og kanskje salt) */
        // Thomas: If a new employee is being created (i dont meant he object.. i mean a real new one to be added to the DB), the method to encrypt the password should called before and only the hash should be passed to the object.
        //passHash = "encryption-ting her";
    }

    // employee constructor without password

    public Employee(int employeeId, String username, double salary) {
        this.employeeId = employeeId;
        this.username = username;
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {this.employeeId = employeeId;}
    public String getUsername() {
        return username;
    }

    public double getSalary() {
        return salary;
    }
    public String getPassHash() {
        return passHash;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public String geteMail() {
        return eMail;
    }

    public Address getAddress() {
        return address;
    }

    public EmployeePosition getPosition() {
        return position;
    }
    public void setSalary(double newSalary) {
        salary = newSalary;
        updateSalary();
    }

    private void updateSalary()
    {
        //query to update
    }

    public boolean isAdmin(){
        return (position.getId() == ADMIN);}
    public boolean isCEO() {
        return (position.getId() == CEO);
    }
    public boolean isChef() {
        return (position.getId() == CHEF);
    }
    public boolean isDriver() {
        return (position.getId() == DRIVER);
    }
    public boolean isSales() {
        return (position.getId() == SALES);
    }
    public boolean isNutrition() {
        return (position.getId() == NUTRITION);
    }


    // Usage:
    // Employee a = LoadEmployee(5);
    /*
    public static Employee LoadEmployee(int p_personId)
    {
        Statement statement = statics.db.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `employee` WHERE person_id = '" + p_personId + "' LIMIT 1");
        while (res.next())
            return new Employee(res.getInt("person_id"), res.getString("username"), res.getInt("pos_id"), res.getDouble("salary"), res.getString("passhash"));
        return null;
    }
    */
}