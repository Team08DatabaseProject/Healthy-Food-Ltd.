package classpackage;

/**
 * Created by axelkvistad on 3/16/16.
 */
class Employee {
    private int personId;
    private String username;
    private int posId;
    private double salary;
    private final String passHash;

    public Employee(int personId, String username, int posId, double salary, String passHash) {
        this.personId = personId;
        this.username = username;
        this.posId = posId;
        this.salary = salary;

        /* Kall til EncryptionService her for å generere passHash (og kanskje salt) */
        // Thomas: If a new employee is being created (i dont meant he object.. i mean a real new one to be added to the DB), the method to encrypt the password should called before and only the hash should be passed to the object.
        //passHash = "encryption-ting her";
    }

    // employee constructor without password

    public Employee(int personId, String username, int posId, double salary) {
        this.personId = personId;
        this.posId = posId;
        this.username = username;
        this.salary = salary;
    }

    public int getPersonId() {
        return personId;
    }
    public String getUsername() {
        return username;
    }
    public int getPosId() {
        return posId;
    }
    public double getSalary() {
        return salary;
    }
    public String getPassHash() {
        return passHash;
    }

    public void setSalary(double newSalary) {
        salary = newSalary;
        updateSalary();
    }

    private void updateSalary()
    {
        //query to update
    }

    // Usage:
    // Employee a = LoadEmployee(5);
    public static Employee LoadEmployee(int p_personId)
    {
        Statement statement = statics.db.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `employee` WHERE person_id = '" + p_personId + "' LIMIT 1");
        while (res.next())
            return new Employee(res.getInt("person_id"), res.getString("username"), res.getInt("pos_id"), res.getDouble("salary"), res.getString("passhash"));
        return null;
    }
}