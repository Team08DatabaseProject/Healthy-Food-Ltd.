package classpackage;
/**
 * Created by axelkvistad on 3/18/16.
 */
public class EmployeePosition {

  private int id;
	private String description;
	private double defaultSalary;

	/**
	 * Creates the EmployeePosition object
	 *
	 * @param id the unique identifier for the EmployeePosition onject
	 * @param description the description of the position
	 * @param defaultSalary the default salary, though it can be altered
	 */
	public EmployeePosition(int id, String description, double defaultSalary) {
		this.id = id;
		this.description = description;
		this.defaultSalary = defaultSalary;
	}

	/**
	 * Creates the EmployeePosition object
	 *
	 * @param description the description of the position
	 * @param defaultSalary the default salary, though it can be altered
	 */
	public EmployeePosition(String description, double defaultSalary) {
		this.description = description;
		this.defaultSalary = defaultSalary;
	}

	/**
	 * Returns the unique identifier
	 *
	 * @return int ID of the object
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the description of the position
	 *
	 * @return string the description of the position
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the position
	 *
	 * @param description the description to be set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the default salary of the position
	 *
	 * @return double the unaltered (default) salary of the position
	 */
	public double getDefaultSalary() {
		return defaultSalary;
	}

	/**
	 * Sets the default salary of the position
	 *
	 * @param description the default salary of the position
	 */
	public void setDefaultSalary(double defaultSalary) {
		this.defaultSalary = defaultSalary;
	}

	/**
	 * String representation of the object (the id and description of the position)
	 */
	public String toString() {
		return id + ". " + description;
	}
}
