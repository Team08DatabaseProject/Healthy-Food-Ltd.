package classpackage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by paul thomas on 16.03.2016.
 */
public class Supplier {
    private int supplierId;
    private int phoneNumber;
    private Address thisAddress;
    private StringProperty businessName = new SimpleStringProperty();
//    from database
    public Supplier(int supplierId, int phoneNumber, Address thisAddress, String businessName) {
        this.supplierId = supplierId;
        this.phoneNumber = phoneNumber;
        this.thisAddress = thisAddress;
        this.businessName.set(businessName);
    }
//    To database
    public Supplier(int phoneNumber, Address thisAddress) {
        this.phoneNumber = phoneNumber;
        this.thisAddress = thisAddress;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getThisAddress() {
        return thisAddress;
    }

    public void setThisAddress(Address thisAddress) {
        this.thisAddress = thisAddress;
    }

    public String getBusinessName() {
        return businessName.get();
    }

    public StringProperty businessNameProperty() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName.set(businessName);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId=" + supplierId +
                ", phoneNumber=" + phoneNumber +
                ", thisAddress=" + thisAddress +
                ", businessName=" + getBusinessName() +
                '}';
    }
}
