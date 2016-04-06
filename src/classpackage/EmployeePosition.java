package classpackage;

import java.util.Map;

/**
 * Created by axelkvistad on 3/18/16.
 */
public class EmployeePosition {

  private int id;
	private String description;
	private double defaultSalary;

	public EmployeePosition(int id, String description, double defaultSalary) {
		this.id = id;
		this.description = description;
		this.defaultSalary = defaultSalary;
	}

	public EmployeePosition(String description, double defaultSalary) {
		this.description = description;
		this.defaultSalary = defaultSalary;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDefaultSalary() {
		return defaultSalary;
	}

	public void setDefaultSalary(double defaultSalary) {
		this.defaultSalary = defaultSalary;
	}
}
