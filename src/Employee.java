/**
 * Created by axelkvistad on 3/16/16.
 */
class Employee {
    private int personId;
    private String username;
    private int posId;
    private double salary;
    private final String passHash;

    public Employee(int personId, String username, int posId, double salary, String startPassword) {
        this.personId = personId;
        this.username = username;
        this.posId = posId;
        this.salary = salary;

        /* Kall til EncryptionService her for å generere passHash (og kanskje salt) */

        passHash = "encryption-ting her";
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
    }
}
