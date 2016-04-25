package classpackage;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by HUMBUG on 23.04.2016.
 */
public class POrder {

	private IntegerProperty pOrderId = new SimpleIntegerProperty();
	private ObjectProperty<Supplier> supplier = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> placedDate = new SimpleObjectProperty<>();
	private ObservableList<POrderLine> pOrderLines = FXCollections.observableArrayList();
	private BooleanProperty received = new SimpleBooleanProperty();
	private DoubleBinding grandTotalBinding;
	private DoubleProperty grandTotal = new SimpleDoubleProperty();

	// Constructor for getting order from database.
	// Bollock
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

	// Constructor for creating a new order from GUI.
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

	public void setpOrderLines(ObservableList<POrderLine> pOrderLines) {
		this.pOrderLines = pOrderLines;
	}

	public LocalDate getPlacedDate() {
		return placedDate.get();
	}

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
