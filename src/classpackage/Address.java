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

    /**
     * Creates a new Address object from the database
     *
     * @param addressId Address Id
     * @param address The address
     * @param zipCode The zip code of the address
     * @param place the place
     */
    public Address(int addressId, String address, int zipCode, String place) {
        this.addressId.set(addressId);
        this.address.set(address);
        this.zipCode.set(zipCode);
        this.place.set(place);
    }

    /**
     * Creates a new Address object to the database
     *
     * @param String address
     * @param  int zipCode
     * @param  String place
     */
    public Address(String address, int zipCode, String place) {
        this.address.set(address);
        this.zipCode.set(zipCode);
        this.place.set(place);
    }

    /**
     * Return unique address identifier
     *
     * @return int ID of the address
     */
    public int getAddressId() {
        return addressId.get();
    }

    public IntegerProperty addressIdProperty() {
        return addressId;
    }

    /**
     * Sets the unique ID of the object
     *
     * @param addressId the unique ID of the object
     */
    public void setAddressId(int addressId) {
        this.addressId.set(addressId);
    }

    /**
     * Returns the private member Address
     *
     * @return Address
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * Returns the private member Address in style for JavaFX (StringProperty)
     *
     * @return StringProperty
     */
    public StringProperty addressProperty() {
        return address;
    }

    /**
     * Sets the address member of the object
     *
     * @param address the string address
     */
    public void setAddress(String address) {
        this.address.set(address);
    }


    /**
     * Returns the private member zipCode
     *
     * @return the int zipCode
     */
    public int getZipCode() {
        return zipCode.get();
    }

    /**
     * Returns the private member zipCode in style for JavaFX (IntegerProperty)
     *
     * @return IntegerProperty
     */
    public IntegerProperty zipCodeProperty() {
        return zipCode;
    }

    /**
     * Sets the zipCode member of the object
     *
     * @param zipCode the int zipCode
     */
    public void setZipCode(int zipCode) {
        this.zipCode.set(zipCode);
    }

    /**
     * Returns the private member place
     *
     * @return the string place
     */
    public String getPlace() {
        return place.get();
    }

    /**
     * Returns the private member place in style for JavaFX (StringProperty)
     *
     * @return StringProperty
     */
    public StringProperty placeProperty() {
        return place;
    }

    /**
     * Sets the place member of the object
     *
     * @param place the string place
     */
    public void setPlace(String place) {
        this.place.set(place);
    }
}
