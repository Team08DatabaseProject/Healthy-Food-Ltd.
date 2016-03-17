package classpackage;

/**
 * Created by roger on 10.03.2016.
 */

public class TableTest {
	private final String orderNo;
	private final String dishes;

	public TableTest(String orderNo, String dishes) {
		this.orderNo = orderNo;
		this.dishes = dishes;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public String getDishes() {
		return dishes;
	}
}
