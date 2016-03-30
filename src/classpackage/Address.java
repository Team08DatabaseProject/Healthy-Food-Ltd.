package classpackage;

/**
 * Created by paul thomas on 16.03.2016.
 */
public class Address {
    private int addressId;
    private String address;
    private int zipCode;

    public Address(int addressId, String address, int zipCode) {
        this.addressId = addressId;
        this.address = address;
        this.zipCode = zipCode;
    }

    public Address(String address, int zipCode) {
        this.address = address;
        this.zipCode = zipCode;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}
