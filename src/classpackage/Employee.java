package classpackage;

import javafx.beans.property.*;
import java.util.ArrayList;

/**
 * Created by axelkvistad on 3/16/16.
 */

public class Employee {
    private IntegerProperty employeeId = new SimpleIntegerProperty();
    private StringProperty username = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private IntegerProperty phoneNo = new SimpleIntegerProperty();
    private StringProperty eMail = new SimpleStringProperty();
    private DoubleProperty salary = new SimpleDoubleProperty();
    private StringProperty passHash = new SimpleStringProperty();
    private ObjectProperty<Address> address = new SimpleObjectProperty<>();
    private ObjectProperty<EmployeePosition> position = new SimpleObjectProperty<>();
    private BooleanProperty checked = new SimpleBooleanProperty();
    public static final int ADMIN = 0;
    public static final int CEO = 1;
    public static final int CHEF = 2;
    public static final int DRIVER = 3;
    public static final int SALES = 4;
    public static final int NUTRITION = 5;

    /**
     * Creates an employee object from the database
     *
     * @param employeeId the unique identifier for the employee object
     * @param username username used for login
     * @param firstName first name
     * @param lastName last name
     * @param phoneNo phone number
     * @param eMail email
     * @param salary salary
     * @param passHash the pass harsh to be compared
     * @param address address of the employeee
     * @param position position of the employee
     */
    public Employee(int employeeId, String username, String firstName, String lastName, int phoneNo, String eMail, double salary, String passHash, Address address, EmployeePosition position) {
        this.employeeId.set(employeeId);
        this.username.set(username);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.phoneNo.set(phoneNo);
        this.eMail.set(eMail);
        this.salary.set(salary);
        this.passHash.set(passHash);
        this.address.set(address);
        this.position.set(position);

    }

    /**
     * Creates an employee object to the database
     *
     * @param employeeId the unique identifier for the employee object
     * @param username username used for login
     * @param firstName first name
     * @param lastName last name
     * @param phoneNo phone number
     * @param eMail email
     * @param salary salary
     * @param passHash the pass harsh to be compared
     * @param address address of the employeee
     * @param position position of the employee
     */
    public Employee(String username, String firstName, String lastName, int phoneNo, String eMail, double salary, String passHash, Address address, EmployeePosition position) {
        this.username.set(username);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.phoneNo.set(phoneNo);
        this.eMail.set(eMail);
        this.salary.set(salary);
        this.passHash.set(passHash);
        this.address.set(address);
        this.position.set(position);

    }
    /**
     * Constructor without password
     *
     * @param int employeeId
     * @param  String username
     * @param  double salary
     * @return
     */
    public Employee(int employeeId, String username, double salary) {
        this.employeeId.set(employeeId);
        this.username.set(username);
        this.salary.set(salary);
    }

    public int getEmployeeId() {
        return employeeId.get();
    }

    public IntegerProperty employeeIdProperty() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId.set(employeeId);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public int getPhoneNo() {
        return phoneNo.get();
    }

    public IntegerProperty phoneNoProperty() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo.set(phoneNo);
    }

    public String geteMail() {
        return eMail.get();
    }

    public StringProperty eMailProperty() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail.set(eMail);
    }

    public double getSalary() {
        return salary.get();
    }

    public DoubleProperty salaryProperty() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary.set(salary);
    }

    public String getPassHash() {
        return passHash.get();
    }

    public StringProperty passHashProperty() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash.set(passHash);
    }

    public Address getAddress() {
        return address.get();
    }

    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    public void setAddress(Address address) {
        this.address.set(address);
    }

    public EmployeePosition getPosition() {
        return position.get();
    }

    public ObjectProperty<EmployeePosition> positionProperty() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position.set(position);
    }

    public boolean isChecked() {
        return checked.get();
    }

    public BooleanProperty checkedProperty() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public boolean isAdmin(){
        return (position.get().getId() == ADMIN);}
    public boolean isCEO() {
        return (position.get().getId() == CEO);
    }
    public boolean isChef() {
        return (position.get().getId() == CHEF);
    }
    public boolean isDriver() {
        return (position.get().getId() == DRIVER);
    }
    public boolean isSales() {
        return (position.get().getId() == SALES);
    }
    public boolean isNutrition() {
        return (position.get().getId() == NUTRITION);
    }
}