package classpackage;

import javafx.beans.property.*;

/**
 * Created by HUMBUG on 20.04.2016.
 */
public class OrderStatus {
	private IntegerProperty statusId = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();

	/**
	 * Creates the OrderStatus object from the database
	 *
	 * @param statusId the unique identifier of the order status
	 * @param name the name of the order status
	 */
	public OrderStatus(int statusId, String name) {
		this.statusId.set(statusId);
		this.name.set(name);
	}



	/**
	 * Creates the OrderStatus object
	 *
	 * @param statusId the unique identifier of the order status
	 */
	public OrderStatus(int statusId) {
		this.statusId.set(statusId);
	}

	/**
	 * Returns the unique identifier of the order status
	 *
	 * @return the unique identifier of the order status
	 */
	public int getStatusId() {
		return statusId.get();
	}

	/**
	 * Returns the unique identifier of the order status in style for JavaFX (IntegerProperty)
	 *
	 * @return the unique identifier of the order status
	 */
	public IntegerProperty statusIdProperty() {
		return statusId;
	}

	/**
	 * Returns the name of the order status
	 *
	 * @return the unique identifier of the order status
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * Returns the name of the order status  in style for JavaFX (StringProperty)
	 *
	 * @return the unique identifier of the order status
	 */
	public StringProperty nameProperty() {
		return name;
	}
}
