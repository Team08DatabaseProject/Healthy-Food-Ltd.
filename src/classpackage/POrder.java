package classpackage;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * An object for saving all the contents of a Purchase order.
 * Contains metadata for the purchase order itself as well as an object
 * representing the supplier and an ObservableList containing each line of the order,
 * stored as POrderLine objects.
 *
 * A number binding calculating the grand total of the entire purchase order is also provided.
 * It does not support adding or removing purchase order lines after object creation as that
 * is not possible in the GUI.
 */
public class POrder {

	private IntegerProperty pOrderId = new SimpleIntegerProperty();
	private ObjectProperty<Supplier> supplier = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> placedDate = new SimpleObjectProperty<>();
	private ObservableList<POrderLine> pOrderLines = FXCollections.observableArrayList();
	private BooleanProperty received = new SimpleBooleanProperty();
	private DoubleBinding grandTotalBinding;
	private DoubleProperty grandTotal = new SimpleDoubleProperty();

	/**
	 * Constructor for retrieving the complete purchase order from the database.
	 * @param pOrderId	The ID of the purchase order. Automatically generated when order was inserted into the database.
	 * @param supplier	An object containing the data of the supplier to which the purchase order was sent.
	 * @param placedDate	The date on which the purchase order was saved.
	 * @param pOrderLines	An ObservableList containing POrderLine objects representing each line of the purchase order.
	 * @param received	Set to true if the order has been received and put in storage.
	 */
	public POrder(int pOrderId, Supplier supplier, LocalDate placedDate, ObservableList<POrderLine> pOrderLines, boolean received) {
		this.pOrderId .set(pOrderId);
		this.supplier.set(supplier);
		this.placedDate.set(placedDate);
		this.pOrderLines = pOrderLines;
		this.received.set(received);
		for(POrderLine pOrderLine : this.pOrderLines) {
			if(grandTotalBinding == null) {
				grandTotalBinding = pOrderLine.totalProperty().add(0);
			} else {
				grandTotalBinding = grandTotalBinding.add(pOrderLine.totalProperty());
			}
		}
		grandTotal.bind(grandTotalBinding);
	}

	/**
	 * Constructor for saving a new purchase order from the GUI.
	 * @param supplier	An object containing the data of the supplier to which the purchase order was sent.
	 * @param pOrderLines	An ObservableList containing POrderLine objects representing each line of the purchase order.
	 */
	public POrder(Supplier supplier, ObservableList<POrderLine> pOrderLines) {
		this.supplier.set(supplier);
		this.pOrderLines = pOrderLines;
		this.received.set(false);
		for(POrderLine pOrderLine : this.pOrderLines) {
			if(grandTotalBinding == null) {
				grandTotalBinding = pOrderLine.totalProperty().add(0);
			} else {
				grandTotalBinding = grandTotalBinding.add(pOrderLine.totalProperty());
			}
		}
		grandTotal.bind(grandTotalBinding);
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

	public Supplier getSupplier() {
		return supplier.get();
	}

	public ObjectProperty<Supplier> supplierProperty() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier.set(supplier);
	}

	public ObservableList<POrderLine> getpOrderLines() {
		return pOrderLines;
	}

	public LocalDate getPlacedDate() {
		return placedDate.get();
	}
	/**
	 * Returns an ObservableList containing all lines of the purchase order.
	 * @return An ObservableListPOrderLine.
	 */
	public String getFormattedPlacedDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		return placedDate.get().format(formatter);
	}

	public void setPlacedDate(LocalDate placedDate) {
		this.placedDate.set(placedDate);
	}


	public ObjectProperty<LocalDate> placedDateProperty() {
		return placedDate;
	}

	public boolean isReceived() {
		return received.get();
	}

	public BooleanProperty receivedProperty() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received.set(received);
	}

	public int getNumberOfLines() {
		return pOrderLines.size();
	}

	public double getGrandTotal() {
		return grandTotal.get();
	}

	public ReadOnlyDoubleProperty grandTotalProperty() {
		return grandTotal;
	}
}
