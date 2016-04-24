package classpackage;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Created by HUMBUG on 23.04.2016.
 */
public class POrder {

	private IntegerProperty pOrderId = new SimpleIntegerProperty();
	private IntegerProperty supplierId = new SimpleIntegerProperty();
	private ObjectProperty<LocalDate> placedDate;
	private ObservableList<POrderLine> pOrderLines = FXCollections.observableArrayList();
	private DoubleBinding grandTotalBinding;
	private DoubleProperty grandTotal = new SimpleDoubleProperty();

	// Constructor for getting order from database.
	public POrder(int pOrderId, int supplierId, LocalDate placedDate, ObservableList<POrderLine> pOrderLines) {
		this.pOrderId .set(pOrderId);
		this.supplierId.set(supplierId);
		this.placedDate.set(placedDate);
		this.pOrderLines = pOrderLines;
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
	public POrder(int supplierId, ObservableList<POrderLine> pOrderLines) {
		this.supplierId.set(supplierId);
		this.pOrderLines = pOrderLines;
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

	public int getSupplierId() {
		return supplierId.get();
	}

	public IntegerProperty supplierIdProperty() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId.set(supplierId);
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

	public void setPlacedDate(LocalDate placedDate) {
		this.placedDate.set(placedDate);
	}

	public ObjectProperty<LocalDate> placedDateProperty() {
		return placedDate;
	}

	public double getGrandTotal() {
		return grandTotal.get();
	}

	public ReadOnlyDoubleProperty grandTotalProperty() {
		return grandTotal;
	}
}
