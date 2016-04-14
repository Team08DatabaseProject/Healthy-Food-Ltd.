package classpackage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by paul thomas on 16.03.2016.
 */
public class Address {
    private IntegerProperty addressId = new SimpleIntegerProperty();
    private StringProperty address = new SimpleStringProperty();
    private IntegerProperty zipCode = new SimpleIntegerProperty();
    private StringProperty place = new SimpleStringProperty();

    public Address(int addressId, String address, int zipCode, String place) {
        this.addressId.set(addressId);
        this.address.set(address);
        this.zipCode.set(zipCode);
        this.place.set(place);
    }

    public Address(String address, int zipCode, String place) {
        this.address.set(address);
        this.zipCode.set(zipCode);
        this.place.set(place);
    }

    public int getAddressId() {
        return addressId.get();
    }

    public IntegerProperty addressIdProperty() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId.set(addressId);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public int getZipCode() {
        return zipCode.get();
    }

    public IntegerProperty zipCodeProperty() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode.set(zipCode);
    }

    public String getPlace() {
        return place.get();
    }

    public StringProperty placeProperty() {
        return place;
    }

    public void setPlace(String place) {
        this.place.set(place);
    }
}
