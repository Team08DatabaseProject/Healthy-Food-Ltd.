package classpackage;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;

/**
 * An object for saving the data of one purchase order line.
 * Contains the metadata itself as as well as an object representing the ingredient that's being obught.
 * A number binding calculating the total price of the line is also provided.
 */
public class POrderLine {

	private IntegerProperty pOrderId = new SimpleIntegerProperty();
	private ObjectProperty<Ingredient> ingredient = new SimpleObjectProperty<>();
	private DoubleProperty quantity = new SimpleDoubleProperty();
	private BooleanProperty checked = new SimpleBooleanProperty();
	private NumberBinding totalBinding;
	private DoubleProperty total = new SimpleDoubleProperty();

	/**
	 * Constructor for retrieving the purchase order line from the database.
	 * @param pOrderId	The ID of the purchase order. Automatically generated when the order was inserted into the database.
	 * @param ingredient	An object containing the data of the ingredient that is being bought.
	 * @param quantity	The quantity of the ingredient being bought.
	 */
	public POrderLine(int pOrderId, Ingredient ingredient, double quantity) {
		this.pOrderId.set(pOrderId);
		this.ingredient.set(ingredient);
		this.quantity.set(quantity);
		totalBinding = this.quantity.multiply(ingredient.priceProperty());
		total.bind(this.totalBinding);
	}

	/**
	 * Constructor for creating a new purchase order line from the GUI. Missing ID, which is later inserted by the database.
	 * @param ingredient	An object containing the data of the ingredient that is being bought.
	 * @param quantity	The quantity of the ingredient being bought.
	 */
	public POrderLine(Ingredient ingredient, double quantity) {
		this.ingredient.set(ingredient);
		this.quantity.set(quantity);
		totalBinding = this.quantity.multiply(ingredient.priceProperty());
		total.bind(this.totalBinding);
	}

	public int getpOrderId() {
		return pOrderId.get();
	}

	public IntegerProperty pOrderIdProperty() {
		return pOrderId;
	}

	public void setpOrderId(int pOrderId) {
		this.pOrderId.set(pOrderId);
	}

	public Ingredient getIngredient() {
		return ingredient.get();
	}

	public ObjectProperty ingredientProperty() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient.set(ingredient);
	}

	public double getQuantity() {
		return quantity.get();
	}

	public DoubleProperty quantityProperty() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity.set(quantity);
	}

	public double getTotal() {
		return total.get();
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

	public ReadOnlyDoubleProperty totalProperty() {
		return total;
	}
}
