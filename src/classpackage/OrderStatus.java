package classpackage;

import javafx.beans.property.*;

/**
 * Created by HUMBUG on 20.04.2016.
 */
public class OrderStatus {
	private IntegerProperty statusId = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();

	public OrderStatus(int statusId, String name) {
		this.statusId.set(statusId);
		this.name.set(name);
	}

	public OrderStatus(int statusId) {
		this.statusId.set(statusId);
	}

	public int getStatusId() {
		return statusId.get();
	}

	public IntegerProperty statusIdProperty() {
		return statusId;
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}
}
