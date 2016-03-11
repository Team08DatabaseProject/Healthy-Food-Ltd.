/**
 * Created by roger on 10.03.2016.
 */

import javafx.beans.property.SimpleStringProperty;

public class TableTest {
	private final SimpleStringProperty orderNo;
	private final SimpleStringProperty dishes;

	public TableTest(String orderNo, String dishes) {
		this.orderNo = new SimpleStringProperty(orderNo);
		this.dishes = new SimpleStringProperty(dishes);
	}

	public String getOrderNo() {
		return orderNo.get();
	}

	public String getDishes() {
		return dishes.get();
	}
}