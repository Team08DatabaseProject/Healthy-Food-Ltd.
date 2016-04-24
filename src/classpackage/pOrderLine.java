package classpackage;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;

/**
 * Created by HUMBUG on 22.04.2016.
 */
public class POrderLine {

	private IntegerProperty pOrderId = new SimpleIntegerProperty();
	private ObjectProperty<Ingredient> ingredient = new SimpleObjectProperty<>();
	private DoubleProperty quantity = new SimpleDoubleProperty();
	private BooleanProperty checked = new SimpleBooleanProperty();
	private NumberBinding totalBinding;
	private DoubleProperty total = new SimpleDoubleProperty();

	public POrderLine(int pOrderId, Ingredient ingredient, double quantity) {
		this.pOrderId.set(pOrderId);
		this.ingredient.set(ingredient);
		this.quantity.set(quantity);
		totalBinding = this.quantity.multiply(ingredient.priceProperty());
		total.bind(this.totalBinding);
	}

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
